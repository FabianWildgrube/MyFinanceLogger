package financeLogger;

/**
 * Created by fabianwildgrube on 13/05/2017.
 * Part of MyFinanceLogger
 */
public enum Categories {

    TRANSPORTATION ("Transportmittel", "orange"),
    HOUSEHOLD ("Haushalt", "green"),
    FOOD ("Essen", "red"),
    ENTERTAINMENT ("Freizeit", "navy"),
    HOBBIES ("Hobbies", "purple");

    private String name;
    private String color;

    private Categories(String name, String color){
        this.name = name;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString(){
        return this.name;
    }


}
