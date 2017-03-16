package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import configuration.Configuration;
import gameworld.GameRenderer;
import gameworld.GameState;
import gameworld.GameWorld;
import helpers.AssetLoader;
import helpers.FlatColors;
import tweens.Value;
import ui.Text;

/**
 * Created by ManuGil on 27/06/15.
 */
public class Tutorial extends GameObject {

    private GameObject finger;
    private Value timer = new Value();
    private Text text;

    public Tutorial(GameWorld world, float x, float y, float width, float height,
                    TextureRegion texture,
                    Color color, Shape shape) {
        super(world, x, y, width, height, texture, color, shape);
        finger = new GameObject(world, world.gameWidth / 2 - 120, 200, 240, 240,
                AssetLoader.finger,
                FlatColors.WHITE, GameObject.Shape.RECTANGLE);
        text = new Text(world, -world.gameWidth, world.gameHeight / 2+500, world.gameWidth,100,
                AssetLoader.square, Color.WHITE,
                Configuration.TUTORIAL_TEXT, AssetLoader.fontB, FlatColors.WHITE, 0, Align.center);


    }

    @Override
    public void update(float delta) {
        finger.update(delta);
        text.update(delta);
        super.update(delta);

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        //super.render(batch, shapeRenderer);
        finger.render(batch, shapeRenderer);
        text.render(batch, shapeRenderer, GameRenderer.fontShader);
    }

    public void start() {

        finger.fadeIn(.4f, 0f);
        fadeInFromTo(0, .5f, .4f, 0f);
        world.setGameState(GameState.TUTORIAL);
        text.effectX(- world.gameWidth, 0, .4f, 0f);
        finger.effectX(finger.getPosition().x, 0, 1f, 0f);
        finger.effectX(finger.getPosition().x, world.gameWidth - (finger.getSprite().getWidth()),
                2f, 1f);
        finger.effectX(finger.getPosition().x,
                world.gameWidth / 2 - (finger.getSprite().getWidth() / 2), 1f, 3f);
        world.getHero().heroState = Hero.HeroState.IDLE;
        world.getHero().startT();

    }

    public void finish() {
        finger.fadeOut(.4f, 0f);
        fadeOutFrom(0.5f, .4f, 0f);
        timer.setValue(0);
        text.effectX(text.getPosition().x, world.gameWidth, .4f, 0f);
        Tween.to(timer, -1, .4f).target(1).setCallbackTriggers(TweenCallback.COMPLETE).setCallback(
                new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        world.setGameState(GameState.RUNNING);
                        //world.resetGame();
                        // world.getHero().start();
                        world.getPlatformManager().start();
                    }
                }).start(getManager());
    }

    public GameObject getFinger() {
        return finger;
    }
}
