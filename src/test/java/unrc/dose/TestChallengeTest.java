package unrc.dose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to test the TestChallenge class methods.
 * @author Brusati Formento, Matias
 * @author Cuesta, Alvaro
 */
public class TestChallengeTest {

	private static final Logger log = LoggerFactory.getLogger(TestChallengeTest.class);

	@BeforeClass
	public static void before(){
		if (!Base.hasConnection()) {
			Base.open();
			log.info("TestChallengeTest setup");
			Base.openTransaction();
		}

		User u = User.set("test", "1234", "test@example.com", true);
		User u1 = User.set("test1", "1234", "test1@example.com", false);

		String source = "package src.main;\n";
			   source += "public class One {\n";
			   source += "	public static int one() {\n";
			   source += "		return 1;\n";
			   source += "	}\n";
			   source += "}";
		String test = "package src.test;\n";
				test += "import src.main.One;\n";
				test += "import org.junit.*;\n";
				test += "public class OneTest {\n";
				test += "	@Test\n";
				test += "	public void test() {\n";
				test += "		Assert.assertEquals(1, One.one());\n";
				test += "	}\n";
				test += "}";
		TestChallenge.addTestChallenge(u.getInteger("id"), "Test", "One", "description",
		source, 100, 1, test);
		Challenge c = Challenge.findFirst("title = ?", "Test");

		source = "package src.main;\n";
		source += "public class One1 {\n";
		source += "	public static int one() {\n";
		source += "		return 1;\n";
		source += "	}\n";
		source += "}";
		test = "package src.test;\n";
		test += "import src.main.One1;\n";
		test += "import org.junit.*;\n";
		test += "public class One1Test {\n";
		test += "	@Test\n";
		test += "	public void test() {\n";
		test += "		Assert.assertEquals(1, One1.one());\n";
		test += "	}\n";
		test += "}";
		TestChallenge.addTestChallenge(u.getInteger("id"), "Test1", "One1", "description",
		source, 100, 0, test);
		Challenge c1 = Challenge.findFirst("title = ?", "Test1");

		source = "package src.main;\n";
		source += "public class One2 {\n";
		source += "	public static int one() {\n";
		source += "		return 1;\n";
		source += "	}\n";
		source += "}";
		test = "package src.test;\n";
		test += "import src.main.One2;\n";
		test += "import org.junit.*;\n";
		test += "public class One2Test {\n";
		test += "	@Test\n";
		test += "	public void test() {\n";
		test += "		Assert.assertEquals(1, One2.one());\n";
		test += "	}\n";
		test += "}";
		TestChallenge.addTestChallenge(u.getInteger("id"), "Test2", "One2", "description",
		source, 100, 0, test);
		Challenge c2 = Challenge.findFirst("title = ?", "Test2");

		source = "package src.main;\n";
		source += "public class One3 {\n";
		source += "	public static int one() {\n";
		source += "		return 1;\n";
		source += "	}\n";
		source += "}";
		test = "package src.test;\n";
		test += "import src.main.One3;\n";
		test += "import org.junit.*;\n";
		test += "public class One3Test {\n";
		test += "	@Test\n";
		test += "	public void test() {\n";
		test += "		Assert.assertEquals(1, One3.one());\n";
		test += "	}\n";
		test += "}";
		TestChallenge.addTestChallenge(u.getInteger("id"), "Test3", "One3", "description",
		source, 100, 0, test);
		CompilationChallenge.addCompilationChallenge(u.getInteger("id"), "Test4", "Test4", "description",
		source, 100, 0);

		Proposition.newProposition(u.getInteger("id"),c.getInteger("id"));
		Proposition p1 = Proposition.newProposition(u.getInteger("id"), c1.getInteger("id"));
		p1.set("isSolution", 1);
		p1.saveIt();
		Proposition p2 = Proposition.newProposition(u.getInteger("id"), c2.getInteger("id"));
		p2.set("isSolution", 1);
		p2.saveIt();
		Proposition p3 = Proposition.newProposition(u1.getInteger("id"), c2.getInteger("id"));
		p3.set("isSolution", 1);
		p3.saveIt();
	
	}

	@AfterClass
	public static void after(){
		if (Base.hasConnection()) {
			log.info("TestChallengeTest tearDown");
			Base.rollbackTransaction();
			Base.close();
		}  
	}

	/**
	 * Test methods for set and get.
	 */
	@Test
	public void setAndGetTest() {
		TestChallenge testChallenge = new TestChallenge();
		testChallenge.setChallengeId(19);
		testChallenge.setTest("this is a challenge test");
		testChallenge.saveIt();
		assertEquals(19,testChallenge.getChallengeId());
		assertEquals("this is a challenge test",testChallenge.getTest());
	}

	/**
	 * Test method for validateTestChallenge.
	 */
	@Test
	public void validateTestChallengeTest() {
		int userId = 1; 
		String title = "Oneees";
		String description = "...";
		String className = "Ones";
		String source = "package src.main;\n";
			   source += "public class Ones {\n";
			   source += "	public static int one() {\n";
			   source += "		return 1;\n";
			   source += "	}\n";
			   source += "}";
		int point = 100;
		int ownerSolutionId = 9;
		String test = "package src.test;\n";
				test += "import src.main.Ones;\n";
				test += "import org.junit.*;\n";
				test += "public class OnesTest {\n";
				test += "	@Test\n";
				test += "	public void test() {\n";
				test += "		Assert.assertEquals(1, Ones.one());\n";
				test += "	}\n";
				test += "}";
		TestChallenge.addTestChallenge(userId, title, className, description,
		source, point, ownerSolutionId, test);
		TestChallenge testChallenge = TestChallenge.findFirst("test = ?", test);
		boolean validate = TestChallenge.validateTestChallenge(testChallenge);
		assertEquals(true,validate);
	}

	/**
	 * Test method for addTestChallenge.
	 */
	@Test
	public void addTestChallengeTest() {
		int userId = 32; 
		String title= "suma ones";
		String nameClass = "SumaOnes";
		String description = "Test suma ones";
		String source = "package src.main;\n";
			   source += "public class SumaOnes {\n";
			   source += "	public static int sumOnes() {\n";
			   source += "		return 1+1;\n";
			   source += "	}\n";
			   source += "}";
		int point = 52;
		int ownerSolutionId = 3;
		String test = "package src.test;\n";
			   test += "import src.main.SumaOnes;\n";
			   test += "import org.junit.*;\n";
			   test += "public class SumaOnesTest {\n";
			   test += "	@Test\n";
			   test += "	public void test() {\n";
			   test += "		Assert.assertEquals(2, SumaOnes.sumOnes());\n";
			   test += "	}\n";
			   test += "}";
		boolean validation = TestChallenge.addTestChallenge(userId,title,nameClass,description,
		source,point,ownerSolutionId,test);
		assertEquals(true,validation);
	}

	/**
	 * Test method for viewAllTestChallange.
	 */
	@Test
	public void viewAllTestChallangeTest() {
		List<Map<String, Object>> all =
		TestChallenge.viewAllTestChallange();
		assertEquals(4, all.size());
		assertEquals("Test", all.get(0).get("title"));
		assertEquals("Test1", all.get(1).get("title"));
		assertEquals("Test2", all.get(2).get("title"));
		assertEquals("Test3", all.get(3).get("title"));
	}

	/**
	 * Test method for viewResolvedTestChallange.
	 */
	@Test
	public void viewResolvedTestChallangeTest() {
		List<Map<String, Object>> resolved =
		TestChallenge.viewResolvedTestChallange();
		assertEquals(2, resolved.size());
		assertEquals("Test1", resolved.get(0).get("title"));
		assertEquals("Test2", resolved.get(1).get("title"));
	}

	/**
	 * Test method for viewResolvedTestChallange,
	 */
	@Test
	public void viewUnsolvedTestChallangeTest() {
		List<Map<String, Object>> unsolved =
		TestChallenge.viewUnsolvedTestChallange();
		assertEquals(2, unsolved.size());
		assertEquals("Test", unsolved.get(0).get("title"));
		assertEquals("Test3", unsolved.get(1).get("title"));
	}

	/**
	 * Test method for modifyUnsolvedTestChallenge
	 * In case the challenge is solved.
	 */
	@Test
	public void modifyUnsolvedTestChallengeTest() {
		Challenge c = Challenge.findFirst("title = ?", "Test1");
		String title = "Test3";
		String className = "NotFound";
		String description = "";
		String source = "//";
		int point = 0;
		String test = "Test";
		try{
			TestChallenge.modifyUnsolvedTestChallenge(
				c.getInteger("id"), title, className, description,
				source, point, test);
			fail();
		} catch (RuntimeException ex) {
			assertEquals(Challenge.CHALLENGE_RESOLVED, ex.getMessage());
		}
	}

	/**
	 * Test method for modifyUnsolvedTestChallenge
	 * In case the challenge is unsolved.
	 */
	@Test
	public void modifyUnsolvedTestChallengeTest1() {
		Challenge c = Challenge.findFirst("title = ?", "Test3");
		String title = "Test3";
		String className = "MultOnes";
		String description = "";
		String source = "package src.main;\n";
			   source += "public class MultOnes {\n";
			   source += "	public static int multOnes() {\n";
			   source += "		return 1*1;\n";
			   source += "	}\n";
			   source += "}";
		int point = 0;
		String test = "package src.test;\n";
			   test += "import src.main.MultOnes;\n";
			   test += "import org.junit.*;\n";
			   test += "public class MultOnesTest {\n";
			   test += "	@Test\n";
			   test += "	public void test() {\n";
			   test += "		Assert.assertEquals(1, MultOnes.multOnes());\n";
			   test += "	}\n";
			   test += "}";
		boolean obtained = TestChallenge.modifyUnsolvedTestChallenge(
			c.getInteger("id"), title, className, description,
			source, point, test);
		assertEquals(true,obtained);
	}

	/**
	 * Test method for deleteChallenge.
	 * In case of the CompilationChallenge already exist.
	 */
	@Test
	public void deleteTestChallengeTest() {
		int userId = 5; 
		String title= "Hello Word";
		String className = "MultOnes1";
		String description = "";
		String source = "package src.main;\n";
			   source += "public class MultOnes1 {\n";
			   source += "	public static int multOnes1() {\n";
			   source += "		return 1*1;\n";
			   source += "	}\n";
			   source += "}";
		int point = 0;
		String test = "package src.test;\n";
			   test += "import src.main.MultOnes1;\n";
			   test += "import org.junit.*;\n";
			   test += "public class MultOnes1Test {\n";
			   test += "	@Test\n";
			   test += "	public void test() {\n";
			   test += "		Assert.assertEquals(1, MultOnes1.multOnes());\n";
			   test += "	}\n";
			   test += "}";
		int ownerSolutionId = 10;
		TestChallenge.addTestChallenge(userId, title, className, description,
		source, point, ownerSolutionId, test);
		assertNull(TestChallenge.findFirst("test = ?", test));   
	}

	/**
	 * Test method for runCompilationTest.
	 */
	@Test
	public void runCompilationTest() {
		String nameFile = "TestCompilation";
		String source = "package src.test; \n";
			   source += "import org.junit.*;\n";
			   source += "public class "+ nameFile + " {\n";
			   source += "	@Test\n";
			   source += "	public void test() {\n";
		       source += "		Assert.assertTrue(true);\n";
			   source += "	}\n";
			   source += "}";
		Challenge.generateFileJavaTest(nameFile, source);
		boolean obtained = TestChallenge.runCompilationTestJava(nameFile);
		assertEquals(true, obtained);
	}

	/**
	 * Test method for runCompilationTest.
	 */
	@Test
	public void runTestJavaTest() {
		String nameFile = "RunTestCompilation";
		String source = "package src.test; \n";
			   source += "import org.junit.*;\n";
			   source += "public class "+ nameFile + " {\n";
			   source += "	@Test\n";
			   source += "	public void test() {\n";
			   source += "		Assert.assertTrue(true);\n";
			   source += "	}\n";
			   source += "}";
		Challenge.generateFileJavaTest(nameFile, source);
		TestChallenge.runCompilationTestJava(nameFile);
		boolean obtained = TestChallenge.runTestJava(nameFile);
		assertEquals(true, obtained);
	}

	/**
	 * Test method for validatePresenceTestChallenge.
	 */
	@Test
	public void validatePresenceTestChallengeTest() {
		TestChallenge t = null;
		try {
			TestChallenge.validatePresenceTestChallenge(t);
			fail();
		} catch (IllegalArgumentException ex) {
			assertEquals(TestChallenge.CHALLENGE_NOT_EXIST, ex.getMessage());
		}
	}

}
