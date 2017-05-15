package financeLogger;

import java.io.*;
import java.util.*;

/**
 * Created by fabianwildgrube on 10/05/2017.
 * Part of MyFinanceLogger
 */
public class Model extends Observable{

    private ArrayList<Income> einnahmen;
    private ArrayList<Expense> ausgaben;
    private File financelog_file;
    private double balance;
    private LineChartData newest_datapoint = null;
    private Subcategory[][] subcategories;


    public Model() {
        einnahmen = new ArrayList<Income>();
        ausgaben = new ArrayList<Expense>();

        this.financelog_file = new File(Params.FILENAME_INCOMES);

        if (!financelog_file.exists()) {
            //Nothing
        } else {
            readFinanceLog();
        }

//        Calendar c1 = new GregorianCalendar();
//        c1.set(2017, 4, 25);
//        Date datum = c1.getTime();
//        this.einnahmen.add(new Income(340, Categories.ENTERTAINMENT, "Beschreibung", datum));
//       this.ausgaben.add(new Expense(Math.abs(780), Categories.FOOD, "Beschreibung", datum));

        balance = calculateEinnahmen() + calculateAusgaben();

        //Quick and dirty!!!
        subcategories = new Subcategory[Categories.values().length][Params.NR_OF_SUBCATS];
        int j = 0;
        for (Categories mainCat : Categories.values()){
            for (int i = 0; i < Params.NR_OF_SUBCATS; i++) {
                subcategories[j][i] = new Subcategory(mainCat, "Subcat " + i);
            }
            j++;
        }
    }


    public void addExpense(double value, String beschreibung, Date datum) {
        if (value > 0) {
            this.einnahmen.add(new Income(value, Categories.ENTERTAINMENT, beschreibung, datum));
        } else {
            this.ausgaben.add(new Expense(Math.abs(value), Categories.HOUSEHOLD, "Beschreibung", new Date()));
        }
        this.balance = calculateEinnahmen() + calculateAusgaben();
        newest_datapoint = new LineChartData(datum, this.balance);
        setChanged();
        notifyObservers();
    }

    public double calculateEinnahmen() {
        double result = 0;
        for (BaseExpense income : this.einnahmen) {
            result += income.getBetrag();
        }
        return result;
    }

    public double calculateAusgaben() {
        double result = 0;
        for (BaseExpense expense : this.ausgaben) {
            result += expense.getBetrag();
        }
        return result;
    }

    public double getTotalBalance() {
        return this.balance;
    }

    public LineChartData getNewest_datapoint() {
        return newest_datapoint;
    }

    public void saveFinanceLog() {

        FileOutputStream fout = null;
        ObjectOutputStream oos = null;

        try {
            fout = new FileOutputStream(Params.FILENAME_INCOMES);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(this.einnahmen);

            fout = new FileOutputStream(Params.FILENAME_EXPENSES);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(this.ausgaben);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void readFinanceLog() {
        ObjectInputStream objectinputstream = null;
        FileInputStream streamIn = null;
        try {
            streamIn = new FileInputStream(Params.FILENAME_INCOMES);
            objectinputstream = new ObjectInputStream(streamIn);
            this.einnahmen = (ArrayList<Income>) objectinputstream.readObject();

            streamIn = new FileInputStream((Params.FILENAME_EXPENSES));
            objectinputstream = new ObjectInputStream((streamIn));
            this.ausgaben = (ArrayList<Expense>) objectinputstream.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectinputstream != null) {
                try {
                    objectinputstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public ArrayList<Income> getEinnahmen() {
        return einnahmen;
    }

    public ArrayList<Expense> getAusgaben() {
        return ausgaben;
    }

    public Categories[] getCategories() {
        return Categories.values();
    }

    public Subcategory[][] getSubcategories() {
        return subcategories;
    }

    private ArrayList<BaseExpense> getDateSortedBetraege(){
        ArrayList<BaseExpense> result = new ArrayList<>();


        for (Income income : this.einnahmen){
            result.add(income);
        }
        for (Expense expense : this.ausgaben){
            result.add(expense);
        }

        result.sort(new Comparator<BaseExpense>() {
            @Override
            public int compare(BaseExpense o1, BaseExpense o2) {
                return o1.getDatum().compareTo(o2.getDatum());
            }
        });

        return result;
    }

    public ArrayList<LineChartData> getDataPoints(){
        ArrayList<LineChartData> result= new ArrayList<LineChartData>();

        ArrayList<BaseExpense> betraege = getDateSortedBetraege();

        double current_balance = 0.0;
        for (BaseExpense betrag : betraege) {
            current_balance += betrag.getBetrag();
            LineChartData dataPoint = new LineChartData(betrag.getDatum(), current_balance);
            result.add(dataPoint);
        }

        return result;
    }
}