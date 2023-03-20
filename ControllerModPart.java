package application.productapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerModPart implements Initializable {
    private static int selectedRow;
    private static Part p;
    public RadioButton inHouseModRBtn, outsourcedModRBtn;
    public Button saveBtn;
    public TextField modPartIdTxt, modPartNameTxt, modPartInvTxt, modPartPriceTxt, modPartMaxTxt, modPartMinTxt, modPartMachCompTxt;
    public Label partSource;
    private Parent root;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        p = Inventory.lookupPart(selectedRow);
    }

    public void passData(int selectedIndex, Part part) {
        selectedRow = selectedIndex;
        if (part instanceof Outsourced) {
            outsourcedModRBtn.setSelected(true);
            partSource.setText("Company Name");
            modPartMachCompTxt.setText(((Outsourced) part).getCompanyName());
        } else {
            inHouseModRBtn.setSelected(true);
            partSource.setText("Machine Id");
            modPartMachCompTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
        }
        modPartIdTxt.setText(Integer.toString(part.getId()));
        modPartNameTxt.setText(part.getName());
        modPartPriceTxt.setText(Double.toString(part.getPrice()));
        modPartInvTxt.setText(Integer.toString(part.getStock()));
        modPartMaxTxt.setText(Integer.toString(part.getMax()));
        modPartMinTxt.setText(Integer.toString(part.getMin()));
    }

    @FXML
    public void saveHandler(ActionEvent actionEvent) throws IOException {
        p.setId(Integer.parseInt(modPartIdTxt.getText()));
        p.setName(modPartNameTxt.getText());
        p.setPrice(Double.parseDouble(modPartPriceTxt.getText()));
        p.setStock(Integer.parseInt(modPartInvTxt.getText()));
        p.setMin(Integer.parseInt(modPartMinTxt.getText()));
        p.setMax(Integer.parseInt(modPartMaxTxt.getText()));

        if (inHouseModRBtn.isSelected()) {
            ((InHouse) p).setMachineId(Integer.parseInt(modPartMachCompTxt.getText()));

            if (modPartMachCompTxt == null) {
                ((InHouse) p).setMachineId(000);
            }
            if (p.getMax() < p.getMin()) {
                throw new IOException("Min value is higher than the max");
            }
            if (p.getStock() < p.getMin() || p.getStock() > p.getMax()) {
                throw new IOException("Inventory is outside the min/max range");
            }
            Inventory.updatePart(Inventory.getAllParts().indexOf(p), p);

        } else if (outsourcedModRBtn.isSelected()) {
            ((Outsourced) p).setCompanyName(modPartMachCompTxt.getText());
            if (modPartMachCompTxt == null) {
                ((Outsourced) p).setCompanyName(null);
            }
            if (p.getMax() < p.getMin()) {
                throw new IOException("Min value is higher than the max");
            }
            if (p.getStock() < p.getMin() || p.getStock() > p.getMax()) {
                throw new IOException("Inventory is outside the min/max range");
            }
            Inventory.updatePart(selectedRow, p);
        }

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
