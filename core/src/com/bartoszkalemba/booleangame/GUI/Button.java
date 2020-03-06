package com.bartoszkalemba.booleangame.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bartoszkalemba.booleangame.Appearance;
import com.bartoszkalemba.booleangame.BooleanGame;


public class Button {

    /// --- Propeties --- ///
    private String name;
    private Vector2 position = new Vector2(0, 0);
    private Vector2 size = new Vector2(0, 0);
    private float radius = 0;
    private boolean active = false, clicked = false;
    private boolean isCircle = false;
    private boolean hided = false;


    /// --- Resources --- ///
    private static Sound sound;
    protected Sprite sprite;
    private Label label;

    protected static Vector2 mouse;

    public Button(String name) {
        this.name = name;
    }

    public void update() {
        if (!hided) {
            clicked = false;

            if (Gdx.input.justTouched() && isHover())
                active = true;

            if (Gdx.input.isTouched() && !isHover())
                active = false;

            if (!Gdx.input.isTouched()) {

                if (isActive()) {
                    clicked = true;
                    if (BooleanGame.isSound() && sound != null)
                        sound.play();
                }

                active = false;
            }

            updateSprite();
        }
    }

    public void updateSprite(){
        if (isCircle){
            float r = sprite.getWidth()/2;
            sprite.setPosition(position.x - r, position.y - r);

            float scale = radius/r;
            if(isActive())sprite.setScale(scale * 1.1f);
            else sprite.setScale(scale);
        }else{
            sprite.setPosition(position.x, position.y);
            float w = sprite.getWidth();
            float h = sprite.getHeight();
            float scaleX = size.x/w;
            float scaleY = size.y/h;
            sprite.setScale(scaleX, scaleY);
            if(isActive())sprite.setScale(scaleX* 1.1f, scaleY* 1.1f);
            else sprite.setScale(scaleX, scaleY);
        }
    }

    public void draw(SpriteBatch batch){
        if (!hided) {
            if (sprite != null)
                sprite.draw(batch);

            if (label != null)
                label.draw(batch, 1);
        }
    }

    /// --- Propeties --- ///

    public String getName(){
        return  name;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setSize(Vector2 size){
        isCircle = false;
        this.size = size;
    }

    public void  setRadius(float radius){
        isCircle = true;
        this.radius = radius;
    }


    /// --- States --- ///

    public boolean isHover(){
        if (isCircle){
            double a = mouse.x - position.x;
            double b = mouse.y - position.y;
            double c = Math.sqrt( a*a + b*b );

            if (c <= radius)
                return true;

            return false;
        }else{
            if (mouse.x >= position.x && mouse.x <= position.x + size.x &&
                    mouse.y >= position.y && mouse.y <= position.y + size.y)
                return true;

            return false;
        }
    }

    public boolean isActive(){
        return active;
    }

    public boolean isClicked(){ return  clicked; }

    public void show(){
        hided = false;
    }

    public void hide(){
        hided = true;
    }



    /// --- Resources --- ///

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        updateSprite();
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void setText(String text, BitmapFont font) {
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Appearance.BACKGROUND_COLOR);
        label = new Label(text, labelStyle);
        label.setPosition(position.x - (label.getWidth()/2), position.y - (label.getHeight()/2));
    }

    public String getText() {
        return label.getText().toString();
    }

    public static void setSound(Sound sound) {
        Button.sound = sound;
    }

    public static void setMouse(Vector2 mouse) {
        Button.mouse = mouse;
    }
}
