package Business;

public class Player {
    private String name;
    private int pi;
    private int ECT;
    private boolean passStatus = false;
    private String type = "Engineer";



    /**
     * Business.Player class constructor
     * @param name String
     * @param pi int
     * @author Weriwoh Mbang Goddy
     */
    public Player(String name, int pi){
        this.name = name;
        this.pi = pi;
    }

    /**
     * Getter for player name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the current player Pi points
     * @return int
     */
    public int getPi() {
        return pi;
    }

    /**
     * Method to add points to current pi
     * @param c int
     */
    public void addPi(int c) {
        this.pi = this.pi + c;
    }

    /**
     * Method to subtract points from current pi
     * @param c int
     */
    public void subtractPi(int c) {
        this.pi = this.pi - c;
    }

    /**
     * Used to set a new player ECT
     * @param ECT int
     */
    public void setECT(int ECT) {
        this.ECT = ECT;
    }

    /**
     * Used to return player's ECT
     * @return int
     */
    public int getECT(){
        return ECT;
    }

    /**
     * Used to set the trial pass status to the player
     * @param passStatus boolean
     */
    public void setPassStatus(boolean passStatus) {
        this.passStatus = passStatus;
    }

    /**
     * Used to set the player's type
     * @param type String
     */
    public void setPlayerType(String type) {
        this.type = type;
    }

    /**
     * Gets the status of a player passing or not
     * @return boolean
     */
    public boolean isPassStatus() {
        return passStatus;
    }


    public String getType() {
        return type;
    }

    public String getPlayerType() {
       return type;
    }
}
