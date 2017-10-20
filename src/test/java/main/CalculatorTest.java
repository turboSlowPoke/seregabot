package main;

import org.junit.*;

import static org.junit.Assert.*;

public class CalculatorTest {
    Calculator calculator;

    @BeforeClass
    public static void beforeClass(){
        System.out.println("Before "+CalculatorTest.class.getSimpleName());
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("After "+CalculatorTest.class.getSimpleName());
    }

    @Before
    public void setUp() throws Exception {
        calculator = new Calculator();
    }

    @After
    public void tearDown() throws Exception {
        calculator = null;
    }

    @Test
    public void getSum() throws Exception {
        assertEquals(new Integer(3),calculator.getSum(1,2));
    }

}