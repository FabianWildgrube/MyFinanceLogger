package financeLogger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    Controller c1;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Model model = new Model();
        View view = new View();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("financeLogger.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(Params.TITLE);
        primaryStage.show();

        c1 = loader.<Controller>getController();
        c1.setModel(model);
        c1.setView(view);
    }

    @Override
    public void stop(){
        c1.saveData();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
