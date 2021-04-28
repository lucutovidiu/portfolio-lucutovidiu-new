package com.lucutovidiu.models;

import com.lucutovidiu.pojo.UkBankHoliday;
import com.lucutovidiu.pojo.UkBankHolidayBody;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "bankholidays")
public class BankHolidayEntity extends BaseEntity {

    @Indexed(unique = true)
    private String title;
    private String country;
    private Integer year;
    private List<UkBankHolidayBody> holiday;

    public static BankHolidayEntity convert(UkBankHoliday holiday) {
        BankHolidayEntity entity = new BankHolidayEntity();
        entity.setTitle(holiday.getTitle());
        entity.setHoliday(holiday.getHoliday());
        entity.setCountry(holiday.getCountry());
        entity.setYear(holiday.getYear());
        return entity;
    }

    public UkBankHoliday toPojo() {
        UkBankHoliday ukBankHoliday = new UkBankHoliday();
        ukBankHoliday.setId(getId());
        ukBankHoliday.setTitle(title);
        ukBankHoliday.setHoliday(holiday);
        ukBankHoliday.setCountry(country);
        return ukBankHoliday;
    }
}
