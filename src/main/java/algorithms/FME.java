package algorithms;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FME {
    private static FME instance;
    private HashMap<BigInteger,BigInteger> powerToValueMap;
    private ArrayList<BigInteger> partialRepresentation;

    private FME() {
    }

    public static FME getInstance(){
        if(instance == null)
            instance = new FME();
        return instance;
    }

    public BigInteger powmod(BigInteger a, BigInteger b, BigInteger n) {
        if(n.compareTo(BigInteger.ZERO) == 0) {
            throw new ArithmeticException();
        }
        if(b.compareTo(BigInteger.ONE) > 0 && b.compareTo(getMaxPowerOfTwo(b)) == 0)
            return evenPow(a, b, n);
        else
            return oddPow(a, b, n);
    }
    private BigInteger evenPow(BigInteger a, BigInteger b, BigInteger n) {
        BigInteger resultOfTheLastIteration = a.mod(n);
        for(BigInteger i = BigInteger.valueOf(2); i.compareTo(b) <= 0; i = i.multiply(BigInteger.valueOf(2))) {
            resultOfTheLastIteration = resultOfTheLastIteration.pow(2).mod(n);
        }
        return resultOfTheLastIteration;
    }
    private BigInteger oddPow(BigInteger a, BigInteger b, BigInteger n) {
        powerToValueMap = new HashMap<>();
        partialRepresentation = partitionNumber(b);
        BigInteger resultOfTheLastIteration = a.mod(n);
        if(partialRepresentation.contains(BigInteger.valueOf(1))){
            powerToValueMap.put(BigInteger.ONE,resultOfTheLastIteration.add(BigInteger.ZERO));
        }
        BigInteger limit = getMaxPowerOfTwo(b);
        for(BigInteger i = BigInteger.TWO; i.compareTo(limit) <= 0; i = i.multiply(BigInteger.valueOf(2))) {
            resultOfTheLastIteration = resultOfTheLastIteration.pow(2);
            resultOfTheLastIteration = resultOfTheLastIteration.mod(n);
            if(partialRepresentation.contains(i)) {
                powerToValueMap.put(i, resultOfTheLastIteration);
            }
        }



        resultOfTheLastIteration = BigInteger.ONE;

        for(Map.Entry<BigInteger,BigInteger> big : powerToValueMap.entrySet()) {
            resultOfTheLastIteration = resultOfTheLastIteration.multiply(big.getValue());
        }
        return resultOfTheLastIteration.mod(n);
    }

    private ArrayList<BigInteger> partitionNumber(BigInteger number){
        ArrayList<BigInteger> vals = new ArrayList<>();
        BigInteger max = getMaxPowerOfTwo(number);

        BigInteger n = number;

        do  {
            vals.add(max);
            n = n.subtract(max);
            max = max.divide(BigInteger.valueOf(2));
            while(n.subtract(max).compareTo(BigInteger.ZERO) < 0 && max.compareTo(BigInteger.ONE) != 0) {
                max = max.divide(BigInteger.valueOf(2));
            }

        } while(n.compareTo(BigInteger.ZERO) != 0);

        return vals;
    }

    private BigInteger getMaxPowerOfTwo(BigInteger power) {
        BigInteger high = BigInteger.ONE;

        while(high.multiply(BigInteger.valueOf(2)).compareTo(power) <= 0) {
            high = high.multiply(BigInteger.valueOf(2));
        }
        return high;
    }

}
