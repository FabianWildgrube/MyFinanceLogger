package financeLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fabianwildgrube on 10/05/2017.
 * Part of MyFinanceLogger
 */
public class Model {

    private ArrayList<Income> einnahmen;
    private ArrayList<Expense> ausgaben;
    private File financelog_file;
    private double balance;

    public Model() {
        einnahmen = new ArrayList<Income>();
        ausgaben = new ArrayList<Expense>();

        this.financelog_file = new File(Params.FILENAME_INCOMES);

        if (!financelog_file.exists()) {
            //Nothing
        } else {
            readFinanceLog();
        }

//        this.einnahmen.add(new Income(500, Categories.FOOD, "Beschreibung", new Date()));
//        this.ausgaben.add(new Expense(Math.abs(23), Categories.FOOD, "Beschreibung", new Date()));

        balance = calculateEinnahmen() - calculateAusgaben();
    }


    public void addExpense(double value) {
        if (value > 0) {
            this.einnahmen.add(new Income(value, Categories.FOOD, "Beschreibung", new Date()));
        } else {
            this.ausgaben.add(new Expense(Math.abs(value), Categories.FOOD, "Beschreibung", new Date()));
        }
        this.balance = calculateEinnahmen() - calculateAusgaben();
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

    public Categories[] getCategories() {
        return Categories.values();
    }
}