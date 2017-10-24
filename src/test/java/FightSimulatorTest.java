import com.mposhatov.dto.Warrior;
import com.mposhatov.dto.WarriorCharacteristics;
import com.mposhatov.entity.AttackType;
import com.mposhatov.service.FightSimulator;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/test-settings.xml"})
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class FightSimulatorTest {

    @Autowired
    private FightSimulator fightSimulator;

    @Autowired

    @Test
    public void fightTest() {
        final Warrior warrior1 = createWarrior(1);
        final Warrior warrior2 = createWarrior(2);

        long health = warrior2.getWarriorCharacteristics().getHealth();

        int hits = 0;
        while (!warrior2.isDead()) {
            fightSimulator.simpleAttack(warrior1, warrior2);
            long newHealth = warrior2.getWarriorCharacteristics().getHealth();
            Assert.assertNotEquals(health, newHealth);
            health = newHealth;
            hits++;
        }

        Assert.assertEquals(true, warrior2.isDead());
        Assert.assertEquals(false, warrior1.isDead());

        Assert.assertEquals(0, warrior2.getWarriorCharacteristics().getHealth());

        Assert.assertEquals(100, hits);
    }

    private Warrior createWarrior(long id) {
        return new Warrior(id, "name", "picture", true,null,
                new WarriorCharacteristics(100, 1, 1, 1,
                        AttackType.PHYSICAL, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
    }

}
