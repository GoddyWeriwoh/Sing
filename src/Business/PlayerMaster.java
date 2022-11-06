package Business;

public class PlayerMaster extends Player{
    /**
     * Business.Player class constructor
     *
     * @param name String
     * @param pi   int
     * @author Weriwoh Mbang Goddy
     */
    public PlayerMaster(String name, int pi) {

        super(name, pi);
        this.setPlayerType("Master");

    }

    /**
     * subtracts player maser pi by half
     * @param c int
     */
    @Override
    public void subtractPi(int c) {
        super.subtractPi(c/2);
    }
}
