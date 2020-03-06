package com.bartoszkalemba.booleangame.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.bartoszkalemba.booleangame.Appearance;
import com.bartoszkalemba.booleangame.BooleanGame;

import java.util.ArrayList;
import java.util.List;



public class BulletsManager {

    private BooleanGame game;
    private List<Bullet> bullets = new ArrayList<Bullet>();
    private List<Bullet> bulletsToRemove = new ArrayList<Bullet>();

    public void addBullet(Vertex source, Vertex target) {
        Bullet bullet = new Bullet(source, target);
        bullet.sprite = new Sprite(game.getAssets().getTexture("bullet.png"));
        bullet.sprite.setRotation(MathUtils.random(0, 360));
        bullet.sprite.setColor(Appearance.MAIN_COLOR);
        bullets.add(bullet);
    }

    public void setGame(BooleanGame game) {
        this.game = game;
    }

    public void update() {
        // Updating of bullets
        for(Bullet bullet : bullets) {
            bullet.update(Gdx.graphics.getDeltaTime());

            if (bullet.hit()) {
                // add bullet to trash
                bulletsToRemove.add(bullet);

                // start animation of target
                bullet.target.switchState();
            }
        }

        for (Bullet bullet : bulletsToRemove)
            bullets.remove(bullet);

        // clean trash
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
