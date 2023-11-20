package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;
import java.util.ArrayList;


// EFFECTS: Represents a RoutineList Object a routineName, and an empty list of exercises
public class RoutineList implements Writable {
    private final String routineName;
    private final List<Exercise> exercises;

    //  EFFECTS: name of Routine is set to routineName,  total calories is set to 0, and also holds an empty List of
    //           Exercise objects
    public RoutineList(String routineName) {
        this.routineName = routineName;
        this.exercises = new ArrayList<>();
    }

    public String getRoutineName() {
        return routineName;
    }

    // EFFECTS: Adds up total calories of each exercise within the list and returns it
    public int getTotalCaloriesBurnt() {
        if (this.exercises.isEmpty()) {
            return 0;
        }
        int tempTotal = 0;
        for (Exercise e : exercises) {
            tempTotal = tempTotal + e.getCalories();
        }
        return tempTotal;
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

    // EFFECTS: Checks if the name for new exercise is unique within the list, returns false if yes
    public boolean noDuplicate(String chosenName) {
        for (Exercise e : exercises) {
            if (e.getName().equals(chosenName)) {
                return false;
            }
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: Adds an Exercise Object to the exercise list within RoutineList and returns true624 if unique
    public boolean addExercise(Exercise exercise) {
        if (noDuplicate(exercise.getName())) {
            exercises.add(exercise);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: Removes  an Exercise Object from the exercise list within RoutineList
    public void removeExercise(Exercise exercise) {
        exercises.remove(exercise);
    }

    //MODIFIES: this
    // EFFECTS: Removes all Exercise Objects to the exercise list within RoutineList
    public void clearRoutine() {
        exercises.clear();
    }

    // EFFECTS: Obtains the size exercise list within RoutineList
    public int routineSize() {
        return this.exercises.size();
    }

    // EFFECTS: Checks if the exercise list within RoutineList is empty
    public boolean routineIsEmpty() {
        return this.exercises.isEmpty();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("routineName", routineName);
        json.put("exercises", exercisesToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise t : exercises) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}