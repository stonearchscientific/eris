package com.stonearchscientific.eris;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

import java.io.File;
import java.util.Collection;

public class MultiFunction<C extends Comparable, D extends Comparable> extends AbstractContext<Domain<C, D>> {


    public MultiFunction() {
        super();
        lattice = new Lattice<>(graph, Domain.none());
    }
    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }
    public void draw(final String filename) {
        DotFormatFixture<Domain<C, D>> fixture = new DotFormatFixture<>();
        Iterator<Domain<C, D>> iterator = this.iterator(lattice.bottom().getProperty(Lattice.LABEL), fixture);
        while (iterator.hasNext()) {
            iterator.next();
        }
        System.out.println(fixture.toString());
        String graphvizOutput = fixture.toString();

        try {
            MutableGraph g = new Parser().read(graphvizOutput);
            Graphviz.fromGraph(g).width(700).render(Format.PNG).toFile(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
