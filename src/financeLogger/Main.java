package financeLogger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    HomeController home_controller;
    InputController input_Controller;
    StatsController stats_Controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Model model = new Model();

        //Initialize Home
        FXMLLoader home_loader = new FXMLLoader(getClass().getResource("fxml/financeLogger.fxml"));
        Scene home_sc = new Scene (home_loader.load());
        home_controller = home_loader.<HomeController>getController();
        home_controller.setModel(model);
        model.addObserver(home_controller);


        //Initialize Input
        FXMLLoader input_loader = new FXMLLoader(getClass().getResource("fxml/input.fxml"));
        Scene input_sc = new Scene(input_loader.load());
        input_Controller = input_loader.<InputController>getController();
        input_Controller.setModel(model);


        //Initialize Stats
        FXMLLoader stats_loader = new FXMLLoader(getClass().getResource("fxml/stats.fxml"));
        Scene stats_sc = new Scene(stats_loader.load());
        stats_Controller = stats_loader.<StatsController>getController();
        stats_Controller.setModel(model);
        model.addObserver(stats_Controller);

        //Create View
        View view = new View(primaryStage, home_sc, input_sc, stats_sc);
        home_controller.setView(view);
        input_Controller.setView(view);
        stats_Controller.setView(view);
    }

    @Override
    public void stop(){
        home_controller.saveData();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
