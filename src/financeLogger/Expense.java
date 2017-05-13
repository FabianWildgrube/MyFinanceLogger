package financeLogger;

import java.util.Date;

/**
 * Created by fabianwildgrube on 13/05/2017.
 * Part of MyFinanceLogger
 */
public class Expense extends BaseExpense {
    public Expense(double betrag, Categories categorie, String beschreibung, Date datum) {
        super(betrag, categorie, beschreibung, datum);
    }

    public double getBetrag() {
        return (Math.abs(super.getBetrag()));
    }
}
