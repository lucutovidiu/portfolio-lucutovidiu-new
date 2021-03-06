package com.lucutovidiu.household.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
        return "<b>" + itemName + "</b> din grupul <b>" + groupName + "</b> va expira la data de <b style='color: #e74c3c'>" + itemExpirationDate + "</b><br/>" +
                "Mai multe informatii: <b>" + moreInfo + "</b>";
    }
}
