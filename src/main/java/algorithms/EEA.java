package algorithms;

import java.math.BigInteger;

public class EEA {
    private BigInteger x;

    private static EEA instance;

    private EEA() {
    }

    public static EEA getInstance() {
        if (instance == null)
            instance = new EEA();

        return instance;
    }

    public BigInteger calculateGCD(BigInteger a, BigInteger b) {
        if (a.compareTo(BigInteger.ZERO) == 0 || b.compareTo(BigInteger.ZERO) == 0)
            return a.add(b);

        if (a.signum() == -1)
            a = a.negate();
        if (b.signum() == -1)
            b = b.negate();

        x = BigInteger.ZERO;
        BigInteger previousX = BigInteger.ONE;
        BigInteger r1 = a;
        BigInteger r2 = b;
        BigInteger i;
        for (i = BigInteger.ZERO; r2.compareTo(BigInteger.ZERO) != 0; i = i.add(BigInteger.ONE)) {
            BigInteger resultOfDivision = r1.divide(r2);
            BigInteger tempPreviousX = previousX.add(BigInteger.ZERO);
            previousX = x.add(BigInteger.ZERO);
            x = x.multiply(resultOfDivision).add(tempPreviousX);
            BigInteger tempR1 = r1.add(BigInteger.ZERO);
            r1 = r2.add(BigInteger.ZERO);
            r2 = tempR1.mod(r2);
        }
        x = BigInteger.valueOf(-1).pow(i.intValue()).multiply(previousX);
        return r1;


    }


    public BigInteger getX() {
        return x;
    }

}
