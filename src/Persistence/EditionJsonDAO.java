package Persistence;

import Business.*;
import com.google.gson.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class EditionJsonDAO {
    private final String pathCurrentEdition = "save/CurrentEdition.json";
    private final String path = "save/Editions.json";
    private static final String PAPER_PUBLICATION = "Paper publication";
    private static final String MASTER = "Master studies";
    private static final String DOCTOR = "Doctoral thesis defense";
    private static final String BUDGET_REQUEST = "Budget request";


    /**
     * Method to get all information about editions from Json files and create a List.
     * @return ArrayList<Edition>
     * @param trials ArrayList<Edition>
     */
    public ArrayList<Edition> loadEditionsInformation(ArrayList<Trial> trials) {
        ArrayList<Edition> editions = new ArrayList<>();
        JsonParser parser = new JsonParser();
        try{
            JsonElement jsonElement = parser.parse(new FileReader(path));
            JsonArray jsonArray = new JsonArray();
            if(!jsonElement.isJsonNull()) {
                jsonArray = jsonElement.getAsJsonArray();
            }
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonEditionElement = jsonArray.get(i).getAsJsonObject();
                    editions.add(createEdition(jsonEditionElement));
                }

            return editions;
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method to create an edition element
     * @param editionObject JsonObject
     * @return Edition
     */
    private Edition createEdition(JsonObject editionObject) {
        ArrayList<Trial> trials = new ArrayList<>();

        String name = editionObject.get("Year").getAsString();
        int numOfPlayers = editionObject.get("Number Of Players").getAsInt();
        int numberOfTrials = editionObject.get("Number Of Trials").getAsInt();
        JsonArray trialList = editionObject.get("Trial List").getAsJsonArray();

        for (int i = 0; i < numberOfTrials; i++) {

            trials.add(getTrial(trialList.get(i).getAsJsonObject()));

        }
        return new Edition(name, numOfPlayers, numberOfTrials, trials);
    }

    /**
     * Method to create a trial from a set of information
     * @param jsonObject JsonObject
     * @return Trial
     */
    private Trial getTrial(JsonObject jsonObject) {
        String type = jsonObject.get("Type").getAsString();
        String trialName = jsonObject.get("Name").getAsString();
        Trial trial;

        if(type.equals(PAPER_PUBLICATION)){
            String journalName = jsonObject.get("Journal Name").getAsString();
            String journalQuartile = jsonObject.get("Journal Quartile").getAsString();
            float acceptanceProbability = jsonObject.get("Acceptance probability").getAsFloat();
            float revisionProbability = jsonObject.get("Revision probability").getAsFloat();
            float rejectionProbability = jsonObject.get("Rejection probability").getAsFloat();
            trial = new TrialPublication(type, trialName, journalName, journalQuartile, acceptanceProbability, revisionProbability, rejectionProbability);
            return trial;
        }

        if(type.equals(MASTER)){
            String masterName = jsonObject.get("Master Name").getAsString();
            int masterECT = jsonObject.get("ECT").getAsInt();;
            float creditPassProbability = jsonObject.get("Credit Pass probability").getAsFloat();

            trial = new TrialMaster(type, trialName,masterName, masterECT, creditPassProbability);
            return trial;
        }
        if(type.equals(DOCTOR)){
            String studyField = jsonObject.get("Study field").getAsString();
            int difficulty = jsonObject.get("Thesis difficulty").getAsInt();

            trial= new TrialDoctor(type, trialName,studyField, difficulty);
            return trial;
        }
        if(type.equals(BUDGET_REQUEST)){
            String entityName = jsonObject.get("Entity Name").getAsString();
            float budget = jsonObject.get("Budget").getAsFloat();

            trial = new TrialBudgetRequest(type, trialName, entityName, budget);
            return trial;
        }
        return null;
    }

    /**
     * Saves edition to Json file.
     * @param edition Edition
     * @throws IOException e
     */
    public void saveEditionToFile(Edition edition) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject editionObject = new JsonObject();
        JsonParser parser = new JsonParser();

        editionObject.addProperty("Year", edition.getYear());
        editionObject.addProperty("Number Of Players",String.valueOf(edition.getNumberOfPlayers()));
        editionObject.addProperty("Number Of Trials",String.valueOf(edition.getNumberOfTrials()));

        JsonArray jsonArray = new JsonArray();

        for (int i = 0; i < edition.getNumberOfTrials(); i++) {
            Trial t = edition.getEditionTrialList().get(i);
            if(t.getType().equals(PAPER_PUBLICATION)){
                jsonArray.add(createTrialObject((TrialPublication)t));
            }
            if(t.getType().equals(MASTER)){
                jsonArray.add(createTrialObject((TrialMaster)t));
            }
            if(t.getType().equals(DOCTOR)){
                jsonArray.add(createTrialObject((TrialDoctor)t));
            }
            if(t.getType().equals(BUDGET_REQUEST)){
                jsonArray.add(createTrialObject((TrialBudgetRequest)t));
            }

        }
        editionObject.add("Trial List", jsonArray);

        JsonElement jsonObject = parser.parse(new FileReader(path));
        JsonArray result = new JsonArray();
        if(!jsonObject.isJsonNull()){
            result = jsonObject.getAsJsonArray();
        }

        result.add(editionObject);

        FileWriter fileWriter = new FileWriter(path, false);
        fileWriter.write(gson.toJson(result));
        fileWriter.flush();
        fileWriter.close();

    }

    private JsonObject createTrialObject(TrialPublication trial) {
        JsonObject trialObject = new JsonObject();
        trialObject.addProperty("Type", trial.getType());
        trialObject.addProperty("Name", trial.getTrialName());
        trialObject.addProperty("Journal Name", trial.getJournalName());
        trialObject.addProperty("Journal Quartile", trial.getJournalQuartile());
        trialObject.addProperty("Acceptance probability", String.valueOf((int)trial.getAcceptanceProbability()));
        trialObject.addProperty("Revision probability", String.valueOf((int)trial.getAcceptanceProbability()));
        trialObject.addProperty("Rejection probability", String.valueOf((int)trial.getAcceptanceProbability()));

           return trialObject;
    }
    private JsonObject createTrialObject(TrialMaster trialMaster){
        JsonObject trialObject = new JsonObject();
        trialObject.addProperty("Type", trialMaster.getType());
        trialObject.addProperty("Name", trialMaster.getTrialName());
        trialObject.addProperty("Master Name", trialMaster.getMasterName());
        trialObject.addProperty("ECT", String.valueOf(trialMaster.getMasterECT()));
        trialObject.addProperty("Credit Pass probability", String.valueOf((int)trialMaster.getCreditPassProbability()));

        return trialObject;
    }

    private JsonObject createTrialObject(TrialDoctor trialDoctor){
        JsonObject trialObject = new JsonObject();
        trialObject.addProperty("Type", trialDoctor.getType());
        trialObject.addProperty("Name", trialDoctor.getTrialName());
        trialObject.addProperty("Study field", trialDoctor.getFieldOfStudy());
        trialObject.addProperty("Thesis difficulty", String.valueOf(trialDoctor.getThesisDifficulty()));

        return trialObject;

    }

    private JsonObject createTrialObject(TrialBudgetRequest trialBudgetRequest){
        JsonObject trialObject = new JsonObject();
        trialObject.addProperty("Type", trialBudgetRequest.getType());
        trialObject.addProperty("Name", trialBudgetRequest.getTrialName());
        trialObject.addProperty("Entity Name", trialBudgetRequest.getEntityName());
        trialObject.addProperty("Budget", String.valueOf(trialBudgetRequest.getBudgetAmount()));

        return trialObject;
    }

    /**
     * deletes edition from Json file.
     * @param year String
     * @throws IOException e
     */
    public void deleteEditionFromJson(String year) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Gson gson1 = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement jsonObject = parser.parse(new FileReader(path));
        JsonArray result = jsonObject.getAsJsonArray();

        int year1 = Calendar. getInstance(). get(Calendar. YEAR);


        for (int i = 0; i < result.size(); i++) {
            String s = result.get(i).getAsJsonObject().get("Year").getAsString();
            if(year.equals(s)){
                result.remove(i);

                if(Integer.valueOf(year) == year1){
                    try{
                        FileWriter fileWriter = new FileWriter("save/currentEdition.json", false);
                        fileWriter.write(gson1.toJson(new JsonObject()));
                        fileWriter.flush();
                        fileWriter.close();

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        }

        FileWriter fileWriter = new FileWriter(path, false);
        fileWriter.write(gson.toJson(result));
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * Gets the current edition information from CSV.
     * @return ArrayList<String>
     * @throws IOException e
     */
    public ArrayList<String> getCurrentEditionInfo() throws FileNotFoundException {
        JsonParser parser = new JsonParser();
        JsonElement jsonObject = parser.parse(new FileReader(pathCurrentEdition));
        JsonObject editionObject = new JsonObject();
        if(!jsonObject.isJsonNull()){
            editionObject = jsonObject.getAsJsonObject();
        }

        String trialNumber = editionObject.get("Trial Number").getAsString();
        JsonArray playersArray = editionObject.get("Players").getAsJsonArray();
        ArrayList<String> currentEditionStringInfo = new ArrayList<>();

        currentEditionStringInfo.add(trialNumber);
        for (int i = 0; i < playersArray.size(); i++) {
            currentEditionStringInfo.add(getPlayerString(playersArray.get(i).getAsJsonObject()));
        }
        return currentEditionStringInfo;
    }

    private String getPlayerString(JsonObject asJsonObject) {
        StringBuilder sb = new StringBuilder();

        String name = asJsonObject.get("Name").getAsString();
        sb.append(name);
        sb.append(',');

        String ip = asJsonObject.get("PI").getAsString();
        sb.append(ip);
        sb.append(',');

        String type = asJsonObject.get("Type").getAsString();
        sb.append(type);

        return sb.toString();
    }

    /**
     * Gets if current edition is ongoing or not.
     * @return List<String>
     */
    public List<String> getCurrentEditionStatus() {
        List<String> list = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonElement jsonObject = new JsonObject();
        try{
             jsonObject = parser.parse(new FileReader(pathCurrentEdition));
             if(jsonObject.isJsonNull()){
                 return new ArrayList<>();
             }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        list.add("checked");
        return list;
    }

    /**
     * Saves edition information to CSV.
     * @param currentEditionInfo1 List<String>
     * @throws IOException e
     */
    public void saveEditionInfo(List<String> currentEditionInfo1) throws IOException {
        ArrayList<String> currentEditionInfo = getArrayOfInfo(currentEditionInfo1);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject currentEditionObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        for (int i = 1; i < currentEditionInfo.size(); i++) {
            jsonArray.add(createPlayerObject((currentEditionInfo.get(i))));
            int c = 0;
        }

        currentEditionObject.addProperty( "Trial Number", currentEditionInfo.get(0));
        currentEditionObject.add("Players", jsonArray);

        FileWriter fileWriter = new FileWriter(pathCurrentEdition, false);
        fileWriter.write(gson.toJson(currentEditionObject));
        fileWriter.flush();
        fileWriter.close();
    }

    private ArrayList<String> getArrayOfInfo(List<String> currentEditionInfo1) {
        ArrayList<String> string = new ArrayList<>();
        String[] s = currentEditionInfo1.get(0).split("\n");
        string.addAll(Arrays.asList(s));
        return string;
    }

    private JsonObject createPlayerObject(String s) {
        String[] array = s.split(",");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Name", array[0]);
        jsonObject.addProperty("PI", array[1]);
        jsonObject.addProperty("Type", array[2]);

        return jsonObject;
    }

    /**
     * Deletes current year edition from CVS file.
     * @throws IOException e
     */
    public void deleteCurrentEditionInfo() throws IOException {
        Gson gson1 = new Gson();
        FileWriter fileWriter = new FileWriter("save/currentEdition.json", false);
        fileWriter.write(gson1.toJson(new JsonObject()));
        fileWriter.flush();
        fileWriter.close();
    }
}
