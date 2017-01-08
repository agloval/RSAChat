package rsachat;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RsaKeyGenerate {
  private  BigInteger n, d, e;

  private int bitlen = 1024;

  public RsaKeyGenerate(int bits) {
    bitlen = bits;
    SecureRandom r = new SecureRandom();
    BigInteger p = new BigInteger(bitlen / 2, 100, r);
    BigInteger q = new BigInteger(bitlen / 2, 100, r);
    n = p.multiply(q);
    BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q
        .subtract(BigInteger.ONE));
    e = new BigInteger("3");
    while (m.gcd(e).intValue() > 1) {
      e = e.add(new BigInteger("2"));
    }
    d = e.modInverse(m);
  }
  public void Print() throws FileNotFoundException
  {
    PrintWriter out = new PrintWriter("RSAKeys.txt");
    out.println("N "+n);
    out.println("E "+e);
    out.println("D "+d);
    out.close();
  }


  /** Trivial test program. */
  public static void main(String[] args) {
    //RsaKeyGenerate rsa = new RsaKeyGenerate(1024);
    
  }
}

   
    