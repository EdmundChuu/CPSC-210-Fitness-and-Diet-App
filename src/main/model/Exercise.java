package model;

import persistence.Writable;
import org.json.JSONObject;


// Represents an Exercise having a name, reps, duration(seconds) and estimated calorie intake(kcal)
public class Exercise implements Writable {
    private String name;              // Exercise Name id
    private int repetitions;          // Repetitions of exercise/Volume
    private double duration;          // Duration in seconds
    private int calories;             // Calories

    // REQUIRES: Exercise has a non-zero length, repetitions is positive integer, duration is positive double, calories
    //           is positive integer
    // EFFECTS: name of Exercise is set to name; repetitions, duration and calories should all be positive and assigned
    //          to repetitions, duration adn calories respectively. Can be repeatedly called to add multiple exercises.
    public Exercise(String name, int repetitions, double duration, int calories) {
        this.name = name;
        this.repetitions = repetitions;
        this.duration = duration;
        this.calories = calories;
    }

    // MODIFIES: this
    // EFFECTS: changes the name variable
    public void changeName(String newName) {
        this.name = newName;

        EventLog.getInstance().logEvent(new Event("Name changed for exercise: " + this.getName()));
    }

    // MODIFIES: this
    // EFFECTS: changes the name variable
    public void changeRepetition(int newRepetition) {
        this.repetitions = newRepetition;

        EventLog.getInstance().logEvent(new Event("Repetitions changed for exercise: " + this.getName()));
    }

    // MODIFIES: this
    // EFFECTS: changes the Calories variable
    public void changeCalories(int newCalories) {
        this.calories = newCalories;

        EventLog.getInstance().logEvent(new Event("Calories changed for exercise: " + this.getName()));
    }

    // MODIFIES: this
    // EFFECTS: changes the Duration variable
    public void changeDuration(double newDuration) {
        this.duration = newDuration;

        EventLog.getInstance().logEvent(new Event("Duration changed for exercise: " + this.getName()));
    }


    public String getName() {
        return name;
    }

    public int getReps() {
        return repetitions;
    }

    public double getDuration() {
        return duration;
    }

    public int getCalories() {
        return calories;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("repetitions", repetitions);
        json.put("duration", duration);
        json.put("calories", calories);
        return json;
    }
}


