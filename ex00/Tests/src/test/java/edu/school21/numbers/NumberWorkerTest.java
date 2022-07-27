package edu.school21.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {


    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, 0, 1})
        void isExpectedExceptions(int number) {
        IllegalNumberException illegalNumberException = Assertions.assertThrows(IllegalNumberException.class, () -> NumberWorker.isPrime(number));
        Assertions.assertEquals("Wrong input number", illegalNumberException.toString());
    }
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 113, 3231233})
    void isReallyPrime(int number) throws IllegalNumberException {
       Assertions.assertTrue(NumberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 9, 169})
    void isNotPrime(int number) throws IllegalNumberException {
        Assertions.assertFalse(NumberWorker.isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"/data.csv"}, delimiter = ',')
    void isNotPrime(int number, int sum) throws IllegalNumberException {
        Assertions.assertEquals(NumberWorker.digitsSum(number), sum);
    }
}
