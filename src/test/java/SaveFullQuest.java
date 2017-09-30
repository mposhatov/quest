import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.WarriorRepository;
import com.mposhatov.dao.HeroRepository;
import com.mposhatov.dao.GameSearchRequestRepository;
import com.mposhatov.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/test-settings.xml"})
public class SaveFullQuest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private WarriorRepository warriorRepository;

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private GameSearchRequestRepository gameSearchRequestRepository;

}
