package rsachat;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

public class RsaEncrypt {
  private BigInteger n, e;
  Scanner sc;
  
  public RsaEncrypt() throws FileNotFoundException
  {
     sc = new Scanner(new File("RSAKeys.txt"));
     sc.next();
     n = new BigInteger(sc.next());
     sc.next();
     e = new BigInteger(sc.next());
  }
  
  public synchronized BigInteger encrypt(BigInteger message) {
    return message.modPow(e, n);
  }

  public static void main(String[] args) throws FileNotFoundException {
    RsaEncrypt rsa = new RsaEncrypt();
  }
}

   
    