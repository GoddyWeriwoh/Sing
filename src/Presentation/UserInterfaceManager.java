package Presentation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import Business.*;

public class UserInterfaceManager {

    /**
     * Displays main menu
     * @return String
     */
    public String displayMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("/__ \\ |__ ___ \n/__ \\_ __(_) __ _| |___ \n" +
                " / /\\/ '_ \\ / _ \\ / /\\/ '__| |/ _` | / __|\n" +
                "/ / | | | | __/ / / | | | | (_| | \\__ \\\n" +
                "\\/ |_| |_|\\___| \\/ |_| |_|\\__,_|_|___/\n");
        System.out.println("Welcome to the trials. Who are you?"
        + "\n\n\tA) The Composer" + "\n\tB) This year's Conductor\n\nEnter a role: ");

        String choice = scanner.nextLine();
        return choice;
    }

    /**
     * Displays composer menu
     * @return int
     */
    public int displayComposerMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEntering management mode...\n"
                + "\n\t1) Manage Trials\n\t2) Manage Editions\n\n\t3) Exit\n\nEnter an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        return choice;
    }

    /**
     * Shows shutting down system
     */
    public void showShutting() {
        System.out.println("Shutting down...");
    }

    /**
     * Displays trial menu
     * @return String
     */
    public String showTrialManagementSubmenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("a) Create trial\n" +
                " b) List trials\n" +
                " c) Delete trial\n\n" +
                " d) Back\n\n" +
                "Enter an option:");

        String choice = scanner.nextLine();

        return choice;

    }

    /**
     * Displays trial creation heading
     * @return int
     */
    public int showTrialCreationHeading() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-- Trial types --\n\n1) Paper publication\n" +
                " 2) Master studies\n" +
                " 3) Doctoral thesis defense\n" +
                " 4) Budget request\n\nEnter the trial's type: ");

        int choice = 0;
        try{
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return choice;
    }

    /**
     * Asks trial name
     * @return String
     */
    public String trialNameDemand() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the trial's name: ");
        String trialName = scanner.nextLine();
        return trialName;
    }

    /**
     * Displays error in trial name
     */
    public void showInvalidListNameError() {
        System.out.println("Error, invalid trial name provided\n");
    }

    /**
     * Asks for journal name
     * @return String
     */
    public String journalNameDemand() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter journal's name: ");

        return scanner.nextLine();
    }

    /**
     * shows invalid journal
     */
    public void showInvalidJournalNameError() {
        System.out.println("Error, invalid journal name\n");
    }

    /**
     * asks quartile
     * @return String
     */
    public String journalQuartileDemand() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter journal's quartile: ");

        return scanner.nextLine();
    }

    /**
     * Shows invalid quartile
     */
    public void showQuartileEntryError() {
        System.out.println("Error, Invalid quartile entry\n");
    }

    /**
     * Asks acceptance probability
     * @return float
     */
    public float acceptanceProbabilityDemand() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the acceptance probability: ");

        float c = scanner.nextFloat();
        scanner.nextLine();
        return c;
    }

    /**
     * Asks revision probability
     * @return float
     */
    public float revisionProbabilityDemand() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the revision probability: ");

        float c = scanner.nextFloat();
        scanner.nextLine();
        return c;
    }

    /**
     * Shows invalid probability
     */
    public void showInvalidProbabilityError() {
        System.out.println("Error, Invalid probability, must be in [0, 100] range.\n");
    }

    /**
     * Displays error in summing of probabilities
     * @param i int
     */
    public void showInvalidProbabilitySumError(int i) {
        if(i == 2){
            System.out.println("Error, sum of acceptance and revision probabilities greater than 100\n");
        }
        else{
            System.out.println("Error, sum of acceptance, revision and rejection probabilities must be equal to 100\n");

        }
    }

    /**
     * Asks rejection probability
     * @return float
     */
    public float rejectionProbabilityDemand() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the rejection probability: ");

        float c = scanner.nextFloat();
        scanner.nextLine();
        return c;
    }

    /**
     * Displays trial creation success
     */
    public void showCompleteTrialCreation() {
        System.out.println("\nThe trial was created successfully!\n");
    }

    /**
     * Displays when trial name already exist
     */
    public void showTrialNameExists() {
        System.out.println("Error, trial name already exists\n");
    }

    /**
     * Displays trial list
     * @param delete boolean
     * @param trialList ArrayList<Business.Trial>
     * @return int
     */
    public int showTrialList(boolean delete, ArrayList<Trial> trialList) {
        Scanner scanner = new Scanner(System.in);

            if(delete){
                System.out.println("Which trial do you want to delete?\n\n");
            }
            else{
                System.out.println("Here are the current trials, do you want to see more details or go back?\n\n");
            }

        int i;
        for (i = 0; i < trialList.size(); i++) {
            System.out.println("\t" + (i+1) + ") " + trialList.get(i).getTrialName() + "\n");

        }
        System.out.println("\n\t" + (i+1) + ") Back\n\nEnter an option: ");
        int c = scanner.nextInt();
        scanner.nextLine();

        return (c-1);
    }

    /**
     * Displays trial list
     * @param trials ArrayList<Business.Trial>
     */
    public void showTrialList(ArrayList<Trial> trials){
        System.out.println("--- Trials ---\n\t"
                );
        for (int i = 0; i < trials.size(); i++) {
            System.out.println( i+1 + ") " + trials.get(i).getTrialName() + "\n\t");
        }
    }

    /**
     * Shows a trial details
     * @param trial Business.Trial
     */
    public void showTrialDetails(TrialPublication trial) {
        System.out.println("\nBusiness.Trial: " + trial.getTrialName() + " (" + trial.getType() + ")\n"
        + "Journal: " + trial.getJournalName() + " (" + trial.getJournalQuartile() + ")\n"
        + "Chances: " + (int)trial.getAcceptanceProbability() + "% acceptance, " + (int)trial.getRevisionProbability() + "% revision, "
        + (int)trial.getRejectionProbability() + "% rejection\n");
    }

    public void showTrialDetails(TrialMaster trial) {
        System.out.println("\nTrial: " + trial.getTrialName() +
                "\nMaster: " + trial.getMasterName() +
                "\nECTS: " + trial.getMasterECT() +", with a " + (int)trial.getCreditPassProbability() + "% chance to pass each one\n");

    }

    public void showTrialDetails(TrialDoctor trial) {
        System.out.println("\nTrial: " + trial.getTrialName() +
                "\nField: " + trial.getFieldOfStudy() +
                "\nDifficulty: " + trial.getThesisDifficulty() + "\n");
    }

    public void showTrialDetails(TrialBudgetRequest trial) {
        System.out.println("\nTrial: " + trial.getTrialName() +
                "\nEntity: " + trial.getEntityName() +
                "\nBudget: " + (int)trial.getBudgetAmount() + "\n");
    }


    /**
     * Demands for confirmation
     * @return String
     */
    public String demandConfirmation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the trial's name for confirmation: ");
        return scanner.nextLine();
    }

    /**
     * Displays success in deletion of a trial
     */
    public void showDeleteSuccess() {
        System.out.println("The trial was successfully deleted.\n");
    }

    /**
     * Displays when invalid name entered
     */
    public void showIncorrectName() {
        System.out.println("Invalid name entered\n");
    }

    /**
     * Asks for edition year
     * @return String
     */
    public String askEditionYear() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the edition’s year: ");
        return scanner.nextLine();
    }

    /**
     * Displays when year is invalid
     */
    public void showInvalidYear() {
        System.out.println("Error, the year must be greater than the current year\n");
    }

    /**
     * Asks for number of initial players when running edition for the first time
     * @return int
     */
    public int askInitNumberOfPlayers() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the initial number of players: ");

        int c = scanner.nextInt();
        scanner.nextLine();
        return c;
    }

    /**
     * Displays when number of players entered is incorrect, in edition creation
     */
    public void showInvalidNumberOfPlayers() {
        System.out.println("Error, number of players must be in the range [1, 5]\n");
    }

    /**
     * Demands initial number of trials
     * @return int
     */
    public int askInitNumberOfTrials() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of trials: ");

        int c = scanner.nextInt();
        scanner.nextLine();
        return c;
    }

    /**
     * Displays when invalid number of trials entered
     */
    public void showInvalidNumberOfTrials() {
        System.out.println("Error, number of trials must be in the range [3, 12]\n");

    }

    /**
     * Demands to pick a trial
     * @param numOfTrials int
     * @param i int
     * @return int
     */
    public int askToPickATrial(int numOfTrials, int i) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pick a trial (" + i + "/" + numOfTrials + "): ");
        int c = scanner.nextInt();
        scanner.nextLine();
        return c - 1;
    }

    /**
     * When invalid number of trials entered
     */
    public void askForCorrectPick() {
        System.out.println("Please enter a valid trial number");
    }

    /**
     * Displays when edition creation was successful
     */
    public void showCompleteEditionCreation() {
        System.out.println("\nThe edition was created successfully!\n");
    }

    /**
     * shows edition details
     * @param edition Business.Edition
     */
    public void showEditionDetails(Edition edition) {
        System.out.println("\nYear: " + edition.getYear() + "\n"
                + "Players: " + edition.getNumberOfPlayers() + "\n"
                + "Trials:\t");

        for (int i = 0; i < edition.getEditionTrialList().size(); i++) {
            System.out.println("\t" + (i+1) + "- " + edition.getEditionTrialList().get(i).getTrialName() + " (" +
                    edition.getEditionTrialList().get(i).getType() + ")");
        }
        System.out.println("\n");
    }

    /**
     * Displays when edition already exists in edition creation
     */
    public void showYearExistsError() {
        System.out.println("Error, this year's edition already exists\n");
    }

    /**
     * Displays edition menu
     * @return String
     */
    public String showEditionManagementSubmenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("a) Create edition\n" +
                " b) List editions\n" +
                " c) Duplicate edition\n" +
                " d) Delete edition\n\n" +
                " e) Back\n\n" +
                "Enter an option:");

        String choice = scanner.nextLine();

        return choice;
    }

    /**
     * Displays list of editions
     * @param delete boolean
     * @param clone boolean
     * @param editionList ArrayList<Business.Edition>
     * @return int
     */
    public int showEditionList(boolean delete, boolean clone, ArrayList<Edition> editionList) {
        Scanner scanner = new Scanner(System.in);

        if(delete){
            System.out.println("Which edition do you want to delete?\n\n");
        }
        else{
            if(clone){
                System.out.println("Which edition do you want to clone?\n\n");
            }
            else{
                System.out.println("Here are the current editions, do you want to see more details or go back?\n\n");
            }
        }

        int i;
        for (i = 0; i < editionList.size(); i++) {
            System.out.println("\t" + (i+1) + ") " + "The Trials " + editionList.get(i).getYear() + "\n");

        }
        System.out.println("\n\t" + (i+1) + ") Back\n\nEnter an option: ");
        int c = scanner.nextInt();
        scanner.nextLine();

        return (c-1);
    }

    /**
     * Demands new edition year when duplicating
     * @return String
     */
    public String askNewEditionYear() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the edition’s year: ");
        return scanner.nextLine();
    }

    /**
     * Demands initial number of players when duplicating an edition
     * @return int
     */
    public int askDuplicateNewPlayers() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the new edition’s initial number of players:");
        int c = scanner.nextInt();
        scanner.nextLine();
        return c;
    }

    /**
     * Displays when edition cloning was successful
     */
    public void showCloningSuccessful() {
        System.out.println("The edition was cloned successfully!\n");
    }

    /**
     * Asks for year confirmation when deleting edition
     * @return String
     */
    public String askYearConfirmation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the edition’s year for confirmation:");
        return scanner.nextLine();
    }

    /**
     * Shows when entered year during validation is incorrect
     */
    public void showWrongYearEntered() {
        System.out.println("Error, please enter the same year as the chosen edition\n");
    }

    /**
     * Shows when edition deletion successful
     */
    public void showSuccessfulDelete() {
        System.out.println("The edition was successfully deleted.\n");
    }

    /**
     * Displays when trial list is empty
     */
    public void showThatTrialListEmpty() {
        System.out.println("No trials have been created.\n");
    }

    /**
     * Displays when entering edition execution
     */
    public void showEnteringExecution() {
        System.out.println("Entering execution mode...\n");
    }

    /**
     * When edition of current year was not created
     */
    public void showNoCurrentEditionYear() {
        int year = Calendar. getInstance(). get(Calendar. YEAR);

        System.out.println("No edition is defined for the current year (" + year + ").\n\n" +
                "Shutting down...\n");
    }

    /**
     * Displays to demand player's name
     * @param i int
     * @param size int
     * @return String
     */
    public String askPlayerName(int i, int size) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the player’s name " + "(" + i + "/" + size + "): ");
        return scanner.nextLine();
    }

    /**
     * displays current year
     */
    public void showCurrentYear() {
        int year = Calendar. getInstance(). get(Calendar. YEAR);
        System.out.println("--- The Trials "+ year + " ---\n");
    }

    /**
     * Displays trial number and name
     * @param i int
     * @param trialName String
     */
    public void showTrialNumberAndName(int i, String trialName) {
        System.out.println("Trial #" + i + " - " + trialName + "\n");
    }

    /**
     * To know if user wishes to continue or end and save edition
     * @return boolean
     */
    public boolean askToContinue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nContinue the execution? [yes/no]: ");
        String s = scanner.nextLine();
        if(s.equals("no") || s.equals("No")){
            return false;
        }
        return true;
    }

    /**
     * Displays when saving before shutting down system
     */
    public void showShuttingDown() {
        System.out.println("Saving & Shutting down...\n");
    }

    /**
     * Displays when all trials are finished
     */
    public void showTrialsEnded() {
        System.out.println("\nTHE TRIALS 2021 HAVE ENDED - PLAYERS WON\n" +
                "Shutting down...\n");
    }

    /**
     * Displays when submitting a publication
     * @param name String
     */
    public void showSubmitting(String name) {
        System.out.print(name + " is submitting...");
    }

    /**
     * Displays when publication has been accepted
     * @param pi int
     */
    public void showAcceptedAndPi(int pi) {
        System.out.println(" Accepted! PI count: " + pi);
    }

    /**
     * Shows when revising a player publication
     */
    public void showRevising() {
        System.out.print(" Revisions... ");
    }

    /**
     * When a publication has been denied
     * @param pi int
     */
    public void showRejectionAndPi(int pi) {
        if(pi <= 0){
            System.out.println(" Rejected. PI count: " + pi + " - Disqualified!");
        }
        else{
            System.out.println(" Rejected. PI count: " + pi);
        }
    }

    /**
     * Displays when no players left before lats trial
     */
    public void showNoMorePlayers() {
        System.out.println("\nTrial failed!, all players have been eliminated");
    }

    public void showEmptyInput() {
        System.out.println("Error,the name cannot be empty.\n");
    }

    /**
     * Gets the master name from the user
     * @return String
     */
    public String masterNameDemand() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter master's name: ");

        return scanner.nextLine();
    }

    /**
     * Demands and gets master ECT from the user
     * @return int
     */
    public int masterECTDemand() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the master's ECT number: ");
        int c = scanner.nextInt();
        scanner.nextLine();
        return c;
    }

    /**
     * Displays when invalid master ECT entered
     */
    public void showInvalidECT() {
        System.out.println("Error, invalid ECT value provided, must be in [60, 120] interval\n");
    }

    /**
     * Demands and gets master credit pass from the user
     * @return float
     */
    public float askCreditPassProbability() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the credit pass probability: ");
        float c = scanner.nextFloat();
        scanner.nextLine();
        return c;
    }

    /**
     * Displays when invalid master credit pass probability
     */
    public void showInvalidPassProbability() {
        System.out.println("Error, invalid probability provided must be in interval [0, 100]\n");
    }

    /**
     * Menu to obtain the storage type by the user
     * @return String
     */
    public String displayStorageMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nThe IEEE needs to know where your allegiance lies.\n\n" +
                " I) People’s Front of Engineering (CSV)\n" +
                " II) Engineering People’s Front (JSON)\n\n" +
                "Pick a faction: ");

        String choice = scanner.nextLine();
        return choice;
    }

    public void showLoadingData(boolean b) {
        if(b){
            System.out.println("Loading data from JSON files...\n");
        }else{
            System.out.println("Loading data from CSV files...\n");
        }
    }

    /**
     * Displays when an invalid input has been entered
     */
    public void showInvalidEntry() {
        System.out.println("Invalid input\n");
    }

    /**
     * Demands an obtain the study field input for Doctor trial creation from user
     * @return String
     */
    public String studyFieldNameDemand() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the thesis field of study: ");

        return scanner.nextLine();
    }

    /**
     * Obtains thesis difficulty from user
     * @return int
     */
    public int thesisDifficultyDemand() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the defense difficulty: ");
        int c = scanner.nextInt();
        scanner.nextLine();
        return c;
    }

    /**
     * Displays when invalid thesis difficulty given by user
     */
    public void showInvalidDifficulty() {
        System.out.println("Error, invalid thesis difficulty provided, must be in interval [1, 10]\n");
    }

    /**
     * Gets the entity name during budget request creation from user.
     * @return String
     */
    public String entityNameDemand() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the entity’s name: ");

        return scanner.nextLine();
    }

    /**
     * Gets the budget amount from user during budget request trial creation.
     * @return int
     */
    public int budgetAmountDemand() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the budget amount: ");
        int c = scanner.nextInt();
        scanner.nextLine();
        return c;
    }

    /**
     * Displays when invalid budget entered by the user.
     */
    public void showInvalidBudget() {
        System.out.println("Error, invalid budget provided, must be in interval [1000, 2000000000]\n");
    }

    /**
     * Displays when the edition list is empty.
     */
    public void showNoEditionAvailable() {
        System.out.println("There is no edition created yet!\n");
    }

    /**
     * Displays when the trial list is empty.
     */
    public void showNoTrialAvailable() {
        System.out.println("There is no trial created yet!\n");

    }

    /**
     * Displays when the player passes Master trial
     * @param name String
     * @param ect int
     * @param pi int
     * @param initEct int
     */
    public void showSuccess(String name, int ect, int pi, int initEct) {
        System.out.println("\t" + name + " passed "+ ect + "/" + initEct + " ECTS. Congrats! PI count: " + pi);
    }

    /**
     * Displays when the player fails Master trial
     * @param name String
     * @param ect int
     * @param pi int
     * @param masterECT int
     */
    public void showFailure(String name, int ect, int pi, int masterECT) {
        if(pi <= 0){
            System.out.println("\t" + name + " passed "+ ect + "/" + masterECT + " ECTS. Sorry... PI count: " + pi + " - Disqualified!");

        }else{
            System.out.println("\t" + name + " passed "+ ect + "/" + masterECT + " ECTS. Sorry... PI count: " + pi);
        }
    }

    /**
     * shows Evolution of the player
     * @param type String
     * @param playerName String
     * @param playerPi int
     */
    public void showEvolution(String type, String playerName, int playerPi) {
        if(type.equals("Master")){
            System.out.println(playerName + " is now a master (with " + playerPi + " PI). ");
        }
       if(type.equals("Doctor")){
            System.out.println(playerName + " is now a doctor (with " + playerPi + " PI). ");
       }
    }

    /**
     * Displays when the player was successful in Doctor trial
     * @param name String
     * @param pi int
     */
    public void showSuccess(String name, int pi) {
        System.out.println("\t" + name + " was successful. Congrats! PI count: "+ pi);

    }

    /**
     * Displays when the player was unsuccessful in Doctor trial
     * @param name
     * @param pi
     */
    public void showFailure(String name, int pi) {
        if(pi <= 0){
            System.out.println("\t" + name + " was unsuccessful. Sorry... PI count: "+ pi + " - Disqualified! ");
        }
        else{
            System.out.println("\t" + name + " was unsuccessful. Sorry... PI count: "+ pi);

        }
    }

    /**
     * Displays when the research group succeeded the budget request trial
     * @param b boolean
     */
    public void showBudgetGotten(boolean b) {
        if(b){
            System.out.println("The research group got the budget!");
        }
        else{
            System.out.println("Sorry, the research group did not get the budget.");
        }
    }

    /**
     * Shows when the player was a doctor and now has more than or 10 IPs.
     * @param name String
     * @param pi int
     * @param toPHD boolean
     */
    public void showPI(String name, int pi, boolean toPHD) {
        if(toPHD){
            System.out.println("\t" + name + ", PhD. PI count: " + pi);
        }
        else{
            if(pi <= 0){
                System.out.println("\t" + name + ". PI count: " + pi + " - Disqualified!");
            }
            else{
                System.out.println("\t" + name + ". PI count: " + pi);
            }
        }
    }
}
