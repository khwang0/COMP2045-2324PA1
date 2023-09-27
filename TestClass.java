// Test Sokoban with junit

import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

public class TestClass {
	//	test for readValidInput
	@Test
	public void readValidInputTest(){
		System.out.println("readValidInput() method test: ");

		String[] testCases = { "W", "A", "S", "D", "q", "r", "h" };
		String[] negativeTestCases = { "w", "a", "s", "d", "Q", "R", "H", "1", "2", "3" };
		String[] allCases = Arrays.copyOf(testCases, testCases.length + negativeTestCases.length);
		System.arraycopy(negativeTestCases, 0, allCases, testCases.length, negativeTestCases.length);

		HashMap<String, Boolean> results = new HashMap<>();
		Thread[] inputThreads = new Thread[allCases.length];

		// save the original output streams
		PrintStream originalOut = System.out;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(baos));

		for (int i = 0; i < allCases.length; i++) {
			final int index = i;
			inputThreads[i] = new Thread(() -> {
				try {
					InputStream inputStream = System.in;
					System.setIn(new ByteArrayInputStream(allCases[index].getBytes()));

					Sokoban sokoban = new Sokoban();
					char cleanedChar = sokoban.readValidInput();
					results.put(allCases[index], cleanedChar == allCases[index].charAt(0));
					System.setIn(inputStream);

				} catch (Exception e) {}
			});
		}

		for (int i = 0; i < allCases.length; i++) {
			try {
				inputThreads[i].start();
				inputThreads[i].join(50);
			} catch (InterruptedException e) {}
		}
		System.setOut(originalOut);

		for (int i = 0; i < testCases.length; i++) {
			if(!results.containsKey(testCases[i])){
				fail("InputTest failed for " + testCases[i]);
			}
			assertTrue(results.containsKey(testCases[i]));
		}

		System.out.println("\tPassed for positive test cases");

		for (int i = 0; i < negativeTestCases.length; i++) {
			if(results.containsKey(negativeTestCases[i])){
				fail("InputTest failed for " + negativeTestCases[i]);
			}
			assertFalse(results.containsKey(negativeTestCases[i]));
		}

		System.out.println("\tPassed for negative test cases");
	}

	//	test for moveBox
	@Test
	public void moveBoxTest(){
		System.out.println("moveBox() method test: ");
		Sokoban sokoban = new Sokoban();

		char[][] testMapOri = new char[][]{
				{'#','#','#','#','#','#','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','o','@','.',' ',' ','#'},
				{'#',' ',' ','.',' ',' ','#'},
				{'#',' ',' ','@',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','#','#','#','#','#','#'}
		};

		char[][] resultMapD = new char[][]{
				{'#','#','#','#','#','#','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','o','@','.',' ',' ','#'},
				{'#',' ',' ','.',' ',' ','#'},
				{'#',' ',' ',' ','@',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','#','#','#','#','#','#'}
		};

		char[][] resultMapW = new char[][]{
				{'#','#','#','#','#','#','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','o','@','.',' ',' ','#'},
				{'#',' ',' ','%',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','#','#','#','#','#','#'}
		};

		char[][] resultMapS = new char[][]{
				{'#','#','#','#','#','#','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','o','@','.',' ',' ','#'},
				{'#',' ',' ','.',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ',' ','@',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','#','#','#','#','#','#'}
		};

		char[][] resultMapA = new char[][]{
				{'#','#','#','#','#','#','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','o','@','.',' ',' ','#'},
				{'#',' ',' ','.',' ',' ','#'},
				{'#',' ','@',' ',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','#','#','#','#','#','#'}
		};

		// deep copy the testMapOri to testMap
		char[][] testMap = CopyMap(testMapOri);
		sokoban.moveBox(testMap, 4, 3, 'D');
		assertTrue(Arrays.deepEquals(resultMapD, testMap));
		testMap = CopyMap(testMapOri);
		sokoban.moveBox(testMap, 4, 3, 'W');
		assertTrue(Arrays.deepEquals(resultMapW, testMap));
		testMap = CopyMap(testMapOri);
		sokoban.moveBox(testMap, 4, 3, 'S');
		assertTrue(Arrays.deepEquals(resultMapS, testMap));
		testMap = CopyMap(testMapOri);
		sokoban.moveBox(testMap, 4, 3, 'A');
		assertTrue(Arrays.deepEquals(resultMapA, testMap));

		System.out.println("\tPassed");
	}

	private char[][] CopyMap(char[][] map){
		char[][] copy = new char[map.length][];
		for(int i = 0; i < map.length; i++){
			copy[i] = map[i].clone();
		}
		return copy;
	}

	@Test
	public void movePlayerTest(){
		System.out.println("movePlayer() method test: ");
		Sokoban sokoban = new Sokoban();

		char[][] testMapOri = new char[][]{
				{'#','#','#','#','#','#','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ','@','.',' ',' ','#'},
				{'#',' ',' ','.',' ',' ','#'},
				{'#',' ',' ','@',' ',' ','#'},
				{'#',' ',' ','o',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','#','#','#','#','#','#'}
		};

		char[][] resultMapW = new char[][]{
				{'#','#','#','#','#','#','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ','@','.',' ',' ','#'},
				{'#',' ',' ','%',' ',' ','#'},
				{'#',' ',' ','o',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','#','#','#','#','#','#'}
		};
		char[][] resultMapD = new char[][]{
				{'#','#','#','#','#','#','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ','@','.',' ',' ','#'},
				{'#',' ',' ','.',' ',' ','#'},
				{'#',' ',' ','@',' ',' ','#'},
				{'#',' ',' ',' ','o',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','#','#','#','#','#','#'}
		};
		char[][] resultMapS = new char[][]{
				{'#','#','#','#','#','#','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ','@','.',' ',' ','#'},
				{'#',' ',' ','.',' ',' ','#'},
				{'#',' ',' ','@',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ',' ','o',' ',' ','#'},
				{'#','#','#','#','#','#','#'}
		};
		char[][] resultMapA = new char[][]{
				{'#','#','#','#','#','#','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ','@','.',' ',' ','#'},
				{'#',' ',' ','.',' ',' ','#'},
				{'#',' ',' ','@',' ',' ','#'},
				{'#',' ','o',' ',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','#','#','#','#','#','#'}
		};

		// deep copy the testMapOri to testMap
		char[][] testMap = CopyMap(testMapOri);
		sokoban.movePlayer(testMap, 5, 3, 'D');
		assertTrue(Arrays.deepEquals(resultMapD, testMap));

		testMap = CopyMap(testMapOri);
		sokoban.movePlayer(testMap, 5, 3, 'W');
		assertTrue(Arrays.deepEquals(resultMapW, testMap));

		testMap = CopyMap(testMapOri);
		sokoban.movePlayer(testMap, 5, 3, 'S');
		assertTrue(Arrays.deepEquals(resultMapS, testMap));

		testMap = CopyMap(testMapOri);
		sokoban.movePlayer(testMap, 5, 3, 'A');
		assertTrue(Arrays.deepEquals(resultMapA, testMap));

		System.out.println("\tPassed");
	}

	@Test
	public void gameOverTest(){
		System.out.println("gameOver() method test: ");
		Sokoban sokoban = new Sokoban();

		char[][] negativeTestMap = new char[][]{
				{'#','#','#','#','#','#','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','o','@','.',' ',' ','#'},
				{'#',' ',' ','.',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ',' ','@',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','#','#','#','#','#','#'}
		};

		char[][] positiveTestMap = new char[][]{
				{'#','#','#','#','#','#','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','o',' ','%',' ',' ','#'},
				{'#',' ',' ','%',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','#','#','#','#','#','#'}
		};

		assertTrue(sokoban.gameOver(positiveTestMap));
		assertFalse(sokoban.gameOver(negativeTestMap));
		System.out.println("\tPassed");
	}

	@Test
	public void numberOfRowsTest(){
		System.out.println("numberOfRows() method test: ");
		Sokoban sokoban = new Sokoban();
		assertEquals(11, sokoban.numberOfRows("map1.txt"));
		System.out.println("\tPassed");
	}

	@Test
	public void readmapTest(){
		System.out.println("readmap() method test: ");
		Sokoban sokoban = new Sokoban();
		char[][] map = sokoban.readmap("map1.txt");
		char[][] expected = new char[][]{
				{' ', ' ', '#', '#', '#', '#', '#'},
				{'#', '#', '#', ' ', ' ', ' ', '#'},
				{'#', '.', 'o', '@', ' ', ' ', '#'},
				{'#', '#', '#', ' ', '@', '.', '#'},
				{'#', '.', '#', '#', '@', ' ', '#'},
				{'#', ' ', '#', ' ', '.', ' ', '#', '#'},
				{'#', '@', ' ', '%', '@', '@', '.', '#'},
				{'#', ' ', ' ', ' ', ' ', ' ', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
				{'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
				{'#', ' ', ' ', ' ', '.', ' ', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
				{'#', '#', '#', '#', '#', '#', '#', '#'}
		};

		for(int i = 0; i < Math.min(map.length, expected.length); i++){
			for(int j = 0; j < Math.min(map[i].length, expected[i].length); j++){
				assertEquals(expected[i][j], map[i][j]);
			}
		}
		System.out.println("\tPassed");
	}

	@Test
	public void findPlayerTest(){
		System.out.println("findPlayer() method test: ");
		Sokoban sokoban = new Sokoban();

		char[][] testMap = new char[][]{
				{'#','#','#','#','#','#','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ','@','.',' ',' ','#'},
				{'#',' ',' ','.',' ',' ','#'},
				{'#',' ',' ',' ',' ','o','#'},
				{'#',' ',' ','@',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','#','#','#','#','#','#'}
		};

		int[] expected = new int[]{4, 5};
		int[] result = sokoban.findPlayer(testMap);
		assertArrayEquals(expected, result);
		System.out.println("\tPassed");
	}

	@Test
	public void isValidTest(){
		System.out.println("isValid() method test: ");
		char[][] negativeTestMap01 = new char[][]{
				{'#','#','#','#','#','#','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','o','@','%',' ',' ','#'},
				{'#',' ',' ','.',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','#','#','#','#','#','#'}
		};
		char[][] negativeTestMap02 = new char[][]{
				{'#','#','#','#','#','#','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ','@','.',' ',' ','#'},
				{'#',' ',' ','.',' ',' ','#'},
				{'#',' ',' ',' ',' ','o','#'},
				{'#',' ',' ','@',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','#','#','#','#','#','#'}
		};
		char[][] positiveTestMap01 = new char[][]{
				{'#','#','#','#','#','#','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ','@','.',' ',' ','#'},
				{'#',' ',' ','.',' ',' ','#'},
				{'#',' ',' ',' ','o',' ','#'},
				{'#',' ',' ','@',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','#','#','#','#','#','#'}
		};
		char[][] positiveTestMap02 = new char[][]{
				{'#','#','#','#','#','#','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ','@','.',' ',' ','#'},
				{'#',' ',' ','.',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#',' ','o','@',' ',' ','#'},
				{'#',' ',' ',' ',' ',' ','#'},
				{'#','#','#','#','#','#','#'}
		};

		Sokoban sokoban = new Sokoban();
		assertFalse(sokoban.isValid(negativeTestMap01, 2,1, 'D'));
		assertFalse(sokoban.isValid(negativeTestMap02, 4,5, 'D'));
		assertTrue(sokoban.isValid(positiveTestMap01,4,4, 'D'));
		assertTrue(sokoban.isValid(positiveTestMap02,5,2, 'D'));
		System.out.println("\tPassed");
	}


}