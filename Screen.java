import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.*;
import java.awt.event.WindowEvent;


//This is the super class for all screens in the game
class Screen extends Panel {

    //the welcome screen
    static class WelcomeScreen extends Screen {

        //constructor that creates the screen
        WelcomeScreen(Game game) {

            this.setLayout(new BorderLayout());
            JLabel label = new JLabel("Welcome to Space Trader!", SwingConstants.CENTER);
            label.setFont(new Font(label.getFont().getName(),
                    label.getFont().getStyle(), 50));
            // label.setBorder(new LineBorder(Color.MAGENTA));
            JButton start = new JButton("Start New Game");
            start.setPreferredSize(new Dimension(0, 100));
            start.setFont(new Font(label.getFont().getName(),
                    label.getFont().getStyle(), 30));

            start.addActionListener(e -> game.switchToScreen(
                    game.getScreens().get("configScreen")));

            this.add(label, BorderLayout.CENTER);
            this.add(start, BorderLayout.PAGE_END);
        }
    }

    //the LOSING screen
    static class LosingScreen extends Screen {

        //constructor that creates the screen
        LosingScreen(Game game) {

            this.setLayout(new BorderLayout());
            JLabel label = new JLabel("You have LOST.", SwingConstants.CENTER);
            label.setFont(new Font(label.getFont().getName(),
                    label.getFont().getStyle(), 67));
            label.setVerticalAlignment(JLabel.CENTER);
            JButton start = new JButton("Start A New Game");
            start.setPreferredSize(new Dimension(0, 100));
            start.setFont(new Font(label.getFont().getName(),
                    label.getFont().getStyle(), 30));

            start.addActionListener(e -> this.setVisible(false));
            start.addActionListener(e -> game.startNewGame());


            this.add(label, BorderLayout.CENTER);
            this.add(start, BorderLayout.PAGE_END);
        }
    }

    static class WinScreen extends Screen {
        //constructor that creates the screen
        WinScreen(Game game) {
            this.setLayout(new BorderLayout());
            JLabel winLabel = new JLabel("You did it!!! You bought the universe and won the game!", SwingConstants.CENTER);
            winLabel.setFont(new Font(winLabel.getFont().getName(),
                    winLabel.getFont().getStyle(), 20));
            this.add(winLabel, BorderLayout.CENTER);
        }
    }

    //the config screen
    static class ConfigScreen extends Screen {
        private Player player;
        private Game game;

        //constructor that creates the config screen
        public ConfigScreen(Game game, Player player) {
            this.game = game;
            this.player = player;

            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            JLabel enterNameLabel = new JLabel("Enter your name:", SwingConstants.CENTER);
            enterNameLabel.setFont(new Font(enterNameLabel.getFont().getName(),
                    enterNameLabel.getFont().getStyle(), 30));
            JTextField textBox = new JTextField();
            textBox.setFont(new Font(textBox.getFont().getName(),
                    textBox.getFont().getStyle(), 25));
            textBox.setPreferredSize(new Dimension(400, 50));
            JLabel difficultyLabel = new JLabel("Select Difficulty:", SwingConstants.CENTER);
            difficultyLabel.setFont(new Font(difficultyLabel.getFont().getName(),
                    difficultyLabel.getFont().getStyle(), 30));
            difficultyLabel.setBackground(Color.BLUE);
            JRadioButton easyButton = new JRadioButton("Easy");
            JRadioButton mediumButton = new JRadioButton("Medium");
            JRadioButton hardButton = new JRadioButton("Hard");
            easyButton.setFont(new Font(easyButton.getFont().getName(),
                    easyButton.getFont().getStyle(), 30));
            mediumButton.setFont(new Font(mediumButton.getFont().getName(),
                    mediumButton.getFont().getStyle(), 30));
            hardButton.setFont(new Font(hardButton.getFont().getName(),
                    hardButton.getFont().getStyle(), 30));
            ButtonGroup group = new ButtonGroup();
            group.add(easyButton);
            group.add(mediumButton);
            group.add(hardButton);
            JTextField totalPoints = new JTextField(" Skill Points Remaining: 0 ");
            totalPoints.setFont(new Font(totalPoints.getFont().getName(),
                    totalPoints.getFont().getStyle(), 30));
            totalPoints.setEditable(false);
            Font buttonFont = new Font(totalPoints.getFont().getName(),
                    totalPoints.getFont().getStyle(), 30);
            Font labelFont = new Font(totalPoints.getFont().getName(),
                    totalPoints.getFont().getStyle(), 30);

            JButton minusPilot = new JButton("-");
            JTextField pilotSkill = new JTextField(" Pilot: 0 ");
            JButton plusPilot = new JButton("+");
            JButton minusFighter = new JButton("-");
            JTextField fighterSkill = new JTextField(" Fighter: 0 ");
            JButton plusFighter = new JButton("+");
            JButton minusMerchant = new JButton("-");
            JTextField merchantSkill = new JTextField(" Merchant: 0 ");
            JButton plusMerchant = new JButton("+");
            JButton minusEngineer = new JButton("-");
            JTextField engineerSkill = new JTextField(" Engineer: 0 ");
            JButton plusEngineer = new JButton("+");
            setSkillButtons(buttonFont, labelFont, pilotSkill, fighterSkill,
                    merchantSkill, engineerSkill);

            minusPilot.setFont(buttonFont);
            plusPilot.setFont(buttonFont);
            minusPilot.setFont(buttonFont);
            plusFighter.setFont(buttonFont);
            minusFighter.setFont(buttonFont);
            plusEngineer.setFont(buttonFont);
            minusEngineer.setFont(buttonFont);
            plusMerchant.setFont(buttonFont);
            minusMerchant.setFont(buttonFont);
            JPanel row1 = new JPanel();
            row1.add(enterNameLabel);
            row1.add(textBox);
            JPanel row2 = new JPanel();
            row2.add(difficultyLabel);
            JPanel row3 = new JPanel();
            row3.add(easyButton);
            row3.add(mediumButton);
            row3.add(hardButton);
            JPanel row4 = new JPanel();
            row4.add(totalPoints);
            JPanel row5 = new JPanel();
            row5.add(minusPilot);
            row5.add(pilotSkill);
            row5.add(plusPilot);
            JPanel row6 = new JPanel();
            row6.add(minusFighter);
            row6.add(fighterSkill);
            row6.add(plusFighter);
            JPanel row7 = new JPanel();
            row7.add(minusMerchant);
            row7.add(merchantSkill);
            row7.add(plusMerchant);
            JPanel row8 = new JPanel();
            row8.add(minusEngineer);
            row8.add(engineerSkill);
            row8.add(plusEngineer);
            JPanel row9 = new JPanel();
            JButton randomButton = new JButton("Randomize");
            randomButton.setFont(new Font(randomButton.getFont().getName(),
                    randomButton.getFont().getStyle(), 25));
            JButton resetButton = new JButton("Reset");
            resetButton.setFont(new Font(resetButton.getFont().getName(),
                    resetButton.getFont().getStyle(), 25));
            JButton confirm = new JButton("Confirm");
            confirm.setFont(new Font(confirm.getFont().getName(),
                    confirm.getFont().getStyle(), 25));
            row9.add(randomButton);
            row9.add(resetButton);
            row9.add(confirm);

            this.add(Box.createRigidArea(new Dimension(0, 30)));
            this.add(row1);
            this.add(row2);
            this.add(row3);
            this.add(row4);
            this.add(row5);
            this.add(row6);
            this.add(row7);
            this.add(row8);
            this.add(row9);

            easyButton.addActionListener(e -> {
                difficultySetter(Game.EASY_DIFFICULTY, pilotSkill, fighterSkill,
                        merchantSkill, engineerSkill, totalPoints); });
            mediumButton.addActionListener(e -> {
                difficultySetter(Game.MEDIUM_DIFFICULTY, pilotSkill, fighterSkill,
                        merchantSkill, engineerSkill, totalPoints); });
            hardButton.addActionListener(e -> {
                difficultySetter(Game.HARD_DIFFICULTY, pilotSkill, fighterSkill,
                        merchantSkill, engineerSkill, totalPoints);
            });

            minusPilot.addActionListener(e -> {
                changeSkillPoint(0, true, totalPoints, pilotSkill); });
            plusPilot.addActionListener(e -> {
                changeSkillPoint(0, false, totalPoints, pilotSkill); });
            minusFighter.addActionListener(e -> {
                changeSkillPoint(1, true, totalPoints, fighterSkill); });
            plusFighter.addActionListener(e -> {
                changeSkillPoint(1, false, totalPoints, fighterSkill); });
            minusMerchant.addActionListener(e -> {
                changeSkillPoint(2, true, totalPoints, merchantSkill); });
            plusMerchant.addActionListener(e -> {
                changeSkillPoint(2, false, totalPoints, merchantSkill); });
            minusEngineer.addActionListener(e -> {
                changeSkillPoint(3, true, totalPoints, engineerSkill); });
            plusEngineer.addActionListener(e -> {
                changeSkillPoint(3, false, totalPoints, engineerSkill); });

            randomButton.addActionListener(e -> {
                randomize(pilotSkill, fighterSkill, merchantSkill, engineerSkill, totalPoints); });
            resetButton.addActionListener(e -> {
                reset(pilotSkill, fighterSkill, merchantSkill, engineerSkill, totalPoints); });
            confirm.addActionListener(e -> {
                confirm(textBox); });
        }



        //helper methods:
        private void setSkillButtons(Font buttonFont, Font labelFont, JTextField pilotSkill,
                                     JTextField fighterSkill, JTextField merchantSkill,
                                     JTextField engineerSkill) {
            pilotSkill.setFont(labelFont);
            pilotSkill.setEditable(false);
            fighterSkill.setFont(labelFont);
            fighterSkill.setEditable(false);
            merchantSkill.setFont(labelFont);
            merchantSkill.setEditable(false);
            engineerSkill.setFont(labelFont);
            engineerSkill.setEditable(false);
        }

        private void confirm(JTextField textBox) {
            if (textBox.getText().length() == 0) {
                System.out.println("Please choose a name");
            } else if (game.getDifficulty() == -1) {
                System.out.println("Please choose a getDifficulty()");
            } else {
                if (player.getUsedSkills() != player.getSkillPoints()) {
                    System.out.println("Not all skill points have been applied");
                }
                player.setName(textBox.getText());
                game.getScreens().put("displayScreen", new Screen.DisplayScreen(game, player));
                game.switchToScreen(game.getScreens().get("displayScreen"));
            }
        }

        private void reset(JTextField p, JTextField f, JTextField m, JTextField e,
                           JTextField totalPoints) {
            player.resetSkillPoints(4 * (4 - game.getDifficulty()));
            resetBoxes(p, f, m, e);
            game.setSkillpoints(player.getSkillPoints());
            totalPoints.setText("Skill Points Remaining: " + game.getSkillpoints());
        }

        private void resetBoxes(JTextField p, JTextField f, JTextField m, JTextField e) {
            int[] skills = player.getSkills();
            p.setText("Pilot: " + skills[0]);
            f.setText("Fighter: " + skills[1]);
            m.setText("Merchant: " + skills[2]);
            e.setText("Engineer: " + skills[3]);
        }

        private void changeSkillPoint(int skillType, boolean upOrDown,
                                     JTextField totalPoints, JTextField skillLabel) {
            int p = 0;
            int f = 0;
            int m = 0;
            int e = 0;
            String skillName = "";
            switch (skillType) {
            case 0:
                p++;
                skillName = "Pilot";
                break;
            case 1:
                f++;
                skillName = "Fighter";
                break;
            case 2:
                m++;
                skillName = "Merchant";
                break;
            case 3:
                e++;
                skillName = "Engineer";
                break;
            default:
                break;
            }
            if (!upOrDown) {
                if (player.getSkillPoints() == player.getUsedSkills()) {
                    System.out.println("Error: Out of getSkillpoints()");
                } else {
                    player.addUsedSkills();
                    game.decreaseSkillpoints();
                    totalPoints.setText("Skill Points Remaining: " + game.getSkillpoints());
                    player.levelSkills(p, f, m, e);
                    skillLabel.setText(skillName + ": " + player.getSkills()[skillType]);
                }
            } else {
                if (player.getSkills()[skillType] == 0) {
                    System.out.println("Error: cannot reduce from zero");
                } else {
                    player.reduceUsedSkills();
                    game.increaseSkillpoints();
                    totalPoints.setText("Skill Points Remaining: " + game.getSkillpoints());
                    player.levelSkills(p * -1, f * -1, m * -1, e * -1);
                    skillLabel.setText(skillName + ": " + player.getSkills()[skillType]);
                }
            }
        }

        private void randomize(JTextField pilotSkill, JTextField fighterSkill,
                              JTextField merchantSkill, JTextField engineerSkill,
                              JTextField totalPoints) {
            if (game.getDifficulty() == -1) {
                System.out.println("Choose a Difficulty");
            } else {
                player.resetSkillPoints(4 * (4 - game.getDifficulty()));
                resetBoxes(pilotSkill, fighterSkill, merchantSkill, engineerSkill);
                game.setSkillpoints(player.getSkillPoints());
                totalPoints.setText("Skill Points Remaining: " + game.getSkillpoints());
                int random;
                for (int i = 0; i < player.getSkillPoints(); i++) {
                    random = (int) (Math.random() * 4 + 1);
                    switch (random) {
                    case 1:
                        player.addUsedSkills();
                        game.decreaseSkillpoints();
                        totalPoints.setText("Skill Points Remaining: " + game.getSkillpoints());
                        player.levelSkills(1, 0, 0, 0);
                        pilotSkill.setText("Pilot: " + player.getSkills()[0]);
                        break;
                    case 2:
                        player.addUsedSkills();
                        game.decreaseSkillpoints();
                        totalPoints.setText("Skill Points Remaining: " + game.getSkillpoints());
                        player.levelSkills(0, 1, 0, 0);
                        fighterSkill.setText("Fighter: " + player.getSkills()[1]);
                        break;
                    case 3:
                        player.addUsedSkills();
                        game.decreaseSkillpoints();
                        totalPoints.setText("Skill Points Remaining: " + game.getSkillpoints());
                        player.levelSkills(0, 0, 1, 0);
                        merchantSkill.setText("Merchant: " + player.getSkills()[2]);
                        break;
                    case 4:
                        player.addUsedSkills();
                        game.decreaseSkillpoints();
                        totalPoints.setText("Skill Points Remaining: " + game.getSkillpoints());
                        player.levelSkills(0, 0, 0, 1);
                        engineerSkill.setText("Engineer: " + player.getSkills()[3]);
                        break;
                    default:
                        System.out.println("OOPIE");
                    }
                }
            }
        }

        private void difficultySetter(int difficulty, JTextField p, JTextField f, JTextField m,
                                      JTextField e, JTextField totalPoints) {
            game.setDifficulty(difficulty);
            player.resetSkillPoints(4 * (4 - game.getDifficulty()));
            resetBoxes(p, f, m, e);
            game.setSkillpoints(player.getSkillPoints());
            totalPoints.setText("Skill Points Remaining: " + game.getSkillpoints());
            player.setCredits((int) (Math.pow((3 - game.getDifficulty()), 2)) * 100);
        }
    }

    //the display screen
    public static class DisplayScreen extends Screen {
        private JPanel plStats;

        //constructor that creates the display screen
        public DisplayScreen(Game game, Player player) {
            String cargo = "Available Cargo Space: ";
            String fuel = "Current Fuel: ";
            JPanel col1 = new JPanel();
            //Code For Player Stats in the Top Left Corner of the Screen
            plStats = new JPanel();
            plStats.setBorder(BorderFactory.createTitledBorder(BorderFactory
                            .createBevelBorder(BevelBorder.LOWERED),
                    "Player Stats:", TitledBorder.LEFT, TitledBorder.TOP));
            plStats.setLayout(new BoxLayout(plStats, BoxLayout.Y_AXIS));
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            JLabel rowName = new JLabel("Name: " + player.getName());
            JLabel rowDifficulty = new JLabel();
            if (game.getDifficulty() == 0) {
                rowDifficulty.setText("Difficulty: Easy");
            } else if (game.getDifficulty() == 1) {
                rowDifficulty.setText("Difficulty: Medium");
            } else if (game.getDifficulty() == 2) {
                rowDifficulty.setText("Difficulty: Hard");
            }
            int[] skills = player.getSkills();
            JLabel rowCredits = new JLabel("Schmeckles: " + player.getCredits());
            JLabel rowPilot = new JLabel("Pilot Skill: " + skills[0]);
            JLabel rowFight = new JLabel("Fighter Skill: " + skills[1]);
            JLabel rowMerch = new JLabel("Merchant Skill: " + skills[2]);
            JLabel rowEngin = new JLabel("Engineer Skill: " + skills[3]);
            Ship currentShip = player.getCurrentShip();
            JLabel shipHealth = new JLabel("Ship Health: " + currentShip.getCurrentHealth()
                    + "/" + currentShip.getMaxHealth());
            JLabel cargoSpace = new JLabel(cargo + currentShip.getAvailableCargoSpots()
                    + "/" + currentShip.getMaxCargoSpace());
            JLabel fuelTank = new JLabel("Total Fuel: " + currentShip.getCurrentFuel()
                    + "/" + currentShip.getFuelCap());
            JLabel karmaNum = new JLabel("Karma: " + player.getKarma());
            plStats.add(rowName);
            plStats.add(rowDifficulty);
            plStats.add(rowCredits);
            plStats.add(rowPilot);
            plStats.add(rowFight);
            plStats.add(rowMerch);
            plStats.add(rowEngin);
            plStats.add(shipHealth);
            plStats.add(cargoSpace);
            plStats.add(karmaNum);
            plStats.add(fuelTank);

            player.setRegion(Game.getSetting().getRegions().get(0));
            JPanel regionDisplay = new JPanel();
            updateRegionDisplay(regionDisplay, player);

            JButton travelButton = new JButton("Travel Menu");
            travelButton.setFont(new Font(travelButton.getFont().getName(),
                    travelButton.getFont().getStyle(), 20));

            JFrame travelFrame = Travel.getTravelFrame(player, regionDisplay,
                    this, game.getDifficulty());

            travelFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    setDisplayEnabled();
                }

                @Override
                public void windowDeactivated(WindowEvent e) {
                    fuelTank.setText("Total Fuel: " + currentShip.getCurrentFuel()
                            + "/" + currentShip.getFuelCap());
                }
            });

            travelButton.addActionListener(e -> {
                ((JLabel) ((Container) ((Container) ((Container) ((Container) travelFrame.
                        getComponent(0))
                        .getComponent(1)).getComponent(0)).getComponent(0))
                        .getComponent(12)).setText("Current Fuel: "
                        + player.getCurrentShip().getCurrentFuel());
                travelFrame.setVisible(true);
                this.setEnabled(false);
            });

            regionDisplay.setLayout(new BoxLayout(regionDisplay, BoxLayout.Y_AXIS));
            col1.setLayout(new BoxLayout(col1, BoxLayout.Y_AXIS));
            col1.add(plStats);
            col1.add(travelButton);
            JButton marketButton = new JButton("Visit Market");
            marketButton.setFont(new Font(marketButton.getFont().getName(),
                    marketButton.getFont().getStyle(), 20));
            marketButton.addActionListener(e -> {
                JFrame marketFrame = player.getRegion().getMarket().getMarketFrame(player);
                marketFrame.setVisible(true);
                this.setEnabled(false);
                marketFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        setDisplayEnabled();
                        rowCredits.setText("Schmeckles: " + player.getCredits());
                        cargoSpace.setText(cargo + currentShip.getAvailableCargoSpots()
                                + "/" + currentShip.getMaxCargoSpace());
                        fuelTank.setText("Total Fuel: " + currentShip.getCurrentFuel()
                                + "/" + currentShip.getFuelCap());
                    }
                });
            });
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.add(col1);
            this.add(regionDisplay);
            this.add(marketButton);
        }

        public static void updateRegionDisplay(JPanel regionDisplay, Player player) {
            System.out.println("Updated Region Display.");
            regionDisplay.removeAll();
            regionDisplay.setBorder(BorderFactory.createTitledBorder(BorderFactory
                    .createBevelBorder(BevelBorder.LOWERED)));
            JLabel curRegion = new JLabel("Current Region: " + player.getRegion().getName());
            JLabel coordinates = new JLabel("Coordinates: (" + player.getRegion().getXPos()
                    + ", " + player.getRegion().getYPos() + ")");
            JLabel techLevel = new JLabel("Tech Level: " + player.getRegion().getTechLevel());
            curRegion.setFont(new Font(curRegion.getFont().getName(),
                    curRegion.getFont().getStyle(), 30));
            coordinates.setFont(new Font(coordinates.getFont().getName(),
                    coordinates.getFont().getStyle(), 30));
            techLevel.setFont(new Font(techLevel.getFont().getName(),
                    techLevel.getFont().getStyle(), 30));
            regionDisplay.add(curRegion);
            regionDisplay.add(coordinates);
            regionDisplay.add(techLevel);
            regionDisplay.repaint();
        }

        public void updatePlayerDisplay(Player player) {
            Component[] labels = plStats.getComponents();
            ((JLabel) labels[2]).setText("Schmeckles: " + player.getCredits());
            ((JLabel) labels[7]).setText("Ship Health: "
                    + player.getCurrentShip().getCurrentHealth()
                    + "/" + player.getCurrentShip().getMaxHealth());
            ((JLabel) labels[8]).setText("Available Cargo Space: " + player.getCurrentShip()
                    .getAvailableCargoSpots() + "/" + player.getCurrentShip().getMaxCargoSpace());
            ((JLabel) labels[9]).setText("Karma: " + player.getKarma());
        }

        private void setDisplayEnabled() {
            this.setEnabled(true);
        }
    }
}