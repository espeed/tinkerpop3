package com.tinkerpop.gremlin.process.oltp.util;

import com.tinkerpop.gremlin.process.Pipe;
import com.tinkerpop.gremlin.process.Traversal;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class GremlinHelper {

    private static final String UNDERSCORE = "_";

    public static boolean isLabeled(final Pipe pipe) {
        return !pipe.getAs().startsWith(UNDERSCORE);
    }

    public static boolean isLabeled(final String as) {
        return !as.startsWith(UNDERSCORE);
    }

    public static <S, E> Pipe<S, E> getAs(final String as, final Traversal<?, ?> pipeline) {
        return (Pipe) pipeline.getPipes().stream()
                .filter(p -> as.equals(p.getAs()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The provided name does not exist: " + as));
    }

    public static boolean asExists(final String as, final Traversal<?, ?> pipeline) {
        return pipeline.getPipes().stream()
                .filter(p -> as.equals(p.getAs()))
                .findFirst().isPresent();
    }

    public static <S, E> Pipe<S, ?> getStart(final Traversal<S, E> pipeline) {
        return pipeline.getPipes().get(0);
    }

    public static <S, E> Pipe<?, E> getEnd(final Traversal<S, E> pipeline) {
        return pipeline.getPipes().get(pipeline.getPipes().size() - 1);
    }

    public static boolean areEqual(final Iterator a, final Iterator b) {
        while (a.hasNext() || b.hasNext()) {
            if (a.hasNext() != b.hasNext())
                return false;
            if (!a.next().equals(b.next()))
                return false;
        }
        return true;
    }

    public static boolean hasNextIteration(final Iterator iterator) {
        if (iterator.hasNext()) {
            while (iterator.hasNext()) {
                iterator.next();
            }
            return true;
        } else {
            return false;
        }
    }

    public static void iterate(final Iterator iterator) {
        try {
            while (true) {
                iterator.next();
            }
        } catch (final NoSuchElementException e) {

        }
    }

    public static void removePipe(final Pipe pipe, final Traversal pipeline) {
        final List<Pipe> pipes = pipeline.getPipes();
        final int index = pipes.indexOf(pipe);
        if (index - 1 >= 0 && index + 1 < pipes.size()) {
            pipes.get(index - 1).setNextPipe(pipes.get(index + 1));
            pipes.get(index + 1).setPreviousPipe(pipes.get(index - 1));
        }
        pipes.remove(pipe);
    }

    public static void insertPipe(final Pipe pipe, final int index, final Traversal pipeline) {
        final List<Pipe> pipes = pipeline.getPipes();
        final Pipe leftPipe = pipes.get(index - 1);
        final Pipe rightPipe = pipes.get(index);
        leftPipe.setNextPipe(pipe);
        pipe.setPreviousPipe(leftPipe);
        pipe.setNextPipe(rightPipe);
        rightPipe.setPreviousPipe(pipe);
        pipes.add(index, pipe);
    }

    public static String makePipeString(final Pipe pipe, final Object... arguments) {
        final StringBuilder builder = new StringBuilder(pipe.getClass().getSimpleName());
        if (arguments.length > 0) {
            builder.append("(");
            for (int i = 0; i < arguments.length; i++) {
                if (i > 0) builder.append(",");
                builder.append(arguments[i]);
            }
            builder.append(")");
        }
        if (GremlinHelper.isLabeled(pipe))
            builder.append("@").append(pipe.getAs());
        return builder.toString();
    }
}