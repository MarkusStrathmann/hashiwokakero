
/**
 * main
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

public class Main {
        public static void main(String[] args) throws IOException, ParseException, FileNotFoundException  {

                File puzzleJSON = new File("src/main/resources/examplePuzzle.json"); // path to json file with puzzle
                String keyword = "weightMatrix"; // keyword for weight matrix in json file

                int[][] weightMatrix = AbstractPuzzleLoader.loadPuzzle(puzzleJSON, keyword);
                Graph graph = new Graph(weightMatrix);
                graph.solveGraph();
                graph.plotGraph();

                // graph.showEdgeInfo(); // show edge info
                // graph.showNodeInfo(); // show node info
        }
}