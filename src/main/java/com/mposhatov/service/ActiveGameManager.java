package com.mposhatov.service;

import com.mposhatov.dao.*;
import com.mposhatov.dto.*;
import com.mposhatov.entity.*;
import com.mposhatov.exception.ActiveGameException;
import com.mposhatov.exception.ClientException;
import com.mposhatov.exception.LogicException;
import com.mposhatov.holder.ActiveGame;
import com.mposhatov.holder.ActiveGameHolder;
import com.mposhatov.request.GetUpdatedActiveGameProcessor;
import com.mposhatov.service.validator.ActiveGameExceptionThrower;
import com.mposhatov.util.Calculator;
import com.mposhatov.util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ActiveGameManager {

    private final Logger logger = LoggerFactory.getLogger(ActiveGameManager.class);

    @Autowired
    private ActiveGameHolder activeGameHolder;

    @Autowired
    private GetUpdatedActiveGameProcessor getUpdatedActiveGameProcessor;

    @Autowired
    private ClosedGameRepository closedGameRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private HeroLevelRequirementRepository heroLevelRequirementRepository;

    @Autowired
    private WarriorRepository warriorRepository;

    @Autowired
    private HierarchyWarriorRepository hierarchyWarriorRepository;

    @Autowired
    private ActiveGameExceptionThrower activeGameExceptionThrower;

    @Autowired
    private AttackSimulator attackSimulator;

    @Autowired
    private DefendSimulator defendSimulator;

    @Value("${game.ratingByWin}")
    private int ratingByWin;

    public ActiveGame createGame(Client firstClient, Client secondClient) throws LogicException {

        final List<Warrior> queueWarriors = new LinkedList<>();

        queueWarriors.addAll(firstClient.getHero().getWarriors());
        queueWarriors.addAll(secondClient.getHero().getWarriors());

        queueWarriors.sort(Comparator.comparing(o -> o.getWarriorCharacteristics().getVelocity()));

        final ActiveGame activeGame = new ActiveGame(
                activeGameHolder.generateActiveGameId(), firstClient, secondClient, queueWarriors);

        activeGameHolder.registerActiveGame(activeGame);

        return activeGame;
    }

    public void simpleAttack(Warrior attackWarrior, Warrior defendWarrior) {

        final int damage = attackSimulator.generateDamage(attackWarrior);

        int takingDamage =
                defendSimulator.generateTakingDamage(
                        defendWarrior, damage, attackWarrior.getWarriorCharacteristics().getAttackType());

        defendWarrior.getWarriorCharacteristics().minusHealth(takingDamage);

        final int vampirismHealth =
                Calculator.calculatePercentageOf(attackWarrior.getWarriorCharacteristics().getVampirism(), takingDamage);

        attackWarrior.getWarriorCharacteristics().plusHealth(vampirismHealth);
    }

    public void spellAttack(Warrior attackWarrior, SpellAttack spellAttack, Warrior defendWarrior) {

        final int damage =
                attackSimulator.generateDamage(
                        spellAttack, attackWarrior.getWarriorCharacteristics().getSpellPower());

        int takingDamage = defendSimulator.generateTakingDamage(defendWarrior, damage, AttackType.MAGICAL);

        defendWarrior.getWarriorCharacteristics().minusHealth(takingDamage);

        attackWarrior.getWarriorCharacteristics().minusMana(spellAttack.getMana());
    }

    public void spellHeal(Warrior castingWarrior, SpellHeal spellHeal, Warrior targetWarrior) {

        targetWarrior.getWarriorCharacteristics()
                .plusHealth(
                        spellHeal.getHealth() +
                                spellHeal.getHealthBySpellPower() *
                                        castingWarrior.getWarriorCharacteristics().getSpellPower());

        castingWarrior.getWarriorCharacteristics().minusMana(spellHeal.getMana());

    }

    public void spellExhortation(ActiveGame activeGame, Warrior castingWarrior, SpellExhortation spellExhortation, Integer position) {

        activeGame.addWarrior(spellExhortation.getHierarchyWarrior(), position, castingWarrior.getHero());

        castingWarrior.getWarriorCharacteristics().minusMana(spellExhortation.getMana());
    }

    public void spellPassive(ActiveGame activeGame, Warrior castingWarrior, SpellPassive spellPassive, Warrior targetWarrior) {

        activeGame.addEffect(castingWarrior, targetWarrior, spellPassive);

        castingWarrior.getWarriorCharacteristics().minusMana(spellPassive.getMana());
    }

    public boolean refresh(ActiveGame activeGame) throws ClientException.HasNotActiveGame {

        final ArrayList<Long> killedWarriors = new ArrayList<>();

        for (Warrior warrior : activeGame.getQueueWarriors()) {
            if (warrior.getWarriorCharacteristics().getHealth() == 0) {
                killedWarriors.add(warrior.getId());
            }
        }

        for (Long warriorId : killedWarriors) {
            activeGame.registerDeadWarrior(warriorId);
        }

        if (activeGame.getFirstClient().getHero().getWarriors().isEmpty()) {
            activeGame.setWinClient(activeGame.getSecondClient().getId());
            activeGame.gameOver();
        } else if (activeGame.getSecondClient().getHero().getWarriors().isEmpty()) {
            activeGame.setWinClient(activeGame.getFirstClient().getId());
            activeGame.gameOver();
        }

        return activeGame.isGameOver();
    }

    public ActiveGame stepUp(ActiveGame activeGame) throws ActiveGameException.InvalidCurrentStepInQueue {
        return stepUp(activeGame, null);
    }

    public ActiveGame stepUp(ActiveGame activeGame, SpellPassive spellPassiveForHimself) throws ActiveGameException.InvalidCurrentStepInQueue {

        final Warrior warrior = activeGame.getCurrentWarrior();

        final List<Effect> expiredEffects = new ArrayList<>();

        for (Effect effect : warrior.getEffects()) {

            if (spellPassiveForHimself == null || !effect.getName().equals(spellPassiveForHimself.getName())) {
                effect.stepUp();
            }

            if (effect.isExpired()) {
                expiredEffects.add(effect);
            }
        }

        for (Effect expiredEffect : expiredEffects) {
            warrior.deleteEffect(expiredEffect);
        }

        return activeGame.stepUp();
    }

    public ClosedGame closeGame(long activeGameId) throws LogicException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameById(activeGameId);

        activeGameExceptionThrower.throwExceptionIfActiveGameDoesNotContainTwoClients(activeGame);
        activeGameExceptionThrower.throwExceptionIfActiveGameDoesNotContainWinClient(activeGame);

        final DbClient dbFirstClient = clientRepository.getOne(activeGame.getFirstClient().getId());
        final DbClient dbSecondClient = clientRepository.getOne(activeGame.getSecondClient().getId());

        ClientGameResult firstClientGameResult = new ClientGameResult();
        ClientGameResult secondClientGameResult = new ClientGameResult();

        if (activeGame.getWinClientId() == null) {
            firstClientGameResult = getDeadHeatClientGameResult(activeGame, dbFirstClient);
            secondClientGameResult = getDeadHeatClientGameResult(activeGame, dbSecondClient);
        } else if (activeGame.getWinClientId().equals(dbFirstClient.getId())) {
            firstClientGameResult = getWinClientGameResult(activeGame, dbFirstClient);
            secondClientGameResult = getLoseClientGameResult(activeGame, dbSecondClient);
        } else if (activeGame.getWinClientId().equals(dbSecondClient.getId())) {
            secondClientGameResult = getWinClientGameResult(activeGame, dbSecondClient);
            firstClientGameResult = getLoseClientGameResult(activeGame, dbFirstClient);
        }

        dbFirstClient.addRating(firstClientGameResult.getRating());
        dbSecondClient.addRating(secondClientGameResult.getRating());

        final DbClosedGame closedGame = closedGameRepository.saveAndFlush(new DbClosedGame(activeGame.getCreateAt()));

        final DbClientGameResult dbFirstClientGameResult =
                new DbClientGameResult(
                        dbFirstClient, closedGame, firstClientGameResult.isWin(), firstClientGameResult.getRating());

        final DbClientGameResult dbSecondClientGameResult =
                new DbClientGameResult(
                        dbSecondClient, closedGame, secondClientGameResult.isWin(), secondClientGameResult.getRating());

        closedGame.addGameResults(Arrays.asList(dbFirstClientGameResult, dbSecondClientGameResult));

        activeGameHolder.deregisterActiveGame(activeGame.getId());

        return new ClosedGame(closedGame.getStartTime(), closedGame.getFinishTime(), firstClientGameResult, secondClientGameResult);
    }

    private ClientGameResult getWinClientGameResult(ActiveGame activeGame, DbClient client) throws LogicException {

        final List<WarriorUpgrade> warriorUpgrades = addExperience(
                activeGame,
                client.getHero(),
                activeGame.getStartWarriorsByClientId(client.getId()),
                activeGame.getKilledWarriorIdsByClientId(client.getId()));

        return new ClientGameResult().clientId(client.getId())
                .win()
                .rating(ratingByWin)
                .warriorUpgrades(warriorUpgrades);
    }

    private ClientGameResult getLoseClientGameResult(ActiveGame activeGame, DbClient client) throws LogicException {

        final List<WarriorUpgrade> warriorUpgrades = addExperience(
                activeGame,
                client.getHero(),
                activeGame.getStartWarriorsByClientId(client.getId()),
                activeGame.getKilledWarriorIdsByClientId(client.getId()));

        return new ClientGameResult().clientId(client.getId())
                .rating(-ratingByWin)
                .warriorUpgrades(warriorUpgrades);
    }

    private ClientGameResult getDeadHeatClientGameResult(ActiveGame activeGame, DbClient client) throws LogicException {

        final List<WarriorUpgrade> warriorUpgrades = addExperience(
                activeGame,
                client.getHero(),
                activeGame.getStartWarriorsByClientId(client.getId()),
                activeGame.getKilledWarriorIdsByClientId(client.getId()));

        return new ClientGameResult().clientId(client.getId())
                .warriorUpgrades(warriorUpgrades);
    }

    private List<WarriorUpgrade> addExperience(ActiveGame activeGame, DbHero hero, List<Long> destinationWarriorIds, List<Long> sourceActiveGameWarriorIds) throws LogicException {

        final List<WarriorUpgrade> warriorUpgrades = new ArrayList<>();

        long experience = 0;

        final List<DbWarrior> destinationWarriors = warriorRepository.findAll(destinationWarriorIds);

        for (Long warriorId : sourceActiveGameWarriorIds) {
            experience += activeGame.getWarriorById(warriorId).getKilledExperience();
        }

        final long experienceByOne = experience / (destinationWarriorIds.size() + 1);

        hero = hero.addExperience(experienceByOne);

        DbHeroLevelRequirement heroLevelRequirement = heroLevelRequirementRepository.findOne(hero.getLevel() + 1);

        while (hero.getExperience() >= heroLevelRequirement.getRequirementExperience()) {
            hero = hero.upLevel(heroLevelRequirement.getAdditionalHeroPoint());
            heroLevelRequirement = heroLevelRequirementRepository.findOne(hero.getLevel() + 1);
        }

        for (DbWarrior warrior : destinationWarriors) {

            final WarriorUpgrade warriorUpgrade =
                    new WarriorUpgrade().warriorBeforeUpgrade(EntityConverter.toWarrior(warrior, true, false, false, false, false));

            warrior = warrior.addExperience(experienceByOne);

            DbHierarchyWarrior currentHierarchyWarrior = warrior.getHierarchyWarrior();

            while (warrior.getExperience() >= currentHierarchyWarrior.getImprovementExperience()) {

                final DbHierarchyWarrior nextHierarchyWarrior =
                        hierarchyWarriorRepository.findNextAvailableToUpdateForHero(hero, currentHierarchyWarrior);

                if (nextHierarchyWarrior != null) {
                    warrior.hierarchyWarrior(nextHierarchyWarrior);
                    currentHierarchyWarrior = nextHierarchyWarrior;
                } else {
                    break;
                }

            }

            warriorUpgrade.warriorAfterUpgrade(EntityConverter.toWarrior(warrior, true, false, false, false, false));
            warriorUpgrades.add(warriorUpgrade);

        }

        return warriorUpgrades;
    }

}