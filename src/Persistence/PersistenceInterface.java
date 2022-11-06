package Persistence;
import Business.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface PersistenceInterface {
    /**
     * Reads the trial file and gets the list.
     * @return ArrayList<Trial>
     */
    ArrayList<Trial> readTrialsFile();
    /**
     * Reads the edition file and gets the list.
     * @return ArrayList<Edition>
     */
    ArrayList<Edition> readEditionsFile();
    /**
     * Saves the given trial publication to the database file.
     * @param trial TrialPublication
     * @throws IOException e
     */
    void saveTrialToFile(TrialPublication trial) throws IOException;
    /**
     * Saves the given trial master to the trial database file.
     * @param trial TrialMaster
     * @throws IOException e
     */
    void saveTrialToFile(TrialMaster trial) throws IOException;
    /**
     * Saves the given trial doctoral thesis to the trial database file.
     * @param trial Trial
     * @throws IOException e
     */
    void saveTrialToFile(TrialDoctor trial) throws IOException;
    /**
     * Saves the given trial budget request to the trial database file.
     * @param trial Trial
     * @throws IOException e
     */
    void saveTrialToFile(TrialBudgetRequest trial) throws IOException;

    /**
     * Deletes the given trial from the trial database file.
     * @param trialName String
     * @throws IOException e
     */
    void deleteTrialFromFile(String trialName) throws IOException;

    /**
     * Saves the given edition to the edition database file.
     * @param edition Edition
     * @throws IOException e
     */
    void saveEditionToFile(Edition edition) throws IOException;
    /**
     * Deletes the given edition from the edition database file.
     * @param year String
     * @throws IOException e
     */
    void deleteEditionFromFile(String year)throws IOException;

    /**
     * Gets the current edition ongoing status by returning information present in database to be later on checked, if it is null or not.
     * @return List<String>
     */
    List<String> getCurrentEditionStatus();

    /**
     * Gets current edition information. That is, current trial and players and data.
     * @return List<String>
     */
    ArrayList<String> getCurrentEditionInfo() throws IOException;

    /**
     * Saves the current edition information into current edition database file.
     * @param currentEditionInfo List<String>
     * @throws IOException e
     */
    void saveEditionInfo(List<String> currentEditionInfo) throws IOException;

    /**
     * Deletes the current edition information from current edition database file.
     * @throws IOException e
     */
    void deleteCurrentEditionInfo() throws IOException;
}
