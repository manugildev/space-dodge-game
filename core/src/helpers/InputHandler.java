package helpers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import configuration.Configuration;
import gameworld.GameWorld;

public class InputHandler implements InputProcessor {

    private GameWorld world;
    private float scaleFactorX;
    private float scaleFactorY;
    private Vector2 touchDownPoint, touchUpPoint;
    private int activeTouch = 0, angle;


    public InputHandler(GameWorld world, float scaleFactorX, float scaleFactorY) {
        this.world = world;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        touchDownPoint = new Vector2();
        touchUpPoint = new Vector2();
    }

    @Override
    public boolean keyDown(int keycode) {
        //Gdx.app.log("GameState", world.getGameState().toString());
        if (keycode == Input.Keys.R) {
            //world.startGame();
            world.resetGame();
        } else if (keycode == Input.Keys.F) {
        } else if (keycode == Input.Keys.D) {
            if (Configuration.DEBUG) Configuration.DEBUG = false;
            else Configuration.DEBUG = true;
        } else if (keycode == Input.Keys.A) {
            world.getPlatformManager().rewind();
        }
        //Gdx.app.log("GameState: ", world.getGameState().toString());
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE) {
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        if (world.isMenu()) {
            for (int i = 0; i < world.getMenu().getMenuButtons().size(); i++) {
                world.getMenu().getMenuButtons().get(i).isTouchDown(screenX, screenY);
            }
        } else if (world.isRunning()) {

        } else if (world.isGameOver()) {

            world.getMenu().getGameOver().getPayButton().isTouchDown(screenX, screenY);
            world.getMenu().getGameOver().getFreeButton().isTouchDown(screenX, screenY);
            world.getMenu().getGameOver().getxButton().isTouchDown(screenX, screenY);
        }
        touchDownPoint.set(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        touchUpPoint.set(screenX, screenY);
        //Gdx.app.log("Clicked!!!","");
        if (world.isRunning()) {

        } else if (world.isMenu()) {
            if (world.getMenu().getMenuButtons().get(0).isTouchUp(screenX, screenY)) {
                world.getMenu().startPlayButton();
                //JUST TESTING OUT TODO:
                if (AssetLoader.getAds()) {
                    world.actionResolver.viewAd(false);
                } else {
                    world.actionResolver.viewAd(true);
                }
            } else if (world.getMenu().getMenuButtons().get(1)
                    .isTouchUp(screenX, screenY)) {
                world.actionResolver.showScores();
            } else if (world.getMenu().getMenuButtons().get(3)
                    .isTouchUp(screenX, screenY)) {
                world.actionResolver.shareGame(Configuration.SHARE_MESSAGE);
            } else if (world.getMenu().getMenuButtons().get(2)
                    .isTouchUp(screenX, screenY)) {
                world.actionResolver.showAchievement();
            } else if (Configuration.IAP_ON) {
                if (world.getMenu().getMenuButtons().get(4).isTouchUp(screenX, screenY)) {
                    world.actionResolver.iapClick();
                }
            }

        } else if (world.isGameOver()) {
             if (world.getMenu().getGameOver().getxButton().isTouchUp(screenX, screenY)) {
                world.getMenu().goToHome();
            } else if (world.getMenu().getGameOver().getPayButton().isTouchUp(screenX, screenY)) {
                 world.getMenu().getGameOver().startAgain();
            } else if (world.getMenu().getGameOver().getFreeButton().isTouchUp(screenX, screenY)) {
                world.actionResolver.viewVideoAd();
            } else {

            }

        } else if (world.isPaused()) {
            world.setToRunning();
        } else if(world.isTutorial()){
            world.tutorial.finish();
        }
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        if (world.isRunning()) {
            world.getHero().setPositionPointX(screenX);

        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (world.gameHeight - screenY / scaleFactorY);
    }

    public static int angleBetweenTwoPoints(Vector2 one, Vector2 two, int distance) {
        float deltaY = one.y - two.y;
        float deltaX = two.x - one.x;
        double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
        if (angle < 0) {
            angle = 360 + angle;
        }
        if (new Vector2(deltaX, deltaY).len() < distance) {
            return -1;
        }
        return (int) angle;
    }

}
