package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
public class Matrix {
    public BitSet[] matrix;

    public Matrix(int[][] matrix) {
        this.matrix = new BitSet[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            this.matrix[i] = new BitSet(matrix[i].length);
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 1) {
                    this.matrix[i].set(j);
                }
            }
        }
    }
    public Matrix(int n, int m) {
        matrix = new BitSet[n];
        for (int i = 0; i < n; i++) {
            matrix[i] = new BitSet(m);
        }
    }
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