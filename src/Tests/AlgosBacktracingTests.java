package Tests;

import General_Questions.AlgosBacktracing;
import org.junit.Test;
import java.util.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class AlgosBacktracingTests {

	@Test
	public void testLetterCombinations() {
		List<String> results = AlgosBacktracing.letterCombinations("23");
		assertEquals(Arrays.asList("ad","ae","af","bd","be","bf","cd","ce","cf"), results);
	}

	@Test
	public void testWordSearch2() {
		List<String> l = AlgosBacktracing.findWords(new char[][]{{'o','a','a','n'},{'e','t','a','e'},{'i','h','k','r'},{'i','f','l','v'}}, new String[]{"oath","pea","eat","rain"});
		assertEquals(2, l.size());
		assertEquals("oath", l.get(0));
		assertEquals("eat", l.get(1));
		List<String> l2 = AlgosBacktracing.findWords(new char[][]{{'a','b'},{'c','d'}}, new String[]{"abcb"});
		assertEquals(0, l2.size());
	}

	@Test
	public void testWordSearch() {
		assertTrue(AlgosBacktracing.exist(new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}, "ABCCED"));
		assertFalse(AlgosBacktracing.exist(new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}, "ABCEC"));
		assertFalse(AlgosBacktracing.exist(new char[][]{{'A','A','A','A'},{'A','A','A','A'},{'A','A','A','A'}}, "aaaaaaaaaaaaa".toUpperCase()));
		assertFalse(AlgosBacktracing.exist(new char[][]{{'A','A','A'}}, "AAAA"));
		assertTrue(AlgosBacktracing.exist(new char[][]{{'A','A', 'A'},{'A','A','A'},{'A','A','B'}}, "AAAAAAAA"));
		assertTrue(AlgosBacktracing.exist(new char[][]{{'A','A','A','A'},{'A','A','A','A'},{'A','A','A','A'}}, "aaaaaaaaaaaa".toUpperCase()));
		assertTrue(AlgosBacktracing.exist(new char[][]{{'A','B','C','E'},{'S','F','E','S'},{'A','D','E','E'}}, "ABCESEEEFS"));
	}

	@Test
	public void testNQueens() {
		List<List<String>> boardsLarge = AlgosBacktracing.solveNQueens(9);
		assertEquals(352, boardsLarge.size());
//		for (List<String> board : boardsLarge) {
//			for (String row : board)
//				System.out.println(row);
//			System.out.println();
//		}
		List<List<String>> boards = AlgosBacktracing.solveNQueens(4);
		assertEquals(2, boards.size());
		assertThat(boards.get(0), is(Arrays.asList(".Q..","...Q","Q...","..Q.")));
		assertThat(boards.get(1), is(Arrays.asList("..Q.","Q...","...Q",".Q..")));

		boards = AlgosBacktracing.solveNQueens(1);
		assertEquals(1, boards.size());
		assertThat(boards.get(0), is(List.of("Q")));
	}
	@Test
	public void testValidBoardNQueens() {
		assertTrue(AlgosBacktracing.validBoard(Arrays.asList(".Q..","...Q","Q...","..Q.")));
		assertTrue(AlgosBacktracing.validBoard(Arrays.asList(".Q..","...Q","Q...","..Q")));
		assertTrue(AlgosBacktracing.validBoard(Arrays.asList(".Q..","...Q","Q...","..")));
		assertTrue(AlgosBacktracing.validBoard(Arrays.asList(".Q..","...Q","Q...")));

		assertFalse(AlgosBacktracing.validBoard(Arrays.asList(".Q..","...Q","Q..Q","..Q.")));
		assertFalse(AlgosBacktracing.validBoard(Arrays.asList(".Q..","...Q","Q...",".Q..")));
		assertFalse(AlgosBacktracing.validBoard(Arrays.asList(".Q..","...Q","Q...","Q")));
	}

	@Test
	public void testCombinationSum() {
		List<List<Integer>> combinations = AlgosBacktracing.combinationSum(new int[]{2,3,6,7}, 7);
		System.out.println(combinations);
		assertEquals(2, combinations.size());
		List<List<Integer>> combinations2 = AlgosBacktracing.combinationSum(new int[]{2,3,5}, 8);
		System.out.println(combinations2);
		assertEquals(3, combinations2.size());
		assertEquals(0, AlgosBacktracing.combinationSum(new int[]{2}, 1).size());
	}

//	@Test
//	public void testCombinationSum3() {
//		List<List<Integer>> combinations = AlgosBacktracing.combinationSum(new int[]{2,3,6,7}, 7);
//		System.out.println(combinations);
//		assertEquals(2, combinations.size());
//		List<List<Integer>> combinations2 = AlgosBacktracing.combinationSum(new int[]{2,3,5}, 8);
//		System.out.println(combinations2);
//		assertEquals(3, combinations2.size());
//		assertEquals(0, AlgosBacktracing.combinationSum(new int[]{2}, 1).size());
//	}

	@Test
	public void testCombinationSum4() {
		assertEquals(7, AlgosBacktracing.combinationSum4(new int[]{1,2,3}, 4));
		assertEquals(0, AlgosBacktracing.combinationSum4(new int[]{9}, 3));
		assertEquals(1132436852, AlgosBacktracing.combinationSum4(new int[]{2,1,3}, 35));
	}

	@Test
	public void testPermute() {
		List<List<Integer>> l = AlgosBacktracing.permute(new int[]{1,2,3});
		assertEquals(6, l.size());
		assertEquals(2, AlgosBacktracing.permute(new int[]{0,1}).size());
		assertEquals(1, AlgosBacktracing.permute(new int[]{1}).size());
	}

	@Test
	public void testPermutations() {
//		System.out.println(AlgosBacktracing.permutations("abcde"));
		System.out.println(AlgosBacktracing.permutations("14372"));
		List<String> c = AlgosBacktracing.permutations("14372");
		Collections.sort(c);
		System.out.println(c);
	}

	@Test
	public void testSpaceWordsEfficient() {
		Set<String> dict = new HashSet<String>();
		dict.add("sales");
		dict.add("sale");
		dict.add("force");
		dict.add("for");
		dict.add("a");
		dict.add("how");
		assertEquals("sales force", AlgosBacktracing.spaceWordsEfficient("salesforce", dict));
	}

	@Test
	public void testSubsets() {
		System.out.println(AlgosBacktracing.permuteIterative(new int[] {1,2,3}));
	}
}
