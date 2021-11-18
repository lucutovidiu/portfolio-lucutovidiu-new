package com.lucutovidiu.automated;

import com.lucutovidiu.expiredprod.ExpiredProducts;
import com.lucutovidiu.household.dto.HouseholdGroupDto;
import com.lucutovidiu.household.dto.HouseholdItemDto;
import com.lucutovidiu.mongo.HouseholdService;
import com.lucutovidiu.mongo.UkBankHolidayService;
import com.lucutovidiu.service.EmailService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExpiredProductsImpl implements ExpiredProducts {

    private final EmailService emailService;
    private final HouseholdService householdService;

    //@Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]"
    //cron every 2 hours
//    @Scheduled(cron = "0 0 */2 * * ?")
    public void emailExpiredProducts() {
        Map<String, List<ExpiredProductsEmailStructure>> expiredProductsGroupedByEmail = householdService.getAllGroups().stream()
                .flatMap(dto -> dto.getHouseholdItems().stream()
                        .filter(HouseholdItemDto::isExpirationDateDue)
                        .filter(HouseholdItemDto::shouldNotificationBeSendAgainAtThisTime)
                        .map(item -> {
                            if (dto.getGroupName().equals(UkBankHolidayService.GROUP_NAME)) {
                                return getHolidayEmail(dto, item);
                            }
                            return getGeneralEmail(dto, item);
                        }))
                .collect(groupingBy(ExpiredProductsEmailStructure::getCreatedBy));
        emailExpiredProducts(expiredProductsGroupedByEmail);
    }

    private ExpiredProductsEmailStructure getHolidayEmail(HouseholdGroupDto dto, HouseholdItemDto item) {
        return ExpiredProductsEmailStructure.builder()
                .groupId(dto.getId())
                .itemId(item.getId())
                .msg(item.getHolidayFormattedEmail(dto.getGroupName()))
                .mailSubject("Upcoming uk Holiday")
                .createdBy(dto.getCreatedBy())
                .build();
    }

    private ExpiredProductsEmailStructure getGeneralEmail(HouseholdGroupDto dto, HouseholdItemDto item) {
        return ExpiredProductsEmailStructure.builder()
                .groupId(dto.getId())
                .itemId(item.getId())
                .msg(item.getFormattedEmail(dto.getGroupName()))
                .mailSubject("Expired Products Email")
                .createdBy(dto.getCreatedBy())
                .build();
    }

    private void emailExpiredProducts(Map<String, List<ExpiredProductsEmailStructure>> expiredProductsGroupedByEmail) {
        expiredProductsGroupedByEmail.forEach((email, productsEmailStructures) -> {
            productsEmailStructures.forEach(product -> householdService.updateLastNotification(product.getGroupId(), product.getItemId()));
            String combinedMsg = productsEmailStructures.stream().map(ExpiredProductsEmailStructure::getMsg).collect(Collectors.joining("<br/><br/>"));
            emailService.sendExpiredProductsEmail(productsEmailStructures.get(0).getMailSubject(), combinedMsg, email);
            log.info("{}", productsEmailStructures.get(0).getMailSubject());
        });
    }
}

@Getter
@Setter
@AllArgsConstructor
@Builder
class ExpiredProductsEmailStructure {
    private String groupId;
    private String itemId;
    private String msg;
    private String mailSubject;
    private String createdBy;
}
