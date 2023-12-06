import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    // Node()
    @Test
    void nameShouldBeNull() {
        Node testNode = new Node();
        assertNull(testNode.getName());
    }

    @Test
    void actualWeightShouldBeZero() {
        Node testNode = new Node();
        assertEquals(0, testNode.getActualWeight());
    }

    @Test
    void targetWeightShouldBeZero() {
        Node testNode = new Node();
        assertEquals(0, testNode.getTargetWeight());
    }

    @Test
    void connectedEdgesShouldBeTypeEdgeArray() {
        Node testNode = new Node();
        assertEquals(Edge[].class, testNode.getConnectedEdges().getClass());
    }

    @Test
    void lengthOfConnectedEdgesShouldBeSix() {
        Node testNode = new Node();
        assertEquals(6, testNode.getConnectedEdges().length);
    }

    @Test
    void neighborNodesShouldBeTypeNodeArray() {
        Node testNode = new Node();
        assertEquals(Node[].class, testNode.getNeighborNodes().getClass());
    }

    @Test
    void lengthOfNeighborNodesShouldBeSix() {
        Node testNode = new Node();
        assertEquals(6, testNode.getNeighborNodes().length);
    }

    // Node(Node node)
    @Test
    void targetWeightShouldBeEqual() {
        Node testNode1 = new Node();
        testNode1.setTargetWeight(6);
        Node testNode2 = new Node(testNode1);
        assertEquals(testNode1.getTargetWeight(), testNode2.getTargetWeight());
    }

    @Test
    void connectedEdgesShouldBeEqual() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Node testNode4 = new Node();
        Edge testEdge1 = new Edge(testNode2, testNode3, "testEdge1");
        Edge testEdge2 = new Edge(testNode3, testNode4, "testEdge2");
        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge2, null, null, null, null });
        Node testNode5 = new Node(testNode1);
        assertArrayEquals(testNode1.getConnectedEdges(), testNode5.getConnectedEdges());
    }

    @Test
    void neighborNodesShouldBeEqual() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Node testNode4 = new Node();
        testNode1.setNeighborNodes(new Node[] { testNode2, testNode3, testNode4, null, null, null });
        Node testNode5 = new Node(testNode1);
        assertArrayEquals(testNode1.getNeighborNodes(), testNode5.getNeighborNodes());
    }

    // getName() & setName()
    @Test
    void getNameShouldBeGerald() {
        Node testNode = new Node();
        testNode.setName("Gerald");
        assertEquals("Gerald", testNode.getName());
    }

    @Test
    void getNameShouldBeEmptyString() {
        Node testNode = new Node();
        testNode.setName("");
        assertEquals("", testNode.getName());
    }

    @Test
    void getNameShouldBeNull() {
        Node testNode = new Node();
        assertNull(testNode.getName());
    }

    // getActualWeight() & setActualWeight()
    @Test
    void getActualWeightShouldBeFive() {
        Node testNode = new Node();
        testNode.setTargetWeight(6);
        testNode.setActualWeight(5);
        assertEquals(5, testNode.getActualWeight());
    }

    @Test
    void getActualWeightShouldBeZero() {
        Node testNode = new Node();
        testNode.setTargetWeight(6);
        testNode.setActualWeight(0);
        assertEquals(0, testNode.getActualWeight());
    }

    @Test
    void getActualWeightOfNewNodeShouldBeZero() {
        Node testNode = new Node();
        assertEquals(0, testNode.getActualWeight());
    }

    @Test
    void setActualWeightHigherThanTargetWeightShouldReturnIllegalStateException() {
        Node testNode = new Node();
        assertThrows(IllegalStateException.class, () -> testNode.setActualWeight(5));
    }

    @Test
    void setActualWeightBelowZeroShouldReturnIllegalArgumentException() {
        Node testNode = new Node();
        assertThrows(IllegalArgumentException.class, () -> testNode.setActualWeight(-1));
    }

    // getTargetWeight() & setTargetWeight()
    @Test
    void getTargetWeightShouldBeSix() {
        Node testNode = new Node();
        testNode.setTargetWeight(6);
        assertEquals(6, testNode.getTargetWeight());
    }

    @Test
    void getTargetWeightShouldBeZero() {
        Node testNode = new Node();
        testNode.setTargetWeight(0);
        assertEquals(0, testNode.getTargetWeight());
    }

    @Test
    void getTargetWeightOfNewNodeShouldBeZero() {
        Node testNode = new Node();
        assertEquals(0, testNode.getTargetWeight());
    }

    @Test
    void setTargetWeightLowerThanActualWeightShouldReturnIllegalStateException() {
        Node testNode = new Node();
        testNode.setTargetWeight(6);
        testNode.setActualWeight(5);
        assertThrows(IllegalStateException.class, () -> testNode.setTargetWeight(4));
    }

    @Test
    void setTargetWeightBelowZeroShouldReturnIllegalArgumentException() {
        Node testNode = new Node();
        testNode.setTargetWeight(6);
        testNode.setActualWeight(5);
        assertThrows(IllegalArgumentException.class, () -> testNode.setTargetWeight(-1));
    }

    // getConnectedEdges() & setConnectedEdges()
    @Test
    void getConnectedEdgesOfNewNodeShouldBeArrayOfNulls() {
        Node testNode = new Node();
        assertArrayEquals(testNode.getConnectedEdges(), new Edge[] { null, null, null, null, null, null });
    }

    @Test
    void getConnectedEdgesShouldBeArrayOfNulls() {
        Node testNode = new Node();
        testNode.setConnectedEdges(new Edge[] { null, null, null, null, null, null });
        assertArrayEquals(testNode.getConnectedEdges(), new Edge[] { null, null, null, null, null, null });
    }

    @Test
    void getConnectedEdgesShouldBeArrayOfTestEdge1AndTestEdge2() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Edge testEdge1 = new Edge(testNode2, testNode3, "testEdge1");
        Edge testEdge2 = new Edge(testNode3, testNode2, "testEdge2");
        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge2, testEdge1, testEdge2, testEdge1, testEdge2 });
        assertArrayEquals(
                new Edge[] { testEdge1, testEdge2, testEdge1, testEdge2, testEdge1, testEdge2 },
                testNode1.getConnectedEdges());
    }

    @Test
    void setConnectedEdgesWithFewerThanSixEdgesShouldReturnIllegalArgumentException() {
        Node testNode = new Node();
        assertThrows(IllegalArgumentException.class,
                () -> testNode.setConnectedEdges(new Edge[] { null, null, null, null, null }));
    }

    @Test
    void setConnectedEdgesWithMoreThanSixEdgesShouldReturnIllegalArgumentException() {
        Node testNode = new Node();
        assertThrows(IllegalArgumentException.class,
                () -> testNode.setConnectedEdges(new Edge[] { null, null, null, null, null, null, null }));
    }

    // setConnectedEdge() & getConnectedEdge()
    @Test
    void getConnectedEdgeWithDirectionFiveShouldReturnTestEdge2() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Edge testEdge1 = new Edge(testNode2, testNode3, "testEdge1");
        Edge testEdge2 = new Edge(testNode3, testNode2, "testEdge2");
        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge1, testEdge1, testEdge1, testEdge1, testEdge2 });
        assertEquals(testEdge2, testNode1.getConnectedEdge(5));
    }

    @Test
    void getConnectedEdgeWithDirectionZeroShouldReturnTestEdge1() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Edge testEdge1 = new Edge(testNode2, testNode3, "testEdge1");
        Edge testEdge2 = new Edge(testNode3, testNode2, "testEdge2");
        testNode1.setConnectedEdges(new Edge[] { testEdge2, testEdge1, testEdge1, testEdge1, testEdge1, testEdge1 });
        assertEquals(testEdge2, testNode1.getConnectedEdge(0));
    }

    @Test
    void getConnectedEdgeWithDirectionBelowZeroShouldReturnIllegalArgumentException() {
        Node testNode = new Node();
        assertThrows(IllegalArgumentException.class, () -> testNode.getConnectedEdge(-1));
    }

    @Test
    void getConnectedEdgeWithDirectionAboveFiveShouldReturnIllegalArgumentException() {
        Node testNode = new Node();
        assertThrows(IllegalArgumentException.class, () -> testNode.getConnectedEdge(6));
    }

    @Test
    void setConnectedEdgeWithDirectionBelowZeroShouldReturnIllegalArgumentException() {
        Node testNode = new Node();
        Edge testEdge = new Edge(testNode, testNode, "testEdge");
        assertThrows(IllegalArgumentException.class, () -> testNode.setConnectedEdge(-1, testEdge));
    }

    @Test
    void setConnectedEdgeWithDirectionAboveFiveShouldReturnIllegalArgumentException() {
        Node testNode = new Node();
        Edge testEdge = new Edge(testNode, testNode, "testEdge");
        assertThrows(IllegalArgumentException.class, () -> testNode.setConnectedEdge(6, testEdge));
    }

    @Test
    void getConnectedEdgeWithSetConnectedEdgeWithDirectionFourAndNullShouldReturnNull() {
        Node testNode = new Node();
        testNode.setConnectedEdge(4, null);
        assertNull(testNode.getConnectedEdge(4));
    }

    // getWeightDifference()
    @Test
    void getWeightDifferenceShouldReturnZero() {
        Node testNode = new Node();
        testNode.setTargetWeight(6);
        testNode.setActualWeight(6);
        assertEquals(0, testNode.getWeightDifference());
    }

    @Test
    void getWeightDifferenceShouldReturnTwo() {
        Node testNode = new Node();
        testNode.setTargetWeight(8);
        testNode.setActualWeight(6);
        assertEquals(2, testNode.getWeightDifference());
    }

    @Test
    void getWeightDifferenceOfNewNodeShouldReturnZero() {
        Node testNode = new Node();
        assertEquals(0, testNode.getWeightDifference());
    }

    // getNFreeEdges()
    @Test
    void getNFreeEdgesShouldReturnZero() {
        Node testNode1 = new Node();
        testNode1.setTargetWeight(12);
        Node testNode2 = new Node();
        testNode2.setTargetWeight(12);
        Node testNode3 = new Node();
        testNode3.setTargetWeight(12);
        Edge testEdge1 = new Edge(testNode2, testNode3, "testEdge1");
        Edge testEdge2 = new Edge(testNode2, testNode3, "testEdge2");
        Edge testEdge3 = new Edge(testNode2, testNode3, "testEdge3");
        Edge testEdge4 = new Edge(testNode2, testNode3, "testEdge4");
        Edge testEdge5 = new Edge(testNode2, testNode3, "testEdge5");
        Edge testEdge6 = new Edge(testNode2, testNode3, "testEdge6");
        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge2, testEdge3, testEdge4, testEdge5, testEdge6 });
        testNode1.getConnectedEdge(0).incrementNBridges();
        testNode1.getConnectedEdge(0).incrementNBridges();
        testNode1.getConnectedEdge(1).incrementNBridges();
        testNode1.getConnectedEdge(1).incrementNBridges();
        testNode1.getConnectedEdge(2).incrementNBridges();
        testNode1.getConnectedEdge(2).incrementNBridges();
        testNode1.getConnectedEdge(3).incrementNBridges();
        testNode1.getConnectedEdge(3).incrementNBridges();
        testNode1.getConnectedEdge(4).incrementNBridges();
        testNode1.getConnectedEdge(4).incrementNBridges();
        testNode1.getConnectedEdge(5).incrementNBridges();
        testNode1.getConnectedEdge(5).incrementNBridges();
        assertEquals(0, testNode1.getNFreeEdges());

    }

    @Test
    void getNFreeEdgesShouldReturnThree() {
        Node testNode1 = new Node();
        testNode1.setTargetWeight(12);
        Node testNode2 = new Node();
        testNode2.setTargetWeight(12);
        Node testNode3 = new Node();
        testNode3.setTargetWeight(12);
        Edge testEdge1 = new Edge(testNode2, testNode3, "testEdge1");
        Edge testEdge2 = new Edge(testNode2, testNode3, "testEdge2");
        Edge testEdge3 = new Edge(testNode2, testNode3, "testEdge3");
        Edge testEdge4 = new Edge(testNode2, testNode3, "testEdge4");
        Edge testEdge5 = new Edge(testNode2, testNode3, "testEdge5");
        Edge testEdge6 = new Edge(testNode2, testNode3, "testEdge6");
        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge2, testEdge3, testEdge4, testEdge5, testEdge6 });
        testNode1.getConnectedEdge(0).incrementNBridges();
        testNode1.getConnectedEdge(1).incrementNBridges();
        testNode1.getConnectedEdge(1).incrementNBridges();
        testNode1.getConnectedEdge(2).incrementNBridges();
        testNode1.getConnectedEdge(3).incrementNBridges();
        testNode1.getConnectedEdge(3).incrementNBridges();
        testNode1.getConnectedEdge(4).incrementNBridges();
        testNode1.getConnectedEdge(5).incrementNBridges();
        testNode1.getConnectedEdge(5).incrementNBridges();
        assertEquals(3, testNode1.getNFreeEdges());

    }

    @Test
    void getNFreeEdgesShouldReturnSix() {
        Node testNode1 = new Node();
        testNode1.setTargetWeight(12);
        Node testNode2 = new Node();
        testNode2.setTargetWeight(12);
        Node testNode3 = new Node();
        testNode3.setTargetWeight(12);
        Edge testEdge1 = new Edge(testNode2, testNode3, "testEdge1");
        Edge testEdge2 = new Edge(testNode2, testNode3, "testEdge2");
        Edge testEdge3 = new Edge(testNode2, testNode3, "testEdge3");
        Edge testEdge4 = new Edge(testNode2, testNode3, "testEdge4");
        Edge testEdge5 = new Edge(testNode2, testNode3, "testEdge5");
        Edge testEdge6 = new Edge(testNode2, testNode3, "testEdge6");
        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge2, testEdge3, testEdge4, testEdge5, testEdge6 });
        testNode1.getConnectedEdge(0).incrementNBridges();
        testNode1.getConnectedEdge(1).incrementNBridges();
        testNode1.getConnectedEdge(2).incrementNBridges();
        testNode1.getConnectedEdge(3).incrementNBridges();
        testNode1.getConnectedEdge(4).incrementNBridges();
        testNode1.getConnectedEdge(5).incrementNBridges();
        assertEquals(6, testNode1.getNFreeEdges());
    }

    @Test
    void getNFreeEdgesShouldReturnFive() {
        Node testNode1 = new Node();
        testNode1.setTargetWeight(12);
        Node testNode2 = new Node();
        testNode2.setTargetWeight(12);
        Node testNode3 = new Node();
        testNode3.setTargetWeight(12);
        Edge testEdge1 = new Edge(testNode2, testNode3, "testEdge1");
        Edge testEdge2 = new Edge(testNode2, testNode3, "testEdge2");
        Edge testEdge3 = new Edge(testNode2, testNode3, "testEdge3");
        Edge testEdge4 = new Edge(testNode2, testNode3, "testEdge4");
        Edge testEdge5 = new Edge(testNode2, testNode3, "testEdge5");
        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge2, testEdge3, testEdge4, testEdge5, null });
        testNode1.getConnectedEdge(0).incrementNBridges();
        testNode1.getConnectedEdge(1).incrementNBridges();
        testNode1.getConnectedEdge(2).incrementNBridges();
        testNode1.getConnectedEdge(3).incrementNBridges();
        testNode1.getConnectedEdge(4).incrementNBridges();
        assertEquals(5, testNode1.getNFreeEdges());
    }

    // setNeighborNodes() & getNeighborNodes()
    @Test
    void getNeighborNodesOfNewNodeShouldBeArrayOfNulls() {
        Node testNode = new Node();
        assertArrayEquals(testNode.getNeighborNodes(), new Node[] { null, null, null, null, null, null });
    }

    @Test
    void getNeighborNodesShouldBeArrayOfNulls() {
        Node testNode = new Node();
        testNode.setNeighborNodes(new Node[] { null, null, null, null, null, null });
        assertArrayEquals(testNode.getNeighborNodes(), new Node[] { null, null, null, null, null, null });
    }

    @Test
    void getNeighborNodesShouldBeArrayOfTestNode2AndTestNode3() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        testNode1.setNeighborNodes(new Node[] { testNode2, testNode3, testNode2, testNode3, testNode2, testNode3 });
        assertArrayEquals(testNode1.getNeighborNodes(),
                new Node[] { testNode2, testNode3, testNode2, testNode3, testNode2, testNode3 });
    }

    @Test
    void setNeighborNodesWithFewerThanSixNodesShouldReturnIllegalArgumentException() {
        Node testNode = new Node();
        assertThrows(IllegalArgumentException.class,
                () -> testNode.setNeighborNodes(new Node[] { null, null, null, null, null }));
    }

    @Test
    void setNeighborNodesWithMoreThanSixNodesShouldReturnIllegalArgumentException() {
        Node testNode = new Node();
        assertThrows(IllegalArgumentException.class,
                () -> testNode.setNeighborNodes(new Node[] { null, null, null, null, null, null, null }));
    }

    // setNeighborNode() & getNeighborNode()
    @Test
    void getNeighborNodeWithDirectionFiveShouldReturnTestNode3() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        testNode1.setNeighborNodes(new Node[] { testNode2, testNode2, testNode2, testNode2, testNode2, testNode3 });
        assertEquals(testNode3, testNode1.getNeighborNode(5));
    }

    @Test
    void getNeighborNodeWithDirectionZeroShouldReturnTestNode3() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        testNode1.setNeighborNodes(new Node[] { testNode3, testNode2, testNode2, testNode2, testNode2, testNode2 });
        assertEquals(testNode3, testNode1.getNeighborNode(0));
    }

    @Test
    void getNeighborNodeWithDirectionBelowZeroShouldReturnIllegalArgumentException() {
        Node testNode = new Node();
        assertThrows(IllegalArgumentException.class, () -> testNode.getNeighborNode(-1));
    }

    @Test
    void getNeighborNodeWithDirectionAboveFiveShouldReturnIllegalArgumentException() {
        Node testNode = new Node();
        assertThrows(IllegalArgumentException.class, () -> testNode.getNeighborNode(6));
    }

    @Test
    void setNeighborNodeWithDirectionBelowZeroShouldReturnIllegalArgumentException() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        assertThrows(IllegalArgumentException.class, () -> testNode1.setNeighborNode(-1, testNode2));
    }

    @Test
    void setNeighborNodeWithDirectionAboveFiveShouldReturnIllegalArgumentException() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        assertThrows(IllegalArgumentException.class, () -> testNode1.setNeighborNode(6, testNode2));
    }

    @Test
    void getNeighborNodeWithsetNeighborNodeWithDirectionFourAndNullShouldReturnNull() {
        Node testNode = new Node();
        testNode.setNeighborNode(4, null);
        assertNull(testNode.getNeighborNode(4));
    }

    // incrementActualWeight() & decrementActualWeight()
    @Test
    void getActualWeightAfterIncrementActualWeightShouldReturnFive() {
        Node testNode = new Node();
        testNode.setTargetWeight(12);
        testNode.setActualWeight(4);
        testNode.incrementActualWeight();
        assertEquals(5, testNode.getActualWeight());
    }

    @Test
    void getActualWeightAfterIncrementActualWeightShouldReturnOne() {
        Node testNode = new Node();
        testNode.setTargetWeight(12);
        testNode.incrementActualWeight();
        assertEquals(1, testNode.getActualWeight());
    }

    @Test
    void getActualWeightAfterIncrementActualWeightShouldReturnTwelve() {
        Node testNode = new Node();
        testNode.setTargetWeight(12);
        testNode.setActualWeight(11);
        testNode.incrementActualWeight();
        assertEquals(12, testNode.getActualWeight());
    }

    @Test
    void incrementActualWeightAboveTargetWeightShouldReturnIllegalStateException() {
        Node testNode = new Node();
        testNode.setTargetWeight(7);
        testNode.setActualWeight(7);
        assertThrows(IllegalStateException.class, () -> testNode.incrementActualWeight());
    }

    @Test
    void getActualWeightAfterDecrementActualWeightShouldReturnFive() {
        Node testNode = new Node();
        testNode.setTargetWeight(12);
        testNode.setActualWeight(6);
        testNode.decrementActualWeight();
        assertEquals(5, testNode.getActualWeight());
    }

    @Test
    void getActualWeightAfterDecrementActualWeightShouldReturnZero() {
        Node testNode = new Node();
        testNode.setTargetWeight(12);
        testNode.setActualWeight(1);
        testNode.decrementActualWeight();
        assertEquals(0, testNode.getActualWeight());
    }

    @Test
    void getActualWeightAfterDecrementActualWeightShouldReturnEleven() {
        Node testNode = new Node();
        testNode.setTargetWeight(12);
        testNode.setActualWeight(12);
        testNode.decrementActualWeight();
        assertEquals(11, testNode.getActualWeight());
    }

    @Test
    void decrementActualWeightBelowZeroShouldReturnIllegalStateException() {
        Node testNode = new Node();
        testNode.setTargetWeight(7);
        testNode.setActualWeight(0);
        assertThrows(IllegalStateException.class, () -> testNode.decrementActualWeight());
    }

    // getNConnectedEdges()
    @Test
    void getNConnectedEdgesShouldReturnZeroForNewNode() {
        Node testNode = new Node();
        assertEquals(0, testNode.getNConnectedEdges());
    }

    @Test
    void getNConnectedEdgesShouldReturnZero() {
        Node testNode = new Node();
        testNode.setConnectedEdges(new Edge[] { null, null, null, null, null, null });
        assertEquals(0, testNode.getNConnectedEdges());
    }

    @Test
    void getNConnectedEdgesShouldReturnFive() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Edge testEdge1 = new Edge(testNode2, testNode3, "testEdge1");
        Edge testEdge2 = new Edge(testNode3, testNode2, "testEdge2");
        Edge testEdge3 = new Edge(testNode2, testNode3, "testEdge3");
        Edge testEdge4 = new Edge(testNode3, testNode2, "testEdge4");
        Edge testEdge5 = new Edge(testNode2, testNode3, "testEdge5");

        testNode1.setConnectedEdges(new Edge[] { null, testEdge1, testEdge2, testEdge3, testEdge4, testEdge5 });
        assertEquals(5, testNode1.getNConnectedEdges());
    }

    @Test
    void getNConnectedEdgesShouldReturnSix() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Edge testEdge1 = new Edge(testNode2, testNode3, "testEdge1");
        Edge testEdge2 = new Edge(testNode3, testNode2, "testEdge2");
        Edge testEdge3 = new Edge(testNode2, testNode3, "testEdge3");
        Edge testEdge4 = new Edge(testNode3, testNode2, "testEdge4");
        Edge testEdge5 = new Edge(testNode2, testNode3, "testEdge5");
        Edge testEdge6 = new Edge(testNode3, testNode2, "testEdge6");

        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge2, testEdge3, testEdge4, testEdge5, testEdge6 });
        assertEquals(6, testNode1.getNConnectedEdges());
    }

    // getNPossibleBridges()
    @Test
    void getNPossibleBridgesShouldReturnZeroForNewNode() {
        Node testNode = new Node();
        assertEquals(0, testNode.getNPossibleBridges());
    }

    @Test
    void getNPossibleBridgesShouldReturnZero() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Edge testEdge1 = new Edge(testNode2, testNode3, "testEdge1");
        Edge testEdge2 = new Edge(testNode3, testNode2, "testEdge2");
        Edge testEdge3 = new Edge(testNode2, testNode3, "testEdge3");
        Edge testEdge4 = new Edge(testNode3, testNode2, "testEdge4");
        Edge testEdge5 = new Edge(testNode2, testNode3, "testEdge5");
        Edge testEdge6 = new Edge(testNode3, testNode2, "testEdge6");
        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge2, testEdge3, testEdge4, testEdge5, testEdge6 });
        testNode1.setTargetWeight(12);
        testNode2.setTargetWeight(12);
        testNode3.setTargetWeight(12);
        testEdge1.incrementNBridges();
        testEdge1.incrementNBridges();
        testEdge2.incrementNBridges();
        testEdge2.incrementNBridges();
        testEdge3.incrementNBridges();
        testEdge3.incrementNBridges();
        testEdge4.incrementNBridges();
        testEdge4.incrementNBridges();
        testEdge5.incrementNBridges();
        testEdge5.incrementNBridges();
        testEdge6.incrementNBridges();
        testEdge6.incrementNBridges();
        assertEquals(0, testNode1.getNPossibleBridges());
    }

    @Test
    void getNPossibleBridgesShouldReturnSeven() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Edge testEdge1 = new Edge(testNode2, testNode3, "testEdge1");
        Edge testEdge2 = new Edge(testNode3, testNode2, "testEdge2");
        Edge testEdge3 = new Edge(testNode2, testNode3, "testEdge3");
        Edge testEdge4 = new Edge(testNode3, testNode2, "testEdge4");
        Edge testEdge5 = new Edge(testNode2, testNode3, "testEdge5");
        Edge testEdge6 = new Edge(testNode3, testNode2, "testEdge6");
        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge2, testEdge3, testEdge4, testEdge5, testEdge6 });
        testNode1.setTargetWeight(12);
        testNode2.setTargetWeight(12);
        testNode3.setTargetWeight(12);
        testEdge1.incrementNBridges();
        testEdge1.incrementNBridges();
        testEdge3.incrementNBridges();
        testEdge5.incrementNBridges();
        testEdge6.incrementNBridges();
        assertEquals(7, testNode1.getNPossibleBridges());
    }

    @Test
    void getNPossibleBridgesShouldReturnTwelve() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Edge testEdge1 = new Edge(testNode2, testNode3, "testEdge1");
        Edge testEdge2 = new Edge(testNode3, testNode2, "testEdge2");
        Edge testEdge3 = new Edge(testNode2, testNode3, "testEdge3");
        Edge testEdge4 = new Edge(testNode3, testNode2, "testEdge4");
        Edge testEdge5 = new Edge(testNode2, testNode3, "testEdge5");
        Edge testEdge6 = new Edge(testNode3, testNode2, "testEdge6");
        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge2, testEdge3, testEdge4, testEdge5, testEdge6 });
        testNode1.setTargetWeight(12);
        testNode2.setTargetWeight(12);
        testNode3.setTargetWeight(12);
        assertEquals(12, testNode1.getNPossibleBridges());
    }

    @Test
    void getNPossibleBridgesShouldReturnTwo() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Edge testEdge1 = new Edge(testNode1, testNode2, "testEdge1");
        Edge testEdge2 = new Edge(testNode1, testNode3, "testEdge2");
        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge2, null, null, null, null });
        testNode1.setTargetWeight(4);
        testNode2.setTargetWeight(2);
        testNode3.setTargetWeight(2);
        testEdge1.incrementNBridges();
        testEdge1.incrementNBridges();
        assertEquals(2, testNode1.getNPossibleBridges());
    }

    // getNFunnyEdges()
    @Test
    void getNFunnyEdgesShouldReturnZeroForNewNode() {
        Node testNode = new Node();
        assertEquals(0, testNode.getNFunnyEdges());
    }

    @Test
    void getNFunnyEdgesShouldReturnZero() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Edge testEdge1 = new Edge(testNode1, testNode2, "testEdge1");
        Edge testEdge2 = new Edge(testNode1, testNode3, "testEdge2");
        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge2, null, null, null, null });
        testNode1.setNeighborNodes(new Node[] { testNode2, testNode3, null, null, null, null });
        testNode1.setTargetWeight(2);
        testNode2.setTargetWeight(3);
        testNode3.setTargetWeight(4);
        assertEquals(0, testNode1.getNFunnyEdges());
    }

    @Test
    void getNFunnyEdgesShouldReturnThree() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Node testNode4 = new Node();
        Edge testEdge1 = new Edge(testNode1, testNode2, "testEdge1");
        Edge testEdge2 = new Edge(testNode1, testNode3, "testEdge2");
        Edge testEdge3 = new Edge(testNode1, testNode4, "testEdge3");
        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge2, testEdge3, null, null, null });
        testNode1.setNeighborNodes(new Node[] { testNode2, testNode3, testNode4, null, null, null });
        testNode1.setTargetWeight(2);
        testNode2.setTargetWeight(2);
        testNode3.setTargetWeight(2);
        testNode4.setTargetWeight(2);
        assertEquals(3, testNode1.getNFunnyEdges());
    }

    @Test
    void getNFunnyEdgesShouldReturnSix() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Node testNode4 = new Node();
        Node testNode5 = new Node();
        Node testNode6 = new Node();
        Node testNode7 = new Node();
        Edge testEdge1 = new Edge(testNode1, testNode2, "testEdge1");
        Edge testEdge2 = new Edge(testNode1, testNode3, "testEdge2");
        Edge testEdge3 = new Edge(testNode1, testNode4, "testEdge3");
        Edge testEdge4 = new Edge(testNode1, testNode5, "testEdge4");
        Edge testEdge5 = new Edge(testNode1, testNode6, "testEdge5");
        Edge testEdge6 = new Edge(testNode1, testNode7, "testEdge6");
        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge2, testEdge3, testEdge4, testEdge5, testEdge6 });
        testNode1.setNeighborNodes(new Node[] { testNode2, testNode3, testNode4, testNode5, testNode6, testNode7 });
        testNode1.setTargetWeight(1);
        testNode2.setTargetWeight(1);
        testNode3.setTargetWeight(1);
        testNode4.setTargetWeight(1);
        testNode5.setTargetWeight(1);
        testNode6.setTargetWeight(1);
        testNode7.setTargetWeight(1);
        assertEquals(6, testNode1.getNFunnyEdges());
    }

    @Test
    void getNFunnyEdgesShouldReturnTwo() {
        Node testNode1 = new Node();
        Node testNode2 = new Node();
        Node testNode3 = new Node();
        Node testNode4 = new Node();
        Node testNode5 = new Node();
        Node testNode6 = new Node();
        Node testNode7 = new Node();
        Edge testEdge1 = new Edge(testNode1, testNode2, "testEdge1");
        Edge testEdge2 = new Edge(testNode1, testNode3, "testEdge2");
        Edge testEdge3 = new Edge(testNode1, testNode4, "testEdge3");
        Edge testEdge4 = new Edge(testNode1, testNode5, "testEdge4");
        Edge testEdge5 = new Edge(testNode1, testNode6, "testEdge5");
        Edge testEdge6 = new Edge(testNode1, testNode7, "testEdge6");
        testNode1.setConnectedEdges(new Edge[] { testEdge1, testEdge2, testEdge3, testEdge4, testEdge5, testEdge6 });
        testNode1.setNeighborNodes(new Node[] { testNode2, testNode3, testNode4, testNode5, testNode6, testNode7 });
        testNode1.setTargetWeight(1);
        testNode2.setTargetWeight(1);
        testNode3.setTargetWeight(1);
        testNode4.setTargetWeight(2);
        testNode5.setTargetWeight(3);
        testNode6.setTargetWeight(4);
        testNode7.setTargetWeight(0);
        assertEquals(2, testNode1.getNFunnyEdges());
    }

}
