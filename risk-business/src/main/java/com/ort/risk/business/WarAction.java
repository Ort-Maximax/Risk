package com.ort.risk.business;

import com.ort.risk.model.Game;
import com.ort.risk.model.Player;
import com.ort.risk.model.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * @author CS
 * Functions tied to the War action
 */
public class WarAction {
    public static Object[] Assault(Region atkRegion, Player atkPlayer, Region defRegion, int nbAttack, int nbDef){
        /**
         * 0 : Jet de dés de l'attaque
         * 1 : jet de dès de la defense
         * 2 : Victoire ou defaite
         * 3 : [ atkLoss, defLoss ]
         * 4 : Prise du territoire ?
         */
        Object[] result = new Object[5];
        Game gameObj = Game.getInstance();

        List<Integer> atkRolls = new ArrayList<Integer>();
        List<Integer> defRolls = new ArrayList<Integer>();

        //Throw as many dices as attacking troops
        for(int a = 1; a <= nbAttack; a++){
            int rand = (int) ((Math.random() * (6 - 1) + 1 ));
            atkRolls.add( rand );
        }

        //Throw as many dices as defending troops
        for(int d = 1; d <= nbDef; d++){
            int rand = (int) ((Math.random() * (6 - 1) + 1 ));
            defRolls.add( rand );
        }

        result[0] = atkRolls;
        result[1] = defRolls;


        //Sort the lists in descending order
        atkRolls.sort(Collections.reverseOrder());
        defRolls.sort(Collections.reverseOrder());

        //Compare the dice rolls
        int nbIter = Math.min(atkRolls.size(), defRolls.size());

        int atkLoss = 0;
        int defLoss = 0;

        int rollDef;
        int rollAtk;

        for(int i = 0; i < nbIter; i++){
            rollDef = defRolls.get(i);
            rollAtk = atkRolls.get(i);

            if(rollAtk > rollDef){
                defLoss++;
            }

            if(rollDef >= rollAtk){
                atkLoss++;
            }

        }

        Integer[] arrLoss = {atkLoss, defLoss};
        result[3] = arrLoss;

        if(atkLoss > defLoss){
            result[2] = -1;
        }
        if(defLoss > atkLoss){
            result[2] = 1;
        }
        if(defLoss == atkLoss){
            result[2] = 0;
        }

        //Apply the troops loss to the warring regions
        atkRegion.changeDeployedTroops(- atkLoss);

        if(defRegion.getDeployedTroops() - defLoss > 0){
            defRegion.changeDeployedTroops(- defLoss);
            result[4] = false;
        } else {
            // if there is no remaining troop on the defense region

            //The remaining of the attacking squad leave the attacking region
            atkRegion.changeDeployedTroops(nbAttack - atkLoss);

            //And go occupy the losing defending region

            // In case there are survivor from the attack
            if(nbAttack - atkLoss > 0) {
                defRegion.setDeployedTroops(nbAttack - atkLoss);
            } else {
                //If there is at least 2 troops remaining in the attacking region after the loss
                if(atkRegion.getDeployedTroops() >= 2){
                    atkRegion.changeDeployedTroops(-1);
                    defRegion.setDeployedTroops(1);
                } else {
                    //Should not happen EVER
                    defRegion.setDeployedTroops(1);
                }
            }

            gameObj.conquerRegion(atkPlayer, defRegion);
            result[4] = true;
        }



        return result;
    }
}
