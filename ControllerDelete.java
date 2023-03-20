package application.productapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDelete implements Initializable {
    private Stage stage;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void okHandler(ActionEvent actionEvent) {
    }

    @FXML
    public void cancelPartHandler(ActionEvent actionEvent) throws IOException {
        ControllerMain.delPartStage.close();
    }

    @FXML
    public void cancelProdHandler(ActionEvent actionEvent) throws IOException {
        ControllerMain.delProdStage.close();
    }

    @FXML
    public void cancelRemoveHandler(ActionEvent actionEvent) {
        ControllerAddProduct.removeAssocStage.close();
    }

    @FXML
    public void cancelModRemoveHandler(ActionEvent actionEvent) {
        ControllerModProduct.removeAssocStage.close();
    }
}
