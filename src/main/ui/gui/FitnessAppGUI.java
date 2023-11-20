package ui.gui;

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

    public FitnessAppGUI() throws FileNotFoundException {
        super("Fitness App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        newRoutine = new RoutineList("single routine");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

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
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(7, 1));

        addButton("ADD EXERCISE ROUTINE", e -> doNewExercise(), menuPanel);

        addButton("SELECT EXERCISE", e -> doChooseExercise(), menuPanel);

        addButton("CLEAR EXERCISE ROUTINE", e -> doClearExerciseList(), menuPanel);

        addButton("GET TOTAL CALORIES", e -> doGetTotalCalories(), menuPanel);

        addButton("LOAD ROUTINE LIST FROM FILE", e -> loadRoutineList(), menuPanel);

        addButton("SAVE ROUTINE LIST TO FILE", e -> saveRoutineList(), menuPanel);

        addButton("LEAVE THE APP", e -> System.exit(0), menuPanel);

        return menuPanel;
    }

    private JPanel createExerciseListPanel() {
        JPanel exerciseListPanel = new JPanel();
        exerciseListPanel.setLayout(new BorderLayout());

        // Create the table model
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Exercise Name"); // Add a column for Exercise Name

        // Create the JTable using the model
        exerciseListTable = new JTable(model);

        // Add the JTable to a scroll pane
        JScrollPane scrollPane = new JScrollPane(exerciseListTable);
        exerciseListPanel.add(scrollPane, BorderLayout.CENTER);

        return exerciseListPanel;
    }

    private void addButton(String buttonText, ActionListener actionListener, JPanel panel) {
        JButton button = new JButton(buttonText);
        button.addActionListener(actionListener);
        panel.add(button);
    }

    private void doNewExercise() {
        String newName = "";

        while (!newRoutine.noDuplicate(newName) || newName.equals("")) {
            if (newName.equals("")) {
                newName = JOptionPane.showInputDialog("Amazing! Enter a new name for your new Exercise Routine!");
            } else if (!newRoutine.noDuplicate(newName)) {
                newName = JOptionPane.showInputDialog("Exercise Name Already Exists! Pick a new One!");
            }
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

    private void doClearExerciseList() {
        newRoutine.clearRoutine();
        JOptionPane.showMessageDialog(null, "Your list is now Empty! Add something, quickly!");
        updateExerciseListPanel();
    }

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


    private void saveRoutineList() {
        try {
            // Assuming jsonWriter is an instance of your JSON writer class
            jsonWriter.open();
            jsonWriter.write(newRoutine);
            jsonWriter.close();

            // After saving, update the exercise list panel
            updateExerciseListPanel();

            JOptionPane.showMessageDialog(null, "Saved " + newRoutine.getRoutineName()
                    + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE);
        }
    }

    private void loadRoutineList() {
        try {
            // Assuming jsonReader is an instance of your JSON reader class
            newRoutine = jsonReader.read();

            // After loading, update the exercise list panel
            updateExerciseListPanel();

            JOptionPane.showMessageDialog(null, "Loaded " + newRoutine.getRoutineName()
                    + " from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE);
        }
    }

    private void updateExerciseListPanel() {
        DefaultTableModel model = (DefaultTableModel) exerciseListTable.getModel();
        model.setRowCount(0);

        List<Exercise> exerciseList = newRoutine.getExerciseList();

        for (Exercise exercise : exerciseList) {
            model.addRow(new Object[]{exercise.getName()});
        }
    }

    /////////////////////////////////////////ENTERING EXERCISE LEVEL /////////////////////////////////////////////////
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

    private void doRemoveExercise(Exercise esource) {
        newRoutine.removeExercise(esource);
        JOptionPane.showMessageDialog(this, esource.getName() + " has been removed!",
                "Exercise Removed", JOptionPane.INFORMATION_MESSAGE);
        updateExerciseListPanel();
    }

    private void doEditExercise(Exercise esource) {
        Object[] options = {"Edit Name", "Edit Repetitions", "Edit Duration", "Edit Calories", "Leave"};

        while (true) {
            int choice = showOptionDialog(options);

            if (choice == -1 || choice == 4) {
                break;
            }

            switch (choice) {
                case 0: editName(esource);
                break;
                case 1: editRepetitions(esource);
                break;
                case 2: editDuration(esource);
                break;
                case 3: editCalories(esource);
                break;
            }
        }
    }

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

    private void editName(Exercise esource) {
        String newName = showInputDialog("Enter a new name:", esource.getName());
        if (newName != null && !newName.equals("")) {
            esource.changeName(newName);
            showMessageDialog("Name has been updated to: " + newName);
        }
    }

    private void editRepetitions(Exercise esource) {
        int newReps = getIntInputs("Enter new repetitions:", esource.getReps());
        esource.changeRepetition(newReps);
        showMessageDialog("Repetitions has been updated to: " + newReps);
    }

    private void editDuration(Exercise esource) {
        double newDuration = getDoubleInputs(esource.getDuration());
        esource.changeDuration(newDuration);
        showMessageDialog("Duration has been updated to: " + newDuration);
    }

    private void editCalories(Exercise esource) {
        int newCalories = getIntInputs("Enter new calories:", esource.getCalories());
        esource.changeCalories(newCalories);
        showMessageDialog("Calories has been updated to: " + newCalories);
    }

    private String showInputDialog(String prompt, String defaultValue) {
        return JOptionPane.showInputDialog(null, prompt, defaultValue);
    }

    private int getIntInputs(String prompt, int defaultValue) {
        return Integer.parseInt(showInputDialog(prompt, String.valueOf(defaultValue)));
    }

    private double getDoubleInputs(double defaultValue) {
        return Double.parseDouble(showInputDialog("Enter new duration:", String.valueOf(defaultValue)));
    }

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





