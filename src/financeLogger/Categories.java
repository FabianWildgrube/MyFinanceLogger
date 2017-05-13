package financeLogger;

/**
 * Created by fabianwildgrube on 13/05/2017.
 * Part of MyFinanceLogger
 */
public enum Categories {

    TRANSPORTATION ("Transportation"),
    HOUSEHOLD ("Household"),
    FOOD ("Food"),
    ENTERTAINMENT ("Entertainment"),
    HOBBIES ("Hobbies");

    private String name;

    private Categories(String name){
        this.name = name;
    }
}
