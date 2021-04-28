package com.lucutovidiu.news;

import com.lucutovidiu.news.bankholiday.dto.BankHolidayDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BankHolidays {

    List<BankHolidayDto> getUkBankHolidays();

    List<BankHolidayDto> getUkBankHolidaysByYears(List<Integer> years);
}
