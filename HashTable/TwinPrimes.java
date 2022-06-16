import java.util.Random;

public class TwinPrimes {

    private int start, end;

    public TwinPrimes(int start, int end) {
        if(start < 0 || end < 0 || end <= start) {
            throw new IllegalArgumentException("valid range: 0 < start < end");
        }
        if(start < 3) {
            start = 3;
        }
        this.start = start;
        this.end = end;
    }

    public int getSecondTwin() {
        if(start % 2 == 0) {
            start++;
        }
        for(int a = start; a < end; a += 2) {
            if(isPrime(a)) {
                if(isPrime(a+2)) {
                    return a+2;
                }
            }
        }
        return -1;
    }

    private boolean isPrime(int number) {
        int base;
        int checks = 0;
        boolean result = false;
        Random random = new Random();

        for(int i = 0; i < 10; i++) { // loop to lower chance of false positives
            base = random.nextInt(number) + 2;
            while(base >= number) {
                base = random.nextInt(number) + 2;
            }
            if(square_and_multiply(base,number) == 1) {
                checks++;
            }
        }

        if(checks > 7) { // tolerance
            result = true;
        }

        return result;
    }

    private double square_and_multiply(int base, int power) {
        double result = base;
        String binary = Integer.toBinaryString(power-1);

        for(int i = 1; i < binary.length(); i++) {
            if(binary.charAt(i) == '1') {
                result *= result; // square
                result *= base;   // multiple
                result %= power;
            } else {
                result *= result; // square
                result %= power;
            }
        }
        return result;
    }

    /*
        a = some random integer (1 < a < p)
        p = the number being checked for prime

        if a^(p-1) mod p != 1, then p is not a prime
        if a^(p-1) mod p == 1, then p is most likely a prime

        Example: 7^107, a = 7, p-1 = 106 -> bx1101010

        if 1: square & multiple
        if 0: square

        1st bit = 1 | 7
        2nd bit = 1 | 7  ^ 2 % 107 * 7 = r1
        3rd bit = 0 | r1 ^ 2 % 107     = r2
        4th bit = 1 | r2 ^ 2 % 107 * 7 = r3
        5th bit = 0 | r3 ^ 2 % 107     = r4
        6th bit = 1 | r4 ^ 2 % 107 * 7 = r5
        7th bit = 0 | r5 ^ 2 % 107     = result
    */

}

