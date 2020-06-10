import javax.swing.*;

public class Player {

    private String name;

    //ints to keep track of skill points
    private int totalSkillPoints;
    private int usedSkillPoints;
    private int pilotSkill;
    private int fighterSkill;
    private int merchantSkill;
    private int engineerSkill;
    private int credits;
    private int karma; //scale from -10 to 10
    private Region lastRegion;
    private Region currentRegion;
    private Region selectedRegion;
    private Ship currentShip;

    Player() {
        currentShip = new Ship(Ship.ShipType.TYPE1);
        karma = 0;
    }

    String getName() {
        return name;
    }

    void setName(String newName) {
        name = newName;
    }

    void levelSkills(int pilot, int fighter, int merchant, int engineer) {
        pilotSkill += pilot;
        fighterSkill += fighter;
        merchantSkill += merchant;
        engineerSkill += engineer;
    }

    int[] getSkills() {
        return new int[]{pilotSkill, fighterSkill, merchantSkill, engineerSkill};
    }

    int getSkillPoints() {
        return totalSkillPoints;
    }

    int getUsedSkills() {
        return usedSkillPoints;
    }

    void reduceUsedSkills() {
        usedSkillPoints--;
    }

    void addUsedSkills() {
        usedSkillPoints++;
    }

    //Set Player Region
    void setRegion(Region newRegion) {
        lastRegion = currentRegion;
        currentRegion = newRegion;
    }

    //Get Player Region
    Region getRegion() {
        return currentRegion;
    }

    //Get the last region the player was in
    Region getLastRegion() {
        return lastRegion;
    }

    //Set the region that is selected
    void setSelectedRegion(Region r) {
        selectedRegion = r;
    }

    //Get the region that is selected
    Region getSelectedRegion() {
        return selectedRegion;
    }

    //Set Player credits
    void setCredits(int newCredits) {
        credits = newCredits;
    }

    //Get Player credits
    int getCredits() {
        return credits;
    }

    void resetSkillPoints(int numPoints) {
        totalSkillPoints = numPoints;
        usedSkillPoints = 0;
        pilotSkill = 0;
        fighterSkill = 0;
        merchantSkill = 0;
        engineerSkill = 0;
    }

    int fuelCost() {
        return ((int) (Math.pow(Math.E, Math.negateExact(this.getSkills()[0])
                / 16.0) * ((Math.sqrt(Math.pow(this.getSelectedRegion().getYPos()
                - this.getRegion().getYPos(), 2)
                + Math.pow(this.getSelectedRegion().getXPos()
                - this.getRegion().getXPos(), 2))) / 60)) + 1);
    }

    boolean hasEnoughFuel() {
        return (currentShip.getCurrentFuel() >= (fuelCost()));
    }


    public String toString() {
        return "total: " + totalSkillPoints + ", used: "
            + usedSkillPoints + ", pilot: " + pilotSkill
            + ", fighter: " + fighterSkill + ", merchant: "
            + merchantSkill + ", engineer: " + engineerSkill;
    }

    Ship getCurrentShip() {
        return currentShip;
    }


    public int getKarma() {
        return karma;
    }

    public void increaseKarma() {
        if (this.karma < 10) {
            this.karma++;
        }
    }

    public void decreaseKarma() {
        if (this.karma > -10) {
            this.karma--;
        }
    }
}