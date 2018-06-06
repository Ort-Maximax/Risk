package com.ort.risk.ui.console;

import com.ort.risk.ui.Launcher;
import com.ort.risk.model.Map;
import com.ort.risk.model.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author CS
 * Player turn
 */
public class Turn {
    public static void TakeTurn(Player currentPlayer){
        Map mapObj = Map.getInstance();
        int exMode = mapObj.getExMode();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        ConsoleLauncher.printTitle("DEBUT DU TOUR DE " + currentPlayer.getName(), 60, '=' ) ;

        Deployment.execute(currentPlayer);

        if(currentPlayer.getWarRegions().size() > 0){
            War.execute(currentPlayer);
        }

        if(currentPlayer.getReinforcementRegions().size() > 0) {
            Reinforcement.execute(currentPlayer);
        }




        ConsoleLauncher.printTitle("FIN DU TOUR DE " + currentPlayer.getName(), 60, '=' ) ;

        //Print end of turn info only in console mode
        if(exMode == Launcher.ExecMode.CONSOLE.value()){
            System.out.println("Entrez 'e' pour voir les regions controllées par les joueurs ");
            System.out.println("Appuyez Entrée pour passer au joueur suivant ");
            try {
                String s = br.readLine();
                if(s.equals("e")){
                    System.out.println("\n");
                    System.out.println("===========================================");
                    System.out.println("===========================================");
                    System.out.println("\n");
                    Play.printPlayerRegions();
                    System.out.println("\n");
                    System.out.println("===========================================");
                    System.out.println("===========================================");
                    System.out.println("\n");

                    System.out.println("--Appuyez sur une touche pour revenir au jeu--");
                    br.readLine();
                }
            } catch (Exception ex) {

            }
        }
    }
}
