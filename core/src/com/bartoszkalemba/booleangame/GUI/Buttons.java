package com.bartoszkalemba.booleangame.GUI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class Buttons {

    private List<Button> buttons = new ArrayList<Button>();

    public void add(String name){
        Button button = new Button(name);
        buttons.add(button);
    }

    public Button get(String name){
        for(Button b : buttons)
            if (b.getName().equals(name))
                return b;

        return null;
    }

    public List<Button> getList(){
        return buttons;
    }

    public void update(){
        for(Button b : buttons) b.update();
    }

    public void draw(SpriteBatch batch){
        for(Button b : buttons) b.draw(batch);
    }
}
