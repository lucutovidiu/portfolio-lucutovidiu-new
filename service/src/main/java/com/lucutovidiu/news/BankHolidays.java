package com.lucutovidiu.news;

import com.lucutovidiu.news.bankholiday.dto.UkBankHolidayDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BankHolidays {

    List<UkBankHolidayDto> getUkBankHolidays();
}
