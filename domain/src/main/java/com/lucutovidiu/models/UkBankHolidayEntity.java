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
@Document(collection = "ukbankholiday")
public class UkBankHolidayEntity {

    @Indexed(unique = true)
    private String title;
    private List<UkBankHolidayBody> holiday;

    public UkBankHoliday toPojo() {
        return new UkBankHoliday(title, holiday);
    }

    public static UkBankHolidayEntity convert(UkBankHoliday holiday) {
        UkBankHolidayEntity entity = new UkBankHolidayEntity();
        entity.setTitle(holiday.getTitle());
        entity.setHoliday(holiday.getHoliday());
        return entity;
    }
}
