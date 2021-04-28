package com.lucutovidiu.mongo;

import com.lucutovidiu.models.BankHolidayEntity;
import com.lucutovidiu.mongo.country.CountryEnum;
import com.lucutovidiu.news.exceptions.WebScrapingError;
import com.lucutovidiu.pojo.UkBankHoliday;
import com.lucutovidiu.pojo.UkBankHolidayBody;
import com.lucutovidiu.repos.BankHolidayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UkBankHolidayServiceImpl implements UkBankHolidayService {
    public static final String BANK_HOLIDAYS_ENGLAND = ".*bank.*holidays.*england.*";
    private static final String UK_BANK_HOLIDAY_URL = "https://www.gov.uk/bank-holidays";
    private static final HttpClient client = HttpClient.newHttpClient();
    private final BankHolidayRepository bankHolidayRepository;

    public List<UkBankHoliday> getAllUkBankHolidays() {
        List<UkBankHoliday> ukBankHolidays = bankHolidayRepository.findAll()
                .stream()
                .map(BankHolidayEntity::toPojo)
                .collect(Collectors.toList());
        if (!ukBankHolidays.isEmpty()) {
            return ukBankHolidays;
        }
        List<UkBankHoliday> scrapedUkBackHolidays = scrapeUkBackHolidays();
        saveUkBankHolidaysToDb(scrapedUkBackHolidays);
        return scrapedUkBackHolidays;
    }

    @Override
    public List<UkBankHoliday> getAllUkBankHolidaysByYears(List<Integer> years) {
        return years.stream()
                .map(bankHolidayRepository::getAllByYear)
                .flatMap(Collection::stream)
                .map(BankHolidayEntity::toPojo)
                .collect(Collectors.toList());
    }

    private void saveUkBankHolidaysToDb(List<UkBankHoliday> scrapedUkBackHolidays) {
        scrapedUkBackHolidays.stream()
                .map(BankHolidayEntity::convert)
                .forEach(bankHolidayRepository::save);
    }

    private List<UkBankHoliday> scrapeUkBackHolidays() {
        return getEnglandBankHolidays(getBankHolidayWebpage()).stream()
                .map(this::convertElementToPojo)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<UkBankHoliday> convertElementToPojo(Element element) {
        String title = extractTitle(element);
        return extractYear(title)
                .map(year -> getUkBankHolidayPojo(title, year, element));
    }

    private UkBankHoliday getUkBankHolidayPojo(String title, Integer year, Element element) {
        UkBankHoliday ukBankHoliday = new UkBankHoliday();
        ukBankHoliday.setTitle(title);
        ukBankHoliday.setCountry(CountryEnum.UNITED_KINGDOM.getCountryName());
        ukBankHoliday.setYear(year);
        ukBankHoliday.setHoliday(extractHolidayLines(element, year));
        return ukBankHoliday;
    }

    private List<UkBankHolidayBody> extractHolidayLines(Element element, Integer year) {
        Elements elements = element.select("tr>td");
        int size = elements.size();
        List<UkBankHolidayBody> list = new ArrayList<>();
        for (int i = 0; i < size; i = i + 3) {
            UkBankHolidayBody pojo = new UkBankHolidayBody();
            String shortDate = elements.get(i).text().trim();
            String dayOfWeek = elements.get(i + 1).text().trim();
            String holidayName = elements.get(i + 2).text().trim();
            String[] dayAndMonth = shortDate.split(" ");
            pojo.setShortDate(shortDate);
            pojo.setDayOfWeek(dayOfWeek);
            pojo.setHolidayName(holidayName);
            if (dayAndMonth.length == 2) {
                pojo.setFullDate(LocalDate.of(year, Month.valueOf(dayAndMonth[1].trim().toUpperCase()),
                        Integer.parseInt(dayAndMonth[0].trim())).toString());
            } else {
                log.error("Could not properly extract the day and month from the extracted table!");
            }
            list.add(pojo);
        }
        return list;
    }

    private Optional<Integer> extractYear(String title) {
        Matcher matcher = Pattern.compile("^[\\w ]*(\\d{4})$").matcher(title);
        if (matcher.matches()) {
            return Optional.of(Integer.parseInt(matcher.group(1).trim()));
        }
        return Optional.empty();
    }

    private String extractTitle(Element element) {
        return element.getElementsByTag("caption").text();
    }

    private List<Element> getEnglandBankHolidays(String html) {
        Document doc = Jsoup.parse(html);
        Elements table = doc.getElementsByTag("table");
        return table.stream()
                .filter(element -> isEnglishHoliday(element.getElementsByTag("caption").text()))
                .filter(this::isHolidayBodyValid)
                .collect(Collectors.toList());
    }

    private boolean isHolidayBodyValid(Element element) {
        Elements select = element.select("tr>td");
        return select.size() > 0 && select.size() % 3 == 0;
    }

    private boolean isEnglishHoliday(String caption) {
        return Pattern.compile(BANK_HOLIDAYS_ENGLAND, Pattern.CASE_INSENSITIVE).matcher(caption).matches();
    }

    private String getBankHolidayWebpage() {
        HttpRequest request = HttpRequest.newBuilder(URI.create(UK_BANK_HOLIDAY_URL)).GET().build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        if (Objects.requireNonNull(response).statusCode() == 200) {
            return response.body();
        }
        throw new WebScrapingError("Could not fetch holiday webpage");
    }
}
