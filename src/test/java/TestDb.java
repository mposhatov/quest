import com.mposhatov.dao.*;
import com.mposhatov.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

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
    public void init() {
        DbQuest quest = new DbQuest("Прогулка в лесу", "Вам предстоит прогулка по дивным лесам");

        DbStep step1 = new DbStep("Вы идете по лесу и видете две дороги.", quest);

        DbStep step2 = new DbStep("Хорошо. Вы выбрали левую дорогу", quest);

        DbStep step3 = new DbStep("Хорошо. Вы выбрали правую дорогу.", quest);

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

        DbAnswer dbAnswer21 = new DbAnswer("Очень жаль, но зато вы живы!", null);

        DbAnswer dbAnswer31 = new DbAnswer("Очень жаль", null);

        DbAnswer dbAnswerWin = new DbAnswer("Вы победили", null);
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
