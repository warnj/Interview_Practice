package Tests;

import General_Questions.Sorting;
import Specific_Questions.MergeSortedArrays;
import Specific_Questions.Sudoku;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SortingTest {

    @Test
    public void testMergeTwoSorted() {
        int[] test = new int[]{1,2,3,0,0,0,0};
        int[] test2 = new int[]{2,5,6,8};
        MergeSortedArrays.merge(test, 3, test2, 4);
        assertArrayEquals(new int[]{1,2,2,3,5,6,8}, test);

        test = new int[]{1};
        test2 = new int[]{};
        MergeSortedArrays.merge(test, 1, test2, 0);
        assertArrayEquals(new int[]{1}, test);

        test = new int[]{0};
        test2 = new int[]{1};
        MergeSortedArrays.merge(test, 0, test2, 1);
        assertArrayEquals(new int[]{1}, test);
    }

    @Test
    public void testDutchSort() {
        int[] test = new int[]{1,0,1,1,1,0,1,1,0};
        Sorting.sortBySign(test);
        assertArrayEquals(new int[]{0,0,0,1,1,1,1,1,1}, test);

        test = new int[]{-1,0,0,-1,-1,0,-1,-1,0};
        Sorting.sortBySign(test);
        assertArrayEquals(new int[]{-1,-1,-1,-1,-1,0,0,0,0}, test);

        test = new int[]{-1,0,1,1,-1,0,1,-1,0};
        Sorting.sortBySign(test);
        assertArrayEquals(new int[]{-1,-1,-1,0,0,0,1,1,1}, test);
    }
}
