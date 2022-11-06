package Presentation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import Business.*;

public class MainController {
    private final UserInterfaceManager uim;
    private final TrialManager trialManager;
    private final EditionManager editionManager;
    private final CurrentEditionManager currentEditionManager;

    /**
     * Main controller constructor which takes these parameters to distribute them accordingly to the other controllers
     * @param uim UserInterfaceManager
     * @param trialManager TrialManager
     * @param editionManager EditionManager
     * @param currentEditionManager CurrentEditionManager
     * @author Weriwoh Mbang Goddy
     */
    public MainController(UserInterfaceManager uim, TrialManager trialManager, EditionManager editionManager, CurrentEditionManager currentEditionManager) {
    this.uim = uim;
    this.trialManager = trialManager;
    this.editionManager = editionManager;
    this.currentEditionManager = currentEditionManager;
    }

    /**
     * A method to run the main controller and run internal methods
     */
    public void run() {

        String choice1 = uim.displayStorageMenu();

        if(choice1.equals("I")){
            uim.showLoadingData(false);
            trialManager.isJsonStorage(false);
            editionManager.isJsonStorage(false);
            currentEditionManager.isJsonStorage(false);
            String choice = uim.displayMenu();

            if (choice.equals("A")) {
                ComposerController composerController = new ComposerController(uim, trialManager, editionManager);
                composerController.run();
            } else {
                if(choice.equals("B")){
                    ConductorController conductorController = new ConductorController(uim, currentEditionManager);
                    conductorController.run();
                }
            }
        }
        else{
            if(choice1.equals("II")){
                uim.showLoadingData(true);
                trialManager.isJsonStorage(true);
                editionManager.isJsonStorage(true);
                currentEditionManager.isJsonStorage(true);

                String choice = uim.displayMenu();

                if (choice.equals("A")) {
                    ComposerController composerController = new ComposerController(uim, trialManager, editionManager);
                    composerController.run();
                } else {
                    if(choice.equals("B")){
                        ConductorController conductorController = new ConductorController(uim, currentEditionManager);
                        conductorController.run();
                    }
                }
            }
            else{
                uim.showInvalidEntry();
            }
        }
    }

}
