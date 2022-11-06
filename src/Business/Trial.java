package Business;

public class Trial {
    private String type;
    private String trialName;

    /**
     * Trial constructor
     * @param type String
     * @param trialName String
     * @author Weriwoh Mbang Goddy
     */
    public Trial(String type, String trialName){
        this.type = type;
        this.trialName = trialName;
    }

    /**
     * Getter for trial type
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for trial name
     * @return String
     */
    public String getTrialName() {
        return trialName;
    }
}
