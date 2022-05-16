package Tests;

import General_Questions.GraphQuestions.*;
import General_Questions.GraphQuestions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GraphQuestionsTest {

    @Test
    public void testCloneGraph() {
        Node one = new Node(1);
        Node two = new Node(2);
        Node three = new Node(3);
        Node four = new Node(4);
        one.neighbors.add(two);
        one.neighbors.add(three);
        two.neighbors.add(one);
        two.neighbors.add(four);
        three.neighbors.add(one);
        three.neighbors.add(four);
        four.neighbors.add(two);
        four.neighbors.add(three);

        Node copy = GraphQuestions.cloneGraph(one);
        assertEquals(one.val, copy.val);
        System.out.println(one.neighbors);
        System.out.println(copy.neighbors);
        assertEquals(one.neighbors.size(), copy.neighbors.size());
        assertNotSame(one.neighbors, copy.neighbors);

        assertEquals(two.val, copy.neighbors.get(0).val);
        System.out.println(two.neighbors);
        System.out.println(copy.neighbors.get(0).neighbors);
        assertEquals(two.neighbors.size(), copy.neighbors.get(0).neighbors.size());
        assertNotSame(two.neighbors, copy.neighbors.get(0).neighbors);

        assertEquals(three.val, copy.neighbors.get(1).val);
        assertEquals(three.neighbors.size(), copy.neighbors.get(1).neighbors.size());
        assertNotSame(three.neighbors, copy.neighbors.get(1).neighbors);

        assertNull(GraphQuestions.cloneGraph(null));
        assertEquals(1, GraphQuestions.cloneGraph(new Node(1)).val);

//        Node one = new Node(1);
//        Node two = new Node(2);
//        Node three = new Node(3);
//        Node four = new Node(4);
//        one.neighbors.add(two);
//        one.neighbors.add(three);
//        two.neighbors.add(three);
//        two.neighbors.add(four);
//        three.neighbors.add(four);
//
//        Node copy = GraphQuestions.cloneGraph(one);
//        assertEquals(one.val, copy.val);
//        assertEquals(one.neighbors.size(), copy.neighbors.size());
//        System.out.println(one.neighbors);
//        System.out.println(copy.neighbors);
//        assertNotSame(one.neighbors, copy.neighbors);
//
//        assertEquals(two.val, copy.neighbors.get(0).val);
//        assertEquals(two.neighbors.size(), copy.neighbors.get(0).neighbors.size());
//        assertNotSame(two.neighbors, copy.neighbors.get(0).neighbors);
//
//        assertEquals(three.val, copy.neighbors.get(1).val);
//        assertEquals(three.neighbors.size(), copy.neighbors.get(1).neighbors.size());
//        assertNotSame(three.neighbors, copy.neighbors.get(1).neighbors);
    }

    @Test
    public void testMinHeightTrees() {
        List<Integer> trees = GraphQuestions.findMinHeightTrees(4, new int[][]{{1,0},{1,2},{1,3}});
        assertEquals(1, trees.size());
        assertTrue(trees.contains(1));

        trees = GraphQuestions.findMinHeightTrees(6, new int[][]{{3,0},{3,1},{3,2},{3,4},{5,4}});
        assertEquals(2, trees.size());
        assertTrue(trees.contains(3) && trees.contains(4));

        trees = GraphQuestions.findMinHeightTrees(3, new int[][]{{0,1},{0,2}});
        assertEquals(1, trees.size());
        assertTrue(trees.contains(0));

        trees = GraphQuestions.findMinHeightTrees(1, new int[][]{});
        assertEquals(1, trees.size());
        assertTrue(trees.contains(0));
    }

    @Test
    public void testCanFinish() {
        assertTrue(GraphQuestions.canFinish(2, new int[][]{{1,0}}));
        assertTrue(GraphQuestions.canFinish(3, new int[][]{{1,0},{2,0}}));
        assertTrue(GraphQuestions.canFinish(6, new int[][]{{1,0},{3,2},{5,4}}));
        assertTrue(GraphQuestions.canFinish(6, new int[][]{{1,0},{3,2},{5,4},{4,1}}));
        assertFalse(GraphQuestions.canFinish(6, new int[][]{{1,0},{3,2},{5,4},{4,1},{0,5}}));
        assertTrue(GraphQuestions.canFinish(3, new int[][]{{1,0}}));
        assertTrue(GraphQuestions.canFinish(3, new int[][]{}));
        assertFalse(GraphQuestions.canFinish(2, new int[][]{{1,0},{0,1}}));
        assertTrue(GraphQuestions.canFinish(7, new int[][]{{1,0},{0,3},{0,2},{3,2},{2,5},{4,5},{5,6},{2,4}}));
        assertFalse(GraphQuestions.canFinish(7, new int[][]{{1,0},{0,3},{0,2},{3,2},{2,5},{4,5},{5,6},{2,4},{3,1}}));
        assertFalse(GraphQuestions.canFinish(7, new int[][]{{1,0},{0,3},{0,2},{3,2},{2,5},{4,5},{5,6},{2,4},{5,2}}));
    }

    @Test
    public void testFindOrder() {
        assertArrayEquals(new int[]{0,1}, GraphQuestions.findOrder(2, new int[][]{{1,0}}));
        assertArrayEquals(new int[]{0,2,1}, GraphQuestions.findOrder(3, new int[][]{{1,0}}));
        assertArrayEquals(new int[]{1,0,2}, GraphQuestions.findOrder(3, new int[][]{{2,1}}));
        assertArrayEquals(new int[]{}, GraphQuestions.findOrder(3, new int[][]{{1,0},{2,1},{0,2}}));
        assertArrayEquals(new int[]{0}, GraphQuestions.findOrder(1, new int[][]{}));
        assertArrayEquals(new int[]{0,1}, GraphQuestions.findOrder(2, new int[][]{}));
        assertArrayEquals(new int[]{0,1,2,3}, GraphQuestions.findOrder(4, new int[][]{{1,0},{2,0},{3,1},{3,2}}));
        // ^ other valid ordering: 0,2,1,3
    }

}
