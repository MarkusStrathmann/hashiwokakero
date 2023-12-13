
/**
 * main
 */

import java.io.File;

public class Main {
        public static void main(String[] args) {

                File puzzleJSON = new File("src/main/resources/examplePuzzle.json"); // path to json file with puzzle
                String puzzleName = "examplePuzzle"; // keyword for weightMatrix of puzzle in json file

                int[][] weightMatrix = PuzzleLoader.loadPuzzle(puzzleJSON, puzzleName);
                Graph graph = new Graph(weightMatrix);
                graph.solveGraph();
                graph.plotGraph();

                // graph.showEdgeInfo(); // show edge info
                // graph.showNodeInfo(); // show node info
        }
}