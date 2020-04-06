package RISK.Territory;

import RISK.Utils.IntFormatException;
import RISK.Utils.NumUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class TerrJSON extends Territory<JSONObject> {
    /*
  { "Territory": { "terrID": int, id of the territory,
                   "name": String, the Territory Name,
                   "unitGenMap": JSONObject <String:int>
                   "food": int
                   "tech": int
                   "size":int
                  }
      }
   */
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
    public JSONObject pton() {
        JSONObject terrJO = new JSONObject();
        terrJO.put("terrID", this.terrID);
        terrJO.put("owner", this.owner.getPlayerID());
        terrJO.put("ownerArmy", this.ownerArmy.getArmyID());
        terrJO.put("name", this.name);
        terrJO.put("food", this.food);
        terrJO.put("tech", this.food);
        terrJO.put("size", this.size);
        JSONObject unitGenMapJO = new JSONObject();
        for (int level : this.unitGenMap.keySet()) {
            int num = this.unitGenMap.get(level);
            unitGenMapJO.put(Integer.toString(level), num);
        }
        terrJO.put("unitGenMap", unitGenMapJO);

        JSONArray attackArmyMapJA = new JSONArray();
        for (int armyID : this.attackArmyMap.keySet()) {
            attackArmyMapJA.put(armyID);
        }
        terrJO.put("attackArmyMap", attackArmyMapJA);

        JSONArray neighborMapJA = new JSONArray();
        for (int terrID : this.neighborMap.keySet()) {
            neighborMapJA.put(terrID);
        }
        terrJO.put("neighborMap", neighborMapJA);

        return terrJO;
    }

}
