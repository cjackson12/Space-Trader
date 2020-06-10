import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class Police extends NPC {
    private EquivalentItemCounter<Item> stolenGoods;

    public JFrame getFrame(Player player) {
        JFrame encounterScreen = new JFrame("Uh Oh! The police are here!");
        encounterScreen.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        encounterScreen.setBounds(100, 100, 700, 400);

        JPanel formatter = new JPanel();
        formatter.setLayout(new BoxLayout(formatter, BoxLayout.Y_AXIS));

        stolenGoods = new EquivalentItemCounter<>();
        for (Item i: player.getCurrentShip().getCargo()) {
            if (.9 < Math.random() || i.isStolen()) {
                stolenGoods.addItem(i);
            }
        }
        if (stolenGoods.size() == 0) {
            stolenGoods.addItem(player.getCurrentShip().getCargo().get(0));
        }
        String inquiry = "Halt! You have violated the law. Surrender your stolen items. "
                + "Those goods are the ";
        for (Item i: stolenGoods) {
            if (stolenGoods.getCount(i) > 1) {
                inquiry += "" + stolenGoods.getCount(i) + " " + i.getType() + "s, ";
            } else {
                inquiry += "" + stolenGoods.getCount(i) + " " + i.getType() + ", ";
            }
        }
        inquiry += "you are carrying. Forfeit them to me now or pay the consequences.";
        JLabel copText = new JLabel(inquiry);
        copText.setFont(new Font(copText.getFont().getName(),
                copText.getFont().getStyle(), 15));
        formatter.add(copText);
        formatter.add(forfeitItem(player, encounterScreen));
        formatter.add(flee(player, encounterScreen, Game.getInstance()));
        formatter.add(fight(player, encounterScreen, Game.getInstance()));

        encounterScreen.add(formatter);
        encounterScreen.pack();
        return encounterScreen;
    }

    public JButton forfeitItem(Player player, JFrame encounterScreen) {
        JButton button = new JButton("Forfeit the Items");
        button.setFont(new Font(button.getFont().getName(),
                button.getFont().getStyle(), 15));

        button.addActionListener(e -> {
            player.increaseKarma();
            for (Item i: stolenGoods) {
                for (int j = 0; j < stolenGoods.getCount(i); j++) {
                    player.getCurrentShip().removeFromCargoHold(i);
                }
            }
            encounterScreen.setVisible(false);
        });
        return button;
    }

    public JButton flee(Player player, JFrame encounterScreen, Game game) {
        JButton button = new JButton("Flee");
        button.setFont(new Font(button.getFont().getName(),
                button.getFont().getStyle(), 15));
        button.addActionListener(e -> {
            player.decreaseKarma();
            player.setRegion(player.getLastRegion());
            Ship ship = player.getCurrentShip();
            int[] skills = player.getSkills();
            double successfulFlee = Math.random() + (skills[0] / 32.0);
            JFrame popup = new JFrame();
            popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            popup.addWindowListener(new WindowAdapter() {
                @Override
                public void windowDeactivated(WindowEvent e) {
                    popup.setVisible(false);
                }
            });
            JLabel popupMessage = new JLabel("");
            if (successfulFlee > .75) {
                popupMessage.setText("You outmaneuvered the cops! "
                        + "You feel really cool. You head back to "
                        + player.getRegion().getName() + ".");
                popup.add(popupMessage);
            } else {
                int fine = 50;
                if (player.getCredits() < 50) {
                    fine = player.getCredits();
                }
                popupMessage.setText("\'Stop right there, criminal scum! "
                        + "Nobody breaks the law on my watch!"
                        + " You will pay with your blood! I'm confiscating "
                        + "your stolen goods, and "
                        + " you will pay your " + fine + " Schmeckles "
                        + " fine, or its off to prison!\'"
                        + "\n\n You pay your fine, and head back to "
                        + player.getRegion().getName() + ".");
                player.setCredits(player.getCredits() - fine);
                if (ship.getCurrentHealth() == 1) {
                    game.switchToScreen(game.getScreens().get("losingScreen"));
                } else {

                    ship.setCurrentHealth(ship.getCurrentHealth() / 2);
                    if (ship.getCurrentHealth() <= 0) {
                        game.switchToScreen(game.getScreens().get("losingScreen"));

                    }

                }

                for (Item i: stolenGoods) {
                    for (int j = 0; j < stolenGoods.getCount(i); j++) {
                        player.getCurrentShip().removeFromCargoHold(i);
                    }
                }
            }
            popup.add(popupMessage);
            popup.pack();
            popup.setVisible(true);
            encounterScreen.setVisible(false);
        });
        return button;
    }

    public JButton fight(Player player, JFrame encounterScreen, Game game) {
        JButton button = new JButton("Fight");
        button.setFont(new Font(button.getFont().getName(),
                button.getFont().getStyle(), 15));
        button.addActionListener(e -> {
            player.decreaseKarma();
            Ship ship = player.getCurrentShip();
            int[] skills = player.getSkills();
            double successfulFight = Math.random() + (skills[1] / 32.0);
            JFrame popup = new JFrame();
            popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            popup.addWindowListener(new WindowAdapter() {
                @Override
                public void windowDeactivated(WindowEvent e) {
                    popup.setVisible(false);
                }
            });
            JLabel popupMessage = new JLabel("");
            if (successfulFight < .75) {
                player.setCredits(0); // police takes all your money
                if (ship.getCurrentHealth() == 1) {
                    game.switchToScreen(game.getScreens().get("losingScreen"));
                } else {
                    ship.setCurrentHealth(ship.getCurrentHealth() / 2);
                    if (ship.getCurrentHealth() <= 0) {
                        game.switchToScreen(game.getScreens().get("losingScreen"));

                    }
                }
                popupMessage.setText("The police officer raided your ship and knocked you out. "
                        + "Upon awakening, you find the stolen goods gone and your wallet empty.");
                for (Item i: stolenGoods) {
                    for (int j = 0; j < stolenGoods.getCount(i); j++) {
                        player.getCurrentShip().removeFromCargoHold(i);
                    }
                }
            } else {
                popupMessage.setText("You successfully overcame the police officer, and "
                        + "continued on your way to " + player.getRegion().getName() + ".");
            }
            popup.add(popupMessage);
            popup.pack();
            popup.setVisible(true);
            encounterScreen.setVisible(false);
        });
        return button;
    }
}
