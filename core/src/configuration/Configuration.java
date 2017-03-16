package configuration;

/**
 * Created by ManuGil on 09/03/15.
 */

public class Configuration {

    public static final String GAME_NAME = "Space Dodge";

    public static boolean DEBUG = false;
    public static boolean SPLASHSCREEN = true;
    public static boolean LEADERBOARDS = true;
    public static boolean FPS_COUNTER = false;

    //ADMOB IDS
    public static final String AD_UNIT_ID_BANNER = "ca-app-pub-6147578034437241/9585551817";
    public static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-6147578034437241/4596949011";
    public static float AD_FREQUENCY = .6f;

    //VIDEO ADS
    public static boolean VIDEO_ADS = true;
    public static final String AD_COLONY_APP_ID = "app03593c089087438a91";
    public static final String AD_COLONY_ZONE_ID = "vz3a15012a7e1b40758d";

    //In App Purchases
    public static final boolean IAP_ON = true;
    public static final String ENCODED_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAstHIj8NFvBmzl0oIEho074DR/csphstmYmf0lAV9DskmoqQT9RcVflcbIk9v0UDQZqPKP7kLf7tL9IkJ/TpZM7JUiG7AmQhXr5kg5Mu3lhHhVt9+/d/cyhAHizchT9Ui58AM+vpdISd+FGs1Yz+PkRAmFXk9OPUbIdyZtNkLjvVxI2AvJp7PbJmrmskHMS+F3IQh4aCwc8sM2LmbCWxAnjJHbdC0ymH8eb67teGySL4DJesBWDYYX0wgQgu4tS555a/1TxDXFq6UlQqYphdyjHW/t9ZGGsICAdoAWdzXeue/wxE5GnO4Os0JwbJ1JDGnUgI42M2bNtJ/vBdHjLzYvQIDAQAB";
    public static final String PRODUCT_ID = "removeads";

    //LEADERBOARDS
    public static final String LEADERBOARD_HIGHSCORE = "CgkI_Pbk96ESEAIQAQ";
    public static final String LEADERBOARD_GAMESPLAYED = "CgkI_Pbk96ESEAIQAg";

    //ACHIEVEMENTS IDS Points
    public static final String ACHIEVEMENT_5_P = "CgkI_Pbk96ESEAIQAw";
    public static final String ACHIEVEMENT_10_P = "CgkI_Pbk96ESEAIQBA";
    public static final String ACHIEVEMENT_25_P = "CgkI_Pbk96ESEAIQBQ";
    public static final String ACHIEVEMENT_50_P = "CgkI_Pbk96ESEAIQBg";
    public static final String ACHIEVEMENT_100_P = "CgkI_Pbk96ESEAIQBw";
    public static final String ACHIEVEMENT_200_P = "CgkI_Pbk96ESEAIQCA";
    //GAMES PLAYED
    public static final String ACHIEVEMENT_10_GP = "CgkI_Pbk96ESEAIQCQ";
    public static final String ACHIEVEMENT_25_GP = "CgkI_Pbk96ESEAIQCg";
    public static final String ACHIEVEMENT_50_GP = "CgkI_Pbk96ESEAIQCw";
    public static final String ACHIEVEMENT_100_GP = "CgkI_Pbk96ESEAIQDA";
    public static final String ACHIEVEMENT_200_GP = "CgkI_Pbk96ESEAIQDQ";

    //COLORS
    public static final String COLOR_BACKGROUND_COLOR = "#ecf0f1";
    //CHANGE THE COLOR in background.png inside Android>Assets

    //TEXTs
    public static final String SCORE_TEXT = "Score: ";
    public static final String BEST_TEXT = "Best: ";
    public static final String GAMES_PLAYED_TEXT = "şŞğĞüÜİçÇöÖı: ";
    public static final String TUTORIAL_TEXT = "Tap to Start";
    public static final String CONTINUE_TEXT = "CONTINUE?";

    //Share Message
    public static final String SHARE_MESSAGE = "Can you beat my High Score at " + GAME_NAME + "? #SpaceDodge ";

}
