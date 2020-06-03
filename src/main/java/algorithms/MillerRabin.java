package algorithms;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MillerRabin {
    private static MillerRabin instance;
    private BigInteger testingLimit;
    private BigInteger s, d;

    private MillerRabin() {

    }

    public static MillerRabin getInstance() {
        if (instance == null)
            instance = new MillerRabin();
        return instance;
    }

    public void setTestingLimit(BigInteger testingLimit) {
        this.testingLimit = testingLimit.compareTo(BigInteger.ZERO) > 0 ? testingLimit : BigInteger.valueOf(3);
    }

    public boolean runTests(BigInteger number) {
        if (testingLimit == null) {
            setTestingLimit(BigInteger.valueOf(3));
        }

        s = BigInteger.ZERO;
        d = BigInteger.ZERO;
        if (number.compareTo(BigInteger.ZERO) <= 0) {
            throw new ArithmeticException("the given 'number' must be positive!!");
        }

        splitUpNumber(number.subtract(BigInteger.ONE));
        List<BigInteger> bases = generateBases(number);


        FME calc = FME.getInstance();

        return bases.stream().anyMatch(b -> {
            if(calc.powmod(b,d,number).compareTo(BigInteger.ONE) == 0)
                return true;
            for(BigInteger i = BigInteger.ZERO; i.compareTo(s) < 0; i = i.add(BigInteger.ONE)) {
                if(calc.powmod(b,BigInteger.valueOf(2).pow(i.intValue()).multiply(d),number)
                        .compareTo(BigInteger.valueOf(-1).add(number)) == 0)
                    return true;

            }
            return false;
        });
    }

    private void splitUpNumber(BigInteger number) {
        BigInteger helper = number;

        while (helper.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 0) {
            helper = helper.divide(BigInteger.valueOf(2));
            s = s.add(BigInteger.ONE);
        }
        d = helper.add(BigInteger.ZERO);
    }

    private List<BigInteger> generateBases(BigInteger number) {
        List<BigInteger> localBases = new ArrayList<>();
        BigInteger helper;
        for (helper = BigInteger.valueOf(2); BigInteger.valueOf(localBases.size()).compareTo(testingLimit) <= 0; helper = helper.add(BigInteger.ONE)) {
            if(helper.compareTo(number) >= 0)
                break;
            localBases.add(helper);
        }
        return localBases;
    }
}
