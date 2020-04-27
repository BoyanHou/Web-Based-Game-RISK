package RISK.GUI;

import RISK.Army.Army;
import RISK.Game.GameInitial;
import RISK.Player.Player;
import RISK.Territory.Territory;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
public class TerritoryBlockInitial {
	private HashMap<String, TerritoryBlock> territoryBlockMap;

	public TerritoryBlockInitial() {
		territoryBlockMap = new HashMap<String,TerritoryBlock>();
	}
	
	public HashMap<String, TerritoryBlock> getTerritoryBlockMap(){
			
				///////////////////////initialize territoryBlocks////////////////////////////
				// terrBlck1
				TerritoryBlock terrBlck1 = new TerritoryBlock();

				ArrayList<Block> terr1 = new ArrayList<Block>();
				for (int i = 15; i <= 23; i ++) {
					for (int j = 4; j <= 6; j++) {
						Block blockUnit = new Block(j*10, i*10);
						terr1.add(blockUnit);
					}
				}	
				
				for (int i = 17; i <= 23; i ++) {
					for (int j = 7; j <= 9; j++) {
						Block blockUnit = new Block(j*10, i*10);
						terr1.add(blockUnit);
					}
				}	
				terrBlck1.setBlocks(terr1);

				// terrBlck2
				TerritoryBlock terrBlck2 = new TerritoryBlock();
				
				ArrayList<Block> terr2 = new ArrayList<Block>();
				for (int i = 10; i <= 16; i ++) {
					for (int j = 7; j <= 10; j++) {
						Block BlockUnit = new Block(j*10, i*10);
						terr2.add(BlockUnit);
					}
				}	
				for (int i = 10; i <= 11; i ++) {
					for (int j = 11; j <= 12; j++) {
						Block BlockUnit = new Block(j*10, i*10);
						terr2.add(BlockUnit);
					}
				}
				for (int i = 14; i <= 16; i ++) {
					for (int j = 11; j <= 12; j++) {
						Block BlockUnit = new Block(j*10, i*10);
						terr2.add(BlockUnit);
					}
				}
				
				terrBlck2.setBlocks(terr2);
				
				// terrBlock3
				TerritoryBlock terrBlck3 = new TerritoryBlock();
				
				ArrayList<Block> terr3 = new ArrayList<Block>();
				for (int i = 12; i <= 13; i ++) {
					for (int j = 11; j <= 21; j++) {
						Block BlockUnit = new Block(j*10, i*10);
						terr3.add(BlockUnit);
					}
				}	
				for (int i = 9; i <= 11; i ++) {
					for (int j = 13; j <= 21; j++) {
						Block BlockUnit = new Block(j*10, i*10);
						terr3.add(BlockUnit);
					}
				}	
				for (int i = 7; i <= 8; i ++) {
					for (int j = 18; j <= 22; j++) {
						Block BlockUnit = new Block(j*10, i*10);
						terr3.add(BlockUnit);
					}
				}
				for (int i = 5; i <= 6; i ++) {
					for (int j = 21; j <= 22; j++) {
						Block BlockUnit = new Block(j*10, i*10);
						terr3.add(BlockUnit);
					}
				}
				
				terrBlck3.setBlocks(terr3);
				
				// terrBlock4
				TerritoryBlock terrBlck4 = new TerritoryBlock();
				
				ArrayList<Block> terr4 = new ArrayList<Block>();
				for (int i = 23; i <= 30; i ++) {
					for (int j = 3; j <= 4; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr4.add(BlockUnit);
					}
				}	
				for (int i = 23; i <= 28; i ++) {
					for (int j = 5; j <= 13; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr4.add(BlockUnit);
					}
				}	
				
				for (int i = 22; i <= 22; i ++) {
					for (int j = 9; j <= 16; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr4.add(BlockUnit);
					}
				}	
				for (int i = 21; i <= 21; i ++) {
					for (int j = 14; j <= 16; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr4.add(BlockUnit);
					}
				}
				for (int i = 29; i <= 30; i ++) {
					for (int j = 8; j <= 13; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr4.add(BlockUnit);
					}
				}	
				
				for (int i = 31; i <= 36; i ++) {
					for (int j = 4; j <= 6; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr4.add(BlockUnit);
					}
				}			
				terrBlck4.setBlocks(terr4);
				
				
				// terrBlock5
				TerritoryBlock terrBlck5 = new TerritoryBlock();
				
				ArrayList<Block> terr5 = new ArrayList<Block>();
				for (int i = 29; i <= 30; i ++) {
					for (int j = 5; j <= 7; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr5.add(BlockUnit);
					}
				}	
				
				for (int i = 31; i <= 39; i ++) {
					for (int j = 7; j <= 7; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr5.add(BlockUnit);
					}
				}
				
				for (int i = 31; i <= 36; i ++) {
					for (int j = 8; j <= 8; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr5.add(BlockUnit);
					}
				}
				
				for (int i = 31; i <= 48; i ++) {
					for (int j = 9; j <= 13; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr5.add(BlockUnit);
					}
				}	
				for (int i = 41; i <= 48; i ++) {
					for (int j = 14; j <= 15; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr5.add(BlockUnit);
					}
				}	
				
				terrBlck5.setBlocks(terr5);
				
				// terrBlock6
				TerritoryBlock terrBlck6 = new TerritoryBlock();
				
				ArrayList<Block> terr6 = new ArrayList<Block>();
				for (int i = 41; i <= 48; i ++) {
					for (int j = 4; j <= 4; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr6.add(BlockUnit);
					}
				}	
				
				for (int i = 37; i <= 48; i ++) {
					for (int j = 5; j <= 6; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr6.add(BlockUnit);
					}
				}	
				for (int i = 44; i <= 48; i ++) {
					for (int j = 7; j <= 7; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr6.add(BlockUnit);
					}
				}
				terrBlck6.setBlocks(terr6);
				
				// terrBlock7
				TerritoryBlock terrBlck7 = new TerritoryBlock();
				
				ArrayList<Block> terr7 = new ArrayList<Block>();
				for (int i = 49; i <= 54; i ++) {
					for (int j = 6; j <= 12; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr7.add(BlockUnit);
					}
				}	
				
				for (int i = 40; i <= 53; i ++) {
					for (int j = 7; j <= 7; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr7.add(BlockUnit);
					}
				}
				
				for (int i = 37; i <= 48; i ++) {
					for (int j = 8; j <= 8; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr7.add(BlockUnit);
					}
				}		
				
				terrBlck7.setBlocks(terr7);
				
				// terrBlock8
				TerritoryBlock terrBlck8 = new TerritoryBlock();
				
				ArrayList<Block> terr8 = new ArrayList<Block>();
				for (int i = 55; i <= 58; i ++) {
					for (int j = 9; j <= 16; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr8.add(BlockUnit);
					}
				}	
				for (int i = 54; i <= 54; i ++) {
					for (int j = 11; j <= 15; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr8.add(BlockUnit);
					}
				}
				
				for (int i = 53; i <= 53; i ++) {
					for (int j = 13; j <= 13; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr8.add(BlockUnit);
					}
				}		
				
				
				terrBlck8.setBlocks(terr8);
				
				// terrBlock9
				TerritoryBlock terrBlck9 = new TerritoryBlock();
				
				ArrayList<Block> terr9 = new ArrayList<Block>();
				for (int i = 49; i <= 52; i ++) {
					for (int j = 13; j <= 15; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr9.add(BlockUnit);
					}
				}
				for (int i = 53; i <= 53; i ++) {
					for (int j = 14; j <= 15; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr9.add(BlockUnit);
					}
				}
				
				for (int i = 41; i <= 54; i ++) {
					for (int j = 16; j <= 19; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr9.add(BlockUnit);
					}
				}
				for (int i = 41; i <= 52; i ++) {
					for (int j = 20; j <= 20; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr9.add(BlockUnit);
					}
				}
			   
				terrBlck9.setBlocks(terr9);
				
				// terrBlock10
				TerritoryBlock terrBlck10 = new TerritoryBlock();
				
				ArrayList<Block> terr10 = new ArrayList<Block>();
				for (int i = 41; i <= 55; i ++) {
					for (int j = 21; j <= 23; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr10.add(BlockUnit);
					}
				}
				for (int i = 41; i <= 53; i ++) {
					for (int j = 24; j <= 26; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr10.add(BlockUnit);
					}
				}
				for (int i = 41; i <= 51; i ++) {
					for (int j = 27; j <= 29; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr10.add(BlockUnit);
					}
				}
				for (int i = 41; i <= 49; i ++) {
					for (int j = 30; j <= 31; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr10.add(BlockUnit);
					}
				}
				for (int i = 41; i <= 47; i ++) {
					for (int j = 32; j <= 34; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr10.add(BlockUnit);
					}
				}

				terrBlck10.setBlocks(terr10);
				
				// terrBlock11
				TerritoryBlock terrBlck11 = new TerritoryBlock();
				
				ArrayList<Block> terr11 = new ArrayList<Block>();
				for (int i = 27; i <= 33; i ++) {
					for (int j = 27; j <= 30; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr11.add(BlockUnit);
					}
				}
				for (int i = 29; i <= 33; i ++) {
					for (int j = 31; j <= 31; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr11.add(BlockUnit);
					}
				}
				for (int i = 29; i <= 40; i ++) {
					for (int j = 32; j <= 35; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr11.add(BlockUnit);
					}
				}
				for (int i = 34; i <= 40; i ++) {
					for (int j = 36; j <= 37; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr11.add(BlockUnit);
					}
				}
				
				terrBlck11.setBlocks(terr11);
				
				
				// terrBlock12
				TerritoryBlock terrBlck12 = new TerritoryBlock();
				
				ArrayList<Block> terr12 = new ArrayList<Block>();
				for (int i = 23; i <= 40; i ++) {
					for (int j = 14; j <= 16; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr12.add(BlockUnit);
					}
				}
				for (int i = 21; i <= 40; i ++) {
					for (int j = 17; j <= 22; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr12.add(BlockUnit);
					}
				}
				for (int i = 27; i <= 33; i ++) {
					for (int j = 23; j <= 23; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr12.add(BlockUnit);
					}
				}
				terrBlck12.setBlocks(terr12);
				
				// terrBlock13
				TerritoryBlock terrBlck13 = new TerritoryBlock();
				
				ArrayList<Block> terr13 = new ArrayList<Block>();
				for (int i = 13; i <= 20; i ++) {
					for (int j = 14; j <= 16; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr13.add(BlockUnit);
					}
				}
				
				for (int i = 10; i <= 20; i ++) {
					for (int j = 17; j <= 26; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr13.add(BlockUnit);
					}
				}
				
				for (int i = 12; i <= 20; i ++) {
					for (int j = 24; j <= 26; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr13.add(BlockUnit);
					}
				}
				for (int i = 14; i <= 20; i ++) {
					for (int j = 27; j <= 29; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr13.add(BlockUnit);
					}
				}
				for (int i = 16; i <= 20; i ++) {
					for (int j = 30; j <= 32; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr13.add(BlockUnit);
					}
				}
				
				for (int i = 18; i <= 20; i ++) {
					for (int j = 33; j <= 35; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr13.add(BlockUnit);
					}
				}

				terrBlck13.setBlocks(terr13);
				
				// terrBlock14
				TerritoryBlock terrBlck14 = new TerritoryBlock();
				
				ArrayList<Block> terr14 = new ArrayList<Block>();
				for (int i = 21; i <= 26; i ++) {
					for (int j = 23; j <= 23; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr14.add(BlockUnit);
					}
				}
				for (int i = 34; i <= 40; i ++) {
					for (int j = 23; j <= 23; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr14.add(BlockUnit);
					}
				}
				for (int i = 21; i <= 40; i ++) {
					for (int j = 24; j <= 26; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr14.add(BlockUnit);
					}
				}
				for (int i = 21; i <= 26; i ++) {
					for (int j = 27; j <= 30; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr14.add(BlockUnit);
					}
				}
				for (int i = 34; i <= 40; i ++) {
					for (int j = 27; j <= 31; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr14.add(BlockUnit);
					}
				}
				
				terrBlck14.setBlocks(terr14);
				
				// terrBlock15
				TerritoryBlock terrBlck15 = new TerritoryBlock();
				
				ArrayList<Block> terr15 = new ArrayList<Block>();
				for (int i = 23; i <= 28; i ++) {
					for (int j = 31; j <= 37; j++) {
						Block BlockUnit = new Block(i*10, j*10);
						terr15.add(BlockUnit);
					}
				}
				
				terrBlck15.setBlocks(terr15);
				
				territoryBlockMap.put("Mango",terrBlck1);
				territoryBlockMap.put("Apple",terrBlck2);
				territoryBlockMap.put("Narnia",terrBlck3);
				territoryBlockMap.put("Midkemia",terrBlck4);
				territoryBlockMap.put("Oz",terrBlck5);
				territoryBlockMap.put("Banana",terrBlck6);
				territoryBlockMap.put("Gondor",terrBlck7);
				territoryBlockMap.put("Pear",terrBlck8);
				territoryBlockMap.put("Mordor",terrBlck9);
				territoryBlockMap.put("Hogwarts",terrBlck10);
				territoryBlockMap.put("Peach",terrBlck11);
				territoryBlockMap.put("Scadrial",terrBlck12);
				territoryBlockMap.put("Elantris",terrBlck13);
				territoryBlockMap.put("Roshar",terrBlck14);
				territoryBlockMap.put("Cherry",terrBlck15);


				//set all the fog and spy position
				terrBlck1.setFogPos(new Point(10, 40));
				terrBlck1.setSpyPos(new Point(40, 40));


				

		
		return territoryBlockMap;
		
	}

	// get the position of each territory
	public HashMap<String,Rectangle> getTerrNamePos(){
		HashMap<String,Rectangle> terrPosMap = new HashMap<>();
		int rectWidth = 80;
		int rectHeight = 30;

		terrPosMap.put("Mango",new Rectangle(49,202,rectWidth,rectHeight));
		terrPosMap.put("Apple",new Rectangle(82,148,rectWidth,rectHeight));
		terrPosMap.put("Narnia",new Rectangle(141,109,rectWidth,rectHeight));
		terrPosMap.put("Midkemia",new Rectangle(235,103,rectWidth,rectHeight));
		terrPosMap.put("Oz",new Rectangle(337,109,rectWidth,rectHeight));
		terrPosMap.put("Banana",new Rectangle(418,47,rectWidth,rectHeight));
		terrPosMap.put("Gondor",new Rectangle(497,76,rectWidth,rectHeight));
		terrPosMap.put("Pear",new Rectangle(551,119,rectWidth,rectHeight));
		terrPosMap.put("Mordor",new Rectangle(442,178,rectWidth,rectHeight));
		terrPosMap.put("Hogwarts",new Rectangle(448,258,rectWidth,rectHeight));
		terrPosMap.put("Peach",new Rectangle(326,340,rectWidth,rectHeight));
		terrPosMap.put("Scadrial",new Rectangle(272,169,rectWidth,rectHeight));
		terrPosMap.put("Elantris",new Rectangle(125,208,rectWidth,rectHeight));
		terrPosMap.put("Roshar",new Rectangle(260,247,rectWidth,rectHeight));
		terrPosMap.put("Cherry",new Rectangle(236,337,rectWidth,rectHeight));

		return terrPosMap;

	}


	// get the position of each spy
	public HashMap<String, Rectangle> getSpyPos(){
		HashMap<String,Rectangle> spyPosMap = new HashMap<>();
		int rectWidth = 80;
		int rectHeight = 30;

		spyPosMap.put("Mango",new Rectangle(59,187,rectWidth,rectHeight));
		spyPosMap.put("Apple",new Rectangle(87,133,rectWidth,rectHeight));
		spyPosMap.put("Narnia",new Rectangle(161,94,rectWidth,rectHeight));
		spyPosMap.put("Midkemia",new Rectangle(255,87,rectWidth,rectHeight));
		spyPosMap.put("Oz",new Rectangle(357,94,rectWidth,rectHeight));
		spyPosMap.put("Banana",new Rectangle(438,32,rectWidth,rectHeight));
		spyPosMap.put("Gondor",new Rectangle(497,61,rectWidth,rectHeight));
		spyPosMap.put("Pear",new Rectangle(561,104,rectWidth,rectHeight));
		spyPosMap.put("Mordor",new Rectangle(462,163,rectWidth,rectHeight));
		spyPosMap.put("Hogwarts",new Rectangle(468,243,rectWidth,rectHeight));
		spyPosMap.put("Peach",new Rectangle(356,325,rectWidth,rectHeight));
		spyPosMap.put("Scadrial",new Rectangle(292,154,rectWidth,rectHeight));
		spyPosMap.put("Elantris",new Rectangle(145,193,rectWidth,rectHeight));
		spyPosMap.put("Roshar",new Rectangle(280,233,rectWidth,rectHeight));
		spyPosMap.put("Cherry",new Rectangle(260,323,rectWidth,rectHeight));
		
		return spyPosMap;
	}

	// get the position of each spy number
	public HashMap<String, Rectangle> getSpyNumPos(){
		HashMap<String,Rectangle> spyPosNumMap = new HashMap<>();
		int rectWidth = 80;
		int rectHeight = 30;

		spyPosNumMap.put("Mango",new Rectangle(79,187,rectWidth,rectHeight));
		spyPosNumMap.put("Apple",new Rectangle(107,133,rectWidth,rectHeight));
		spyPosNumMap.put("Narnia",new Rectangle(181,94,rectWidth,rectHeight));
		spyPosNumMap.put("Midkemia",new Rectangle(275,87,rectWidth,rectHeight));
		spyPosNumMap.put("Oz",new Rectangle(377,94,rectWidth,rectHeight));
		spyPosNumMap.put("Banana",new Rectangle(458,32,rectWidth,rectHeight));
		spyPosNumMap.put("Gondor",new Rectangle(517,61,rectWidth,rectHeight));
		spyPosNumMap.put("Pear",new Rectangle(581,104,rectWidth,rectHeight));
		spyPosNumMap.put("Mordor",new Rectangle(482,163,rectWidth,rectHeight));
		spyPosNumMap.put("Hogwarts",new Rectangle(488,243,rectWidth,rectHeight));
		spyPosNumMap.put("Peach",new Rectangle(376,325,rectWidth,rectHeight));
		spyPosNumMap.put("Scadrial",new Rectangle(312,154,rectWidth,rectHeight));
		spyPosNumMap.put("Elantris",new Rectangle(165,193,rectWidth,rectHeight));
		spyPosNumMap.put("Roshar",new Rectangle(300,233,rectWidth,rectHeight));
		spyPosNumMap.put("Cherry",new Rectangle(280,323,rectWidth,rectHeight));
		
		return spyPosNumMap;
	}

	// get the position of each fog
	public HashMap<String, Rectangle> getFogPos(){
		HashMap<String,Rectangle> fogPosMap = new HashMap<>();
		int rectWidth = 80;
		int rectHeight = 30;

		fogPosMap.put("Mango",new Rectangle(39,187,rectWidth,rectHeight));
		fogPosMap.put("Apple",new Rectangle(67,133,rectWidth,rectHeight));
		fogPosMap.put("Narnia",new Rectangle(141,94,rectWidth,rectHeight));
		fogPosMap.put("Midkemia",new Rectangle(235,87,rectWidth,rectHeight));
		fogPosMap.put("Oz",new Rectangle(337,94,rectWidth,rectHeight));
		fogPosMap.put("Banana",new Rectangle(418,32,rectWidth,rectHeight));
		fogPosMap.put("Gondor",new Rectangle(477,61,rectWidth,rectHeight));
		fogPosMap.put("Pear",new Rectangle(541,104,rectWidth,rectHeight));
		fogPosMap.put("Mordor",new Rectangle(442,163,rectWidth,rectHeight));
		fogPosMap.put("Hogwarts",new Rectangle(448,243,rectWidth,rectHeight));
		fogPosMap.put("Peach",new Rectangle(336,325,rectWidth,rectHeight));
		fogPosMap.put("Scadrial",new Rectangle(272,154,rectWidth,rectHeight));
		fogPosMap.put("Elantris",new Rectangle(125,193,rectWidth,rectHeight));
		fogPosMap.put("Roshar",new Rectangle(260,233,rectWidth,rectHeight));
		fogPosMap.put("Cherry",new Rectangle(240,323,rectWidth,rectHeight));
		
		return fogPosMap;
	}















}
