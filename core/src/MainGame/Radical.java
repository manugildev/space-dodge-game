package MainGame;

import com.badlogic.gdx.Game;

import helpers.AssetLoader;
import screens.SplashScreen;

public class Radical extends Game {

    private ActionResolver actionresolver;

    public Radical(ActionResolver actionresolver) {
        this.actionresolver = actionresolver;
    }

    @Override
    public void create() {
        AssetLoader.load1();
        setScreen(new SplashScreen(this, actionresolver));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }


}
