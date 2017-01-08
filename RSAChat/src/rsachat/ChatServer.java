package rsachat;

import java.net.*;
import java.io.*;
import java.math.BigInteger;

public class ChatServer implements Runnable
{  private ChatServerThread clients[] = new ChatServerThread[50];
   private ServerSocket server = null;
   private Thread       thread = null;
   private int clientCount = 0;
   private RsaKeyGenerate RKG = null;
   private RsaEncrypt rsa = null;
   
   public ChatServer(int port)
   {  try
      {  
         RKG = new RsaKeyGenerate(1024); 
         RKG.Print();
         rsa = new RsaEncrypt();
         System.out.println("Wiazanie z portem " + port + ", prosze czekac  ...");
         server = new ServerSocket(port);  
         System.out.println("Serwer dziala na: " + server);
         
         start(); 
         
      }
      catch(IOException ioe)
      {  System.out.println("Nie mozna uzyc portu " + port + ": " + ioe.getMessage()); }
   }
   public void run()
   {  while (thread != null)
      {  try
         {  System.out.println("Oczekiwanie na klienta ..."); 
            addThread(server.accept()); }
         catch(IOException ioe)
         {  System.out.println("Blad akceptacji serwera: " + ioe); stop(); }
      }
   }
   public void start()  { 
       if (thread == null)
      {  thread = new Thread(this); 
         thread.start();
      } }
   public void stop()   { 
       if (thread != null)
      {  thread.stop(); 
         thread = null;
      } }
   private int findClient(int ID)
   {  for (int i = 0; i < clientCount; i++)
         if (clients[i].getID() == ID)
            return i;
      return -1;
   }
   public synchronized void handle(int ID, String input)
   { 
       System.out.println("test2");
       String msg=new String(rsa.encrypt(new BigInteger(input.getBytes())).toString());
       
       if (input.equals(".koniec"))
      {  clients[findClient(ID)].send(".koniec");
         remove(ID); }
      else
         for (int i = 0; i < clientCount; i++)
           
            clients[i].send(msg);   
   }
   public synchronized void remove(int ID)
   {  int pos = findClient(ID);
      if (pos >= 0)
      {  ChatServerThread toTerminate = clients[pos];
         System.out.println("Usuwanie watku serwera " + ID + " na " + pos);
         if (pos < clientCount-1)
            for (int i = pos+1; i < clientCount; i++)
               clients[i-1] = clients[i];
         clientCount--;
         try
         {  toTerminate.close(); }
         catch(IOException ioe)
         {  System.out.println("Blad zamkniecia watku: " + ioe); }
         toTerminate.stop(); }
   }
   private void addThread(Socket socket)
   {  if (clientCount < clients.length)
      {  System.out.println("Zaakceptowano klienta: " + socket);
         clients[clientCount] = new ChatServerThread(this, socket);
         try
         {  clients[clientCount].open(); 
            clients[clientCount].start();  
            clientCount++; }
         catch(IOException ioe)
         {  System.out.println("Blad otworzenia watku: " + ioe); } }
      else
         System.out.println("Odrzucono klienta, uzyskano maksymalna liczbe klientow: " + clients.length);
   }
   public static void main(String args[]) { 
       ChatServer server = null;
     
         server = new ChatServer(1);
   } 
}