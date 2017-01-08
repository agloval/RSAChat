package rsachat;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

public class RsaDecrypt {
  private BigInteger n, d;
  Scanner sc;
  
  public RsaDecrypt() throws FileNotFoundException
  {
     sc = new Scanner(new File("RSAKeys.txt"));
     sc.next();
     n = new BigInteger(sc.next());
     sc.next();
     sc.next();
     sc.next();
     d = new BigInteger(sc.next());
  }
  
  public synchronized BigInteger decrypt(BigInteger message) {
    return message.modPow(d, n);
  }
  
  public static void main(String[] args) throws FileNotFoundException {
    RsaDecrypt rsa = new RsaDecrypt();
  }
  
}

   
    