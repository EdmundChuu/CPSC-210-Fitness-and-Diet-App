package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

    public class ExerciseTest {
       private Exercise pushup;


        @BeforeEach
        void runBefore(){
        pushup = new Exercise("Pushup", 1, 2, 3);
        }

        @Test
        public void testChangeName( ){
            pushup.changeName("Situp");
            assertEquals("Situp", pushup.getName());
        }

        @Test
        public void testChangeReps( ){
            pushup.changeRepetition(10);
            assertEquals(10, pushup.getReps());
        }

        @Test
        public void testChangeDuration( ){
            pushup.changeDuration(20);
            assertEquals(20, pushup.getDuration());
        }

        @Test
        public void testChangeCalories( ){
            pushup.changeCalories(30);
            assertEquals(30, pushup.getCalories());
        }

}
