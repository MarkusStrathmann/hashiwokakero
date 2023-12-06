import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Vector;

public class EdgeTest {
    // Edge(Node firstNode, Node secondNode, String name)
    @Test
    void nameShouldBeNull() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Edge testEdge = new Edge(testNode1, testNode2, null);
        assertNull(testEdge.getName());
    }

    @Test
    void nameShouldBeViola() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Edge testEdge = new Edge(testNode1, testNode2, "Viola");
        assertEquals("Viola", testEdge.getName());
    }

    @Test
    void firstConnectedNodeShouldBeNull() {
        Node testNode = new Node();
        Edge testEdge = new Edge(null, testNode, "testEdge");
        assertNull(testEdge.getConnectedNodes()[0]);
    }

    @Test
    void firstConnectedNodeShouldBeTestNode() {
        Node testNode = new Node();
        Edge testEdge = new Edge(testNode, null, "testEdge");
        assertEquals(testNode, testEdge.getConnectedNodes()[0]);
    }

    @Test
    void secondConnectedNodeShouldBeNull() {
        Node testNode = new Node();
        Edge testEdge = new Edge(testNode, null, "testEdge");
        assertNull(testEdge.getConnectedNodes()[1]);
    }

    @Test
    void secondConnectedNodeShouldBeTestNode() {
        Node testNode = new Node();
        Edge testEdge = new Edge(null, testNode, "testEdge");
        assertEquals(testNode, testEdge.getConnectedNodes()[1]);
    }

    // getName() & setName()
    @Test
    void getNameShouldBeGerald() {
        Edge testEdge = new Edge(null, null, null);
        testEdge.setName("Gerald");
        assertEquals("Gerald", testEdge.getName());
    }

    @Test
    void getNameShouldBeEmptyString() {
        Edge testEdge = new Edge(null, null, null);
        testEdge.setName("");
        assertEquals("", testEdge.getName());
    }

    @Test
    void getNameShouldBeNull() {
        Edge testEdge = new Edge(null, null, null);
        assertNull(testEdge.getName());
    }

    // getConnectedNodes() & setConnectedNodes()
    @Test
    void getConnectedNodesOfNewEdgeShouldBeArrayOfNulls() {
        Edge testEdge = new Edge(null, null, null);
        assertArrayEquals(testEdge.getConnectedNodes(), new Node[] { null, null });
    }

    @Test
    void getConnectedNodesShouldBeArrayOfNulls() {
        Edge testEdge = new Edge(null, null, null);
        testEdge.setConnectedNodes(new Node[] { null, null });
        assertArrayEquals(testEdge.getConnectedNodes(), new Node[] { null, null });
    }

    @Test
    void getConnectedNodesShouldBeArrayOfTestNode1AndTestNode2() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Edge testEdge = new Edge(testNode1, testNode2, "testEdge1");
        testEdge.setConnectedNodes(new Node[] { testNode1, testNode2 });
        assertArrayEquals(new Node[] { testNode1, testNode2 }, testEdge.getConnectedNodes());
    }

    @Test
    void setConnectedNodesWithFewerThanTwoNodesShouldReturnIllegalArgumentException() {
        Edge testEdge = new Edge(null, null, null);
        assertThrows(IllegalArgumentException.class,
                () -> testEdge.setConnectedNodes(new Node[] { null }));
    }

    @Test
    void setConnectedNodesWithMoreThanTwoNodesShouldReturnIllegalArgumentException() {
        Edge testEdge = new Edge(null, null, null);
        assertThrows(IllegalArgumentException.class,
                () -> testEdge.setConnectedNodes(new Node[] { null, null, null }));
    }

    // getNodesInPath() & setNodesInPath()
    @Test
    void getNodesInPathForNewEdgeShouldReturnEmptyVector() {
        Edge testEdge = new Edge(null, null, null);
        assertEquals(0, testEdge.getNodesInPath().size());
    }

    @Test
    void getNodesInPathShouldReturnVectorWithTestNode() {
        Edge testEdge = new Edge(null, null, null);
        Node testNode = new Node();
        Vector<Node> targetVector = new Vector<Node>();
        targetVector.add(testNode);
        testEdge.setŃodesInPath(targetVector);
        assertEquals(targetVector, testEdge.getNodesInPath());
    }

    @Test
    void setNodesInPathWithEmptyVectorShouldReturnIllegalArgumentException() {
        Edge testEdge = new Edge(null, null, null);
        Vector<Node> targetVector = new Vector<Node>();
        assertThrows(IllegalArgumentException.class, () -> testEdge.setŃodesInPath(targetVector));
    }

    // getNPossibleBridges() & setNPossibleBridges()
    @Test
    void getNPossibleBridgesShouldReturnTwo() {
        Node testNode1 = new Node();
        testNode1.setTargetWeight(2);
        Node testNode2 = new Node();
        testNode2.setTargetWeight(2);
        Edge testEdge = new Edge(testNode1, testNode2, null);
        assertEquals(2, testEdge.getNPossibleBridges());
    }

    @Test
    void getNPossibleBridgesForWeightDifferenceOfFirstNodeIsOneShouldReturnOne() {
        Node testNode1 = new Node();
        testNode1.setTargetWeight(1);
        Node testNode2 = new Node();
        testNode2.setTargetWeight(2);
        Edge testEdge = new Edge(testNode1, testNode2, null);
        assertEquals(1, testEdge.getNPossibleBridges());
    }

    @Test
    void getNPossibleBridgesForWeightDifferenceOfSecondNodeIsOneShouldReturnOne() {
        Node testNode1 = new Node();
        testNode1.setTargetWeight(2);
        Node testNode2 = new Node();
        testNode2.setTargetWeight(1);
        Edge testEdge = new Edge(testNode1, testNode2, null);
        assertEquals(1, testEdge.getNPossibleBridges());
    }

    @Test
    void getNPossibleBridgesForWeightDifferenceOfFirstNodeIsZeroShouldReturnZero() {
        Node testNode1 = new Node();
        testNode1.setTargetWeight(0);
        Node testNode2 = new Node();
        testNode2.setTargetWeight(1);
        Edge testEdge = new Edge(testNode1, testNode2, null);
        assertEquals(0, testEdge.getNPossibleBridges());
    }

    @Test
    void getNPossibleBridgesForWeightDifferenceOfSecondNodeIsZeroShouldReturnZero() {
        Node testNode1 = new Node();
        testNode1.setTargetWeight(1);
        Node testNode2 = new Node();
        testNode2.setTargetWeight(0);
        Edge testEdge = new Edge(testNode1, testNode2, null);
        assertEquals(0, testEdge.getNPossibleBridges());
    }

    // incrementNBridges()
    @Test
    void getNBrigesShouldReturnOneAfterIncrementBridges() {
        Node testNode1 = new Node();
        testNode1.setTargetWeight(2);
        Node testNode2 = new Node();
        testNode2.setTargetWeight(2);
        Edge testEdge = new Edge(testNode1, testNode2, "testEdge");
        testEdge.incrementNBridges();
        assertEquals(1, testEdge.getnBridges());
    }

    @Test
    void getNBrigesShouldReturnTwoAfterIncrementBridgesTwice() {
        Node testNode1 = new Node();
        testNode1.setTargetWeight(2);
        Node testNode2 = new Node();
        testNode2.setTargetWeight(2);
        Edge testEdge = new Edge(testNode1, testNode2, "testEdge");
        testEdge.incrementNBridges();
        testEdge.incrementNBridges();
        assertEquals(2, testEdge.getnBridges());
    }

    @Test
    void incrementBrigesAboveNPossibleBridgesShouldReturnIllegalStateException() {
        Node testNode1 = new Node();
        testNode1.setTargetWeight(10);
        Node testNode2 = new Node();
        testNode2.setTargetWeight(10);
        Edge testEdge = new Edge(testNode1, testNode2, "testEdge");
        testEdge.incrementNBridges();
        testEdge.incrementNBridges();
        assertThrows(IllegalStateException.class, () -> testEdge.incrementNBridges());
    }

    // blockCrossingEdges()
    @Test
    void getNPossibleBridgesShouldReturnZeroAfterBlockCrossingEdges() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Node testNode4 = new Node();
        Node testNode5 = new Node();
        Node testNode6 = new Node();
        testNode1.setTargetWeight(10);
        testNode2.setTargetWeight(10);
        testNode3.setTargetWeight(10);
        testNode4.setTargetWeight(10);
        testNode5.setTargetWeight(10);
        testNode6.setTargetWeight(10);
        Vector<Node> nodesInPath1 = new Vector<Node>();
        Vector<Node> nodesInPath2 = new Vector<Node>();
        nodesInPath1.add(testNode3);
        nodesInPath2.add(testNode6);
        Edge testEdge1 = new Edge(testNode1, testNode5, "testEdge1");
        Edge testEdge2 = new Edge(testNode2, testNode4, "testEdge2");
        Edge testEdge3 = new Edge(testNode3, testNode4, "testEdge3");
        testEdge1.setŃodesInPath(nodesInPath1);
        testEdge2.setŃodesInPath(nodesInPath1);
        testEdge3.setŃodesInPath(nodesInPath2);
        Vector<Edge> edges = new Vector<Edge>();
        edges.add(testEdge1);
        edges.add(testEdge2);
        edges.add(testEdge3);
        testEdge1.blockCrossingEdges(edges);
        assertEquals(0, testEdge2.getNPossibleBridges());
    }

    @Test
    void getNPossibleBridgesShouldReturnTwoAfterBlockCrossingEdges() {
        Node testNode1 = new Node();
        testNode1.setTargetWeight(10);
        Node testNode2 = new Node();
        testNode2.setTargetWeight(10);
        Node testNode3 = new Node();
        testNode3.setTargetWeight(10);
        Node testNode4 = new Node();
        testNode4.setTargetWeight(10);
        Edge testEdge1 = new Edge(testNode1, testNode4, "testEdge1");
        Edge testEdge2 = new Edge(testNode2, testNode3, "testEdge2");
        Vector<Edge> edges = new Vector<Edge>();
        edges.add(testEdge1);
        edges.add(testEdge2);
        testEdge1.blockCrossingEdges(edges);
        assertEquals(2, testEdge2.getNPossibleBridges());
    }

}