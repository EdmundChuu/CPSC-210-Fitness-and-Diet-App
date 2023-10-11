package model;

// Represents an Exercise having a name, reps, duration(seconds) and estimated calorie intake(kcal)
public class Exercise {
    private String name;              // Exercise Name id
    private int repetitions;          // Repetitions of exercise/Volume
    private double duration;          // Duration in seconds
    private int calories;             // Calories

    /*
     * REQUIRES: Exercise has a non-zero length, repetitions is positive integer, duration is positive double, calories
     *           is positive integer
     * EFFECTS: name of Exercise is set to name; repetitions, duration and calories should all be positive and assigned
     *           to repetitions, duration adn calories respectively. Can be repeatedly called to add multiple exercises.
     */
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
    }

    // MODIFIES: this
    // EFFECTS: changes the name variable
    public void changeRepetition(int newRepetition) {
        this.repetitions = newRepetition;
    }

    // MODIFIES: this
    // EFFECTS: changes the Calories variable
    public void changeCalories(int newCalories) {
        this.calories = newCalories;
    }

    // MODIFIES: this
    // EFFECTS: changes the Duration variable
    public void changeDuration(double newDuration) {
        this.duration = newDuration;
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


}


