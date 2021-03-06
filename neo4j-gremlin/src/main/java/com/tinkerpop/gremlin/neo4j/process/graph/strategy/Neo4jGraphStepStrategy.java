package com.tinkerpop.gremlin.neo4j.process.graph.strategy;

import com.tinkerpop.gremlin.neo4j.process.graph.step.map.Neo4jGraphStep;
import com.tinkerpop.gremlin.process.Step;
import com.tinkerpop.gremlin.process.Traversal;
import com.tinkerpop.gremlin.process.TraversalStrategy;
import com.tinkerpop.gremlin.process.graph.step.filter.HasStep;
import com.tinkerpop.gremlin.process.util.EmptyStep;
import com.tinkerpop.gremlin.process.util.TraversalHelper;

/**
 * @author Pieter Martin
 */
public class Neo4jGraphStepStrategy implements TraversalStrategy.NoDependencies {

    private static final Neo4jGraphStepStrategy INSTANCE = new Neo4jGraphStepStrategy();

    private Neo4jGraphStepStrategy() {
    }

    public void apply(final Traversal traversal) {
        if (traversal.getSteps().get(0) instanceof Neo4jGraphStep) {
            final Neo4jGraphStep neo4jGraphStep = (Neo4jGraphStep) traversal.getSteps().get(0);
            Step currentStep = neo4jGraphStep.getNextStep();
            while (true) {
                if (currentStep == EmptyStep.instance() || TraversalHelper.isLabeled(currentStep)) break;

                if (currentStep instanceof HasStep) {
                    neo4jGraphStep.hasContainers.add(((HasStep) currentStep).hasContainer);
                    TraversalHelper.removeStep(currentStep, traversal);
                } else {
                    break;
                }
                currentStep = currentStep.getNextStep();
            }
        }
    }

    public static Neo4jGraphStepStrategy instance() {
        return INSTANCE;
    }

}
