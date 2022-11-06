package Business;

public class TrialMaster extends Trial{

    private String masterName;
    private int masterECT;
    private float creditPassProbability;


    /**
     * Trial Master constructor
     * @param type String
     * @param trialName String
     * @param masterName String
     * @param masterECT int
     * @param creditPassProbability float
     */
    public TrialMaster(String type, String trialName, String masterName, int masterECT, float creditPassProbability) {
        super(type, trialName);
        this.masterName = masterName;
        this.masterECT = masterECT;
        this.creditPassProbability = creditPassProbability;
    }

    /**
     * Getter for master name
     * @return String
     */
    public String getMasterName() {
        return masterName;
    }

    /**
     * Getter for master ECT
     * @return int
     */
    public int getMasterECT() {
        return masterECT;
    }

    /**
     * Getter for credit pass probability
     * @return float
     */
    public float getCreditPassProbability() {
        return creditPassProbability;
    }

    /**
     * Used to determine the trial remaining ECT passed by player
     * @return int
     */
    public int getRemainingECT() {
        int remainingECT = getMasterECT();

        for (int i1 = 0; i1 < getMasterECT(); i1++) {
            var d = Math.random() * 100;

            if (d < getCreditPassProbability()){
                //passed
            }
            else{
                //failed
                remainingECT = remainingECT - 1;
            }
        }

        return remainingECT;
    }

    /**
     * Used to determine if the Master trial was successful my checking ECTs
     * @param player Player
     * @return boolean
     */
    public boolean successfulPI(Player player) {
        if(player.getECT() < (getMasterECT()/2)){
            //failed trial
            player.subtractPi(3);
            return false;
        }
        else{
            player.addPi(3);
            player.setPassStatus(true);
            return true;
        }
    }

    /**
     * This method checks if the player has passed the master trial and was an engineer
     * @param player Player
     * @return boolean
     */
    public boolean engineerNPass(Player player) {
        if(player instanceof PlayerEngineer && player.isPassStatus()){
            return true;
        }
        return false;
    }
}
