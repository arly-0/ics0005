import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.Before;
import org.junit.Test;

public class GraphTaskTest {

    private Graph graph;

    @Before
    public void SetUp() {
        graph = new Graph("Test");
    }

    @Test
    public void testEccentricity_SimpleGraph() {
        // Create vertices
        Vertex v1 = graph.createVertex("v1");
        Vertex v2 = graph.createVertex("v2");
        Vertex v3 = graph.createVertex("v3");
        Vertex v4 = graph.createVertex("v4");
        Vertex v5 = graph.createVertex("v5");

        // Create arcs (edges) to form a simple graph with 5 vertices and 6 edges
        graph.createArc("a12", v1, v2);
        graph.createArc("a21", v2, v1);
        graph.createArc("a23", v2, v3);
        graph.createArc("a32", v3, v2);
        graph.createArc("a34", v3, v4);
        graph.createArc("a43", v4, v3);
        graph.createArc("a45", v4, v5);
        graph.createArc("a54", v5, v4);
        graph.createArc("a51", v5, v1);
        graph.createArc("a15", v1, v5);

        // Eccentricity of v1 should be 2
        assertEquals(2, v1.eccentricity());
    }

    @Test
    public void testEccentricity_EmptyGraph() {
        // Create an empty graph
        graph.createRandomSimpleGraph(0, 0);

        // Eccentricity of a vertex in an empty graph should throw NullPointerException
        assertThrows(NullPointerException.class, () -> {
            graph.getFirst().eccentricity();
        });
    }

    @Test
    public void testEccentricity_DisconnectedVertex() {
        // Create a graph with 5 vertices and 2 disconnected components
        Vertex v1 = graph.createVertex("v1");
        Vertex v2 = graph.createVertex("v2");
        Vertex v3 = graph.createVertex("v3");
        Vertex v4 = graph.createVertex("v4");
        Vertex v5 = graph.createVertex("v5");

        // First component: v1-v2
        graph.createArc("a1", v1, v2);
        graph.createArc("a2", v2, v1);

        // Second component: v3-v4-v5
        graph.createArc("a3", v3, v4);
        graph.createArc("a4", v4, v3);
        graph.createArc("a5", v4, v5);
        graph.createArc("a6", v5, v4);

        // Eccentricity of a disconnected vertex should be 1
        assertEquals(1, v1.eccentricity());
    }

    @Test
    public void testEccentricity_LargeCircleGraph() {
        // Create 2000 vertices
        Vertex[] vertices = new Vertex[2000];
        for (int i = 0; i < 2000; i++) {
            vertices[i] = graph.createVertex("v" + i);
        }

        // Connect vertices to form a cycle
        for (int i = 0; i < 1999; i++) {
            graph.createArc("a" + i, vertices[i], vertices[i + 1]);
        }
        // Connect the last vertex to the first to close the cycle
        graph.createArc("a1999", vertices[1999], vertices[0]);

        // Test the eccentricity of the first vertex
        int eccentricity = graph.getFirst().eccentricity();

        assertEquals(1999, eccentricity);
    }

    @Test
    public void testEccentricity_SparseGraph() {
        // Create a sparse graph with 10 vertices
        Vertex[] vertices = new Vertex[10];
        for (int i = 0; i < 10; i++) {
            vertices[i] = graph.createVertex("v" + i);
        }

        // Connect vertices in a sparse manner
        graph.createArc("a0", vertices[0], vertices[1]);
        graph.createArc("a1", vertices[0], vertices[2]);
        graph.createArc("a2", vertices[1], vertices[3]);
        graph.createArc("a3", vertices[2], vertices[4]);
        graph.createArc("a4", vertices[3], vertices[5]);
        graph.createArc("a5", vertices[4], vertices[6]);
        graph.createArc("a6", vertices[5], vertices[7]);
        graph.createArc("a7", vertices[6], vertices[8]);
        graph.createArc("a8", vertices[7], vertices[9]);

        // Calculate the eccentricity for the first vertex
        int eccentricity = vertices[0].eccentricity();

        // Assert the eccentricity
        assertEquals(5, eccentricity);
    }

}