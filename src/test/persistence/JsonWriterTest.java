package persistence;

import model.Exercise;
import model.RoutineList;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            RoutineList rl = new RoutineList("r1");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            RoutineList rl = new RoutineList("r1");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRoutineList.json");
            writer.open();
            writer.write(rl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRoutineList.json");
            rl = reader.read();
            assertEquals("r1", rl.getRoutineName());
            assertEquals(0, rl.routineSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            RoutineList rl = new RoutineList("r1");
            rl.addExercise(new Exercise("push-up", 2, 4, 6));
            rl.addExercise(new Exercise("sit-up", 1, 3, 5));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRoutineList.json");
            writer.open();
            writer.write(rl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralRoutineList.json");
            rl = reader.read();
            assertEquals("r1", rl.getRoutineName());
            List<Exercise> exercises = rl.getExerciseList();
            assertEquals(2, exercises.size());
            checkExercise("push-up", 2,4, 6, exercises.get(0));
            checkExercise("sit-up", 1, 3, 5, exercises.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
