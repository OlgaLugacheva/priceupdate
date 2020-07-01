package ru.los.priceupdate;

import ru.los.priceupdate.entities.Price;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PriceMerge {

    /**
     * @param oldPriceList the price list before updating
     * @param newPriceList the price list for insert
     * @return the merged prices list
     */
    static List<Price> updatePrice(List<Price> oldPriceList, List<Price> newPriceList) {
        List<Price> updatedList = new ArrayList<>();
        newPriceList.forEach(np -> {

                    List<Price> preparedList = Stream.concat(Stream.of(np), oldPriceList.stream()
                            .filter(op ->
                                    op.getProductCode().equals(np.getProductCode()) &&
                                            op.getNumber() == np.getNumber() && op.getDepart() == np.getDepart()))
                            .sorted(Comparator.comparing(Price::getBegin)).collect(Collectors.toList());

                    for (int i = 1; i < preparedList.size(); i++) {
                        Price price = preparedList.get(i);
                        Price prevPrice = preparedList.get(i - 1);
                        if (price.getEnd().compareTo(prevPrice.getEnd()) < 0) {
                            preparedList.add(new Price(price.getProductCode(), price.getNumber(), price.getDepart(), price.getEnd(), prevPrice.getEnd(), prevPrice.getValue()));
                            preparedList.sort(Comparator.comparing(Price::getBegin));
                        }
                        if (prevPrice.getEnd().compareTo(price.getBegin()) > 0) {

                            if (!prevPrice.equals(np)) {
                                prevPrice.setEnd(price.getBegin());
                                preparedList.set(i - 1, prevPrice);
                            } else {
                                price.setBegin(prevPrice.getEnd());
                                preparedList.set(i, price);
                            }
                        }


                    }
                    updatedList.addAll(preparedList);
                }
        );

        return updatedList;
    }

    public PriceMerge() {
    }
}
