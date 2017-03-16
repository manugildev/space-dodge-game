package ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import configuration.Settings;
import gameobjects.Coin;
import gameobjects.GameObject;
import gameworld.GameWorld;
import helpers.AssetLoader;
import helpers.FlatColors;

/**
 * Created by ManuGil on 19/06/15.
 */
public class PointsUI extends GameObject {

    private Coin coin;
    private Text text;

    public PointsUI(GameWorld world, float x, float y, float width, float height,
                    TextureRegion texture,
                    Color color, Shape shape, int align) {
        super(world, x, y, width, height, texture, color, shape);
        coin = new Coin(world, -30, y + height / 2 - Settings.COIN_SIZE / 2, Settings.COIN_SIZE,
                Settings.COIN_SIZE,
                AssetLoader.coin, FlatColors.WHITE, Shape.RECTANGLE);
        text = new Text(world,-100, y, width - x, 90, AssetLoader.square,
                FlatColors.WHITE, AssetLoader.getCoinNumber() + "", AssetLoader.fontS,
                FlatColors.WHITE, 3,
                align);
        start();
    }

    @Override
    public void update(float delta) {

        coin.update(delta);
        coin.setPosition(position.x + getSprite().getWidth() / 2 + 10, coin.position.y);
        text.update(delta);
        text.setPosition(position.x+getSprite().getWidth()/2+Settings.COIN_SIZE-10+50,text.position.y);
        super.update(delta);
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontshader) {
        super.render(batch, shapeRenderer);
        coin.render(batch, shapeRenderer);
        text.render(batch, shapeRenderer, fontshader);
    }

    public void setText(String string) {
        text.setText(string);
    }

    public void start() {
        effectX(position.x - world.gameWidth, position.x, 1f, 0f);
    }

}
