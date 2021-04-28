package com.lucutovidiu.pages;

import com.lucutovidiu.news.BankHolidays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import static com.lucutovidiu.cache.CacheNames.GET_ALL_UK_BANK_HOLIDAYS;
import static com.lucutovidiu.util.PageAttributesUtil.UkBankHolidays;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class NewsImpl implements News {

    private final BankHolidays bankHolidays;

    @Override
    public String getNews(Model model) {
        model.addAttribute(UkBankHolidays, bankHolidays.getUkBankHolidays());
        return "news/news";
    }

    @Scheduled(cron = "0 0 9 25 * ?")
    @CacheEvict(value = {GET_ALL_UK_BANK_HOLIDAYS}, allEntries = true)
    public void expireBankHolidays() {
        log.info("Cleared Cache: {} all entries", GET_ALL_UK_BANK_HOLIDAYS);
    }
}
