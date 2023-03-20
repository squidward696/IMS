package application.productapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    public static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Program");
        stage.setScene(scene);
        stage.show();

        mainStage = stage;
    }

    public static void main(String[] args) {

        Inventory.addPart(new InHouse(1, "Tire", 19.99, 16, 1, 30, 3456));
        Inventory.addPart(new InHouse(2, "Brakes", 200.01, 2, 1, 12, 6575));
        Inventory.addPart(new Outsourced(3, "Chain", 45.98, 5, 0, 10, "Factory"));

        Inventory.addProduct(new Product(1, "Mtn Bike", 299.99, 6, 0, 10));
        Inventory.addProduct(new Product(2, "Road Bike", 679.99, 2, 1, 15));

        launch();
    }
}