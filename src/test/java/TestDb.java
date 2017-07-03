import com.mposhatov.dao.ClientRepository;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.logging.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/test-settings.xml"})
public class TestDb {

    private static final Logger logger = Logger.getLogger("TestDb");

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void testDb() {
        final DbClient mposhatov2 = new DbClient("new1123", "new1123",
                Arrays.asList(Role.ROLE_GAMER, Role.ROLE_ADMIN));
        clientRepository.save(mposhatov2);
        System.out.println();
    }
}
