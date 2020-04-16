package RISK.Player;

import RISK.Territory.Territory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class PlayerJSON extends Player<JSONObject> {

    public PlayerJSON() {}

    //    "playerID": int
    //    "name": string
    //    "status": int
    //    "food": int
    //    "tech": int
    public PlayerJSON(JSONObject playerJO) {
        super(playerJO.getInt("playerID"),
                playerJO.getString("name"));
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

    @Override
    public Player getCopy() {
        PlayerJSON playerCopy = new PlayerJSON();

        playerCopy.playerID = this.playerID;
        playerCopy.name = this.name;
        playerCopy.status = this.status;
        playerCopy.tech = this.tech;
        playerCopy.food = this.food;
        playerCopy.tech = this.tech;
        playerCopy.terrMap = new HashMap<>();

        return playerCopy;
    }
}
