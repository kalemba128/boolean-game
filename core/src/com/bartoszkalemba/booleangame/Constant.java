package com.bartoszkalemba.booleangame;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Bartek on 2017-05-21.
 */

public class Constant {

    public static int CircleSegments = 100;
    public static Color BackgroundColor = Color(234, 240, 241);
    public static Color MainColor = Color(44, 62, 80);
    public static Color LockColor = Color(127, 140, 141);

    public static Color Color(int r, int g, int b) {
        return  new Color(r/255.f, g/255.f, b/255.f, 1.f);
    }
}
