package financeLogger;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fabianwildgrube on 13/05/2017.
 * Part of MyFinanceLogger
 *
 * Basisklasse fuer Einnahmen und Ausgaben
 */
public class BaseExpense implements Serializable{

    private double betrag;
    private Categories categorie;
    private String beschreibung;
    private Date datum;

    public BaseExpense(double betrag, Categories categorie, String beschreibung, Date datum) {
        this.betrag = betrag;
        this.categorie = categorie;
        this.beschreibung = beschreibung;
        this.datum = datum;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    public Categories getCategorie() {
        return categorie;
    }

    public void setCategorie(Categories categorie) {
        this.categorie = categorie;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
}
