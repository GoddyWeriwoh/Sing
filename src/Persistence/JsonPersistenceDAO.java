package Persistence;

import Business.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonPersistenceDAO implements PersistenceInterface{

    @Override
    public ArrayList<Trial> readTrialsFile() {
        TrialsJsonDAO trialsJsonDAO = new TrialsJsonDAO();
        return trialsJsonDAO.loadTrialsInformation();
    }

    @Override
    public ArrayList<Edition> readEditionsFile() {
        EditionJsonDAO editionJsonDAO = new EditionJsonDAO();
        TrialsJsonDAO trialsJsonDAO = new TrialsJsonDAO();
        return editionJsonDAO.loadEditionsInformation(trialsJsonDAO.loadTrialsInformation());
    }

    @Override
    public void saveTrialToFile(TrialPublication trial) throws IOException {
        TrialsJsonDAO trialsJsonDAO = new TrialsJsonDAO();
        trialsJsonDAO.saveTrialToFile(trial);
    }

    @Override
    public void saveTrialToFile(TrialMaster trial) throws IOException {
        TrialsJsonDAO trialsJsonDAO = new TrialsJsonDAO();
        trialsJsonDAO.saveTrialToFile(trial);
    }

    @Override
    public void saveTrialToFile(TrialDoctor trial) throws IOException {
        TrialsJsonDAO trialsJsonDAO = new TrialsJsonDAO();
        trialsJsonDAO.saveTrialToFile(trial);
    }

    @Override
    public void saveTrialToFile(TrialBudgetRequest trial) throws IOException {
        TrialsJsonDAO trialsJsonDAO = new TrialsJsonDAO();
        trialsJsonDAO.saveTrialToFile(trial);
    }

    @Override
    public void deleteTrialFromFile(String trialName) throws IOException {
        TrialsJsonDAO trialsJsonDAO = new TrialsJsonDAO();
        trialsJsonDAO.deleteTrialFromFile(trialName);
    }

    @Override
    public void saveEditionToFile(Edition edition) throws IOException {
        EditionJsonDAO editionJsonDAO = new EditionJsonDAO();
        editionJsonDAO.saveEditionToFile(edition);
    }

    @Override
    public void deleteEditionFromFile(String year) throws IOException {
        EditionJsonDAO editionJsonDAO = new EditionJsonDAO();
        editionJsonDAO.deleteEditionFromJson(year);
    }

    @Override
    public List<String> getCurrentEditionStatus() {
        EditionJsonDAO editionJsonDAO = new EditionJsonDAO();
        return editionJsonDAO.getCurrentEditionStatus();
    }

    @Override
    public ArrayList<String> getCurrentEditionInfo() throws FileNotFoundException {
        EditionJsonDAO editionJsonDAO = new EditionJsonDAO();
        return editionJsonDAO.getCurrentEditionInfo();
    }

    @Override
    public void saveEditionInfo(List<String> currentEditionInfo) throws IOException {
        EditionJsonDAO editionJsonDAO = new EditionJsonDAO();
        editionJsonDAO.saveEditionInfo(currentEditionInfo);
    }

    @Override
    public void deleteCurrentEditionInfo() throws IOException {
        EditionJsonDAO editionJsonDAO = new EditionJsonDAO();
        editionJsonDAO.deleteCurrentEditionInfo();
    }
}
