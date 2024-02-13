package com.stonearchscientific.example;

import org.junit.Test;

public class ErisExampleTest {
    @Test
    public void testErisExample() {
        ErisExample example = new ErisExample();
        //Context.InnerClass innerClass = ErisExample.context.inner();
        ErisExample.context.draw("eris-example-concept-lattice.png");
    }
}