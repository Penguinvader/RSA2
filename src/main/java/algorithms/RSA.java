package algorithms;

import java.math.BigInteger;
import java.util.Random;

public class RSA {

    public static class PublicKey {
        private final BigInteger n, e;

        public PublicKey(BigInteger n, BigInteger e) {
            this.n = n;
            this.e = e;
        }

        public BigInteger getN() {
            return n;
        }

        public BigInteger getE() {
            return e;
        }
    }

    public static class SecretKey {
        private final BigInteger d;

        public SecretKey(BigInteger d) {
            this.d = d;

        }

        public BigInteger getD() {
            return d;
        }

    }

    private int keyLength = 0;
    private PublicKey pk;
    private SecretKey sk;

    public RSA() {
        this.keyLength = new Random().nextInt(128) + 16;
        generateKeys();
    }

    public RSA(int keyLength) {
        if (keyLength > 2)
            this.keyLength = keyLength;
        else {
            this.keyLength = keyLength * 4;
            System.err.println("Given length was invalid, setting keyLength to " + this.keyLength);
        }
        generateKeys();
    }

    public RSA(PublicKey pk, SecretKey sk) {
        this.pk = pk;
        this.sk = sk;
    }

    public BigInteger encode(BigInteger number) {
        if (number.compareTo(pk.getN()) >= 0) {
            throw new ArithmeticException("The given message was longer than the modulo.");
        }
        return FME.getInstance().powmod(number, pk.getE(), pk.getN());
    }

    public BigInteger decode(BigInteger cypherText) {
        return FME.getInstance().powmod(cypherText, sk.getD(), pk.getN());
    }

    private void generateKeys() {
        MillerRabin primeTester = MillerRabin.getInstance();
        primeTester.setTestingLimit(BigInteger.valueOf(3));
        BigInteger p, q, phin, d;
        BigInteger e = BigInteger.valueOf(3);
        do {
            p = new BigInteger(keyLength, new Random()).add(BigInteger.ONE);
            q = new BigInteger(keyLength, new Random()).add(BigInteger.ONE);


            if (p.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
                continue;
            if (q.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
                continue;


        } while ((primeTester.runTests(p) == false) || false == primeTester.runTests(q));

        phin = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        EEA keySeeker = EEA.getInstance();
        while (keySeeker.calculateGCD(e, phin).compareTo(BigInteger.ONE) != 0) {
            e = new BigInteger(keyLength, new Random());
        }
        pk = new PublicKey(p.multiply(q), e);
        d = keySeeker.getX();

        if (d.compareTo(BigInteger.ZERO) <= 0)
            d = d.add(phin);

        if (d.compareTo(phin) > 0) {
            d = d.subtract(phin);
        }
        sk = new SecretKey(d);
    }

    public PublicKey getPK() {
        return pk;
    }

    public SecretKey getSK() {
        return sk;
    }

}