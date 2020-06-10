import java.util.*;


public class Universe {

    private static Universe currentUniverse;

    private static ArrayList<Region> regionList;

    private Universe() {
        Random random = new Random();
        regionList = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        names.addAll(Arrays.asList(Game.REGION_NAMES));
        int rand;
        ArrayList<Integer> xCoords = new ArrayList<>();
        ArrayList<Integer> yCoords = new ArrayList<>();
        int xCoord = 0;
        int yCoord = 0;
        boolean stopper = true;
        int winRegion = (int) (Math.random()*10);
        for (int k = 0; k < 10; k++) {
            while (stopper) {
                stopper = false;
                xCoord = (int) ((Math.random() * 401) - 200);
                for (int x = 0; x < xCoords.size(); x++) {
                    if (Math.abs(xCoords.get(x) - xCoord) <= 5) {
                        stopper = true;
                        break;
                    }
                }
            }
            xCoords.add(xCoord);
            stopper = true;
            while (stopper) {
                stopper = false;
                yCoord = (int) ((Math.random() * 401) - 200);
                for (int y = 0; y < yCoords.size(); y++) {
                    if (Math.abs(yCoords.get(y) - yCoord) <= 5) {
                        stopper = true;
                        break;
                    }
                }
            }
            yCoords.add(yCoord);
            stopper = true;
            Region.TechLevel level = Region.TechLevel.values()[random.nextInt(7)];
            rand = (int) ((names.size()) * Math.random());
            regionList.add(new Region(xCoord, yCoord, level, names.get(rand),winRegion == k));
            names.remove(rand);
        }
    }

    public static Universe getInstance() {
        if (currentUniverse == null) {
            currentUniverse = new Universe();
        }
        return currentUniverse;
    }

    public static ArrayList<String> randomRegionNames(List<String> nameList) {
        ArrayList<String> randomList = new ArrayList<>();
        Random random = new Random();
        for (int k = 0; k < 7; k++) {
            int randNum = random.nextInt(nameList.size());
            randomList.add(nameList.get(randNum));
            nameList.remove(nameList.get(randNum));
        }

        return randomList;
    }

    public ArrayList<Region> getRegions() {
        return regionList;
    }
}