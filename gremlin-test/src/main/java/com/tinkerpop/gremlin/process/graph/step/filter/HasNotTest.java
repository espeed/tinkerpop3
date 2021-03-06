package com.tinkerpop.gremlin.process.graph.step.filter;

import com.tinkerpop.gremlin.LoadGraphWith;
import com.tinkerpop.gremlin.process.AbstractGremlinProcessTest;
import com.tinkerpop.gremlin.process.Traversal;
import com.tinkerpop.gremlin.structure.Element;
import com.tinkerpop.gremlin.structure.Vertex;
import com.tinkerpop.gremlin.util.StreamFactory;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.tinkerpop.gremlin.LoadGraphWith.GraphData.CLASSIC;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Joshua Shinavier (http://fortytwo.net)
 */
public abstract class HasNotTest extends AbstractGremlinProcessTest {

    public abstract Traversal<Vertex, Vertex> get_g_v1_hasNotXprop(final Object v1Id, final String prop);

    public abstract Traversal<Vertex, Vertex> get_g_V_hasNotXprop(final String prop);

    @Test
    @LoadGraphWith(CLASSIC)
    public void get_g_v1_hasNotXprop() {
        Traversal<Vertex, Vertex> traversal = get_g_v1_hasNotXprop(convertToVertexId("marko"), "circumference");
        printTraversalForm(traversal);
        assertEquals("marko", traversal.next().<String>value("name"));
        assertFalse(traversal.hasNext());
        traversal = get_g_v1_hasNotXprop(convertToVertexId("marko"), "name");
        printTraversalForm(traversal);
        assertFalse(traversal.hasNext());
    }

    @Test
    @LoadGraphWith(CLASSIC)
    public void get_g_V_hasNotXprop() {
        Traversal<Vertex, Vertex> traversal = get_g_V_hasNotXprop("circumference");
        printTraversalForm(traversal);
        final List<Element> list = StreamFactory.stream(traversal).collect(Collectors.toList());
        assertEquals(6, list.size());
    }

    public static class JavaHasNotTest extends HasNotTest {
        public JavaHasNotTest() {
            requiresGraphComputer = false;
        }

        public Traversal<Vertex, Vertex> get_g_v1_hasNotXprop(final Object v1Id, final String prop) {
            return g.v(v1Id).hasNot(prop);
        }

        public Traversal<Vertex, Vertex> get_g_V_hasNotXprop(final String prop) {
            return g.V().hasNot(prop);
        }
    }

    public static class JavaComputerHasNotTest extends HasNotTest {
        public JavaComputerHasNotTest() {
            requiresGraphComputer = true;
        }

        public Traversal<Vertex, Vertex> get_g_v1_hasNotXprop(final Object v1Id, final String prop) {
            return g.v(v1Id).<Vertex>hasNot(prop).submit(g.compute());
        }

        public Traversal<Vertex, Vertex> get_g_V_hasNotXprop(final String prop) {
            return g.V().<Vertex>hasNot(prop).submit(g.compute());
        }
    }
}