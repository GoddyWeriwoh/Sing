package Business;

import Persistence.CsvPersistenceDAO;
import Persistence.JsonPersistenceDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class EditionManager {
    private  CsvPersistenceDAO csvPersistenceDAO;
    private  JsonPersistenceDAO jsonPersistenceDAO;
    private  ArrayList<Edition> editionList;
    private boolean isJsonStorage = false;


    /**
     * Edition manager constructor which will use these parameters to function.
     * @param csvPersistenceDAO CsvPersistenceDAO
     * @param jsonPersistenceDAO JsonPersistenceDAO
     * @author Weriwoh Mbang Goddy
     */
    public EditionManager(CsvPersistenceDAO csvPersistenceDAO, JsonPersistenceDAO jsonPersistenceDAO) {
        this.csvPersistenceDAO = csvPersistenceDAO;
        this.jsonPersistenceDAO = jsonPersistenceDAO;
    }

    /**
     * Makes sure the edition year entered is not before the present year.
     * @param editionYear String
     * @return boolean
     */
    public boolean checkValidEditionYear(String editionYear) {
        int year = Calendar. getInstance(). get(Calendar. YEAR);
        if(Integer.valueOf(editionYear) < year){
            return false;
        }
        return true;
    }

    /**
     * Validates the edition year entered during edition creation, making sure it is unique.
     * @param editionYear String
     * @return boolean
     */
    public boolean checkUniqueEditionYear(String editionYear) {
            for (int i = 0; i < editionList.size(); i++) {
                if(editionList.get(i).getYear().equals(editionYear)){
                    return false;
                }
            }
        return true;
    }

    /**
     * Checks validity of number of players entered during creation.
     * @param numOfPlayers int
     * @return boolean
     */
    public boolean validNumberOfPlayer(int numOfPlayers) {
        if(numOfPlayers < 1 || numOfPlayers > 5){
            return false;
        }
        return true;
    }

    /**
     * Checks validity of number of trials entered during creation.
     * @param numOfTrials int
     * @return boolean
     */
    public boolean validNumberOfTrials(int numOfTrials) {
        if(numOfTrials < 3 || numOfTrials > 12){
          return false;
        }
        return true;
    }

    /**
     * Adds the new edition to the list and sends for storage to database.
     * @param editionYear String
     * @param numOfPlayers int
     * @param numOfTrials int
     * @param trialList int
     * @throws IOException e
     */
    public void addEdition(String editionYear, int numOfPlayers, int numOfTrials, ArrayList<Trial> trialList) throws IOException {
        Edition edition = new Edition(editionYear, numOfPlayers, numOfTrials, trialList);
        editionList.add(edition);
        if(isJsonStorage){
            jsonPersistenceDAO.saveEditionToFile(edition);
        }
        else{
            csvPersistenceDAO.saveEditionToFile(edition);
        }
    }

    /**
     * Returns list of editions.
     * @return ArrayList<Edition>
     */
    public ArrayList<Edition> getEditionList() {
        return new ArrayList<>(editionList);
    }

    /**
     * Gets a desired edition.
     * @param choice nt
     * @return Edition
     */
    public Edition getEdition(int choice) {
        return editionList.get(choice);
    }

    /**
     * Duplicates an edition and sends for storage to database.
     * @param editionYear String
     * @param numbPlayers int
     * @param edition Edition
     */
    public void duplicateEdition(String editionYear, int numbPlayers, Edition edition) {
        Edition edition1 = new Edition(editionYear, numbPlayers, edition.getNumberOfTrials(), edition.getEditionTrialList());
        editionList.add(edition1);
        try {
            if(isJsonStorage){
                jsonPersistenceDAO.saveEditionToFile(edition1);
            }
            else{
                csvPersistenceDAO.saveEditionToFile(edition1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Deletes the desired edition from list and sends to be deleted in database.
     * @param choice int
     * @throws IOException e
     */
    public void deleteEdition(int choice) throws IOException {
        if(isJsonStorage){
            jsonPersistenceDAO.deleteEditionFromFile(editionList.get(choice).getYear());
        }
        else{
            csvPersistenceDAO.deleteEditionFromFile(editionList.get(choice).getYear());
        }
        editionList.remove(choice);
    }

    /**
     * Sets the boolean to determine if we will be storing trial information to CSV or to Json files.
     * @param b boolean
     */
    public void isJsonStorage(boolean b) {
        isJsonStorage = b;
    }

    /**
     * loads all the trials information to the edition list
     */
    public void loadAllInfo() {
        if(isJsonStorage){
            this.editionList = jsonPersistenceDAO.readEditionsFile();
        }
        else{
            this.editionList = csvPersistenceDAO.readEditionsFile();
        }
    }
}
