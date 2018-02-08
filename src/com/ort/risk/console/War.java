package com.ort.risk.console;

import com.ort.risk.game.Launcher;
import com.ort.risk.model.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import java.util.concurrent.TimeUnit;


/**
 * @author CS
 * Console input mode for the war action
 */
public class War {

    public static void execute(Player player) {
        Map mapObj = Map.getInstance();
        int exMode = mapObj.getExMode();

        List<Region> warStartRegions = player.getWarRegions();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        ConsoleLauncher.printTitle("GUERRE", 60, '~');
        while (warStartRegions.size() >= 1) {

            int warDecision = 0;

            //Choose to attack, and stop war
            if (exMode == Launcher.ExecMode.RANDOM.value()) {
                /*if (Math.random() >= 0.5) {
                    warDecision = WarAction.ATTACK.value();
                } else {
                    warDecision = WarAction.STOP.value();
                }*/
                warDecision = WarAction.ATTACK.value();
            }

            if (exMode == Launcher.ExecMode.CONSOLE.value()) {
                System.out.println("Quel est votre choix ? ");
                System.out.println("\t[0] Passer a la phase de renforcement");
                System.out.println("\t[1] Lancer une attaque");
                if (player.getIsHuman()) {
                    try {
                        do {
                            System.out.println("(0-1)");
                            warDecision = Integer.parseInt(br.readLine());
                        } while (warDecision < 0 || warDecision > 1);
                    } catch (Exception ex) {

                    }
                }

                if (!player.getIsHuman()) {
                    /* TODO DYLAN */
                    //Choix de lancer une attaque ou pas
                    // Aucune idée. Random pondéré en fonction du nombre de region controllé par l'IA peut etre ?
                	try {
                        do {
                        	//TODO
                        } while (warDecision < 0 || warDecision > 1);
                    } catch (Exception ex) {

                    }
                }

                System.out.println("\n==========================================================\n");

            }


            //If player choose to attack
            if (warDecision == WarAction.ATTACK.value) {
                //Choose a start region for the war
                int selectedStartRegionIndex = 0;

                //Console input mode
                if (exMode == Launcher.ExecMode.CONSOLE.value()) {
                    System.out.println("Regions prête à la guerre :");
                    for (int wr = 0; wr < warStartRegions.size(); wr++) {
                        Region currentRegion = warStartRegions.get(wr);
                        System.out.println("\t[" + wr + "] " + currentRegion.getName() + " - Nb troupes : " + currentRegion.getDeployedTroops());
                    }

                    if (player.getIsHuman()) {
                        try {
                            do {
                                System.out.println("Choix de la region de départ ? ");
                                selectedStartRegionIndex = Integer.parseInt(br.readLine());
                            }
                            while (selectedStartRegionIndex >= warStartRegions.size() || selectedStartRegionIndex < 0);
                        } catch (Exception ex) {

                        }
                    }

                    if (!player.getIsHuman()) {
                        /* TODO DYLAN */
                        // Choix de la région attaquante
                        // choisir une region forte, et/ou qui a des adjacences enemies faible
                    	try {
                            do {
                                //TODO
                            }
                            while (selectedStartRegionIndex >= warStartRegions.size() || selectedStartRegionIndex < 0);
                        } catch (Exception ex) {

                        }
                    }
                    System.out.println("\n==========================================================\n");
                }

                //Random mode
                if (exMode == Launcher.ExecMode.RANDOM.value()) {
                    selectedStartRegionIndex = (int) ((Math.random() * (warStartRegions.size())));

                }

                Region selectedStartRegion = warStartRegions.get(selectedStartRegionIndex);


                //Choose a end region for the attack
                List<Region> allWarTargets = selectedStartRegion.getAllWarTargets(player);
                int selectedEndRegionIndex = 0;


                //Console input mode
                if (exMode == Launcher.ExecMode.CONSOLE.value()) {
                    System.out.println("Selectionnez la région à attaquer");
                    for (int wt = 0; wt < allWarTargets.size(); wt++) {
                        Region currentRegion = allWarTargets.get(wt);
                        System.out.println("\t[" + wt + "] " + currentRegion.getName() + " - Nb troupes : " + currentRegion.getDeployedTroops());
                    }

                    if (player.getIsHuman()) {
                        try {
                            do {
                                System.out.println("(0-" + allWarTargets.size() + ")");
                                selectedEndRegionIndex = Integer.parseInt(br.readLine());
                            } while (selectedEndRegionIndex >= allWarTargets.size() || selectedEndRegionIndex < 0);
                        } catch (Exception ex) {

                        }
                    }

                    if (!player.getIsHuman()) {
                        /* TODO DYLAN */
                        //Selection d'une region a attaquer
                        //Choisir la region la plus faible possible
                    	try {
                            do {
                                //TODO
                            } while (selectedEndRegionIndex >= allWarTargets.size() || selectedEndRegionIndex < 0);
                        } catch (Exception ex) {

                        }
                    }

                    System.out.println("\n==========================================================\n");
                }

                //Random mode
                if (exMode == Launcher.ExecMode.RANDOM.value()) {

                    selectedEndRegionIndex = 0;

                }

                //Fetch the list of war move for the given start and end region
                Region startRegion = warStartRegions.get(selectedStartRegionIndex);

                if (allWarTargets.size() > 0) {


                    String endRegionName = allWarTargets.get(selectedEndRegionIndex).getName();

                    List<Move> availableMoves = startRegion.getFrontierWarMoves(endRegionName);
                    int selectedMoveIndex = 0;


                    //Console input mode
                    if (exMode == Launcher.ExecMode.CONSOLE.value()) {
                        System.out.println("Selectionner l'action à effectuer");
                        for (int wm = 0; wm < availableMoves.size(); wm++) {
                            Move currentMove = availableMoves.get(wm);
                            System.out.println("\t[" + wm + "] " + currentMove.getName());
                        }

                        if (player.getIsHuman()) {
                            try {
                                do {
                                    System.out.println("Choix de l'action ? ");
                                    selectedMoveIndex = Integer.parseInt(br.readLine());
                                } while (selectedMoveIndex >= availableMoves.size() || selectedMoveIndex < 0);
                            } catch (Exception ex) {

                            }
                        }

                     

                        if(!player.getIsHuman()) {
                            /* TODO DYLAN */
                            // Toujours action 0
                          try {
                                do {
                                    selectedMoveIndex = 0;
                                } while (selectedMoveIndex >= availableMoves.size() || selectedMoveIndex < 0);
                            } catch (Exception ex) {

                            }

                        //Random mode
                        if (exMode == Launcher.ExecMode.RANDOM.value()) {
                            selectedMoveIndex = (int) ((Math.random() * (availableMoves.size())));

                        }


                    int nbAttack = 0;

                    System.out.println("Choisissez le nombre de troupes à envoyer pour l'attaque");
                    if (exMode == Launcher.ExecMode.CONSOLE.value()) {
                        if (player.getIsHuman()) {


                            try {
                                do {
                                    System.out.println("(1-" + Math.min(3, startRegion.getDeployedTroops()) + ")");
                                    nbAttack = Integer.parseInt(br.readLine());
                                } while (nbAttack < 0 || nbAttack > Math.min(3, startRegion.getDeployedTroops()));
                            } catch (Exception ex) {

                            }

                        }
                    }


                if(!player.getIsHuman()) {
                    /* TODO DYLAN */
                    // Aucune idée... random ?
                	// Most Efficient Tactical Attack ?
                	try {
                        do {
                            //TODO
                        } while (nbAttack < 0 || nbAttack > Math.min(3, startRegion.getDeployedTroops()));
                    } catch (Exception ex) {

                    }
                }

                    if (exMode == Launcher.ExecMode.RANDOM.value()) {
                        nbAttack = Math.min(3, (startRegion.getDeployedTroops()));
                    }



                    Region endRegion = mapObj.getRegionByName(endRegionName);

                    int nbDef = 0;

                    boolean r = endRegion.getIsRogue();

                    try {
                        TimeUnit.MILLISECONDS.sleep(400);
                    } catch (Exception e) {

                    }
                
                    if (!r) {
                        Player defPlayer = mapObj.getOwnerOfRegion(endRegion);

                        System.out.println("\n" + defPlayer.getName() + ", combien de troupes vont defendre l'attaque ?");
                        if (exMode == Launcher.ExecMode.CONSOLE.value()) {
                            if (defPlayer.getIsHuman()) {
                                try {
                                    do {
                                        System.out.println("(1-" + Math.min(2, endRegion.getDeployedTroops()) + ")");
                                        nbDef = Integer.parseInt(br.readLine());
                                    } while (nbDef < 0 || nbDef > Math.min(2, endRegion.getDeployedTroops()));
                                } catch (Exception ex) {

                                }
                            }
                        }

                        if (exMode == Launcher.ExecMode.RANDOM.value()) {
                            nbDef = Math.min(2, endRegion.getDeployedTroops());
                        }


                        if (!defPlayer.getIsHuman()) {
                            /* TODO DYLAN */
                            // Choix du nombre de troupes pour la défenses
                            // Aucune idée...
                        }

                    } else {
                        nbDef = 1;
                    }


                    Object[] result = availableMoves.get(selectedMoveIndex).execute(startRegion, endRegion, player, nbAttack, nbDef);
                    /**
                     * 0 : Jet de dés de l'attaque
                     * 1 : jet de dès de la defense
                     * 2 : Victoire ou defaite
                     * 3 : [ atkLoss, defLoss ]
                     * 4 : Prise du territoire ?
                     */
                    switch ((Integer) result[2]) {
                        case -1:
                            System.out.println("\nL'assaut est un echec ! ");
                            break;
                        case 0:
                            System.out.println("\nLes pertes sont égales des deux cotés ! ");
                            break;
                        case 1:
                            System.out.println("\nL'assaut est un succès ! ");
                            break;
                    }

                    Integer[] arrLoss = (Integer[]) result[3];

                    System.out.println("L'attaque a perdu " + arrLoss[0] + " de ses " + nbAttack + " troupes engagées !");
                    System.out.println("La défense a perdu " + arrLoss[1] + " de ses " + nbDef + " troupes engagées !");

                    if ((boolean) result[4]) {
                        System.out.println("Tout les troupes de la région attaquées ont été vaincu, " + player.getName() + " s'empare de la region de " + endRegion.getName() + " !!\n");
                    }

                    System.out.println("\n==========================================================\n");


                } else {
                    System.out.println("\n" + player.getName() + " a arreter la guerre !");
                    // If the player choose to stop the war
                    return;
                }
            } else {
                System.out.println(" ??? ");
                // If the player choose to stop the war
                return;
            }

            warStartRegions = player.getWarRegions();

        }
    }

    enum WarAction {
        ATTACK(1),
        STOP(0);

        private final int value;

        WarAction(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }

    }
}
