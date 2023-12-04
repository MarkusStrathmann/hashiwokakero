import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    @Test
    void testDecrementActualWeight() {
        Node testNode = new Node();
        testNode.setTargetWeight(10);
        testNode.setActualWeight(5);
        assertEquals(5, testNode.getActualWeight());
        testNode.decrementActualWeight();
        assertEquals(4, testNode.getActualWeight());
    }

    @Test
    void testGetActualWeight() {

    }

    @Test
    void testGetConnectedEdge() {

    }

    @Test
    void testGetConnectedEdges() {

    }

    @Test
    void testGetNConnectedEdges() {

    }

    @Test
    void testGetNFreeEdges() {

    }

    @Test
    void testGetNFunnyEdges() {

    }

    @Test
    void testGetNPossibleBridges() {

    }

    @Test
    void testGetName() {

    }

    @Test
    void testGetNeighborNode() {

    }

    @Test
    void testGetNeighborNodes() {

    }

    @Test
    void testGetTargetWeight() {

    }

    @Test
    void testGetWeightDifference() {

    }

    @Test
    void testIncrementActualWeight() {

    }

    @Test
    void testSetActualWeight() {

    }

    @Test
    void testSetConnectedEdge() {

    }

    @Test
    void testSetConnectedEdges() {

    }

    @Test
    void testSetName() {

    }

    @Test
    void testSetNeighborNode() {

    }

    @Test
    void testSetNeighborNodes() {

    }

    @Test
    void testSetTargetWeight() {

    }
}
