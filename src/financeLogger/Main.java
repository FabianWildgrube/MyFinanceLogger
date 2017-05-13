package financeLogger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {

    Controller c1;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Model model = new Model();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/financeLogger.fxml"));
        Parent root = loader.load();
        View view = new View(primaryStage, root);


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
