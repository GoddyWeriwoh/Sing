package Business;

import Persistence.CsvPersistenceDAO;
import Persistence.JsonPersistenceDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CurrentEditionManager {
    private  JsonPersistenceDAO jsonPersistenceDAO;
    private  CsvPersistenceDAO csvPersistenceDAO;
    private Edition currentEdition;
    private boolean ongoing;
    private boolean isJsonStorage = false;


    /**
     * Constructor for current edition manager.
     * @param csvPersistenceDAO CsvPersistenceDAO
     * @author Weriwoh Mbang Goddy
     */
    public CurrentEditionManager(CsvPersistenceDAO csvPersistenceDAO, JsonPersistenceDAO jsonPersistenceDAO) {
        this.csvPersistenceDAO = csvPersistenceDAO;
        this.jsonPersistenceDAO = jsonPersistenceDAO;
    }

    /**
     * Checks if the current year edition exists
     * @return boolean
     */
    public boolean correctCurrentYearEdition() {
        boolean correct = false;
        ArrayList<Edition> editionList = new ArrayList<>();
        if(isJsonStorage){
          editionList = jsonPersistenceDAO.readEditionsFile();
        }
        else{
            editionList = csvPersistenceDAO.readEditionsFile();
        }

        int year = Calendar. getInstance(). get(Calendar. YEAR);

        for (int i = 0; i < editionList.size(); i++) {
            if(editionList.get(i).getYear().equals(String.valueOf(year))){
                correct = true;
            }
        }
        return correct;
    }

    /**
     * Gets the current year edition information from database through persistence classes.
     */
    public void extractCurrentEdition() {
        ArrayList<Edition> editionList = new ArrayList<>();
        if(isJsonStorage){
            editionList = jsonPersistenceDAO.readEditionsFile();
        }
        else{
           editionList = csvPersistenceDAO.readEditionsFile();
        }

        int year = Calendar. getInstance(). get(Calendar. YEAR);
        int current = 0;

        for (int i = 0; i < editionList.size(); i++) {
            if(editionList.get(i).getYear().equals(String.valueOf(year))){
                current = i;
            }
        }
        currentEdition = editionList.get(current);
    }

    /**
     * Checks if the edition is ongoing.
     * @return boolean
     */
    public boolean ongoingEdition() {
        if(!isJsonStorage){
            List<String> lines = csvPersistenceDAO.getCurrentEditionStatus();
            if(lines.size() == 0){
                ongoing = false;
                return false;
            }
            ongoing = true;
            return true;
        }
            List<String> lines = jsonPersistenceDAO.getCurrentEditionStatus();
            if(lines.size() == 0){
                ongoing = false;
                return false;
            }
            ongoing = true;
            return true;
    }

    /**
     * Gets the current edition.
     * @return Edition
     */
    public Edition getCurrentEdition() {
        return currentEdition;
    }

    /**
     * Adds player to the edition
     * @param name String
     */
    public void addPlayer(String name) {
        currentEdition.addPlayer(name);
    }

    /**
     * Sets the current edition information from database
     * @throws IOException e
     */
    public void setCurrentEdition() throws IOException {
        if(!ongoing){
            currentEdition.initialiseCurrentTrial();
        }
        else{
            if(isJsonStorage){
                currentEdition.loadCurrentEditionInfo(jsonPersistenceDAO.getCurrentEditionInfo());
            }
            else{
                currentEdition.loadCurrentEditionInfo(csvPersistenceDAO.getCurrentEditionInfo());
            }
        }
    }

    /**
     * Gets the current trial array position number.
     * @return int
     */
    public int getCurrentTrialNumber() {
        return currentEdition.getCurrentTrialNumber();
    }

    /**
     * Gets the ongoing trial name.
     * @return String
     */
    public String getCurrentTrialName() {
        return currentEdition.getCurrentTrialName();
    }

    /**
     * Gets the requested player name.
     * @param j int
     * @return String
     */
    public String getPlayerName(int j) {
        return currentEdition.getPlayerName(j);
    }

    /**
     * Calculates the player PI in case of paper publication and return the status of paper, player: array position j, trial: array position i.
     * @param i int
     * @param j int
     * @return String
     */
    public String calculatePlayerPI(int i, int j) {
        return currentEdition.calculatePlayerPI(i, j);
    }

    /**
     * Gets the player PI
     * @param j int
     * @return int
     */
    public int getPlayerPi(int j) {
        return currentEdition.getPlayerPI(j);
    }

    /**
     * Removes player from edition
     * @param j int
     */
    public void removePlayer(int j) {
        currentEdition.removePlayer(j);
    }

    /**
     * Gets the number of trials from the edition.
     * @return int
     */
    public int getNumberOfTrials() {
        return currentEdition.getNumberOfTrials();
    }

    /**
     * Sets the required trial success status.
     * @param b boolean
     */
    public void setTrialSuccess(boolean b) {
        currentEdition.setTrialSuccess(b);
    }

    /**
     * Increments the trial array position.
     */
    public void incrementCurrentTrialNum() {
        currentEdition.incrementCurrentTrialNum();
    }

    /**
     * Gets the trial success status from edition.
     * @return boolean
     */
    public boolean trialSuccessful() {
        return currentEdition.trialSuccessful();
    }

    /**
     * sends edition information to persistence class for storage.
     * @throws IOException e
     */
    public void saveEditionInfo() throws IOException {
        if(isJsonStorage){
            jsonPersistenceDAO.saveEditionInfo(currentEdition.getEditionInfo());
        }
        else{
            csvPersistenceDAO.saveEditionInfo(currentEdition.getEditionInfo());
        }
    }

    /**
     * Deletes the current edition information from database through persistence classes.
     * @throws IOException e
     */
    public void deleteCurrentEditionInfo() throws IOException {
        if(isJsonStorage){
            jsonPersistenceDAO.deleteCurrentEditionInfo();
        }
        else{
            csvPersistenceDAO.deleteCurrentEditionInfo();
        }
    }

    /**
     * Checks edition, if we storing into Json or Csv file.
     * @param b boolean
     */
    public void isJsonStorage(boolean b) {
        isJsonStorage = b;
    }

    /**
     * Gets the requested trial
     * @param i int
     * @return Trial
     */
    public Trial getTrial(int i) {
        return currentEdition.getEditionTrialList().get(i);
    }

    /**
     * Asks the edition to calculate the number of ECT passed, in case of master trial and returns status if passed or not.
     * @param j int
     * @param i int
     * @return boolean
     */
    public boolean calculatePlayerECTSPass(int j, int i) {
        return currentEdition.calculatePlayerECTS(j, i);
    }

    /**
     * Gets the player's ECT.
     * @param j int
     * @return int
     */
    public int getPlayerECT(int j) {
        return currentEdition.getPlayerECT(j);
    }

    /**
     * Gets the player's PI from edition.
     * @param j int
     * @return int
     */
    public int getPlayerPI(int j) {
        return currentEdition.getPlayerPI(j);
    }

    /**
     * Gets the master's ECT from edition.
     * @param i int
     * @return int
     */
    public int getMasterECT(int i) {
        return currentEdition.getMasterECT(i);
    }

    /**
     * Checks if player passed and was an engineer in case of trial master.
     * @param i int
     * @param j1 int
     * @return boolean
     */
    public boolean engineerNPass(int i, int j1) {
        if(currentEdition.engineerNPass(i, j1)){
            currentEdition.evolutionToMaster(j1);
            return true;
        }
        return false;
    }

    /**
     * Calculates if player passes the doctor trial or not, based on algorithm.(Player, position j of array, Trial position i of its array), through edition.
     * @param j int
     * @param i int
     * @return boolean
     */
    public boolean calculatePlayerPass(int j, int i) {
        return currentEdition.calculatePlayerPass(j, i);
    }

    /**
     * Checks if player passed and was a master in case of trial doctor, through the edition.
     * @param i int
     * @param j1 int
     * @return boolean
     */
    public boolean masterNPass(int i, int j1) {
        if(currentEdition.masterNPass(i, j1)){
            currentEdition.evolutionToDoctor(j1);
            return true;
        }
        return false;
    }

    /**
     * Checks success status of budget request trial, through edition.
     * @param i int
     * @return boolean
     */
    public boolean budgetSuccess(int i) {
        return currentEdition.getSumOfResults(i);
    }

    /**
     * Adds the layer's PI through edition.
     * @param i int
     */
    public void addPlayersPI(int i) {
        currentEdition.addPlayersPI(i);
    }
    /**
     * Checks if player passes 10 PI, through edition and was a doctor in case of budget request trial.
     * @param j int
     * @param i int
     * @return boolean
     */
    public boolean doctorNPassTen(int j, int i) {
        return currentEdition.doctorNPassTen(j, i);
    }

    /**
     * Subtracts the corresponding player PI.
     * @param j int
     * @param i int
     */
    public void subtractPlayerPI(int j, int i) {
        currentEdition.subtractPlayerPI(j, i);
    }

    /**
     * Gets current number of players not eliminated from the edition.
     * @return int
     */
    public int getCurrentNumberOfPlayers() {
        return currentEdition.getCurrentNumberOfPlayers();
    }

    /**
     * Checks if player has more than 10 PI.
     * @param i int
     * @return boolean
     */
    public boolean playerHasTenIp(int i) {
        return currentEdition.playerHasTenIp(i);
    }

    /**
     * Gets the requested player
     * @param i int
     * @return Player
     */
    public Player getPlayer(int i) {
        return currentEdition.getPlayer(i);
    }

    /**
     * Upgrades player to master, through edition
     * @param i int
     */
    public void evolveToMaster(int i) {
        currentEdition.evolutionToMaster(i);
    }

    /**
     * Upgrades player to doctor, through edition
     * @param i int
     */
     public void evolveToDoctor(int i) {
         currentEdition.evolutionToDoctor(i);
     }
}
