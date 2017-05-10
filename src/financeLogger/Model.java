package financeLogger;

import java.io.*;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by fabianwildgrube on 10/05/2017.
 * Part of MyFinanceLogger
 */
public class Model{

    private ArrayList<Double> einnahmen;
    private ArrayList<Double> ausgaben;
    private File financelog_file;

    public Model(){
        einnahmen = new ArrayList<Double>();
        ausgaben = new ArrayList<Double>();

        this.financelog_file = new File(Params.FILENAME);

        if (!financelog_file.exists()){
            try {
                financelog_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(financelog_file));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    double amount = Double.parseDouble(line);
                    if(amount > 0.0){
                        einnahmen.add(amount);
                    } else {
                        ausgaben.add(Math.abs(amount));
                    }
                }
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            } catch (NumberFormatException e){
                System.err.format("NumberFormatException: %s%n", e);
            } finally {
                if (reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public void addExpense(double value){
        if (value > 0){
            this.einnahmen.add(value);
        } else {
            this.einnahmen.add(Math.abs(value));
        }
    }

    public double calculateEinnahmen(){
        double result = 0;
        for (double x : this.einnahmen){
            result += x;
        }
        return result;
    }

    public double calculateAusgaben(){
        double result = 0;
        for (double x : this.ausgaben){
            result += x;
        }
        return result;
    }

    public void saveFinanceLog(){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(financelog_file));
            String all_entries = "";
            for (Double x : this.einnahmen){
                String x_str = Double.toString(x);
                all_entries += x_str + "\n";
            }
            for (Double x : this.ausgaben){
                String x_str = Double.toString(-x);
                all_entries += x_str + "\n";
            }

            writer.write(all_entries, 0, all_entries.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        } finally {
            if (writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
