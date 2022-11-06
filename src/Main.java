import Business.CurrentEditionManager;
import Business.EditionManager;
import Business.TrialManager;
import Persistence.CsvPersistenceDAO;
import Persistence.JsonPersistenceDAO;
import Presentation.MainController;
import Presentation.UserInterfaceManager;

public class Main {
    public static void main(String[] args) {
        CsvPersistenceDAO csvPersistenceDAO = new CsvPersistenceDAO();
        JsonPersistenceDAO jsonPersistenceDAO = new JsonPersistenceDAO();
        TrialManager trialManager = new TrialManager(csvPersistenceDAO, jsonPersistenceDAO);
        EditionManager editionManager = new EditionManager(csvPersistenceDAO, jsonPersistenceDAO);
        CurrentEditionManager currentEditionManager = new CurrentEditionManager(csvPersistenceDAO, jsonPersistenceDAO);

        UserInterfaceManager uim = new UserInterfaceManager();
        MainController mainController = new MainController(uim, trialManager, editionManager, currentEditionManager);
        mainController.run();
    }
}
