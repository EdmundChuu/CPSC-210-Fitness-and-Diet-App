package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class RoutineListTest {
    private RoutineList testRoutine;
    private Exercise pushUp;
    private Exercise sitUp;

    @BeforeEach
    void runBefore() {
        testRoutine = new RoutineList("Calisthenics");
        pushUp = new Exercise("Push Up", 100, 300, 1500);
        sitUp = new Exercise("Sit Up", 200, 600, 3000);
    }

    @Test
    void testGetTotalCalories() {
        assertEquals(0, testRoutine.getTotalCaloriesBurnt());
        testRoutine.addExercise(pushUp);
        testRoutine.addExercise(sitUp);
        assertEquals(4500, testRoutine.getTotalCaloriesBurnt());
        testRoutine.removeExercise(sitUp);
        assertEquals(1500, testRoutine.getTotalCaloriesBurnt());
    }

    @Test
    void testGetExerciseFromName() {
        testRoutine.addExercise(pushUp);
        testRoutine.addExercise(sitUp);
        String chosenName = "Push Up";
        assertEquals(pushUp, testRoutine.getExerciseFromName(chosenName));
        assertNull(testRoutine.getExerciseFromName("flying"));
    }

    @Test
    void testAddDuplicateName() {
        assertTrue(testRoutine.addExercise(pushUp));
        assertFalse(testRoutine.addExercise(pushUp));
        assertEquals(1, testRoutine.routineSize());
    }


    @Test
    void testRemoveExercise() {
        assertTrue(testRoutine.addExercise(pushUp));
        assertTrue(testRoutine.addExercise(sitUp));
        testRoutine.removeExercise(sitUp);
        assertEquals(1, testRoutine.routineSize());
        assertEquals(pushUp, testRoutine.getExerciseList().get(0));
    }

    @Test
    void testClearRoutine() {
        assertTrue(testRoutine.addExercise(pushUp));
        assertTrue(testRoutine.addExercise(sitUp));
        testRoutine.clearRoutine();
        assertTrue(testRoutine.routineIsEmpty());
    }

    @Test
    void testGetExerciseList() {
        assertTrue(testRoutine.addExercise(pushUp));
        assertTrue(testRoutine.addExercise(sitUp));
        assertEquals(2, testRoutine.getExerciseList().size());
        assertEquals(pushUp, testRoutine.getExerciseList().get(0));
        assertEquals(sitUp, testRoutine.getExerciseList().get(1));
    }
}
