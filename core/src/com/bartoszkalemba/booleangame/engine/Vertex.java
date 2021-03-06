package com.bartoszkalemba.booleangame.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.bartoszkalemba.booleangame.Appearance;
import com.bartoszkalemba.booleangame.BooleanGame;

public class Vertex {
    public static float SoundCounter = 0;
    public static float SoundPitch = 1f;
    public static Sound sound;
    public Vector2 mouse;

    public static int Radius = 50;
    public static int Border = 10;
    public static int BallRadius = 20;

    public enum State {Positive, Negative}
    public State state = State.Positive;

    public Animation animation;
    public Vector2 position;
    public int id = 0;
    public int counter = 0;


    public Vertex(float x, float y, State state, int id) {
        position = new Vector2(x, y);
        this.state = state;
        this.id = id;
        this.animation = new Animation(BallRadius);
    }

    // Changing state of vertex
    public void switchState() {
        animation.start();
        counter++;
    }

    public boolean isUnderMouse(Vector2 mouse) {
        float a = mouse.x - position.x;
        float b = mouse.y - position.y;
        double c = Math.sqrt(a * a + b * b);

        if (c <= Vertex.Radius)
            return true;

        return  false;
    }

    public boolean isClick(Vector2 mouse) {
        if (Gdx.input.justTouched()) {
            if (isUnderMouse(mouse)) {
                if (animation.state == Animation.State.Stand) {
                        if (BooleanGame.isSound()){

                            if (SoundCounter < 0.7f) SoundPitch+=0.5f;
                            else SoundPitch = 1;

                            SoundCounter = 0;

                            long id = sound.play();
                            sound.setPitch(id, SoundPitch);
                        }
                        return true;
                    }
                }
        }
        return false;
    }


    // Drawing background of vertex
    public void drawBack(ShapeRenderer renderer) {
        renderer.circle(position.x, position.y, Radius, Appearance.CIRCLE_SEGMENTS);

    }

    // Drawing foreground of vertex
    public void drawFront(ShapeRenderer renderer) {
        renderer.setColor(Appearance.BACKGROUND_COLOR);
        renderer.circle(position.x, position.y, Radius - Border, Appearance.CIRCLE_SEGMENTS);


        if (animation.isSwitch()) {
            if (state == State.Negative)
                state = State.Positive;
            else state = State.Negative;
        }

        if (animation.stop()) {
            counter--;
            if (counter> 0)
                animation.start();
        }

        // Drawing border of ball
        if (state == Vertex.State.Positive) {
            renderer.setColor(Appearance.MAIN_COLOR);
            renderer.circle(position.x, position.y, animation.radius, Appearance.CIRCLE_SEGMENTS);
        }
        else {
            renderer.setColor(Appearance.MAIN_COLOR);
            renderer.circle(position.x, position.y, animation.radius + 3, Appearance.CIRCLE_SEGMENTS);

            renderer.setColor(Appearance.BACKGROUND_COLOR);
            renderer.circle(position.x, position.y, animation.radius - 6, Appearance.CIRCLE_SEGMENTS);

        }
    }


    static public State StringToState(String string) {
        if (string.equals("Positive"))
            return State.Positive;
        return State.Negative;
    }
    static public String StateToString(State state) {
        if (state == State.Positive)
            return new String("Positive");
        return new String("Negative");
    }
}
