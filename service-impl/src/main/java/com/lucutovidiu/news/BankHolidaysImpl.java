package com.lucutovidiu.news;

import com.lucutovidiu.mongo.UkBankHolidayService;
import com.lucutovidiu.news.bankholiday.dto.BankHolidayDto;
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
    public List<BankHolidayDto> getUkBankHolidays() {
        return ukBankHolidayService.getAllUkBankHolidays().stream()
                .map(UkBankHoliday::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = GET_ALL_UK_BANK_HOLIDAYS)
    public List<BankHolidayDto> getUkBankHolidaysByYears(List<Integer> years) {
        return ukBankHolidayService.getAllUkBankHolidaysByYears(years).stream()
                .map(UkBankHoliday::toDto)
                .collect(Collectors.toList());
    }


}
