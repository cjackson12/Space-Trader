import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;

public class Merchant extends NPC {
    private Item merchItem;
    private int quantity;
    private int price;

    public Merchant(Player player) {
        merchItem = new Item(player.getCurrentShip().getAvailableCargoSpots());
    }

    public JFrame getFrame(Player player) {
        JFrame encounterScreen = new JFrame("A Wild Merchant Has Appeared!");
        encounterScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        encounterScreen.setBounds(100, 100, 700, 400);

        JPanel formatter = new JPanel();
        formatter.setLayout(new BoxLayout(formatter, BoxLayout.Y_AXIS));

        double priceOfOne = merchItem.getBasePrice() * ((Math.random() * 3) + 1);
        System.out.println(Math.min((player.getCurrentShip().getAvailableCargoSpots()
                        / merchItem.getWeight()),
                (player.getCredits() / priceOfOne)));
        quantity = (int) ((Math.random() * Math.min((player.getCurrentShip()
                .getAvailableCargoSpots()
                / merchItem.getWeight()), (int) (player.getCredits() / priceOfOne))) + 1);
        price = (int) (priceOfOne * quantity);
        System.out.println(quantity);

        JLabel offer = new JLabel("", SwingConstants.CENTER);
        if (quantity == 1) {
            offer.setText("Hello! I will offer to sell you one"
                    + " " + merchItem.getType().toString() + " for " + price + " Schmeckles.");
        } else {
            offer.setText("Hello! I will offer to sell you " + quantity
                    + " " + merchItem.getType().toString() + "s for "
                    + price + " Credits.");
        }
        offer.setFont(new Font(offer.getFont().getName(),
                offer.getFont().getStyle(), 20));
        formatter.add(offer);

        JLabel creditAmt = new JLabel("You have " + player.getCredits()
                + " Schmeckles.", SwingConstants.CENTER);
        creditAmt.setFont(new Font(creditAmt.getFont().getName(),
                creditAmt.getFont().getStyle(), 20));
        formatter.add(creditAmt);
        //create buttons
        formatter.add(buyFromMerchant(player, encounterScreen));
        formatter.add(continueTravel(player, encounterScreen));
        formatter.add(robMerchant(player, encounterScreen, Game.getInstance()));
        formatter.add(negotiateMerchant(player, encounterScreen, offer));

        encounterScreen.add(formatter);
        encounterScreen.pack();
        return encounterScreen;
    }

    //choice 1 : buys items
    private JButton buyFromMerchant(Player player, JFrame encounterScreen) {
        player.increaseKarma();
        JButton button = new JButton("Buy");
        button.setFont(new Font(button.getFont().getName(),
                button.getFont().getStyle(), 25));
        button.addActionListener(e -> {
            Ship ship = player.getCurrentShip();
            player.setCredits(player.getCredits() - price);
            for (int i = 0; i < quantity; i++) {
                ship.addToCargoHold(merchItem);
            }
            encounterScreen.setVisible(false);

        });
        return button;
    }

    //choice 2 : ignore merchant & travel to place
    private JButton continueTravel(Player player, JFrame encounterScreen) {
        JButton button = new JButton("Pass Up Offer");
        button.setFont(new Font(button.getFont().getName(),
                button.getFont().getStyle(), 25));
        button.addActionListener(e -> {
            encounterScreen.setVisible(false);
        });
        return button;
    }

    // choice 3: rob merchant
    private JButton robMerchant(Player player, JFrame encounterScreen, Game game) {
        double percentChance = 20 * Math.pow(player.getSkills()[1], .25);
        JLabel popupMessage = new JLabel();
        String buttonText = String.format("Rob Merchant; %.1f%% Chance", percentChance);
        JButton button = new JButton(buttonText);
        button.setFont(new Font(button.getFont().getName(),
                button.getFont().getStyle(), 25));
        button.addActionListener(e -> {
            player.decreaseKarma();
            if (100 * Math.random() < percentChance) {
                int itemsStolen = (int) (Math.random() * quantity) + 1;
                popupMessage.setText("Success! You stole " + itemsStolen + " "
                        + merchItem.getType() + "s.");
                merchItem.markAsStolen();
                for (int i = 0; i < itemsStolen; i++) {
                    player.getCurrentShip().addToCargoHold(merchItem);
                }
            } else {
                if (player.getCurrentShip().getCurrentHealth() == 1) {
                    game.switchToScreen(game.getScreens().get("losingScreen"));
                }
                int lostHealth = player.getCurrentShip().getCurrentHealth() / 2;
                popupMessage.setText("Failure! You lost " + lostHealth + " Health Points.");
                player.getCurrentShip().setCurrentHealth(player
                        .getCurrentShip().getCurrentHealth() - lostHealth);
                if (player.getCurrentShip().getCurrentHealth() <= 0) {
                    game.switchToScreen(game.getScreens().get("losingScreen"));

                }
            }
            JFrame popup = new JFrame();
            popup.addWindowListener(new WindowAdapter() {
                @Override
                public void windowDeactivated(java.awt.event.WindowEvent e) {
                    popup.setVisible(false);
                }
            });
            popupMessage.setFont(new Font(popupMessage.getFont().getName(),
                    popupMessage.getFont().getStyle(), 20));
            popup.add(popupMessage);
            popup.pack();
            encounterScreen.setVisible(false);
            popup.setVisible(true);
            popup.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        });
        return button;
    }


    //choice 4: negotiate
    private JButton negotiateMerchant(Player player, JFrame encounterScreen, JLabel offer) {
        double odds = (player.getSkills()[2] * (90 / 16.0)) + 10;
        String buttonText = String.format("Negotiate with Merchant; %.1f%% Chance", odds);
        JButton button = new JButton(buttonText);
        button.setFont(new Font(button.getFont().getName(),
                button.getFont().getStyle(), 25));

        button.addActionListener(e -> {
            double rng = Math.random() * 100;
            if (rng < odds) {
                price /= 2;
                offer.setText("Alright, I'll haggle with ya... How about "
                        + price + "Schmeckles for the "
                        + quantity + " " + merchItem.getType() + " instead?");
                button.setEnabled(false);
            } else {
                price *= 2;
                offer.setText("Well now you've just made me angry... You can give me " + price
                        + " Schmeckles for the " + quantity + " "
                        + merchItem.getType() + ", final offer.");
                button.setEnabled(false);
            }
            encounterScreen.pack();
        });
        return button;
    }
}
