package com.bartoszkalemba.booleangame.engine;

import java.util.ArrayList;
import java.util.List;

public class LevelManager extends LevelFile {

    public int counterSteps = 0;

    public void addVertex(float x, float y, Vertex.State state) {
        vertices.add(new Vertex(x, y, state, maxID));
        maxID++;
    }

    // Removing vertex with connections
    public void removeVertex(Vertex v) {
        for (Vertex V : vertexConnections(v))
            removeConnection(v, V);
        vertices.remove(v);
    }

    // Adding new connection between vertices
    public void addConnection(Vertex v1, Vertex v2) {
        connections.add(new Connection(v1, v2));
    }


    // Checking connection between vertices
    public boolean isConnection(Vertex v1, Vertex v2) {
        for (Connection c : connections) {
            if (c.v1 == v1 && c.v2 == v2)
                return true;

            if (c.v1 == v2 && c.v2 == v1)
                return true;
        }

        return false;
    }

    // Removing connection between vertices
    public void removeConnection(Vertex v1, Vertex v2) {
        List<Connection> toRemove = new ArrayList<Connection>();

        for(Connection c : connections) {
            if ((c.v1 == v1 && c.v2 == v2) ||(c.v1 == v2 && c.v2 == v1))
                toRemove.add(c);
        }

        for (Connection c : toRemove)
            connections.remove(c);
    }


    // List of outgoing connections from the vertex
    public List<Vertex> vertexConnections(Vertex v) {
        List<Vertex> list = new ArrayList<Vertex>();
        for (Connection c : connections) {
            if (c.v1 == v)
                list.add(c.v2);
            else if (c.v2 == v)
                list.add(c.v1);
        }
        return list;
    }

    public void clear() {
        vertices.clear();
        connections.clear();
        maxID = 0;
    }



    //// GETERS & SETTERS ////
    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public List<Connection> getConnections() {
        return connections;
    }
}
