import com.mposhatov.dao.*;
import com.mposhatov.entity.DbAnswer;
import com.mposhatov.entity.DbQuest;
import com.mposhatov.entity.DbStep;
import com.mposhatov.entity.DbSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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
        final DbQuest firstQuest = new DbQuest("Quest", "Description first Quest");

        final DbStep step1 = new DbStep("Pre Finish Step", firstQuest);
        final DbStep step2 = new DbStep("Finish Step", firstQuest);

        firstQuest.setStartStep(step1);
        final DbAnswer answer41 = new DbAnswer("Pre Exit And Get Product", step1, step2);
        final DbSubject product = new DbSubject("Product", "Sweat product");
        answer41.addGivingSubject(product);
        step1.addAnswer(answer41);

        DbAnswer answer21 = new DbAnswer("Game Over", step2, null);
        answer21.addGivingSubject(product);
        step2.addAnswer(answer21);
        int a = 1;
        questRepository.save(firstQuest);
    }

    @Test
    @Commit
    @Transactional
    public void testDb() {
        List<DbQuest> all = questRepository.findAll();
        System.out.println(all);
    }
}
