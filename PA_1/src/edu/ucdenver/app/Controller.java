package edu.ucdenver.app;

import edu.ucdenver.server.Client;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.io.IOException;

public class Controller {
    Client client;

    public Controller() {
        client = new Client();
        client.connect();
    }
}