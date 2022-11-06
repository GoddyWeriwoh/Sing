package Business;

public class PlayerDoctor extends Player{
    /**
     * Business.Player class constructor
     *
     * @param name String
     * @param pi   int
     * @author Weriwoh Mbang Goddy
     */
    public PlayerDoctor(String name, int pi) {

        super(name, pi);
        this.setPlayerType("Doctor");
    }

    /**
     * subtracts the doctor's pi by half
     * @param c int
     */
    @Override
    public void subtractPi(int c) {
        super.subtractPi(c/2);
    }

    /**
     * adds doctor's twice than the amount
     * @param c int
     */
    @Override
    public void addPi(int c) {
        super.addPi(2 * c);
    }
}
