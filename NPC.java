import javax.swing.*;

public abstract class NPC {

    public abstract JFrame getFrame(Player player);

    private static int npcType;

    public int getNPCType() {
        return npcType;
    }

    public static NPC getRandomNPC(int difficulty, Player player) {
        //grab random number
        double num = Math.random() * 10;
        if (num <= 2 + difficulty || difficulty == Game.EASY_DIFFICULTY) {
            //get an NPC
            double chance = Math.random() * 10;
            switch (difficulty) {
            case Game.EASY_DIFFICULTY:
                if (chance < 4 && player.getCurrentShip().getAvailableCargoSpots() > 0) {
                    //40%
                    npcType = 1;
                    return new Merchant(player);
                } else if (chance < 7 && player.getCurrentShip().getCargo().size() > 0) {
                    //30%
                    npcType = 2;
                    return new Police();
                } else {
                    //30%
                    npcType = 3;
                    return new Bandit();
                }
            case Game.MEDIUM_DIFFICULTY:
                if (chance < 3.34 && player.getCurrentShip().getAvailableCargoSpots() > 0) {
                    //33%
                    npcType = 1;
                    return new Merchant(player);
                } else if (chance < 6.67 && player.getCurrentShip().getCargo().size() > 0) {
                    //33%
                    npcType = 2;
                    return new Police();
                } else {
                    //33%
                    npcType = 3;
                    return new Bandit();
                }
            case Game.HARD_DIFFICULTY:
                if (chance < 2.5 && player.getCurrentShip().getAvailableCargoSpots() > 0) {
                    //25%
                    npcType = 1;
                    return new Merchant(player);
                } else if (chance < 6 && player.getCurrentShip().getCargo().size() > 0) {
                    //35%
                    npcType = 2;
                    return new Police();
                } else {
                    //40%
                    npcType = 3;
                    return new Bandit();
                }
            default:
                return null;
            }
        } else {
            //no NPC
            return null;
        }
    }
}
