package edu.ucdenver.server;

import edu.ucdenver.app.AdminApp;
import edu.ucdenver.app.Controller;
import edu.ucdenver.app.UserApp;
import edu.ucdenver.tournament.Tournament;
import javafx.application.Application;
import javafx.application.Platform;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static javafx.application.Application.launch;

public class Server  {

    private final int port;
    private final int backlog; // how many clients can wait until they get connected
    private int connectionCounter; // keep track of how many clients are connected
    private String clientType;     // type of client (ADMIN or USER)

    private ServerSocket serverSocket;

//    private Tournament tournament;

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

//    public Server() {
//        this(9888, 10, "ADMIN", new Tournament(null,null,null ));
//    }

    public Server() {
        this(9888, 10, "ADMIN");
    }

    //--------------------------------------------------
    //              wait for connection
    //--------------------------------------------------

    private Socket waitForClientConnection() throws IOException {
        System.out.println("[SERVER] Waiting for client connection...");
        Socket clientConnection = this.serverSocket.accept();
        System.out.println("<status message: client connection socket created>");
        this.connectionCounter++;
        System.out.println("<status message: connection counter incremented>");
        System.out.printf("[SERVER] Connection: '[%s %d]' accepted from %s %n", clientType, connectionCounter,
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
            System.out.println("<status message: server socket created>");

//            launch(AdminApp.class);

            while (true) {

                try {
                    Socket clientConnection = this.waitForClientConnection();

//                    ClientWorker cw = new ClientWorker(clientConnection, tournament,  clientType, connectionCounter);
                    ClientWorker cw = new ClientWorker(clientConnection, clientType, connectionCounter);
                    System.out.println("<status message: ClientWorker object created>");
                    executorService.execute(cw);

                    System.out.println("<status message: new client thread initialized>");


                }
                catch (IOException ioe) {
                    System.out.println("\n[SERVER] Error accepting client connection.");
                    ioe.printStackTrace();
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

        System.out.println("\n[SERVER] Terminating server connection...\n");
        try {
            this.serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

