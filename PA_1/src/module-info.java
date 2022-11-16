module edu.ecdenver.pa_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;



    exports edu.ucdenver.app;
    opens edu.ucdenver.app to javafx.fxml;
    exports edu.ucdenver.server;
    opens edu.ucdenver.server to javafx.fxml;
    exports edu.ucdenver.tournament;
    opens edu.ucdenver.tournament to javafx.fxml;
}