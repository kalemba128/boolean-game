package com.bartoszkalemba.booleangame.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bartoszkalemba.booleangame.BooleanGame;
import com.bartoszkalemba.booleangame.Constant;

/**
 * Created by Bartek on 20.07.2017.
 */

public class RectButton {

    public static Sound sound;

    public Vector2 size;
    public Vector2 SIZE;
    public Vector2 position;
    public Sprite sprite;

    private boolean down;
    private boolean click;

    public Label label;

    public RectButton(Vector2 size, Vector2 position, Sprite sprite) {
        this.size = new Vector2(size.x, size.y);
        this.sprite = sprite;
        this.position = position;
        this.down = false;
        this.click = false;
        this.SIZE = new Vector2(size.x, size.y);

        updateSprite();
    }

    public String getText() {
        return label.getText().toString();
    }

    public void setText(String text, BitmapFont font) {
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Constant.BackgroundColor);
        label = new Label(text, labelStyle);

        float w = label.getWidth();
        float h = label.getHeight();

        label.setPosition(position.x - (w/2), position.y - (h/2));
    }


    public void updateSprite() {
        sprite.setPosition(position.x, position.y);

        float w = sprite.getWidth();
        float h = sprite.getHeight();
        float scaleX = size.x/w;
        float scaleY = size.y/h;
        sprite.setScale(scaleX, scaleY);
    }

    private boolean isOver(Vector2 mouse){
        if (mouse.x >= position.x && mouse.x <= position.x + size.x &&
                mouse.y >= position.y && mouse.y <= position.y + size.y) {
            return true;
        }

        return false;
    }


    public void update(Vector2 mouse) {
        click = false;

        if (Gdx.input.justTouched()) {
            if (isOver(mouse)) {
                down = true;
            }
        }

        if (Gdx.input.isTouched()) {
            if (!isOver(mouse)) {
                down = false;
            }
        }

        if (!Gdx.input.isTouched() && down) {
            click = true;
            if (BooleanGame.isSound())sound.play();
        }

        if (!Gdx.input.isTouched()) {
            down = false;
        }

        if (isDown()) {
            size.x = SIZE.x + (SIZE.x * 1.f/30.f);
            size.y = SIZE.y + (SIZE.y * 1.f/30.f);
        }
        else {
            size.x = SIZE.x;
            size.y = SIZE.y;
        }

        updateSprite();
    }

    public boolean isDown() {
        return down;
    }

    public boolean isClick() {
        return click;
    }

    public  void draw(ShapeRenderer renderer){
        renderer.rect(position.x, position.y, size.x, size.y);
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);

        if (label != null)
            label.draw(batch, 1);
    }

}
