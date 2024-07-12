package edu.school21.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.CsvFileSource;

public class NumberWorkerTest {
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 37})
    void isPrimeForPrimes(int number) {
        NumberWorker numberWorker = new NumberWorker();
        assertTrue(numberWorker.isPrime(number));
    }
    @ParameterizedTest
    @ValueSource(ints = {4, 33, 27295})
    void isPrimeForNotPrimes(int number) {
        NumberWorker numberWorker = new NumberWorker();
        assertFalse(numberWorker.isPrime(number));
    }
    @ParameterizedTest
    @ValueSource(ints = {0, 1, -17})
    void isPrimeForIncorrectNumbers(int number) {
        NumberWorker numberWorker = new NumberWorker();
        assertThrows(IllegalArgumentException.class, () -> numberWorker.isPrime(number));
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 2)
    void digitSum(int number, int expected) {
        NumberWorker numberWorker = new NumberWorker();
        assertEquals(expected, numberWorker.digitsSum(number));
    }
}
