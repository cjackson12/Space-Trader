import javax.swing.*;
import java.awt.*;

public class Region {
    // private attributes
    private int xPos;
    private int yPos;
    private Market market;
    private boolean hasWinCon;

    public Market getMarket() {
        return market;
    }

    public enum TechLevel {
        PREAG(0), AGRICULTURE(1), MEDIEVAL(2), RENAISSANCE(3),
        INDUSTRIAL(4), MODERN(5), FUTURISTIC(6);
        private int num;
        public int getNum() {
            return num;
        }
        TechLevel(int num) {
            this.num = num;
        }
    }
    private TechLevel techLevel;
    private String name;

    //Constructor with params
    public Region(int xPosition, int yPosition, TechLevel level, String startName, boolean canWin) {
        xPos = xPosition;
        yPos = yPosition;
        techLevel = level;
        name = startName;
        hasWinCon = canWin;
        market = new Market(techLevel, hasWinCon);
    }



    public int getXPos() {
        return xPos;
    }
    public int getYPos() {
        return yPos;
    }
    public TechLevel getTechLevel() {
        return techLevel;
    }
    public String getName() {
        return name;
    }

    public String toString() {
        return name + ": (" + xPos + ", " + yPos + "), " + techLevel;
    }
}