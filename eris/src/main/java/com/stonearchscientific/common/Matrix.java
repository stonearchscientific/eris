package com.stonearchscientific.common;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.Direction;
public class Matrix {
    public static int[][] generateAdjacencyMatrix(Graph graph) {
        List<Vertex> vertices = new ArrayList<>();
        for (Vertex vertex : graph.getVertices()) {
            vertices.add(vertex);
        }

        for (int i = 0; i < vertices.size(); i++) {
            System.out.println(i + " : " + vertices.get(i).getProperty("label"));
        }

        int n = vertices.size();
        int[][] adjacencyMatrix = new int[n][n];

        for (Edge edge : graph.getEdges()) {
            Vertex outVertex = edge.getVertex(Direction.OUT);
            Vertex inVertex = edge.getVertex(Direction.IN);
            if (((Concept) outVertex.getProperty("label")).greaterOrEqual((Concept) inVertex.getProperty("label"))) {
                int outIndex = vertices.indexOf(outVertex);
                int inIndex = vertices.indexOf(inVertex);
                adjacencyMatrix[outIndex][inIndex] = 1;
                adjacencyMatrix[inIndex][outIndex] = 1; // For undirected graph
            }
        }

        return adjacencyMatrix;
    }
}