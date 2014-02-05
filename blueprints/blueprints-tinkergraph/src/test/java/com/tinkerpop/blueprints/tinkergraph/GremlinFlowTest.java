package com.tinkerpop.blueprints.tinkergraph;

import com.tinkerpop.blueprints.Graph;
import org.junit.Test;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class GremlinFlowTest {

    @Test
    public void shouldFlow() {
        Graph g = TinkerFactory.createClassic();
        g.V().has("name", "marko").has("age", 29).out("created", "knows").forEachRemaining(System.out::println);
    }
}