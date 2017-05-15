package financeLogger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by fabianwildgrube on 14/05/2017.
 * Part of MyFinanceLogger
 */
public class InputController implements Initializable {

    private Model model;
    private View view;
    private boolean input_positive_flag = true; //Flag, ob der Input als Einnahme gewertet werden soll

    @FXML
    private TextField datum_in;

    @FXML
    private TextField betrag_in;

    @FXML
    private TextField kategorie_in;

    @FXML
    private VBox neuer_betrag_box;

    @FXML
    private Accordion categories_accordion;


    public void setModel(Model model){
        this.model = model;
    }

    public void setView(View view){
        this.view = view;
        updateView();
    }

    public void updateView(){
        this.view.updateTextField(this.datum_in, (new Date()).toString());
        updateCategoryAccordion();
    }

    private void updateCategoryAccordion() {
       Categories[] mainCategories = Categories.values();
       int j = 0;
       for (Categories mainCat : mainCategories){

           ObservableList subCats = FXCollections.observableArrayList();
           ListView listView = new ListView(subCats);
           for (int i = 0; i < Params.NR_OF_SUBCATS; i++) {
               subCats.add(this.model.getSubcategories()[j][i].getBeschreibung());
           }
           listView.setItems(subCats);
           TitledPane titledPane = new TitledPane(mainCat.toString(), listView);
           this.categories_accordion.getPanes().add(titledPane);
           this.categories_accordion.setExpandedPane(categories_accordion.getPanes().get(0));
           j++;
       }
    }

    @FXML
    public void clearInput(MouseEvent event){
        this.view.clearTextField((TextField)event.getSource());
    }

    @FXML
    public void setInput_positive_flag_true(){
        this.input_positive_flag = true;
        this.view.changeStyleClass(this.neuer_betrag_box, "negative_bg", "positive_bg");
    }

    @FXML
    public void setInput_positive_flag_false(){
        this.input_positive_flag = false;
        this.view.changeStyleClass(this.neuer_betrag_box,"positive_bg", "negative_bg");
    }


    @FXML
    protected void saveInput() {
        String infotext = "";
        try {
            double new_betrag = view.readInput_double(this.betrag_in, "Betrag");
            Date new_date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(view.readInput_string(datum_in, new Date().toString()));
            String kategorie = view.readInput_string(this.kategorie_in, "Kategorie");
            if (kategorie.equals("Kategorie") || kategorie.equals("")){
                kategorie = "Default";
            }

            if (this.input_positive_flag) {
                model.addExpense(Math.abs(new_betrag), kategorie, new_date);
            } else {
                model.addExpense(-Math.abs(new_betrag), kategorie, new_date);
            }
            infotext = "Der Betrag von " + new_betrag + " wurde erfolgreich gespeichert";
            view.showInfoDialog("Erfolgreich gespeichert", "Die Ausgabe wurde erfolgreich gespeichert", infotext);
        } catch (NumberFormatException e){
            view.showAlert("Sie haben keine gültige Zahl eingegeben",
                    "Geben Sie im Betragsfeld bitte eine gültige Zahl ein",
                    "Zahlen müssen entweder positiv oder negativ sein und dem Format 123.34 folgen.");
        } catch (NullPointerException e){
            view.showAlert("Sie haben keine Zahl eingegeben",
                    "Geben Sie bitte mind. eine Ziffer ein!",
                    "Das Feld New Expense darf nicht leer sein, da sie ja dann keine neue Ausgabe hinzufügen!");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void go_to_home(){
        this.view.showHome();
    }

    @FXML
    protected void go_to_stats(){
        this.view.showStats();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
