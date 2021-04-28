package com.lucutovidiu.mongo;

import com.lucutovidiu.models.UkBankHolidayEntity;
import com.lucutovidiu.news.exceptions.WebScrapingError;
import com.lucutovidiu.pojo.UkBankHoliday;
import com.lucutovidiu.pojo.UkBankHolidayBody;
import com.lucutovidiu.repos.UkBankHolidayRepository;
import lombok.RequiredArgsConstructor;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UkBankHolidayServiceImpl implements UkBankHolidayService {
    public static final String BANK_HOLIDAYS_ENGLAND = ".*bank.*holidays.*england.*";
    private static final HttpClient client = HttpClient.newHttpClient();
    private final String bankHolidayUrl = "https://www.gov.uk/bank-holidays";
    private final UkBankHolidayRepository ukBankHolidayRepository;

    public List<UkBankHoliday> getUkBankHolidays() {
        List<UkBankHoliday> ukBankHolidays = ukBankHolidayRepository.findAll()
                .stream()
                .map(UkBankHolidayEntity::toPojo)
                .collect(Collectors.toList());
        if (!ukBankHolidays.isEmpty()) {
            return ukBankHolidays;
        }
        List<UkBankHoliday> scrapedUkBackHolidays = scrapeUkBackHolidays();
        saveUkBankHolidaysToDb(scrapedUkBackHolidays);
        return scrapedUkBackHolidays;
    }

    private void saveUkBankHolidaysToDb(List<UkBankHoliday> scrapedUkBackHolidays) {
        scrapedUkBackHolidays.stream()
                .map(UkBankHolidayEntity::convert)
                .forEach(ukBankHolidayRepository::save);
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
        return new UkBankHoliday(title, extractHolidayLines(element, year));
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
            pojo.setFullDate(LocalDate.of(year, Month.valueOf(dayAndMonth[1].trim().toUpperCase()),
                    Integer.parseInt(dayAndMonth[0].trim())).toString());
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
        HttpRequest request = HttpRequest.newBuilder(URI.create(bankHolidayUrl)).GET().build();
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
