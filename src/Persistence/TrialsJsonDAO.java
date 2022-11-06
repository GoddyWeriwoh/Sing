package Persistence;

import Business.*;
import com.google.gson.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class TrialsJsonDAO {
    private final String path = "save/Trials.json";
    private Path pathToFile;

    public TrialsJsonDAO(){
        pathToFile = Paths.get(path);
    }

    /**
     * loads trials' information from Json file to manager.
     * @return ArrayList<Trial>
     */
    public ArrayList<Trial> loadTrialsInformation(){
        ArrayList<Trial> trials = new ArrayList<>();
        JsonParser parser = new JsonParser();
        try{
            JsonElement jsonElement = parser.parse(new FileReader(path));
            JsonArray jsonArray = new JsonArray();
            if(!jsonElement.isJsonNull()){
                jsonArray = jsonElement.getAsJsonArray();
            }
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonTrialElement = jsonArray.get(i).getAsJsonObject();
                    trials.add(createTrial(jsonTrialElement));
                }
                return trials;

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method to create a trial from a set of information
     * @param jsonObject JsonObject
     * @return Trial
     */
    private Trial createTrial(JsonObject jsonObject) {
        String type = jsonObject.get("Type").getAsString();
        String trialName = jsonObject.get("Name").getAsString();
        Trial trial;

        if(type.equals("Paper publication")){
            String journalName = jsonObject.get("Journal Name").getAsString();
            String journalQuartile = jsonObject.get("Journal Quartile").getAsString();
            float acceptanceProbability = jsonObject.get("Acceptance probability").getAsFloat();
            float revisionProbability = jsonObject.get("Revision probability").getAsFloat();
            float rejectionProbability = jsonObject.get("Rejection probability").getAsFloat();
            trial = new TrialPublication(type, trialName, journalName, journalQuartile, acceptanceProbability, revisionProbability, rejectionProbability);
            return trial;
        }

        if(type.equals("Master studies")){
            String masterName = jsonObject.get("Master Name").getAsString();
            int masterECT = jsonObject.get("ECT").getAsInt();;
            float creditPassProbability = jsonObject.get("Credit Pass probability").getAsFloat();

            trial = new TrialMaster(type, trialName,masterName, masterECT, creditPassProbability);
            return trial;
        }
        if(type.equals("Doctoral thesis defense")){
            String studyField = jsonObject.get("Study field").getAsString();
            int difficulty = jsonObject.get("Thesis difficulty").getAsInt();

            trial= new TrialDoctor(type, trialName,studyField, difficulty);
            return trial;
        }
        if(type.equals("Budget request")){
            String entityName = jsonObject.get("Entity Name").getAsString();
            float budget = jsonObject.get("Budget").getAsFloat();

            trial = new TrialBudgetRequest(type, trialName, entityName, budget);
            return trial;
        }

        return null;
    }

    /**
     * Saves trial paper publication to CSV file
     * @param trial Trial
     * @throws IOException e
     */
    public void saveTrialToFile(TrialPublication trial) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject trialObject = new JsonObject();
        JsonParser parser = new JsonParser();

        trialObject.addProperty("Type", trial.getType());
        trialObject.addProperty("Name", trial.getTrialName());
        trialObject.addProperty("Journal Name", trial.getJournalName());
        trialObject.addProperty("Journal Quartile", trial.getJournalQuartile());
        trialObject.addProperty("Acceptance probability", String.valueOf((int)trial.getAcceptanceProbability()));
        trialObject.addProperty("Revision probability", String.valueOf((int)trial.getAcceptanceProbability()));
        trialObject.addProperty("Rejection probability", String.valueOf((int)trial.getAcceptanceProbability()));


        JsonElement jsonObject = parser.parse(new FileReader(path));
        JsonArray result = new JsonArray();
        if(!jsonObject.isJsonNull()){
            result = jsonObject.getAsJsonArray();
        }

        result.add(trialObject);

        FileWriter fileWriter = new FileWriter(path, false);
        fileWriter.write(gson.toJson(result));
        fileWriter.flush();
        fileWriter.close();

    }

    /**
     * Saves Trial Master to CSV file
     * @param trial Trial
     * @throws IOException e
     */
    public void saveTrialToFile(TrialMaster trial) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject trialObject = new JsonObject();
        JsonParser parser = new JsonParser();

        trialObject.addProperty("Type", trial.getType());
        trialObject.addProperty("Name", trial.getTrialName());
        trialObject.addProperty("Master Name", trial.getMasterName());
        trialObject.addProperty("ECT", String.valueOf(trial.getMasterECT()));
        trialObject.addProperty("Credit Pass probability", String.valueOf((int)trial.getCreditPassProbability()));

        JsonElement jsonObject = parser.parse(new FileReader(path));
        JsonArray result = new JsonArray();
        if(!jsonObject.isJsonNull()){
            result = jsonObject.getAsJsonArray();
        }

        result.add(trialObject);

        FileWriter fileWriter = new FileWriter(path, false);
        fileWriter.write(gson.toJson(result));
        fileWriter.flush();
        fileWriter.close();

    }

    /**
     * Saves Trial Doctor to CSV file
     * @param trial Trial
     * @throws IOException e
     */
    public void saveTrialToFile(TrialDoctor trial) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject trialObject = new JsonObject();
        JsonParser parser = new JsonParser();

        trialObject.addProperty("Type", trial.getType());
        trialObject.addProperty("Name", trial.getTrialName());
        trialObject.addProperty("Study field", trial.getFieldOfStudy());
        trialObject.addProperty("Thesis difficulty", String.valueOf(trial.getThesisDifficulty()));

        JsonElement jsonObject = parser.parse(new FileReader(path));
        JsonArray result = new JsonArray();
        if(!jsonObject.isJsonNull()){
            result = jsonObject.getAsJsonArray();
        }
        result.add(trialObject);

        FileWriter fileWriter = new FileWriter(path, false);
        fileWriter.write(gson.toJson(result));
        fileWriter.flush();
        fileWriter.close();
        
    }

    /**
     * Saves Trial Budget Request to CSV file
     * @param trial Trial
     * @throws IOException e
     */
    public void saveTrialToFile(TrialBudgetRequest trial) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject trialObject = new JsonObject();
        JsonParser parser = new JsonParser();

        trialObject.addProperty("Type", trial.getType());
        trialObject.addProperty("Name", trial.getTrialName());
        trialObject.addProperty("Entity Name", trial.getEntityName());
        trialObject.addProperty("Budget", String.valueOf(trial.getBudgetAmount()));

        JsonElement jsonObject = parser.parse(new FileReader(path));
        JsonArray result = new JsonArray();
        if(!jsonObject.isJsonNull()){
            result = jsonObject.getAsJsonArray();
        }

        result.add(trialObject);

        FileWriter fileWriter = new FileWriter(path, false);
        fileWriter.write(gson.toJson(result));
        fileWriter.flush();
        fileWriter.close();

    }

    /**
     * delete trial from CSV file.
     * @param trialName String
     * @throws IOException e
     */
    public void deleteTrialFromFile(String trialName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser parser = new JsonParser();
        JsonElement jsonObject = parser.parse(new FileReader(path));
        JsonArray result = jsonObject.getAsJsonArray();

        for (int i = 0; i < result.size(); i++) {
            String s = result.get(i).getAsJsonObject().get("Name").getAsString();
            if(trialName.equals(s)){
                result.remove(i);
            }
        }

        FileWriter fileWriter = new FileWriter(path, false);
        fileWriter.write(gson.toJson(result));
        fileWriter.flush();
        fileWriter.close();
    }
}
