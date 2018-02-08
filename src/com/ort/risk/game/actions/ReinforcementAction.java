package com.ort.risk.game.actions;
import com.ort.risk.model.Map;
import com.ort.risk.model.Player;
import com.ort.risk.model.Region;
import com.ort.risk.model.Zone;

import java.util.List;

/**
 * @author CS
 * Functions tied to the Reinforcement action
 */
public class ReinforcementAction {
    public static void execute(Region regionTo, Region regionFrom, int nbTroops){
        Object[] ret = new Object[5];

        //Move n troops from the regionFrom to regionTo
        //Make sure to leave behind at least one troop
        if(nbTroops < regionFrom.getDeployedTroops()){
            regionFrom.changeDeployedTroops( -nbTroops);
            regionTo.changeDeployedTroops(nbTroops);
        }
    }
}
