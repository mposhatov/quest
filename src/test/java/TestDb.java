import com.mposhatov.dao.*;
import com.mposhatov.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/test-settings.xml"})
public class TestDb {

    private static final Logger logger = Logger.getLogger("TestDb");

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ActiveGameRepository activeGameRepository;

    @Autowired
    private ClosedGameRepository closedGameRepository;

    @Before
    @Commit
    @Transactional
    public void init() {
        final DbQuest firstQuest = new DbQuest("firstQuest", "Description first Quest");

        final DbStep step1 = new DbStep("Step 1", firstQuest);
        final DbStep step2 = new DbStep("Step 2", firstQuest);
        final DbStep step3 = new DbStep("Step 3", firstQuest);
        final DbStep step4 = new DbStep("Finish Step", firstQuest);

        firstQuest.setStartStep(step1);
        firstQuest.addSteps(Arrays.asList(step2, step3, step4));

        final DbAnswer answer11 = new DbAnswer("1.1", step1, step2);

        final DbSubject oil = new DbSubject("oil", "sweet oil");
        answer11.addRequirementSubject(oil);

        final DbAnswer answer12 = new DbAnswer("1.2", step1, step3);
        final DbAnswer answer13 = new DbAnswer("1.3", step1, step4);

        final DbAnswer answer21 = new DbAnswer("2.1", step2, step4);
        final DbAnswer answer22 = new DbAnswer("2.2", step2, step4);
        final DbAnswer answer23 = new DbAnswer("2.3", step2, step4);

        final DbAnswer answer31 = new DbAnswer("2.1", step3, step4);
        final DbAnswer answer32 = new DbAnswer("2.2", step3, step4);
        final DbAnswer answer33 = new DbAnswer("2.3", step3, step4);

        final DbAnswer answer41 = new DbAnswer("Finish", step1, step4);

        step1.addAnswerSteps(Arrays.asList(answer11, answer12, answer13));
        step2.addAnswerSteps(Arrays.asList(answer21, answer22, answer23));
        step3.addAnswerSteps(Arrays.asList(answer31, answer32, answer33));
        step4.addAnswerSteps(Arrays.asList(answer41));

        final DbClient client = new DbClient("test", "test", Collections.singletonList(Role.ROLE_GAMER));
        final DbActiveGame activeGame = new DbActiveGame(client, firstQuest, new Date());
        activeGame.addSubject(oil);
        activeGameRepository.save(activeGame);
        questRepository.save(firstQuest);
    }

    @Test
    @Commit
    @Transactional
    public void testDb() {
        List<DbAnswer> all = answerRepository.findAll();
        System.out.println(all);
    }
}
