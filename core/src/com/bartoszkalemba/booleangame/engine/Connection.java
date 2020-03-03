package com.bartoszkalemba.booleangame.engine;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Connection {

    public static int Width = 40;
    public static int Border = 10;

    public Vertex v1;
    public Vertex v2;

    public Connection(Vertex v1, Vertex v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public void drawBack(ShapeRenderer renderer) {
        renderer.rectLine(v1.position, v2.position, Width);
    }

   public void drawFront(ShapeRenderer renderer) {
        renderer.rectLine(v1.position, v2.position, Width - 2 * Border);
    }
}
