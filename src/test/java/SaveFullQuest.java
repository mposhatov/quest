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
                    Collections.singletonList(Role.ROLE_GAMER));
            clientRepository.save(client.addRate());
        }
    }

    @Test
    @Commit
    public void saveRequests() throws IOException {
        for (int i = 0; i < 50; ++i) {
            final DbClient client = new DbClient(
                    Collections.singletonList(Role.ROLE_GAMER));
            final DbClient dbClient = clientRepository.save(client.addRate());
            assignRateGameRequestRepository.save(new DbAssignRateGameRequest(dbClient));
        }
    }
}
