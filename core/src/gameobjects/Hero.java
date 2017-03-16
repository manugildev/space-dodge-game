package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import configuration.Settings;
import gameworld.GameWorld;
import helpers.AssetLoader;
import tweens.VectorAccessor;

/**
 * Created by ManuGil on 19/06/15.
 */
public class Hero extends GameObject {

    private Vector2 positionPoint;
    float lerp = Settings.HERO_LERP_SPEED;
    private Vector2 position;
    private ParticleEffect effect;
    public boolean notCollided = true;

    public enum HeroState {
        DEAD, IDLE
    }

    public HeroState heroState;
    private GameObject circle;

    public Hero(GameWorld world, float x, float y, float width, float height,
                TextureRegion texture,                Color color, Shape shape) {
        super(world, x, y, width, height, texture, color, shape);
        positionPoint = new Vector2(x, y);
        position = new Vector2(x, y);
        position.set(getPosition());
        getRectangle().set(x, y, width - 10, height - 10);
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("misc/jetpack.p"), Gdx.files.internal(""));
        effect.setPosition(-100, -100);
        notCollided = true;
        heroState = HeroState.IDLE;

        circle = new GameObject(world, x, y, 100, 100, AssetLoader.dot, Color.WHITE, Shape.CIRCLE);
        circle.getSprite().setAlpha(0f);
    }

    public void start() {
        effectY(-100, Settings.HERO_INITIAL_Y, 1, 0.5f);
        notCollided = true;
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("misc/jetpack.p"), Gdx.files.internal(""));
        effect.setPosition(-100, -100);
    }

    public void startT() {
        notCollided = true;

    }

    @Override
    public void update(float delta) {

        getManager().update(delta);
        if (!isDead()) {
            position.x += (positionPoint.x - position.x) * lerp;
            if (Math.abs((positionPoint.x - position.x) * (lerp + 0.1f)) < 60)
                getSprite().setRotation(-(positionPoint.x - position.x) * (lerp + 0.1f));
            if (position.x < 0 + getSprite().getWidth() / 2)
                position.x = getSprite().getWidth() / 2;
            if (position.x > world.gameWidth - getSprite().getWidth() / 2)
                position.x = world.gameWidth - getSprite().getWidth() / 2;
            getRectangle().setPosition(position.x - (getSprite().getWidth() / 2) + 5, position.y);
            getSprite().setPosition(position.x - (getSprite().getWidth() / 2), position.y);
            getSprite().setOriginCenter();
            updateEffects();
            effect.update(delta);
            effect.setPosition(position.x, position.y - 5);


        }
        if(world.isTutorial()){
           setPositionPointX(world.tutorial.getFinger().getPosition().x+world.tutorial.getFinger().getSprite().getWidth()/2);
        }

        //CIRCLE EFFECT
        circle.update(delta);
        circle.setPosition(position.x - (circle.getSprite().getWidth() / 2) - (getSprite()
                .getRotation() / 2), position.y - 15);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        effect.draw(batch);
        circle.render(batch, shapeRenderer);
        super.render(batch, shapeRenderer);
    }

    public void setPositionPointX(float p) {
        this.positionPoint.x = p;
    }

    @Override
    public void effectY(float from, float to, float duration, float delay) {
        position.y = from;
        Tween.to(position, VectorAccessor.VERTICAL, duration).target(to).delay(delay)
                .ease(TweenEquations.easeInOutSine).start(getManager());
    }

    public void coinEffect() {
        circle.scale(0, 2f, .3f, 0f);
        circle.fadeOut(.3f, 0f);
    }

    public void finish() {
        AssetLoader.end.play();
        heroState = HeroState.DEAD;
        circle.scale(0,50,.5f,.0f);
        circle.fadeOut(.4f,.0f);
    }

    public boolean isDead() {

        return heroState == HeroState.DEAD;
    }
}
