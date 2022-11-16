package edu.ucdenver.app;

import edu.ucdenver.server.Server;
import edu.ucdenver.tournament.Tournament;

import java.util.Scanner;

public class ServerApplication {
    //added application configuration for this file

    public static void main(String[] args) {
        //init Server object
        Server server = new Server();
        Tournament tournament = new Tournament(null, null, null);
        Boolean stillRunning = true;

        //TODO: create option menu to display in console via text

        while (stillRunning) {
            // create menu
            try {

                Scanner sc = new Scanner(System.in);
                System.out.println("Select option\n[0] START SERVER\n[1] STOP SERVER\n[2] LOAD FROM FILE\n[3] SAVE TO FILE");
                String choice = sc.nextLine();

                // inform server of app type (ADMIN or USER)
                Scanner sc1 = new Scanner(System.in);
                System.out.println("Select client type:\n[A] ADMIN\n[U] USER\n");
                String type = sc1.nextLine();

                switch (choice) {

                    //--------------------------------------------------
                    //                [0] start server
                    //--------------------------------------------------
                    case "0":
                        try {
                            server = new Server(9888, 10, type, tournament);
                            server.run();

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
                            server = new Server(9888, 10, type, tournament);
                            System.out.println("Loaded previous state.\n");
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
                            System.out.println("Saved current state.\n");
                        } catch (Exception e) {
                            System.err.print(e);
                        }
                        break;
                }
            } catch (IllegalArgumentException iae) {
                System.out.println("ERROR| " + iae.getMessage());
            }
        }
    }
}
