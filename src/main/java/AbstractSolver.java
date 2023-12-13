
/**
 * Solver
 */
public abstract class AbstractSolver {
    public static boolean buildedBridge = false;
    public static String buildInfo = "";

    public static String buildInfo(Node node, int direction) {
        String builInfo = " -> builded bridge on edge " + node.getConnectedEdge(direction).getName() + " ("
                + node.getConnectedEdge(direction).getConnectedNodes()[0].getName() + " to "
                + node.getConnectedEdge(direction).getConnectedNodes()[1].getName() + ")";
        return builInfo;
    }

    public static int invertDir(int direction) {
        int invertedDir;
        if (direction >= 0 && direction < 3) {
            invertedDir = direction + 3;
        } else if (direction >= 3 && direction <= 5) {
            invertedDir = direction - 3;
        } else {
            throw new IllegalArgumentException("Argument must be between 0 and 5 but is " + direction + "!");
        }
        return invertedDir;
    }
}
