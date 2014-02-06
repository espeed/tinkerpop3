package com.tinkerpop.gremlin.process.oltp.sideeffect;

import com.tinkerpop.gremlin.process.Traversal;
import com.tinkerpop.gremlin.process.oltp.map.MapPipe;
import com.tinkerpop.gremlin.process.oltp.util.GremlinHelper;
import com.tinkerpop.gremlin.structure.Direction;
import com.tinkerpop.gremlin.structure.Vertex;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class LinkPipe extends MapPipe<Vertex, Vertex> {

    public Direction direction;
    public String label;
    public String as;

    public LinkPipe(final Traversal pipeline, final Direction direction, final String label, final String as) {
        super(pipeline);
        this.direction = direction;
        this.label = label;
        this.as = as;
        super.setFunction(holder -> {
            final Vertex current = holder.get();
            final Vertex other = holder.getPath().get(as);
            if (direction.equals(Direction.IN) || direction.equals(Direction.BOTH)) {
                other.addEdge(label, current);
            }
            if (direction.equals(Direction.OUT) || direction.equals(Direction.BOTH)) {
                current.addEdge(label, other);
            }
            return current;
        });
    }

    public String toString() {
        return GremlinHelper.makePipeString(this, this.direction.name(), this.label, this.as);
    }
}