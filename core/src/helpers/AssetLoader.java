package helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import configuration.Configuration;

/**
 * Created by ManuGil on 09/03/15.
 */
public class AssetLoader {

    public static Texture logoTexture, dotT,
            buttonsT, buttonBackT, backgroundT, titleT, rocketT, coinT, scoreBackT, platformT, score_circleT, uibackT,
            xButtonT, freeButtonT, payButtonT, bigBannerT, particlesT, fingerT;
    public static TextureRegion logo, square, dot, playButtonUp, rankButtonUp, background, uiback,
            title, pauseButton, rocket, achieveButtonUp, homeButtonUp, coin, particle1, particle2,
            shareButtonUp, adsUp, buttonBack, arrow, scoreBack, platform2, score_circle, platform1, platform3,
            xButton, freeButton, payButton, bigBanner, finger;

    //BUTTONS
    public static BitmapFont font, fontS, fontXS, fontB, fontXL, fontScore;
    private static Preferences prefs;

    //MUSIC
    public static Music music;
    public static Sound click, success, end, coinS;


    public static void load1() {
        logoTexture = new Texture(Gdx.files.internal("logo.png"));
        logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        logo = new TextureRegion(logoTexture, 0, 0, logoTexture.getWidth(),
                logoTexture.getHeight());
        backgroundT = new Texture(Gdx.files.internal("background.png"));
        backgroundT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        background = new TextureRegion(backgroundT, 0, 0, backgroundT.getWidth(),
                backgroundT.getHeight());
    }

    public static void load() {
        //LOGO TEXTURE "logo.png"

        square = new TextureRegion(new Texture(Gdx.files.internal("square.png")), 0, 0, 10, 10);

        dotT = new Texture(Gdx.files.internal("dot.png"));
        dotT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        dot = new TextureRegion(dotT, 0, 0, dotT.getWidth(), dotT.getHeight());

        coinT = new Texture(Gdx.files.internal("coin.png"));
        coinT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        coin = new TextureRegion(coinT, 0, 0, coinT.getWidth(), coinT.getHeight());

        rocketT = new Texture(Gdx.files.internal("rocket1.png"));
        rocketT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        rocket = new TextureRegion(rocketT, 0, 0, rocketT.getWidth(), rocketT.getHeight());

        uibackT = new Texture(Gdx.files.internal("uiback.png"));
        uibackT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        uiback = new TextureRegion(uibackT, 0, 0, uibackT.getWidth(), uibackT.getHeight());

        buttonBackT = new Texture(Gdx.files.internal("button_back.png"));
        buttonBackT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        buttonBack = new TextureRegion(buttonBackT, 0, 0, buttonBackT.getWidth(),
                buttonBackT.getHeight());

        titleT = new Texture(Gdx.files.internal("title.png"));
        titleT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        title = new TextureRegion(titleT, 0, 0, titleT.getWidth(), titleT.getHeight());

        score_circleT = new Texture(Gdx.files.internal("score_circle.png"));
        score_circleT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        score_circle = new TextureRegion(score_circleT, 0, 0, score_circleT.getWidth(),
                score_circleT.getHeight());

        fingerT = new Texture(Gdx.files.internal("finger.png"));
        fingerT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        finger = new TextureRegion(fingerT, 0, 0, fingerT.getWidth(),
                fingerT.getHeight());

        platformT = new Texture(Gdx.files.internal("platform.png"));
        platformT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        platform1 = new TextureRegion(platformT, 0, 0, platformT.getWidth(), platformT.getHeight());

        platformT = new Texture(Gdx.files.internal("platform1.png"));
        platformT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        platform2 = new TextureRegion(platformT, 0, 0, platformT.getWidth(), platformT.getHeight());

        platformT = new Texture(Gdx.files.internal("platform2.png"));
        platformT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        platform3 = new TextureRegion(platformT, 0, 0, platformT.getWidth(), platformT.getHeight());



        //MENU
        xButtonT = new Texture(Gdx.files.internal("menu/xButton.png"));
        xButtonT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        xButton = new TextureRegion(xButtonT, 0, 0, xButtonT.getWidth(),
                xButtonT.getHeight());

        freeButtonT = new Texture(Gdx.files.internal("menu/freeButton.png"));
        freeButtonT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        freeButton = new TextureRegion(freeButtonT, 0, 0, freeButtonT.getWidth(),
                freeButtonT.getHeight());

        payButtonT = new Texture(Gdx.files.internal("menu/payButton.png"));
        payButtonT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        payButton = new TextureRegion(payButtonT, 0, 0, payButtonT.getWidth(),
                payButtonT.getHeight());

        bigBannerT = new Texture(Gdx.files.internal("menu/bigBanner.png"));
        bigBannerT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        bigBanner = new TextureRegion(bigBannerT, 0, 0, bigBannerT.getWidth(),
                bigBannerT.getHeight());

        //LOADING FONT
        Texture tfont = new Texture(Gdx.files.internal("misc/font.png"), true);
        tfont.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);

        fontXL = new BitmapFont(Gdx.files.internal("misc/font.fnt"), new TextureRegion(tfont),
                true);
        fontXL.getData().setScale(2.4f, -2.4f);
        fontXL.setColor(FlatColors.WHITE);

        font = new BitmapFont(Gdx.files.internal("misc/font.fnt"), new TextureRegion(tfont), true);
        font.getData().setScale(1.9f, -1.9f);
        font.setColor(FlatColors.WHITE);

        fontB = new BitmapFont(Gdx.files.internal("misc/font.fnt"), new TextureRegion(tfont), true);
        fontB.getData().setScale(1.4f, -1.4f);
        fontB.setColor(FlatColors.WHITE);

        fontS = new BitmapFont(Gdx.files.internal("misc/font.fnt"), new TextureRegion(tfont), true);
        fontS.getData().setScale(1.2f, -1.2f);
        fontS.setColor(FlatColors.WHITE);

        fontXS = new BitmapFont(Gdx.files.internal("misc/font.fnt"), new TextureRegion(tfont),
                true);
        fontXS.getData().setScale(0.9f, -0.9f);
        fontXS.setColor(FlatColors.WHITE);

        Texture tfontScore = new Texture(Gdx.files.internal("misc/fontScore.png"), true);
        tfont.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
        fontScore = new BitmapFont(Gdx.files.internal("misc/fontScore.fnt"),
                new TextureRegion(tfontScore),
                false);
        fontScore.getData().setScale(1f, 1f);
        fontScore.setColor(FlatColors.WHITE);

        //BUTTONS
        buttonsT = new Texture(Gdx.files.internal("buttons.png"));
        buttonsT.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        //CROP BUTTONS
        playButtonUp = new TextureRegion(buttonsT, 0, 0, 240, 240);
        rankButtonUp = new TextureRegion(buttonsT, 240, 0, 240, 240);
        shareButtonUp = new TextureRegion(buttonsT, 720, 0, 240, 240);
        achieveButtonUp = new TextureRegion(buttonsT, 960, 0, 240, 240);
        adsUp = new TextureRegion(buttonsT, 1200, 0, 240, 240);
        homeButtonUp = new TextureRegion(buttonsT, 480, 0, 240, 240);
        arrow = new TextureRegion(buttonsT, 1200 + 240, 0, 240, 240);
        pauseButton = new TextureRegion(buttonsT, 1200 + 480, 0, 240, 240);


        //PREFERENCES - SAVE DATA IN FILE
        prefs = Gdx.app.getPreferences(Configuration.GAME_NAME);

        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }

        if (!prefs.contains("games")) {
            prefs.putInteger("games", 0);
        }

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.ogg"));
        click = Gdx.audio.newSound(Gdx.files.internal("sounds/blip_click.wav"));
        success = Gdx.audio.newSound(Gdx.files.internal("sounds/blip_success.wav"));
        end = Gdx.audio.newSound(Gdx.files.internal("sounds/blip_end.wav"));
        coinS = Gdx.audio.newSound(Gdx.files.internal("sounds/blip_coin.wav"));

        //PARTICLES
        particlesT = new Texture(Gdx.files.internal("particles.png"));
        particlesT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        particle1 = new TextureRegion(particlesT, 0, 0, particlesT.getWidth() / 2,
                particlesT.getHeight());
        particle2 = new TextureRegion(particlesT, 120, 0, particlesT.getWidth() / 2,
                particlesT.getHeight());
    }

    public static void dispose() {
        font.dispose();
        fontS.dispose();
        fontXS.dispose();
        fontB.dispose();
        fontScore.dispose();
        fontXL.dispose();
        click.dispose();
        success.dispose();
        end.dispose();
        dotT.dispose();
        logoTexture.dispose();

    }

    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static void setButtonsClicked(int val) {
        prefs.putInteger("buttonsClicked", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public static void addGamesPlayed() {
        prefs.putInteger("games", prefs.getInteger("games") + 1);
        prefs.flush();
    }

    public static int getGamesPlayed() {
        return prefs.getInteger("games");
    }

    public static void setAds(boolean removeAdsVersion) {
        prefs = Gdx.app.getPreferences(Configuration.GAME_NAME);
        prefs.putBoolean("ads", removeAdsVersion);
        prefs.flush();
    }

    public static boolean getAds() {
        Gdx.app.log("ADS", prefs.getBoolean("ads") + "");
        return prefs.getBoolean("ads", false);
    }


    public static int getCoinNumber() {
        return prefs.getInteger("bonus");
    }

    public static void addCoinNumber(int number) {
        prefs.putInteger("bonus", prefs.getInteger("bonus") + number);
        prefs.flush();
    }

    public static void prefs() {
        prefs = Gdx.app.getPreferences(Configuration.GAME_NAME);
    }

    public static void setVolume(boolean val) {
        prefs.putBoolean("volume", val);
        prefs.flush();
    }

    public static boolean getVolume() {
        return prefs.getBoolean("volume");
    }


}
