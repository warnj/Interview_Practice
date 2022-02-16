package Tests;

import General_Questions.GraphQuestions;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GraphQuestionsTest {

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

}
