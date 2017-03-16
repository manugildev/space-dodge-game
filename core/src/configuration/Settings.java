package configuration;

/**
 * Created by ManuGil on 23/04/15.
 */

public class Settings {

    //HERO
    public static final float HERO_INITIAL_Y = 600;
    public static final float HERO_LERP_SPEED = 0.080f;
    public static final float HERO_WIDTH = 65;
    public static final float HERO_HEIGHT = 70;

    //PLATFORM
    public static final float MIN_GAP_SIZE = 160;
    public static final float MAX_GAP_SIZE = 220;
    public static final float MAX_DISTANCE_BETWEEN_PLATFORMS = 480;
    public static final float MIN_DISTANCE_BETWEEN_PLATFORMS = 480;
    public static final float PLAT_INITAL_VELOCITY = 680;

    //COIN
    public static final float COIN_SIZE = 40;
    public static final double COIN_RATE = 0.35f;

    //MENU
    public static final float PLAY_BUTTON_SIZE = 170;
    public static final float BUTTON_SIZE = 150;//Set to true if you have textures in buttons.png
    public static final boolean SHADOWS = false;
    public static float MENU_BACK_ALPHA = 1f;
    public static final float MUSIC_VOLUME = .8f;

    public static final boolean USE_TITLE_TEXTURE = true;

    ////////COLORS/////////
    //TEXTS
    public static final String TITLE_TEXT_COLOR = "#FFFFFF";

    ////////COLORS/////////
    //MENU
    public static final String BEST_HUD_COLOR = "#FFFFFF";
    public static final String SCORE_TEXT_COLOR = "495488";

    ////////COLORS/////////
    //BUTTONS
    public static final String PLAY_BUTTON_COLOR = "#FF0843";
    public static final String RANK_BUTTON_COLOR = "#FF0843";
    public static final String ACHIEVEMENT_BUTTON_COLOR = "#FF0843";
    public static final String SHARE_BUTTON_COLOR = "#FF0843";
    public static final String ADS_BUTTON_COLOR = "#FF0843";


}
