package com.bartoszkalemba.booleangame;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class Assets {

    public AssetManager manager;

    public Assets() {
        manager = new AssetManager();
    }

    public void init() {
        addTexture("logo.png");
        while (!manager.update());
    }


    ////////////////////////////////////////////////
    // Textures
    ////////////////////////////////////////////////

    String texturesPath = "images/";

    public void addTexture(String name) {
        manager.load(texturesPath + name, Texture.class);
    }

    public Texture getTexture(String name) {
        return manager.get(texturesPath + name, Texture.class);
    }

    public Sprite getSprite(String name) {
        return new Sprite(getTexture(name));
    }


    public void loadTextures() {

        // menu
        addTexture("big-play-button.png");
        addTexture("buttons-bar.png");
        addTexture("sound-on-button.png");
        addTexture("sound-off-button.png");
        addTexture("options-button.png");
        addTexture("info-button.png");

        //select level
        addTexture("back-button.png");
        addTexture("topbar.png");
        addTexture("level-button.png");
        addTexture("lock-button.png");

        // gameplay
        addTexture("pause-button.png");
        addTexture("resume-button.png");
        addTexture("levels-button.png");
        addTexture("reset-button.png");
        addTexture("dialog.png");
        addTexture("bullet.png");

        for (int i = 1; i <= 16; i++) {
            String path = "colors/" + i + ".png";
            addTexture(path);
        }

        addTexture("button-mode-normal.png");
        addTexture("button-mode-creative.png");
        addTexture("button-mode-random.png");

        addTexture("hand.png");
        addTexture("instr1.png");
        addTexture("instr2.png");

    }


    ////////////////////////////////////////////////
    // Fonts
    ////////////////////////////////////////////////

    String fontsPath = "fonts/";

    public void addFont(String name) {
        manager.load(fontsPath + name, BitmapFont.class);
    }

    public BitmapFont getFont(String name) {
        return manager.get(fontsPath + name, BitmapFont.class);
    }

    public void loadFonts() {
        addFont("default72.fnt");
        addFont("default48.fnt");
        addFont("default32.fnt");
    }


    ////////////////////////////////////////////////
    // Audio
    ////////////////////////////////////////////////

    String audioPath = "audio/";

    public void addMusic(String name) {
        manager.load(audioPath + name, Music.class);
    }

    public void addSound(String name) {
        manager.load(audioPath + name, Sound.class);
    }

    public Music getMusic(String name){
        return manager.get(audioPath + name, Music.class);
    }

    public Sound getSound(String name){
        return manager.get(audioPath + name, Sound.class);
    }

    public void loadAudio() {
        addSound("button-click.mp3");
        addSound("vertex-click.mp3");
        addSound("win.mp3");
    }
}