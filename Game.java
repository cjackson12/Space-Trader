import javax.swing.*;
import java.awt.BorderLayout;
import java.applet.Applet;
import java.util.Map;
import java.util.HashMap;

//The controller for a game of SpaceTrader
public class Game extends Applet {

    private static final int INIT_SCREEN_X = 100;
    private static final int INIT_SCREEN_Y = 100;
    private static final int INIT_SCREEN_LENGTH = 750;
    private static final int INIT_SCREEN_WIDTH = 750;

    static final int EASY_DIFFICULTY = 0;
    static final int MEDIUM_DIFFICULTY = 1;
    static final int HARD_DIFFICULTY = 2;

    private int difficulty;

    private static Game thisGame;


    private static Universe setting;
    static final String[] REGION_NAMES = {"Omicron Persei 8", "Glap Flap",
        "Earth C-137", "Alphabetrium", "Cronenburg World", "Planet Squanch", "Dorian 5",
        "Flarbellon-7", "Gazorpazorp", "Terraneous System"};

    //    public static final Item[] Items = {
    //
    //    };

    private int skillpoints;

    //Map that holds screens and their names, to manage multiple different screens
    private Map<String, Screen> screens = new HashMap<>();

    private static String userName = "";

    private JFrame gameFrame;

    private Screen currentScreen;

    private Player player;


    private Game() {
        super();
        gameFrame = new JFrame("Space Trader");
        gameFrame.setBounds(INIT_SCREEN_X, INIT_SCREEN_Y, INIT_SCREEN_WIDTH, INIT_SCREEN_LENGTH);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        player = new Player();
        setting = Universe.getInstance();
        difficulty = -1;
    }

    public static Game getInstance() {

        return thisGame;
    }

    public static void startNewGame() {
        Game newGame = new Game();
        thisGame = newGame;
        newGame.playGame();
    }

    public static void winGame() {
        Screen winFrame = new Screen.WinScreen(thisGame);
        thisGame.switchToScreen(winFrame);
    }

    public static void main(String[] args) {
        //initialize some stuff
        Game theGame = new Game();
        thisGame = theGame;
        theGame.playGame();
    }

    static Universe getSetting() {
        return setting;
    }


    private void playGame() {
        screens.put("welcomeScreen", new Screen.WelcomeScreen(this));
        screens.put("configScreen", new Screen.ConfigScreen(this, player));
        screens.put("losingScreen", new Screen.LosingScreen(this));
        currentScreen = screens.get("welcomeScreen");
        gameFrame.setLayout(new BorderLayout());
        gameFrame.add(currentScreen, BorderLayout.CENTER);
        gameFrame.show();

        Universe.getInstance();
    }

    //method to switch to a newScreen
    void switchToScreen(Screen newScreen) {
        System.out.println("switchToScreen called");
        if (newScreen == null) {
            return;
        }
        gameFrame.getContentPane().removeAll();
        gameFrame.getContentPane().add(newScreen, BorderLayout.CENTER);
        currentScreen = newScreen;
        gameFrame.revalidate();

    }

    int getDifficulty() {
        return difficulty;
    }

    Map<String, Screen> getScreens() {
        return screens;
    }

    void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    int getSkillpoints() {
        return skillpoints;
    }

    void setSkillpoints(int skillpoints) {
        this.skillpoints = skillpoints;
    }

    void increaseSkillpoints() {
        skillpoints++;
    }

    void decreaseSkillpoints() {
        skillpoints--;
    }

}