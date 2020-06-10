import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class GalaxyPanel extends JPanel {
    private Player player;
    GalaxyPanel(Player p) {
        player = p;
    }

    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        ArrayList<Region> listOfRegions = Game.getSetting().getRegions();
        g.fillRect(0, 0, 400, 400);

        g.setColor(Color.WHITE);
        int temp;
        for (Region listOfRegion : listOfRegions) {
            temp = listOfRegion.getXPos() + 200;
            g.fillOval(temp, listOfRegion.getYPos() + 200, 3, 3);
        }
        g.setColor(Color.RED);
        if (player.getSelectedRegion() != null) {
            temp = player.getRegion().getXPos() + 201;
            int temp2 = player.getRegion().getYPos() + 201;
            int temp3 = player.getSelectedRegion().getXPos() + 201;
            g.drawLine(temp, temp2, temp3, player.getSelectedRegion().getYPos() + 201);
        }

    }
}
