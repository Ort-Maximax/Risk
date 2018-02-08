package com.ort.risk.console;

import com.ort.risk.model.*;
import com.ort.risk.game.actions.*;
import com.ort.risk.game.Launcher.ExecMode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author CS
 * Deployment action : one of the 3 action a player can do in its turn
 */

public class Deployment {

    public static void execute(Player player) {
        Map mapObj = Map.getInstance();
        int exMode = mapObj.getExMode();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int min = mapObj.getNbMinReinforcement();
        int nbDeployMax = DeploymentAction.calcMaxDeploy(player);
        List<Region> playerRegions = player.getControlledRegions();
        //System.out.println(nbRenfort);

        System.out.println("==========================================================");
        System.out.println("\t\t\tDEPLOIEMENT ");
        System.out.println("==========================================================");

        while (nbDeployMax > 0) {

            int selectedRegionIndex = 0;
            Region target = playerRegions.get(selectedRegionIndex);


            //Chosing a number of troops to deploy
            int nbTroopsToDeploy = 1;

            //Random mode
            if (exMode == ExecMode.RANDOM.value()) {
                selectedRegionIndex = (int) ((Math.random() * (playerRegions.size())));
                target = playerRegions.get(selectedRegionIndex);

                nbTroopsToDeploy = (int) ((Math.random() * (nbDeployMax - min))) + min;
            }

            //Console mode
            if (exMode == ExecMode.CONSOLE.value()) {
                //Select region to deploy troop too
                System.out.println("Region controllés :");
                for (int cr = 0; cr < playerRegions.size(); cr++) {
                    Region currentRegion = playerRegions.get(cr);
                    System.out.println("\t[" + cr + "] " + currentRegion.getName() + " - Nb troupes : " + currentRegion.getDeployedTroops());
                }

                if (player.getIsHuman()) {
                    try {
                        do {
                            System.out.println("Choix de la region ? ");
                            selectedRegionIndex = Integer.parseInt(br.readLine());
                        } while (selectedRegionIndex >= playerRegions.size() || selectedRegionIndex < 0);
                    } catch (Exception ex) {

                    }
                }

                if (!player.getIsHuman()) {
                    /* TODO DYLAN */
                    // CHoix de la région sur laquelle deployé des troupes
                    // Choisir une région faible avec des adjacences enemies
                	try {
                        do {
                        	selectedRegionIndex = (int) ((Math.random() * (playerRegions.size())));
                        } while (selectedRegionIndex >= playerRegions.size() || selectedRegionIndex < 0);
                    } catch (Exception ex) {

                    }
                }
                target = playerRegions.get(selectedRegionIndex);

                //Select number of troop to deploy
                System.out.println("Combien de troupes deployer sur la region de " + target.getName());

                if (player.getIsHuman()) {
                    try {
                        do {
                            System.out.println(" (" + min + "-" + nbDeployMax + ") ?");
                            nbTroopsToDeploy = Integer.parseInt(br.readLine());
                        } while (nbTroopsToDeploy < min || nbTroopsToDeploy > nbDeployMax);
                    } catch (Exception ex) {

                    }
                }

                if (!player.getIsHuman()) {
                    /* TODO DYLAN */
                    //Choix du nombre de troupes à y deployer
                    //Choisir en fonction de région faible à renforcer, et du nombre de troupe total à deployer
                	try {
                        do {
                        	nbTroopsToDeploy = (int) ((Math.random() * (nbDeployMax - min))) + min;
                        } while (nbTroopsToDeploy < min || nbTroopsToDeploy > nbDeployMax);
                    } catch (Exception ex) {

                    }
                }
            }


            DeploymentAction.deployTroops(target, nbTroopsToDeploy);
            nbDeployMax -= nbTroopsToDeploy;

            System.out.println(player.getName() + " a deployé (" + nbTroopsToDeploy + ") troupes sur la region de " + target.getName() + " !");
            if (nbDeployMax > 0) {
                System.out.println("Il doit encore deployer " + nbDeployMax + " troupes !!");
            } else {
                System.out.println("Déploiement de troupes terminé !");
            }
            System.out.println("\n");

        }
    }
}
