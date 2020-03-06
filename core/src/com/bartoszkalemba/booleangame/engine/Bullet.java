package com.bartoszkalemba.booleangame.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Bullet {

    public static int Radius = 8;
    public static float Speed = 250.f;

    public Vector2 position;
    public Vector2 velocity;
    public Vertex target;

    public Bullet(Vertex source, Vertex target) {
        // Calculating angle
        float angle = MathUtils.atan2(target.position.y - source.position.y, target.position.x - source.position.x);

        this.position = new Vector2(source.position.x, source.position.y);
        this.velocity = new Vector2(MathUtils.cos(angle), MathUtils.sin(angle));
        this.target = target;
    }

    public void update(float delta) {
        // Updating position of bullet
        position.x += (velocity.x * Speed) * delta;
        position.y += (velocity.y * Speed) * delta;
    }

    public boolean hit() {
        /* Checking collision between objects */
        float a = position.x - target.position.x;
        float b = position.y - target.position.y;
        float c = (float)Math.sqrt(a*a + b*b);

        if (c <= Radius + Vertex.BallRadius) {
           return true;
        }

        return  false;
    }

    public Sprite sprite;

    public void draw(SpriteBatch batch) {
        sprite.setPosition(position.x - Radius, position.y - Radius);
        sprite.rotate(300.f * Gdx.graphics.getDeltaTime());
        sprite.draw(batch);
    }
}
