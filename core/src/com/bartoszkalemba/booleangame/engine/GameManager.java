package com.bartoszkalemba.booleangame.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.bartoszkalemba.booleangame.Appearance;
import com.bartoszkalemba.booleangame.BooleanGame;

public class GameManager {

    private BooleanGame game;
    private BulletsManager bulletsManager = new BulletsManager();
    private LevelManager level = new LevelManager();

    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;


    // Change the state of vertex and shoot to neighbors
    public void hitVertex(Vertex v) {
        for (Vertex it : level.vertexConnections(v))
            bulletsManager.addBullet(v, it);

        v.switchState();
    }

    public boolean isWin() {
        for (Vertex v : level.getVertices()) {
            if ( v.state == Vertex.State.Negative ||
                    v.animation.state != Animation.State.Stand || !bulletsManager.isEmpty())
                return false;
        }

        return  true;
    }

    public void update(Vector2 mouse) {
        Vertex.SoundCounter += Gdx.graphics.getDeltaTime();
        bulletsManager.update();


            for (Vertex v : level.getVertices()){
                if (v.isClick(mouse)) {
                    hitVertex(v);
                    level.counterSteps++;
                }
            }
    }

    public void render() {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        /////////////////////////////////////////////
        // DRAW BACK

        shapeRenderer.setColor(Appearance.MAIN_COLOR);

        // Vertices
        for (Vertex v : level.vertices)
            v.drawBack(shapeRenderer);

        // Connections
        for (Connection c : level.connections)
            c.drawBack(shapeRenderer);

        /////////////////////////////////////////////



        /////////////////////////////////////////////
        // DRAW FRONT

        // Connections
        shapeRenderer.setColor(Appearance.BACKGROUND_COLOR);
        for (Connection c : level.connections)
            c.drawFront(shapeRenderer);

        // Vertices
        for (Vertex v : level.vertices)
            v.drawFront(shapeRenderer);

        /////////////////////////////////////////////

        shapeRenderer.end();

        bulletsManager.draw(spriteBatch);
    }


    /* GETTERS & SETTERS */

    public void setMouse(Vector2 mouse) {
        for (Vertex v : level.vertices)
            v.mouse = mouse;
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public LevelManager getLevel() {
        return level;
    }

    public void setGame(BooleanGame game) {
        this.game = game;
    }

    public BulletsManager getBulletsManager() {
        return bulletsManager;
    }
}
