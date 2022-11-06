package Business;

public class PlayerEngineer extends Player{
    /**
     * Business.Player class constructor
     *
     * @param name String
     * @param pi   int
     * @author Weriwoh Mbang Goddy
     */
    public PlayerEngineer(String name, int pi) {
        super(name, pi);
        this.setPlayerType("Engineer");
    }
}
