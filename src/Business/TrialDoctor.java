package Business;

public class TrialDoctor extends Trial {

    private String fieldOfStudy;
    private int thesisDifficulty;

    /**
     * Trial Doctor constructor
     * @param type String
     * @param trialName String
     * @param fieldOfStudy String
     * @param thesisDifficulty int
     * @author Weriwoh Mbang Goddy
     */
    public TrialDoctor(String type, String trialName, String fieldOfStudy, int thesisDifficulty) {
        super(type, trialName);
        this.fieldOfStudy = fieldOfStudy;
        this.thesisDifficulty = thesisDifficulty;
    }

    /**
     * Gets the doctor trial field of study.
     * @return String
     */
    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    /**
     * Gets the thesis difficulty.
     * @return int
     */
    public int getThesisDifficulty() {
        return thesisDifficulty;
    }

    /**
     * Calculates player success status.
     * @param player Player
     * @return boolean
     */
    public boolean calculatePlayerPass(Player player) {
        int sum = 0;

        for (int i1 = 0; i1 < getThesisDifficulty(); i1++) {
            sum = ((2 * i1) - 1) + sum;
        }

        if (player.getPi() <= Math.sqrt(sum)) {
            player.subtractPi(5);
            return false;
        } else {
            player.addPi(5);
            player.setPassStatus(true);
        }
        return true;
    }

    /**
     * Checks if player is master and passed
     * @param player Player
     * @return boolean
     */
    public boolean masterNPass(Player player) {
        if (player instanceof PlayerMaster && player.isPassStatus()) {
            return true;
        }
        return false;
    }
}

