package web.spring.soap;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

//@Component
public class soaptest_CountryRepository {
    private static final Map<String, soaptest_Country> countries = new HashMap<>();

    //@PostConstruct
    public void initData() {
        soaptest_Country spain = new soaptest_Country();
        spain.setName("Spain");
        spain.setCapital("Madrid");
        //spain.setCurrency(Currency.EUR);
        spain.setPopulation(46704314);

        countries.put(spain.getName(), spain);

        soaptest_Country poland = new soaptest_Country();
        poland.setName("Poland");
        poland.setCapital("Warsaw");
        //poland.setCurrency(Currency.PLN);
        poland.setPopulation(38186860);

        countries.put(poland.getName(), poland);

        soaptest_Country uk = new soaptest_Country();
        uk.setName("United Kingdom");
        uk.setCapital("London");
        //uk.setCurrency(Currency.GBP);
        uk.setPopulation(63705000);

        countries.put(uk.getName(), uk);
    }

    public soaptest_Country findCountry(String name) {
        Assert.notNull(name, "The country's name must not be null");
        return countries.get(name);
    }
}
