package Business;

import Persistence.CsvPersistenceDAO;
import Persistence.JsonPersistenceDAO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class TrialManager {
    private static final String PAPER_PUBLICATION = "Paper publication";
    private static final String MASTER = "Master studies";
    private static final String DOCTOR = "Doctoral thesis defense";
    private static final String BUDGET_REQUEST = "Budget request";


    private final CsvPersistenceDAO csvPersistenceDAO;
    private final JsonPersistenceDAO jsonPersistenceDAO;
    private ArrayList<Trial> trialList;
    private boolean isJsonStorage = false;

    /**
     * Trial manager constructor
     * @param csvPersistenceDAO CsvPersistenceDAO
     * @param jsonPersistenceDAO JsonPersistenceDAO
     * @author Weriwoh Mbang Goddy
     */
    public TrialManager( CsvPersistenceDAO csvPersistenceDAO, JsonPersistenceDAO jsonPersistenceDAO) {
        this.csvPersistenceDAO = csvPersistenceDAO;
        this.jsonPersistenceDAO = jsonPersistenceDAO;
    }

    /**
     * Checks the uniqueness of a trial input name in the database
     * @param trialName String
     * @return boolean
     */
    public boolean checkUniqueness(String trialName) {
            for (int i = 0; i < trialList.size(); i++) {
                if(trialList.get(i).getTrialName().equals(trialName)){
                    return false;
                }
            }
        return true;
    }

    /**
     * Checks if journal name is empty, during input
     * @param journalName String
     * @return boolean
     */
    public boolean checkJournalName(String journalName) {
        if(journalName.equals("")){
            return false;
        }
        return true;
    }

    /**
     * Checks if journal quartile is correct, during input
     * @param journalQuartile String
     * @return boolean
     */
    public boolean checkQuartile(String journalQuartile) {
        if(!journalQuartile.equals("Q1") && !journalQuartile.equals("Q2") && !journalQuartile.equals("Q3") && !journalQuartile.equals("Q4")){
            return false;
        }
        return true;
    }

    /**
     * Checks correctness of entered acceptance probability
     * @param acceptanceProbability float
     * @return boolean
     */
    public boolean checkAcceptanceProbability(float acceptanceProbability) {
        if(acceptanceProbability > 100 || acceptanceProbability < 0){
            return false;
        }
        return true;
    }

    /**
     * Checks correctness of entered revision probability
     * @param revisionProbability float
     * @return boolean
     */
    public boolean checkRevisionProbability(float revisionProbability) {
        if(revisionProbability > 100 || revisionProbability < 0){
            return false;
        }
        return true;
    }

    /**
     * Checks correctness of entered revision probability based on these parameters
     * @param revisionProbability float
     * @param acceptanceProbability float
     * @return boolean
     */
    public boolean checkRevisionProbability(float revisionProbability, float acceptanceProbability) {
        if(revisionProbability > 100 || revisionProbability < 0){
            return false;
        }
        else{
            if((revisionProbability + acceptanceProbability) > 100){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks correctness of entered rejection probability
     * @param revisionProbability float
     * @return boolean
     */
    public boolean checkRejectionProbability(float revisionProbability) {
        if(revisionProbability > 100 || revisionProbability < 0){
            return false;
        }
        return true;
    }

    /**
     * Checks correctness of entered rejection probability based on these parameters
     * @param revisionProbability float
     * @param acceptanceProbability float
     * @param rejectionProbability float
     * @return boolean
     */
    public boolean checkRejectionProbability(float revisionProbability, float acceptanceProbability, float rejectionProbability) {
        if((revisionProbability + acceptanceProbability + rejectionProbability) != 100){
            return false;
        }
        return true;
    }

    /**
     *This method creates and saves a new paper publication trial created
     * @param trialName String
     * @param journalName String
     * @param journalQuartile String
     * @param acceptanceProbability float
     * @param revisionProbability float
     * @param rejectionProbability float
     * @throws IOException e
     */
    public void createAndSaveTrial(String trialName, String journalName, String journalQuartile, float acceptanceProbability, float revisionProbability, float rejectionProbability) throws IOException {
        Trial trial = new TrialPublication(PAPER_PUBLICATION, trialName, journalName, journalQuartile, acceptanceProbability, revisionProbability, rejectionProbability);
        trialList.add(trial);
        if(!isJsonStorage){
            csvPersistenceDAO.saveTrialToFile((TrialPublication) trial);
        }else{
            jsonPersistenceDAO.saveTrialToFile((TrialPublication) trial);
        }

    }

    /**
     *This method creates and saves a new Master Studies trial created
     * @param trialName String
     * @param masterName String
     * @param masterECT int
     * @param creditPassProbability float
     * @throws IOException e
     */
    public void createAndSaveTrial(String trialName, String masterName, int masterECT, float creditPassProbability) throws IOException {
        Trial masterTrial = new TrialMaster(MASTER, trialName, masterName, masterECT, creditPassProbability);
        trialList.add(masterTrial);
        if(!isJsonStorage){
            csvPersistenceDAO.saveTrialToFile((TrialMaster) masterTrial);
        }else{
            jsonPersistenceDAO.saveTrialToFile((TrialMaster) masterTrial);
        }    }

    /**
     *This method creates and saves a doctoral thesis trial created
     * @param trialName String
     * @param studyField String
     * @param difficulty int
     * @throws IOException e
     */
    public void createAndSaveTrial(String trialName, String studyField, int difficulty) throws IOException {
        Trial doctorTrial = new TrialDoctor(DOCTOR, trialName, studyField, difficulty);
        trialList.add(doctorTrial);
        if(!isJsonStorage){
            csvPersistenceDAO.saveTrialToFile((TrialDoctor) doctorTrial);
        }else{
            jsonPersistenceDAO.saveTrialToFile((TrialDoctor) doctorTrial);
        }
    }

    /**
     *This method creates and saves a budget request trial created
     * @param trialName String
     * @param studyField String
     * @param difficulty int
     * @throws IOException e
     */
    public void createAndSaveTrial(String trialName, String studyField, float difficulty) throws IOException {
        Trial budgetRequestTrial = new TrialBudgetRequest(BUDGET_REQUEST, trialName, studyField, difficulty);
        trialList.add(budgetRequestTrial);
        if(!isJsonStorage){
            csvPersistenceDAO.saveTrialToFile((TrialBudgetRequest) budgetRequestTrial);
        }else{
            jsonPersistenceDAO.saveTrialToFile((TrialBudgetRequest) budgetRequestTrial);
        }
    }

    /**
     * Checks validity of trial input during creation to check its not empty
     * @param trialName String
     * @return boolean
     */
    public boolean checkTrialInput(String trialName) {
        if(trialName.equals("")){
            return false;
        }
        return true;
    }

    /**
     * Returns the list of trials
     * @return ArrayList<Trial>
     */
    public ArrayList<Trial> getTrialList() {
        return new ArrayList<>(trialList);
    }

    /**
     * Gets the trial name of the desired trial from trial list and from database.
     * @param choice int
     * @return String
     */
    public String getTrialName(int choice) {
        return trialList.get(choice).getTrialName();
    }

    /**
     * Deletes the desired trial from trial list and from database.
     * @param choice int
     * @throws IOException e
     */
    public void deleteTrial(int choice) throws IOException {
        if(isJsonStorage){
            jsonPersistenceDAO.deleteTrialFromFile(trialList.get(choice).getTrialName());
        }
        else{
            csvPersistenceDAO.deleteTrialFromFile(trialList.get(choice).getTrialName());
        }
        trialList.remove(choice);
    }

    /**
     * Gets the desired trial from trial list
     * @param c int
     * @return Trial
     */
    public Trial getTrial(int c) {
        return trialList.get(c);
    }

    /**
     * checks correct master ECT entered
     * @param masterECT int
     * @return boolean
     */
    public boolean correctMasterECT(int masterECT) {
        if(masterECT < 60 || masterECT > 120){
            return false;
        }
        return true;
    }

    /**
     * checks correct credit pass probability entered
     * @param creditPassProbability float
     * @return boolean
     */
    public boolean correctCreditPassProbability(float creditPassProbability) {
        if(creditPassProbability < 0 || creditPassProbability > 100){
            return false;
        }
        return true;
    }

    /**
     *Checks if the desired trial is a paper publication Trial
     * @param choice int
     * @return boolean
     */
    public boolean isPublication(int choice) {
        if(trialList.get(choice).getType().equals(PAPER_PUBLICATION)){
            return true;
        }
        return false;
    }

    /**
     *Checks if the desired trial is a Master studies Trial
     * @param choice int
     * @return boolean
     */
    public boolean isMaster(int choice) {
        if(trialList.get(choice).getType().equals(MASTER)){
            return true;
        }
        return false;
    }

    /**
     *Checks if the desired trial is a Doctoral thesis Trial
     * @param choice int
     * @return boolean
     */
    public boolean isDoctor(int choice) {
        if(trialList.get(choice).getType().equals(DOCTOR)){
            return true;
        }
        return false;
    }

    /**
     *Checks if the desired trial is a budget request trial
     * @param choice int
     * @return boolean
     */
    public boolean isBudgetRequest(int choice) {
        if(trialList.get(choice).getType().equals(BUDGET_REQUEST)){
            return true;
        }
        return false;
    }

    /**
     * Checks validity of thesis difficulty during doctoral thesis trial creation.
     * @param difficulty int
     * @return boolean
     */
    public boolean correctThesisDifficulty(int difficulty) {
        if(difficulty < 1 || difficulty > 10){
            return false;
        }
        return true;
    }

    /**
     * Checks correct budget entered during budget request trial creation.
     * @param budget float
     * @return boolean
     */
    public boolean correctBudget(float budget) {
        if(budget < 1000 || budget > 2000000000){
            return false;
        }
        return true;
    }

    /**
     * Sets the boolean to determine if we will be storing trial information to CSV or to Json files.
     * @param b boolean
     */
    public void isJsonStorage(boolean b) {
        isJsonStorage = b;
    }

    /**
     * loads all the trials information to the trial list
     */
    public void loadAllInfo() {
        if(isJsonStorage){
            this.trialList = jsonPersistenceDAO.readTrialsFile();
        }
        else{
            this.trialList = csvPersistenceDAO.readTrialsFile();
        }
    }
}
