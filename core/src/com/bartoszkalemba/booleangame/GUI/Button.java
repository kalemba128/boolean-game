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


public class Button {

    public static Sound sound;

    public float radius;
    private float RADIUS;
    public Vector2 position;
    public Sprite sprite;

    private boolean down;
    private boolean click;

    public Label label;

    public Button(float radius, Vector2 position, Sprite sprite) {
        this.radius = radius;
        this.sprite = sprite;
        this.position = position;
        this.down = false;
        this.click = false;
        this.RADIUS = radius;

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
        float r = sprite.getWidth()/2;
        sprite.setPosition(position.x - r, position.y - r);

        float scale = radius/r;
        sprite.setScale(scale);
    }

    public void update(Vector2 mouse) {
        click = false;

        if (Gdx.input.justTouched()) {
            double a = mouse.x - position.x;
            double b = mouse.y - position.y;
            double c = Math.sqrt( a*a + b*b );

            if (c <= radius) {
                down = true;

            }
        }

        if (Gdx.input.isTouched()) {
            double a = mouse.x - position.x;
            double b = mouse.y - position.y;
            double c = Math.sqrt( a*a + b*b );

            if (c > radius)
                down = false;
        }

        if (!Gdx.input.isTouched() && down) {
            click = true;
            if (BooleanGame.isSound())sound.play();
        }

        if (!Gdx.input.isTouched()) {
            down = false;
        }

        if (isDown()) radius = RADIUS + (RADIUS * 1.f/30.f);
        else radius = RADIUS;

        updateSprite();
    }

    public boolean isDown() {
        return down;
    }

    public boolean isClick() {
        return click;
    }

    public  void draw(ShapeRenderer renderer){
        renderer.circle(position.x, position.y, radius, 100);
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);

        if (label != null)
            label.draw(batch, 1);
    }

}
