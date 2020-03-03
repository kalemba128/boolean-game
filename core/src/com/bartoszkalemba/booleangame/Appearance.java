package com.bartoszkalemba.booleangame;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Bartek on 01.08.2017.
 */

public class Appearance {

    public static int CIRCLE_SEGMENTS = 100;
    public static Color BACKGROUND_COLOR = Color(234, 240, 241);
    public static Color MAIN_COLOR = Color(44, 62, 80);
    public static Color LOCK_COLOR = Color(127, 140, 141);


    private class Set{
        public Color main;
        public Color background;
        public Color lock;

        public Set() {
        }
    }

    public Set[] sets = new Set[16];

    public Appearance(){

        for (int i = 0; i < 16; i++)
            sets[i] = new Set();

        setColor(0, Color(234, 238, 239), Color(44, 62, 80));
        setColor(1, Color(241, 196, 15), Color(44, 62, 80));
        setColor(2, Color(231, 76, 60), Color(44, 62, 80));
        setColor(3, Color(26, 188, 156), Color(44, 62, 80));
        setColor(4, Color(0, 153, 188), Color(44, 62, 80));
        setColor(5, Color(255, 64, 129), Color(44, 62, 80));
        setColor(6, Color(149, 117, 205), Color(44, 62, 80));
        setColor(7, Color(161, 136, 127), Color(44, 62, 80));

        setColor(8, Color(44, 62, 80), Color(234, 238, 239));
        setColor(9, Color(44, 62, 80),  Color(241, 196, 15));
        setColor(10, Color(44, 62, 80), Color(231, 76, 60));
        setColor(11, Color(44, 62, 80), Color(26, 188, 156));
        setColor(12, Color(44, 62, 80), Color(0, 153, 188));
        setColor(13, Color(44, 62, 80), Color(255, 64, 129));
        setColor(14, Color(44, 62, 80), Color(149, 117, 205));
        setColor(15, Color(44, 62, 80), Color(161, 136, 127));
    }

    public void setColor(int id, Color background, Color main) {
        sets[id].main = main;
        sets[id].background = background;
        sets[id].lock = new Color(main.r, main.g, main.b, 0.5f);
    }

    public static Color Color(int r, int g, int b) {
        return  new Color(r/255.f, g/255.f, b/255.f, 1.f);
    }

    public Color getMain(int id){
        return sets[id].main;
    }

    public Color getBackground(int id){
        return sets[id].background;
    }

    public Color getLock(int id){
        return sets[id].lock;
    }
}
