package com.stonearchscientific.example;

import java.util.BitSet;
import java.util.ArrayList;
import com.stonearchscientific.eris.Concept;
import com.stonearchscientific.eris.Lattice;
import com.stonearchscientific.eris.Context;
import com.stonearchscientific.eris.Matrix;
import java.util.Arrays;

public class ErisExample {
    public static Context context;

    ErisExample() {
        int[][] example = {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 0, 1},
                {0, 0, 1, 1, 0},
                {0, 0, 0, 0, 1},
                {1, 1, 1, 1, 0}
        };

        Matrix relation = new Matrix(example);

        context = new Context(new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5")),
                  new ArrayList<String>(Arrays.asList("a", "b", "c", "d", "e")), relation);
        /*
        Context.Builder<String, String> builder = new Context.Builder<>();
        context = builder.withObjects(Arrays.asList("1", "2", "3", "4", "5"))
                  .withAttributes(Arrays.asList("a", "b", "c", "d", "e"))
                  .withRelation(relation)
                  .build();

        */
        //context.dual();
        //Context.Iterator<String, String> iterator;
        /*= context.iterator();
        while (iterator.hasNext()) {
            System.out.println("Context:Concept = " + iterator.next());
        }

        */
    }
}