import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;

class Item {

    private ItemType type;
    private int weight;
    private int basePrice;
    private int techLevel;
    private boolean isStolen;

    public enum ItemType {
        FUEL(10, 1, 1),
        HEALTH_KIT(10, 1, 1),
        WEAPON(50, 1, 1),
        BIRDPERSON_SEED(5, 1, 0),
        PICKLE(2, 1, 1),
        PLUMBUS(20, 2, 2),
        TURBULENT_JUICE(75, 2, 3),
        LOVE_POTION(30, 1, 4),
        MEESEKS_BOX(100, 3, 5),
        TIME_STABILIZING_COLLAR(60, 1, 6),
        CONCENTRATED_DARK_MATTER(200, 8, 7),
        UNIVERSE(500,0,0);

        private int baseTechLevel;
        private int basePrice;
        private int baseWeight;
        
        ItemType(int basePrice, int baseWeight, int baseTechLevel) {
            this.basePrice = basePrice;
            this.baseWeight = baseWeight;
            this.baseTechLevel = baseTechLevel;
        }

        public int getBasePrice() {
            return basePrice;
        }
        public int getBaseWeight() {
            return baseWeight;
        }
        public int getBaseTechLevel() {
            return baseTechLevel;
        }
    }

    private Item(ItemType type, int basePrice, int weight, int techLevel) {
        this.type = type;
        this.weight = weight;
        this.basePrice = basePrice;
        this.techLevel = techLevel;
        isStolen = false;
    }

    Item(ItemType type) {
        this(type, type.getBasePrice(), type.getBaseWeight(), type.getBaseTechLevel());
    }

    Item(int maxWeight) {
        List<ItemType> lightEnough = new ArrayList<>();
        for (ItemType t: ItemType.values()) {
            if (t.baseWeight <= maxWeight && !t.equals(ItemType.FUEL)
                    && !t.equals(ItemType.HEALTH_KIT) && !t.equals(ItemType.WEAPON)) {
                lightEnough.add(t);
            }
        }
        ItemType randType = lightEnough.get((int) (Math.random() * lightEnough.size()));
        this.type = randType;
        this.weight = randType.getBaseWeight();
        this.basePrice = randType.getBasePrice();
        this.techLevel = randType.getBaseTechLevel();
        isStolen = false;
    }

    ItemType getType() {
        return type;
    }

    int getWeight() {
        return weight;
    }
    int getBasePrice() {
        return basePrice;
    }
    int getTechLevel() {
        return techLevel;
    }

    public void markAsStolen() {
        isStolen = true;
    }

    public boolean isStolen() {
        return isStolen;
    }

    //    public class Weapon extends Item {
    //
    //        private int level;
    //
    //        public Weapon(int level) {
    //            super(ItemType.WEAPON,
    //                    ItemType.WEAPON.getBasePrice() * level,
    //                    ItemType.WEAPON.getBaseWeight());
    //            this.level = level;
    //        }
    //
    //    }
}
