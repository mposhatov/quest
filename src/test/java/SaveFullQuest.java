import com.mposhatov.dao.QuestRepository;
import com.mposhatov.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/test-settings.xml"})
public class SaveFullQuest {

    @Autowired
    private QuestRepository questRepository;

    @Test
    @Commit
    @Transactional
    public void save() throws IOException {

        DbQuest quest = new DbQuest("Прогулка в лесу", "Вам предстоит прогулка по дивным лесам",
                Difficulty.EASY, 100, Collections.singletonList(Category.ADVENTURE), "3.jpeg");

        quest.approve();

        DbStep step1 = new DbStep("Вы идете по лесу и видете две дороги.", "1.jpg", quest);

        DbStep step2 = new DbStep("Хорошо. Вы выбрали левую дорогу", "2.jpg", quest);

        DbStep step3 = new DbStep("Хорошо. Вы выбрали правую дорогу.", "2.jpg", quest);

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
}
