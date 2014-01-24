package com.tinkerpop.gremlin.oltp.map;

import com.tinkerpop.gremlin.Pipeline;
import com.tinkerpop.gremlin.util.GremlinHelper;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class BackPipe<S,E> extends MapPipe<S, E> {

    public String as;

    public BackPipe(final Pipeline pipeline, final String as) {
        super(pipeline);
        this.as = as;
        this.setFunction(holder -> holder.getPath().get(this.as));
    }

    public String toString() {
        return GremlinHelper.makePipeString(this, this.as);
    }
}