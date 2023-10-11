package model;

import java.util.List;
import java.util.ArrayList;


// Represents a RoutineList Object containing n account having an id, owner name and balance (in dollars)
public class RoutineList {
    private final String routineName;
    private int totalCalories;
    private final List<Exercise> exercises;

    //  REQUIRES: accountName has a non-zero length
//  EFFECTS: name of Routine is set to routineName,  total calories is set to 0, and also holds an empty List of
//           Exercise objects
    public RoutineList(String routineName) {
        this.routineName = routineName;
        this.totalCalories = 0;
        this.exercises = new ArrayList<>();
    }

    // EFFECTS: Adds up total calories of each exercise within the list and returns it
    public int getTotalCaloriesBurnt() {
        if (this.exercises.isEmpty()) {
            return this.totalCalories;
        }

        for (Exercise e : exercises) {
            this.totalCalories = this.totalCalories + e.getCalories();
        }
        return this.totalCalories;
    }

    // EFFECTS: Returns exercise list within the Routine
    public List<Exercise> getExerciseList() {
        return exercises;
    }


    // EFFECTS: Finds the exercise with the name.equals(exerciseName)
    //          Returns the exercise object, null if method doesn't find it.
    public Exercise getExerciseFromName(String exerciseName) {
        for (Exercise e : exercises) {
            if (e.getName().equals(exerciseName)) {
                return e;
            }
        }
        return null;
    }

    // EFFECTS: Returns false if an Exercise with the corresponding name same as choseName is not found,
    //            otherwise returns true. Basically searches for duplicates via name within the list, true not found
    public boolean noDuplicate(String chosenName) {
        for (Exercise e : exercises) {
            if (e.getName().equals(chosenName)) {
                return false;
            }
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: Adds an Exercise Object to the exercise list within RoutineList
    public boolean addExercise(Exercise exercise) {
        if (noDuplicate(exercise.getName())) {
            exercises.add(exercise);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: Removes  an Exercise Object to the exercise list within RoutineList
    public void removeExercise(Exercise exercise) {
        exercises.remove(exercise);
    }

    //MODIFIES: this
    // EFFECTS: Removes all Exercise Objects to the exercise list within RoutineList
    public void clearRoutine() {
        exercises.clear();
    }

    // EFFECTS: Obtains name of Routine
    public String getRoutineName() {
        return this.routineName;
    }

    // EFFECTS: Obtains the size exercise list within RoutineList
    public int routineSize() {
        return this.exercises.size();
    }

    // EFFECTS: Checks if the exercise list within RoutineList is empty
    public boolean routineIsEmpty() {
        return this.exercises.isEmpty();
    }


}