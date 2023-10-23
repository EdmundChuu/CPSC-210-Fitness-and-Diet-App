package persistence;

import model.Exercise;
import model.RoutineList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads routineList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public RoutineList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRoutineList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses routineList from JSON object and returns it
    private RoutineList parseRoutineList(JSONObject jsonObject) {
        String name = jsonObject.getString("routineName");
        RoutineList rl = new RoutineList(name);
        addExercises(rl, jsonObject);
        return rl;
    }

    // MODIFIES: rl
    // EFFECTS: parses exercises from JSON object and adds them to RoutineList
    private void addExercises(RoutineList rl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("exercises");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addExercise(rl, nextThingy);
        }
    }

    // MODIFIES: rl
    // EFFECTS: parses exercise from JSON object and adds it to RoutineList
    private void addExercise(RoutineList rl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int repetitions = jsonObject.getInt("repetitions");
        double duration = jsonObject.getDouble("duration");
        int calories = jsonObject.getInt("calories");
        Exercise exercise = new Exercise(name, repetitions, duration, calories);
        rl.addExercise(exercise);
    }
}
