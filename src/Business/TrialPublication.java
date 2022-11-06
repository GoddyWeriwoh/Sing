package Business;

public class TrialPublication extends Trial{
    private String journalName;
    private String journalQuartile;
    private float acceptanceProbability;
    private float revisionProbability;
    private float rejectionProbability;

    /**
     *
     * @param type String
     * @param trialName String
     * @param journalName String
     * @param journalQuartile String
     * @param acceptanceProbability float
     * @param revisionProbability float
     * @param rejectionProbability float
     * @author Weriwoh Mbang Goddy
     */
    public TrialPublication(String type, String trialName, String journalName, String journalQuartile, float acceptanceProbability, float revisionProbability, float rejectionProbability) {
        super(type, trialName);
        this.journalName = journalName;
        this.journalQuartile = journalQuartile;
        this.acceptanceProbability = acceptanceProbability;
        this.revisionProbability = revisionProbability;
        this.rejectionProbability = rejectionProbability;
    }


    /**
     * Getter for journal name
     * @return String
     */
    public String getJournalName() {
        return journalName;
    }

    /**
     * getter for journal quartile
     * @return String
     */
    public String getJournalQuartile() {
        return journalQuartile;
    }

    /**
     * getter for acceptance probability
     * @return float
     */
    public float getAcceptanceProbability() {
        return acceptanceProbability;
    }

    /**
     * getter for revision probability
     * @return float
     * @author Weriwoh Mbang Goddy
     */
    public float getRevisionProbability() {
        return revisionProbability;
    }

    /**
     * getter for acceptance probability
     * @return float
     * @author Weriwoh Mbang Goddy
     */
    public float getRejectionProbability() {
        return rejectionProbability;
    }

    /**
     * Method to calculate player's PI
     * @param player Player
     * @return String
     */
    public String calculatePlayerPI(Player player) {
        var d = Math.random() * 100;

        if (d < acceptanceProbability){
            //accepted
            int c = getQuartilePi(true);
            player.addPi(c);
            return "Accepted";
        }
        else if (d < (acceptanceProbability + revisionProbability)){
            //revise
            return "Revising";
        }
            //reject
            int c = getQuartilePi(false);
            player.subtractPi(c);

        return "Rejected";

    }

    /**
     * Gets the PI depending on the Quartile associated
     * @param b boolean
     * @return int
     */
    private int getQuartilePi(boolean b) {
        if(b){
            if(journalQuartile.equals("Q1")){
                return 4;
            }
            if(journalQuartile.equals("Q2")){
                return 3;
            }
            if(journalQuartile.equals("Q3")){
                return 2;
            }
            if(journalQuartile.equals("Q4")){
                return 1;
            }
        }else{
            if(journalQuartile.equals("Q1")){
                return 5;
            }
            if(journalQuartile.equals("Q2")){
                return 4;
            }
            if(journalQuartile.equals("Q3")){
                return 3;
            }
            if(journalQuartile.equals("Q4")){
                return 2;
            }
        }
        return 0;
    }

}
