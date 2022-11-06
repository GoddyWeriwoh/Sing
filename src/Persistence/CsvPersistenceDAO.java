package Persistence;

import Business.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CsvPersistenceDAO implements PersistenceInterface{
    @Override
    public ArrayList<Trial> readTrialsFile(){
        TrialsCsvDAO trialsCsvDAO = new TrialsCsvDAO();
        return trialsCsvDAO.loadTrialsInformation();
    }

    @Override
    public ArrayList<Edition> readEditionsFile() {
        EditionCsvDAO editionCsvDAO = new EditionCsvDAO();
        TrialsCsvDAO trialsCsvDAO = new TrialsCsvDAO();
        return editionCsvDAO.loadEditionsInformation(trialsCsvDAO.loadTrialsInformation());
    }

    @Override
    public void saveTrialToFile(TrialPublication trial) throws IOException {
        TrialsCsvDAO trialsCsvDAO = new TrialsCsvDAO();
        trialsCsvDAO.saveTrialToFile(trial);
    }

    @Override
    public void saveTrialToFile(TrialMaster trial) throws IOException {
        TrialsCsvDAO trialsCsvDAO = new TrialsCsvDAO();
        trialsCsvDAO.saveTrialToFile(trial);
    }

    @Override
    public void saveTrialToFile(TrialDoctor trial) throws IOException {
        TrialsCsvDAO trialsCsvDAO = new TrialsCsvDAO();
        trialsCsvDAO.saveTrialToFile(trial);
    }

    @Override
    public void saveTrialToFile(TrialBudgetRequest trial) throws IOException {
        TrialsCsvDAO trialsCsvDAO = new TrialsCsvDAO();
        trialsCsvDAO.saveTrialToFile(trial);
    }

    @Override
    public void deleteTrialFromFile(String trialName) throws IOException {
        TrialsCsvDAO trialsCsvDAO = new TrialsCsvDAO();
        trialsCsvDAO.deleteTrialFromFile(trialName);
    }

    @Override
    public void saveEditionToFile(Edition edition) throws IOException {
        EditionCsvDAO editionCsvDAO = new EditionCsvDAO();
        editionCsvDAO.saveEditionToFile(edition);
    }

    @Override
    public void deleteEditionFromFile(String year) throws IOException {
        EditionCsvDAO editionCsvDAO = new EditionCsvDAO();
        editionCsvDAO.deleteEditionFromCSV(year);
    }

    @Override
    public List<String> getCurrentEditionStatus() {
        EditionCsvDAO editionCsvDAO = new EditionCsvDAO();
        return editionCsvDAO.getCurrentEditionStatus();

    }

    @Override
    public ArrayList<String> getCurrentEditionInfo() throws IOException {
        EditionCsvDAO editionCsvDAO = new EditionCsvDAO();
        return editionCsvDAO.getCurrentEditionInfo();
    }

    @Override
    public void saveEditionInfo(List<String> currentEditionInfo) throws IOException {
        EditionCsvDAO editionCsvDAO = new EditionCsvDAO();
        editionCsvDAO.saveEditionInfo(currentEditionInfo);
    }

    @Override
    public void deleteCurrentEditionInfo() throws IOException {
        EditionCsvDAO editionCsvDAO = new EditionCsvDAO();
        editionCsvDAO.deleteCurrentEditionInfo();
    }
}
