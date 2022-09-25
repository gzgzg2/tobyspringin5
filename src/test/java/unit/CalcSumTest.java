package unit;

import org.junit.Test;
import springbook.cal.Calculator;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CalcSumTest {

    @Test
    public void sumOfNumbers() throws IOException {
        Calculator calculator = new Calculator();
        int sum = calculator.calcSum(getClass().getClassLoader().getResource(
                "numbers.txt").getPath());

        assertThat(sum, is(10));
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        Calculator calculator = new Calculator();
        int mul = calculator.calcMul(getClass().getClassLoader().getResource(
                "numbers.txt").getPath());

        assertThat(mul, is(24));
    }

    @Test
    public void concatenate() throws IOException {
        Calculator calculator = new Calculator();
        String result = calculator.concatenate(getClass().getClassLoader().getResource(
                "numbers.txt").getPath());

        assertThat(result, is("1234"));
    }
}
