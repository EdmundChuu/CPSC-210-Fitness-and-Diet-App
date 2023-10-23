package persistence;

import model.Exercise;
import model.RoutineList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            RoutineList rl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyRoutineList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRoutineList.json");
        try {
            RoutineList rl = reader.read();
            assertEquals("r1", rl.getRoutineName());
            assertEquals(0, rl.routineSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralRoutineList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralRoutineList.json");
        try {
            RoutineList rl = reader.read();
            assertEquals("r1", rl.getRoutineName());
            List<Exercise> exercises = rl.getExerciseList();
            assertEquals(2, exercises.size());
            checkExercise("push-up",2, 4, 6,  exercises.get(0));
            checkExercise("sit-up", 1,3, 5, exercises.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}