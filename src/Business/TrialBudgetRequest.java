package Business;

import java.util.ArrayList;

public class TrialBudgetRequest extends Trial{

    private String entityName;
    private float budgetAmount;

    /**
     * Constructor of the budget request trial
     * @param type String
     * @param trialName String
     * @param entityName String
     * @param budgetAmount float
     */
    public TrialBudgetRequest(String type, String trialName, String entityName, float budgetAmount) {
        super(type, trialName);
        this.entityName = entityName;
        this.budgetAmount = budgetAmount;
    }

    /**
     * Getter for entity name
     * @return String
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * Getter for budget amount
     * @return int
     */
    public float getBudgetAmount() {
        return budgetAmount;
    }

    /**
     * Gets the sum of players pi and compares to the algorithm given for budget request trial
     * @param players ArrayList<Player>
     * @return boolean
     */
    public boolean getSumOfResults(ArrayList<Player> players) {
        int sum = 0;

        for (int i = 0; i < players.size(); i++) {
            sum = sum + (players.get(i).getPi());
        }

        int result = (int)(Math.log(getBudgetAmount()) / Math.log(2));

        if(sum > result){
            return true;
        }
        return false;
    }

    /**
     * adds players pi based on given algorithm
     * @param players ArrayList<Player>
     */
    public void addPlayersPI(ArrayList<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            double d = players.get(i).getPi() / 2;
            double d1 = Math.ceil(d);
            players.get(i).addPi((int)d1);
        }
    }

    /**
     *  Checks if the player passed this and was a doctor
     * @param player Player
     * @return boolean
     */
    public boolean doctorNPassTen(Player player) {
        if(player.getType().equals("Doctor") && player.getPi() >= 10) {
            return true;
        }
        return false;
    }

    /**
     * Subtracts player's pi in failure scenario.
     * @param player Player
     */
    public void subtractPlayerPI(Player player) {
        player.subtractPi(2);
    }
}

