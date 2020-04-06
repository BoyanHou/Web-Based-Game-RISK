package RISK.Army;

import RISK.Unit.Soldier;
import RISK.Unit.Unit;
import RISK.Unit.UnitLevelException;
import RISK.Utils.IntFormatException;
import RISK.Utils.NumUtils;
import org.json.JSONObject;
import java.util.HashMap;

public class ArmyJSON extends Army<JSONObject> {
    public ArmyJSON(int armyID) {
        super(armyID);
    }

    /*{ "owner": the id of the owner player,
        "armyID": int
        "unitMap": JSONObject <String:int>
      }
    */
    // build the primitive fields from JSONObject: armyID, unitMap
    public ArmyJSON(JSONObject armyJO) throws UnitLevelException {
        super(armyJO.getInt("armyID"));
        JSONObject unitMapJO = armyJO.getJSONObject("unitMap");
        this.unitMap = new HashMap<>();
        for (String levelStr : unitMapJO.keySet()) {
            int level = 0;
            try {
                level = NumUtils.strToInt(levelStr);
            } catch(IntFormatException e) {
                // do nothing, impossible to happen
            }
            int num = unitMapJO.getInt(levelStr);
            // create units for this army
            for (int i = 0; i < num; i++) {
                Unit unit = new Soldier(level);
                this.addUnit(unit);
            }
        }
    }

    // pton packs all fields into "network" form
    @Override
    public JSONObject pton() {
        JSONObject armyJO = new JSONObject();
        armyJO.put("owner", this.getOwnerRO().getPlayerID());
        armyJO.put("armyID", this.getArmyID());
        JSONObject unitMapJO = new JSONObject();
        for (int level : this.getUnitMap().keySet()) {
            int num = this.getUnitMap().get(level).size();
            unitMapJO.put(Integer.toString(level), num);
        }
        armyJO.put("unitMap", unitMapJO);

        return armyJO;
    }
}
