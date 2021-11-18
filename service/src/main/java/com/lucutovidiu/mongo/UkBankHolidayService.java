package com.lucutovidiu.mongo;

import com.lucutovidiu.pojo.UkBankHoliday;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UkBankHolidayService {
    String GROUP_NAME = "UK-Bank-Holidays";

    List<UkBankHoliday> getAllUkBankHolidays();

    List<UkBankHoliday> getAllUkBankHolidaysByYears(List<Integer> years);
}
