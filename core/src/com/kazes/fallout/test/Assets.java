package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * All game assets
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class Assets {

    /* Global constants */

    public static final int FONT_FILE_SIZE = 200;

    /*-----------------*/

    public static class Images {
        public static final String PIKACHU = "images/characters/pik.png";
        public static final String NPC_TEMP_1 = "images/characters/aloen.png";
        public static final String NPC_TEMP_2 = "images/characters/2pointO.png";
        public static final String PARALLAX_1 = "images/maps/parallax/img1.png";
        public static final String PARALLAX_2 = "images/maps/parallax/img2.png";
        public static final String PARALLAX_3 = "images/maps/parallax/img3.png";
        public static final String PARALLAX_4 = "images/maps/parallax/img4.png";
        public static final String PARALLAX_5 = "images/maps/parallax/img5.png";
        public static final String PARALLAX_6 = "images/maps/parallax/img6.png";
        public static final String MAP = "images/maps/tempmap2.png";
        public static final String MAP_GREEN = "images/maps/green_map.png";
        public static final String BONFIRE = "images/maps/bonfire.png";
        public static final String TREELOG = "images/maps/wood.png";
        public static final String TUNA = "images/maps/tuna.png";
        public static final String WATER = "images/maps/water.png";
        public static final String MEDKIT = "images/maps/medkit.png";
        public static final String SPLASH = "images/statics/splash.png";
        public static final String BEARTRAP = "images/maps/bear_trap.jpg";
        public static final String HOUSE1 = "images/maps/house.png";
        public static final String HOUSE2 = "images/maps/house2.png";
        public static final String WALL = "images/maps/wall.png";
        public static final String CRATE = "images/maps/crate.png";
        public static final String ITEMS = "images/maps/items.png";
        public static final String FIRE = "particles/fire.png";
        public static final String BLOOD = "particles/blood.png";
    }
    private static final String[] _Images = {
            Images.PIKACHU,
            Images.NPC_TEMP_1, Images.NPC_TEMP_2,
            Images.PARALLAX_1, Images.PARALLAX_2,
            Images.PARALLAX_3, Images.PARALLAX_4,
            Images.PARALLAX_5, Images.PARALLAX_6,
            Images.MAP, Images.MAP_GREEN, Images.BONFIRE,
            Images.TREELOG, Images.TUNA, Images.WATER,
            Images.SPLASH, Images.MEDKIT, Images.BEARTRAP,
            Images.HOUSE1, Images.HOUSE2,
            Images.WALL, Images.CRATE,
            Images.ITEMS, Images.FIRE,
            Images.BLOOD
    };

    public static class Atlases {
        public static final String items = "images/maps/items.atlas";
        public static final String items_new = "images/maps/items (2).atlas";
        public static final String items_icons = "images/maps/items2.atlas";
    }

    private static final String[] _Atlases = {
            Atlases.items, Atlases.items_new, Atlases.items_icons
    };

    public static final String[] _Parallax1 = {
            Images.PARALLAX_1, Images.PARALLAX_2,
            Images.PARALLAX_3, Images.PARALLAX_4,
            Images.PARALLAX_5, Images.PARALLAX_6
    };

    public static class ParticleEffects {
        public static final String fire = "particles/fireball.pe";
        public static final String blood = "particles/bloodParticle.pe";
    }

    public static final String[] _Effects = {
        ParticleEffects.fire, ParticleEffects.blood
    };

    public static class Buttons {/*
        public static final String MENU_PLAY = "buttons/menu_play";
        public static final String NEXT_LEVEL = "buttons/next_level";
        public static final String PREVIOUS_LEVEL = "buttons/previous_level";
        public static final String REPLAY_LEVEL = "buttons/replay_level";
        public static final String BACK_MENU = "buttons/back_menu";
        public static final String PLAIN = "buttons/level_select";
        public static final String PAUSE = "buttons/pause";
        public static final String SETTINGS = "buttons/settings";
        public static final String CROSS = "buttons/cross";
        public static final String BACK = "buttons/back";
        public static final String RED = "buttons/red";
        public static final String GREEN = "buttons/green";
    */}
    private static final String[] _Buttons = {
            /*Buttons.MENU_PLAY, Buttons.NEXT_LEVEL,
            Buttons.REPLAY_LEVEL, Buttons.BACK_MENU,
            Buttons.PLAIN, Buttons.PAUSE,
            Buttons.SETTINGS, Buttons.CROSS,
            Buttons.BACK, Buttons.PREVIOUS_LEVEL,
            Buttons.RED, Buttons.GREEN*/
    };

    public static class Dialogues {
        public static final String SHIP  = "dialogues/ship.json";
        public static final String SALLY  = "dialogues/sally.json";
        public static final String EXAMPLE  = "dialogues/example.json";
        public static final String PROLOGUE  = "dialogues/prologue.json";
        public static final String MERCENARIES  = "dialogues/mercenaries.json";
        public static final String CHAPTER1  = "dialogues/Chapter1.json";
        public static final String CHAPTER2  = "dialogues/Chapter2.json";

    }
    private static final String[] _Dialogues = {
            Dialogues.SHIP, Dialogues.SALLY,
            Dialogues.EXAMPLE, Dialogues.PROLOGUE,
            Dialogues.MERCENARIES
    };

    public static class FontSizes {
        public static final int TWENTY = 20;
        public static final int TWENTY_FIVE = 25;
        public static final int THIRTY = 30;
        public static final int FORTY = 40;
        public static final int FIFTY = 50;
        public static final int SIXTY = 50;
        public static final int SEVENTY = 50;
        public static final int EIGHTY = 50;
        public static final int NINETY = 50;
        public static final int HUNDRED = 100;
        public static final int HUNDRED_TWENTY = 100;
        public static final int HUNDRED_FIFTY = 150;
        public static final int HUNDRED_SEVENTY = 170;

        public static final int TWO_HUNDRED = 200;
    }
    private static final int[] _FontSizes = { FontSizes.TWENTY, FontSizes.TWENTY_FIVE, FontSizes.THIRTY, FontSizes.FORTY,
            FontSizes.FIFTY, FontSizes.SIXTY, FontSizes.SEVENTY, FontSizes.EIGHTY, FontSizes.NINETY, FontSizes.HUNDRED,
            FontSizes.HUNDRED_TWENTY, FontSizes.HUNDRED_FIFTY, FontSizes.HUNDRED_SEVENTY, FontSizes.TWO_HUNDRED };

    public static class Fonts {
        public static final String DIN_ALT = "fonts/default";
        public static final String CHAKRA = "fonts/chakra";

        public static final String DEFAULT = DIN_ALT;
    }
    private static final String[] _Fonts = { Fonts.DIN_ALT, Fonts.CHAKRA };
    private static final ArrayMap<String, ArrayMap<Integer, BitmapFont>> fontCache = new ArrayMap<String, ArrayMap<Integer, BitmapFont>>();

    public static class Animations {
        public static final String HERO = "images/characters/hero_spritesheet.png";
    }
    public static final ArrayMap<String, Animation<TextureRegion>> animationList = new ArrayMap<String, Animation<TextureRegion>>();
    private static final String[] _Animations = { Animations.HERO};

    public static final String UI_SKIN = "skins/clean-crispy-ui.json";

    private static AssetManager assetManager;

    //Private constructor to prevent instantiation
    private Assets () { }

    /**
     *  Static method that loads all the assets in the asset classes
     */
    public static void loadAll(AssetManager manager) {

        assetManager = manager;

        for (String image: _Images) assetManager.load(image, Texture.class);
        for (String animation: _Animations) assetManager.load(animation, Texture.class);
        for (String atlas: _Atlases) assetManager.load(atlas, TextureAtlas.class);
        for (String effect: _Effects) {
            assetManager.load(effect, ParticleEffect.class);
        }

        //for (String dialogues: _Dialogues) assetManager.load(dialogues, String.class);

        /*for (String button: _Buttons) {
            assetManager.load(button + ".png", Texture.class);
            assetManager.load(button + "_hover.png", Texture.class);
            assetManager.load(button + "_down.png", Texture.class);
        }*/

        assetManager.load(UI_SKIN, Skin.class, new SkinLoader.SkinParameter("skins/clean-crispy-ui.atlas"));
    }

    /**
     * Separate function to load fonts synchronously
     */
    public static void loadFonts() {
        //Load fonts separately
        for (String fontName: _Fonts) {
            ArrayMap<Integer, BitmapFont> localCache = new ArrayMap<Integer, BitmapFont>();

            for (int i = 0; i < _FontSizes.length; i++) {
                BitmapFont font = new BitmapFont(Gdx.files.internal(fontName + ".fnt"), false);
                font.getData().setScale((1.0f * _FontSizes[i]) / FONT_FILE_SIZE);

                localCache.put(_FontSizes[i], font);
            }

            fontCache.put(fontName, localCache);
        }
    }

    /**
     * Handler to be called when the AssetManager finishes
     * loading all the required assets
     */
    public static void finishLoading() {
        //Setup the animations
        for (String animation: _Animations) {
            //TextureRegion texture = new TextureRegion(assetManager.get(animation, Texture.class));
            //TextureRegion[] regions = texture.split(24, 24)[0];

            TextureRegion[][] tmp = TextureRegion.split(assetManager.get(animation, Texture.class),
                    assetManager.get(animation, Texture.class).getWidth() / 8,
                    assetManager.get(animation, Texture.class).getHeight() / 5);

            TextureRegion[] idleFrames = new TextureRegion[8];
            for (int j = 0; j < 8; j++) {
                idleFrames[j] = tmp[0][j];
            }
            Animation idle = new Animation<TextureRegion>(1/10f, idleFrames);
            idle.setPlayMode(Animation.PlayMode.LOOP);
            animationList.put(animation + "_idle", idle);

            TextureRegion[] walkingFrames= new TextureRegion[6];
            for (int j = 0; j < 6; j++) {
                walkingFrames[j] = tmp[1][j];
            }
            Animation walking = new Animation<TextureRegion>(1/10f, walkingFrames);
            walking.setPlayMode(Animation.PlayMode.LOOP);
            animationList.put(animation + "_walking", walking);

            //animationList.put(animation, new Animation(0.1f, regions));
        }
    }

    /**
     * Get a loaded asset of type T from the loaded cache
     *
     * @param fileName The identifier for the asset
     * @param type The type to return
     * @return	The asset with the type as specified
     */
    public static <T> T getAsset(String fileName, Class<T> type) {
        return assetManager.get(fileName, type);
    }

    /**
     * Returns a BitmapFont of the specified font
     *
     * @param fontName	The font name
     * @return The associated BitmapFont
     */
    public static BitmapFont getFont(String fontName, int size) { return fontCache.get(fontName).get(size); }


    /**
     * Get an animation that has already been loaded and cached
     *
     * @param animation
     * @return The given animation corresponding to the string
     */
    public static Animation getAnimation(String animation) {
        return animationList.get(animation);
    }

    /**
     * Dispose all the assets as well as the Asset Manager (not resettable)
     */
    public static void dispose() {
        for (ArrayMap<Integer, BitmapFont> caches: fontCache.values()) for (BitmapFont font: caches.values()) font.dispose();
    }

}