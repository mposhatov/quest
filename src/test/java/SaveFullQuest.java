import com.mposhatov.dao.AssignRateGameRequestRepository;
import com.mposhatov.dao.ClientRepository;
import com.mposhatov.entity.DbAssignRateGameRequest;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.Collections;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/test-settings.xml"})
public class SaveFullQuest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AssignRateGameRequestRepository assignRateGameRequestRepository;

    @Test
    @Commit
    public void saveClients() throws IOException {
        for (int i = 0; i < 50; ++i) {
            final DbClient client = new DbClient(
                    Collections.singletonList(Role.ROLE_GAMER),
                    1, 1, 1,
                    1, 1,
                    1, 100,
                    1000, 0);
            clientRepository.save(client.addRate(new Random().nextInt(1000) + 1));
        }
    }

    @Test
    @Commit
    public void saveRequests() throws IOException {
        for (int i = 0; i < 50; ++i) {
            final DbClient client = new DbClient(
                    Collections.singletonList(Role.ROLE_GAMER),
                    1, 1, 1,
                    1, 1,
                    1, 100,
                    1000, 0);
            final DbClient dbClient = clientRepository.save(client.addRate(new Random().nextInt(1000) + 1));
            assignRateGameRequestRepository.save(new DbAssignRateGameRequest(dbClient));
        }
    }
}
