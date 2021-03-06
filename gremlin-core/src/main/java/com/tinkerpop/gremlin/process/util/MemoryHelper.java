package com.tinkerpop.gremlin.process.util;

import com.tinkerpop.gremlin.process.Traversal;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class MemoryHelper {

    public static void validateVariable(final String key, final Object value) throws IllegalArgumentException {
        if (null == value)
            throw Traversal.Memory.Exceptions.variableValueCanNotBeNull();
        if (null == key)
            throw Traversal.Memory.Exceptions.variableKeyCanNotBeNull();
        if (key.isEmpty())
            throw Traversal.Memory.Exceptions.variableKeyCanNotBeEmpty();
    }

}
