import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

public class SolverATest {
    // SolverA()
    @Test
    void getClassOfInstanceOfSolverAShouldThrowNoException() {
        SolverA solverA = new SolverA();
        assertDoesNotThrow(() -> solverA.getClass());
    }

    // apply(Node node, Vector<Edge> edges)
    @Test
    void solverAShouldReturnTrue() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Node testNode4 = new Node();
        Node testNode5 = new Node();
        Node testNode6 = new Node();
        testNode1.setTargetWeight(1);
        testNode6.setTargetWeight(5);

        Edge testEdge1 = new Edge(testNode1, testNode2, "testEdge1");
        Edge testEdge2 = new Edge(testNode1, testNode3, "testEdge2");
        Edge testEdge3 = new Edge(testNode1, testNode4, "testEdge3");
        Edge testEdge4 = new Edge(testNode1, testNode5, "testEdge4");
        Edge testEdge5 = new Edge(testNode1, testNode6, "testEdge5");

        Vector<Edge> edges = new Vector<Edge>();
        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge2, testEdge3, testEdge4, testEdge5, null });
        assertTrue(SolverA.apply(testNode1, edges));
    }

    @Test
    void solverAShouldReturnTrueWhileLoopAllPossibleValuesForDir() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Node testNode4 = new Node();
        Node testNode5 = new Node();
        Node testNode6 = new Node();
        Node testNode7 = new Node();
        testNode1.setTargetWeight(1);
        testNode7.setTargetWeight(5);

        Edge testEdge1 = new Edge(testNode1, testNode2, "testEdge1");
        Edge testEdge2 = new Edge(testNode1, testNode3, "testEdge2");
        Edge testEdge3 = new Edge(testNode1, testNode4, "testEdge3");
        Edge testEdge4 = new Edge(testNode1, testNode5, "testEdge4");
        Edge testEdge5 = new Edge(testNode1, testNode6, "testEdge5");
        Edge testEdge6 = new Edge(testNode1, testNode7, "testEdge6");

        Vector<Edge> edges = new Vector<Edge>();
        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge2, testEdge3, testEdge4, testEdge5, testEdge6 });
        assertTrue(SolverA.apply(testNode1, edges));
    }

    @Test
    void solverAShouldReturnFalse() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Node testNode4 = new Node();
        Node testNode5 = new Node();
        Node testNode6 = new Node();
        Node testNode7 = new Node();
        testNode1.setTargetWeight(2);
        testNode2.setTargetWeight(5);

        Edge testEdge1 = new Edge(testNode1, testNode2, "testEdge1");
        Edge testEdge2 = new Edge(testNode1, testNode3, "testEdge2");
        Edge testEdge3 = new Edge(testNode1, testNode4, "testEdge3");
        Edge testEdge4 = new Edge(testNode1, testNode5, "testEdge4");
        Edge testEdge5 = new Edge(testNode1, testNode6, "testEdge5");
        Edge testEdge6 = new Edge(testNode1, testNode7, "testEdge6");

        Vector<Edge> edges = new Vector<Edge>();
        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge2, testEdge3, testEdge4, testEdge5, testEdge6 });
        assertFalse(SolverA.apply(testNode1, edges));
    }

    // solverAPuzzleTest
    @Test
    void isGraphSolvedOfSolverAPuzzleTestShouldReturnTrue() throws IOException, ParseException, FileNotFoundException {
        File puzzleJSON = new File("src/test/resources/solverAPuzzleTest.json");
        String puzzleName = "weightMatrix";
        int[][] weightMatrix = AbstractPuzzleLoader.loadPuzzle(puzzleJSON, puzzleName);
        Graph graph = new Graph(weightMatrix);
        graph.solveGraph();
        assertTrue(graph.isGraphSolved());
    }

    @Test
    void getNBridgesOfAllEdgesOfSolverAPuzzleTestShouldBeEqualToNBridgesOfAllEdges()
            throws IOException, ParseException, FileNotFoundException {
        File puzzleJSON = new File("src/test/resources/solverAPuzzleTest.json");
        int[][] weightMatrix = AbstractPuzzleLoader.loadPuzzle(puzzleJSON, "weightMatrix");
        int[] nBridgesOfAllEdges = AbstractPuzzleLoader.loadPuzzle(puzzleJSON, "nBridgesOfAllEdges")[0];
        Graph graph = new Graph(weightMatrix);
        graph.solveGraph();
        assertArrayEquals(nBridgesOfAllEdges, graph.getNBridgesOfAllEdges());
    }
}
