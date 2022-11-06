package Presentation;

import Business.*;

import java.io.IOException;

public class ConductorController {
    private final UserInterfaceManager uim;
    private final CurrentEditionManager currentEditionManager;

    public ConductorController(UserInterfaceManager uim, CurrentEditionManager currentEditionManager) {
        this.uim = uim;
        this.currentEditionManager = currentEditionManager;
    }

    public void run(){
        boolean go = true;
        boolean finished = false;

        uim.showEnteringExecution();
        if(!currentEditionManager.correctCurrentYearEdition()){
            uim.showNoCurrentEditionYear();
        }
        else{
            currentEditionManager.extractCurrentEdition();
            uim.showCurrentYear();
            if(!currentEditionManager.ongoingEdition()){
                getPlayers(currentEditionManager.getCurrentEdition().getNumberOfPlayers());
                try{
                    currentEditionManager.setCurrentEdition();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            else {
                try{
                    currentEditionManager.setCurrentEdition();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            int i = currentEditionManager.getCurrentTrialNumber();

            do{
                go = manageTrial(i);
                if(currentEditionManager.trialSuccessful()){
                    if(i == (currentEditionManager.getNumberOfTrials() - 1)){
                        finished = true;
                    }
                    i++;
                    currentEditionManager.incrementCurrentTrialNum();
                }else{
                    finished = true;
                }
            }while(go && !finished);

            if(!go){
                uim.showShuttingDown();
                try{
                    currentEditionManager.saveEditionInfo();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            else{
                if(currentEditionManager.trialSuccessful()){
                    uim.showTrialsEnded();
                }
                else{
                    if(finished){
                        uim.showNoMorePlayers();
                    }
                }
                try{
                    currentEditionManager.deleteCurrentEditionInfo();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }
    }

    private boolean manageTrial(int i) {
        boolean go = true;

        uim.showTrialNumberAndName((i+1), currentEditionManager.getCurrentTrialName());

            if(currentEditionManager.getTrial(i) instanceof TrialPublication) {
                for (int j = 0; j < currentEditionManager.getCurrentNumberOfPlayers(); j++) {
                    uim.showSubmitting(currentEditionManager.getPlayerName(j));
                    String s = currentEditionManager.calculatePlayerPI(i, j);
                    boolean endCheck = false;

                    do {
                        endCheck = true;
                        if (s.equals("Accepted")) {
                            uim.showAcceptedAndPi(currentEditionManager.getPlayerPi(j));
                        } else if (s.equals("Revising")) {
                            //revise
                            uim.showRevising();
                            s = currentEditionManager.calculatePlayerPI(i, j);
                            endCheck = false;
                        } else {
                            //reject
                            uim.showRejectionAndPi(currentEditionManager.getPlayerPi(j));
                        }
                    } while (!endCheck);
                    if(currentEditionManager.getPlayerPi(j) <= 0){
                        currentEditionManager.removePlayer(j);
                        j--;
                    }
                    if(currentEditionManager.getCurrentNumberOfPlayers() == 0){
                        currentEditionManager.setTrialSuccess(false);
                    }
                }

                checkEvolutions();
            }

            if(currentEditionManager.getTrial(i) instanceof TrialMaster){
                for (int j = 0; j < currentEditionManager.getCurrentNumberOfPlayers(); j++) {
                    if(currentEditionManager.calculatePlayerECTSPass(j, i)){
                        uim.showSuccess(currentEditionManager.getPlayerName(j), currentEditionManager.getPlayerECT(j), currentEditionManager.getPlayerPI(j), currentEditionManager.getMasterECT(i));
                    }else{
                        uim.showFailure(currentEditionManager.getPlayerName(j), currentEditionManager.getPlayerECT(j), currentEditionManager.getPlayerPI(j), currentEditionManager.getMasterECT(i));
                    }
                }
                for (int j1 = 0; j1 < currentEditionManager.getCurrentNumberOfPlayers(); j1++) {
                    if(currentEditionManager.engineerNPass(i, j1)){
                        uim.showEvolution("Master", currentEditionManager.getPlayerName(j1), currentEditionManager.getPlayerPI(j1));
                    }
                    if(currentEditionManager.getPlayerPi(j1) <= 0){
                        currentEditionManager.removePlayer(j1);
                        j1--;
                    }
                    if(currentEditionManager.getCurrentNumberOfPlayers() == 0){
                        currentEditionManager.setTrialSuccess(false);
                    }
                }
                checkEvolutions();
            }

            if(currentEditionManager.getTrial(i) instanceof TrialDoctor){
                for (int j = 0; j < currentEditionManager.getCurrentNumberOfPlayers(); j++) {
                    boolean b = currentEditionManager.calculatePlayerPass(j, i);
                    if(b){
                        uim.showSuccess(currentEditionManager.getPlayerName(j), currentEditionManager.getPlayerPI(j));
                    }
                    else{
                        uim.showFailure(currentEditionManager.getPlayerName(j), currentEditionManager.getPlayerPI(j));
                    }
                }
                for (int j1 = 0; j1 < currentEditionManager.getCurrentNumberOfPlayers(); j1++) {
                    if(currentEditionManager.masterNPass(i, j1)){
                        uim.showEvolution("Doctor", currentEditionManager.getPlayerName(j1), currentEditionManager.getPlayerPI(j1));
                    }
                    if(currentEditionManager.getPlayerPi(j1) <= 0){
                        currentEditionManager.removePlayer(j1);
                        j1--;
                    }
                    if(currentEditionManager.getCurrentNumberOfPlayers() == 0){
                        currentEditionManager.setTrialSuccess(false);
                    }
                }
                checkEvolutions();
            }

            if(currentEditionManager.getTrial(i) instanceof TrialBudgetRequest){
                 if(currentEditionManager.budgetSuccess(i)){
                     uim.showBudgetGotten(true);
                     currentEditionManager.addPlayersPI(i);

                     for (int j = 0; j < currentEditionManager.getCurrentNumberOfPlayers(); j++) {
                         boolean b = currentEditionManager.doctorNPassTen(j, i);
                         if(b){
                             uim.showPI(currentEditionManager.getPlayerName(j), currentEditionManager.getPlayerPI(j), true);
                         }
                         else{
                             uim.showPI(currentEditionManager.getPlayerName(j), currentEditionManager.getPlayerPI(j), false);
                         }
                     }
                     checkEvolutions();
                 }
                else{
                     uim.showBudgetGotten(false);
                     for (int j = 0; j < currentEditionManager.getCurrentNumberOfPlayers(); j++) {
                         currentEditionManager.subtractPlayerPI(j, i);
                         uim.showPI(currentEditionManager.getPlayerName(j), currentEditionManager.getPlayerPI(j), false);
                     }
                }
            }

        for (int j = 0; j < currentEditionManager.getCurrentNumberOfPlayers(); j++) {
            if(currentEditionManager.getPlayerPi(j) <= 0){
                currentEditionManager.removePlayer(j);
                j--;
            }
        }
        if(currentEditionManager.getCurrentNumberOfPlayers() == 0){
            currentEditionManager.setTrialSuccess(false);
        }

        boolean b = currentEditionManager.trialSuccessful();
        if((i < currentEditionManager.getNumberOfTrials() - 1) && currentEditionManager.trialSuccessful()){
            go = uim.askToContinue();
        }

        return go;
    }

    private void checkEvolutions() {
        for (int i = 0; i < currentEditionManager.getCurrentNumberOfPlayers(); i++) {
            if(currentEditionManager.playerHasTenIp(i)){
                if(currentEditionManager.getPlayer(i) instanceof PlayerEngineer){
                    currentEditionManager.evolveToMaster(i);
                    uim.showEvolution("Master", currentEditionManager.getPlayerName(i), currentEditionManager.getPlayerPi(i));
                }
                else{
                    if(currentEditionManager.getPlayer(i) instanceof PlayerMaster){
                        currentEditionManager.evolveToDoctor(i);
                        uim.showEvolution("Doctor", currentEditionManager.getPlayerName(i), currentEditionManager.getPlayerPi(i));
                    }
                }
            }
        }
    }

    private void getPlayers(int numberOfPlayers) {
        for (int i = 0; i < numberOfPlayers; i++) {
            String name = uim.askPlayerName((i+1), numberOfPlayers);
            currentEditionManager.addPlayer(name);
        }
    }
}
