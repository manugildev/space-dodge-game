package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import gameworld.GameWorld;
import helpers.AssetLoader;
import tweens.SpriteAccessor;
import tweens.Value;

/**
 * Created by ManuGil on 19/06/15.
 */
public class Coin extends GameObject {
    private Value angle = new Value();
    private float angleInc;
    public boolean isScored = false;

    public Coin(GameWorld world, float x, float y, float width, float height,
                TextureRegion texture,
                Color color, Shape shape) {
        super(world, x, y, width, height, texture, color, shape);
        Tween.to(getSprite(), SpriteAccessor.SCALE, .3f).target(0.8f).repeatYoyo(100000, 0f)
                .ease(TweenEquations.easeInOutSine).start(getManager());
        getSprite().setRotation(45);
        angleInc = Math.random() < 0.5f ? -MathUtils.random(2, 4) : MathUtils.random(2, 4);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        angle.setValue(angle.getValue() + angleInc);
        getSprite().setRotation(angle.getValue());
        collisions();

    }

    private void collisions() {
        if (!isScored && Intersector.overlaps(getRectangle(), world.getHero().getRectangle())) {
            isScored = true;
            AssetLoader.addCoinNumber(1);
            world.getCoinsUI().setText(AssetLoader.getCoinNumber() + "");
            AssetLoader.coinS.play();
            fadeOut(.3f, 0f);
            scaleZero(.3f,0f);
            world.getHero().coinEffect();
        }
    }
}
