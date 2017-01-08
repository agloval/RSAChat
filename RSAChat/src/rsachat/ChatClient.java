package rsachat;
import java.net.*;
import java.io.*;
import java.math.BigInteger;

public class ChatClient implements Runnable
{  private Socket socket              = null;
   private Thread thread              = null;
   private DataInputStream  console   = null;
   private DataOutputStream streamOut = null;
   private ChatClientThread client    = null;
   private RsaDecrypt rsa;
   private ChatWindow w=null;

   public ChatClient(String serverName, int serverPort) throws FileNotFoundException
   { 
       rsa = new RsaDecrypt();
       w = new ChatWindow(this);
       w.setVisible(true);
       System.out.println("Laczenie, prosze czekac ...");
      try
      {  socket = new Socket(serverName, serverPort);
         System.out.println("Polaczono z: " + socket);
         start();
      }
      catch(UnknownHostException uhe)
      {  System.out.println("Host nieznany: " + uhe.getMessage()); }
      catch(IOException ioe)
      {  System.out.println("Blad " + ioe.getMessage()); }
   }
   public void run()
   {  
      
       while (thread != null)
      {  try
         {  
             
            streamOut.writeUTF(console.readLine());
            streamOut.flush();
             System.out.println("test");
         }
         catch(IOException ioe)
         {  System.out.println("Nastapil blad: " + ioe.getMessage());
            stop();
         }
      }
   }
   public void BntRun(String msg)
   {
         try
         {  
            
            streamOut.writeUTF(msg);
            streamOut.flush();
         }
         catch(IOException ioe)
         {  System.out.println("Nastapil blad: " + ioe.getMessage());
            stop();
         }
      
   }
   public void handle(String msg)
   { BigInteger ciphertext = null;
     BigInteger plaintext = null;
       if (msg.equals(".koniec"))
      {  System.out.println("Koniec ...");
         w.setVisible(false);
         w.dispose();
         stop();
      }
      else
       //Co robi z wiadomoscia
       ciphertext = new BigInteger(msg);
       plaintext = rsa.decrypt(ciphertext);
       w.ShowMsg("Ciphertext "+ciphertext);
       w.ShowMsg("Plaintext "+new String(plaintext.toByteArray()));
         System.out.println("Ciphertext "+ciphertext);
         System.out.println("Plaintext "+new String(plaintext.toByteArray()));
   }
   public void start() throws IOException
   {  console   = new DataInputStream(System.in);
      streamOut = new DataOutputStream(socket.getOutputStream());
      if (thread == null)
      {  client = new ChatClientThread(this, socket);
         thread = new Thread(this);                   
         thread.start();
      }
   }
   public void stop()
   {  if (thread != null)
      {  thread.stop();  
         thread = null;
      }
      try
      {  if (console   != null)  console.close();
         if (streamOut != null)  streamOut.close();
         if (socket    != null)  socket.close();
      }
      catch(IOException ioe)
      {  System.out.println("Blad ..."); }
      client.close();  
      client.stop();
   }
   public static void main(String args[]) throws FileNotFoundException
   {  ChatClient client = null;
      
         client = new ChatClient("localhost", 1);
   }
}