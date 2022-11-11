package edu.ucdenver.app;

import edu.ucdenver.testing.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class Controller {

    public TextArea txtOutput;
    public TextField txtTestCmd;
    public Button btnTestSendCmd;

    Client client;

    public Controller(){
        client = new Client();
        client.connect();
    }

    public void testSendCmd(ActionEvent actionEvent) {
        String cmd = txtTestCmd.getText();
        Alert alert;

        if(client.isConnected()) {
            try {
                String response = client.sendRequest(cmd);
                String[] respArgs = response.split("\\|");

                switch (respArgs[0]){
                    case "OK":
                        alert = new Alert(Alert.AlertType.CONFIRMATION, "Server Response:" + response, ButtonType.OK);
                        alert.show();
                        break;
                    case "ERR":
                        alert = new Alert(Alert.AlertType.ERROR, respArgs[1], ButtonType.OK);
                        alert.show();
                        break;
                }
                txtOutput.clear();
                for (String s: respArgs){
                    txtOutput.appendText(s);
                    txtOutput.appendText(System.getProperty("line.separator"));
                }
            } catch (IOException e) {
                alert = new Alert(Alert.AlertType.ERROR, "Exception:" + e.getMessage(), ButtonType.OK);
                alert.show();
            }
        }
        else {
            alert = new Alert(Alert.AlertType.ERROR, "Client is not connected.", ButtonType.OK);
            alert.show();
        }
    }
}