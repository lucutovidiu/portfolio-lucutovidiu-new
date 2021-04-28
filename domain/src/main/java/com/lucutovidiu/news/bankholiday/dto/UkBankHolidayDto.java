package com.lucutovidiu.news.bankholiday.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UkBankHolidayDto {
    private String title;
    private List<UkBankHolidayBodyDto> holidays;
}
