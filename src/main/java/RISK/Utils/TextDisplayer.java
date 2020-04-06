package RISK.Utils;


/*
    Writer & Maintainer: lyt
    TextDisplayer: this class is used for display the game in text

 */

import RISK.Army.ArmyRO;
import RISK.Game.GameDisplayable;
import RISK.Player.PlayerRO;
import RISK.Territory.TerritoryRO;

import java.util.ArrayList;
import java.util.HashMap;
//
//public class TextDisplayer implements Displayer {
//    public void displayTerr(HashMap<Integer, TerritoryRO> territories) {
//        System.out.println("===============Territory Information===============\n");
//        for (TerritoryRO territory : territories.values()) {
//            System.out.print("Name: ");
//            System.out.println(territory.getName());
//
//            System.out.print("Occupied by: ");
//            System.out.println(territory.getOwnerRO().getName());
//
//            System.out.print("Neighbors: ");
//            ArrayList<TerritoryRO> neighbors = territory.getNeighborListRO();
//            for (TerritoryRO t : neighbors) {
//                System.out.print(t.getName());
//                System.out.print(" ");
//            }
//            System.out.println("");
//
//            System.out.print("Defending Units: ");
//            System.out.println(territory.getOwnerArmyRO().getUnitListSize());
//
//            System.out.print("Attacked By: ");
//            ArrayList<ArmyRO> armyROS = territory.getAttackArmyListRO();
//            if (armyROS != null) {
//                for (ArmyRO armyRO : armyROS) {
//                    System.out.print(armyRO.getOwnerRO().getName());
//                    System.out.print("(");
//                    System.out.print(armyRO.getUnitListSize());
//                    System.out.print(" units) ");
//                }
//            }
//            System.out.println("\n");
//        }
//        System.out.println("========================END========================");
//    }
//
//    public void displayPlayer(HashMap<Integer, PlayerRO> playerROHashMap) {
//        System.out.println("================Player  Information================\n");
//        for (PlayerRO playerRO : playerROHashMap.values()) {
//            System.out.print("Name: ");
//            System.out.println(playerRO.getName());
//
//            System.out.print("Occupied Territories: ");
//            ArrayList<TerritoryRO> territoryROS = playerRO.getTerrListRO();
//            for (TerritoryRO territoryRO : territoryROS) {
//                System.out.print(territoryRO.getName());
//                System.out.print(" ");
//            }
//            System.out.println("\n");
//        }
//        System.out.println("========================END========================");
//    }
//
//
//    public void display(GameDisplayable game) {
//        //display the information of Territory
//        displayPlayer(game.getPlayerMapRO());
//        displayTerr(game.getTerrMapRO());
//    }
//
//    public void display(GameDisplayable game, String text) {
//        display(game);
//
//        System.out.print("IMPORTANT MESSAGE FROM SERVER: ");
//        System.out.println(text);
//    }
//}
