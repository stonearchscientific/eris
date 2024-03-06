package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

import java.util.*;

public class FormatFixture<R extends Relatable<R>> extends DefaultFixture<R> {
    protected StringBuilder sb;
    protected R[] labels;
    protected Matrix edges;
    public FormatFixture(final Vertex start, int size) {
        super(start);
        sb = new StringBuilder();
        labels = (R[]) new Relatable[size];
        //labels.add(start.getProperty(Lattice.LABEL));
        edges = new Matrix(size, size);
    }
    @Override
    public R proceed() {
        R label = super.proceed();
        labels[(int) visiting.getProperty("ID")] = label;
        return label;
    }
    @Override
    public boolean apply(final Vertex target, final Edge edge) {
        R sourceLabel = visiting.getProperty(Lattice.LABEL);
        R targetLabel = target.getProperty(Lattice.LABEL);
        if (!sourceLabel.equals(targetLabel)) {
            if (filter.test(targetLabel, sourceLabel)) {
                int sourceID = visiting.getProperty("ID");
                int targetID = target.getProperty("ID");
                System.out.println("addUndirectedEdge(" + sourceLabel + ", " + targetLabel + ")");
                System.out.println("addUndirectedEdge(" + sourceID + ", " + targetID + ")");
                edges.matrix[sourceID].set(targetID);
                //edges.put(sourceID, targetID);
                //edges.put(targetID, sourceID);
            }
        }
        return super.apply(target, edge);
    }
    @Override
    public String toString() {
        System.out.println("LABELS");
        int index = 0;
        for (R label : labels) {
            System.out.println(index + " : " + label);
            index++;
        }
//        System.out.println("EDGES");
//        for (Map.Entry<Integer, List<Integer>> entry : edges.entrySet()) {
//            R sourceLabel = ((ArrayList<R>) labels).get(entry.getKey());
//            List<Integer> targets = entry.getValue();
//            for (int target : targets) {
//                R targetLabel = ((ArrayList<R>) labels).get(target);
//                System.out.println(sourceLabel + " -> " + targetLabel);
//            }
//        }
        return sb.toString();
    }
}
