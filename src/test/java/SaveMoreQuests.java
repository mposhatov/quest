import com.mposhatov.dao.QuestRepository;
import com.mposhatov.entity.Category;
import com.mposhatov.entity.DbBackground;
import com.mposhatov.entity.DbQuest;
import com.mposhatov.entity.Difficulty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/test-settings.xml"})
public class SaveMoreQuests {

    @Autowired
    private QuestRepository questRepository;

    //note: set startStep nullable = true
    @Test
    @Commit
    @Transactional
    public void save() throws IOException {

        for(Integer i = 0 ; i < 100; ++i) {
            Category category = Category.byCode(ThreadLocalRandom.current().nextInt(1, 13 + 1));
            Difficulty difficulty = Difficulty.byCode(ThreadLocalRandom.current().nextInt(1, 3 + 1));
            DbQuest quest = new DbQuest(i.toString(), i.toString(), difficulty, 200, Arrays.asList(category),
                    ThreadLocalRandom.current().nextInt(1, 2 + 1) + ".jpg");
            quest.approve();
            questRepository.save(quest);
        }
    }
}
