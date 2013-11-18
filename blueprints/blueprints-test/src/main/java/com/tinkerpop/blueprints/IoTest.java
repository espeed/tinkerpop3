package com.tinkerpop.blueprints;

import com.tinkerpop.blueprints.io.GraphReader;
import com.tinkerpop.blueprints.io.graphml.GraphMLReader;
import com.tinkerpop.blueprints.io.graphml.GraphMLWriter;
import com.tinkerpop.blueprints.util.StreamFactory;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.Assert.assertEquals;

/**
 * @author Joshua Shinavier (http://fortytwo.net)
 * @author Stephen Mallette (http://stephen.genoprime.com)
 */
public class IoTest {

    // todo: should expand test here significantly.  see blueprints2

    private static final String RESOURCE_PATH_PREFIX = "/com/tinkerpop/blueprints/util/io/graphml/";

    @Test
    public void shouldReadGraphML() throws IOException {
        final Graph g = BlueprintsSuite.GraphManager.get().newTestGraph();
        readGraphMLIntoGraph(g);

        assertEquals(6, StreamFactory.stream(g.query().vertices()).count());
        assertEquals(6, StreamFactory.stream(g.query().edges()).count());
    }

    @Test
    public void shouldWriteNormalizedGraphML() throws Exception {
        final Graph g = BlueprintsSuite.GraphManager.get().newTestGraph();
        readGraphMLIntoGraph(g);

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final GraphMLWriter w = new GraphMLWriter.Builder(g).setNormalize(true).build();
        w.outputGraph(bos);

        final String expected = streamToString(IoTest.class.getResourceAsStream(RESOURCE_PATH_PREFIX + "graph-example-1-normalized.xml"));
        assertEquals(expected.replace("\n", "").replace("\r", ""), bos.toString().replace("\n", "").replace("\r", ""));
    }

    @Test
    public void shouldWriteNormalizedGraphMLWithEdgeLabel() throws Exception {
        final Graph g = BlueprintsSuite.GraphManager.get().newTestGraph();
        readGraphMLIntoGraph(g);

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final GraphMLWriter w = new GraphMLWriter.Builder(g)
                .setNormalize(true)
                .setEdgeLabelKey("label").build();
        w.outputGraph(bos);

        String expected = streamToString(IoTest.class.getResourceAsStream(RESOURCE_PATH_PREFIX + "graph-example-1-schema-valid.xml"));
        assertEquals(expected.replace("\n", "").replace("\r", ""), bos.toString().replace("\n", "").replace("\r", ""));
    }

    /**
     * Note: this is only a very lightweight test of writer/reader encoding. It is known that there are characters
     * which, when written by GraphMLWriter, cause parse errors for GraphMLReader. However, this happens uncommonly
     * enough that is not yet known which characters those are.
     */
    @Test
    public void shouldProperlyEncodeWithGraphML() throws Exception {
        final Graph g = BlueprintsSuite.GraphManager.get().newTestGraph();
        final Vertex v = g.addVertex(Property.of(Property.Key.ID, "1"));
        v.setProperty("text", "\u00E9");

        final GraphMLWriter w = new GraphMLWriter.Builder(g).build();

        final File f = File.createTempFile("test", "txt");
        try (final OutputStream out = new FileOutputStream(f)) {
            w.outputGraph(out);
        }

        // todo: this test won't work if a fresh graph instance isn't created
        final Graph g2 = BlueprintsSuite.GraphManager.get().newTestGraph();
        final GraphMLReader r = new GraphMLReader.Builder(g2).build();

        try (final InputStream in = new FileInputStream(f)) {
            r.inputGraph(in);
        }

        final Vertex v2 = g2.query().ids("1").vertices().iterator().next();
        assertEquals("\u00E9", v2.getProperty("text").getValue());
    }

    private static void readGraphMLIntoGraph(final Graph g) throws IOException {
        final GraphReader reader = new GraphMLReader.Builder(g).build();
        try (final InputStream stream = IoTest.class.getResourceAsStream(RESOURCE_PATH_PREFIX + "graph-example-1.xml")) {
            reader.inputGraph(stream);
        }
    }

    private String streamToString(final InputStream in) throws IOException {
        final Writer writer = new StringWriter();
        final char[] buffer = new char[1024];
        try (final Reader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"))) {
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        }

        return writer.toString();
    }
}