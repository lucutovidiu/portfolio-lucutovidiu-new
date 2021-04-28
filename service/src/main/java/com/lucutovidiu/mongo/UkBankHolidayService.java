package com.lucutovidiu.mongo;

import com.lucutovidiu.pojo.UkBankHoliday;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UkBankHolidayService {
    List<UkBankHoliday> getUkBankHolidays();
}
