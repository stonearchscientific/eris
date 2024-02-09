package com.stonearchscientific.example;

import com.stonearchscientific.eris.Context;
import com.stonearchscientific.eris.Matrix;
import java.util.Arrays;

public class ErisExample {
    public static Context<String, String> context;

    ErisExample() {
        int[][] example = {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 0, 1},
                {0, 0, 1, 1, 0},
                {0, 0, 0, 0, 1},
                {1, 1, 1, 1, 0}
        };

        Matrix relation = new Matrix(example);

        Context.Builder<String, String> builder = new Context.Builder<>();
        context = builder.withObjects(Arrays.asList("1", "2", "3", "4", "5"))
                  .withAttributes(Arrays.asList("a", "b", "c", "d", "e"))
                  .withRelation(relation)
                  .build();
    }
}