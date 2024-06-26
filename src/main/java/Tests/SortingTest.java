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
