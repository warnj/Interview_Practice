package Tests;

import General_Questions.LeetCodeMisc;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class LeetCodeMiscTests {

    @Test
    public void testEvalRPN() {
        assertEquals(9, LeetCodeMisc.evalRPN(new String[]{"2","1","+","3","*"}));
        assertEquals(6, LeetCodeMisc.evalRPN(new String[]{"4","13","5","/","+"}));
        assertEquals(22, LeetCodeMisc.evalRPN(new String[]{"10","6","9","3","+","-11","*","/","*","17","+","5","+"}));
    }

    @Test
    public void testLeastInterval() {
        assertEquals(12, LeetCodeMisc.leastInterval(new char[]{'A','A','A','B','B','B','C','C','C'}, 1));
        assertEquals(8, LeetCodeMisc.leastInterval(new char[]{'A','A','A','B','B','B'}, 2));
        assertEquals(10, LeetCodeMisc.leastInterval(new char[]{'A','A','A','B','B','B','B'}, 2));
        assertEquals(6, LeetCodeMisc.leastInterval(new char[]{'A','A','A','B','B','B'}, 1));
        assertEquals(6, LeetCodeMisc.leastInterval(new char[]{'A','A','A','B','B','B'}, 0));
        assertEquals(16, LeetCodeMisc.leastInterval(new char[]{'A','A','A','A','A','A','B','C','D','E','F','G'}, 2));
    }

    @Test
    public void testAccountsMerge() {
        String[][] accounts = new String[][]{{"John","johnsmith@mail.com","john_newyork@mail.com"},{"John","johnsmith@mail.com","john00@mail.com"},{"Mary","mary@mail.com"},{"John","johnnybravo@mail.com"}};
        List<List<String>> l = new ArrayList<>();
        for (String[] a : accounts) l.add(Arrays.asList(a));
        List<List<String>> merged = LeetCodeMisc.accountsMerge(l);
        assertEquals(3, merged.size());

        accounts = new String[][]{{"David","David0@m.co","David4@m.co","David3@m.co"},{"David","David5@m.co","David5@m.co","David0@m.co"},{"David","David1@m.co","David4@m.co","David0@m.co"},{"David","David0@m.co","David1@m.co","David3@m.co"},{"David","David4@m.co","David1@m.co","David3@m.co"}};
        accounts = new String[][]{{"David","David0@m.co","David1@m.co"},{"David","David3@m.co","David4@m.co"},{"David","David4@m.co","David5@m.co"},{"David","David2@m.co","David3@m.co"},{"David","David1@m.co","David2@m.co"}};
        l = new ArrayList<>();
        for (String[] a : accounts) l.add(Arrays.asList(a));
        merged = LeetCodeMisc.accountsMerge(l);
        System.out.println(merged);
        assertEquals(1, merged.size());
    }

    @Test
    public void testTextJustify() {
        List<String> lines = LeetCodeMisc.textJustify(new String[]{"This", "is", "an", "example", "of", "text", "justification."}, 16);
        assertEquals(3, lines.size());
        assertEquals("This    is    an", lines.get(0));
        assertEquals("example  of text", lines.get(1));
        assertEquals("justification.  ", lines.get(2));
    }

    @Test
    public void testBestTeamScore() {
        assertEquals(34, LeetCodeMisc.bestTeamScore(new int[]{1,3,5,10,15}, new int[]{1,2,3,4,5}));
        assertEquals(16, LeetCodeMisc.bestTeamScore(new int[]{4,5,6,5}, new int[]{2,1,2,1}));
        assertEquals(6, LeetCodeMisc.bestTeamScore(new int[]{1,2,3,5}, new int[]{8,9,10,1}));
        assertEquals(7, LeetCodeMisc.bestTeamScore(new int[]{1,2,3,7}, new int[]{8,9,10,1}));
        assertEquals(29, LeetCodeMisc.bestTeamScore(new int[]{1,3,7,3,2,4,10,7,5}, new int[]{4,5,2,1,1,2,4,1,4}));
    }

    @Test
    public void testFindCheapestPrice() {
        assertEquals(700, LeetCodeMisc.findCheapestPrice(4, new int[][]{{0,1,100},{1,2,100},{2,0,100},{1,3,600},{2,3,200}}, 0, 3, 1));
        assertEquals(400, LeetCodeMisc.findCheapestPrice(4, new int[][]{{0,1,100},{1,2,100},{2,0,100},{1,3,600},{2,3,200}}, 0, 3, 2));
        assertEquals(-1, LeetCodeMisc.findCheapestPrice(4, new int[][]{{0,1,100},{1,2,100},{2,0,100},{1,3,600},{2,3,200}}, 0, 3, 0));
        assertEquals(200, LeetCodeMisc.findCheapestPrice(3, new int[][]{{0,1,100},{1,2,100},{0,2,500}}, 0, 2, 1));
        assertEquals(500, LeetCodeMisc.findCheapestPrice(3, new int[][]{{0,1,100},{1,2,100},{0,2,500}}, 0, 2, 0));
    }

    @Test
    public void testIsAdditiveNumber() {
        assertTrue(LeetCodeMisc.isAdditiveNumber("112358"));
        assertTrue(LeetCodeMisc.isAdditiveNumber("199100199"));
        assertTrue(LeetCodeMisc.isAdditiveNumber("199910001999"));
        assertTrue(LeetCodeMisc.isAdditiveNumber("123"));
        assertTrue(LeetCodeMisc.isAdditiveNumber("101"));
        assertTrue(LeetCodeMisc.isAdditiveNumber("12122436")); // 12+12=24, 12+24=36
        assertTrue(LeetCodeMisc.isAdditiveNumber("198019823962"));
        assertTrue(LeetCodeMisc.isAdditiveNumber("121474836472147483648"));
        assertFalse(LeetCodeMisc.isAdditiveNumber("0235813"));
        assertFalse(LeetCodeMisc.isAdditiveNumber("12"));
        assertFalse(LeetCodeMisc.isAdditiveNumber("1203"));
        assertFalse(LeetCodeMisc.isAdditiveNumber("1023"));
        assertFalse(LeetCodeMisc.isAdditiveNumber("124"));
        assertFalse(LeetCodeMisc.isAdditiveNumber("19991000199"));
    }

    @Test
    public void testMinFlipsMonoIncr() {
        assertEquals(1, LeetCodeMisc.minFlipsMonoIncr("00110"));
        assertEquals(2, LeetCodeMisc.minFlipsMonoIncr("010110"));
        assertEquals(2, LeetCodeMisc.minFlipsMonoIncr("00011000"));
        assertEquals(1, LeetCodeMisc.minFlipsMonoIncr("01000000"));
        assertEquals(3, LeetCodeMisc.minFlipsMonoIncr("01010101"));
        assertEquals(2, LeetCodeMisc.minFlipsMonoIncr("110000001"));
        assertEquals(2, LeetCodeMisc.minFlipsMonoIncr("110000000"));
        assertEquals(2, LeetCodeMisc.minFlipsMonoIncr("10001110"));
    }

    @Test
    public void testTwoCitySchedule() {
        assertEquals(110, LeetCodeMisc.twoCitySchedCost(new int[][]{{10,20},{30,200},{400,50},{30,20}}));
    }

    @Test
    public void testMinimumTotal() {
        List<List<Integer>> tri = Arrays.asList(Arrays.asList(2), Arrays.asList(3,4), Arrays.asList(6,5,7), Arrays.asList(4,1,8,3));
        assertEquals(11, LeetCodeMisc.minimumTotal(tri));
        tri = Arrays.asList(Arrays.asList(2), Arrays.asList(8,3), Arrays.asList(1,7,3), Arrays.asList(2,1,4,5));
        assertEquals(12, LeetCodeMisc.minimumTotal(tri));
    }

    @Test
    public void testRomanToInt() {
        assertEquals(3, LeetCodeMisc.romanToInt("III"));
        assertEquals(58, LeetCodeMisc.romanToInt("LVIII"));
        assertEquals(1994, LeetCodeMisc.romanToInt("MCMXCIV"));
    }

    @Test
    public void testUniquePathsObstacles() {
        assertEquals(0, LeetCodeMisc.uniquePathsWithObstacles(new int[][]{{1,0}}));
        assertEquals(0, LeetCodeMisc.uniquePathsWithObstacles(new int[][]{{0,1,0,0}}));
        assertEquals(1, LeetCodeMisc.uniquePathsWithObstacles(new int[][]{{0,1},{0,0}}));
        assertEquals(2, LeetCodeMisc.uniquePathsWithObstacles(new int[][]{{0,0,0},{0,1,0},{0,0,0}}));
        assertEquals(1, LeetCodeMisc.uniquePathsWithObstacles(new int[][]{{0,0,1},{0,1,0},{0,0,0}}));
        assertEquals(1, LeetCodeMisc.uniquePathsWithObstacles(new int[][]{{0,0,1},{0,1,0},{0,0,0}}));
        assertEquals(4, LeetCodeMisc.uniquePathsWithObstacles(new int[][]{{0,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,0}}));
    }

    @Test
    public void testUniquePaths() {
        assertEquals(1, LeetCodeMisc.uniquePaths(1,1));
        assertEquals(1, LeetCodeMisc.uniquePaths(1,2));
        assertEquals(2, LeetCodeMisc.uniquePaths(2,2));
        assertEquals(3, LeetCodeMisc.uniquePaths(3,2));
        assertEquals(6, LeetCodeMisc.uniquePaths(3,3));
        assertEquals(28, LeetCodeMisc.uniquePaths(3,7));
        assertEquals(28, LeetCodeMisc.uniquePaths(7,3));
        assertEquals(6, LeetCodeMisc.uniquePaths(2,6));
        assertEquals(10, LeetCodeMisc.uniquePaths(4,3));
    }

    @Test
    public void testAddDigits() {
        assertEquals(2, LeetCodeMisc.addDigits(38));
        assertEquals(0, LeetCodeMisc.addDigits(0));
    }

    @Test
    public void testTitleToNumber() {
        assertEquals(1, LeetCodeMisc.titleToNumber("A"));
        assertEquals(26, LeetCodeMisc.titleToNumber("Z"));
        assertEquals(28, LeetCodeMisc.titleToNumber("AB"));
        assertEquals(701, LeetCodeMisc.titleToNumber("ZY"));
        assertEquals(703, LeetCodeMisc.titleToNumber("AAA"));
    }
    @Test
    public void testConvertToTitle() {
        assertEquals("A", LeetCodeMisc.convertToTitle(1));
        assertEquals("Z", LeetCodeMisc.convertToTitle(26));
        assertEquals("AB", LeetCodeMisc.convertToTitle(28));
        assertEquals("ZY", LeetCodeMisc.convertToTitle(701));
        assertEquals("AAA", LeetCodeMisc.convertToTitle(703));
    }

    @Test
    public void testMountainArray() {
        assertFalse(LeetCodeMisc.validMountainArray(new int[]{2,1}));
        assertFalse(LeetCodeMisc.validMountainArray(new int[]{3,5,5}));
        assertFalse(LeetCodeMisc.validMountainArray(new int[]{0,2,3,3,5,2,1,0}));
        assertFalse(LeetCodeMisc.validMountainArray(new int[]{0,2,3,4,5,2,1,0,1,2,5,2,1}));
        assertFalse(LeetCodeMisc.validMountainArray(new int[]{0,1,2,3,4,5,6,7,8,9}));
        assertFalse(LeetCodeMisc.validMountainArray(new int[]{9,8,7,6,5,4,3,2,1,0}));

        assertTrue(LeetCodeMisc.validMountainArray(new int[]{0,3,2,1}));
        assertTrue(LeetCodeMisc.validMountainArray(new int[]{0,2,3,4,5,2,1,0}));
    }

    @Test
    public void testHouseRobber2() {
        assertEquals(3, LeetCodeMisc.rob2(new int[]{2,3,2}));
        assertEquals(3, LeetCodeMisc.rob2(new int[]{1,2,3}));
        assertEquals(4, LeetCodeMisc.rob2(new int[]{1,2,3,1}));

    }

    @Test
    public void testHouseRobber() {
        assertEquals(4, LeetCodeMisc.rob(new int[]{1,2,3,1}));
        assertEquals(12, LeetCodeMisc.rob(new int[]{2,7,9,3,1}));
        assertEquals(12, LeetCodeMisc.rob(new int[]{6,1,1,6}));
        assertEquals(24, LeetCodeMisc.rob(new int[]{1,9,1,1,6,1,9}));
        assertEquals(27, LeetCodeMisc.rob(new int[]{1,9,1,1,1,1,8,2,8,9}));
        assertEquals(19, LeetCodeMisc.rob(new int[]{4,9,6,1,9}));
        assertEquals(14, LeetCodeMisc.rob(new int[]{4,1,2,7,5,3,1}));
        assertEquals(19, LeetCodeMisc.rob(new int[]{2,4,8,9,9,3}));
        assertEquals(103, LeetCodeMisc.rob(new int[]{1,3,1,3,100}));
    }

    @Test
    public void testCanCompleteCircuit() {
        assertEquals(3, LeetCodeMisc.canCompleteCircuitSimple(new int[]{1,2,3,4,5}, new int[]{3,4,5,1,2}));
        assertEquals(-1, LeetCodeMisc.canCompleteCircuitSimple(new int[]{1,2,3,4,5}, new int[]{3,4,6,1,2}));
        assertEquals(3, LeetCodeMisc.canCompleteCircuitSimple(new int[]{1,2,2,4,5}, new int[]{3,4,3,2,2}));
        assertEquals(0, LeetCodeMisc.canCompleteCircuitSimple(new int[]{0,2,1}, new int[]{0,1,2}));
        assertEquals(-1, LeetCodeMisc.canCompleteCircuitSimple(new int[]{2,3,4}, new int[]{3,4,3}));
    }

    @Test
    public void testMinEatingSpeed() {
        // as a human to solve this:
        //    length of array important & number of times output goes into each pile is important
        //    if h == piles.length, then output = max(piles), if h < piles.length then not possible
        //    min possible output is if all piles are even: sum(piles) / h
        //    may be able to sort largest to smallest, then iterate through, actually order doesn't matter
        //    the bigger h is, the smaller the output (infinite h = output 1)
        //    the smaller h is (closer to piles.length), the bigger the output
        //    write out lots of examples and look for pattern
        assertEquals(4, LeetCodeMisc.minEatingSpeed(new int[]{3,6,7,11}, 8));
        assertEquals(30, LeetCodeMisc.minEatingSpeed(new int[]{30,11,23,4,20}, 5));
        assertEquals(23, LeetCodeMisc.minEatingSpeed(new int[]{30,11,23,4,20}, 6));
        assertEquals(4, LeetCodeMisc.minEatingSpeed(new int[]{4,3,2,1}, 4));
        assertEquals(3, LeetCodeMisc.minEatingSpeed(new int[]{4,3,2,1}, 5));
        assertEquals(2, LeetCodeMisc.minEatingSpeed(new int[]{4,3,2,1}, 6));
        assertEquals(2, LeetCodeMisc.minEatingSpeed(new int[]{4,3,2,1}, 7));
        assertEquals(2, LeetCodeMisc.minEatingSpeed(new int[]{4,3,2,1}, 9));
        assertEquals(1, LeetCodeMisc.minEatingSpeed(new int[]{4,3,2,1}, 10));
        assertEquals(1000000000, LeetCodeMisc.minEatingSpeed(new int[]{1000000000, 1000000000}, 3));
        assertEquals(500000000, LeetCodeMisc.minEatingSpeed(new int[]{1000000000, 1000000000}, 4));
    }
	
}
