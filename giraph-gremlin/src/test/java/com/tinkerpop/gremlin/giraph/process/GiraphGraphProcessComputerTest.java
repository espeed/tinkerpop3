package com.tinkerpop.gremlin.giraph.process;

import com.tinkerpop.gremlin.giraph.GiraphGraphProvider;
import com.tinkerpop.gremlin.process.ProcessComputerSuite;
import org.junit.Ignore;
import org.junit.runner.RunWith;

/**
 * Executes the Standard Gremlin Process Test Suite using GiraphGraph.
 *
 * @author Stephen Mallette (http://stephen.genoprime.com)
 */
@RunWith(ProcessComputerSuite.class)
@Ignore
@ProcessComputerSuite.GraphProviderClass(GiraphGraphProvider.class)
public class GiraphGraphProcessComputerTest {
}