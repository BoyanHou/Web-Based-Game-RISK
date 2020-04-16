package RISK.Territory;

import RISK.Player.Player;
import RISK.Player.PlayerJSON;
import RISK.Unit.UnitLevelException;
import RISK.Utils.IntFormatException;
import RISK.Utils.NumUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class TerrJSON extends Territory<JSONObject> {

    public TerrJSON () {}

    public TerrJSON (JSONObject terrJO) {
        super(terrJO.getInt("terrID"), terrJO.getString("name"));
        this.food = terrJO.getInt("food");
        this.tech = terrJO.getInt("tech");
        this.size = terrJO.getInt("size");
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
    }

    @Override
    public JSONObject pton()  {
        JSONObject terrJO = new JSONObject();

        terrJO.put("terrID", this.terrID);
        terrJO.put("name", this.name);
        terrJO.put("size", this.size);
        terrJO.put("food", this.food);
        terrJO.put("tech", this.tech);

        terrJO.put("owner", this.owner.getPlayerID());
        terrJO.put("ownerArmy", this.ownerArmy.getArmyID());

        JSONArray neighborMapJA = new JSONArray();
        for (int terrID : this.neighborMap.keySet()) {
            neighborMapJA.put(terrID);
        }
        terrJO.put("neighborMap", neighborMapJA);

        JSONArray attackArmyMapJA = new JSONArray();
        for (int armyID : this.attackArmyMap.keySet()) {
            attackArmyMapJA.put(armyID);
        }
        terrJO.put("attackArmyMap", attackArmyMapJA);

        JSONObject unitGenMapJO = new JSONObject();
        for (int level : this.unitGenMap.keySet()) {
            int num = this.unitGenMap.get(level);
            unitGenMapJO.put(Integer.toString(level), num);
        }
        terrJO.put("unitGenMap", unitGenMapJO);

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

        terrCopy.owner = this.owner.getCopy();
        terrCopy.ownerArmy = this.ownerArmy.getCopy();
        terrCopy.neighborMap = new HashMap<>();
        terrCopy.attackArmyMap = new HashMap<>();
        terrCopy.unitGenMap = new HashMap<>();
        terrCopy.spyMap = new HashMap<>();

        return terrCopy;
    }

}
