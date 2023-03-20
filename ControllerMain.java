package application.productapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable {
    private Stage stage;
    public static Stage delPartStage, delProdStage;
    private Parent root;
    public Button addPartBtn, modPartBtn, delPartBtn, exitAppBtn, addProdBtn, modProdBtn, delProdBtn;
    public AnchorPane PartsTable, ProductsTable;
    public TableView<Part> partsTable;
    public TableView<Product> productsTable;
    public TableColumn partID, partName, partInventoryLvl, partCost, prodID, prodName, prodInventoryLvl, prodCost;
    public TextField searchPartTxt, searchProductTxt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());
        productsTable.setItems(Inventory.getAllProducts());

        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInventoryLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));

        prodID.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInventoryLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodCost.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * @param keyEvent on "enter" key pressed, searches parts and displays matches
     */
    @FXML
    public void searchPartsEnterKey(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            String x = searchPartTxt.getText();
            ObservableList<Part> parts = FXCollections.observableArrayList();
            ObservableList<Part> allParts = Inventory.getAllParts();

            for (Part p : allParts) {
                if (p.getName().contains(x.toString())) {
                    parts.add(p);
                }
            }

            if (parts.size() == 0) {
                try {
                    int id = Integer.parseInt(x);
                    for (Part p : allParts) {
                        if (p.getId() == id) {
                            if (p != null) {
                                parts.add(p);
                            }
                        }
                    }
                } catch (NumberFormatException e) {

                }
            }

            partsTable.setItems(parts);
            searchPartTxt.setText("");
        }
    }

    /**
     * @param keyEvent on "enter" key pressed, searches products and displays matches.
     *                 Different approach than searchPartsEnterKey. (ignores case)
     */
    @FXML
    public void searchProductsEnterKey(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            if (searchProductTxt == null || searchProductTxt.getText().toString().equals("")) {
                productsTable.setItems(Inventory.getAllProducts());
                return;
            }
            productsTable.setItems(Inventory.lookupProduct(searchProductTxt.getText().trim()));

            ObservableList<Product> ol = FXCollections.observableArrayList();
            try {
                for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
                    if (Inventory.lookupProduct(i).getId() == Integer.parseInt(searchProductTxt.getText().trim())) {
                        ol.add(Inventory.getAllProducts().get(i));
                    }
                    productsTable.setItems(ol);
                }
            } catch (NumberFormatException e) {
            }
            searchProductTxt.setText("");
        }
    }

    /**
     * @param actionEvent the exit button will end the program
     */
    @FXML
    public void exitHandler(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * @param actionEvent the selected part will have the option of being deleted
     * @throws IOException
     */
    @FXML
    public void partsDeleteHandler(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this part?");
        alert.setHeaderText("Delete");
        alert.showAndWait().filter(ButtonType.OK::equals).ifPresent(ButtonType -> {
            Inventory.deletePart((Part) partsTable.getSelectionModel().getSelectedItem());
        });
    }

    /**
     * @param actionEvent The selected part will enter the "modify part" form to be modified.
     *                    The selectedPart boolean will let us know if the part is InHouse or Outsourced.
     * @throws IOException
     */
    @FXML
    public void partsModifyHandler(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("mod-part-view.fxml"));
        root = loader.load();

        ControllerModPart controllerModPart = loader.getController();
        controllerModPart.passData(partsTable.getSelectionModel().getSelectedIndex(), partsTable.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * @param actionEvent Loads the stage with "add parts" form to add a new part
     * @throws IOException
     */
    @FXML
    public void partsAddHandler(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("add-parts-view.fxml"));
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * @param actionEvent the selected product will have the option of being deleted
     * @throws IOException
     */
    @FXML
    public void prodDeleteHandler(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this product?");
        alert.setHeaderText("Delete");
        alert.showAndWait().filter(ButtonType.OK::equals).ifPresent(ButtonType -> {
            Inventory.deleteProduct((Product) productsTable.getSelectionModel().getSelectedItem());
        });
    }

    @FXML
    public void prodModifyHandler(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("mod-product-view.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * @param actionEvent Loads the stage with "add product" form to add a new product
     * @throws IOException
     */
    @FXML
    public void prodAddHandler(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("add-product-view.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}