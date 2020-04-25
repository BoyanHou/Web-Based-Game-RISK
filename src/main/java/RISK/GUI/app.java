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
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class app extends JFrame {
    private static ClientOperator<JSONObject> clientOperator;
    private static GameClient<JSONObject> gameClient;
    private static HashMap<Integer, Player> players;
    private static HashMap<Integer, Territory> territories;
    private static ArrayList<TerritoryBlock> territoryBlocks;
    private static HashMap<Integer, Army> armies;
    private static String[] ownedTerrNames;

    private static JFrame frame;
    private static JPanel actionPanel;
    private static JPanel movePanel;
    private static JPanel attackPanel;
    private static JPanel upgradePanel;
    private static JPanel fogPanel;
    private static JPanel spyPanel;
    private static JPanel spyCoverPanel;
    private static JPanel spyRemovePanel;
    private static JPanel informationPanel;
    private static JPanel mapPanel;
    private static JPanel playerPanel;
    private static JPanel makeChoicePanel;

    private static JPanel terrInfoPanel;
    private static JLabel terrInfo;
    private static JLayeredPane mapInfoPane;

    private static JPanel currentPanel;
    private static JLabel currentSelectedLabel;
    private static JComboBox<String> currentSelectedComboBox;
    private static Territory currentSelectedTerr;
    private static TerritoryBlock currentSelectedTerrBlock;
    private static Point currentSelectedPoint;

    //Mark: the information that will be updated or kept track of
    //the playerPanel, the north one
    private static int playerID;
    private static JLabel foodLabel;
    private static JLabel techLabel;

    //map panel
    private static ArrayList<JLabel> fogLabels;
    private static ArrayList<JLabel> spyLabels;

    //the informationPanel
    private static JLabel details;

    //the movePanel
    private static JLabel moveTerrFrom;
    private static JLabel moveTerrTo;
    private static JList<String> choseMoveNums;

    //the attackPanel
    private static JLabel attackTerrFrom;
    private static JLabel attackTerrTo;
    private static JList<String> choseAttachNums;

    //the upgradePanel
    private static JLabel upgradeTerr;
    private static JComboBox<String> chooseUpgradeFrom;
    private static JComboBox<String> chooseUpgradeTo;

    //the fog panel
    private static JLabel addedFogTerrLabel;

    //the spy panel
    private static JLabel coverSpyOnLabel;
    private static JLabel fromSpyTerrLabel;
    private static JLabel toSpyTerrLabel;

    //position parameter settings
    private static Dimension frameSize = new Dimension(1000, 800);

    private static Dimension mapPanelSize = new Dimension(700, 500);

    private static Dimension playerPanelSize = new Dimension(500, 100);
    private static Rectangle playerIDBounds = new Rectangle(50, 50, 150, 30);
    private static Rectangle foodPromptBounds = new Rectangle(200, 50, 80, 30);
    private static Rectangle foodLabelBounds = new Rectangle(280, 50, 50, 30);
    private static Rectangle techPromptBounds = new Rectangle(330, 50, 50, 30);
    private static Rectangle techLabelBounds = new Rectangle(380, 50, 50, 30);

    private static Dimension informationPanelSize = new Dimension(300, 500);
    private static Rectangle chooseTerrLabelBounds = new Rectangle(0, 50, 150, 30);
    private static Rectangle chooseTerrDropDownBounds = new Rectangle(0, 100, 200, 30);
    private static Rectangle detailsBounds = new Rectangle(50, 150, 200, 300);
    private static Rectangle displayButtonBounds = new Rectangle(200, 50, 100, 30);

    private static Dimension actionPanelSize = new Dimension(1000, 250);
    private static Rectangle chooseActionBounds = new Rectangle(50, 40, 200, 30);
    private static Rectangle moveButtonBounds = new Rectangle(100, 110, 90, 40);
    private static Rectangle attackButtonBounds = new Rectangle(320, 110, 90, 40);
    private static Rectangle upgradeButtonBounds = new Rectangle(540, 110, 90, 40);
    private static Rectangle finishButtonBounds = new Rectangle(760, 110, 90, 40);

    private static Dimension movePanelSize = new Dimension(1000, 250);
    private static Rectangle moveFromPromptBounds = new Rectangle(50, 20, 50, 30);
    private static Rectangle moveFromBounds = new Rectangle(50, 70, 100, 30);
    private static Rectangle moveToPromptsBounds = new Rectangle(170, 20, 50, 30);
    private static Rectangle moveToBounds = new Rectangle(170, 70, 100, 30);
    private static Rectangle moveConfirmButton = new Rectangle(270, 190, 150, 30);

    private static Dimension attackPanelSize = new Dimension(1000, 250);
    private static Rectangle attackFromPromptBounds = new Rectangle(50, 20, 50, 30);
    private static Rectangle attackFromBounds = new Rectangle(50, 70, 100, 30);
    private static Rectangle attackToPromptBounds = new Rectangle(170, 20, 50, 30);
    private static Rectangle attackToBounds = new Rectangle(170, 70, 100, 30);
    private static Rectangle attackConfirmButton = new Rectangle(270, 190, 150, 30);

    // cancel button
    private static Rectangle cancelConfirmButton = new Rectangle(510, 190, 150, 30);


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
        fogPanel = new JPanel();
        spyPanel = new JPanel();
        spyCoverPanel = new JPanel();
        spyRemovePanel = new JPanel();
        informationPanel = new JPanel();
        playerPanel = new JPanel();
        makeChoicePanel = new JPanel();
        terrInfoPanel = new JPanel();
        try {
            setActionPanel();
            setMovePanel();
            setAttackPanel();
            setUpgradePanel();
            setFogPanel();
            setSpyPanel();
            setSpyCoverPanel();
            setSpyRemovePanel();
            setInfoPanel();
            setMapPane();
            setPlayerPanel();
            setMakeChoicePanel();
            setTerrInfoPanel();
        } catch (ClientOperationException ce) {

        }
        frame.setVisible(true);
    }

    private static void setTerrInfoPanel() {
        terrInfoPanel.setLayout(null);
        terrInfoPanel.setPreferredSize(new Dimension(150, 150));
        terrInfo = makeLabel(terrInfoPanel, "Terr Info", new Rectangle(0, 0, 150, 150), false);
        terrInfo.setBackground(Color.white);
        currentSelectedTerrBlock = null;
    }

    private static void setMakeChoicePanel() {
        makeChoicePanel.setLayout(null);
        makeChoicePanel.setPreferredSize(actionPanelSize);

        makeLabel(makeChoicePanel, "Do you want to aduit?", new Rectangle(50, 50, 200, 30), false);
        JButton yesButton = makeButton(makeChoicePanel, "Yes", new Rectangle(100, 100, 100, 30));
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientOperator.AuditOrNot("YES");
                    System.out.println("Send Yes");
                    frame.remove(makeChoicePanel);
                    frame.revalidate();
                    frame.repaint();
                    String message = clientOperator.listenForUpdates();
                    while (message.equals("CONTINUE")) {
                        JOptionPane.showMessageDialog(frame, "Next Round");
                        updateArrtibute();
                        updateMapPanel();
                        message = clientOperator.listenForUpdates();
                    }
                    JOptionPane.showMessageDialog(frame, message);
                } catch (ClientOperationException ce) {
                    JOptionPane.showMessageDialog(frame, ce.getMessage());
                }

            }
        });

        JButton noButton = makeButton(makeChoicePanel, "No", new Rectangle(200, 100, 100, 30));
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientOperator.AuditOrNot("NO");
                } catch (ClientOperationException ce) {
                    JOptionPane.showMessageDialog(frame, ce.getMessage());
                }
                JOptionPane.showMessageDialog(frame, "Please Close the Web now");
            }
        });
    }

    //MARK: - draw the map
    private static void setMapPane() {
        mapInfoPane = new JLayeredPane();
        mapInfoPane.setLayout(null);
        mapInfoPane.setPreferredSize(mapPanelSize);

        //initialize fog and spy
        fogLabels = new ArrayList<>();
        spyLabels = new ArrayList<>();

        //initialize territoryBlocks
        TerritoryBlockInitial initTB = new TerritoryBlockInitial();
        HashMap<String, TerritoryBlock> territoryBlockMap = initTB.getTerritoryBlockMap();

        territoryBlocks = new ArrayList<>();
        HashMap<String, Rectangle> terrNamePos = initTB.getTerrNamePos();
        for (Territory territory : territories.values()) {
            TerritoryBlock territoryBlock = territoryBlockMap.get(territory.getName());
            territoryBlock.setTerritory(territory);
            territoryBlock.setColor(Color.white);
            territoryBlocks.add(territoryBlock);
        }

        mapPanel = new MapPanel(territoryBlocks);
        mapInfoPane.add(terrInfoPanel, 2);
        mapInfoPane.add(mapPanel, 0);
        mapPanel.setLayout(null);
        mapPanel.setPreferredSize(mapPanelSize);
        mapPanel.setBounds(0, 0, mapPanelSize.width, mapPanelSize.height);
        mapPanel.setBackground(Color.white);

        for (Territory territory : territories.values()) {
            String name = territory.getName();
            makeLabel(mapPanel, name, terrNamePos.get(name), false);
        }
        mapPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                System.out.print("The postion of click: ");
                System.out.print(x);
                System.out.print(" ");
                System.out.println(y);

                String selected = "";
                TerritoryBlock selectedTerrBlock = getSelectedTerrBlock(new Point(x, y));
                if (selectedTerrBlock == null) {
                    System.out.println("Invalid");
                } else {
                    selected = selectedTerrBlock.getTerrName();
                }
                if (currentPanel != actionPanel) {
                    currentSelectedLabel.setText(selected);
                }
                String[] armiesInfo = makeUnits(currentSelectedLabel);
                if (currentPanel == movePanel) {
                    choseMoveNums.setListData(armiesInfo);
                }
                if (currentPanel == attackPanel) {
                    choseAttachNums.setListData(armiesInfo);
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
        mapPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                TerritoryBlock selectedTerrBlock = getSelectedTerrBlock(new Point(x, y));
                if (selectedTerrBlock == null) {
                    //not inside the terr
                    if (currentSelectedTerrBlock != null) {
                        updateTerrBlock(currentSelectedTerrBlock, gameClient.getOutdatedTerrMap());
                        currentSelectedTerrBlock = null;
                        mapPanel.revalidate();
                        mapPanel.repaint();
                    }
                    frame.revalidate();
                    frame.repaint();

                } else {
                    TerritoryBlock territoryBlockNow = selectedTerrBlock;
                    if (currentSelectedTerrBlock == null || !currentSelectedTerrBlock.getTerrName().equals(territoryBlockNow.getTerrName())) {
                        //the info should be updated to display new terrInfo
                        updateTerrBlock(currentSelectedTerrBlock, gameClient.getOutdatedTerrMap());
                        currentSelectedTerrBlock = territoryBlockNow;
                        currentSelectedTerrBlock.setColor(Color.yellow);
                        mapPanel.revalidate();
                        mapPanel.repaint();
                        String info = getDisplayInfo(selectedTerrBlock.getTerrName());
                        details.setText(info);
                        frame.revalidate();
                    }
                }
            }
        });
        updateMapPanel();
        frame.add(mapInfoPane, BorderLayout.CENTER);
    }

    private static TerritoryBlock getSelectedTerrBlock(Point p) {
        for (TerritoryBlock territoryBlock : territoryBlocks) {
            if (territoryBlock.check(p)) {
                return territoryBlock;
            }
        }
        return null;
    }

    private static void updateMapPanel() {
        HashMap<Integer, Territory> outdatedTerrMap = gameClient.getOutdatedTerrMap();
        for (TerritoryBlock territoryBlock : territoryBlocks) {
            updateTerrBlock(territoryBlock, outdatedTerrMap);
        }
        mapPanel.revalidate();
        mapPanel.repaint();
    }

    //update one Terr
    private static void updateTerrBlock(TerritoryBlock territoryBlock, HashMap<Integer, Territory> outdatedTerrMap) {
        if (territoryBlock == null) {
            return;
        }
        String terrName = territoryBlock.getTerrName();
        Territory territory = getTerr(terrName);
        territoryBlock.setTerritory(territory);
        if (territory.isVisible(playerID)) {
            //normal
            territoryBlock.update();
        } else if (outdatedTerrMap.containsKey(territory.getTerrID())) {
                //display outdated
                territoryBlock.setColor(Color.GRAY);
        } else {
                //not display information
                territoryBlock.setColor(Color.WHITE);
        }
    }

    private static void updatedFog() {
        //TODO
    }

    private static void updateSpy() {
        //TODO
    }

    //Mark: - setup the player info
    private static void setPlayerPanel() {
        playerPanel.setLayout(null);
        playerPanel.setPreferredSize(playerPanelSize);
        makeLabel(playerPanel, "Your ID: " + String.valueOf(playerID), playerIDBounds, false);

        makeLabel(playerPanel, "Food: ", foodPromptBounds, false);
        foodLabel = makeLabel(playerPanel, "", foodLabelBounds, true);
        makeLabel(playerPanel, "Tech: ", techPromptBounds, false);
        techLabel = makeLabel(playerPanel, String.valueOf(""), techLabelBounds, true);
        updateArrtibute();
        updatePlayerPanel();

        frame.add(playerPanel, BorderLayout.NORTH);
    }

    /*
    Update the food and tech Label
     */
    private static void updatePlayerPanel() {
        int food = players.get(playerID).getFood();
        foodLabel.setText(String.valueOf(food));
        int tech = players.get(playerID).getTech();
        techLabel.setText(String.valueOf(tech));
    }

      //MARK: - SetUp information Display --------------------------------------------------------------------------------
      private static void setInfoPanel() {
          informationPanel.setLayout(null);
          informationPanel.setPreferredSize(informationPanelSize);
          details = makeLabel(informationPanel, "Details Information", detailsBounds, false);
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
    Get the detail info of the selected name(The correct info to display).
     */
    private static String getDisplayInfo(String name) {
        Territory territory = getTerr(name);
        if (territory == null) {
            return "Invalid Name";
        }
        String info;
        HashMap<Integer, Territory> outdatedTerrMap = new HashMap<>();
        if (territory.isVisible(playerID)) {
            //normal
            info = getTerrInfo(territory);
        } else if (outdatedTerrMap.containsKey(territory.getTerrID())) {
            Territory oldTerr = outdatedTerrMap.get(territory.getTerrID());
            info = getTerrInfo(oldTerr);
        } else {
            info = "No Available Information";
        }
        return info;
    }

    public static String getTerrInfo(Territory territory) {
        StringBuilder sb = new StringBuilder();
        Player owner = territory.getOwner();
        sb.append("<html><pre>Owner: ");
        sb.append(owner.getPlayerID());
        sb.append("\n");
        sb.append("Defend by: \n");
        Army army = territory.getOwnerArmy();
        HashMap<Integer, ArrayList<Unit>> armyUnitMap = army.getUnitMap();
        for (Integer level : armyUnitMap.keySet()) {
            sb.append("    Level ");
            sb.append(level);
            sb.append(": ");
            sb.append(armyUnitMap.get(level).size());
            sb.append(" units");
            sb.append("\n");
        }
        sb.append("</pre></html>");
        return sb.toString();
    }

    /*
    @param: name: the territory name
    @return: Territory
    Return the territory with the corresponding name.
     */
    private static Territory getTerr(String name) {
        updateArrtibute();
        for (Territory territory : territories.values()) {
            if (territory.getName().equals(name)) {
                return territory;
            }
        }
        return null;
    }


    //MARK: - SetUp actions --------------------------------------------------------------------------------------------
    private static void setActionPanel() throws ClientOperationException {
        actionPanel.setLayout(null);
        actionPanel.setPreferredSize(actionPanelSize);

        makeLabel(actionPanel, "Choose your action", chooseActionBounds, false);

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
                currentSelectedLabel = moveTerrFrom;
                moveTerrFrom.setText("");
                moveTerrTo.setText("");
                String[] armiesInfo = makeUnits(moveTerrFrom);
                choseMoveNums.setListData(armiesInfo);
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
                currentSelectedLabel = attackTerrFrom;
                attackTerrFrom.setText("");
                attackTerrTo.setText("");
                String[] armiesInfo = makeUnits(attackTerrFrom);
                choseAttachNums.setListData(armiesInfo);

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
                currentSelectedLabel = upgradeTerr;
                upgradeTerr.setText("");
            }
        });

        JButton spyButton = makeButton(actionPanel, "Spy", new Rectangle(10, 10, 50, 30));
        spyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(actionPanel);
                frame.add(spyPanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = spyPanel;
                currentSelectedLabel = new JLabel();
                coverSpyOnLabel.setText("");
                fromSpyTerrLabel.setText("");
                toSpyTerrLabel.setText("");
            }
        });

        JButton fogButton = makeButton(actionPanel, "Fog", new Rectangle(20, 30, 50, 30));
        fogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(actionPanel);
                frame.add(fogPanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = fogPanel;
                currentSelectedLabel = addedFogTerrLabel;
                addedFogTerrLabel.setText("");
            }
        });

        JButton finishButton = makeButton(actionPanel, "Finsh", finishButtonBounds);
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Waiting");
                try {
                    clientOperator.stopMakingOrder();
                    String message = clientOperator.listenForUpdates();
                    //    "LOSE": player has lost the game --> should decide & call willAudit() function to inform server, to audit or not
                    //    "CONTINUE": player can proceed to next round
                    //     otherwise: someone has won, print this String and exit game
                    switch (message) {
                        case "LOSE":

                            JOptionPane.showMessageDialog(frame, "Lose");
                            frame.remove(actionPanel);
                            frame.add(makeChoicePanel, BorderLayout.SOUTH);
                            frame.revalidate();
                            frame.repaint();
                            break;
                        case "CONTINUE": {
                            JOptionPane.showMessageDialog(frame, "New Round");
                            updateArrtibute();
                            break;
                        }
                        default:
                            JOptionPane.showMessageDialog(frame, message);
                    }
                    updateArrtibute();
                    updateMapPanel();

                } catch (ClientOperationException ce) {
                    JOptionPane.showMessageDialog(frame, ce.getMessage());
                }
            }
        });
        currentPanel = actionPanel;
        frame.add(actionPanel, BorderLayout.SOUTH);
    }

    public static void setSpyPanel() {
        spyPanel.setLayout(null);
        spyPanel.setPreferredSize(movePanelSize);

        JButton coverSpy = makeButton(spyPanel, "Cover Spy", new Rectangle(50, 50, 50, 30));
        coverSpy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(spyPanel);
                frame.add(spyCoverPanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = spyCoverPanel;
                currentSelectedLabel = coverSpyOnLabel;
                coverSpyOnLabel.setText("");
            }
        });

        JButton removeSpy = makeButton(spyPanel, "Remove Spy", new Rectangle(150, 50, 50, 30));
        removeSpy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(spyPanel);
                frame.add(spyRemovePanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = spyRemovePanel;
                currentSelectedLabel = fromSpyTerrLabel;
                fromSpyTerrLabel.setText("");
                toSpyTerrLabel.setText("");
            }
        });

        makeCancelButton(spyPanel, cancelConfirmButton);
    }

    public static void setSpyCoverPanel() {
        spyCoverPanel.setLayout(null);
        spyCoverPanel.setPreferredSize(movePanelSize);

        makeLabel(spyCoverPanel, "Put Spy On:", new Rectangle(10, 50, 100, 30), false);
        coverSpyOnLabel = makeLabel(spyCoverPanel, "", new Rectangle(200, 50, 100, 30), true);

        JButton button = makeButton(spyCoverPanel, "Make Spy", new Rectangle(200, 100, 100, 30));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(spyCoverPanel);
                frame.add(actionPanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = actionPanel;
                try {
                    // convertSpy order: enter "convertSpy" for orderType
                    //   "onTerrName": "XXX"
                    HashMap<String, String> convertSpyOrders = new HashMap<>();
                    convertSpyOrders.put("onTerrName", coverSpyOnLabel.getText());
                    clientOperator.makeOrder("convertSpy", convertSpyOrders);
                    updateArrtibute();
                    updatePlayerPanel();
                } catch (ClientOperationException ce) {
                    JOptionPane.showMessageDialog(frame, ce.getMessage());
                }
            }
        });

        makeCancelButton(spyCoverPanel, cancelConfirmButton);
    }

    public static void setSpyRemovePanel() {
        spyRemovePanel.setLayout(null);
        spyRemovePanel.setPreferredSize(movePanelSize);

        makeLabel(spyRemovePanel, "Move Spy From:", new Rectangle(10, 50, 100, 30), false);
        makeLabel(spyRemovePanel, "To", new Rectangle(10, 100, 100, 30), false);
        fromSpyTerrLabel = makeLabel(spyRemovePanel, "", new Rectangle(200, 50, 100, 30), true);
        toSpyTerrLabel = makeLabel(spyRemovePanel, "", new Rectangle(200, 100, 100, 30), true);

        JButton button = makeButton(spyRemovePanel, "Move Spy", new Rectangle(300, 150, 100, 30));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(spyRemovePanel);
                frame.add(actionPanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = actionPanel;
                try {
                    // moveSpy order: enter "moveSpy" for orderType
                    //   fromTerrName: "XXX"
                    //   toTerrName: "XXX"
                    HashMap<String, String> moveSpyOrders = new HashMap<>();
                    moveSpyOrders.put("fromTerrName", fromSpyTerrLabel.getText());
                    moveSpyOrders.put("toTerrName", toSpyTerrLabel.getText());
                    clientOperator.makeOrder("moveSpy", moveSpyOrders);
                    updateArrtibute();
                    updatePlayerPanel();
                } catch (ClientOperationException ce) {
                    JOptionPane.showMessageDialog(frame, ce.getMessage());
                }
            }
        });

        makeCancelButton(spyRemovePanel, cancelConfirmButton);
    }

    public static void setFogPanel() {
        fogPanel.setLayout(null);
        fogPanel.setPreferredSize(movePanelSize);
        makeLabel(fogPanel, "Put fog on: ", new Rectangle(10, 30, 50, 30), false);
        addedFogTerrLabel = makeLabel(fogPanel, "fog on: ", new Rectangle(10, 10, 50, 30), true);
        JButton fogButton = makeButton(fogPanel, "Fog", new Rectangle(100, 10, 50, 30));
        fogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(fogPanel);
                frame.add(actionPanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = actionPanel;
                try {
                    // fog order: enter "fog" for orderType
                    //   "onTerrName": "XXX"
                    HashMap<String, String> fogOrders = new HashMap<>();
                    fogOrders.put("onTerrName", addedFogTerrLabel.getText());
                    clientOperator.makeOrder("fog", fogOrders);
                    updateArrtibute();
                    updatePlayerPanel();
                } catch (ClientOperationException ce) {
                    JOptionPane.showMessageDialog(frame, ce.getMessage());
                }
            }
        });

        makeCancelButton(fogPanel, cancelConfirmButton);
    }


    public static void setMovePanel() {

        movePanel.setLayout(null);
        movePanel.setPreferredSize(movePanelSize);

        makeLabel(movePanel, "From: ", moveFromPromptBounds, false);
        updateOwnerTerrNames();

        makeLabel(movePanel, "To", moveToPromptsBounds, false);

        makeLabel(movePanel, "Units", new Rectangle(290, 20, 50, 30), false);

        moveTerrFrom = makeLabel(movePanel, "", moveFromBounds, true);
        moveTerrTo = makeLabel(movePanel, "", moveToBounds, true);
        String[] armiesInfo = makeUnits(moveTerrFrom);
        choseMoveNums = makeMultiSelectionList(movePanel, armiesInfo, new Rectangle(290, 70, 200, 100));

        JButton moveButton = makeButton(movePanel, "Move", moveConfirmButton);
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(movePanel);
                frame.add(actionPanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = actionPanel;
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
                    for (String key : orders.keySet()) {
                        moveOrders.put(key, String.valueOf(orders.get(key)));
                        System.out.print("MoveOrder: " + key + ": " + String.valueOf(orders.get(key)));
                    }
                    clientOperator.makeOrder("move", moveOrders);
                    updateArrtibute();
                    updatePlayerPanel();
                } catch (ClientOperationException ce) {
                    JOptionPane.showMessageDialog(frame, ce.getMessage());
                }
            }
        });

        makeCancelButton(movePanel, cancelConfirmButton);

    }

    private static HashMap<String, Integer> count(ArrayList<String> commands) {
        HashMap<String, Integer> results = new HashMap<>();
        for (String command : commands) {
            String[] units = command.split(": ");
            String level = units[1];
            if (results.containsKey(level)) {
                results.replace(level, results.get(level) + 1);
            } else {
                results.put(level, 1);
            }
        }
        return results;
    }


    /*
    Make the units info list.
     */
    private static String[] makeUnits(JLabel label) {
        String fromTerr = label.getText();
        Territory territory;
        if (fromTerr.equals("")) {
            return new String[0];
        } else {
            territory = getTerr(fromTerr);
        }
        Army army = territory.getOwnerArmy();
        HashMap<Integer, ArrayList<Unit>> armyUnitMap = army.getUnitMap();
        ArrayList<String> unitsCheckBox = new ArrayList<>();
        for (Integer level : armyUnitMap.keySet()) {
            ArrayList<Unit> units = armyUnitMap.get(level);
            for (Unit unit : units) {
                unitsCheckBox.add(unit.getName() + ": " + String.valueOf(level));
            }
        }
        String[] results = new String[unitsCheckBox.size()];
        int index = 0;
        for (String s : unitsCheckBox) {
            results[index] = s;
            index++;
        }
        for (String s : results) {
            System.out.println(s);
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

        makeLabel(attackPanel, "From", attackFromPromptBounds, false);
        attackTerrFrom = makeLabel(attackPanel, "", attackFromBounds, true);
        attackTerrTo = makeLabel(attackPanel, "", attackToBounds, true);
        String[] armiesInfo = makeUnits(attackTerrFrom);
        choseAttachNums = makeMultiSelectionList(attackPanel, armiesInfo, new Rectangle(290, 70, 200, 100));

        makeLabel(attackPanel, "To", attackToPromptBounds, false);
        makeLabel(attackPanel, "Units", new Rectangle(290, 20, 50, 30), false);

        JButton button = makeButton(attackPanel, "Attack", attackConfirmButton);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(attackPanel);
                frame.add(actionPanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = actionPanel;
                try {
                    // attack order: enter "attack" for orderType
                    //   "myTerrName" : "XXX"
                    //   "targetTerrName" : "XXX"
                    //   "2":"1" <-- level2 units: 1
                    //   "5":"3" <-- level5 units: 3
                    ArrayList<String> selected = new ArrayList<>(choseAttachNums.getSelectedValuesList());
                    HashMap<String, String> attackOrders = new HashMap<>();
                    attackOrders.put("myTerrName", attackTerrFrom.getText());
                    attackOrders.put("targetTerrName", attackTerrTo.getText());
                    HashMap<String, Integer> orders = count(selected);
                    for (String key : orders.keySet()) {
                        attackOrders.put(key, String.valueOf(orders.get(key)));
                        System.out.print("AttackOrder: " + key + ": " + String.valueOf(orders.get(key)));
                    }
                    clientOperator.makeOrder("attack", attackOrders);
                    updateArrtibute();
                    updatePlayerPanel();
                } catch (ClientOperationException ce) {
                    JOptionPane.showMessageDialog(frame, ce.getMessage());
                }

            }
        });

        makeCancelButton(attackPanel, cancelConfirmButton);
    }


    public static void setUpgradePanel() {
        upgradePanel.setLayout(null);
        upgradePanel.setPreferredSize(new Dimension(1000, 250));

        //makeLabel(upgradePanel, "Your Armies: ", new Rectangle(50, 20, 100, 30));
        //armiesSituation = makeLabel(upgradePanel, "current", new Rectangle(60, 60, 300, 30));

        makeLabel(upgradePanel, "UpgradeTerr", new Rectangle(50, 20, 100, 30), false);
        upgradeTerr = makeLabel(upgradePanel, "", new Rectangle(200, 20, 100, 30), true);
        makeLabel(upgradePanel, "From", new Rectangle(50, 110, 100, 30), false);
        makeLabel(upgradePanel, "to", new Rectangle(290, 110, 50, 30), false);

        String[] upgradeString = {"1", "2", "3", "4", "5", "6", "7"};
        chooseUpgradeFrom = makeDropDown(upgradePanel, upgradeString, new Rectangle(170, 110, 100, 30));

        chooseUpgradeTo = makeDropDown(upgradePanel, upgradeString, new Rectangle(360, 110, 100, 30));

        JButton makeUpgradeButton = makeButton(upgradePanel, "Upgrade", new Rectangle(270, 190, 150, 30));
        makeUpgradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(upgradePanel);
                frame.add(actionPanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = actionPanel;
                try {
                    // upgrade order: enter "upgrade" for orderType
                    //   "onTerrName" : "XXX"
                    //   "fromLevel":"1"
                    //   "toLevel":"3"
                    HashMap<String, String> upgradeOrder = new HashMap<>();
                    upgradeOrder.put("onTerrName", upgradeTerr.getText());
                    upgradeOrder.put("fromLevel", (String) chooseUpgradeFrom.getSelectedItem());
                    upgradeOrder.put("toLevel", (String) chooseUpgradeTo.getSelectedItem());
                    clientOperator.makeOrder("upgrade", upgradeOrder);
                    updateArrtibute();
                    updatePlayerPanel();
                } catch (ClientOperationException ce) {
                    JOptionPane.showMessageDialog(frame, ce.getMessage());
                }
            }
        });

        makeCancelButton(upgradePanel, cancelConfirmButton);
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
        button.setBackground(new Color(59, 89, 182));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Serif", Font.BOLD, 12));
        //button.setIcon(new ImageIcon("ButtonImage.jpg"));

        button.setBounds(position);
        panel.add(button);
        return button;
    }

    private static void makeCancelButton(JPanel panel, Rectangle bounds) {
        JButton cancelButton = makeButton(panel, "Cancel", bounds);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(panel);
                frame.add(actionPanel, BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                currentPanel = actionPanel;
                currentSelectedLabel = new JLabel();
            }
        });
    }

    /*
    @param: panel: where the label should be added
            title: the message will be put on the label
            position: the position of the label
    @return: JLabel
    Make a label and put it in the given panel.
     */
    private static JLabel makeLabel(JPanel panel, String title, Rectangle position, Boolean addListener) {
        JLabel label = new JLabel(title);
        label.setBackground(new Color(59, 89, 182));
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Serif", Font.BOLD, 12));
        label.setBounds(position);
        if (addListener) {
            label.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    currentSelectedLabel = label;
                }
            });
        }
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
        listArea.setLayoutOrientation(JList.VERTICAL);
        listScroller.setBounds(position);
        target.add(listScroller);
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

