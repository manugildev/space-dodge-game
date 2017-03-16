package gameobjects;

import com.badlogic.gdx.graphics.Color;
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
import gameworld.GameState;
import gameworld.GameWorld;
import helpers.AssetLoader;
import helpers.FlatColors;
import tweens.SpriteAccessor;
import tweens.Value;
import ui.Text;

/**
 *  
 * Created by ManuGil on 21/06/15.
 */
public class GameOver extends GameObject {

    private GameObject bigBanner, xButton, freeButton, payButton;
    private ArrayList<GameObject> objects = new ArrayList<GameObject>();
    private Text continueText, scoreText, priceText;
    private ArrayList<GameObject> arrows = new ArrayList<GameObject>();
    private Coin coin;
    private Value timer = new Value();

    private int numOfCircles = 7;
    private Value timer1 = new Value();

    public GameOver(GameWorld world, float x, float y, float width, float height,
                    TextureRegion texture,
                    Color color, Shape shape) {
        super(world, x, y, width, height, texture, color, shape);

        //OBJECTS
        bigBanner = new GameObject(world,
                world.gameWidth / 2 - AssetLoader.bigBanner.getRegionWidth() / 2,
                world.gameHeight / 2 - AssetLoader.bigBanner.getRegionHeight() / 2 + 50,
                AssetLoader.bigBanner.getRegionWidth(), AssetLoader.bigBanner.getRegionHeight(),
                AssetLoader.bigBanner,
                FlatColors.WHITE, Shape.RECTANGLE);
        xButton = new GameObject(world, bigBanner.getPosition().x,
                bigBanner.getPosition().y + bigBanner.getSprite().getHeight() + 30,
                AssetLoader.xButton.getRegionWidth(), AssetLoader.xButton.getRegionHeight(),
                AssetLoader.xButton, FlatColors.WHITE, Shape.RECTANGLE);
        xButton.isButton = true;
        freeButton = new GameObject(world,
                world.gameWidth / 2 - (AssetLoader.payButton.getRegionWidth() / 2),
                bigBanner.getPosition().y + 35,
                AssetLoader.freeButton.getRegionWidth(), AssetLoader.freeButton.getRegionHeight(),
                AssetLoader.freeButton, FlatColors.WHITE, Shape.RECTANGLE);
        freeButton.isButton = true;
        payButton = new GameObject(world,
                world.gameWidth / 2 - (AssetLoader.freeButton.getRegionWidth() / 2),
                bigBanner.getPosition().y + AssetLoader.freeButton.getRegionHeight() + 60,
                AssetLoader.payButton.getRegionWidth(), AssetLoader.payButton.getRegionHeight(),
                AssetLoader.payButton, FlatColors.WHITE, Shape.RECTANGLE);
        payButton.isButton = true;
        coin = new Coin(world, payButton.getPosition().x + 510,
                payButton.getPosition().y + payButton.getSprite().getHeight() / 2 - 20,
                40, 40, AssetLoader.coin, FlatColors.WHITE, Shape.RECTANGLE);
        coin.isScored = true;

        continueText = new Text(world, bigBanner.getPosition().x,
                bigBanner.getPosition().y + bigBanner.getSprite().getHeight() - 100,
                bigBanner.getSprite().getWidth(), 100, AssetLoader.square, FlatColors.WHITE,
                Configuration.CONTINUE_TEXT, AssetLoader.fontS, Color.WHITE, 30,
                Align.center);
        scoreText = new Text(world, bigBanner.getPosition().x,
                bigBanner.getPosition().y + bigBanner.getSprite().getHeight() - 200,
                bigBanner.getSprite().getWidth(), 100, AssetLoader.square, FlatColors.WHITE,
                "", AssetLoader.fontS, Color.WHITE, 15,
                Align.center);
        priceText = new Text(world, world.gameWidth / 2 + 35,
                payButton.getPosition().y + 25,
                170, 70, AssetLoader.square, FlatColors.WHITE,
                "", AssetLoader.fontS, Color.WHITE, 0,
                Align.center);

        objects.add(xButton);
        objects.add(bigBanner);
        objects.add(payButton);
        objects.add(freeButton);
        objects.add(coin);

        for (int i = 0; i < numOfCircles; i++) {
            arrows.add(new GameObject(world, world.gameWidth / 2 - ((3 * 50) + 25) + (50 * i),
                    bigBanner.getPosition().y - 30 - 50, 50,
                    50, AssetLoader.dot, FlatColors.WHITE, Shape.RECTANGLE));
            arrows.get(i).getSprite().setAlpha(1);
            arrows.get(i).getSprite().setScale(0.7f);
            Tween.to(arrows.get(i).getSprite(), SpriteAccessor.SCALE, .8f).target(0.1f)
                    .delay(i * 0.1f)
                    .repeatYoyo(10000, 0f)
                    .ease(TweenEquations.easeInOutSine).start(arrows.get(i).getManager());
            objects.add(arrows.get(i));
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).update(delta);

        }

        continueText.setPosition(continueText.position.x,
                bigBanner.getPosition().y + bigBanner.getSprite().getHeight() - 100);
        scoreText.setPosition(scoreText.position.x,
                bigBanner.getPosition().y + bigBanner.getSprite().getHeight() - 200);
        priceText.setPosition(priceText.position.x, payButton.getPosition().y + 25);

        continueText.update(delta);
        scoreText.update(delta);
        priceText.update(delta);
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader) {
        for (int i = 0; i < objects.size(); i++) {

            objects.get(i).render(batch, shapeRenderer);
        }
        continueText.render(batch, shapeRenderer, fontShader);
        scoreText.render(batch, shapeRenderer, fontShader);
        priceText.render(batch, shapeRenderer, fontShader);
    }

    public void setPrice(int price) {
        priceText.setText(price + "");
    }

    public void setScore() {
        scoreText.setText(Configuration.SCORE_TEXT + world.getScore() + "");
    }

    public void start() {
        //startTimer();
        world.actionResolver.checkVideoAd();
        if(!Configuration.VIDEO_ADS){
            world.getMenu().getGameOver().removeFreeButton();
        }
        setScore();
        for (int i = 0; i < objects.size() - numOfCircles - 1; i++) {
            objects.get(i).effectY(objects.get(i).getPosition().y - world.gameHeight,
                    objects.get(i).getPosition().y, 0.7f, 0.03f * i);
        }
        coin.effectY(coin.getPosition().y - world.gameHeight,
                coin.getPosition().y, 0.7f, 0.03f * 3);
        for (int i = objects.size() - numOfCircles; i < objects.size(); i++) {
            objects.get(i).effectY(objects.get(i).getPosition().y - world.gameHeight,
                    objects.get(i).getPosition().y, 0.7f, 0.12f);
        }
        priceText.setText((10 * world.tries + ""));
        world.changeSprites();
    }

    public void finish() {
        for (int i = 0; i < objects.size() - numOfCircles - 1; i++) {
            objects.get(i).effectY(objects.get(i).getPosition().y,
                    objects.get(i).getPosition().y + world.gameHeight, 0.7f, 0.03f * i);
        }
        coin.effectY(coin.getPosition().y, coin.getPosition().y + world.gameHeight, 0.7f,
                0.03f * 3);
        for (int i = objects.size() - numOfCircles; i < objects.size(); i++) {
            objects.get(i).effectY(objects.get(i).getPosition().y,
                    objects.get(i).getPosition().y + world.gameHeight, 0.7f, 0.12f);
        }
    }

    public GameObject getxButton() {
        return xButton;
    }

    public GameObject getPayButton() {
        return payButton;
    }

    public GameObject getFreeButton() {
        return freeButton;
    }

    public void startTimer() {
        timer.setValue(0);
        Tween.to(timer, -1, 4).target(1).setCallbackTriggers(TweenCallback.COMPLETE).setCallback(
                new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        world.getMenu().goToHome();
                    }
                }).start(getManager());
    }

    public void startAgain() {
        if (AssetLoader.getCoinNumber() >= (10 * world.tries)) {
            int score = world.getScore();
            finish();
            AssetLoader.addCoinNumber(-(10 * world.tries));
            world.tries++;
            world.resetGame();
            world.getHero().start();
            world.getPlatformManager().start();
            world.setScore(score);
            timer.setValue(0);
            world.getCoinsUI().setText(AssetLoader.getCoinNumber() + "");
            world.getMenu().getBackground().fadeOut(.8f, .2f);
            Tween.to(timer, -1, .82f).target(1).setCallbackTriggers(TweenCallback.COMPLETE)
                    .setCallback(
                            new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    world.setGameState(GameState.RUNNING);
                                }
                            }).start(getManager());

        }
    }

    public void startAgainVideo() {
        timer.setValue(0);
        Tween.to(timer, -1, .82f).target(1).delay(.1f).setCallbackTriggers(TweenCallback.COMPLETE)
                .setCallback(
                        new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                world.setGameState(GameState.RUNNING);
                            }
                        }).start(getManager());

        timer1.setValue(0);
        Tween.to(timer1, -1, .1f).target(1).setCallbackTriggers(TweenCallback.COMPLETE)
                .setCallback(
                        new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                int score = world.getScore();
                                finish();
                                world.resetGame();
                                world.getHero().start();
                                world.getPlatformManager().start();
                                world.setScore(score);
                                world.getCoinsUI().setText(AssetLoader.getCoinNumber() + "");
                                world.getMenu().getBackground().fadeOut(.8f, .2f);
                            }
                        }).start(getManager());
    }

    public void removeFreeButton() {
        freeButton.getSprite().setAlpha(0);
        freeButton.setPosition(freeButton.getPosition().x,
                bigBanner.getPosition().y - 200);
        payButton.setPosition(payButton.getPosition().x,
                bigBanner.getPosition().y + AssetLoader.payButton.getRegionHeight()+15);
        coin.setPosition(payButton.getPosition().x + 510,
                payButton.getPosition().y + payButton.getSprite().getHeight() / 2 - 20);
    }

    public void appearFreeButton() {
        freeButton.getSprite().setAlpha(1);
        freeButton.setPosition(freeButton.getPosition().x,
                bigBanner.getPosition().y + 35);
        payButton.setPosition(payButton.getPosition().x,
                bigBanner.getPosition().y + AssetLoader.freeButton.getRegionHeight() + 60);
        coin.setPosition(payButton.getPosition().x + 510,
                payButton.getPosition().y + payButton.getSprite().getHeight() / 2 - 20);
    }
}
