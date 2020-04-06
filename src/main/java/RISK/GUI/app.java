package RISK.GUI;

import RISK.Army.Army;
import RISK.Game.GameInitial;
import RISK.Player.Player;
import RISK.Territory.Territory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

public class app extends JFrame {
    private static HashMap<Integer, Player> players;
    private static HashMap<Integer, Territory> territories;
    private static HashMap<Integer, Army> armies;
    private static String[] ownedTerrNames;

    private static JFrame frame;
    private static JPanel actionPanel;
    private static JPanel movePanel;
    private static JPanel attackPanel;
    private static JPanel upgradePanel;
    private static JPanel informationPanel;
    private static JPanel mapPanel;
    private static JPanel playerPanel;

    private static JPanel currentPanel;

    //Mark: the information that will be updated or kept track of
    //the playerPanel, the north one
    private static int playerID;
    private static JLabel foodLabel;
    private static JLabel techLabel;

    //the informationPanel
    private static JLabel details;
    private static JComboBox<String> choseTerrInfo;

    //the movePanel
    private static JLabel moveTerrFrom;
    private static JLabel moveTerrTo;
    private static JComboBox<String> choseMoveNum;

    //the attackPanel
    private static JLabel attackTerrFrom;
    private static JLabel attackTerrTo;
    private static JComboBox<String> choseAttachNum;

    //the upgradePanel
    private static JLabel armiesSituation;
    private static JComboBox<String> chooseUpgradeTerr;
    private static JComboBox<String> chooseUpgradeTo;

    //position parameter settings
    private static Dimension frameSize = new Dimension(1000, 800);

    private static Dimension mapPanelSize = new Dimension(650, 500);

    private static Dimension playerPanelSize = new Dimension(500, 100);
    private static Rectangle playerIDBounds = new Rectangle(50, 50, 150, 30);
    private static Rectangle foodPromptBounds = new Rectangle(200, 50, 80, 30);
    private static Rectangle foodLabelBounds = new Rectangle(280, 50, 50, 30);
    private static Rectangle techPromptBounds = new Rectangle(330, 50, 50, 30);
    private static Rectangle techLabelBounds = new Rectangle(380, 50, 50, 30);

    private static Dimension informationPanelSize = new Dimension(350, 500);
    private static Rectangle chooseTerrLabelBounds = new Rectangle(0, 50, 150, 30);
    private static Rectangle chooseTerrDropDownBounds = new Rectangle(0, 100, 200, 30);
    private static Rectangle detailsBounds = new Rectangle(50, 150, 200, 300);
    private static Rectangle displayButtonBounds = new Rectangle(200, 50, 100, 30);

    private static Dimension actionPanelSize = new Dimension(1000, 250);
    private static Rectangle chooseActionBounds = new Rectangle(50, 20,200, 30);
    private static Rectangle moveButtonBounds = new Rectangle(100, 50, 80, 30);
    private static Rectangle attackButtonBounds = new Rectangle(200, 50, 80, 30);
    private static Rectangle upgradeButtonBounds =new Rectangle(300, 50, 80, 30);
    private static Rectangle finishButtonBounds = new Rectangle(400, 50, 80, 30);

    private static Dimension movePanelSize = new Dimension(1000, 250);
    private static Rectangle moveFromPromptBounds = new Rectangle(50, 20, 50, 30);
    private static Rectangle moveFromBounds = new Rectangle(50, 70, 100, 30);
    private static Rectangle moveToPromptsBounds = new Rectangle(170, 20, 50, 30);
    private static Rectangle moveToBounds = new Rectangle(170, 70, 100, 30);
    private static Rectangle moveConfirmButton = new Rectangle(410, 20,150, 30);

    private static Dimension attackPanelSize = new Dimension(1000, 250);
    private static Rectangle attackFromPromptBounds = new Rectangle(50, 20, 50, 30);
    private static Rectangle attackFromBounds = new Rectangle(50, 70, 100, 30);
    private static Rectangle attackToPromptBounds = new Rectangle(170, 20, 50, 30);
    private static Rectangle attackToBounds = new Rectangle(170, 70, 100, 30);


    public app(HashMap<Integer, Player> p, HashMap<Integer, Territory> t, HashMap<Integer, Army> a, int id) {
        players = p;
        territories = t;
        armies = a;
        playerID = id;

        frame = new JFrame("RISK");
        frame.setSize(frameSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        actionPanel = new JPanel();
        setActionPanel();
        movePanel = new JPanel();
        setMovePanel();
        attackPanel = new JPanel();
        setAttackPanel();
        upgradePanel = new JPanel();
        setUpgradePanel();
        informationPanel = new JPanel();
        setInfoPanel();
        setMapPanel();
        playerPanel = new JPanel();
        setPlayerPanel();
        frame.setVisible(true);
    }

    //MARK: - draw the map
    private static void setMapPanel() {
        GameInitial game = new GameInitial();
        Territory territory = game.getTerritories().get(0);

        TerritoryBlock tb = new TerritoryBlock(territory);

        ArrayList<Block> blocks = new ArrayList<>();
        blocks.add(new Block(50, 50));
        tb.setBlocks(blocks);

        ArrayList<TerritoryBlock> territoryBlocks = new ArrayList<>();
        territoryBlocks.add(tb);
        //TODO the map
        mapPanel = new MapPanel(territoryBlocks);

        mapPanel.setLayout(null);
        mapPanel.setPreferredSize(mapPanelSize);
        mapPanel.setBackground(Color.white);
        mapPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                System.out.print("The postion of click: ");
                System.out.print(x);
                System.out.print(" ");
                System.out.println(y);

                if (currentPanel == movePanel) {
                    if (moveTerrFrom.getText().equals("")) {
                        moveTerrFrom.setText("(" + x + ", " + y + ")");
                    } else if (moveTerrTo.getText().equals("")) {
                        moveTerrTo.setText("(" + x + ", " + y + ")");
                    }
                }

                if (currentPanel == attackPanel) {
                    if (attackTerrFrom.getText().equals("")) {
                        attackTerrFrom.setText("(" + x + ", " + y + ")");
                    } else if (attackTerrTo.getText().equals("")) {
                        attackTerrTo.setText("(" + x + ", " + y + ")");
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e){
                //TODO when pressed, the territory highlighted
            }

            @Override
            public void mouseEntered(MouseEvent e){
            }

            @Override
            public void mouseReleased(MouseEvent e){
                //TODO when released, the territory change to normal
            }
        });
        frame.add(mapPanel, BorderLayout.CENTER);
    }

    //Mark: - setup the player info
    private static void setPlayerPanel() {
        playerPanel.setLayout(null);
        playerPanel.setPreferredSize(playerPanelSize);
        makeLabel(playerPanel, "Your ID: " + String.valueOf(playerID), playerIDBounds);

        makeLabel(playerPanel, "Food: ", foodPromptBounds);
        //TODO how to get the food, tech
        foodLabel = makeLabel(playerPanel, "2", foodLabelBounds);

        makeLabel(playerPanel, "Tech: ", techPromptBounds);
        techLabel = makeLabel(playerPanel, "3", techLabelBounds);

        frame.add(playerPanel, BorderLayout.NORTH);

    }


    //MARK: - SetUp information Display --------------------------------------------------------------------------------
    private static void setInfoPanel() {
        informationPanel.setLayout(null);
        informationPanel.setPreferredSize(informationPanelSize);
        makeLabel(informationPanel, "Choose a Territory:", chooseTerrLabelBounds);
        String[] territoryNames = getTerrNames(new ArrayList<>(territories.values()));
        choseTerrInfo = makeDropDown(informationPanel,territoryNames, chooseTerrDropDownBounds);
        details = makeLabel(informationPanel, "Details Information", detailsBounds);
        JButton button = makeButton(informationPanel, "Display", displayButtonBounds);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String)choseTerrInfo.getSelectedItem();
                String result = getInfo(selected);
                details.setText(result);
            }
        });
        frame.add(informationPanel, BorderLayout.EAST);
    }

    /*
    @param: territories: an arrayList of territories
    @return: String[]
    Make a String array of all given territory.
     */
    private static String[] getTerrNames(ArrayList<Territory> terres) {
        String[] names = new String[terres.size()];
        int index = 0;
        for (Territory territory: terres) {
            names[index] = territory.getName().toString();
            index++;
        }
        return names;
    }

    /*
    @param: name: the territory name
    @return: String
    Get the detail info of the selected name.
     */
    private static String getInfo(String name) {
        Territory territory = getTerr(name);
        if (territory == null) {
            return "Invalid Name";
        }
        StringBuilder sb = new StringBuilder();
        Player owner = territory.getOwner();
        sb.append("Owner: ");
        sb.append(owner.getName());
        sb.append("\n");

        Army army = territory.getOwnerArmy();
        sb.append("Defend by: ");
        sb.append("\n");
        return sb.toString();
    }

    /*
    @param: name: the territory name
    @return: Territory
    Return the territory with the corresponding name.
     */
    private static Territory getTerr(String name) {
        for (Territory territory: territories.values()) {
            if (territory.getName().toString().equals(name)) {
                return territory;
            }
        }
        return null;
    }


    //MARK: - SetUp actions --------------------------------------------------------------------------------------------
    private static void setActionPanel() {
        actionPanel.setLayout(null);
        actionPanel.setPreferredSize(actionPanelSize);

        makeLabel(actionPanel, "Choose your action", chooseActionBounds);

        // Creating button
        JButton moveButton = makeButton(actionPanel,"Move", moveButtonBounds);
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(actionPanel);
                frame.add(movePanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = movePanel;
                moveTerrFrom.setText("");
                moveTerrTo.setText("");
                //TODO set only all invalid from terr candidates into gray
            }
        });




        JButton attackButton = makeButton(actionPanel, "Attack", attackButtonBounds);
        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(actionPanel);
                frame.add(attackPanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = attackPanel;
                attackTerrFrom.setText("");
                attackTerrTo.setText("");
                //TODO set only all invalid from terr candidates into gray
            }
        });

        JButton updateButton = makeButton(actionPanel, "Update", upgradeButtonBounds);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(actionPanel);
                frame.add(upgradePanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = upgradePanel;
            }
        });

        JButton finishButton = makeButton(actionPanel, "Finsh", finishButtonBounds);
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO finish button
            }
        });
        currentPanel = actionPanel;
        frame.add(actionPanel, BorderLayout.SOUTH);
    }

    public static void setMovePanel() {

        movePanel.setLayout(null);
        movePanel.setPreferredSize(movePanelSize);

        makeLabel(movePanel, "From: ", moveFromPromptBounds);
        moveTerrFrom = makeLabel(movePanel, "", moveFromBounds);
        updateOwnerTerrNames();
        //choseMoveTerrFrom = makeDropDown(movePanel, ownedTerrNames, new Rectangle(50, 70, 100, 30));

        makeLabel(movePanel, "To", moveToPromptsBounds);
        moveTerrTo = makeLabel(movePanel, "", moveToBounds);
        //choseMoveTerrTo = makeDropDown(movePanel, ownedTerrNames, new Rectangle(170, 70, 100, 30));

        makeLabel(movePanel, "Number of Units", new Rectangle(290, 20, 50, 30));
        //TODO chosenNum
        String[] nums = {"1", "2"};
        choseMoveNum = makeDropDown(movePanel, nums, new Rectangle(290, 70, 50, 30));

        JButton moveButton = makeButton(movePanel, "Move", moveConfirmButton);
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(movePanel);
                frame.add(actionPanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = attackPanel;
                //TODO send message
            }
        });

        //TODO cancel button

    }

    /*
    @param: void
    @return: void
    Update the String[] of names of all territories owned by playerID
     */
    private static void updateOwnerTerrNames() {
        Player player = players.get(playerID);
        ArrayList<Territory> ownedTerritories = player.getTerrList();
        ownedTerrNames = getTerrNames(ownedTerritories);
    }

    public static void setAttackPanel() {
        attackPanel.setLayout(null);
        attackPanel.setPreferredSize(attackPanelSize);

        makeLabel(attackPanel, "From", attackFromPromptBounds);
        updateOwnerTerrNames();
        attackTerrFrom = makeLabel(attackPanel, "", attackFromBounds);

        makeLabel(attackPanel, "To", attackToPromptBounds);
        attackTerrTo = makeLabel(attackPanel, "", attackToBounds);

        //TODO number
        makeLabel(attackPanel, "Number of Units", new Rectangle(290, 20, 50, 30));
        String[] nums = {"1", "2"};
        choseAttachNum = makeDropDown(attackPanel, nums, new Rectangle(290, 70, 50, 30));

        JButton button = makeButton(attackPanel, "Attack", new Rectangle(410, 20,150, 30));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(attackPanel);
                frame.add(actionPanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = attackPanel;
                //TODO send
            }
        });

        //TODO cancel button
    }

    //TODO
    public static void setUpgradePanel() {
        upgradePanel.setLayout(null);
        upgradePanel.setPreferredSize(new Dimension(1000, 250));

        makeLabel(upgradePanel, "Your Armies: ", new Rectangle(50, 20, 100, 30));
        armiesSituation = makeLabel(upgradePanel, "current", new Rectangle(60, 60, 300, 30));

        makeLabel(upgradePanel, "Upgrade", new Rectangle(50, 110, 100, 30));
        String[] upgradeString = {"1", "2", "3"};
        makeDropDown(upgradePanel, upgradeString, new Rectangle(170, 110, 100, 30));

        makeLabel(upgradePanel, "to", new Rectangle(290, 110, 50, 30));
        String[] toString =  {"1", "2", "3"};
        makeDropDown(upgradePanel, toString, new Rectangle(360, 110, 100, 30));

        JButton makeUpgradeButton = makeButton(upgradePanel, "Upgrade", new Rectangle(480, 110, 100, 30));
        makeUpgradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(upgradePanel);
                frame.add(actionPanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = attackPanel;
                //TODO send
            }
        });
        //TODO cancel button
    }


    /*
    @param: panel: where the button should be added
            title: the message will be put on the button
            position: the position of the button
    @return: JButton
    Make a button and put it in the given panel.
     */
    private static JButton makeButton(JPanel panel, String title, Rectangle position) {
        JButton button = new JButton(title);
        button.setBounds(position);
        panel.add(button);
        return button;
    }

    /*
    @param: panel: where the label should be added
            title: the message will be put on the label
            position: the position of the label
    @return: JLabel
    Make a label and put it in the given panel.
     */
    private static JLabel makeLabel(JPanel panel, String title, Rectangle position) {
        JLabel label = new JLabel(title);
        label.setBounds(position);
        panel.add(label);
        return label;
    }

    /*
    @param: panel: where to put
            lst: the list of string displayed in the dropdown
            position: the position of dropdown
    @return: void
    Make a dropdown and put it in the given panel.
     */
    private static JComboBox<String> makeDropDown(JPanel panel, String[] lst, Rectangle position) {
        JComboBox<String> dropDownList = new JComboBox<>(lst);
        dropDownList.setBounds(position);
        panel.add(dropDownList);
        return dropDownList;
    }


    public static void main(String[] args) {
        GameInitial gameInitial = new GameInitial();
        HashMap<Integer, Player> playerHashMap = new HashMap<>();
        HashMap<Integer, Territory> territoryHashMap = new HashMap<>();
        HashMap<Integer, Army> armyHashMap = new HashMap<>();
        for (Player player: gameInitial.getPlayers()) {
            playerHashMap.put(player.getPlayerID(), player);
        }
        for (Territory territory: gameInitial.getTerritories()) {
            territoryHashMap.put(territory.getTerrID(), territory);
        }
        for (Army army: gameInitial.getArmies()) {
            armyHashMap.put(army.getArmyID(), army);
        }
        new app(playerHashMap, territoryHashMap, armyHashMap, 1);
    }

}

