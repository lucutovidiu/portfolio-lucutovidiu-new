package com.lucutovidiu.news;

import com.lucutovidiu.mongo.UkBankHolidayService;
import com.lucutovidiu.news.bankholiday.dto.UkBankHolidayDto;
import com.lucutovidiu.pojo.UkBankHoliday;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.lucutovidiu.cache.CacheNames.GET_ALL_UK_BANK_HOLIDAYS;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BankHolidaysImpl implements BankHolidays {

    private final UkBankHolidayService ukBankHolidayService;

    @Override
    @Cacheable(value = GET_ALL_UK_BANK_HOLIDAYS)
    public List<UkBankHolidayDto> getUkBankHolidays() {
        return ukBankHolidayService.getUkBankHolidays().stream()
                .map(UkBankHoliday::toDto)
                .collect(Collectors.toList());
    }
}
