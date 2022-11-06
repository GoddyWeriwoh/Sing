package Business;

import java.util.ArrayList;
import java.util.List;

public class Edition {
    private String year;
    private int numberOfPlayers;
    private int numberOfTrials;
    private ArrayList<Trial> editionTrialList;
    private static final int INITIAL_PI = 5;
    private ArrayList<Player> players = new ArrayList<>();
    private int currentTrialNumber;
    private boolean trialSuccess = true;


    /**
     * Business.Edition class constructor
     * @param year String
     * @param numberOfPlayers int
     * @param numberOfTrials int
     * @param editionTrialList ArrayList<Business.Trial>
     * @author Weriwoh Mbang Goddy
     */
    public Edition(String year, int numberOfPlayers, int numberOfTrials, ArrayList<Trial> editionTrialList){
        this.year = year;
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfTrials = numberOfTrials;
        this.editionTrialList = editionTrialList;
    }

    /**
     * Getter for edition year
     * @return String
     */
    public String getYear() {
        return year;
    }

    /**
     * Getter for number of players
     * @return int
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Getter for number of trials in edition
     * @return int
     */
    public int getNumberOfTrials() {
        return numberOfTrials;
    }

    /**
     * Getter of list of trials of the edition
     * @return ArrayList<Business.Trial>
     */
    public ArrayList<Trial> getEditionTrialList() {
        return new ArrayList<Trial>(editionTrialList);
    }

    /**
     * Initialises the current trial array position number to zero
     */
    public void initialiseCurrentTrial() {
        currentTrialNumber = 0;
    }

    /**
     * Loads the current edition information. That is the players' list and data from file.
     * @param csvCurrentEditionInfo ArrayList<String>
     */
    public void loadCurrentEditionInfo(ArrayList<String> csvCurrentEditionInfo) {
        currentTrialNumber = Integer.parseInt(csvCurrentEditionInfo.get(0));
        if(csvCurrentEditionInfo.size() > 2){
            for (int i = 1; i < csvCurrentEditionInfo.size(); i++) {
                String[] attributes = csvCurrentEditionInfo.get(i).split(",");
                Player player = null;
                if(attributes[2].equals("Engineer")){
                    player = new PlayerEngineer(attributes[0], Integer.parseInt(attributes[1]));
                }
                if(attributes[2].equals("Master")){
                    player = new PlayerMaster(attributes[0], Integer.parseInt(attributes[1]));
                }
                if(attributes[2].equals("Doctor")){
                    player = new PlayerDoctor(attributes[0], Integer.parseInt(attributes[1]));
                }
                players.add(player);
            }
        }else{
            players.clear();
        }
    }

    /**
     * Adds the player to the list of edition player
     * @param name String
     */
    public void addPlayer(String name) {
        players.add(new PlayerEngineer(name, INITIAL_PI));
    }

    /**
     * Gets the current trial array position.
     * @return int
     */
    public int getCurrentTrialNumber() {
        return currentTrialNumber;
    }

    /**
     * Gets the current trial name from the list
     * @return String
     */
    public String getCurrentTrialName() {
        return editionTrialList.get(currentTrialNumber).getTrialName();
    }

    /**
     * Gets the requested player's name at the array position provided.
     * @param j int
     * @return String
     */
    public String getPlayerName(int j) {
        return players.get(j).getName();
    }

    /**
     * Calculates the player at position j's PI in the trial at position i in array of trials.
     * @param i int
     * @param j int
     * @return String
     */
    public String calculatePlayerPI(int i, int j) {
        TrialPublication trialPublication =  (TrialPublication) editionTrialList.get(i);
        return trialPublication.calculatePlayerPI(players.get(j));
    }

    /**
     * Gets the player at position j's PI.
     * @param j int
     * @return int
     */
    public int getPlayerPI(int j) {
        return players.get(j).getPi();
    }

    /**
     * Removes the player at position j.
     * @param j int
     */
    public void removePlayer(int j) {
        players.remove(j);
    }

    /**
     * Sets the trial success status with the boolean obtained.
     * @param b boolean
     */
    public void setTrialSuccess(boolean b) {
         trialSuccess = b;
    }

    /**
     * Increments the trial array position.
     */
    public void incrementCurrentTrialNum() {
        currentTrialNumber = currentTrialNumber + 1;
        int  c = 0;
    }

    /**
     * Gets the trial success status.
     * @return boolean
     */
    public boolean trialSuccessful() {
        return trialSuccess;
    }

    /**
     * Gets all the edition information
     * @return List<String>
     */
    public List<String> getEditionInfo() {
        List<String> lines = new ArrayList<>();

        StringBuilder sb = new StringBuilder();

        sb.append(currentTrialNumber);
        sb.append('\n');

        for (int i = 0; i < players.size(); i++) {
            sb.append(players.get(i).getName());
            sb.append(',');
            sb.append(players.get(i).getPi());
            sb.append(',');
            sb.append(players.get(i).getPlayerType());
            if(i < players.size() - 1){
                sb.append('\n');
            }
        }
        lines.add(sb.toString());

        return lines;
    }

    /**
     * Calculates the players ECT in case of master trial, through this one.
     * @param j int
     * @param i int
     * @return boolean
     */
    public boolean calculatePlayerECTS(int j, int i) {
        TrialMaster trialMaster = (TrialMaster) editionTrialList.get(i);
        players.get(j).setECT(trialMaster.getRemainingECT());
        return trialMaster.successfulPI(players.get(j));
    }

    /**
     * Gets the player's ECT.
     * @param j int
     * @return int
     */
    public int getPlayerECT(int j) {
        return players.get(j).getECT();
    }

    /**
     * Gets the master's ECT from the trial master at position i.
     * @param i int
     * @return int
     */
    public int getMasterECT(int i) {
        TrialMaster trialMaster = (TrialMaster) editionTrialList.get(i);
        return trialMaster.getMasterECT();
    }

    /**
     * Upgrades the player from engineer to master
     * @param i int
     */
    public void evolutionToMaster(int i) {
        PlayerMaster playerMaster = new PlayerMaster(players.get(i).getName(), INITIAL_PI);
        players.set(i, playerMaster);
    }

    /**
     * Checks if player passed and was an engineer in case of trial master.
     * @param i int
     * @param j1 int
     * @return boolean
     */
    public boolean engineerNPass(int i, int j1) {
        TrialMaster trialMaster = (TrialMaster) editionTrialList.get(i);
        return trialMaster.engineerNPass(players.get(j1));
    }

    /**
     * Calculates if player passes the doctor trial or not, based on algorithm.(Player, position j of array, Trial position i of its array).
     * @param j int
     * @param i int
     * @return boolean
     */
    public boolean calculatePlayerPass(int j, int i) {
        TrialDoctor trialDoctor = (TrialDoctor)editionTrialList.get(i);
        return trialDoctor.calculatePlayerPass(players.get(j));
    }

    /**
     * Checks if player passed and was a master in case of trial doctor.
     * @param i int
     * @param j1 int
     * @return boolean
     */
    public boolean masterNPass(int i, int j1) {
        TrialDoctor trialDoctor = (TrialDoctor) editionTrialList.get(i);
        return trialDoctor.masterNPass(players.get(j1));
    }

    /**
     * Gets the sum of players results from budget request trial.
     * @param i int
     * @return boolean
     */
    public boolean getSumOfResults(int i) {
        TrialBudgetRequest trialBudgetRequest = (TrialBudgetRequest) editionTrialList.get(i);
        return trialBudgetRequest.getSumOfResults(players);
    }

    /**
     * Adds the corresponding player PI.
     * @param i int
     */
    public void addPlayersPI(int i) {
        TrialBudgetRequest trialBudgetRequest = (TrialBudgetRequest) editionTrialList.get(i);
        trialBudgetRequest.addPlayersPI(players);
    }

    /**
     * Checks if player passes 10 PI and was a doctor in case of budget request trial.
     * @param j int
     * @param i int
     * @return boolean
     */
    public boolean doctorNPassTen(int j, int i) {
        TrialBudgetRequest trialBudgetRequest = (TrialBudgetRequest) editionTrialList.get(i);
        return trialBudgetRequest.doctorNPassTen(players.get(j));
    }

    /**
     * Subtracts the corresponding player PI.
     * @param j int
     * @param i int
     */
    public void subtractPlayerPI(int j, int i) {
        TrialBudgetRequest trialBudgetRequest = (TrialBudgetRequest) editionTrialList.get(i);
         trialBudgetRequest.subtractPlayerPI(players.get(j));
    }

    /**
     * Gets current number of players not eliminated from the edition.
     * @return int
     */
    public int getCurrentNumberOfPlayers() {
        return players.size();
    }

    /**
     * Checks if player has more than 10 PI.
     * @param i int
     * @return boolean
     */
    public boolean playerHasTenIp(int i) {
        if(getPlayerPI(i) >= 10){
            return true;
        }
        return false;
    }

    /**
     * Upgrades player to doctor
     * @param j1 int
     */
    public void evolutionToDoctor(int j1) {
        PlayerDoctor playerDoctor = new PlayerDoctor(players.get(j1).getName(), INITIAL_PI);
        players.set(j1, playerDoctor);
    }

    /**
     * Gets the requested player
     * @param i int
     * @return Player
     */
    public Player getPlayer(int i) {
        return players.get(i);
    }
}
