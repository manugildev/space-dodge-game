package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import configuration.Configuration;
import configuration.Settings;
import gameworld.GameState;
import gameworld.GameWorld;
import helpers.AssetLoader;
import helpers.FlatColors;
import tweens.SpriteAccessor;
import tweens.Value;
import ui.MenuButton;
import ui.Text;

/**
 * Created by ManuGil on 01/05/15.
 */
public class Menu extends GameObject {
    public Text text, gamesPlayedText, bestText;
    public ArrayList<MenuButton> menubuttons = new ArrayList<MenuButton>();
    private Sprite title;
    private GameObject background;
    private Value timer = new Value();
    public boolean isSwipeable = false;
    private GameOver gameOver;

    public Menu(GameWorld world, float x, float y, float width, float height,
                TextureRegion texture,
                Color color, Shape shape) {
        super(world, x, y, width, height, texture, color, shape);

        background = new GameObject(world, x, y, width, height, AssetLoader.background,
                FlatColors.WHITE,
                Shape.RECTANGLE);
        background.getSprite().setAlpha(Settings.MENU_BACK_ALPHA);

        text = new Text(world, 0, world.gameHeight / 2 + 250 + 25,
                world.gameWidth, 150, AssetLoader.square, FlatColors.WHITE, Configuration.GAME_NAME,
                AssetLoader.fontXL, world.parseColor(Settings.TITLE_TEXT_COLOR, 1f), 10,
                Align.center);

        //if (Settings.SHADOWS)
        text.setShadow(true);
        gamesPlayedText = new Text(world, x, world.gameHeight / 2 - 15, width, 100,
                AssetLoader.square,
                Color.WHITE,
                Configuration.GAMES_PLAYED_TEXT + AssetLoader.getGamesPlayed(),
                AssetLoader.fontB, world.parseColor(Settings.BEST_HUD_COLOR, 1f),
                18, Align.center);

        bestText = new Text(world, x, world.gameHeight / 2 + 100, width, 100, AssetLoader.square,
                Color.WHITE,
                Configuration.BEST_TEXT + AssetLoader.getHighScore(),
                AssetLoader.fontB, world.parseColor(Settings.BEST_HUD_COLOR, 1f),
                18, Align.center);

        MenuButton playButton = new MenuButton(world,
                world.gameWidth / 2 - (Settings.PLAY_BUTTON_SIZE / 2),
                world.gameHeight / 2 - 250 - (Settings.PLAY_BUTTON_SIZE) + 100,
                Settings.PLAY_BUTTON_SIZE,
                Settings.PLAY_BUTTON_SIZE, AssetLoader.buttonBack,
                world.parseColor(Settings.PLAY_BUTTON_COLOR, 1f),
                Shape.RECTANGLE,
                AssetLoader.playButtonUp);
        MenuButton leaderboardsButton = new MenuButton(world,
                world.gameWidth / 2 - ((Settings.BUTTON_SIZE * 2 + 30 + 15)),
                world.gameHeight / 2 - 250 - (Settings.BUTTON_SIZE + Settings.PLAY_BUTTON_SIZE + 30) + 100,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE, AssetLoader.buttonBack,
                world.parseColor(Settings.RANK_BUTTON_COLOR, 1f), Shape.RECTANGLE,
                AssetLoader.rankButtonUp);
        MenuButton achievementButton = new MenuButton(world,
                world.gameWidth / 2 - (15 + Settings.BUTTON_SIZE),
                world.gameHeight / 2 - 250 - (Settings.BUTTON_SIZE + Settings.PLAY_BUTTON_SIZE + 30) + 100,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE, AssetLoader.buttonBack,
                world.parseColor(Settings.ACHIEVEMENT_BUTTON_COLOR, 1f), Shape.RECTANGLE,
                AssetLoader.achieveButtonUp);
        MenuButton shareButton = new MenuButton(world,
                world.gameWidth / 2 + (15),
                world.gameHeight / 2 - 250 - (Settings.BUTTON_SIZE + Settings.PLAY_BUTTON_SIZE + 30) + 100,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE, AssetLoader.buttonBack,
                world.parseColor(Settings.SHARE_BUTTON_COLOR, 1f), Shape.RECTANGLE,
                AssetLoader.shareButtonUp);
        MenuButton rateButton = new MenuButton(world,
                world.gameWidth / 2 + (15 + 30 + Settings.BUTTON_SIZE),
                world.gameHeight / 2 - 250 - (Settings.BUTTON_SIZE + Settings.PLAY_BUTTON_SIZE + 30) + 100,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE, AssetLoader.buttonBack,
                world.parseColor(Settings.ADS_BUTTON_COLOR, 1f), Shape.RECTANGLE,
                AssetLoader.adsUp);

        menubuttons.add(playButton);
        menubuttons.add(leaderboardsButton);
        menubuttons.add(achievementButton);
        menubuttons.add(shareButton);

        if (Configuration.IAP_ON) menubuttons.add(rateButton);
        else {
            achievementButton.setPosition(
                    achievementButton.getPosition().x + 15 + (Settings.BUTTON_SIZE / 2),
                    achievementButton.getPosition().y);
            leaderboardsButton.setPosition(
                    leaderboardsButton.getPosition().x + 15 + (Settings.BUTTON_SIZE / 2),
                    leaderboardsButton.getPosition().y);
            shareButton.setPosition(
                    shareButton.getPosition().x + 15 + (Settings.BUTTON_SIZE / 2),
                    shareButton.getPosition().y);
        }

        title = new Sprite(AssetLoader.title);
        title.setPosition(text.getPosition().x, text.getPosition().y - 50);
        title.setSize(world.gameWidth,
                world.gameWidth / AssetLoader.title.getRegionWidth() * AssetLoader.title
                        .getRegionHeight());

        Tween.to(playButton.getSprite(), SpriteAccessor.SCALE, .25f).target(1.05f).delay(.55f)
                .repeatYoyo(1000000, 0f)
                .ease(TweenEquations.easeInOutSine).start(getManager());

        //GAMEOVER
        gameOver = new GameOver(world, 0, 0, world.gameWidth, world.gameHeight, AssetLoader.square,
                FlatColors.WHITE, Shape.RECTANGLE);
    }

    public void start() {
        world.setGameState(GameState.MENU);
        text.effectY((text.getPosition().y + world.gameHeight), text.getPosition().y, .8f, .1f);
        gamesPlayedText.effectY((gamesPlayedText.getPosition().y + world.gameHeight),
                gamesPlayedText.getPosition().y, .8f, .1f);
        bestText.effectY((bestText.getPosition().y + world.gameHeight),
                bestText.getPosition().y, .8f, .1f);

        for (int i = 0; i < menubuttons.size(); i++) {
            menubuttons.get(i).effectY(menubuttons.get(i).getPosition().y - world.gameHeight,
                    menubuttons.get(i).getPosition().y, .8f, 0.03f * i);
        }

        background.fadeInFromTo(0, Settings.MENU_BACK_ALPHA, 1f, 0f);

        timer.setValue(0);
        Tween.to(timer, -1, 1f).target(1).setCallbackTriggers(TweenCallback.COMPLETE).setCallback(
                new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        isSwipeable = true;

                    }
                }).start(getManager());
    }

    public void startGameOver() {
        world.setGameState(GameState.GAMEOVER);
        gameOver.start();
        background.fadeInFromTo(0, Settings.MENU_BACK_ALPHA, 1f, 0f);
        timer.setValue(0);
        Tween.to(timer, -1, 1f).target(1).setCallbackTriggers(TweenCallback.COMPLETE).setCallback(
                new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        isSwipeable = true;
                        if (!AssetLoader.getAds()) {
                            if (Math.random() < Configuration.AD_FREQUENCY) {
                                world.actionResolver.showOrLoadInterstital();
                            }
                        }
                    }
                }).start(getManager());
    }

    public void finish() {
        background.fadeOutFrom(Settings.MENU_BACK_ALPHA, 1f, 0f);
        text.effectY(text.getPosition().y, text.getPosition().y + world.gameHeight, .6f, .1f);

        gamesPlayedText.effectY(gamesPlayedText.getPosition().y,
                gamesPlayedText.getPosition().y + world.gameHeight, .6f, .1f);
        bestText.effectY(bestText.getPosition().y,
                bestText.getPosition().y + world.gameHeight, .6f, .1f);

        for (int i = menubuttons.size() - 1; i >= 0; i--) {
            menubuttons.get(i).effectY(menubuttons.get(i).getPosition().y,
                    menubuttons.get(i).getPosition().y - world.gameHeight, 1f,
                    .03f * menubuttons.size() - (i * .03f));
        }

        timer.setValue(0);
        Tween.to(timer, -1, .71f).target(1).setCallbackTriggers(TweenCallback.COMPLETE).setCallback(
                new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        world.setGameState(GameState.TUTORIAL);
                        world.tutorial.start();
                    }
                }).start(getManager());

        world.resetTutorial();
        world.resetGame();
       /* world.resetGame();
        world.getHero().start();
        world.getPlatformManager().start();*/
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        background.update(delta);
        text.update(delta);

        gamesPlayedText.update(delta);
        bestText.update(delta);
        title.setPosition(text.getPosition().x, text.getPosition().y - 20);

        //MENUBUTTONS
        if (world.isMenu()) {
            for (int i = 0; i < menubuttons.size(); i++) {
                menubuttons.get(i).update(delta);
            }
        }
        //GAMEOVERBUTTONS
        if (world.isGameOver()) {
            gameOver.update(delta);
        }

        //GAMEOVER
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader,
                       ShaderProgram fontShaderA) {

        background.render(batch, shapeRenderer);
        if (world.isMenu() || world.isGameOver()) {
            for (int i = 0; i < world.stars.size(); i++) {
                world.stars.get(i).render(batch, shapeRenderer);
            }
        }
        if (world.isMenu()) {
            if (Settings.USE_TITLE_TEXTURE) title.draw(batch);
            else text.render(batch, shapeRenderer, fontShader, fontShaderA);
            for (int i = 0; i < menubuttons.size(); i++) {
                menubuttons.get(i).render(batch, shapeRenderer);
            }

            bestText.render(batch, shapeRenderer, fontShader);
            gamesPlayedText.render(batch, shapeRenderer, fontShader);
        }

        if (world.isGameOver()) {
            gameOver.render(batch, shapeRenderer, fontShader);
        }


    }

    public void startPlayButton() {
        finish();
        world.startGame();
    }

    public void goToHome() {
        gameOver.finish();
        world.tries = 1;
        text.effectY((text.getPosition().y + world.gameHeight), text.getPosition().y, .7f, .55f);
        gamesPlayedText.effectY((gamesPlayedText.getPosition().y + world.gameHeight),
                gamesPlayedText.getPosition().y, .7f, .55f);
        bestText.effectY((bestText.getPosition().y + world.gameHeight),
                bestText.getPosition().y, .7f, .55f);

        for (int i = 0; i < menubuttons.size(); i++) {
            menubuttons.get(i).effectY(menubuttons.get(i).getPosition().y - world.gameHeight / 2,
                    menubuttons.get(i).getPosition().y, .4f, .03f * i);
        }

        timer.setValue(0);
        Tween.to(timer, -1, .85f).target(1).setCallbackTriggers(TweenCallback.COMPLETE).setCallback(
                new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        world.setGameState(GameState.MENU);
                    }
                }).start(getManager());


        timer.setValue(0);
        Tween.to(timer, -1, 1f).target(1).setCallbackTriggers(TweenCallback.COMPLETE).setCallback(
                new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        isSwipeable = true;
                    }
                }).start(getManager());

        world.getPlatformManager().rewind();
    }

    public ArrayList<MenuButton> getMenuButtons() {
        return menubuttons;
    }

    public GameObject getBackground() {
        return background;
    }

    public GameOver getGameOver() {
        return gameOver;
    }
}
