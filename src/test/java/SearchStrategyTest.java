import com.mposhatov.dto.*;
import com.mposhatov.entity.AttackType;
import com.mposhatov.exception.ClientIsNotInTheQueueException;
import com.mposhatov.holder.ActiveGameHolder;
import com.mposhatov.holder.ActiveGameSearchRequestHolder;
import com.mposhatov.processor.ClientsOfGame;
import com.mposhatov.service.ActiveGameManager;
import com.mposhatov.strategy.RatingSearchStrategy;
import com.mposhatov.util.ProbabilitySimulator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/test-settings.xml"})
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class SearchStrategyTest {

    @Value("${game.option.searchRequests.sizeLimit}")
    private int sizeLimit;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ActiveGameSearchRequestHolder activeGameSearchRequestHolder;

    @Autowired
    private ActiveGameHolder activeGameHolder;

    @Autowired
    private RatingSearchStrategy ratingSearchStrategy;

    @Autowired
    private ActiveGameManager activeGameManager;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void _01_deregisterRequests() throws ClientIsNotInTheQueueException {
        createRequests();

        Assert.assertEquals(sizeLimit, activeGameSearchRequestHolder.getRequests().size());

        for (int i = 1; i <= sizeLimit; ++i) {
            activeGameSearchRequestHolder.deregisterGameSearchRequestByClientId(i);
        }

        Assert.assertEquals(0, activeGameSearchRequestHolder.getRequests().size());
    }

    @Test
    public void _02_createGame() throws ClientIsNotInTheQueueException {
        createRequests();

        Assert.assertEquals(sizeLimit, activeGameSearchRequestHolder.getRequests().size());

        final List<ClientsOfGame> clientsOfGames = ratingSearchStrategy.search();

        Assert.assertEquals(sizeLimit / 2, clientsOfGames.size());

        int games = 0;

        for (ClientsOfGame clientsOfGame : clientsOfGames) {
            final ActiveGame activeGame =
                    activeGameManager.createGame(clientsOfGame.getFirstCommand(), clientsOfGame.getSecondCommand());

            Assert.assertEquals(true, activeGame.getClientByCommands()
                    .values().contains(clientsOfGame.getFirstCommand()));

            Assert.assertEquals(true, activeGame.getClientByCommands()
                    .values().contains(clientsOfGame.getSecondCommand()));

            games++;

        }

        Assert.assertEquals(sizeLimit / 2, games);
    }

    private void createRequests() {
        for (int i = 1; i <= sizeLimit; ++i) {
            String name = "Client_" + i;
            activeGameSearchRequestHolder.registerGameSearchRequest(
                    new Client(i, name, name + "@mail.ru", null, new Date(),
                            ProbabilitySimulator.generateNumberFromTo(0, 100),
                            new Hero(name, null, null, Collections.singletonList(
                                    new Warrior(i, name, name, true,
                                            new WarriorCharacteristics(1, 1, 1, 1,
                                                    AttackType.PHYSICAL, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1))))));
        }
    }

}
