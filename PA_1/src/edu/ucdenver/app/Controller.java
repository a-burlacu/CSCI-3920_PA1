package edu.ucdenver.app;

import edu.ucdenver.server.Client;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public TextField txtNewTournamentName;
    public Button btnNewTournamentSubmit;
    public DatePicker dtpNewTournamentStartDate;
    public DatePicker dtpNewTournamentEndDate;
    public TextField txtAddCountry;
    public Button btnAddCountrySubmit;
    public TextField txtAddTeamName;
    public Button btnAddTeam;
    public ComboBox selAddTeamCountry;
    Client client;

    private List<String> countries;



    public Controller() {
        client = new Client();
        client.connect();
        this.countries = new ArrayList<>();
        this.selAddTeamCountry = new ComboBox();
    }

    private String sendCommand(String cmd) {
        String response;
        if (client.isConnected()) {
            try {
                response = client.sendRequest(cmd);

            } catch (IOException e) {
                response = "ERR|" + e.getMessage();

            }
        } else {
            response = "ERR| Client is not connected.";
        }
        return response;
    }

    public void createTournament(ActionEvent actionEvent) {
        String tournamentNameText = this.txtNewTournamentName.getText();
        LocalDate tournamentStartDate = this.dtpNewTournamentStartDate.getValue();
        LocalDate tournamentEndDate = this.dtpNewTournamentEndDate.getValue();
        Alert alert;

        String cmd = String.format("%s|%s|%s|%s","3",tournamentNameText, tournamentStartDate.toString(), tournamentEndDate.toString());
        String response = sendCommand(cmd);
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
    }

    public void addCountry(ActionEvent actionEvent) {
        String countryNameText = this.txtAddCountry.getText();
        Alert alert;

        String cmd = String.format("%s|%s","4",countryNameText);
        String response = sendCommand(cmd);
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
    }

    public void addTeam(ActionEvent actionEvent) {
        selAddTeamCountry.setItems(FXCollections.observableArrayList(countries));
        String countryNameSelection = this.selAddTeamCountry.getValue().toString();
        String teamNameText = this.txtAddTeamName.getText();
        Alert alert;

        String cmd = String.format("%s|%s|%s","5", countryNameSelection, teamNameText);
        String response = sendCommand(cmd);
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
    }
}