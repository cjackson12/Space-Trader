import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class Bandit extends NPC {

    private int creditsRequired;
    private int banditCredits;

    public JFrame getFrame(Player player) {
        JFrame encounterScreen = new JFrame("A Bandit approaches...");
        encounterScreen.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        encounterScreen.setBounds(100, 100, 700, 400);

        JPanel formatter = new JPanel();
        formatter.setLayout(new BoxLayout(formatter, BoxLayout.Y_AXIS));

        creditsRequired = (int) (Math.random() * player.getCredits() * 1.25);
        banditCredits = creditsRequired / 2;

        JLabel banditText = new JLabel();
        banditText.setFont(new Font(banditText.getFont().getName(),
                banditText.getFont().getStyle(), 15));
        banditText.setText("Give me your money or I won't let ya pass!! I want " + creditsRequired
                + " Schmeckles, or all your items if you can't pay!");

        formatter.add(banditText);
        formatter.add(chooseToPay(player, encounterScreen, Game.getInstance()));
        formatter.add(flee(player, encounterScreen, Game.getInstance()));
        formatter.add(fight(player, encounterScreen, Game.getInstance()));

        encounterScreen.add(formatter);
        encounterScreen.pack();
        return encounterScreen;
    }

    public JButton chooseToPay(Player player, JFrame encounterScreen, Game game) {
        JButton button = new JButton("Pay The Bandit");
        button.setFont(new Font(button.getFont().getName(),
                button.getFont().getStyle(), 15));


        button.addActionListener(e -> {
            encounterScreen.setVisible(false);
            Ship ship = player.getCurrentShip();
            if (creditsRequired <= player.getCredits()) {
                player.setCredits(player.getCredits() - creditsRequired);
                // pay credits (just lose them from player)
            } else if (ship.getCargo().size() != 0) {
                List<Item> newCargo = ship.getCargo();
                for (Item i: newCargo) {
                    ship.removeFromCargoHold(i);
                }
                // lose all inventory
            } else {
                if (ship.getCurrentHealth() == 1) {
                    game.switchToScreen(game.getScreens().get("losingScreen"));
                } else {
                    ship.setCurrentHealth(ship.getCurrentHealth() / 2);
                    if (ship.getCurrentHealth() <= 0) {
                        game.switchToScreen(game.getScreens().get("losingScreen"));

                    }
                }
                // ship gets damaged
            }
        });
        return button;
    }

    public JButton flee(Player player, JFrame encounterScreen, Game game) {
        int[] skills = player.getSkills();
        double successfulFlee = Math.random() + (skills[0] / 32.0);
        String format = String.format("Flee from the Bandit; %.1f%%",
                Math.min(successfulFlee * 100, 100.0));
        JButton button = new JButton(format);
        button.setFont(new Font(button.getFont().getName(),
                button.getFont().getStyle(), 15));

        button.addActionListener(e -> {
            encounterScreen.setVisible(false);
            Ship ship = player.getCurrentShip();
            player.setRegion(player.getLastRegion());
            JFrame popup = new JFrame("Result");
            JLabel popupMessage = new JLabel();
            popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            popup.addWindowListener(new WindowAdapter() {
                @Override
                public void windowDeactivated(WindowEvent e) {
                    popup.setVisible(false);
                }
            });
            if (successfulFlee > .75) {
                popupMessage.setText("You successfully fleed back to "
                        + player.getRegion().getName() + ".");
            } else {
                popupMessage.setText("The bandit caught you, and stole all your money and "
                        + "partially destroyed your ship. You then go back to "
                        + player.getRegion().getName() + ".");
                player.setCredits(0);
                if (ship.getCurrentHealth() == 1) {
                    game.switchToScreen(game.getScreens().get("losingScreen"));
                } else {
                    ship.setCurrentHealth(ship.getCurrentHealth() / 2);
                    if (ship.getCurrentHealth() <= 0) {
                        game.switchToScreen(game.getScreens().get("losingScreen"));

                    }
                }
            }
            popupMessage.setFont(new Font(popupMessage.getFont().getName(),
                    popupMessage.getFont().getStyle(), 15));
            popup.add(popupMessage);
            popup.pack();
            popup.setVisible(true);
        });
        return button;
    }

    public JButton fight(Player player, JFrame encounterScreen, Game game) {
        int[] skills = player.getSkills();
        double successfulFight = Math.random() + (skills[1] / 32.0);
        String format = String.format("Fight The Bandit; %.1f%%",
                Math.min(successfulFight * 100, 100.0));
        JButton button = new JButton(format);
        button.setFont(new Font(button.getFont().getName(),
                button.getFont().getStyle(), 15));

        button.addActionListener(e -> {
            player.increaseKarma();
            encounterScreen.setVisible(false);
            Ship ship = player.getCurrentShip();
            JFrame popup = new JFrame("Result");
            JLabel popupMessage = new JLabel();
            popupMessage.setFont(new Font(popupMessage.getFont().getName(),
                    popupMessage.getFont().getStyle(), 15));
            popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            popup.addWindowListener(new WindowAdapter() {
                @Override
                public void windowDeactivated(WindowEvent e) {
                    popup.setVisible(false);
                }
            });
            if (successfulFight > .75) {
                popupMessage.setText("You beat up the bandit, took his " + banditCredits
                        + " Schmeckles and headed on your way.");
                player.setCredits(player.getCredits() + (int) (Math.random() * banditCredits));
            } else {
                popupMessage.setText("The bandit beat you up, stole all your money,"
                        + "and damaged your ship.");
                player.setCredits(0);
                if (ship.getCurrentHealth() == 1) {
                    game.switchToScreen(game.getScreens().get("losingScreen"));
                } else {
                    ship.setCurrentHealth(ship.getCurrentHealth() / 2);
                    if (ship.getCurrentHealth() <= 0) {
                        game.switchToScreen(game.getScreens().get("losingScreen"));

                    }
                }

            }
            popup.add(popupMessage);
            popup.pack();
            popup.setVisible(true);
        });
        return button;
    }

}


