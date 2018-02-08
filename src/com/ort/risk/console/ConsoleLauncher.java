package com.ort.risk.console;

import com.ort.risk.game.*;
import java.util.stream.*;

/**
 * @author CS
 * Entry point of the console mode
 */
public class ConsoleLauncher {

    public static void run() {
        System.out.println("MODE CONSOLE");

        // Parse XML file into Map object
        Parser.prepMap();

        //Mode selection, and regions repartitions between the players
        Play.InitDeployment();

        //Main loop
        Play.GameLoop();
    }

    public final static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }

    //print a section title
    public static void printTitle(String text, int nbLineChar, char borderChar){
        int blankSpacesNumber = nbLineChar - text.length();


        System.out.print("\n");
        //If the blank spaces can be divided by two
        if(blankSpacesNumber % 2 == 0){

            Stream.generate(() -> borderChar).limit(nbLineChar).forEach(System.out::print);
            System.out.print("\n");

            System.out.print("|");
            Stream.generate(() -> " ").limit(((blankSpacesNumber / 2)) -1).forEach(System.out::print);
            System.out.print(text);
            Stream.generate(() -> " ").limit(((blankSpacesNumber / 2) -1)).forEach(System.out::print);
            System.out.print("|");
            System.out.print("\n");

            Stream.generate(() -> borderChar).limit(nbLineChar).forEach(System.out::print);
            System.out.print("\n");

        } else {

            Stream.generate(() -> borderChar).limit(nbLineChar).forEach(System.out::print);
            System.out.print("\n");

            System.out.print("|");
            Stream.generate(() -> " ").limit(((blankSpacesNumber / 2) -1)).forEach(System.out::print);
            System.out.print(text);
            Stream.generate(() -> " ").limit(((blankSpacesNumber / 2))).forEach(System.out::print);
            System.out.print("|");
            System.out.print("\n");


            Stream.generate(() -> borderChar).limit(nbLineChar).forEach(System.out::print);



        }

        System.out.print("\n");

    }



}
