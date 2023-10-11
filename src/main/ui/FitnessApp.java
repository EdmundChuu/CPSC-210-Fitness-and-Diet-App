package ui;

import model.Exercise;
import model.RoutineList;


import java.util.List;
import java.util.Scanner;

//FitnessApp Application
public class FitnessApp {
    private RoutineList newRoutine;
    private Scanner input;

    // EFFECTS: runs the fitness application
    public FitnessApp() {
        runFitnessApp();
    }


    // MODIFIES: this
    // EFFECTS: processes user input
    private void runFitnessApp() {
        boolean keepGoingRoutineLevel = true;
        String commandRoutineLevel;

        init();

        while (keepGoingRoutineLevel) {
            displayMenuRoutineLevel();
            commandRoutineLevel = input.next();

            if (commandRoutineLevel.equals("q")) {
                keepGoingRoutineLevel = false;
            } else if (commandRoutineLevel.equals("2") && newRoutine.routineIsEmpty()) {
                System.out.println("ITS EMPTY!!! ADD SOMETHING!!!");
            } else {
                processCommandRoutineLevel(commandRoutineLevel);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenuRoutineLevel() {
        System.out.println("\nHello, my name is Astraea! How may I help you today?");
        System.out.println("\t1 -> ADD EXERCISE ROUTINE");
        System.out.println("\t2 -> SELECT EXERCISE");
        System.out.println("\t3 -> CLEAR EXERCISE ROUTINE");
        System.out.println("\t4 -> GET TOTAL CALORIES");
        System.out.println("\tq -> LEAVE THE APP");
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        newRoutine = new RoutineList("single routine");
    }

    // MODIFIES: this
    // EFFECTS: conducts a deposit transaction
    private void processCommandRoutineLevel(String command) {
        switch (command) {
            case "1":
                doNewExercise();
                break;
            case "2":
                doChooseExercise();
                break;
            case "3":
                doClearExerciseList();
                break;

            case "4":
                doGetTotalCalories();
                break;

            default:
                System.out.println("Selection not valid...");
                break;
        }
    }



    //MODIFIES: this
    //EFFECTS: Adds new exercise and prevents duplication of exercise names
    private void doNewExercise() {
        @SuppressWarnings("methodlength")
        String newName = "";  // force entry into loop

        while (!newRoutine.noDuplicate(newName) || newName.equals("")) {
            if (newName.equals("")) {
                System.out.println("\nAmazing! Enter a new name for your new Exercise Routine!");
                newName = input.next();
            } else if (!newRoutine.noDuplicate(newName)) {
                System.out.println("\nExercise Name Already Exists! Pick a new One!");
                newName = input.next();
            } else if (newRoutine.noDuplicate(newName)) {
                newName = input.next();
            }
        }
        System.out.println("\nAmazing! Enter your reps for your new Exercise Routine!");
        int newReps;
        while (true) {
            if (input.hasNextInt()) {
                newReps = input.nextInt();
                break; // Exit the loop if an integer is provided
            } else {
                input.next(); // Consume invalid input to prevent an infinite loop
                System.out.println("\nINPUT INVALID TRY AGAIN PLEASE");
            }
        }

        System.out.println("\nAmazing! Enter your duration for your new Exercise Routine!");
        double newDuration;
        while (true) {
            if (input.hasNextDouble()) {
                newDuration = input.nextDouble();
                break; // Exit the loop if an integer is provided
            } else {
                input.next(); // Consume invalid input to prevent an infinite loop
                System.out.println("\nINPUT INVALID TRY AGAIN PLEASE");
            }
        }

        System.out.println("\nAmazing! Enter your estimated calories burnt for your new Exercise Routine!");
        int newCalorie;
        while (true) {
            if (input.hasNextInt()) {
                newCalorie = input.nextInt();
                break; // Exit the loop if an integer is provided
            } else {
                input.next(); // Consume invalid input to prevent an infinite loop
                System.out.println("\nINPUT INVALID TRY AGAIN PLEASE");
            }
        }

        Exercise exercise = new Exercise(newName, newReps, newDuration, newCalorie);
        newRoutine.addExercise(exercise);
        System.out.println("\nNew exercise Routine " + newName + " has been added to your Library!\n");
    }

    private void doChooseExercise() {
        System.out.println("Here is your entire Library! Which Exercise would you Like?");
        doGetExerciseLibrary();
        String chosenName = input.next();
        Exercise esource = doSelectExercise(chosenName);
        System.out.println("\n Here is the stats of "
                + esource.getName()
                + "\n Name: "
                + esource.getName()
                + "\n Repetitions: "
                + esource.getReps()
                + "\n Duration: "
                + esource.getDuration()
                + "\n Calories Burnt: "
                + esource.getCalories()
                + "\n \n What would you like to do with "
                + esource.getName() + "?");
        runExerciseMenu(esource);
    }

    private void doClearExerciseList() {
        newRoutine.clearRoutine();
        System.out.println("Your list is now Empty! Add something to it quickly!");
    }

    private void doGetExerciseLibrary() {
        int index = 0;
        List<Exercise> routine = newRoutine.getExerciseList();
        for (Exercise s : routine) {
            System.out.println(index++ + 1 + ". " + s.getName());
        }

    }

    private void doGetTotalCalories() {
        int calorieNumber = newRoutine.getTotalCaloriesBurnt();
        System.out.println("You'll burn "
                + calorieNumber
                + " calories If you finish this Routine, don't you want to try it? ");
    }

    ///////////////////////////////////////////////ENTERING EXERCISE LEVEL////////////////////////////////////////////
    private Exercise doSelectExercise(String chosen) {
        while (newRoutine.getExerciseFromName(chosen) == null) {
            System.out.println("Apologies, we don't have that in your library! Please try again?");
            chosen = input.next();

        }
        if (newRoutine.getExerciseFromName(chosen) != null) {
            System.out.println("YAY");
        }
        return newRoutine.getExerciseFromName(chosen);
    }
    ///////////////////////////////////////////////ENTERING EXERCISE LEVEL/////////////////////////////////////////////

    private void runExerciseMenu(Exercise esource) {
        boolean keepGoingExerciseLevel = true;
        String commandExerciseLevel;

        while (keepGoingExerciseLevel) {
            displayMenuExerciseLevel();
            commandExerciseLevel = input.next();

            switch (commandExerciseLevel) {
                case "1":
                    doRemoveExercise(esource);
                    keepGoingExerciseLevel = false;
                    break;
                case "2":
                    doEditExercise(esource);
                    break;
                case "q":
                    keepGoingExerciseLevel = false;
                default:
                    System.out.println("Selection not valid...");
                    break;
            }
        }

        System.out.println("\nGoing Back to Exercise Menu");
    }


    private void doRemoveExercise(Exercise esource) {
        newRoutine.removeExercise(esource);
        System.out.println("\n" + esource.getName() + " has been Removed! Here is your new RoutineList:\n");
        doGetExerciseLibrary();
    }

    private void displayMenuExerciseLevel() {
        System.out.println("\t1 -> REMOVE EXERCISE");
        System.out.println("\t2 -> EDIT EXERCISE");
        System.out.println("\tq -> LEAVE THIS MENU");
    }


    private void doEditExercise(Exercise esource) {
        @SuppressWarnings("methodlength")
        boolean keepGoingExerciseEditLevel = true;
        String commandExerciseEditLevel;

        while (keepGoingExerciseEditLevel) {
            displayMenuExerciseEditLevel();
            commandExerciseEditLevel = input.next();

            switch (commandExerciseEditLevel) {
                case "1":
                    System.out.println("\nAmazing! Enter a new name for your new Exercise Routine!");
                    String newName = input.next();
                    esource.changeName(newName);
                    keepGoingExerciseEditLevel = false;
                    break;
                case "2":
                    System.out.println("\nAmazing! Enter your reps for your new Exercise Routine!");
                    int newReps = input.nextInt();
                    esource.changeRepetition(newReps);
                    break;
                case "3":
                    System.out.println("\nAmazing! Enter your duration for your new Exercise Routine!");
                    double newDuration = input.nextDouble();
                    esource.changeDuration(newDuration);
                    break;
                case "4":
                    System.out.println("\nAmazing! Enter your estimated calories burnt for your new Exercise Routine!");
                    int newCalorie = input.nextInt();
                    esource.changeCalories(newCalorie);
                case "q":
                    keepGoingExerciseEditLevel = false;
                default:
                    System.out.println("Selection not valid...");
                    break;
            }
            System.out.println("\nNew exercise Routine " + esource.getName() + " has been edited!\n");
        }
    }

    private void displayMenuExerciseEditLevel() {
        System.out.println("\t1 -> CHANGE NAME");
        System.out.println("\t2 -> CHANGE REPETITION");
        System.out.println("\t3 -> CHANGE DURATION(SECONDS)");
        System.out.println("\t4 -> CHANGE CALORIES");
        System.out.println("\tq -> LEAVE THIS MENU");
    }
}