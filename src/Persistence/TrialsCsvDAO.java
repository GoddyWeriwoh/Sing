package Persistence;

import Business.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class TrialsCsvDAO {
    private final String path = "save/Trials.csv";
    private Path pathToFile;

    public TrialsCsvDAO(){
         pathToFile = Paths.get(path);
    }

    /**
     * loads trials' information from CSV file to manager.
     * @return ArrayList<Trial>
     */
    public ArrayList<Trial> loadTrialsInformation(){
        ArrayList<Trial> trialList = new ArrayList<>();
        try(BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)){
            String line = br.readLine();

            while(line != null){
                String[] attributes = line.split(",");
                Trial trial = createTrial(attributes);
                trialList.add(trial);
                line = br.readLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return trialList;
    }

    /**
     * Method to create a trial from a set of information
     * @param attributes String[]
     * @return Trial
     */
    private Trial createTrial(String[] attributes) {
        String type = attributes[0];
        String trialName = attributes[1];
        Trial trial;

        if(attributes[0].equals("Paper publication")){
            String journalName = attributes[2];
            String journalQuartile = attributes[3];
            float acceptanceProbability = Float.parseFloat(attributes[4]);
            float revisionProbability = Float.parseFloat(attributes[5]);
            float rejectionProbability = Float.parseFloat(attributes[6]);
            trial = new TrialPublication(type, trialName, journalName, journalQuartile, acceptanceProbability, revisionProbability, rejectionProbability);
            return trial;
        }

        if(attributes[0].equals("Master studies")){
            String masterName = attributes[2];
            int masterECT = Integer.parseInt(attributes[3]);
            float creditPassProbability = Float.parseFloat(attributes[4]);

            trial = new TrialMaster(type, trialName,masterName, masterECT, creditPassProbability);
            return trial;
        }
        if(attributes[0].equals("Doctoral thesis defense")){
            String studyField = attributes[2];
            int difficulty = Integer.parseInt(attributes[3]);

             trial= new TrialDoctor(type, trialName,studyField, difficulty);
            return trial;
        }
        if(attributes[0].equals("Budget request")){
            String entityName = attributes[2];
            float budget = Float.parseFloat(attributes[3]);

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
        List<String> lines = readTrialFileAsSequencesOfLines();

        StringBuilder sb = new StringBuilder();

        sb.append(trial.getType());
        sb.append(',');
        sb.append(trial.getTrialName());
        sb.append(',');
        sb.append(trial.getJournalName());
        sb.append(',');
        sb.append(trial.getJournalQuartile());
        sb.append(',');
        sb.append(String.format("%.0f", trial.getAcceptanceProbability()));
        sb.append(',');
        sb.append(String.format("%.0f", trial.getRevisionProbability()));
        sb.append(',');
        sb.append(String.format("%.0f", trial.getRejectionProbability()));

        lines.add(sb.toString());

        Path path = getTrialFilePath();
        Files.write(path, lines, StandardOpenOption.WRITE);

    }

    /**
     * Saves Trial Master to CSV file
     * @param trial Trial
     * @throws IOException e
     */
    public void saveTrialToFile(TrialMaster trial) throws IOException {
        List<String> lines = readTrialFileAsSequencesOfLines();

        StringBuilder sb = new StringBuilder();

        sb.append(trial.getType());
        sb.append(',');
        sb.append(trial.getTrialName());
        sb.append(',');
        sb.append(trial.getMasterName());
        sb.append(',');
        sb.append(trial.getMasterECT());
        sb.append(',');
        sb.append(String.format("%.0f", trial.getCreditPassProbability()));

        lines.add(sb.toString());

        Path path = getTrialFilePath();
        Files.write(path, lines, StandardOpenOption.WRITE);

    }

    /**
     * Saves Trial Doctor to CSV file
     * @param trial Trial
     * @throws IOException e
     */
    public void saveTrialToFile(TrialDoctor trial) throws IOException {
        List<String> lines = readTrialFileAsSequencesOfLines();

        StringBuilder sb = new StringBuilder();

        sb.append(trial.getType());
        sb.append(',');
        sb.append(trial.getTrialName());
        sb.append(',');
        sb.append(trial.getFieldOfStudy());
        sb.append(',');
        sb.append(trial.getThesisDifficulty());

        lines.add(sb.toString());

        Path path = getTrialFilePath();
        Files.write(path, lines, StandardOpenOption.WRITE);

    }

    /**
     * Saves Trial Budget Request to CSV file
     * @param trial Trial
     * @throws IOException e
     */
    public void saveTrialToFile(TrialBudgetRequest trial) throws IOException {
        List<String> lines = readTrialFileAsSequencesOfLines();

        StringBuilder sb = new StringBuilder();

        sb.append(trial.getType());
        sb.append(',');
        sb.append(trial.getTrialName());
        sb.append(',');
        sb.append(trial.getEntityName());
        sb.append(',');
        sb.append(trial.getBudgetAmount());

        lines.add(sb.toString());

        Path path = getTrialFilePath();
        Files.write(path, lines, StandardOpenOption.WRITE);


    }
    private List<String> readTrialFileAsSequencesOfLines() throws IOException {
        Path path = getTrialFilePath();
        List<String> lines = Files.readAllLines(path);
        return lines;
    }

    private Path getTrialFilePath() {
        Path path = Paths
                .get("save/Trials.csv");
        return path.normalize();
    }

    /**
     * delete trial from CSV file.
     * @param trialName String
     * @throws IOException e
     */
    public void deleteTrialFromFile(String trialName) throws IOException {
        List<String> lines = readTrialFileAsSequencesOfLines();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] s = line.split(",");
            if(s[1].equals(trialName)){
                lines.remove(i);
            }
        }

        Path path = getTrialFilePath();
        Files.write(path, lines, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
