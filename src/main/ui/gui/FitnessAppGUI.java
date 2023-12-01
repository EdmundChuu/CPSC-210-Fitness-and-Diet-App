package ui.gui;

import model.EventLog;
import model.Event;
import model.Exercise;
import model.RoutineList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.swing.table.DefaultTableModel;


public class FitnessAppGUI extends JFrame {
    private static final String JSON_STORE = "./data/routineList.json";
    private RoutineList newRoutine;
    private JTable exerciseListTable; // Added JTable for exercise list
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private boolean changesMade;

//MODIFIES:
//this (object of the FitnessAppGUI class)
// newRoutine
// jsonWriter
// jsonReader
// changesMade

    //EFFECTS:
//Initializes the FitnessAppGUI frame, creates a new RoutineList object,
// sets up the main panel with tabs, and initializes JSON reader and writer.
    public FitnessAppGUI() throws FileNotFoundException {
        super("Fitness App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 2040);

        newRoutine = new RoutineList("single routine");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Hello, my name is Astraea! How may I help you today?");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);  // Center the text
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel menuPanel = createMenuPanel();
        JPanel exerciseListPanel = createExerciseListPanel();

        tabbedPane.addTab("Menu", menuPanel);
        tabbedPane.addTab("Exercise List", exerciseListPanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        changesMade = false;
    }


    //EFFECTS:
    //Creates a JPanel for the menu tab, adds buttons with hover effect.
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(7, 1));

        addButtonWithHover("ADD EXERCISE ROUTINE", e -> doNewExercise(), menuPanel);

        addButtonWithHover("SELECT EXERCISE", e -> doChooseExercise(), menuPanel);

        addButtonWithHover("CLEAR EXERCISE ROUTINE", e -> doClearExerciseList(), menuPanel);

        addButtonWithHover("GET TOTAL CALORIES", e -> doGetTotalCalories(), menuPanel);

        addButtonWithHover("LOAD ROUTINE LIST FROM FILE", e -> loadRoutineList(), menuPanel);

        addButtonWithHover("SAVE ROUTINE LIST TO FILE", e -> saveRoutineList(), menuPanel);

        addButtonWithHover("LEAVE THE APP", e -> doLeave(), menuPanel);

        return menuPanel;
    }
    //MODIFIES:
    //exerciseListTable
    //EFFECTS:
    //Creates a JPanel for the exercise list tab, initializes a JTable with a DefaultTableModel.

    private JPanel createExerciseListPanel() {
        JPanel exerciseListPanel = new JPanel();
        exerciseListPanel.setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Exercise Name"); // Add a column for Exercise Name

        exerciseListTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(exerciseListTable);
        exerciseListPanel.add(scrollPane, BorderLayout.CENTER);

        return exerciseListPanel;
    }
    //MODIFIES:
    //panel
    //EFFECTS:
    //Creates a CustomButton with hover effect and adds it to the specified panel.

    private void addButtonWithHover(String buttonText, ActionListener actionListener, JPanel panel) {
        CustomButton button = new CustomButton(buttonText);
        button.addActionListener(actionListener);
        panel.add(button);
    }

    //EFFECTS:
    //Shows a confirmation dialog with options and returns the user's choice.

    private int showOptionsDialogLeave() {
        String[] options = {"SAVE ROUTINE LIST TO FILE", "NO :("};
        return JOptionPane.showOptionDialog(this, "Save your amazing worklist?",
                "Confirmation", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    //EFFECTS:
    //Checks if changes are made, prompts the user to save, and exits the application.

    private void doLeave() {
        if (changesMade) {
            int saveChoice = showOptionsDialogLeave();
            if (saveChoice == 0) {
                saveRoutineList();
            }

        }
        printLoggedEvents();

        JOptionPane.showMessageDialog(null, "Goodbye!", "Exiting", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    // EFFECTS:
    // Prints all logged events to the console.
    private void printLoggedEvents() {
        System.out.println("Logged Events:");
        for (Event event : EventLog.getInstance()) {
            System.out.println(event);
        }
    }

    //MODIFIES:
    //newRoutine
    //changesMade
    //exerciseListTable

    //EFFECTS:
    //Prompts the user to enter details for a new exercise and adds it to the routine.

    private void doNewExercise() {
        String newName = "";

        while (!newRoutine.noDuplicate(newName) || newName.equals("")) {
            if (newName.equals("")) {
                newName = JOptionPane.showInputDialog("Amazing! Enter a new name for your new Exercise Routine!");
            } else if (!newRoutine.noDuplicate(newName)) {
                newName = JOptionPane.showInputDialog("Exercise Name Already Exists! Pick a new One!");
            }
            changesMade = true;
        }

        int newReps = getIntInput("Amazing! Enter your reps for your new Exercise Routine!");
        double newDuration = getDoubleInput();
        int newCalorie = getIntInput("Amazing! Enter your estimated calories burnt for your new Exercise Routine!");

        Exercise exercise = new Exercise(newName, newReps, newDuration, newCalorie);
        boolean added = newRoutine.addExercise(exercise);

        if (added) {
            JOptionPane.showMessageDialog(null, "New exercise Routine " + newName
                    + " has been added to your Library!");
            updateExerciseListPanel();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Exercise with the same name already exists. Please choose a different name.");
        }
    }

    // EFFECTS: is used in doNewExercise to check if the user input is an integer
    private int getIntInput(String prompt) {
        int inputValue;
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(prompt);
                if (input == null) {
                    return 0; // Cancel button pressed
                }
                inputValue = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Input invalid. Please enter a valid integer.");
            }
        }
        return inputValue;
    }

    // EFFECTS: is used in doNewExercise to check if the user input is a double
    private double getDoubleInput() {
        double inputValue;
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(
                        "Amazing! Enter your duration for your new Exercise Routine!");
                if (input == null) {
                    return 0.0; // Cancel button pressed
                }
                inputValue = Double.parseDouble(input);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Input invalid. Please enter a valid number.");
            }
        }
        return inputValue;
    }

    //MODIFIES:
    //newRoutine, changesMade, exerciseListTable

    //EFFECTS:
    //Clears the exercise list and updates the GUI.

    private void doClearExerciseList() {
        newRoutine.clearRoutine();
        JOptionPane.showMessageDialog(null, "Your list is now Empty! Add something, quickly!");
        updateExerciseListPanel();
        changesMade = true;
    }

    //EFFECTS:
    //Calculates and displays the total calories burnt for the routine.

    private void doGetTotalCalories() {
        int calorieNumber = newRoutine.getTotalCaloriesBurnt();

        if (calorieNumber > 0) {
            JOptionPane.showMessageDialog(null, "You'll burn " + calorieNumber
                    + " calories if you finish this routine. Don't you want to try it?");
        } else {
            JOptionPane.showMessageDialog(null,
                    "No exercises in the list. Add exercises to calculate total calories.");
        }
    }

    //MODIFIES:
    //jsonWriter, exerciseListTable

    //EFFECTS:
    //Saves the routine list to a JSON file.

    private void saveRoutineList() {
        try {
            jsonWriter.open();
            jsonWriter.write(newRoutine);
            jsonWriter.close();

            updateExerciseListPanel();

            JOptionPane.showMessageDialog(null, "Saved " + newRoutine.getRoutineName()
                    + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE);
        }
    }

    //MODIFIES:
    //newRoutine, exerciseListTable

    //EFFECTS:
    //Loads the routine list from a JSON file.

    private void loadRoutineList() {
        try {
            newRoutine = jsonReader.read();

            updateExerciseListPanel();

            JOptionPane.showMessageDialog(null, "Loaded " + newRoutine.getRoutineName()
                    + " from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE);
        }
        changesMade = true;
    }

    //MODIFIES:
    //exerciseListTable

    //EFFECTS:
    //Updates the exercise list panel with the current exercise list.

    private void updateExerciseListPanel() {
        DefaultTableModel model = (DefaultTableModel) exerciseListTable.getModel();
        model.setRowCount(0);

        List<Exercise> exerciseList = newRoutine.getExerciseList();

        for (Exercise exercise : exerciseList) {
            model.addRow(new Object[]{exercise.getName()});
        }
    }

    /////////////////////////////////////////ENTERING EXERCISE LEVEL /////////////////////////////////////////////////

    //EFFECTS:
    //Displays a popup menu for choosing an exercise from the list.

    private void doChooseExercise() {
        List<Exercise> exerciseList = newRoutine.getExerciseList();

        if (exerciseList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "IT'S EMPTY! ADD SOMETHING!!!",
                    "Empty Exercise List", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JPopupMenu exerciseMenu = new JPopupMenu("Exercise Selection");

            for (Exercise exercise : exerciseList) {
                JMenuItem exerciseItem = new JMenuItem(exercise.getName());
                exerciseItem.addActionListener(e -> showExerciseDetails(exercise));
                exerciseMenu.add(exerciseItem);
            }

            exerciseMenu.show(this, getWidth() / 2, getHeight() / 2);
        }
    }

    //EFFECTS:
    //Shows details of a selected exercise.

    private void showExerciseDetails(Exercise exercise) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name: " + exercise.getName()));
        panel.add(new JLabel("Repetitions: " + exercise.getReps()));
        panel.add(new JLabel("Duration: " + exercise.getDuration()));
        panel.add(new JLabel("Calories Burnt: " + exercise.getCalories()));

        JButton removeButton = new JButton("Remove Exercise");
        JButton editButton = new JButton("Edit Exercise");

        removeButton.addActionListener(e -> {
            doRemoveExercise(exercise);
            updateExerciseListPanel();
        });

        editButton.addActionListener(e -> {
            doEditExercise(exercise);
            updateExerciseListPanel();
        });

        panel.add(removeButton);
        panel.add(editButton);

        JOptionPane.showMessageDialog(this, panel, "Exercise Details", JOptionPane.INFORMATION_MESSAGE);
    }

    //MODIFIES:
    //newRoutine, exerciseListTable, changesMade

    //EFFECTS:
    //Removes the selected exercise from the routine.

    private void doRemoveExercise(Exercise esource) {
        newRoutine.removeExercise(esource);
        JOptionPane.showMessageDialog(this, esource.getName() + " has been removed!",
                "Exercise Removed", JOptionPane.INFORMATION_MESSAGE);
        updateExerciseListPanel();
        changesMade = true;
    }

    //MODIFIES:
    //esource, changesMade

    //EFFECTS:
    //Edits the details of a selected exercise.

    private void doEditExercise(Exercise esource) {
        Object[] options = {"Edit Name", "Edit Repetitions", "Edit Duration", "Edit Calories", "Leave"};

        while (true) {
            int choice = showOptionDialog(options);

            if (choice == -1 || choice == 4) {
                break;
            }

            switch (choice) {
                case 0:
                    editName(esource);
                    break;
                case 1:
                    editRepetitions(esource);
                    break;
                case 2:
                    editDuration(esource);
                    break;
                case 3:
                    editCalories(esource);
                    break;
            }
        }
        changesMade = true;
    }

    //EFFECTS:
    //Shows a dialog with options and returns the user's choice.

    private int showOptionDialog(Object[] options) {
        return JOptionPane.showOptionDialog(
                null,
                "Choose an option to edit:",
                "Edit Exercise",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );
    }

    //MODIFIES:
    //esource
    //changesMade
    //EFFECTS:
    //Edits the name of a selected exercise.

    private void editName(Exercise esource) {
        String newName = showInputDialog("Enter a new name:", esource.getName());
        if (newName != null && !newName.equals("")) {
            esource.changeName(newName);
            showMessageDialog("Name has been updated to: " + newName);
        }
    }

    //MODIFIES:
    //esource
    //changesMade
    //EFFECTS:
    //Edits the repetitions of a selected exercise.
    private void editRepetitions(Exercise esource) {
        int newReps = getIntInputs("Enter new repetitions:", esource.getReps());
        esource.changeRepetition(newReps);
        showMessageDialog("Repetitions has been updated to: " + newReps);
    }

    //MODIFIES:
    //esource
    //changesMade

    //EFFECTS:
    //Edits the duration of a selected exercise.
    private void editDuration(Exercise esource) {
        double newDuration = getDoubleInputs(esource.getDuration());
        esource.changeDuration(newDuration);
        showMessageDialog("Duration has been updated to: " + newDuration);
    }

    //MODIFIES:
    //esource
    //changesMade
    //EFFECTS:
    //Edits the calories of a selected exercise.

    private void editCalories(Exercise esource) {
        int newCalories = getIntInputs("Enter new calories:", esource.getCalories());
        esource.changeCalories(newCalories);
        showMessageDialog("Calories has been updated to: " + newCalories);
    }


    //EFFECTS:
    //Shows an input dialog with the specified prompt and default value.
    //Returns the user's input as a String.
    private String showInputDialog(String prompt, String defaultValue) {
        return JOptionPane.showInputDialog(null, prompt, defaultValue);
    }

    //EFFECTS:
    //Shows an input dialog with the specified prompt and default value for integer input.
    //Returns the user's input as an integer.
    private int getIntInputs(String prompt, int defaultValue) {
        return Integer.parseInt(showInputDialog(prompt, String.valueOf(defaultValue)));
    }

    //EFFECTS:
    //Shows an input dialog with the specified prompt and default value for double input.
    //Returns the user's input as a double.
    private double getDoubleInputs(double defaultValue) {
        return Double.parseDouble(showInputDialog("Enter new duration:", String.valueOf(defaultValue)));
    }

    //EFFECTS:
    //Shows a message dialog displaying a goodbye message.
    private void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new FitnessAppGUI();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}





