package edu.ucdenver.server;

import edu.ucdenver.tournament.Tournament;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    private final int port;
    private final int backlog; // how many clients can wait until they get connected
    private int connectionCounter; // keep track of how many clients are connected
    private String clientType;     // type of client (ADMIN or USER)

    private ServerSocket serverSocket;

    private Tournament tournament;


    //--------------------------------------------------
    //                  constructors
    //--------------------------------------------------
    public Server(int port, int backlog, String clientType, Tournament tournament) {
        this.port = port;
        this.backlog = backlog;
        this.connectionCounter = 0;
        this.clientType = clientType;
        this.tournament = tournament;

    }
    public Server() {
        this(9888, 10, "ADMIN", new Tournament(null, null, null));
    }


    //--------------------------------------------------
    //              wait for connection
    //--------------------------------------------------

    private Socket waitForClientConnection() throws IOException {
        System.out.println("Waiting for a connection....");
        Socket clientConnection = this.serverSocket.accept();
        System.out.println("STATUS MSG: clientConnection created");
        this.connectionCounter++;
        System.out.println("STATUS MSG: connection counter incremented");
        System.out.printf("Connection #%d accepted from %s %n", this.connectionCounter,
                clientConnection.getInetAddress().getHostName());

        return clientConnection;
    }


    //--------------------------------------------------
    //                  run server
    //--------------------------------------------------
    public void run() {

        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            this.serverSocket = new ServerSocket(this.port, this.backlog);

            System.out.println("STATUS MSG: serverSocket created");

            /*
            Add command line menu options here?

             System.out.println("Enter 1 to load Catalog from file named: StoreFile.ser.");
            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            if (option == 1){
                this.store = Store.loadFromFile();
                System.out.println("Successfully loaded Store from: StoreFile.ser.");
            }
             */

            while (true) {

                Socket clientConnection = null;
                System.out.println("[SERVER] WAITING FOR CLIENT CONNECTION.");
                try {
                    clientConnection = this.serverSocket.accept();
                    System.out.println("[SERVER] ACCEPTED CLIENT CONNECTION.");

                }
                catch (IOException ioe) {
                    System.out.println("[SERVER] ERROR ACCEPTING CLIENT CONNECTION.");
                    ioe.printStackTrace();
                }
                finally {
                    try {
                        // Create new thread that executes the client connection

                        ClientWorker cw = new ClientWorker(clientConnection, this.tournament, this.clientType, this.connectionCounter);

                        executorService.execute(cw);
                    } catch (Exception e) {
                        System.err.println("[SERVER] ERROR THE THREAD COULD NOT BE OPENED FOR CLIENT.");
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException ioe) {
            System.out.println("\n++++++ Cannot open the server ++++++\n");
            executorService.shutdown();

            ioe.printStackTrace();
        }
        finally {
            try {
                this.stop();
            } catch (Exception e) {
               e.printStackTrace();
            }
        }
    }



    //--------------------------------------------------
    //                  stop server
    //--------------------------------------------------
    public void stop() {

        System.out.println("Terminating connection...");
        try {
            this.serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

