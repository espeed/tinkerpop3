package com.tinkerpop.gremlin.process.graph.step.sideEffect

import com.tinkerpop.gremlin.process.Traversal
import com.tinkerpop.gremlin.structure.Vertex

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
class GroovyGroupCountTestImpl extends GroupCountTest {

    public Traversal<Vertex, Map<Object, Long>> get_g_V_outXcreatedX_groupCountXnameX() {
        g.V.out('created').groupCount { it.value('name') }
    }

    public Traversal<Vertex, Map<Object, Long>> get_g_V_outXcreatedX_name_groupCount() {
        g.V.out('created').name.groupCount
    }

    public Traversal<Vertex, Map<Object, Long>> get_g_V_outXcreatedX_name_groupCount_asXaX() {
        g.V.out('created').name.groupCount.as('a')
    }

    public Traversal<Vertex, Map<Object, Long>> get_g_V_filterXfalseX_groupCount() {
        g.V.filter { false }.groupCount;
    }

    public Traversal<Vertex, Map<Object, Long>> get_g_V_asXxX_out_groupCountXnameX_asXaX_jumpXx_loops_lt_2X_capXaX() {
        g.V.as('x').out.groupCount{ it.value('name') }.as('a').jump('x') { it.loops < 2 }.cap('a')
    }

    public Traversal<Vertex, Map<Object, Long>> get_g_V_asXxX_out_groupCountXnameX_asXaX_jumpXx_2X_capXaX() {
        g.V.as('x').out.groupCount{ it.value('name') }.as('a').jump('x', 2).cap('a')
    }
}
