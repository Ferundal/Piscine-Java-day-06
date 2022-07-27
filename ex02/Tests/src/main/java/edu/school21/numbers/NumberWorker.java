package edu.school21.numbers;

public class NumberWorker {

    static public boolean isPrime(int num) throws IllegalNumberException {
        if (num < 2) {
            throw new IllegalNumberException();
        }
        int remainder = 1;
        if (num > 3) {
            if (num % 2 != 0) {
                int currentDivider = 3;
                while (((long) num + 1 - (long) currentDivider * (long) currentDivider) * remainder > 0) {
                    remainder = num % currentDivider;
                    currentDivider += 2;
                }
            }
            else
            {
                remainder = 0;
            }
        }
        return (remainder != 0);
    }

    static public int digitsSum(int number) {
        if (number == 0) {
            return (1);
        }
        int result = 0;
        while (number / 10 != 0) {
            result += number % 10;
            number /= 10;
        }
        result += number;
        if (result < 0) {
            result = -result;
        }
        return (result);
    }

}
