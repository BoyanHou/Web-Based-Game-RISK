package RISK.Unit;

public class UnitLevelMapper {
    public static String mapName(int level) throws UnitLevelException{
        switch(level){
            case 0:
                return "Lv0_Unit";
            case 1:
                return "Lv1_Unit";
            case 2:
                return "Lv2_Unit";
            case 3:
                return "Lv3_Unit";
            case 4:
                return "Lv4_Unit";
            case 5:
                return "Lv5_Unit";
            case 6:
                return "Lv6_Unit";

            default:
                throw new UnitLevelException("No such unit level: " + level);
        }
    }

    public static int mapBonus(int level) throws UnitLevelException{
        switch(level){
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 3;
            case 3:
                return 5;
            case 4:
                return 8;
            case 5:
                return 11;
            case 6:
                return 15;

            default:
                throw new UnitLevelException("No such unit level: " + level);
        }
    }

    public static int mapCost(int level) throws UnitLevelException{
        switch(level){
            case 0:
                return 0;
            case 1:
                return 3;
            case 2:
                return 11;
            case 3:
                return 30;
            case 4:
                return 55;
            case 5:
                return 90;
            case 6:
                return 140;

            default:
                throw new UnitLevelException("No such unit level: " + level);
        }
    }

}
