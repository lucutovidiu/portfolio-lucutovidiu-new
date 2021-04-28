package com.lucutovidiu.household.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class HouseholdItemDto {
    private String id;
    private String itemName;
    private LocalDate itemStartFromDate;
    private LocalDate itemExpirationDate;
    private String moreInfo;
    private LocalDate lastNotificationSent;
    private int notificationCount;

    public String getItemStartFromDateFormatted() {
        return itemStartFromDate.format(DateTimeFormatter.ofPattern("dd - MMM - yyyy"));
    }

    public String getItemExpirationDateFormatted() {
        return itemExpirationDate.format(DateTimeFormatter.ofPattern("dd - MMM - yyyy"));
    }

    public boolean isExpirationDateDue() {
        return itemExpirationDate.minusMonths(1).isBefore(LocalDate.now());
    }

    public boolean shouldNotificationBeSendAgainAtThisTime() {
        switch (notificationCount) {
            case 0:
                return true;
            case 1:
                return itemExpirationDate.minusDays(5).isBefore(LocalDate.now());
            case 2:
                return itemExpirationDate.minusDays(1).isEqual(LocalDate.now());
            case 3:
                return itemExpirationDate.isEqual(LocalDate.now());
            default:
                return false;
        }
    }

    public String getFormattedEmail(String groupName) {
        return "<b>" + itemName + "</b> din grupul <b>" + groupName + "</b> va expira la data de <b style='color: #e74c3c'>" + itemExpirationDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) + "</b><br/>" +
                "Mai multe informatii: <b>" + moreInfo + "</b>";
    }

    public String getHolidayFormattedEmail(String groupName) {
        return "Vacanta <b>" + itemName + "</b> din grupul <b>" + groupName + "</b> in data de <b style='color: #2980b9'>" + itemExpirationDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) + "</b><br/>" +
                "Mai multe informatii: <b>" + moreInfo + "</b>";
    }
}
