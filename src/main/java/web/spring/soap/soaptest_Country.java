package web.spring.soap;


import java.util.Currency;

public class soaptest_Country {
    String name;
    int population;
    String capital;
    Currency currency;

    public int getPopulation() {
        return population;
    }

    public String getCapital() {
        return capital;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
