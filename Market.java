import javax.swing.*;
//import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.LinkedList;

public class Market {

    private static final double INITIAL_BUYING_INCREASE = 0.5;
    private Region.TechLevel techLevel;
    private Item[] inStock;
    private boolean buyUniverse;

    Market(Region.TechLevel techLevel, boolean hasWinCon) {
        this.techLevel = techLevel;
        buyUniverse = hasWinCon;
        generateStock();
    }

    private void generateStock() {
        inStock = new Item[6];
        inStock[0] = new Item(Item.ItemType.FUEL);
        inStock[1] = new Item(Item.ItemType.HEALTH_KIT);
        inStock[2] = new Item(Item.ItemType.WEAPON);

        switch (techLevel) {
        case PREAG:
            inStock[3] = new Item(Item.ItemType.BIRDPERSON_SEED);
            inStock[4] = new Item(Item.ItemType.PICKLE);
            break;
        case AGRICULTURE:
            inStock[3] = new Item(Item.ItemType.PICKLE);
            inStock[4] = new Item(Item.ItemType.PLUMBUS);
            break;
        case MEDIEVAL:
            inStock[3] = new Item(Item.ItemType.PLUMBUS);
            inStock[4] = new Item(Item.ItemType.TURBULENT_JUICE);
            break;
        case RENAISSANCE:
            inStock[3] = new Item(Item.ItemType.TURBULENT_JUICE);
            inStock[4] = new Item(Item.ItemType.LOVE_POTION);
            break;
        case INDUSTRIAL:
            inStock[3] = new Item(Item.ItemType.LOVE_POTION);
            inStock[4] = new Item(Item.ItemType.MEESEKS_BOX);
            break;
        case MODERN:
            inStock[3] = new Item(Item.ItemType.MEESEKS_BOX);
            inStock[4] = new Item(Item.ItemType.TIME_STABILIZING_COLLAR);
            break;
        case FUTURISTIC:
            inStock[3] = new Item(Item.ItemType.TIME_STABILIZING_COLLAR);
            inStock[4] = new Item(Item.ItemType.CONCENTRATED_DARK_MATTER);
            break;
        default:
            inStock[3] = new Item(Item.ItemType.BIRDPERSON_SEED);
            inStock[4] = new Item(Item.ItemType.PICKLE);
        }

        Item.ItemType tempType = Item.ItemType.values()
                [(int) (Math.random() * 7) + 3];
        inStock[5] = new Item(tempType);
        if (buyUniverse) {
            inStock[5] = new Item(Item.ItemType.UNIVERSE);
        }
    }

    public Item[] getInStock() {
        return inStock;
    }

    private void buy(Player player, Item item, JFrame marketFrame) throws GameError.MarketError {
        //check if the item is in stock, if not return false
        boolean isAvailable = false;
        for (Item i : inStock) {
            if (item.equals(i)) {
                isAvailable = true;
                break;
            }
        }
        if (!isAvailable) {
            throw new GameError.MarketError.ItemNotAvailable();
        }

        //check if the player can afford the item
        if (getBuyingPrice(player, item) > player.getCredits()) {
            throw new GameError.MarketError.CannotAffordItem();
        }

        if (item.getType().equals(Item.ItemType.UNIVERSE)) {
            marketFrame.setVisible(false);
            Game.winGame();
        }

        //check if the player has space for the item
        Ship ship = player.getCurrentShip();
        //if fuel
        if (item.getType().equals(Item.ItemType.FUEL)) {
            if (ship.getCurrentFuel() == ship.getFuelCap()) {
                throw new GameError.MarketError.FuelIsFull();
            }
            //buy the fuel
            player.setCredits(player.getCredits() - getBuyingPrice(player, item));
            ship.setCurrentFuel(ship.getCurrentFuel() + 1);
            return;
        }

        //if health kit
        if (item.getType().equals(Item.ItemType.HEALTH_KIT)) {
            if (ship.getCurrentHealth() >= ship.getMaxHealth()) {
                throw new GameError.MarketError.HealthIsFull();
            }
            //buy the health
            player.setCredits(player.getCredits() - getBuyingPrice(player, item));
            ship.setCurrentHealth(ship.getCurrentHealth() + 1);
            return;
        }

        //else regular item
        if (item.getWeight() > ship.getAvailableCargoSpots()) {
            throw new GameError.MarketError.NotEnoughSpace();
        }
        //buy the item
        player.setCredits(player.getCredits() - getBuyingPrice(player, item));
        ship.addToCargoHold(item);

    }

    private int getMarketPrice(Item item) {
        int orig = item.getBasePrice();
        int dif = item.getTechLevel() - techLevel.getNum();
        double multiplier = dif * .1 + 1;
        return (int) (orig * multiplier);
    }

    private int getBuyingPrice(Player player, Item item) {
        double increase = INITIAL_BUYING_INCREASE;
        int price = getMarketPrice(item);
        for (int i = 0; i < player.getSkills()[2]; i++) {
            increase *= 0.9;
        }
        int val = (int) (price * (1 + increase));
        if (item.getType().equals(Item.ItemType.FUEL)) {
            val -= player.getKarma();
            if (val <= 0) {
                val = 1;
            }
        }
        if (item.getType().equals(Item.ItemType.HEALTH_KIT)) {
            val -= (int) (player.getSkills()[2] * 0.5);
            if (val <= 0) {
                val = 1;
            }
        }
        return val;
    }

    JFrame getMarketFrame(Player player) {
        JFrame marketFrame = new JFrame();
        marketFrame.setBounds(50, 50, 800, 450);
        marketFrame.setLayout(new BorderLayout());

        final JLabel title = new JLabel(player.getRegion().getName()
                + " Marketplace, Tech Level: " + techLevel
                + "   Credits: " + player.getCredits()
                + "   Cargo Space: " + player.getCurrentShip().getAvailableCargoSpots()
                + "   Fuel: " + player.getCurrentShip().getCurrentFuel()
                + "   Health: " + player.getCurrentShip().getCurrentHealth());
        marketFrame.add(title, BorderLayout.PAGE_START);

        JPanel inventory = new JPanel();
        inventory.setLayout(new BoxLayout(inventory, BoxLayout.Y_AXIS));

        LinkedList<JPanel> inventorySlots = new LinkedList<>();

        for (Item i : player.getCurrentShip().getCargo()) {
            JPanel row = new JPanel();
            JLabel name = new JLabel(i.getType().toString());
            JButton sell = new JButton("Sell For: " + getMarketPrice(i));
            sell.addActionListener(e -> {
                player.getCurrentShip().removeFromCargoHold(i);
                player.setCredits(player.getCredits() + getMarketPrice(i));
                inventorySlots.remove(row);
                buildInventory(inventory, inventorySlots);
                title.setText(player.getRegion().getName()
                        + " Marketplace, Tech Level: " + techLevel
                        + "   Credits: " + player.getCredits()
                        + "   Cargo Space: " + player.getCurrentShip().getAvailableCargoSpots()
                        + "   Fuel: " + player.getCurrentShip().getCurrentFuel()
                        + "   Health: " + player.getCurrentShip().getCurrentHealth());
            });
            row.add(name);
            row.add(sell);
            inventorySlots.add(row);
        }

        buildInventory(inventory, inventorySlots);

        marketFrame.add(inventory, BorderLayout.LINE_START);

        JPanel stock = new JPanel();
        stock.setLayout(new BoxLayout(stock, BoxLayout.Y_AXIS));

        for (Item i : inStock) {
            JPanel row = new JPanel();
            JLabel name = new JLabel(i.getType().toString());
            if (i.getType().equals(Item.ItemType.UNIVERSE)) {
                name = new JLabel(player.getName() + "'s UNIVERSE");
            }
            JButton buy = new JButton("Buy For: " + getBuyingPrice(player, i));
            buy.addActionListener(e -> {
                try {
                    buy(player, i, marketFrame);
                } catch (GameError gameError) {
                    System.out.println("GameError");
                    return;
                }
                JPanel iRow = new JPanel();
                JLabel iName = new JLabel(i.getType().toString());
                JButton sell = new JButton("Sell For: " + getMarketPrice(i));
                sell.addActionListener(a -> {
                    player.getCurrentShip().removeFromCargoHold(i);
                    player.setCredits(player.getCredits() + getMarketPrice(i));
                    inventorySlots.remove(iRow);
                    buildInventory(inventory, inventorySlots);
                    title.setText(player.getRegion().getName()
                            + " Marketplace, Tech Level: " + techLevel
                            + "   Credits: " + player.getCredits()
                            + "   Cargo Space: " + player.getCurrentShip().getAvailableCargoSpots()
                            + "   Fuel: " + player.getCurrentShip().getCurrentFuel()
                            + "   Health: " + player.getCurrentShip().getCurrentHealth());
                });
                iRow.add(iName);
                iRow.add(sell);
                if (!(i.getType().equals(Item.ItemType.FUEL)
                        || i.getType().equals(Item.ItemType.HEALTH_KIT))) {
                    inventorySlots.add(iRow);
                }
                buildInventory(inventory, inventorySlots);
                title.setText(player.getRegion().getName()
                        + " Marketplace, Tech Level: " + techLevel
                        + "   Credits: " + player.getCredits()
                        + "   Cargo Space: " + player.getCurrentShip().getAvailableCargoSpots()
                        + "   Fuel: " + player.getCurrentShip().getCurrentFuel()
                        + "   Health: " + player.getCurrentShip().getCurrentHealth());
            });
            row.add(name);
            row.add(buy);
            stock.add(row);
        }
        marketFrame.add(stock, BorderLayout.LINE_END);
        return marketFrame;

    }

    private void buildInventory(JPanel inventory, LinkedList<JPanel> inventorySlots) {
        inventory.removeAll();
        Dimension d = new Dimension(0, 40);
        for (JPanel p : inventorySlots) {
            p.setSize(p.getWidth(), 40);
            inventory.add(p);
        }
        for (int i = inventorySlots.size(); i < 10; i++) {
            JPanel tempPanel = new JPanel();
            tempPanel.setSize(d);
            inventory.add(new JPanel());
        }
    }

}
