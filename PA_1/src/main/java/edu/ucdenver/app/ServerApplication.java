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

        //TODO: create option menu to display in console via text

        Scanner sc = new Scanner(System.in);
        System.out.println("""
                Select option:\s
                [0] START SERVER
                [1] STOP SERVER
                [2] LOAD FROM FILE
                [3] SAVE TO FILE
                """);
        String choice = sc.nextLine();

        // inform server of app type (ADMIN or USER)
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Select client type:\n[A] ADMIN\n[U] USER\n");
        String type = sc1.nextLine();

        // create menu
        try {
            switch (choice) {

                //--------------------------------------------------
                //                [0] start server
                //--------------------------------------------------
                case "0":
                    try {
                        server = new Server(9888, 10, type, tournament);
                        server.runServer();

                    } catch (Exception e) {
                        System.err.print(e);
                    }


                //--------------------------------------------------
                //                [1] stop server
                //--------------------------------------------------
                case "1":
                    try {
                        server.shutdown();
                    } catch (Exception e) {
                        System.err.print(e);
                    }


                //--------------------------------------------------
                //             [2] load previous state
                //--------------------------------------------------
                case "2":
                    try {
                        Tournament.loadFromFile("tournament.ser");
                        server = new Server(9888, 10, type, tournament);
                    } catch (Exception e) {
                        System.err.print(e);
                    }

                //--------------------------------------------------
                //            [3] save current state
                //--------------------------------------------------
                case "3":
                    tournament.saveToFile("tournament.ser");
            }
        }
        catch (IllegalArgumentException iae) {
                System.out.println("ERROR| " + iae.getMessage());
        }


    }

}
