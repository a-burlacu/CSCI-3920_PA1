package edu.ucdenver.server;

import edu.ucdenver.tournament.Tournament;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;

public class ClientWorker implements Runnable {

    private final Socket clientConnection; // the reference is not changing, but the state(content of the object) will
    private final String clientType;             // ADMIN or USER type
    private final int id;                        // number of client
    private Tournament tournament;
    private PrintWriter output;
    private BufferedReader input;
    private boolean keepRunningClient;

    //--------------------------------------------------
    //                  constructors
    //--------------------------------------------------
    public ClientWorker(Socket connection,Tournament tournament, String clientType, int id){

        this.clientConnection = connection;
        this.tournament = tournament;
        this.clientType = clientType;
        this.id = id;
        this.keepRunningClient = true;
    }


    //--------------------------------------------------
    //                  send message
    //--------------------------------------------------
    private void sendMessage(String message) {
        displayMessage("SERVER << " + message);
        this.output.println(message);
    }

    //--------------------------------------------------
    //                  display message
    //--------------------------------------------------
    private void displayMessage(String message) {
        System.out.printf("CLIENT[%s %d] >> %s%n", this.clientType,this.id, message);
    }


    //--------------------------------------------------
    //                   get output
    //--------------------------------------------------
    private void getOutputStream(Socket clientConnection) throws IOException {
        this.output = new PrintWriter(clientConnection.getOutputStream(), true);
    }


    //--------------------------------------------------
    //                   get input
    //--------------------------------------------------
    private void getInputStream(Socket clientConnection) throws IOException {
        this.input = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
    }


    //--------------------------------------------------
    //               close connection
    //--------------------------------------------------
    private void closeClientConnection() {
        // Try to close all input, output and socket
        try {
            this.input.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        try {
            this.output.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            this.clientConnection.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }


    //--------------------------------------------------
    //             process client request
    //--------------------------------------------------
    private void processClientRequest() throws IOException {

        String clientMessage = this.input.readLine(); //recv from client
        displayMessage("CLIENT SAID >> " + clientMessage);

        /*  ::: PROTOCOL :::
            determine if clientType = ADMIN or clientType = USER
            specify which actions to execute depending on type
            define functions and assign access
            //TODO: Set up clientType permissions to allow certain functions
         */

        String[] arglist = clientMessage.split("\\|"); // this splits the string using | as delimiter
        String response = ""; // This will be the response to the server

        try {
            switch(this.clientType) {
                case "A", "a", "ADMIN", "Admin", "admin":
                    switch (arglist[0]) {

                        case "1":       // load from file
                            Tournament.loadFromFile(arglist[1]);
                            response = "OK";
                            break;

                        case "2":       // save to file
                            tournament.saveToFile(arglist[1]);
                            response = "OK";
                            break;

                        case "3":       //create a new tournament
                            String[] start = arglist[2].split("-"); //"YYYY"-"MM-"DD"
                            LocalDate startDate = LocalDate.of(Integer.parseInt(start[0]), Integer.parseInt(start[1]), Integer.parseInt(start[2]));

                            String[] end = arglist[3].split("-"); //"YYYY"-"MM-"DD"
                            LocalDate endDate = LocalDate.of(Integer.parseInt(end[0]), Integer.parseInt(end[1]), Integer.parseInt(end[2]));

                            // not sure if this should be 'this.tournament' or just 'tournament' since it was never initialized in constructor
                            this.tournament = new Tournament(arglist[1], startDate, endDate);
                            response = "OK";
                            break;

                        case "4":       //add country
                            tournament.addCountry(arglist[1].toString());
                            response = "OK";
                            break;

                        case "5":       //add a national team representing a country
                            tournament.addTeam(arglist[1], arglist[2]);
                            response = "OK";
                            break;

                        case "6":       //add referee
                            tournament.addReferee(arglist[1], arglist[2]);
                            response = "OK";
                            break;

                        case "7":       // add a player to a national team squad
                            tournament.addPlayer(arglist[1], arglist[2], Integer.parseInt(arglist[3]),
                                    Double.parseDouble(arglist[4]), Double.parseDouble(arglist[5]));
                            response = "OK";
                            break;

                        case "8":       //add a match on a particular date between two national teams
                            String[] matchDate = arglist[1].split("-"); //"YYYY","MM,"DD"
                            LocalDate dateMatch = LocalDate.of(Integer.parseInt(matchDate[0]), Integer.parseInt(matchDate[1]), Integer.parseInt(matchDate[2]));
                            tournament.addMatch(dateMatch, arglist[2], arglist[3]);
                            response = "OK";
                            break;

                        case "9":       //assign a referee to a match
                            String[] refDate = arglist[1].split("-"); //"YYYY","MM,"DD"
                            LocalDate dateRef = LocalDate.of(Integer.parseInt(refDate[0]), Integer.parseInt(refDate[1]), Integer.parseInt(refDate[2]));
                            tournament.addRefereeToMatch(dateRef, arglist[2]);
                            response = "OK";
                            break;

                        case "10":      //add a player to a national teams lineup for a particular match
                            String[] lineupDate = arglist[1].split("-"); //"YYYY","MM,"DD"
                            LocalDate dateLineup = LocalDate.of(Integer.parseInt(lineupDate[0]), Integer.parseInt(lineupDate[1]), Integer.parseInt(lineupDate[2]));
                            tournament.addPlayerToMatch(dateLineup, arglist[2], arglist[3]);
                            response = "OK";
                            break;

                        case "11":      //record the score of a completed match
                            String[] scoreDate = arglist[1].split("-"); //"YYYY","MM,"DD"
                            LocalDate dateScore = LocalDate.of(Integer.parseInt(scoreDate[0]), Integer.parseInt(scoreDate[1]), Integer.parseInt(scoreDate[2]));
                            tournament.setMatchScore(dateScore, Integer.parseInt(arglist[2]), Integer.parseInt(arglist[3]));
                            response = "OK";
                            break;

                }

                case "U", "u", "USER", "User", "user":
                    switch (arglist[0]) {

                        case "1":        //get a list of the upcoming matches
                            tournament.getUpcomingMatches();
                            response = "OK";
                            break;

                        case "2":        //get a list of matches on a particular date
                            String[] matchDate = arglist[1].split("-"); //"YYYY","MM,"DD"
                            LocalDate dateMatch = LocalDate.of(Integer.parseInt(matchDate[0]), Integer.parseInt(matchDate[1]), Integer.parseInt(matchDate[2]));
                            tournament.getMatchesOn(dateMatch);
                            response = "OK";
                            break;


                        case "3":        // get a list of all games for a specific team
                            tournament.getMatchesFor(arglist[1]);
                            response = "OK";
                            break;

                        case "4":        //get the lineups for a match either past or future
                            String[] lineupDate = arglist[1].split("-"); //"YYYY","MM,"DD"
                            LocalDate dateLineup = LocalDate.of(Integer.parseInt(lineupDate[0]), Integer.parseInt(lineupDate[1]), Integer.parseInt(lineupDate[2]));
                            tournament.getMatchLineUps(dateLineup);
                            response = "OK";
                            break;
                    }
            }

        }catch (IllegalArgumentException iae) {
            response = "ERR| " + iae.getMessage();
        }
        this.sendMessage(response);

    }


    //--------------------------------------------------
    //               shutdown server
    //--------------------------------------------------
    private void forceShutdown() throws IOException {

        this.keepRunningClient = false;

        clientConnection.close();
    }


    //--------------------------------------------------
    //                  run server
    //--------------------------------------------------
    @Override
    public void run() {
        System.out.println("STATUS MSG: you've reached run() in ClientWorker");
        BufferedReader input;
        PrintWriter output;
        String newMessage;

        displayMessage("Getting Data Streams");
        try {
            getOutputStream(clientConnection);
            getInputStream(clientConnection);

            // Now we process the requests and send the responses
            sendMessage("Connected to simple Java Server");

            while (this.keepRunningClient) {
                processClientRequest();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            closeClientConnection();
        }
    }

}
