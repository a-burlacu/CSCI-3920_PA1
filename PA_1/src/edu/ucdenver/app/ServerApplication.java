package edu.ucdenver.app;

import edu.ucdenver.server.Server;
import edu.ucdenver.tournament.Tournament;

import java.util.Scanner;

import static javafx.application.Application.launch;

public class ServerApplication {
    //added application configuration for this file
    public static void main(String[] args) throws InterruptedException {
        //init Server object
        Server server = new Server();
        Tournament tournament = new Tournament(null, null, null);
        Boolean stillRunning = true;


        // display precursor message to menu for ~*~*aesthetic purposes*~*~
        System.out.println("\n<status message: initializing command line menu>\n");
        Thread.sleep(1000);


        while (stillRunning) {
            // create menu
            // TODO: Load Admin/User GUI using command line menu (launch client with ServerApp)
            try {
                // inform server of app type (ADMIN or USER)
                Scanner sc = new Scanner(System.in);
                System.out.println("Select client type:\n" +
                        "\t[A] ADMIN\n" +
                        "\t[U] USER");
                String type = sc.nextLine();


                outerloop:
                switch(type) {
                    //--------------------------------------------------
                    //              [A] admin permissions
                    //--------------------------------------------------

                    case "A":
                        type = "ADMIN";
                        Scanner adminSc = new Scanner(System.in);
                        System.out.println("Select option:\n" +
                                "\t[0] START SERVER\n" +
                                "\t[1] STOP SERVER\n" +
                                "\t[2] LOAD FROM FILE\n" +
                                "\t[3] SAVE TO FILE\n" +
                                "\t[4] BACK");

                        String adminChoice = adminSc.nextLine();


                        switch (adminChoice) {
                            //--------------------------------------------------
                            //                [0] start server
                            //--------------------------------------------------
                            case "0":
                                try {
                                    //server = new Server(9888, 10, type, tournament);
                                    server = new Server(9888, 10, type);

                                    server.runServer();

                                    // this calls the AdminApp start method
                                    launch(AdminApp.class, args);


                                } catch (Exception e) {
                                    System.err.print(e);
                                }
                                break;

                            //--------------------------------------------------
                            //                [1] stop server
                            //--------------------------------------------------
                            case "1":
                                try {
                                    server.stop();
                                    stillRunning = false;
                                } catch (Exception e) {
                                    System.err.print(e);
                                }
                                break;

                            //--------------------------------------------------
                            //             [2] load previous state
                            //--------------------------------------------------
                            case "2":
                                try {
                                    Tournament.loadFromFile("tournament.ser");
                                    //server = new Server(9888, 10, type, tournament);
                                    //server = new Server(9888, 10, type);
                                    System.out.println("\n<status message: loaded previous state>\n");
                                } catch (Exception e) {
                                    System.err.print(e);
                                }
                                break;

                            //--------------------------------------------------
                            //            [3] save current state
                            //--------------------------------------------------
                            case "3":
                                try {
                                    tournament.saveToFile("tournament.ser");
                                    System.out.println("\n<status message: saved current state>\n");
                                } catch (Exception e) {
                                    System.err.print(e);
                                }
                                break;
                            //--------------------------------------------------
                            //            [4] exit to main menu
                            //-------------------------------------------------
                            case "4":
                                break outerloop;

                            default:
                                System.out.println("ERROR| Invalid option selected. Retry.\n");
                                Thread.sleep(1000);
                                break outerloop;

                    }

                    //--------------------------------------------------
                    //              [U] user permissions
                    //--------------------------------------------------
                    case "U":
                        type = "USER";
                        Scanner userSc = new Scanner(System.in);
                        System.out.println("Select option:\n" +
                                "\t[0] START SERVER\n" +
                                "\t[1] STOP SERVER\n" +
                                "\t[2] BACK");

                        String userChoice = userSc.nextLine();

                        switch (userChoice) {
                            //--------------------------------------------------
                            //                [0] start server
                            //--------------------------------------------------
                            case "0":
                                try {
                                    //server = new Server(9888, 10, type, tournament);
                                    server = new Server(9888, 10, type);

                                    server.runServer();

                                    // this calls the AdminApp start method
                                    launch(AdminApp.class, args);


                                } catch (Exception e) {
                                    System.err.print(e);
                                }
                                break;

                            //--------------------------------------------------
                            //                [1] stop server
                            //--------------------------------------------------
                            case "1":
                                try {
                                    server.stop();
                                    stillRunning = false;
                                } catch (Exception e) {
                                    System.err.print(e);
                                }
                                break;
                            //--------------------------------------------------
                            //            [2] exit to main menu
                            //-------------------------------------------------
                            case "2":
                                break outerloop;

                            default:
                                System.out.println("\nERROR| Invalid option selected. Retry.\n");
                                Thread.sleep(1000);
                                break outerloop;
                        }

                }
            } catch (IllegalArgumentException iae) {
                System.out.println("ERROR| " + iae.getMessage());
            }

        }
    }
}
