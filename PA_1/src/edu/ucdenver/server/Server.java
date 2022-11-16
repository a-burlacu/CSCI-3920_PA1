package edu.ucdenver.server;

import edu.ucdenver.tournament.Tournament;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final int port;
    private final int backlog; // how many clients can wait until they get connected
    private int connectionCounter; // keep track of how many clients are connected
    private String clientType;     // type of client (ADMIN or USER)

    private ServerSocket serverSocket;

    //private Tournament tournament;


    //--------------------------------------------------
    //                  constructors
    //--------------------------------------------------
//    public Server(int port, int backlog, String clientType, Tournament tournament) {
//        this.port = port;
//        this.backlog = backlog;
//        this.connectionCounter = 0;
//        this.clientType = clientType;
//        this.tournament = tournament;
//
//    }
    public Server(int port, int backlog, String clientType) {
        this.port = port;
        this.backlog = backlog;
        this.connectionCounter = 0;
        this.clientType = clientType;

    }
    public Server() {
        this(9888, 10, "ADMIN");
    }
//    public Server() {
//        this(9888, 10, "ADMIN", new Tournament(null, null, null));
//    }


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
    public void runServer() {

        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            this.serverSocket = new ServerSocket(this.port, this.backlog);

            System.out.println("STATUS MSG: 'serverSocket created'");


            while (true) {

                Socket clientConnection = waitForClientConnection();
                System.out.println("[SERVER] Waiting for client connection...");
                try {
                    clientConnection = this.serverSocket.accept();
                    System.out.println("[SERVER] Client connection accepted.");

                }
                catch (IOException ioe) {
                    System.out.println("[SERVER] Error accepting client connection.");
                    ioe.printStackTrace();
                }
                finally {
                    try {
                        // Create new thread that executes the client connection

                        //ClientWorker cw = new ClientWorker(clientConnection, this.tournament, this.clientType, this.connectionCounter);
                        ClientWorker cw = new ClientWorker(clientConnection, this.clientType, this.connectionCounter);
                        System.out.println("STATUS MSG: 'ClientWorker object created'");
                        executorService.execute(cw);

                        System.out.println("STATUS MSG: 'New client thread initialized'");

                    } catch (Exception e) {
                        System.err.println("[SERVER] Error creating thread for client.");
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException ioe) {
            System.out.println("[SERVER] Error opening server.");
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

        System.out.println("[SERVER] Terminating server connection...");
        try {
            this.serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

