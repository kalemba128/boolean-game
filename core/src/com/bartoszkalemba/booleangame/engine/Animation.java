package com.bartoszkalemba.booleangame.engine;

import com.badlogic.gdx.Gdx;

public class Animation {

    enum State {
        Lower,
        Stand,
        Grow
    }

    public State state;
    private float tmp_radius;
    public float radius;
    public float speed;

    public Animation(float radius){
        this.state = State.Stand;
        this.tmp_radius = radius;
        this.radius = radius;
        this.speed = 100.f;
    }

    public boolean start() {
        if (state == State.Stand) {
            state = State.Lower;
            return true;
        }
        return false;
    }

    public boolean isSwitch() {
        if (state == State.Lower) {
            radius -= speed * Gdx.graphics.getDeltaTime();

            if (radius <= 0) {
                radius = 0;
                state = Animation.State.Grow;
                return true;
            }
        }

        return false;
    }

    public boolean stop() {
        if (state == State.Grow) {
            radius += speed * Gdx.graphics.getDeltaTime();

            if (radius >= tmp_radius) {
                radius = tmp_radius;
                state = State.Stand;
                return true;
            }
        }
        return false;
    }
}
