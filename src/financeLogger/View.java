package financeLogger;

import com.sun.prism.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by fabianwildgrube on 10/05/2017.
 * Part of MyFinanceLogger
 */
public class View {

    private Stage primaryStage;
    private Scene home;
    private Scene input;
    private Scene stats;

    public View(Stage primaryStage, Scene home, Scene input, Scene stats) throws IOException{
        this.primaryStage = primaryStage;
        this.home = home;
        this.input = input;
        this.stats = stats;
        this.primaryStage.setTitle(Params.TITLE);
        this.primaryStage.setScene(this.home);
        this.primaryStage.show();
    }


    public void showHome(){
        this.primaryStage.setTitle(Params.TITLE + " - Home");
        this.primaryStage.setScene(this.home);
    }

    public void showInput() {
        this.primaryStage.setTitle(Params.TITLE + " - Neuer Betrag");
        this.primaryStage.setScene(this.input);
    }

    public void showStats(){
        this.primaryStage.setTitle(Params.TITLE + " - Statistiken");
        this.primaryStage.setScene(this.stats);
    }

    public void updateLabel(Label label, double value){
        DecimalFormat df = new DecimalFormat("#0.00");
        label.setText(df.format(value) + "€");
        if (value > 0){
            label.getStyleClass().add("positive");
        } else {
            label.getStyleClass().add("negative");
        }
    }

    public void updateTextField(TextField textField, String value){
        textField.setText(value);
    }

    public void clearTextField(TextField textField){
        textField.setText("");
    }

    public double readInput_double(TextField txtfield, String newtText) throws NullPointerException, NumberFormatException{
        String input = txtfield.getText();
        double input_value;
        input_value = Double.parseDouble(input);
        txtfield.setText(newtText);
        return input_value;
    }

    public void changeStyleClass(Node node, String oldclassname, String newclassname){
        int index_old = node.getStyleClass().indexOf(oldclassname);
        if (index_old != -1){
            node.getStyleClass().remove(index_old);
            node.getStyleClass().add(newclassname);
        }
    }

    public String readInput_string(TextField txtfield, String newText) throws NullPointerException {
        String input = txtfield.getText();
        txtfield.setText(newText);
        return input;
    }

    public void showAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public void showInfoDialog(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
