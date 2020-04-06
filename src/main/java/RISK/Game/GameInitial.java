package RISK.Game;

import RISK.Army.Army;
import RISK.Player.Player;
import RISK.Territory.Territory;
import RISK.Unit.Soldier;
import RISK.Unit.Unit;
import RISK.Utils.TerritoryNames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GameInitial {

//    protected ArrayList<Territory> territories;
//    protected ArrayList<Player> players;
//    protected ArrayList<Army> armies;
//
//    public GameInitial() {
//
//        //initial all the territory
//        Territory narnia = new Territory(1, TerritoryNames.Narnia);
//        Territory midkemia = new Territory(2, TerritoryNames.Midkemia);
//        Territory oz = new Territory(3, TerritoryNames.Oz);
//
//        Territory gondor = new Territory(4, TerritoryNames.Gondor);
//        Territory mordor = new Territory(5, TerritoryNames.Mordor);
//        Territory hogwarts = new Territory(6, TerritoryNames.Hogwarts);
//
//        Territory elantris = new Territory(7, TerritoryNames.Elantris);
//        Territory scadrial = new Territory(8, TerritoryNames.Scadrial);
//        Territory roshar = new Territory(9, TerritoryNames.Roshar);
//
//        territories = new ArrayList<>(Arrays.asList(narnia, midkemia, oz, gondor, mordor, hogwarts, elantris, scadrial, roshar));
//
//        //initial all players
//        Player green = new Player(1, "Green");
//        Player red = new Player(2, "Red");
//        Player purple = new Player(3, "Purple");
//
//        players = new ArrayList<>(Arrays.asList(green, red, purple));
//
//        //set owner's territory
//        green.setTerrList(Arrays.asList(narnia, midkemia, oz));
//        red.setTerrList(Arrays.asList(gondor, mordor, hogwarts));
//        purple.setTerrList(Arrays.asList(elantris, scadrial, roshar));
//
//        //set territory's owner
//        setTerritoryOwner(players);
//
//
//        //initial army
//        HashMap<TerritoryNames, Integer> map = new HashMap<>();
//        map.put(TerritoryNames.Narnia, 10);
//        map.put(TerritoryNames.Midkemia, 12);
//        map.put(TerritoryNames.Oz, 8);
//
//        map.put(TerritoryNames.Gondor, 13);
//        map.put(TerritoryNames.Mordor, 14);
//        map.put(TerritoryNames.Hogwarts, 3);
//
//        map.put(TerritoryNames.Elantris, 6);
//        map.put(TerritoryNames.Scadrial, 5);
//        map.put(TerritoryNames.Roshar, 3);
//
//        armies = setOwnerArmy(territories, map);
//
//        //set neighbor
//        narnia.setNeighborList(Arrays.asList(midkemia, elantris));
//        midkemia.setNeighborList(Arrays.asList(narnia, elantris, scadrial, oz));
//        oz.setNeighborList(Arrays.asList(midkemia, scadrial, mordor, gondor));
//        gondor.setNeighborList(Arrays.asList(oz, mordor));
//        mordor.setNeighborList(Arrays.asList(gondor, oz, scadrial, hogwarts));
//        hogwarts.setNeighborList(Arrays.asList(mordor, scadrial, roshar));
//        elantris.setNeighborList(Arrays.asList(narnia, midkemia, scadrial, roshar));
//        scadrial.setNeighborList(Arrays.asList(elantris, midkemia, oz, mordor, hogwarts, roshar));
//        roshar.setNeighborList(Arrays.asList(elantris, scadrial, hogwarts));
//
//    }
//
//    public ArrayList<Army> setOwnerArmy(ArrayList<Territory> territories, HashMap<TerritoryNames, Integer> map) {
//        ArrayList<Army> armies = new ArrayList<>();
//        for (Territory territory : territories) {
//            TerritoryNames name = territory.getName();
//            int number = map.get(name);
//            Army army = new Army(makeUnits(number), territory.getOwner(), territory.getTerrID());
//            territory.setOwnerArmy(army);
//            armies.add(army);
//        }
//        return armies;
//    }
//
//    public void setTerritoryOwner(List<Player> players) {
//        for (Player player : players) {
//            for (Territory territory : player.getTerrList()) {
//                territory.setOwner(player);
//            }
//        }
//
//    }
//
//    public ArrayList<Unit> makeUnits(int number) {
//        ArrayList<Unit> units = new ArrayList<>();
//        while (number > 0) {
//            units.add(new Soldier());
//            number--;
//        }
//        return units;
//    }
//
//    public ArrayList<Territory> getTerritories() {
//        return territories;
//    }
//
//    public ArrayList<Player> getPlayers() {
//        return players;
//    }
//
//    public ArrayList<Army> getArmies() {
//        return armies;
//    }
}
