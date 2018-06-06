package com.ort.risk.ui.console;

import com.ort.risk.business.DeploymentAction;
import com.ort.risk.model.*;
import com.ort.risk.ui.Launcher.ExecMode;
import com.ort.risk.model.Map;
import com.ort.risk.model.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author CS
 * Contaien the two game phases : Initialisation, and turn loop
 */
public class Play {


    public static void GameLoop() {
        Map mapObj = Map.getInstance();
        int exMode = mapObj.getExMode();
        List<Player> playerList = mapObj.getPlayerList();

        //Sort the players by turn order
        playerList.sort(Comparator.comparing(Player::getOrder));

        /*
        playerList.forEach((player) -> {
            System.out.println(player.getOrder());

        });
        */

        //Filter to keep only the players that have at least 1 controlled region
        List<Player> validPlayerList = playerList.stream()
                .filter(p -> p.getControlledRegions().size() >= 1).collect(Collectors.toList());

        //While there is still 2 valid player in the game
        while (validPlayerList.size() >= 2) {

            // For each player

            validPlayerList.forEach((player) -> {
                //Player take turn
                Turn.TakeTurn(player);


            });

            //Update the valid player list after every turn
            validPlayerList = playerList.stream()
                    .filter(p -> p.getControlledRegions().size() >= 1).collect(Collectors.toList());
        }


        //When there is only one valid player
        displayEndGameScreen(validPlayerList.get(0));



    }

    public static void InitDeployment() {
        Map mapObj = Map.getInstance();
        int exMode = mapObj.getExMode();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(
                "__________.___  ______________  _.\n" +
                        "\\______   \\   |/   _____/    |/ _|\n" +
                        " |       _/   |\\_____  \\|      <  \n" +
                        " |    |   \\   |/        \\    |  \\ \n" +
                        " |____|_  /___/_______  /____|__ \\\n" +
                        "        \\/            \\/        \\/");

        List<Mode> allModes = mapObj.getModes();
        int selectedModeIndex = 0;

        //Random mode
        if (exMode == ExecMode.RANDOM.value()) {
            selectedModeIndex = (int) (Math.random() * allModes.size());
            //selectedModeIndex = 2;
        }

        //Console mode
        if (exMode == ExecMode.CONSOLE.value()) {
            System.out.println("Modes disponibles : ");
            for (int ci = 0; ci < allModes.size(); ci++) {
                Mode curMode = allModes.get(ci);
                System.out.println("[" + ci + "]");
                System.out.println("\t Nb joueurs : " + curMode.getNbPlayer());
                System.out.println("\t Nb troupes initial : " + curMode.getNbInitTroops());

            }
            try {
                do {
                    System.out.println("Choix du mode ? ");
                    selectedModeIndex = Integer.parseInt(br.readLine());
                } while (selectedModeIndex > allModes.size() || selectedModeIndex < 0);


            } catch (Exception ex) {

            }
        }

        allModes.get(selectedModeIndex).setIsSelected(true);

        System.out.println("Mode selectionné : ");
        System.out.println("\tNb joueurs : " + allModes.get(selectedModeIndex).getNbPlayer());
        System.out.println("\tNb initial de troupes : " + allModes.get(selectedModeIndex).getNbInitTroops());
        System.out.println("\n");

        int nbTroupePerPlayer = allModes.get(selectedModeIndex).getNbInitTroops();
        for (int n = 1; n <= allModes.get(selectedModeIndex).getNbPlayer(); n++) {

            String playerName = "Player" + n;
            String playerIsHuman = "y";

            if(exMode == ExecMode.CONSOLE.value()) {

                try {
                    System.out.println("\nEntrez le nom du joueur " + n);


                    playerName = br.readLine();



                } catch (Exception ex) {

                }

                System.out.println("Joueur humain ? ");
                try {

                    System.out.println("(y/n)");
                    playerIsHuman = br.readLine();

                } catch (Exception ex) {

                }
            }

            boolean isHuman = true;

            switch (playerIsHuman) {
                case "n":
                    playerName = "(COM)" + playerName;
                    isHuman = false;

                    break;
                default:
                    isHuman = true;
                    break;
            }
            // Add players to the map

            if(playerName.length() == 0){
                playerName = "Player" + n;
            }
            mapObj.addPlayer(new Player(playerName, isHuman, n, nbTroupePerPlayer));

            /*System.out.println("\tNom : " + playerName);
            System.out.println("\tOrdre de passage : " + n);*/
        }

        if (exMode == ExecMode.CONSOLE.value()) {

            ConsoleLauncher.printTitle("Attribution des regions", 60, '+');
        }

        //Regions' attribution
        //Filter to get only the not occupied regions
        List<Region> notOccupiedRegions = mapObj.getRegions().stream()
                .filter(p -> !p.getIsOccupied()).collect(Collectors.toList());

        while (notOccupiedRegions.size() >= mapObj.getPlayerList().size() ) {
            for (int p = 0; p < mapObj.getPlayerList().size() && notOccupiedRegions.size() > 0; p++) {
                int selectedRegionIndex = 0;
                Player currentPlayer = mapObj.getPlayerList().get(p);
                //Random mode
                if (exMode == ExecMode.RANDOM.value()) {
                    selectedRegionIndex = (int) (Math.random() * notOccupiedRegions.size());
                }

                //Console mode
                if (exMode == ExecMode.CONSOLE.value()) {

                    String s = "CHOIX DU JOUEUR " + currentPlayer.getName();
                    ConsoleLauncher.printTitle(s, 60, '=');

                    System.out.println("Regions neutres : ");
                    for (int nor = 0; nor < notOccupiedRegions.size(); nor++) {
                        System.out.println("\t[" + nor + "]" + notOccupiedRegions.get(nor).getName());
                    }

                    if (currentPlayer.getIsHuman()) {
                        try {
                            do {
                                System.out.println("Choix de la region ? ");
                                selectedRegionIndex = Integer.parseInt(br.readLine());
                            } while (selectedRegionIndex >= notOccupiedRegions.size() || selectedRegionIndex < 0);
                        } catch (Exception ex) {

                        }
                    }

                    if (!currentPlayer.getIsHuman()) {
                    	try {
                            do {
                            	if(currentPlayer.getControlledRegions() == null)
                            	{
                            		selectedRegionIndex = (int) ((Math.random() * (notOccupiedRegions.size())));
                            	}
                            	else
                            	{
                            		List<Region> controlledRegions = currentPlayer.getControlledRegions();
                            		Region r = new Region();
                            		for(int i=0; i < controlledRegions.size(); i++ )
                            		{
                            			r = controlledRegions.get(i);
                            		}
                            		List<Frontier> frontierR = r.getFrontiers();
                            		Frontier f = new Frontier();
                            		for(int i=0; i < frontierR.size(); i++) 
                            		{
                            			f = frontierR.get(i);
                            		}
                            		String regionEndName = f.getRegionEndName();
                            		
                            		for(int i=0; i < notOccupiedRegions.size(); i++)
                            		{
                            			if(notOccupiedRegions.get(i).getName() == regionEndName)
                            			{
                            				selectedRegionIndex = i;
                            				break;
                            			}
                            			else
                            			{
                            				selectedRegionIndex = (int) ((Math.random() * (notOccupiedRegions.size())));
                            			}
                            		}
                            	}
                            } while (selectedRegionIndex >= notOccupiedRegions.size() || selectedRegionIndex < 0);
                            System.out.println(notOccupiedRegions.get(selectedRegionIndex).getName());
                        } catch (Exception ex) {

                        }
                    }

                }

                //Both region and zone list references the same region objects
                //Hence, change flags in the region list would change them in the zone list too
                Region chosenRegion = notOccupiedRegions.get(selectedRegionIndex);

                DeploymentAction.attribRegion(currentPlayer, chosenRegion);


                notOccupiedRegions = notOccupiedRegions.stream()
                        .filter(po -> !po.getIsOccupied()).collect(Collectors.toList());

            }
        }

        //Give the regions that cant be evenly splitted between the player to the ORCS
        for(Region stillNotOccupiedRegion : notOccupiedRegions){
            //We can't use attribRegion function here, since the region doesnt actually belong to any player
            stillNotOccupiedRegion.setDeployedTroops(1);
            stillNotOccupiedRegion.setIsRogue(true);
            stillNotOccupiedRegion.setIsOccupied(true);
        }


        //Initial deployment

        // Console mode
        if (exMode == ExecMode.CONSOLE.value()) {

            ConsoleLauncher.printTitle("Deploiement initial", 60, '+');

            for (int q = 0; q < mapObj.getPlayerList().size(); q++) {
                Player currentPlayer = mapObj.getPlayerList().get(q);
                List<Region> playerRegions = currentPlayer.getControlledRegions();
                int selectedRegionIndex = 0;
                int nbTroopsToDeploy = 0;

                ConsoleLauncher.printTitle("DEPLOIEMENT DE " + currentPlayer.getName(), 60, '=');

                while (currentPlayer.getNbTroops() > 0) {

                    System.out.println("Troupes a repartir par " + currentPlayer.getName() + " : " + currentPlayer.getNbTroops() + "\n");

                    //Select region to deploy troop too
                    System.out.println("Regions controlées :");
                    for (int cr = 0; cr < playerRegions.size(); cr++) {
                        Region currentRegion = playerRegions.get(cr);
                        System.out.println("\t[" + cr + "] " + currentRegion.getName() + " - Nb troupes : " + currentRegion.getDeployedTroops());
                    }

                    if (currentPlayer.getIsHuman()) {
                        try {
                            do {
                                System.out.println("Choix de la region ? ");
                                selectedRegionIndex = Integer.parseInt(br.readLine());
                            } while (selectedRegionIndex >= playerRegions.size() || selectedRegionIndex < 0);
                        } catch (Exception ex) {

                        }
                    }

                    if (!currentPlayer.getIsHuman()) {
                    	try {
                            do {
                            	List<Region> controlledRegions = currentPlayer.getControlledRegions();
		                    	Region r = new Region();
		                		for(int i=0; i < controlledRegions.size(); i++ )
		                		{
		                			r = controlledRegions.get(i);
		                			List<Frontier> frontierR = r.getFrontiers();
		                			Frontier f = new Frontier();
		                    		for(int j=0; j < frontierR.size(); j++) 
		                    		{
		                    			f = frontierR.get(j);
		                    			String regionEndName = f.getRegionEndName();
		                    			boolean isOccupied = mapObj.getRegionByName(regionEndName).getIsOccupied();
		                    			if(isOccupied == true && r.getDeployedTroops() == 1)
		                    			{
		                    				selectedRegionIndex = i;
                            				break;
		                    			}
		                    			else
		                    			{
		                    				selectedRegionIndex = i+1;
		                    			}
		                    		}
		                		}
                            } while (selectedRegionIndex >= playerRegions.size() || selectedRegionIndex < 0);
                		} catch (Exception ex) {
                			
                		}
                    }

                    //Select number of troop to deploy
                    System.out.println("Combien de troupes deployer sur la region de " + playerRegions.get(selectedRegionIndex).getName());

                    if (currentPlayer.getIsHuman()) {
                        try {
                            do {
                                System.out.println(" (1-" + currentPlayer.getNbTroops() + ") ?");
                                nbTroopsToDeploy = Integer.parseInt(br.readLine());
                            } while (nbTroopsToDeploy < 1 || nbTroopsToDeploy > currentPlayer.getNbTroops());
                        } catch (Exception ex) {

                        }
                    }

                    if (!currentPlayer.getIsHuman()) {
                    	try {
                            do {
                                 int nbTroops = currentPlayer.getNbTroops();
                                 if(nbTroops > 10)
                                 {
                                	 nbTroopsToDeploy = (int) (Math.random() * (nbTroops - 9));
                                 }
                                 else
                                 {
                                	 nbTroopsToDeploy = 1;
                                 }
                            } while (nbTroopsToDeploy < 1 || nbTroopsToDeploy > currentPlayer.getNbTroops());
                        } catch (Exception ex) {

                        }
                    }

                    Region selectedRegion = currentPlayer.getControlledRegions().get(selectedRegionIndex);

                    DeploymentAction.deployTroops(selectedRegion, nbTroopsToDeploy);

                    currentPlayer.changeNbTroops(-nbTroopsToDeploy);

                    if (currentPlayer.getNbTroops() > 0) {
                        System.out.println("\n==========================================================\n");
                    }
                }
                System.out.println("\n==========================================================\n");

            }
        }

        //Random mode
        if (exMode == ExecMode.RANDOM.value()) {
            /* TODO  : A CHANGER - Le déploiement se fait de façon random, en attendant l'interface graphique */
            for (int q = 0; q < mapObj.getPlayerList().size(); q++) {
                Player currentPlayer = mapObj.getPlayerList().get(q);
                while (currentPlayer.getNbTroops() > 0) {
                    int rand = (int) (Math.random() * currentPlayer.getControlledRegions().size());
                    currentPlayer.getControlledRegions().get(rand).changeDeployedTroops(1);
                    currentPlayer.changeNbTroops(-1);
                }
            }
        }


        ConsoleLauncher.printTitle("DEBUT DE LA PARTIE", 60, '=');
    }

    public static void displayEndGameScreen(Player winner){
        //Make sure to clear the screen
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        System.out.println(
                "   ____     ____        _    __     __    U  ___ u \n" +
                "U | __\")uU |  _\"\\ u U  /\"\\  u\\ \\   /\"/u    \\/\"_ \\/ \n" +
                " \\|  _ \\/ \\| |_) |/  \\/ _ \\/  \\ \\ / //     | | | | \n" +
                "  | |_) |  |  _ <    / ___ \\  /\\ V /_,-.-,_| |_| | \n" +
                "  |____/   |_| \\_\\  /_/   \\_\\U  \\_/-(_/ \\_)-\\___/  \n" +
                " _|| \\\\_   //   \\\\_  \\\\    >>  //            \\\\    \n" +
                "(__) (__) (__)  (__)(__)  (__)(__)          (__)   ");

        System.out.println( winner.getName() + " remporte la partie !!!");

    }

    public static void printPlayerRegions() {
        Map mapObj = Map.getInstance();
        //For each player
        for (Player p : mapObj.getPlayerList()) {
            System.out.println(p.getName());
            List<Region> playerRegions = p.getControlledRegions();

            //For every player region
            for (Region r : playerRegions) {
                System.out.println("\t" + r.getName() + " : " + r.getDeployedTroops());

            }
            System.out.println("\n");
        }

        List<Region> a = mapObj.getRogueRegions();
        if(mapObj.getRogueRegions().size() > 0) {
            System.out.println("Regions sauvage");

            for (Region r : mapObj.getRogueRegions()) {
                System.out.println("\t" + r.getName() + " : " + r.getDeployedTroops());

            }

        }
    }



}
