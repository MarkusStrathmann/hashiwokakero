package src.main.java;

/**
 * main
 */
public class Main {
        public static void main(String[] args) {
                int [] [] weightMatrix = {
                        {0,0,1,7,5,3,3,6,5,2},
                        {0,2,4,4,0,0,0,5,0,0},
                        {0,0,2,3,0,0,1,0,0,0},
                        {0,2,7,3,4,4,0,0,0,0},
                        {0,0,7,7,0,1,0,0,0,0},
                        {5,5,3,0,0,0,0,0,0,0},
                        {2,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,1,0,0,0,0}
                };
                Graph graph = new Graph(weightMatrix);
                graph.solveGraph();
                graph.plotGraph();

                // graph.showEdgeInfo(); // show edge info
                // graph.showNodeInfo(); // show node info
        }
}