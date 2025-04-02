// package me.ms;

import java.io.*;
import java.net.*;


/**
 *
 * @author Sergio
 */
public class Main {

    public static final int PORT = 1050; // porta al di fuori del range 1-1024 !

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */

    public static void main(String[] args) throws IOException {
        try (  ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("EchoServer: started ");
            System.out.println("Server Socket: " + serverSocket);
            Socket clientSocket=null;
            BufferedReader in=null;
            PrintWriter out=null;
            try {
                // bloccante finchè non avviene una connessione
                clientSocket = serverSocket.accept();
                System.out.println("Connection accepted: "+ clientSocket);
                // creazione stream di input da clientSocket
                InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
                in = new BufferedReader(isr);
                // creazione stream di output su clientSocket
                OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
                BufferedWriter bw = new BufferedWriter(osw);
                out = new PrintWriter(bw, true);
                //ciclo di ricezione dal client e invio di risposta
                out.print("Hello (END to close connection): ");
                out.flush();
                while (true) {
                    String str = in.readLine();
                    if (str.equals("END")) break;
                    System.out.println("Echoing: " + str.toUpperCase());
                    out.println(str.toUpperCase());
                }
            }
            catch (IOException e) {
                System.err.println("Accept failed");
                System.exit(1);
            }
            // chiusura di stream e socket
            System.out.println("EchoServer: closing...");
            out.close();
            in.close();
            clientSocket.close();
        } }


}

