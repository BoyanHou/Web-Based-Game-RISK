package RISK.Territory;

import RISK.Player.Player;
import RISK.Player.PlayerJSON;
import RISK.Unit.Spy;
import RISK.Unit.UnitLevelException;
import RISK.Utils.IntFormatException;
import RISK.Utils.NumUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TerrJSON extends Territory<JSONObject> {

    public TerrJSON () {}

    public TerrJSON (JSONObject terrJO) { // build some of the fields according to JSONObject
        //////////////////////////
        /// all non-object fields
        //////////////////////////
        super(terrJO.getInt("terrID"), terrJO.getString("name"));
        this.food = terrJO.getInt("food");
        this.tech = terrJO.getInt("tech");
        this.size = terrJO.getInt("size");
        this.outdated = false;   // by default not outdated
        this.fogged = terrJO.getBoolean("fogged");
        JSONObject unitGenMapJO = terrJO.getJSONObject("unitGenMap");
        this.unitGenMap = new HashMap<>();
        for (String levelStr : unitGenMapJO.keySet()) {
            int level = 0;
            try {
                level = NumUtils.strToInt(levelStr);
            } catch (IntFormatException e) {
                // do nothing
            }
            int num = unitGenMapJO.getInt(Integer.toString(level));
            this.unitGenMap.put(level, num);
        }

        //////////////////////////////
        /// one object field (spyMap)
        //////////////////////////////
        this.spyMap = new HashMap<>();
        JSONObject spyMapJO = terrJO.getJSONObject("spyMap");
        for (String ownerIDStr : spyMapJO.keySet()) {
            int ownerID = 0;
            try {
                ownerID = NumUtils.strToInt(ownerIDStr);
            } catch (IntFormatException e) {
                System.out.println(e.getMessage());  // this is impossible to happen
            }
            int num = spyMapJO.getInt(ownerIDStr);
            ArrayList<Spy> spyList = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                try {
                    spyList.add(new Spy());
                } catch (UnitLevelException e) {
                    System.out.println("Spy unit level exception: " + e.getMessage()); // impossible to happen
                }
            }
            this.spyMap.put(ownerID, spyList);
        }
    }

    @Override
    public JSONObject pton()  {
        JSONObject terrJO = new JSONObject();

        ///////////////////////
        /// non-object fields
        ///////////////////////
        terrJO.put("terrID", this.terrID);
        terrJO.put("name", this.name);
        terrJO.put("size", this.size);
        terrJO.put("food", this.food);
        terrJO.put("tech", this.tech);
        // !! do not pass outdated info !!
        terrJO.put("fogged", this.fogged);
        JSONObject unitGenMapJO = new JSONObject();
        for (int level : this.unitGenMap.keySet()) {
            int num = this.unitGenMap.get(level);
            unitGenMapJO.put(Integer.toString(level), num);
        }
        terrJO.put("unitGenMap", unitGenMapJO);

        /////////////////////
        /// object fields
        /////////////////////
        terrJO.put("owner", this.owner.getPlayerID());
        terrJO.put("ownerArmy", this.ownerArmy.getArmyID());

        JSONArray neighborMapJA = new JSONArray();
        for (int terrID : this.neighborMap.keySet()) {
            neighborMapJA.put(terrID);
        }
        terrJO.put("neighborMap", neighborMapJA);

        // no need to pass attack army map!!
//        JSONArray attackArmyMapJA = new JSONArray();
//        for (int armyID : this.attackArmyMap.keySet()) {
//            attackArmyMapJA.put(armyID);
//        }
//        terrJO.put("attackArmyMap", attackArmyMapJA);
        JSONObject spyMapJO = new JSONObject();
        for (int ownerID : this.spyMap.keySet()) {
            spyMapJO.put(Integer.toString(ownerID), this.spyMap.get(ownerID).size());
        }
        terrJO.put("spyMap", spyMapJO);

        return terrJO;
    }

    @Override
    public Territory getCopy() throws UnitLevelException {
        Territory terrCopy = new TerrJSON();
        terrCopy.terrID = this.terrID;
        terrCopy.name = this.name;
        terrCopy.size = this.size;
        terrCopy.food = this.food;
        terrCopy.tech = this.tech;
        terrCopy.outdated = true;  // !! territory copy is by default outdated
        terrCopy.fogged = this.fogged;
        terrCopy.unitGenMap = new HashMap<>();

        terrCopy.owner = this.owner.getCopy();
        terrCopy.ownerArmy = this.ownerArmy.getCopy();
        terrCopy.neighborMap = new HashMap<>();
        terrCopy.attackArmyMap = new HashMap<>();
        terrCopy.spyMap = new HashMap<>();

        return terrCopy;
    }

}
