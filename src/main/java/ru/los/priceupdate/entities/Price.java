package ru.los.priceupdate.entities;

import java.sql.Date;
import java.util.Objects;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Price {

    private static final AtomicInteger count = new AtomicInteger(0);
    long id; // идентификатор в БД
    String productCode; // код товара
    int number; // номер цены
    int depart; // номер отдела
    Date begin; // начало действия
    Date end; // конец действия
    long value; // значение цены в копейках

    public Price(String productCode, int number, int depart, Date begin, Date end, long value) {
        this.id = count.incrementAndGet();
        this.productCode = productCode;
        this.number = number;
        this.depart = depart;
        this.begin = begin;
        this.end = end;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return depart == price.depart &&
                value == price.value &&
                number == price.number &&
                Objects.equals(productCode, price.productCode) &&
                Objects.equals(begin, price.begin) &&
                Objects.equals(end, price.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productCode, number, depart, begin, end, value);
    }
}
