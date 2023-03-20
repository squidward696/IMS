package application.productapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAddPart implements Initializable {

    public AnchorPane addPartStage;
    public Button saveBtn;
    public RadioButton inHouseRBtn, outsourcedRBtn;
    public ToggleGroup addPartRBtn;
    public TextField partIdTxt, partNameTxt, partInvTxt, partMinTxt, partPriceTxt, partMaxTxt, partMachineCompanyTxt;
    public Label partSource;
    private Parent root;
    private Stage stage;
    private static int uniquePartID = 4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void saveHandler(ActionEvent actionEvent) throws IOException {
        if (addPartRBtn.getSelectedToggle() == inHouseRBtn) {
            if (partMachineCompanyTxt == null) {
                Inventory.addPart(new InHouse(uniquePartID, partNameTxt.getText(), Double.parseDouble(partPriceTxt.getText()),
                        Integer.parseInt(partInvTxt.getText()), Integer.parseInt(partMinTxt.getText()),
                        Integer.parseInt(partMaxTxt.getText()), 000));
            }
            Inventory.addPart(new InHouse(uniquePartID, partNameTxt.getText(), Double.parseDouble(partPriceTxt.getText()),
                    Integer.parseInt(partInvTxt.getText()), Integer.parseInt(partMinTxt.getText()),
                    Integer.parseInt(partMaxTxt.getText()), Integer.parseInt(partMachineCompanyTxt.getText())));
        }
        if (addPartRBtn.getSelectedToggle() == outsourcedRBtn) {
            if (partMachineCompanyTxt == null) {
                Inventory.addPart(new Outsourced(uniquePartID, partNameTxt.getText(), Double.parseDouble(partPriceTxt.getText()),
                        Integer.parseInt(partInvTxt.getText()), Integer.parseInt(partMinTxt.getText()),
                        Integer.parseInt(partMaxTxt.getText()), null));
            }
            Inventory.addPart(new Outsourced(uniquePartID, partNameTxt.getText(), Double.parseDouble(partPriceTxt.getText()),
                    Integer.parseInt(partInvTxt.getText()), Integer.parseInt(partMinTxt.getText()),
                    Integer.parseInt(partMaxTxt.getText()), (partMachineCompanyTxt.getText())));

        }
        uniquePartID += 1;
        root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void cancelHandler(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void onInhouseSelected(ActionEvent actionEvent) {
        partSource.setText("Machine Id");
    }

    public void onOutsourcedSelected(ActionEvent actionEvent) {
        partSource.setText("Company Name");
    }
}
