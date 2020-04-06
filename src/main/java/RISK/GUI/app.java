package RISK.GUI;

import RISK.Army.Army;
import RISK.ClassBuilder.ClassBuilder;
import RISK.ClassBuilder.ClassBuilderEvo2;
import RISK.ClientOperator.ClientOperationException;
import RISK.ClientOperator.ClientOperator;
import RISK.ClientOperator.ClientOperatorEvo2;
import RISK.Game.GameClient;
import RISK.Game.GameClientJSON;
import RISK.Order.OrderFactory;
import RISK.Order.OrderFactoryEvo2;
import RISK.Player.Player;
import RISK.Territory.Territory;
import RISK.Unit.Unit;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

public class app extends JFrame {
    private static ClientOperator<JSONObject> clientOperator;
    private static GameClient<JSONObject> gameClient;
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
    private static JOptionPane message;

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
    private static JList<String> choseMoveNums;

    //the attackPanel
    private static JLabel attackTerrFrom;
    private static JLabel attackTerrTo;
    private static JList<String> choseAttachNums;

    //the upgradePanel
    private static JLabel armiesSituation;
    private static JLabel upgradeTerr;
    private static JComboBox<String> chooseUpgradeFrom;
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
    private static Rectangle chooseActionBounds = new Rectangle(50, 20, 200, 30);
    private static Rectangle moveButtonBounds = new Rectangle(100, 50, 80, 30);
    private static Rectangle attackButtonBounds = new Rectangle(200, 50, 80, 30);
    private static Rectangle upgradeButtonBounds = new Rectangle(300, 50, 80, 30);
    private static Rectangle finishButtonBounds = new Rectangle(400, 50, 80, 30);

    private static Dimension movePanelSize = new Dimension(1000, 250);
    private static Rectangle moveFromPromptBounds = new Rectangle(50, 20, 50, 30);
    private static Rectangle moveFromBounds = new Rectangle(50, 70, 100, 30);
    private static Rectangle moveToPromptsBounds = new Rectangle(170, 20, 50, 30);
    private static Rectangle moveToBounds = new Rectangle(170, 70, 100, 30);
    private static Rectangle moveConfirmButton = new Rectangle(410, 20, 150, 30);

    private static Dimension attackPanelSize = new Dimension(1000, 250);
    private static Rectangle attackFromPromptBounds = new Rectangle(50, 20, 50, 30);
    private static Rectangle attackFromBounds = new Rectangle(50, 70, 100, 30);
    private static Rectangle attackToPromptBounds = new Rectangle(170, 20, 50, 30);
    private static Rectangle attackToBounds = new Rectangle(170, 70, 100, 30);


    public app(ClientOperator<JSONObject> co) {
        clientOperator = co;
        gameClient = clientOperator.gameClient;
        playerID = gameClient.getPlayerID();
        updateArrtibute();
        setFrame();
    }

    private static void updateArrtibute() {
        players = gameClient.getPlayerMap();
        territories = gameClient.getTerrMap();
        armies = gameClient.getArmyMap();
    }

    private static void setFrame() {
        frame = new JFrame("RISK");
        frame.setSize(frameSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        actionPanel = new JPanel();
        movePanel = new JPanel();
        attackPanel = new JPanel();
        upgradePanel = new JPanel();
        informationPanel = new JPanel();
        playerPanel = new JPanel();
        try {
            setActionPanel();
            setMovePanel();
            setAttackPanel();
            setUpgradePanel();
            setInfoPanel();
            setMapPanel();
            setPlayerPanel();
        } catch (ClientOperationException ce) {

        }
        frame.setVisible(true);
    }

    //MARK: - draw the map
    private static void setMapPanel() {
        //TODO the map
        TerritoryBlock tb = new TerritoryBlock(territories.get(0));
        Block b = new Block(50, 50);
        ArrayList<Block> bs = new ArrayList<>();
        bs.add(b);
        tb.setBlocks(bs);
        ArrayList<TerritoryBlock> tbs = new ArrayList<>();
        tbs.add(tb);
        mapPanel = new MapPanel(tbs);

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

                if (currentPanel == upgradePanel) {
                    if (upgradeTerr.getText().equals("")) {
                        upgradeTerr.setText("(" + x + ", " + y + ")");
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //TODO when pressed, the territory highlighted
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //TODO when released, the territory change to normal
            }
        });
        frame.add(mapPanel, BorderLayout.CENTER);
    }

    private static void updateMapPanel() {
        //TODO
    }

    //Mark: - setup the player info
    private static void setPlayerPanel() {
        playerPanel.setLayout(null);
        playerPanel.setPreferredSize(playerPanelSize);
        makeLabel(playerPanel, "Your ID: " + String.valueOf(playerID), playerIDBounds);

        makeLabel(playerPanel, "Food: ", foodPromptBounds);
        makeLabel(playerPanel, "Tech: ", techPromptBounds);
        updateArrtibute();

        frame.add(playerPanel, BorderLayout.NORTH);
    }

    /*
    Update the food and tech Label
     */
    private static void updatePlayerPanel() {
        int food = players.get(playerID).getFood();
        foodLabel = makeLabel(playerPanel, String.valueOf(food), foodLabelBounds);
        int tech = players.get(playerID).getTech();
        techLabel = makeLabel(playerPanel, String.valueOf(tech), techLabelBounds);
    }


    //MARK: - SetUp information Display --------------------------------------------------------------------------------
    private static void setInfoPanel() {
        informationPanel.setLayout(null);
        informationPanel.setPreferredSize(informationPanelSize);
        makeLabel(informationPanel, "Choose a Territory:", chooseTerrLabelBounds);
        String[] territoryNames = getTerrNames(new ArrayList<>(territories.values()));
        choseTerrInfo = makeDropDown(informationPanel, territoryNames, chooseTerrDropDownBounds);
        details = makeLabel(informationPanel, "Details Information", detailsBounds);
        JButton button = makeButton(informationPanel, "Display", displayButtonBounds);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) choseTerrInfo.getSelectedItem();
                updateArrtibute();
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
        for (Territory territory : terres) {
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
        for (Territory territory : territories.values()) {
            if (territory.getName().toString().equals(name)) {
                return territory;
            }
        }
        return null;
    }


    //MARK: - SetUp actions --------------------------------------------------------------------------------------------
    private static void setActionPanel() throws ClientOperationException {
        actionPanel.setLayout(null);
        actionPanel.setPreferredSize(actionPanelSize);

        makeLabel(actionPanel, "Choose your action", chooseActionBounds);

        // Creating button
        JButton moveButton = makeButton(actionPanel, "Move", moveButtonBounds);
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
                upgradeTerr.setText("");
                //TODO set only all invalid from terr candidates into gray
            }
        });

        JButton finishButton = makeButton(actionPanel, "Finsh", finishButtonBounds);
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Waiting");
                try {
                    String message = clientOperator.listenForUpdates();
                    //    "LOSE": player has lost the game --> should decide & call willAudit() function to inform server, to audit or not
                    //    "CONTINUE": player can proceed to next round
                    //     otherwise: someone has won, print this String and exit game
                    switch (message) {
                        case "LOSE":
                            try {
                                clientOperator.AuditOrNot("YES");
                            } catch (ClientOperationException ce) {

                            }
                            break;
                        case "CONTINUE": {
                            updateArrtibute();
                            break;
                        }
                        default:
                            JOptionPane.showMessageDialog(frame, message);
                            //TODO exit
                    }
                } catch (ClientOperationException ce) {

                }
            }
        });
        currentPanel = actionPanel;
        frame.add(actionPanel, BorderLayout.SOUTH);
    }

    public static void setMovePanel() {

        movePanel.setLayout(null);
        movePanel.setPreferredSize(movePanelSize);

        makeLabel(movePanel, "From: ", moveFromPromptBounds);
        updateOwnerTerrNames();

        makeLabel(movePanel, "To", moveToPromptsBounds);

        makeLabel(movePanel, "Units", new Rectangle(290, 20, 50, 30));

        upgradeMovePanel();

        JButton moveButton = makeButton(movePanel, "Move", moveConfirmButton);
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(movePanel);
                frame.add(actionPanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = attackPanel;
                try {
                    // move order: enter "move" for orderType
                    //   "fromTerrName" : "XXX"
                    //   "toTerrName" : "XXX"
                    //   "2":"1" <-- level2 units: 1
                    //   "5":"3" <-- level5 units: 3
                    ArrayList<String> selected = new ArrayList<>(choseMoveNums.getSelectedValuesList());
                    HashMap<String, String> moveOrders = new HashMap<>();
                    moveOrders.put("fromTerrName", moveTerrFrom.getText());
                    moveOrders.put("toTerrName", moveTerrTo.getText());
                    HashMap<String, Integer> orders = count(selected);
                    for (String key: orders.keySet()) {
                        moveOrders.put(key, String.valueOf(orders.get(key)));
                        System.out.print("MoveOrder: " + key + ": " + String.valueOf(orders.get(key)));
                    }
                    clientOperator.makeOrder("move", moveOrders);
                    updateArrtibute();
                } catch (ClientOperationException ce) {

                }
            }
        });

        //TODO cancel button

    }

    private static HashMap<String, Integer> count(ArrayList<String> commands) {
        HashMap<String, Integer> results = new HashMap<>();
        for(String command: commands) {
            String[] units = command.split(": ");
            String level = units[1];
            if (results.containsKey(level)) {
                results.replace(level, results.get(level)+1);
            } else {
                results.put(level, 1);
            }
        }
        return results;
    }

    private static void upgradeMovePanel() {
        moveTerrFrom = makeLabel(movePanel, "", moveFromBounds);
        moveTerrTo = makeLabel(movePanel, "", moveToBounds);
        String[] armiesInfo = makeUnits();
        choseMoveNums = makeMultiSelectionList(movePanel, armiesInfo, new Rectangle(290, 70, 100, 100));

    }

    /*
    Make the units info list.
     */
    private static String[] makeUnits() {
        String fromTerr = moveTerrFrom.getText();
        Territory territory;
        if (fromTerr.equals("")) {
            territory = territories.get(1);
        } else {
            territory = getTerr(fromTerr);
        }
        Army army = territory.getOwnerArmy();
        HashMap<Integer, ArrayList<Unit>> armyUnitMap = army.getUnitMap();
        ArrayList<String> unitsCheckBox = new ArrayList<>();
        for (Integer level: armyUnitMap.keySet()) {
            ArrayList<Unit> units = armyUnitMap.get(level);
            for (Unit unit: units) {
                unitsCheckBox.add(unit.getName() + ": " + String.valueOf(level));
            }
        }
        String[] results = new String[unitsCheckBox.size()];
        int index = 0;
        for (String s: unitsCheckBox) {
            results[index] = s;
            index++;
        }
        return results;
    }


    /*
    @param: void
    @return: void
    Update the String[] of names of all territories owned by playerID
     */
    private static void updateOwnerTerrNames() {
        Player player = players.get(playerID);
        ArrayList<Territory> ownedTerritories = new ArrayList<>(player.getTerrMap().values());
        ownedTerrNames = getTerrNames(ownedTerritories);
    }

    public static void setAttackPanel() {
        attackPanel.setLayout(null);
        attackPanel.setPreferredSize(attackPanelSize);

        makeLabel(attackPanel, "From", attackFromPromptBounds);
        updateOwnerTerrNames();

        makeLabel(attackPanel, "To", attackToPromptBounds);
        makeLabel(attackPanel, "Units", new Rectangle(290, 20, 50, 30));
        updateAttackPanel();

        JButton button = makeButton(attackPanel, "Attack", new Rectangle(410, 20, 150, 30));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(attackPanel);
                frame.add(actionPanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = attackPanel;
                try {
                    // attack order: enter "attack" for orderType
                    //   "myTerrName" : "XXX"
                    //   "targetTerrName" : "XXX"
                    //   "2":"1" <-- level2 units: 1
                    //   "5":"3" <-- level5 units: 3
                    ArrayList<String> selected = new ArrayList<>(choseAttachNums.getSelectedValuesList());
                    HashMap<String, String> attackOrders = new HashMap<>();
                    attackOrders.put("myTerrName", attackTerrFrom.getText());
                    attackOrders.put("targetName", attackTerrTo.getText());
                    HashMap<String, Integer> orders = count(selected);
                    for (String key: orders.keySet()) {
                        attackOrders.put(key, String.valueOf(orders.get(key)));
                        System.out.print("AttackOrder: " + key + ": " + String.valueOf(orders.get(key)));
                    }
                    clientOperator.makeOrder("attack", attackOrders);
                    updateArrtibute();
                } catch (ClientOperationException ce) {

                }

            }
        });

        //TODO cancel button
    }

    private static void updateAttackPanel() {
        attackTerrFrom = makeLabel(attackPanel, "", attackFromBounds);
        attackTerrTo = makeLabel(attackPanel, "", attackToBounds);
        String[] armiesInfo = makeUnits();
        choseAttachNums = makeMultiSelectionList(attackPanel, armiesInfo, new Rectangle(290, 70, 100, 100));
    }

    //TODO
    public static void setUpgradePanel() {
        upgradePanel.setLayout(null);
        upgradePanel.setPreferredSize(new Dimension(1000, 250));

        makeLabel(upgradePanel, "Your Armies: ", new Rectangle(50, 20, 100, 30));
        armiesSituation = makeLabel(upgradePanel, "current", new Rectangle(60, 60, 300, 30));

        makeLabel(upgradePanel, "UpgradeTerr", new Rectangle(50, 110, 100, 30));
        upgradeTerr = makeLabel(upgradePanel, "", new Rectangle(50, 150, 100, 30));
        makeLabel(upgradePanel, "From", new Rectangle(170, 110, 100, 30));
        makeLabel(upgradePanel, "to", new Rectangle(290, 110, 50, 30));

        String[] upgradeString = {"1", "2", "3", "4", "5", "6", "7"};
        chooseUpgradeFrom = makeDropDown(upgradePanel, upgradeString, new Rectangle(170, 110, 100, 30));

        chooseUpgradeTo = makeDropDown(upgradePanel, upgradeString, new Rectangle(360, 110, 100, 30));

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
                try{
                    // upgrade order: enter "upgrade" for orderType
                    //   "onTerrName" : "XXX"
                    //   "fromLevel":"1"
                    //   "toLevel":"3"
                    HashMap<String, String> upgradeOrder = new HashMap<>();
                    upgradeOrder.put("onTerrName", upgradeTerr.getText());
                    upgradeOrder.put("fromLevel", (String)chooseUpgradeFrom.getSelectedItem());
                    upgradeOrder.put("toLevel", (String)chooseUpgradeTo.getSelectedItem());
                    clientOperator.makeOrder("upgrade", upgradeOrder);
                    updateArrtibute();
                }catch (ClientOperationException ce) {

                }
            }
        });
        //TODO cancel button
    }

    private static void updateUpgradePanel() {
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
    @param: target: where the label should be added
            elements: the content of JList
            position: the position of the JList
    @return: JList<String>
    Make a multiSelectionJList and add it to the target JPanel.
     */
    private static JList<String> makeMultiSelectionList(JPanel target, String[] elements, Rectangle position) {
        JScrollPane listScroller = new JScrollPane();
        JList<String> listArea = new JList<>();
        listArea.setSelectionMode(2); //ListSelectionModel.MULTIPLE_INTERVAL_SELECTION = 2
        listArea.setListData(elements);
        listArea.setCellRenderer(new CheckboxListCellRenderer());
        listArea.setVisibleRowCount(5);
        listScroller.setViewportView(listArea);
        target.add(listScroller);
        listArea.setLayoutOrientation(JList.VERTICAL);
        listScroller.setBounds(position);
        return listArea;
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
        // make client
        ClassBuilder classBuilder = new ClassBuilderEvo2();
        GameClient client = new GameClientJSON(classBuilder);
        // make client operator
        OrderFactory orderFactory = new OrderFactoryEvo2();
        ClientOperator clientOperator = new ClientOperatorEvo2(client, orderFactory);
        // make text GUI
        try {
            clientOperator.initConnection("0.0.0.0", "8000");
        } catch (ClientOperationException ce) {

        }
        System.out.println("Finish Connection");
        new app(clientOperator);
    }

}

