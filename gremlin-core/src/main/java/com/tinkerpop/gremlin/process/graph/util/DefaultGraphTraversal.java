package com.tinkerpop.gremlin.process.graph.util;

import com.tinkerpop.gremlin.process.graph.GraphTraversal;
import com.tinkerpop.gremlin.process.graph.strategy.DedupOptimizerStrategy;
import com.tinkerpop.gremlin.process.graph.strategy.IdentityReductionStrategy;
import com.tinkerpop.gremlin.process.graph.strategy.SideEffectCapStrategy;
import com.tinkerpop.gremlin.process.graph.strategy.UnrollJumpStrategy;
import com.tinkerpop.gremlin.process.util.DefaultTraversal;
import com.tinkerpop.gremlin.structure.Graph;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DefaultGraphTraversal<S, E> extends DefaultTraversal<S, E> implements GraphTraversal<S, E> {

    public DefaultGraphTraversal() {
        super();
        this.traversalStrategies.register(DedupOptimizerStrategy.instance());
        this.traversalStrategies.register(IdentityReductionStrategy.instance());
        this.traversalStrategies.register(SideEffectCapStrategy.instance());
        this.traversalStrategies.register(UnrollJumpStrategy.instance());
    }

    public DefaultGraphTraversal(final Graph graph) {
        this();
        this.memory().set(Graph.Key.hide("g"), graph);
    }
}
