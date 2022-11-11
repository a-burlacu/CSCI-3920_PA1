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
        System.out.println("clientConnection created");
        this.connectionCounter++;
        System.out.println("connection counter incremented");
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
            System.out.println("serverSocket created");

            while (true) {
                try {
                    Socket clientConnection = this.waitForClientConnection();

                    // Create new thread that executes the client connection
                    //TODO: figure out how to pass clientType(ADMIN or USER) to ClientWorker objects created as threads

                    ClientWorker cw = new ClientWorker(clientConnection, this.tournament, this.clientType, this.connectionCounter);

                    executorService.execute(cw);
                }
                catch (IOException ioe) {
                    System.out.println("\n-------------------\nServer Terminated");
                    ioe.printStackTrace();
                }
            }
        } catch (IOException ioe) {
            System.out.println("\n++++++ Cannot open the server ++++++\n");
            executorService.shutdown();
            ioe.printStackTrace();
        }
    }



    //--------------------------------------------------
    //                  stop server
    //--------------------------------------------------
    public void shutdown() throws IOException {

        System.out.println("Terminating connection...");
        try {
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

