package com.bartoszkalemba.booleangame.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class LevelFile {

    protected int maxID = 0;
    protected List<Vertex> vertices = new ArrayList<Vertex>();
    protected List<Connection> connections = new ArrayList<Connection>();
    protected String filename;

    public LevelFile(String filename){
        this.filename = filename;
        load(filename);
    }

    public LevelFile(){
        this.filename = filename;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    /// --- LOAD ---- ///
    //////////////////////////////////////////////////////////////////////////////////////////////////

    public void load(String filename) {
        try {
            _load(filename);
            System.out.println("LevelFile: Załadowano plik: " + filename + "\n");
        } catch (Exception exc) {
            System.out.println("LevelFile: Nie załadowano pliku: " + filename + " " + exc.toString() + "\n" );
        } finally {}
    }

    private void _load(String filename) throws FileNotFoundException {

        // usun wszystkie dotychczasowe wierzcholki oraz polaczenia
        vertices.clear();
        connections.clear();

        FileHandle handle = Gdx.files.internal(filename);

        String buffer = handle.readString();
        Scanner in = new Scanner(buffer);

        while (in.hasNext())
        {
            String text = in.next();

            if ( text.equals("maxID") ) {
                maxID = Integer.parseInt(in.next());
            }

            if ( text.equals("vertex") ) {
                addVertex(in.next(), in.next(), in.next(), in.next());
            }

            if ( text.equals("connection")) {
                addConnection(in.next(), in.next());
            }
        }

    }

    private void addVertex(String pX, String pY, String state, String id) {
        vertices.add(new Vertex(Float.parseFloat(pX),
                Float.parseFloat(pY),
                Vertex.StringToState(state), Integer.parseInt(id)));
    }

    private void addConnection(String vID1, String vID2){
        Vertex v1 = getVertexByID(Integer.parseInt(vID1));
        Vertex v2 = getVertexByID(Integer.parseInt(vID2));

        connections.add(new Connection(v1, v2));
    }

    public Vertex getVertexByID(int id) {
        for (Vertex v : vertices) {
            if (v.id == id)
                return  v;
        }
        return null;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////
    /// --- SAVE ---- ///
    //////////////////////////////////////////////////////////////////////////////////////////////////
    /*public void save(String filename) {
        try {
            _save(filename);
            System.out.println("LevelFile: Zapisano plik: " + filename + "\n");
        } catch (Exception exc) {
            System.out.println("LevelFile: Nie zapisano pliku: " + filename + "\n");
        } finally {
        }
    }

    private void _save(String filename) throws FileNotFoundException {
        FileHandle handle = Gdx.files.local(filename);

        String header = "maxID " + maxID + " ";
        handle.writeString(header, false);

        for (Vertex v : vertices) {
            String data = "vertex " + v.position.x + " " + v.position.y + " " + Vertex.StateToString(v.state) + " " + v.id  + " ";
            handle.writeString(data, true);
        }

        for (Connection c : connections) {
            String data = "connection " + c.v1.id + " " + c.v2.id + " ";
            handle.writeString(data, true);
        }
    }*/
}
