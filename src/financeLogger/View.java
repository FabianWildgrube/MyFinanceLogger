package financeLogger;

import com.sun.prism.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by fabianwildgrube on 10/05/2017.
 * Part of MyFinanceLogger
 */
public class View {

    private Stage primaryStage;

    public View(Stage primaryStage, Parent root) throws IOException{
        Scene scene = new Scene(root);
        this.primaryStage = primaryStage;
        this.primaryStage.setScene(scene);
        this.primaryStage.setTitle(Params.TITLE);
        this.primaryStage.show();
    }

    public void updateLabel(Label label, double value){
        label.setText(String.valueOf(value) + "€");
        if (value > 0){
            label.getStyleClass().add("positive");
        } else {
            label.getStyleClass().add("negative");
        }
    }

    public void updateScene(Parent root){
        Scene scene = new Scene(root);
        this.primaryStage.setScene(scene);
    }

    public double readInput(TextField txtfield) throws NullPointerException, NumberFormatException{
        String input = txtfield.getText();
        double input_value;
        input_value = Double.parseDouble(input);
        txtfield.setText("");
        return input_value;
    }

    public void showAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
