import com.mposhatov.dao.*;
import com.mposhatov.entity.*;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
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
    public void init() throws URISyntaxException, IOException {
        DbQuest quest = new DbQuest("Прогулка в лесу", "Вам предстоит прогулка по дивным лесам");

        File backFile1 = new File("C:\\Users\\Zver\\Desktop\\Морской бой\\images.jpg");
        InputStream inputStream1 = new FileInputStream(backFile1);
        byte[] bytes1 = new byte[(int) backFile1.length()];
        inputStream1.read(bytes1);

        File backFile2 = new File("C:\\Users\\Zver\\Desktop\\Морской бой\\main_21238.jpg");
        InputStream inputStream2 = new FileInputStream(backFile2);
        byte[] bytes2 = new byte[(int) backFile2.length()];
        inputStream2.read(bytes2);

        DbBackground dbBackgroundStep1 = new DbBackground(bytes1, "image/jpeg;base64", quest);
        DbBackground dbBackgroundStep2 = new DbBackground(bytes2, "image/jpeg;base64", quest);

        DbStep step1 = new DbStep("Вы идете по лесу и видете две дороги.", dbBackgroundStep1, quest);

        DbStep step2 = new DbStep("Хорошо. Вы выбрали левую дорогу", dbBackgroundStep2, quest);

        DbStep step3 = new DbStep("Хорошо. Вы выбрали правую дорогу.", dbBackgroundStep2, quest);

        quest.setStartStep(step1);
        quest.addSteps(Arrays.asList(step1, step2, step3));

        DbSubject dbSubjectWin = new DbSubject(
                "Предмет победителя", "Этот предмет необходим для того чтобы пройти квест");
        DbSubject dbSubjectWin1 = new DbSubject(
                "Предмет победителя 1", "Этот предмет необходим для того чтобы пройти квест 1");

        DbEvent dbEvent = new DbEvent("Вы сделали дело", "Вы сделали стоящее дело, вы мо-ло-дец!!!");

        DbAnswer dbAnswer11 = new DbAnswer("Выбрать левую дорогу и получить предмет побелителя", step2);
        dbAnswer11.addGivingSubject(dbSubjectWin);
        dbAnswer11.addGivingSubject(dbSubjectWin1);
        dbAnswer11.addGivingEvent(dbEvent);
        DbAnswer dbAnswer12 = new DbAnswer("Выбрать правую дорогу", step3);

        DbAnswer dbAnswer21 = new DbAnswer("Очень жаль, но зато вы живы!", false);

        DbAnswer dbAnswer31 = new DbAnswer("Очень жаль", false);

        DbAnswer dbAnswerWin = new DbAnswer("Вы победили", true);
        dbAnswerWin.addRequirementSubject(dbSubjectWin);
        dbAnswerWin.addRequirementEvent(dbEvent);

        step1.addAnswers(Arrays.asList(dbAnswer11, dbAnswer12, dbAnswerWin));
        step2.addAnswers(Arrays.asList(dbAnswer21, dbAnswerWin));
        step3.addAnswers(Arrays.asList(dbAnswer31, dbAnswerWin));
        questRepository.save(quest);
    }

    @Test
    @Commit
    @Transactional
    public void testDb() {
        List<DbQuest> all = questRepository.findAll();
        System.out.println(all);
    }
}
