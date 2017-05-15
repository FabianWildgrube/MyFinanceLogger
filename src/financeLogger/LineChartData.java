package financeLogger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fabianwildgrube on 14/05/2017.
 * Part of MyFinanceLogger
 */
public class LineChartData {

    private String daterepr;
    private Date datum;
    private double balance;

    public LineChartData(Date datum, double balance) {
        this.datum = datum;
        this.balance = balance;

        SimpleDateFormat df = new SimpleDateFormat("dd. MMM hh:mm");
        this.daterepr = df.format(this.datum);
    }

    public String getDaterepr() {
        return daterepr;
    }

    public double getBalance() {
        return balance;
    }

    public Date getDatum() {
        return datum;
    }
}
