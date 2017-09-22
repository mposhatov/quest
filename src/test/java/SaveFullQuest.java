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

    @Test
    @Commit
    @Transactional
    public void addWarrior() throws IOException {
        final DbHero hero = heroRepository.findOne(2L);
        hero.addWarrior(
                new DbWarrior(
                        new DbWarriorCharacteristics(0,
                                2, 0,
                                90, 2, 6, 11), hero,
                        new DbWarriorDescription("Марлок", "Марлок", "1.jpg", 1,
                                new DbWarriorCharacteristics(1,
                                        1, 1,
                                        10, 1, 1, 1))));
    }


    @Test
    @Commit
    @Transactional
    public void addQueue() throws IOException {
        for(int i = 0; i < 100; ++i) {
            final DbClient dbClient = clientRepository.save(new DbClient(Collections.singletonList(Role.ROLE_GAMER)));
            gameSearchRequestRepository.save(new DbGameSearchRequest(dbClient));
        }
    }



}
