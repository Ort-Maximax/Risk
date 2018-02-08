package com.ort.risk.console;


import com.ort.risk.game.Launcher;
import com.ort.risk.model.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
/**
 * @author CS
 * Console input mode for the reinforcement action
 */
public class Reinforcement {
    enum ReinfAction {
        REINFORCE(1),
        STOP(0);

        private final int value;

        ReinfAction(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }

    }

    public static void execute(Player player) {
        Map mapObj = Map.getInstance();
        int exMode = mapObj.getExMode();
        List<Region> reinforcementRegions = player.getReinforcementRegions();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        ConsoleLauncher.printTitle("RENFORCEMENT", 60, '~');

        while (reinforcementRegions.size() >= 1) {


            int reinfDecision = 0;

            //Choose to attack, and stop war
            if (exMode == Launcher.ExecMode.RANDOM.value()) {
                if (Math.random() >= 0.5) {
                    reinfDecision = ReinfAction.REINFORCE.value();
                } else {
                    reinfDecision = ReinfAction.STOP.value();
                }
            }

            if (exMode == Launcher.ExecMode.CONSOLE.value()) {

                System.out.println("\t[0] Fin du tour");
                System.out.println("\t[1] Renforcer une région");
                if (player.getIsHuman()) {
                    try {
                        do {
                            System.out.println("Quel est votre choix ? ");
                            reinfDecision = Integer.parseInt(br.readLine());
                        } while (reinfDecision < 0 || reinfDecision > 1);
                    } catch (Exception ex) {

                    }
                }

                if (!player.getIsHuman()) {
                    /* TODO DYLAN */
                    // Choix de renforcer ou non ce tour
                    // Aucune idée
                    try {
                        do {
                            //TODO
                        } while (reinfDecision < 0 || reinfDecision > 1);
                    } catch (Exception ex) {

                    }
                }

            }


            //If player choose to reinforce
            if (reinfDecision == ReinfAction.REINFORCE.value()) {

                //Choose a region to reinforce
                int selectedStartRegionIndex = 0;

                //Console input mode
                if (exMode == Launcher.ExecMode.CONSOLE.value()) {
                    System.out.println("Region éligible à un renforcement :");
                    for (int rr = 0; rr < reinforcementRegions.size(); rr++) {
                        Region currentRegion = reinforcementRegions.get(rr);
                        System.out.println("\t[" + rr + "] " + currentRegion.getName() + " - Nb troupes : " + currentRegion.getDeployedTroops());
                    }

                    if (player.getIsHuman()) {
                        try {
                            do {
                                System.out.println("Choix de la region de renforcer ? ");
                                selectedStartRegionIndex = Integer.parseInt(br.readLine());
                            }
                            while (selectedStartRegionIndex >= reinforcementRegions.size() || selectedStartRegionIndex < 0);
                        } catch (Exception ex) {

                        }
                    }

                    if (!player.getIsHuman()) {
                        /* TODO DYLAN */
                        //Choix de la région à renforcer
                        // Choisir une région qui a des adjacences avec avec des region enemies forte / avec des regions allié faible
                        try {
                            do {
                                //TODO
                            }
                            while (selectedStartRegionIndex >= reinforcementRegions.size() || selectedStartRegionIndex < 0);
                        } catch (Exception ex) {

                        }
                    }
                    System.out.println("\n==========================================================\n");
                }

                //Random mode
                if (exMode == Launcher.ExecMode.RANDOM.value()) {
                    selectedStartRegionIndex = (int) ((Math.random() * (reinforcementRegions.size())));

                }

                Region selectedStartRegion = reinforcementRegions.get(selectedStartRegionIndex);


                //Choose a end region for the attack
                List<Region> allReinfProviders = selectedStartRegion.getAllReinforcementTargets(player);
                int selectedEndRegionIndex = 0;

                if (allReinfProviders.size() > 0) {


                    //Console input mode
                    if (exMode == Launcher.ExecMode.CONSOLE.value()) {
                        System.out.println("Selectionnez la région qui fournis les renforts");
                        for (int wt = 0; wt < allReinfProviders.size(); wt++) {
                            Region currentRegion = allReinfProviders.get(wt);
                            System.out.println("\t[" + wt + "] " + currentRegion.getName() + " - Nb troupes : " + currentRegion.getDeployedTroops());
                        }

                        if (player.getIsHuman()) {
                            try {
                                do {
                                    System.out.println("Choix de la region fournisseuse ? ");
                                    selectedEndRegionIndex = Integer.parseInt(br.readLine());
                                }
                                while (selectedEndRegionIndex >= allReinfProviders.size() || selectedEndRegionIndex < 0);
                            } catch (Exception ex) {

                            }
                        }

                        if (!player.getIsHuman()) {
                            /* TODO DYLAN */
                            // CHoix de la region qui fournis les troupes du renforcement
                            // CHoisir une région si possible qui n'a pas d'adjacences avec une region enemies, et un nb de troupe confortable
                            try {
                                do {
                                    //TODO
                                }
                                while (selectedEndRegionIndex >= allReinfProviders.size() || selectedEndRegionIndex < 0);
                            } catch (Exception ex) {

                            }
                        }

                        //Random mode
                        if (exMode == Launcher.ExecMode.RANDOM.value()) {

                            selectedEndRegionIndex = (int) ((Math.random() * (allReinfProviders.size())));

                        }

                        Region startRegion = reinforcementRegions.get(selectedStartRegionIndex);

                        String endRegionName = allReinfProviders.get(selectedEndRegionIndex).getName();

                        Region endRegion = mapObj.getRegionByName(endRegionName);

                        Move reinfMove = startRegion.getFrontierReinfMove(endRegionName);

                        System.out.println("Combien de troupes souhaitez-vous deplacer ? ");
                        int nbTroops = 1;
                        if (exMode == Launcher.ExecMode.CONSOLE.value()) {
                            if (player.getIsHuman()) {
                                try {
                                    do {
                                        System.out.println("(1-" + (endRegion.getDeployedTroops() - 1) + ")");
                                        nbTroops = Integer.parseInt(br.readLine());
                                    } while (nbTroops > endRegion.getDeployedTroops() - 1 || nbTroops < 1);
                                } catch (Exception ex) {

                                }
                            }
                        }

                        if (!player.getIsHuman()) {
                            /* TODO DYLAN */
                            // CHoix du nb de troupe a deplacer
                            // Choisir un nombre en fonction de "l'urgence" du renforcement = si la region a renforcer est entouré de region enemie tres forte, mettre un gros chiffre
                            try {
                                do {
                                    //TODO
                                } while (nbTroops > endRegion.getDeployedTroops() - 1 || nbTroops < 1);
                            } catch (Exception ex) {

                            }
                        }

                        if (!player.getIsHuman()) {
                            /* TODO DYLAN */
                            // CHoix du nb de troupe a deplacer
                            // Choisir un nombre en fonction de "l'urgence" du renforcement = si la region a renforcer est entouré de region enemie tres forte, mettre un gros chiffre
                        }

                        reinfMove.execute(startRegion, endRegion, nbTroops);

                        System.out.println(player.getName() + " à deplacer " + nbTroops + " de " + endRegion.getName() + " jusqu'à " + startRegion.getName() + "!\n");


                    } else {
                        System.out.println("\n" + player.getName() + " a fini de renforcer ces régions !");
                        return;
                    }
                } else {
                    System.out.println("Il ne reste aucune region apte à renforcer !");
                    return;
                }

                reinforcementRegions = player.getReinforcementRegions();

            }

        }
    }
}
