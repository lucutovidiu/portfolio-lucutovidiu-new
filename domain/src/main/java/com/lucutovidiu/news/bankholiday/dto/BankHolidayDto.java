package com.lucutovidiu.news.bankholiday.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BankHolidayDto {
    private String title;
    private String country;
    private List<UkBankHolidayBodyDto> holidays;
}
