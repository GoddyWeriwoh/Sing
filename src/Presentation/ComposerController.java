package Presentation;

import Business.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class ComposerController {
    private final UserInterfaceManager uim;
    private final TrialManager trialManager;
    private final EditionManager editionManager;

    /**
     * Composer Controller constructor
     * @param uim UserInterfaceManager
     * @param trialManager  TrialManager
     * @param editionManager EditionManager
     * @author Weriwoh Mbang Goddy
     */
    public ComposerController(UserInterfaceManager uim, TrialManager trialManager, EditionManager editionManager) {
        this.uim = uim;
        this.trialManager = trialManager;
        this.editionManager = editionManager;
    }

    /**
     * Method to run the composer controller and the corresponding methods
     * @author Weriwoh Mbang Goddy
     */
    public void run(){
        trialManager.loadAllInfo();
        editionManager.loadAllInfo();
        boolean exit = false;

        do{
            exit = false;
            int modeChoice = uim.displayComposerMenu();
            switch (modeChoice){
                case 1:
                    runTrialManagementControl();
                    break;

                case 2:
                    runEditionManagement();
                    break;

                case 3:
                    exit = true;
                    uim.showShutting();
                    break;
            }
        }while(!exit);

    }

    private void runEditionManagement() {
        boolean end;
        do{
            end = false;
            String managementChoice = uim.showEditionManagementSubmenu();
            switch (managementChoice){
                case "a":
                    runEditionCreation();
                    break;

                case "b":
                    runEditionList();
                    break;

                case "c":
                    runEditionDuplication();
                    break;

                case "d":
                    runEditionDeletion();
                    break;

                case "e":
                    end = true;
                    break;
            }
        }while(!end);
    }

    private void runEditionDeletion() {
        if(editionManager.getEditionList().size() == 0){
            uim.showNoEditionAvailable();
        }
        else{
            int choice = uim.showEditionList(true, false, editionManager.getEditionList());
            if(choice < editionManager.getEditionList().size() && choice >= 0){
                String year = uim.askYearConfirmation();
                if(year.equals(editionManager.getEdition(choice).getYear())){
                    try{
                        editionManager.deleteEdition(choice);
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    uim.showSuccessfulDelete();
                }
                else{
                    uim.showWrongYearEntered();
                }
            }
        }
    }

    private void runEditionDuplication() {
        if(editionManager.getEditionList().size() == 0){
            uim.showNoEditionAvailable();
        }
        else{
            int choice = uim.showEditionList(false, true, editionManager.getEditionList());
            String editionYear = uim.askNewEditionYear();
            if(editionManager.checkValidEditionYear(editionYear)){
                if(editionManager.checkUniqueEditionYear(editionYear)){
                    if(choice < editionManager.getEditionList().size()){
                        int numbPlayers = uim.askDuplicateNewPlayers();
                        if(numbPlayers < 1 ||  numbPlayers > 5){
                            uim.showInvalidNumberOfPlayers();
                        }
                        else{
                            editionManager.duplicateEdition(editionYear, numbPlayers, editionManager.getEdition(choice));
                            uim.showCloningSuccessful();
                        }
                    }
                }
                else{
                    uim.showYearExistsError();
                }
            }
            else{
                uim.showInvalidYear();
            }
        }
    }

    private void runEditionList() {
        if(editionManager.getEditionList().size() == 0){
            uim.showNoEditionAvailable();
        }
        else{
            int choice = uim.showEditionList(false, false, editionManager.getEditionList());

            if(editionManager.getEditionList().size() >= 1){
                if((choice) < editionManager.getEditionList().size() && choice >= 0){
                    uim.showEditionDetails(editionManager.getEdition(choice));
                }
            }

        }
    }

    private void runEditionCreation() {
        String editionYear = uim.askEditionYear();
        if(editionManager.checkValidEditionYear(editionYear)){
            if(editionManager.checkUniqueEditionYear(editionYear)){
                int numOfPlayers = uim.askInitNumberOfPlayers();
                if(!editionManager.validNumberOfPlayer(numOfPlayers)){
                    uim.showInvalidNumberOfPlayers();
                }
                else{
                    int numOfTrials = uim.askInitNumberOfTrials();
                    if(!editionManager.validNumberOfTrials(numOfTrials)){
                        uim.showInvalidNumberOfTrials();
                    }
                    else{
                        uim.showTrialList(trialManager.getTrialList());
                        if(trialManager.getTrialList().size() == 0){
                            uim.showThatTrialListEmpty();
                        }else{
                            boolean ok = true;
                            ArrayList<Trial> editionTrialList = new ArrayList<>();

                            for (int i = 0; i < numOfTrials; i++) {
                                do{
                                    ok = true;
                                    int c = uim.askToPickATrial(numOfTrials, i+1);
                                    if(c >= (trialManager.getTrialList().size()) || c < 0){
                                        uim.askForCorrectPick();
                                        ok = false;
                                    }
                                    else{
                                        editionTrialList.add(trialManager.getTrial(c));
                                    }
                                }while(!ok);
                            }
                            try{
                                editionManager.addEdition(editionYear, numOfPlayers, numOfTrials, editionTrialList);
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                            uim.showCompleteEditionCreation();
                        }
                    }
                }
            }
            else{
                uim.showYearExistsError();
            }
        }
        else{
            uim.showInvalidYear();
        }
    }


    private void runTrialManagementControl() {
        boolean end;
        do{
            end = false;
            String managementChoice = uim.showTrialManagementSubmenu();
            switch (managementChoice){
                case "a":
                    runTrialCreation();
                    break;

                case "b":
                    runTrialList();
                    break;

                case "c":
                    runTrialDeletion();
                    break;

                case "d":
                    end = true;
                    break;
            }
        }while(!end);
    }

    private void runTrialDeletion() {
        if(trialManager.getTrialList().size() == 0){
            uim.showNoTrialAvailable();
        }
        else{
            boolean delete = true;
            int choice = uim.showTrialList(delete, trialManager.getTrialList());
            String name = uim.demandConfirmation();

            if(trialManager.getTrialName(choice).equals(name)){
                try{
                    trialManager.deleteTrial(choice);

                }catch (IOException e){
                    e.printStackTrace();
                }
                uim.showDeleteSuccess();
            }
            else{
                uim.showIncorrectName();
            }
        }
    }

    private void runTrialList() {
        if(trialManager.getTrialList().size() == 0){
            uim.showNoTrialAvailable();
        }
        else{
            int choice = uim.showTrialList(false,trialManager.getTrialList());
            if(choice < trialManager.getTrialList().size() && choice >= 0){
            }
            if(choice < trialManager.getTrialList().size() && choice >= 0){
                if(trialManager.isPublication(choice)){
                    uim.showTrialDetails((TrialPublication)  trialManager.getTrial(choice));
                }
                if(trialManager.isMaster(choice)){
                    uim.showTrialDetails((TrialMaster) trialManager.getTrial(choice));
                }
                if(trialManager.isDoctor(choice)){
                    uim.showTrialDetails((TrialDoctor)  trialManager.getTrial(choice));
                }
                if(trialManager.isBudgetRequest(choice)){
                    uim.showTrialDetails((TrialBudgetRequest)  trialManager.getTrial(choice));
                }
            }
        }
    }

    private void runTrialCreation() {
        int choice = uim.showTrialCreationHeading();

        switch (choice){
            case 1:
                String trialName = uim.trialNameDemand();

                if(trialManager.checkTrialInput(trialName)){
                    if(trialManager.checkUniqueness(trialName)){
                        String journalName = uim.journalNameDemand();
                        if(!trialManager.checkJournalName(journalName)){
                            uim.showInvalidJournalNameError();
                        }
                        else{
                            String journalQuartile = uim.journalQuartileDemand();

                            if(trialManager.checkQuartile(journalQuartile)){
                                float acceptanceProbability = uim.acceptanceProbabilityDemand();
                                if(!trialManager.checkAcceptanceProbability(acceptanceProbability)){
                                    uim.showInvalidProbabilityError();
                                }
                                else{
                                    float revisionProbability = uim.revisionProbabilityDemand();
                                    if(trialManager.checkRevisionProbability(revisionProbability)){
                                        if(trialManager.checkRevisionProbability(revisionProbability,acceptanceProbability)){
                                            float rejectionProbability = uim.rejectionProbabilityDemand();
                                            if(trialManager.checkRejectionProbability(rejectionProbability)){
                                                if(trialManager.checkRejectionProbability(revisionProbability,acceptanceProbability,rejectionProbability)){
                                                    try{
                                                        trialManager.createAndSaveTrial(trialName, journalName, journalQuartile, acceptanceProbability, revisionProbability, rejectionProbability);
                                                    }catch (IOException e){
                                                        e.printStackTrace();
                                                    }
                                                    uim.showCompleteTrialCreation();
                                                }
                                                else{
                                                    uim.showInvalidProbabilitySumError(3);
                                                }
                                            }
                                            else {
                                                uim.showInvalidProbabilityError();
                                            }

                                        }
                                        else{
                                            uim.showInvalidProbabilitySumError(2);
                                        }
                                    }
                                    else{
                                        uim.showInvalidProbabilityError();
                                    }
                                }
                            }
                            else{
                                uim.showQuartileEntryError();
                            }
                        }
                    }
                    else{
                        uim.showInvalidListNameError();
                    }
                }
                else{
                    uim.showEmptyInput();
                }

                break;

            case 2:
                demandMasterInformation();
                break;

            case 3:
                demandDoctorInformation();
                break;

            case 4:
                demandBudgetInformation();
                break;
        }
    }

    private void demandBudgetInformation() {
        boolean correct = true;
        String trialName = uim.trialNameDemand();

        if(trialManager.checkTrialInput(trialName)) {
            if (trialManager.checkUniqueness(trialName)) {
                String entityName = uim.entityNameDemand();
                if (trialManager.checkTrialInput(entityName)) {
                    float budget = 0;
                    try{
                        budget = uim.budgetAmountDemand();
                    }
                    catch (InputMismatchException e){
                        uim.showInvalidBudget();
                         correct = false;
                    }
                    if(correct){
                        if(trialManager.correctBudget(budget)){
                            try{
                                trialManager.createAndSaveTrial(trialName, entityName, budget);
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                            uim.showCompleteTrialCreation();
                        }
                        else{
                            uim.showInvalidBudget();
                        }
                    }

                }else{
                    uim.showEmptyInput();
                }
            }else{
                uim.showInvalidListNameError();
            }
        }else{
            uim.showEmptyInput();
        }
    }

    private void demandDoctorInformation() {
        String trialName = uim.trialNameDemand();

        if(trialManager.checkTrialInput(trialName)) {
            if (trialManager.checkUniqueness(trialName)) {
                String studyField = uim.studyFieldNameDemand();
                if(trialManager.checkTrialInput(studyField)){
                    int difficulty = uim.thesisDifficultyDemand();

                    if(trialManager.correctThesisDifficulty(difficulty)){
                        try{
                            trialManager.createAndSaveTrial(trialName, studyField, difficulty);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        uim.showCompleteTrialCreation();

                    }else{
                        uim.showInvalidDifficulty();
                    }
                }else{
                    uim.showEmptyInput();
                }

            }
            else{
                uim.showInvalidListNameError();
            }
        }
        else{
            uim.showEmptyInput();
        }

    }

    private void demandMasterInformation() {
        String trialName = uim.trialNameDemand();
        if(trialManager.checkTrialInput(trialName)){
            if(trialManager.checkUniqueness(trialName)){
                String masterName = uim.masterNameDemand();
                if(trialManager.checkTrialInput(masterName)){
                    int masterECT = uim.masterECTDemand();
                    if(trialManager.correctMasterECT(masterECT)){
                        float creditPassProbability = uim.askCreditPassProbability();
                        if(trialManager.correctCreditPassProbability(creditPassProbability)){
                            try{
                                trialManager.createAndSaveTrial(trialName, masterName, masterECT, creditPassProbability);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            uim.showCompleteTrialCreation();
                        }else{
                            uim.showInvalidPassProbability();
                        }
                    }else{
                        uim.showInvalidECT();
                    }
                }else{
                    uim.showEmptyInput();
                }
            }else{
                uim.showInvalidListNameError();
            }
        }
        else{
            uim.showEmptyInput();
        }
    }
}
