import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Travel {

    public static JFrame getTravelFrame(Player player, JPanel regionDisplay,
                                        Screen.DisplayScreen screen, int difficulty) {
        String fuel = "Current Fuel: ";
        JFrame travelFrame = new JFrame();
        travelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        travelFrame.setBounds(50, 50, 1200, 400);
        travelFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel regions = new JPanel();
        regions.setLayout(new BoxLayout(regions, BoxLayout.Y_AXIS));
        JLabel regionTitle = new JLabel("Regions:");
        regionTitle.setFont(new Font(regionTitle.getFont().getName(),
                regionTitle.getFont().getStyle(), 30));
        regions.add(regionTitle);
        ArrayList<Region> listOfRegions = Game.getSetting().getRegions();
        Map<Region, JRadioButton> rButtons = new HashMap<>();
        ButtonGroup bGroup = new ButtonGroup();
        for (Region r: listOfRegions) {
            rButtons.put(r, new JRadioButton(r.toString() + ", Distance: "
                    + (int) (Math.sqrt(Math.pow(listOfRegions.get(0).getXPos()
                    - r.getXPos(), 2)
                    + Math.pow(listOfRegions.get(0).getYPos()
                    - r.getYPos(), 2)))));
            rButtons.get(r).setFont(new Font(rButtons.get(r).getFont().getName(),
                    rButtons.get(r).getFont().getStyle(), 15));
            bGroup.add(rButtons.get(r));
            regions.add(rButtons.get(r));
        }
        player.setRegion(listOfRegions.get(0));
        rButtons.get(listOfRegions.get(0)).setEnabled(false);
        JButton travel = new JButton("Travel");
        travel.setFont(new Font(travel.getFont().getName(), travel.getFont().getStyle(), 20));
        regions.add(travel);
        JLabel curFuel = new JLabel(fuel + player.getCurrentShip().getCurrentFuel());
        JLabel fuelCost = new JLabel("Fuel Cost: " + 0);
        curFuel.setFont(new Font(curFuel.getFont().getName(), curFuel.getFont().getStyle(),
                20));
        fuelCost.setFont(new Font(fuelCost.getFont().getName(), fuelCost.getFont().getStyle(),
                20));
        regions.add(curFuel);
        regions.add(fuelCost);
        GalaxyPanel gPanel = new GalaxyPanel(player);
        for (Map.Entry<Region, JRadioButton> entry : rButtons.entrySet()) {
            final Region region = entry.getKey();
            final JRadioButton button = entry.getValue();
            button.addActionListener(e -> {
                player.setSelectedRegion(region);
                fuelCost.setText("Fuel Cost: " + player.fuelCost());
                gPanel.repaint();
            });
        }
        travel.addActionListener(e -> {
            if (player.getSelectedRegion() == null) {
                System.out.println("Select a valid region to travel to");
            } else if (!player.hasEnoughFuel()) {
                System.out.println("Not Enough Fuel in Tank to travel there");
            } else {
                int costOfFuel = player.fuelCost();
                player.setRegion(player.getSelectedRegion());
                double probability = Math.random();
                NPC newNPC = NPC.getRandomNPC(difficulty, player);
                if (newNPC != null) {
                    JFrame npcFrame = newNPC.getFrame(player);
                    npcFrame.setVisible(true);
                    npcFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowDeactivated(WindowEvent e) {
                            screen.updatePlayerDisplay(player);
                        }
                    });
                }

                player.getCurrentShip().setCurrentFuel(player.getCurrentShip().getCurrentFuel()
                        - costOfFuel);
                curFuel.setText("Current Fuel: " + player.getCurrentShip().getCurrentFuel());
                fuelCost.setText("Fuel Cost: " + 0);
                rButtons.get(player.getLastRegion()).setEnabled(true);
                rButtons.get(player.getRegion()).setEnabled(false);
                Screen.DisplayScreen.updateRegionDisplay(regionDisplay, player);
                for (Map.Entry<Region, JRadioButton> entry : rButtons.entrySet()) {
                    Region region = entry.getKey();
                    JRadioButton button = entry.getValue();
                    button.setText(region.toString() + ", Distance: "
                            + calculateDistance(player, region));
                }
                travelFrame.setVisible(false);
                screen.setEnabled(true);

            }
        });
        travelFrame.add(regions);
        travelFrame.add(gPanel);

        travelFrame.pack();
        return travelFrame;
    }

    private static int calculateDistance(Player player, Region region) {
        return (int) (Math.sqrt(Math.pow(player.getRegion().getXPos()
                - region.getXPos(), 2) + Math.pow(player.getRegion().getYPos()
                - region.getYPos(), 2)));
    }
}
