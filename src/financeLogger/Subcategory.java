package financeLogger;

/**
 * Created by fabianwildgrube on 14/05/2017.
 * Part of MyFinanceLogger
 */
public class Subcategory {

    private Categories mainCategory;
    private String beschreibung;

    public Subcategory(Categories mainCategory, String beschreibung) {
        this.mainCategory = mainCategory;
        this.beschreibung = beschreibung;
    }

    public Categories getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(Categories mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}
