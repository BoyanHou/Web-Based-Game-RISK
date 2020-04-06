package RISK.Player;

import org.json.JSONArray;
import org.json.JSONObject;

public class PlayerJSON extends Player<JSONObject> {
    //    "playerID": int
    //    "name": string
    //    "status": int
    //    "food": int
    //    "tech": int
    public PlayerJSON(JSONObject playerJO) {
        super(playerJO.getInt("playerID"),
                playerJO.getString("name"),
                playerJO.getInt("status"));
        this.food = playerJO.getInt("food");
        this.tech = playerJO.getInt("tech");
    }

    @Override
    public JSONObject pton() {
        JSONObject playerJO = new JSONObject();
        playerJO.put("playerID", this.playerID);
        playerJO.put("name", this.name);
        playerJO.put("status", this.status);
        playerJO.put("food", this.food);
        playerJO.put("tech", this.tech);
        JSONArray terrMapJO = new JSONArray();
        for (int terrID : this.terrMap.keySet()) {
            terrMapJO.put(terrID);
        }
        playerJO.put("terrMap", terrMapJO);

        return playerJO;
    }

}
