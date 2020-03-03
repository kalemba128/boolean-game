package com.bartoszkalemba.booleangame.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.bartoszkalemba.booleangame.BooleanGame;
import com.bartoszkalemba.booleangame.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bartek on 22.07.2017.
 */

public class BulletsManager {

    private BooleanGame game;
    private List<Bullet> bullets = new ArrayList<Bullet>();
    private List<Bullet> bulletsToRemove = new ArrayList<Bullet>();

    public void addBullet(Vertex source, Vertex target) {
        Bullet bullet = new Bullet(source, target);
        bullet.sprite = new Sprite(game.getAssets().getTexture("bullet.png"));
        bullet.sprite.setRotation(MathUtils.random(0, 360));
        bullet.sprite.setColor(Constant.MainColor);
        bullets.add(bullet);
    }

    public void setGame(BooleanGame game) {
        this.game = game;
    }

    public void update() {
        // Aktualizacja pociskow
        for(Bullet bullet : bullets) {
            bullet.update(Gdx.graphics.getDeltaTime());

            if (bullet.hit()) {
                // dodaj pocisk do kosza
                bulletsToRemove.add(bullet);

                // rozpoczni animacje trafionego
                bullet.target.switchState();
            }
        }

        for (Bullet bullet : bulletsToRemove)
            bullets.remove(bullet);

        // wyczysc kosz
        bulletsToRemove.clear();
    }

    public void draw(SpriteBatch batch) {
        batch.begin();

        for(Bullet b : bullets)
            b.draw(batch);

        batch.end();
    }

    public boolean isEmpty(){
        if (bullets.size() == 0)
            return true;
        return false;
    }


}
