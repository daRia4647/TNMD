import com.google.gson.JsonArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class JSONUpdater {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: JSONUpdater <testsJsonPath> <valuesJsonPath>");
            return;
        }

        String testsJsonPath = args[0];
        String valuesJsonPath = args[1];

        try {
            String testsJsonContent = readFile(testsJsonPath);
            String valuesJsonContent = readFile(valuesJsonPath);

            JSONObject testsObject = new JSONObject(testsJsonContent);
            JSONObject valuesObject = new JSONObject(valuesJsonContent);

            updateTestValues(testsObject, valuesObject);

            try (FileWriter reportFile = new FileWriter("report.json")) {
                reportFile.write(testsObject.toString());
            } catch (IOException e) {
                System.err.println("Error writing to report.json: " + e.getMessage());
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (org.json.JSONException e) {
            System.err.println("JSON error: " + e.getMessage());
        }
    }

    private static String readFile(String filePath) throws FileNotFoundException {
        StringBuilder content = new StringBuilder();
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine());
            }
        }

        return content.toString();
    }

    private static void updateTestValues(JSONObject testsObject, JSONObject valuesObject) {
        if (testsObject.has("id")) {
            String testId = testsObject.getString("id");
            if (testsObject.has("value")) {
                String testValue = getValueValue(valuesObject, testId);
                testsObject.put("value", testValue);
            }
        }

        if (testsObject.has("values")) {
            JSONArray valuesArray = testsObject.getJSONArray("values");
            for (int i = 0; i < valuesArray.length(); i++) {
                JSONObject valueObject = valuesArray.getJSONObject(i);
                updateTestValues(valueObject, valuesObject);
            }
        }

        if (testsObject.has("tests")) {
            JSONArray testsArray = testsObject.getJSONArray("tests");
            for (int i = 0; i < testsArray.length(); i++) {
                JSONObject testObject = testsArray.getJSONObject(i);
                updateTestValues(testObject, valuesObject);
            }
        }
    }

    private static String getValueValue(JSONObject valuesObject, String valueId) {
        JSONArray valuesArray = valuesObject.getJSONArray("values");

        for (int i = 0; i < valuesArray.length(); i++) {
            JSONObject valueObject = valuesArray.getJSONObject(i);
            String id = valueObject.getString("id");

            if (id.equals(valueId)) {
                return valueObject.getString("value");
            }
        }

        return "";
    }
}