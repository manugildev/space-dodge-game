package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import configuration.Settings;
import gameworld.GameWorld;
import helpers.AssetLoader;
import tweens.VectorAccessor;

/**
 * Created by ManuGil on 19/06/15.
 */
public class Platform extends GameObject {

    private GameObject platform1, platform2;
    private float centerPoint;
    private float gap;
    private Coin coin;
    private boolean isCoin;
    private boolean isScored = false;
    private TextureRegion textR;
    private float extradistance;

    public Platform(GameWorld world, float x, float y, float width, float height,
                    TextureRegion texture,
                    Color color, Shape shape) {
        super(world, x, y, width, height, texture, color, shape);
        if (Math.random() < 0.1f) isCoin = true;
        else isCoin = false;
        reset();

    }

    public void reset() {
        /*textR = Math.random() < 0.3f ? (AssetLoader.platform1) : (Math
                .random() < 0.5f ? AssetLoader.platform2 : AssetLoader.platform3);*/
        platform1 = platform2 = null;
        textR = AssetLoader.platform1;
        isScored = false;
        this.gap = MathUtils.random(Settings.MIN_GAP_SIZE, Settings.MAX_GAP_SIZE);
        centerPoint = MathUtils.random(gap / 2 + 50, world.gameWidth - (gap / 2) - 50);
        platform1 = new GameObject(world, centerPoint - (gap / 2) - world.gameWidth,
                getPosition().y, world.gameWidth, 70,
                textR, Color.WHITE, Shape.RECTANGLE);
        platform2 = new GameObject(world, centerPoint + (gap / 2), getPosition().y, world.gameWidth,
                70,
                textR, Color.WHITE, Shape.RECTANGLE);

        getSprite().setSize(world.gameWidth * 2 + gap, 70);
        setPosition(centerPoint - (gap / 2) - world.gameWidth, getPosition().y);

        if (Math.random() < Settings.COIN_RATE) isCoin = true;
        else isCoin = false;

        /*coin = new Coin(world, platform2.position.x - (gap / 2) - (Settings.COIN_SIZE / 2),
                position.y + (70 / 2) - (Settings.COIN_SIZE / 2), Settings.COIN_SIZE,
                Settings.COIN_SIZE, AssetLoader.coin,
                FlatColors.WHITE, Shape.RECTANGLE);*/

        coin = new Coin(world,
                platform2.position.x - (gap / 2) - (Settings.COIN_SIZE / 2) + MathUtils
                        .random(-130, 130),
                position.y + (70 / 2) - (Settings.COIN_SIZE / 2), Settings.COIN_SIZE,
                Settings.COIN_SIZE, AssetLoader.coin,
                Color.WHITE, Shape.RECTANGLE);

        Tween.to(position, VectorAccessor.HORIZONTAL, 1).target(position.x + 100).delay(0)
                .repeatYoyo(10000, 0)
                .ease(TweenEquations.easeInOutSine).start(getManager());

        extradistance = MathUtils.random(120, (Settings.MIN_DISTANCE_BETWEEN_PLATFORMS / 1.5f));
    }

    @Override
    public void update(float delta) {
        getManager().update(delta);
        if (velocity.y > -Settings.PLAT_INITAL_VELOCITY) {
            velocity.add(getAcceleration().cpy().scl(delta));
        } else {
            velocity.y = -Settings.PLAT_INITAL_VELOCITY;
        }

        position.add(velocity.cpy().scl(delta));
        platform1.setYPosition(getPosition().y);
        platform2.setYPosition(getPosition().y);
        platform1.setVelocity(getVelocity());
        platform2.setVelocity(getVelocity());
        if (isCoin) {
            coin.update(delta);
            coin.setYPosition(platform1.position.y + (getSprite()
                    .getHeight() / 2) - (Settings.COIN_SIZE / 2) - 6 + extradistance);
            coin.setVelocity(getVelocity());
        }
        platform1.update(delta);
        platform2.update(delta);

        checkScored();


    }

    private void checkScored() {
        if (!isScored && position.y < world.getHero().getPosition().y) {
            world.addScore(1);
            AssetLoader.success.play();
            isScored = true;
        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        platform1.render(batch, shapeRenderer);
        platform2.render(batch, shapeRenderer);
        if (isCoin) {
            coin.render(batch, shapeRenderer);
        }
    }

    public boolean collision() {
        if (Intersector
                .overlaps(world.getHero().getRectangle(), platform2.getRectangle()) || Intersector
                .overlaps(world.getHero().getRectangle(), platform1.getRectangle())) {
            return true;
        }
        return false;
    }

    public void changeSprite(TextureRegion region) {
        platform1.getSprite().setRegion(region);
        platform2.getSprite().setRegion(region);
    }
}
