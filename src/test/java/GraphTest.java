import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GraphTest {
    // Graph(int[][] weightMatrix)

    // examplePuzzle
    @Test
    void isGraphSolvedOfExamplePuzzleShouldReturnTrue() throws IOException, ParseException, FileNotFoundException  {
        File puzzleJSON = new File("src/test/resources/examplePuzzleTest.json");
        String puzzleName = "weightMatrix";
        int[][] weightMatrix = AbstractPuzzleLoader.loadPuzzle(puzzleJSON, puzzleName);
        Graph graph = new Graph(weightMatrix);
        graph.solveGraph();
        assertTrue(graph.isGraphSolved());
    }

    @Test
    void getNBridgesOfAllEdgesOfExamplePuzzleShouldBeEqualToNBridgesOfAllEdges() throws IOException, ParseException, FileNotFoundException {
        File puzzleJSON = new File("src/test/resources/examplePuzzleTest.json");
        int[][] weightMatrix = AbstractPuzzleLoader.loadPuzzle(puzzleJSON, "weightMatrix");
        int [] nBridgesOfAllEdges = AbstractPuzzleLoader.loadPuzzle(puzzleJSON, "nBridgesOfAllEdges")[0];
        Graph graph = new Graph(weightMatrix);
        graph.solveGraph();
        assertArrayEquals(nBridgesOfAllEdges, graph.getNBridgesOfAllEdges());
    }
}
