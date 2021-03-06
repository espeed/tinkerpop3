package com.tinkerpop.gremlin.process.graph.step.map;

import com.tinkerpop.gremlin.AbstractGremlinTest;
import com.tinkerpop.gremlin.LoadGraphWith;
import com.tinkerpop.gremlin.process.Traversal;
import com.tinkerpop.gremlin.process.util.MapHelper;
import com.tinkerpop.gremlin.structure.Vertex;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.tinkerpop.gremlin.LoadGraphWith.GraphData.CLASSIC;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @author Joshua Shinavier (http://fortytwo.net)
 */
public abstract class ChooseTest extends AbstractGremlinTest {

    public abstract Traversal<Vertex, String> get_g_V_chooseXname_length_5XoutXinX_name();

    public abstract Traversal<Vertex, String> get_g_v1_chooseX0XoutX_name(Object v1Id);

    public abstract Traversal<Vertex, String> get_g_V_hasXageX_chooseXname_lengthX5_in_4_out_3_bothX_name();

    public abstract Traversal<Vertex, Object> get_g_V_chooseXout_count_nextX2L_valueXnameX_3L_valuesX();

    @Test
    @LoadGraphWith(CLASSIC)
    public void g_V_chooseXname_length_5XoutXinX_name() {
        final Traversal<Vertex, String> traversal = get_g_V_chooseXname_length_5XoutXinX_name();
        printTraversalForm(traversal);
        Map<String, Long> counts = new HashMap<>();
        int counter = 0;
        while (traversal.hasNext()) {
            MapHelper.incr(counts, traversal.next(), 1l);
            counter++;
        }
        assertFalse(traversal.hasNext());
        assertEquals(9, counter);
        assertEquals(5, counts.size());
        assertEquals(Long.valueOf(1), counts.get("vadas"));
        assertEquals(Long.valueOf(3), counts.get("josh"));
        assertEquals(Long.valueOf(2), counts.get("lop"));
        assertEquals(Long.valueOf(2), counts.get("marko"));
        assertEquals(Long.valueOf(1), counts.get("peter"));

    }

    @Test
    @LoadGraphWith(CLASSIC)
    public void g_v1_chooseX0XoutX_name() {
        final Traversal<Vertex, String> traversal = get_g_v1_chooseX0XoutX_name(convertToVertexId("marko"));
        printTraversalForm(traversal);
        Map<String, Long> counts = new HashMap<>();
        int counter = 0;
        while (traversal.hasNext()) {
            MapHelper.incr(counts, traversal.next(), 1l);
            counter++;
        }
        assertFalse(traversal.hasNext());
        assertEquals(3, counter);
        assertEquals(3, counts.size());
        assertEquals(Long.valueOf(1), counts.get("vadas"));
        assertEquals(Long.valueOf(1), counts.get("josh"));
        assertEquals(Long.valueOf(1), counts.get("lop"));
    }

    @Test
    @LoadGraphWith(CLASSIC)
    public void g_V_hasXageX_chooseXname_lengthX5_in_4_outX_name() {
        final Traversal<Vertex, String> traversal = get_g_V_hasXageX_chooseXname_lengthX5_in_4_out_3_bothX_name();
        printTraversalForm(traversal);
        Map<String, Long> counts = new HashMap<>();
        int counter = 0;
        while (traversal.hasNext()) {
            MapHelper.incr(counts, traversal.next(), 1l);
            counter++;
        }
        assertFalse(traversal.hasNext());
        assertEquals(3, counter);
        assertEquals(3, counts.size());
        assertEquals(Long.valueOf(1), counts.get("marko"));
        assertEquals(Long.valueOf(1), counts.get("lop"));
        assertEquals(Long.valueOf(1), counts.get("ripple"));
    }

    @Test
    @LoadGraphWith(CLASSIC)
    public void g_V_chooseXout_count_nextX2L_valueXnameX_3L_valuesX() {
        final Traversal<Vertex, Object> traversal = get_g_V_chooseXout_count_nextX2L_valueXnameX_3L_valuesX();
        printTraversalForm(traversal);
        Map<String, Long> counts = new HashMap<>();
        int counter = 0;
        while (traversal.hasNext()) {
            MapHelper.incr(counts, traversal.next().toString(), 1l);
            counter++;
        }
        assertFalse(traversal.hasNext());
        assertEquals(2, counter);
        assertEquals(2, counts.size());
        assertEquals(Long.valueOf(1), counts.get("{name=marko, age=29}"));
        assertEquals(Long.valueOf(1), counts.get("josh"));
    }

    public static class JavaChooseTest extends ChooseTest {

        public Traversal<Vertex, String> get_g_V_chooseXname_length_5XoutXinX_name() {
            return g.V().choose(t -> t.get().<String>value("name").length() == 5,
                    g.of().out(),
                    g.of().in()).value("name");
        }

        public Traversal<Vertex, String> get_g_v1_chooseX0XoutX_name(Object v1Id) {
            return g.v(v1Id).choose(t -> 0, new HashMap() {{
                put(0, g.of().out().value("name"));
            }});
        }

        public Traversal<Vertex, String> get_g_V_hasXageX_chooseXname_lengthX5_in_4_out_3_bothX_name() {
            return g.V().has("age").choose(t -> t.get().<String>value("name").length(), new HashMap() {{
                put(5, g.of().in());
                put(4, g.of().out());
                put(3, g.of().both());
            }}).value("name");
        }

        public Traversal<Vertex, Object> get_g_V_chooseXout_count_nextX2L_valueXnameX_3L_valuesX() {
            return g.V().choose(t -> t.get().out().count().next(), new HashMap() {{
                put(2L, g.of().value("name"));
                put(3L, g.of().values());
            }});
        }
    }
}