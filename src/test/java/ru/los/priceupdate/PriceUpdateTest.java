package ru.los.priceupdate;

import org.junit.Test;
import ru.los.priceupdate.entities.Price;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.los.priceupdate.PriceMerge.updatePrice;

public class PriceUpdateTest {

    @Test
    public void testUpdatePriceForOneInitialIntervalCross() {

        //Попадание разделяющего интервала новой цены в один интервал
        List<Price> priceList = new ArrayList<Price>() {{
            add(new Price("12284", 1, 2, Date.valueOf("2013-01-01"), Date.valueOf("2013-01-31"), 350));
        }};
        List<Price> newpriceList = new ArrayList<Price>() {{
            add(new Price("12284", 1, 2, Date.valueOf("2013-01-05"), Date.valueOf("2013-01-15"), 200));
        }};
        List<Price> updatedList = updatePrice(priceList, newpriceList);


        assertThat(updatedList, hasItems(
                new Price("12284", 1, 2, Date.valueOf("2013-01-01"), Date.valueOf("2013-01-05"), 350),
                new Price("12284", 1, 2, Date.valueOf("2013-01-15"), Date.valueOf("2013-01-31"), 350),
                new Price("12284", 1, 2, Date.valueOf("2013-01-05"), Date.valueOf("2013-01-15"), 200)
        ));

    }

    @Test
    public void testUpdatePriceForTwoInitialsIntervalCross() {

        //Попадание разделяющего интервала новой цены в два изначальных интервала
        List<Price> priceList = new ArrayList<Price>() {{
            add(new Price("12284", 1, 2, Date.valueOf("2013-01-01"), Date.valueOf("2013-01-5"), 100));
            add(new Price("12284", 1, 2, Date.valueOf("2013-01-5"), Date.valueOf("2013-01-31"), 200));
        }};
        List<Price> newpriceList = new ArrayList<Price>() {{
            add(new Price("12284", 1, 2, Date.valueOf("2013-01-03"), Date.valueOf("2013-01-15"), 300));
        }};
        List<Price> updatedList = updatePrice(priceList, newpriceList);


        assertThat(updatedList, hasItems(
                new Price("12284", 1, 2, Date.valueOf("2013-01-01"), Date.valueOf("2013-01-03"), 100),
                new Price("12284", 1, 2, Date.valueOf("2013-01-03"), Date.valueOf("2013-01-15"), 300),
                new Price("12284", 1, 2, Date.valueOf("2013-01-15"), Date.valueOf("2013-01-31"), 200)
        ));

    }
}
