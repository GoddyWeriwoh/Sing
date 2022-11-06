package Persistence;

import Business.Edition;
import Business.Trial;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditionCsvDAO{
    private final String path = "save/Editions.csv";
    private Path pathToFile;

    public EditionCsvDAO(){
        pathToFile = Paths.get(path);
    }

    /**
     * Method to get all information about editions from CSV files and create a List.
     * @return ArrayList<Edition>
     * @param trials ArrayList<Edition>
     */
    public ArrayList<Edition> loadEditionsInformation(ArrayList<Trial> trials) {
        ArrayList<Edition> editionList = new ArrayList<>();
        Path pathToFile = Paths.get("save/Editions.csv");
        try(BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)){
            String line = br.readLine();

            while(line != null){
                String[] attributes = line.split(",");
                Edition edition = createEdition(attributes, trials);
                editionList.add(edition);
                line = br.readLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return editionList;
    }

    /**
     * Method to create an edition element
     * @param attributes String[]
     * @return Edition
     */
    private Edition createEdition(String[] attributes, ArrayList<Trial> trialList) {
        ArrayList<Trial> trials = new ArrayList<>();

        int l = Integer.valueOf(attributes[2]);

        for (int i = 0; i < l; i++) {

            trials.add(getTrialElement(attributes[3 + i], trialList));

        }
        return new Edition(attributes[0], Integer.valueOf(attributes[1]), Integer.valueOf(attributes[2]), trials);
    }

    /**
     * Method to create a trial from a set of information
     * @param attribute String
     * @param trialList
     * @return Trial
     */
    private Trial getTrialElement(String attribute, ArrayList<Trial> trialList) {
        for (int i = 0; i < trialList.size(); i++) {
            if(trialList.get(i).getTrialName().equals(attribute)){
                return trialList.get(i);
            }
        }
        return null;
    }

    /**
     * Saves edition to file.
     * @param edition Edition
     * @throws IOException e
     */
    public void saveEditionToFile(Edition edition) throws IOException {
        List<String> lines = readEditionFileAsSequencesOfLines();

        StringBuilder sb = new StringBuilder();

        sb.append(edition.getYear());
        sb.append(',');
        sb.append(edition.getNumberOfPlayers());
        sb.append(',');
        sb.append(edition.getNumberOfTrials());


        for (int j = 0; j < edition.getEditionTrialList().size(); j++) {
            sb.append(',');
            sb.append(edition.getEditionTrialList().get(j).getTrialName());

        }

        lines.add(sb.toString());

        Path path = getEditionFilePath();
        Files.write(path, lines, StandardOpenOption.WRITE);

    }
    private static List<String> readEditionFileAsSequencesOfLines() throws IOException {
        Path path = getEditionFilePath();
        List<String> lines = Files.readAllLines(path);
        return lines;
    }
    private static Path getEditionFilePath() {
        Path path = Paths
                .get("save/Editions.csv");
        return path.normalize();
    }

    /**
     * deletes edition from CSV file.
     * @param year String
     * @throws IOException e
     */
    public void deleteEditionFromCSV(String year) throws IOException {
        List<String> lines = readEditionFileAsSequencesOfLines();
        int year1 = Calendar. getInstance(). get(Calendar. YEAR);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] s = line.split(",");
            if(s[0].equals(year)){
                lines.remove(i);

                if(Integer.valueOf(year) == year1){
                    try{
                        List<String> lines1 = readCurrentEditionFileAsSequencesOfLines();
                        lines1.clear();
                        Path path1 = Paths
                                .get("save/currentEdition.csv");

                        Files.write(path1, lines1, StandardOpenOption.TRUNCATE_EXISTING);

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        }

        Path path = getEditionFilePath();
        Files.write(path, lines, StandardOpenOption.TRUNCATE_EXISTING);
    }


    private List<String> readCurrentEditionFileAsSequencesOfLines() throws IOException {
        Path path = Paths
                .get("save/currentEdition.csv");

        List<String> lines = Files.readAllLines(path.normalize());
        return lines;
    }

    /**
     * Gets the current edition information from CSV.
     * @return ArrayList<String>
     * @throws IOException e
     */
    public ArrayList<String> getCurrentEditionInfo() throws IOException {
        ArrayList<String> currentInfo = new ArrayList<>();
        Path pathToFile = Paths.get("save/currentEdition.csv");
        BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII);
            //currentTrialNumber = Integer.parseInt(br.readLine());
            String line = br.readLine();

            while(line != null){
                currentInfo.add(line);
                line = br.readLine();
            }

        return currentInfo;
    }

    /**
     * Gets if current edition is ongoing or not.
     * @return List<String>
     */
    public List<String> getCurrentEditionStatus() {
        Path path = Paths
                .get("save/currentEdition.csv").normalize();

        List<String> lines = new ArrayList<>();
        try{
            lines = Files.readAllLines(path);
        }catch (Exception e){
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * Saves edition information to CSV.
     * @param currentEditionInfo List<String>
     * @throws IOException e
     */
    public void saveEditionInfo(List<String> currentEditionInfo) throws IOException {
        Path path1 = getCurrentEditionFilePath();
        File file = new File("save/currentEdition.csv");
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();
        Files.write(path1, currentEditionInfo, StandardOpenOption.WRITE);
    }

    private Path getCurrentEditionFilePath() {
        Path path = Paths
                .get("save/currentEdition.csv");
        return path.normalize();
    }

    /**
     * Deletes current year edition from CVS file.
     * @throws IOException e
     */
    public void deleteCurrentEditionInfo() throws IOException {

            List<String> lines1 = readCurrentEditionFileAsSequencesOfLines();
            lines1.clear();
            Path path1 = Paths
                    .get("save/currentEdition.csv");

            Files.write(path1, lines1, StandardOpenOption.TRUNCATE_EXISTING);

    }
}
