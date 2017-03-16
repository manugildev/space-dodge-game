package gameworld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import MainGame.ActionResolver;
import MainGame.Radical;
import configuration.Configuration;
import configuration.Settings;
import gameobjects.Background;
import gameobjects.GameObject;
import gameobjects.Hero;
import gameobjects.Menu;
import gameobjects.PlatformManager;
import gameobjects.Star;
import gameobjects.Tutorial;
import helpers.AssetLoader;
import helpers.FlatColors;
import ui.PointsUI;
import ui.Text;

/**
 * Created by ManuGil on 09/03/15.
 */

public class GameWorld {

    public final float w;
    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    //TODO:CHANGE THIS!
    public Radical game;
    public GameWorld world = this;

    //GAME CAMERA
    private GameCam camera;

    //VARIABLES
    private GameState gameState;
    private int score;

    //GAMEOBJECTS
    private Background background;
    private static Menu menu;
    private GameObject top, scoreCircle;
    private Hero hero;
    private PlatformManager platformManager;
    private Text scoreText;
    private PointsUI coinsUI;

    public int tries = 1;
    private int numberOfStars = 25;
    public final ArrayList<Star> stars = new ArrayList<Star>();

    public Tutorial tutorial;

    public GameWorld(Radical game, ActionResolver actionResolver, float gameWidth,
                     float gameHeight, float worldWidth, float worldHeight) {

        this.gameWidth = gameWidth;
        this.w = gameHeight / 100;
        this.gameHeight = gameHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.game = game;
        this.actionResolver = actionResolver;

        gameState = GameState.MENU;
        camera = new GameCam(this, 0, 0, gameWidth, gameHeight);

        background = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.background);

        top = new GameObject(world, -5, -5, gameWidth + 5, gameHeight + 5, AssetLoader.square,
                FlatColors.SKY_BLUE, GameObject.Shape.RECTANGLE);
        top.fadeOut(.8f, .1f);
        scoreCircle = new GameObject(world, world.gameWidth / 2 - (900 / 2),
                world.gameHeight / 2 - (900 / 2), 900, 900, AssetLoader.score_circle,
                FlatColors.WHITE,
                GameObject.Shape.CIRCLE);
        scoreText = new Text(world, world.gameWidth / 2 - (900 / 2), world.gameHeight / 2 - 110,
                900, 220,
                AssetLoader.square, FlatColors.SKY_BLUE, "200", AssetLoader.fontScore,
                world.parseColor(Settings.SCORE_TEXT_COLOR), 0,
                Align.center);

        coinsUI = new PointsUI(world, -250, worldHeight - 190, AssetLoader.uiback.getRegionWidth(),
                AssetLoader.uiback.getRegionHeight(), AssetLoader.uiback, FlatColors.WHITE,
                GameObject.Shape.RECTANGLE, Align.left);

        checkIfMusicWasPlaying();

        if (AssetLoader.getAds()) {
            world.actionResolver.viewAd(false);
        } else {
            world.actionResolver.viewAd(true);
        }

        resetTutorial();
        resetGame();
        resetMenu();
        menu.start();
        menu.getBackground().getSprite().setAlpha(1);

        for (int i = 0; i < numberOfStars; i++) {
            stars.add(new Star(world));
        }

    }

    private void checkIfMusicWasPlaying() {
        if (AssetLoader.getVolume()) {
            AssetLoader.music.setLooping(true);
            AssetLoader.music.play();
            AssetLoader.music.setVolume(Settings.MUSIC_VOLUME);
            AssetLoader.setVolume(true);
        }

    }

    public void update(float delta) {
        for (int i = 0; i < stars.size(); i++) {
            stars.get(i).update(delta);
        }

        background.update(delta);
        platformManager.update(delta);
        hero.update(delta);
        top.update(delta);
        tutorial.update(delta);
        //UI
        scoreCircle.update(delta);
        scoreText.update(delta);
        coinsUI.update(delta);
        collisions();
        menu.update(delta);
    }

    private void collisions() {

    }

    public void render(SpriteBatch batcher, ShapeRenderer shapeRenderer, ShaderProgram fontShader,
                       ShaderProgram fontShaderA) {
        background.render(batcher, shapeRenderer);

        scoreCircle.render(batcher, shapeRenderer);
        scoreText.render(batcher, shapeRenderer, fontShader);
        if (isRunning() || isTutorial()) {
            for (int i = 0; i < stars.size(); i++) {
                stars.get(i).render(batcher, shapeRenderer);
            }
        }
        platformManager.render(batcher, shapeRenderer);
        hero.render(batcher, shapeRenderer);

        if (isTutorial())
            tutorial.render(batcher, shapeRenderer);

        if (isMenu() || isGameOver())
            menu.render(batcher, shapeRenderer, fontShader, fontShaderA);
        coinsUI.render(batcher, shapeRenderer, fontShader);
        //gradient.render(batcher,shapeRenderer);
        top.render(batcher, shapeRenderer);

        if (Configuration.DEBUG) {
            batcher.setShader(fontShader);
            batcher.setShader(null);
        }
    }

    public void finishGame() {
        saveScoreLogic();
        gameState = GameState.MENU;
        resetMenu();
        menu.startGameOver();

    }

    private void saveScoreLogic() {
        AssetLoader.addGamesPlayed();
        int gamesPlayed = AssetLoader.getGamesPlayed();

        // GAMES PLAYED ACHIEVEMENTS!
        actionResolver.submitScore(score);
        actionResolver.submitGamesPlayed(gamesPlayed);
        if (score > AssetLoader.getHighScore()) {
            AssetLoader.setHighScore(score);
        }
        checkAchievements();
    }

    private void checkAchievements() {
        if (actionResolver.isSignedIn()) {
            if (score >= 5) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_5_P);
            if (score >= 10) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_10_P);
            if (score >= 25) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_25_P);
            if (score >= 50) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_50_P);
            if (score >= 100) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_100_P);
            if (score >= 200) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_200_P);

            int gamesPlayed = AssetLoader.getGamesPlayed();
            // GAMES PLAYED
            if (gamesPlayed >= 10)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_10_GP);
            if (gamesPlayed >= 25)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_25_GP);
            if (gamesPlayed >= 50)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_50_GP);
            if (gamesPlayed >= 100)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_100_GP);
            if (gamesPlayed >= 200)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_200_GP);


        }
    }

    public void startGame() {
        score = 0;
        scoreText.setText(score + "");
    }

    public GameCam getCamera() {
        return camera;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int i) {
        score += i;
        scoreText.setText(score + "");
        changeSprites();
    }

    public static Color parseColor(String hex, float alpha) {
        String hex1 = hex;
        if (hex1.indexOf("#") != -1) {
            hex1 = hex1.substring(1);
        }
        Color color = Color.valueOf(hex1);
        color.a = alpha;
        return color;
    }

    public static Color parseColor(String hex) {
        String hex1 = hex;
        if (hex1.indexOf("#") != -1) {
            hex1 = hex1.substring(1);
        }
        Color color = Color.valueOf(hex1);
        color.a = 1f;
        return color;
    }

    public boolean isRunning() {
        return gameState == GameState.RUNNING;
    }

    public boolean isGameOver() {
        return gameState == GameState.GAMEOVER;
    }

    public boolean isMenu() {
        return gameState == GameState.MENU;
    }

    public boolean isPaused() {
        return gameState == GameState.PAUSE;
    }

    public boolean isTutorial() {
        return gameState == GameState.TUTORIAL;
    }

    public Radical getGame() {
        return game;
    }

    public void resetGame() {
        score = 0;
        hero = null;
        platformManager = null;
        hero = new Hero(world, this.gameWidth / 2, Settings.HERO_INITIAL_Y, Settings.HERO_WIDTH,
                Settings.HERO_HEIGHT, AssetLoader.rocket, Color.WHITE,
                GameObject.Shape.RECTANGLE);
        resetPlatformManager();
    }


    public void resetPlatformManager() {
        platformManager = new PlatformManager(world);
    }

    public void resetMenu() {
        menu = null;
        menu = new Menu(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, FlatColors.WHITE,
                GameObject.Shape.RECTANGLE);
    }


    public void resetTutorial() {
        tutorial = new Tutorial(world, 0, 0, gameWidth, gameHeight, AssetLoader.square,
                FlatColors.DARK_BLACK,
                GameObject.Shape.RECTANGLE);
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public static Menu getMenu() {
        return menu;
    }

    public Hero getHero() {
        return hero;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPauseMode() {
        gameState = GameState.PAUSE;
    }

    public void setToRunning() {
        gameState = GameState.RUNNING;
    }

    public PlatformManager getPlatformManager() {
        return platformManager;
    }

    public PointsUI getCoinsUI() {
        return coinsUI;
    }

    public GameObject getScoreCircle() {
        return scoreCircle;
    }

    public Text getScoreText() {
        return scoreText;
    }

    public void changeSprites() {
        if (score >= 10) {
            platformManager.changeSprites(AssetLoader.platform2);
        }
        if (score >= 25) {
            platformManager.changeSprites(AssetLoader.platform1);
        }
        if (score >= 50) {
            platformManager.changeSprites(AssetLoader.platform3);
        }
        if (score >= 100) {
            platformManager.changeSprites(AssetLoader.platform1);
        }
    }


}
