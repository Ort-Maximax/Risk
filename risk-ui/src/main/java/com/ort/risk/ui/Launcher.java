package com.ort.risk.ui;

import com.ort.risk.model.Game;
import com.ort.risk.ui.console.ConsoleLauncher;
import com.ort.risk.ui.javafx.UILauncher;

/**
 * @author CS
 * Launcher of the app
 */
public class Launcher {

    public static void main(String[] args) {
        Game gameObj = Game.getInstance();

        if(args.length > 0){
            switch (args[0]) {
                //Mode console
                case "-c":
                    gameObj.setExMode(ExecMode.CONSOLE.value());
                    ConsoleLauncher.run();
                    break;
                //Mode random
                case "-r":
                    gameObj.setExMode(ExecMode.RANDOM.value());
                    ConsoleLauncher.run();
                    break;
                //Mode graphique
                case "-g":
                	gameObj.setExMode(ExecMode.GUI.value());
                    UILauncher.run(args);
                    break;
                default:
                    gameObj.setExMode(ExecMode.CONSOLE.value());
                    ConsoleLauncher.run();
            }
        } else {
            gameObj.setExMode(ExecMode.CONSOLE.value());
            ConsoleLauncher.run();
        }

    }

    public enum ExecMode {
        CONSOLE(0, "-c"),
        RANDOM(1, "-r"),
        GUI(2, "-g");

        private final int value;
        private final String consoleArg;

        ExecMode(int value, String consoleArg) {

            this.value = value;
            this.consoleArg = consoleArg;
        }

        public int value() {
            return this.value;
        }
        public String arg() {
            return this.consoleArg;
        }

    }


}
