import java.util.ArrayList;
import java.util.List;

public class Ship {
    //private attributes
    public enum ShipType {
        TYPE1, TYPE2, TYPE3, TYPE4
    }

    private int maxCargoSpace;
    private int fuelCap;
    private int maxHealth;
    private int currentHealth;
    private int currentFuel;
    private int availableCargoSpots;
    private List<Item> cargo;

    public Ship(ShipType type) {
        switch (type) {
        case TYPE1:
            maxCargoSpace = 10;
            fuelCap = 10;
            maxHealth = 10;
            break;
        case TYPE2:
            maxCargoSpace = 20;
            fuelCap = 20;
            maxHealth = 20;
            break;
        case TYPE3:
            maxCargoSpace = 30;
            fuelCap = 30;
            maxHealth = 30;
            break;
        case TYPE4:
            maxCargoSpace = 40;
            fuelCap = 40;
            maxHealth = 40;
            break;
        default:
            break;
        }
        availableCargoSpots = maxCargoSpace;
        currentFuel = fuelCap;
        currentHealth = maxHealth;
        cargo = new ArrayList<>();
    }

    public boolean addToCargoHold(Item item) {
        if (availableCargoSpots >= item.getWeight()) {
            cargo.add(item);
            availableCargoSpots -= item.getWeight();
            return true;
        } else {
            return false;
        }
    }

    public boolean removeFromCargoHold(Item item) {
        if (cargo.contains(item)) {
            cargo.remove(item);
            availableCargoSpots += item.getWeight();
            return true;
        } else {
            return false;
        }
    }

    public List<Item> getCargo() {
        return cargo;
    }

    public int getMaxCargoSpace() {
        return maxCargoSpace;
    }

    public int getFuelCap() {
        return fuelCap;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getCurrentFuel() {
        return currentFuel;
    }

    public void setCurrentFuel(int currentFuel) {
        this.currentFuel = currentFuel;
    }

    public int getAvailableCargoSpots() {
        return availableCargoSpots;
    }

    public void setCargo(List<Item> cargo) {
        this.cargo = cargo;
    }
}
