package rsa;

import algorithms.RSA;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.Random;


public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Do you want to encrypt or decrypt? [1=encrypt(default)/0=decrypt]");
        BigInteger n, e, d, msg;
        if (scanner.hasNextInt() && scanner.nextInt() == 0) {
            System.out.println("Please provide the public key's (n) component first!");
            n = readBigInteger();
            System.out.println("Please now provide the public key's (e) component!");
            e = readBigInteger();
            System.out.println("Please provide the secret key!");
            d = readBigInteger();

            System.out.println("Enter the encoded message:");
            msg = readBigInteger();
            RSA r = new RSA(new RSA.PublicKey(n, e), new RSA.SecretKey(d));
            System.out.println("Performing a decryption on given message with the provided parameters.");
            System.out.println("The encrypted message is the following:\n" + r.decode(msg));
        } else {
            System.out.println("Do you want to set the bit length of the RSA keys? [1,0]");
            RSA r;
            if (scanner.hasNextInt() && scanner.nextInt() == 1) {
                System.out.println("Please enter the bit length, it must be greater than 2!:");
                int length = readInteger();
                System.out.println("Generating random keys...");
                r = new RSA(length);
            } else {
                System.out.println("Generating random keys...");
                r = new RSA();
            }

            System.out.println("PublicKey(" + r.getPK().getN() + "," + r.getPK().getE() + ")");
            System.out.println("SecretKey(" + r.getSK().getD() + ")");

            System.out.println("\nPlease enter a message!");
            msg = readBigInteger();
            while (msg.compareTo(r.getPK().getN()) >= 0) {
                System.out.println("The entered message is bigger than the generated mod!");
                System.out.println("Please enter a shorter message!");
                msg = readBigInteger();
            }

            System.out.println("Performing an encryption on the given message..");
            System.out.println("Encoded message:");
            System.out.println(r.encode(msg).toString());

            if(msg.compareTo(r.decode(r.encode(msg))) != 0) {
                throw new ArithmeticException("The encoded message differs from the original!");
            }

        }

    }


    public static int readInteger() {
        while (true) {
            String line = scanner.hasNextLine() ? scanner.nextLine() : scanner.next();

            try {
                int num = Integer.parseInt(line);
                return num;
            } catch (NumberFormatException ex) {
                System.out.println("enter a number:");
            }
        }
    }

    public static BigInteger readBigInteger() {
        while (true) {
            String line = scanner.hasNextLine() ? scanner.nextLine() : scanner.next();

            try {
                BigInteger num = new BigInteger(line);
                return num;
            } catch (NumberFormatException ex) {
                System.out.println("enter a number:");
            }
        }
    }

}
