package com.tinkerpop.gremlin.process.graph.step.filter

import com.tinkerpop.gremlin.process.Path
import com.tinkerpop.gremlin.process.Traversal
import com.tinkerpop.gremlin.structure.Vertex

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @author Daniel Kuppitz (daniel at thinkaurelius.com)
 */
class GroovyExceptTestImpl extends ExceptTest {

    public Traversal<Vertex, Vertex> get_g_v1_out_exceptXg_v2X(final Object v1Id, final Object v2Id) {
        g.v(v1Id).out.except(g.v(v2Id))
    }

    public Traversal<Vertex, Vertex> get_g_v1_out_aggregate_asXxX_out_exceptXxX(final Object v1Id) {
        g.v(v1Id).out.aggregate.as('x').out.except('x')
    }

    public Traversal<Vertex, String> get_g_v1_outXcreatedX_inXcreatedX_exceptXg_v1X_valueXnameX(final Object v1Id) {
        g.v(v1Id).out('created').in('created').except(g.v(v1Id)).value('name')
    }

    public Traversal<Vertex, Vertex> get_g_V_exceptXg_VX() {
        g.V.out.except(g.V.toList())
    }

    public Traversal<Vertex, Vertex> get_g_V_exceptXX() {
        g.V.out.except([])
    }

    Traversal<Vertex, Path> get_g_v1_asXxX_bothEXcreatedX_exceptXeX_aggregate_asXeX_otherV_jumpXx_true_trueX_path(final Object v1Id) {
        g.v(v1Id).as('x').bothE("created").except('e').aggregate.as('e').otherV.jump('x') { true } { true }.path
    }
}
