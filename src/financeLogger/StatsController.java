package financeLogger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Created by fabianwildgrube on 14/05/2017.
 * Part of MyFinanceLogger
 */
public class StatsController implements Initializable, Observer {

    private Model model;
    private View view;
    private BarChart<String, Number> expenseBarchart;
    private XYChart.Series<String, Number> expenseBarData;
    private BarChart<String, Number> incomeBarchart;
    private XYChart.Series<String, Number> incomeBarData;
    private PieChart pieChart;
    private ObservableList<PieChart.Data> pieChartData;

    @FXML
    private ListView chartSelectionList;

    @FXML
    private HBox chartHolderPane;

    public void setModel(Model model){
        this.model = model;
    }

    public void setView(View view){
        this.view = view;

        //Ausgaben Säulendiagramm
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        this.expenseBarchart = new BarChart<String, Number>(xAxis, yAxis);
        this.expenseBarchart.setTitle("Ausgaben nach Kategorie");
        xAxis.setLabel("Kategorien");
        yAxis.setLabel("Ausgaben in €");
        yAxis.setAnimated(false);
        this.expenseBarchart.setLegendVisible(false);
        setExpenseBarData();

        //Einnahmen Säulendiagramm
        CategoryAxis xAxis2 = new CategoryAxis();
        NumberAxis yAxis2 = new NumberAxis();
        this.incomeBarchart = new BarChart<String, Number>(xAxis2, yAxis2);
        this.incomeBarchart.setTitle("Einnahmen nach Kategorie");
        xAxis2.setLabel("Kategorien");
        yAxis2.setLabel("Einnahmen in €");
        yAxis2.setAnimated(false);
        this.incomeBarchart.setLegendVisible(false);
        setIncomeBarData();

        //Kreisdiagramm
        this.pieChart = new PieChart();
        this.pieChart.setLabelsVisible(false);
        this.pieChart.setTitle("Einnahmen v/s Ausgaben");
        setPieChartData();

        this.chartHolderPane.setFillHeight(true);

        ObservableList graphChoices = FXCollections.observableArrayList();
        graphChoices.add("Einnahmen v/s Ausgaben (Torte)");
        graphChoices.add("Ausgaben nach Kategorie (Säulen)");
        graphChoices.add("Einnahmen nach Kategorie (Säule)");
        graphChoices.add("Gesamtvermögen Jahresverlauf (Linie)");

        this.chartSelectionList.setItems(graphChoices);

        this.chartSelectionList.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String listcell = chartSelectionList.getSelectionModel().getSelectedItem().toString();
                switch (listcell){
                    case "Einnahmen v/s Ausgaben (Torte)":
                        showPieChart();
                        break;
                    case "Ausgaben nach Kategorie (Säulen)":
                        showExpenseBarChart();
                        break;
                    case "Einnahmen nach Kategorie (Säule)":
                        showIncomeBarChart();
                        break;
                }
            }
        });

        this.chartSelectionList.getSelectionModel().select(0);
        showPieChart();

    }

    protected void showPieChart(){
        if (this.chartHolderPane.getChildren().size() > 0){
            this.chartHolderPane.getChildren().remove(0);
        }
        this.chartHolderPane.getChildren().add(this.pieChart);

    }

    private void setPieChartData() {
        double einnahmen = model.calculateEinnahmen();
        double ausgaben =  model.calculateAusgaben();

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Einnahmen", einnahmen),
                new PieChart.Data("Ausgaben", Math.abs(ausgaben))
        );

        this.pieChartData = pieData;
        this.pieChart.setData(this.pieChartData);
    }

    protected void showExpenseBarChart(){
        if (this.chartHolderPane.getChildren().size() > 0){
            this.chartHolderPane.getChildren().remove(0);
        }
        this.chartHolderPane.getChildren().add(this.expenseBarchart);
    }

    private void setExpenseBarData(){
        ArrayList<Expense> ausgaben =  model.getAusgaben();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Expense ausgabe : ausgaben){
            XYChart.Data<String, Number> datapoint = new XYChart.Data<String, Number>(ausgabe.getCategorie().toString(), Math.abs(ausgabe.getBetrag()));
            setBarColor(datapoint, ausgabe);
            series.getData().add(datapoint);
        }
        this.expenseBarData = series;
        if (this.expenseBarchart.getData().size() > 0){
            this.expenseBarchart.getData().remove(0);
        }
        this.expenseBarchart.getData().add(this.expenseBarData);
    }

    protected void showIncomeBarChart(){
        if (this.chartHolderPane.getChildren().size() > 0){
            this.chartHolderPane.getChildren().remove(0);
        }
        this.chartHolderPane.getChildren().add(this.incomeBarchart);
    }

    private void setIncomeBarData(){
        ArrayList<Income> einnahmen =  model.getEinnahmen();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Income einnahme : einnahmen){
            XYChart.Data<String, Number> datapoint = new XYChart.Data<String, Number>(einnahme.getCategorie().toString(), Math.abs(einnahme.getBetrag()));
            setBarColor(datapoint, einnahme);
            series.getData().add(datapoint);
        }
        this.incomeBarData = series;
        if (this.incomeBarchart.getData().size() > 0){
            this.incomeBarchart.getData().remove(0);
        }
        this.incomeBarchart.getData().add(this.incomeBarData);
    }

    private void setBarColor(XYChart.Data<String, Number> datapoint, BaseExpense betrag){
        datapoint.nodeProperty().addListener(new ChangeListener<Node>() {
            @Override
            public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                newValue.setStyle("-fx-bar-fill:" + betrag.getCategorie().getColor() + ";");
            }
        });
    }

    @FXML
    protected void go_to_input(){
        this.view.showInput();
    }

    @FXML
    protected void go_to_home(){
        this.view.showHome();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void update(Observable o, Object arg){
        setPieChartData();
        setExpenseBarData();
        setIncomeBarData();
    }
}
