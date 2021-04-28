package com.lucutovidiu.repos;

import com.lucutovidiu.models.UkBankHolidayEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UkBankHolidayRepository extends MongoRepository<UkBankHolidayEntity, String> {
}
