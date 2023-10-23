package persistence;

import model.Exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkExercise(String name, int repetitions, double duration, int calories, Exercise exercise) {
        assertEquals(name, exercise.getName());
        assertEquals(repetitions, exercise.getReps());
        assertEquals(duration, exercise.getDuration());
        assertEquals(calories, exercise.getCalories());
    }
}
