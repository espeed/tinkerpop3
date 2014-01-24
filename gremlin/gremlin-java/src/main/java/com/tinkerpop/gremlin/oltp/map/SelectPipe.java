package com.tinkerpop.gremlin.oltp.map;

import com.tinkerpop.gremlin.Path;
import com.tinkerpop.gremlin.Pipeline;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class SelectPipe extends MapPipe<Object, Path> {

    public SelectPipe(final Pipeline pipeline, final String... ases) {
        super(pipeline, holder -> {
            final Path path = holder.getPath();
            return ases.length == 0 ? path : path.subset(ases);
        });
    }
}