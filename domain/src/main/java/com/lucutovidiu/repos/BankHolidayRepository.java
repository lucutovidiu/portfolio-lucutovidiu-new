package com.lucutovidiu.repos;

import com.lucutovidiu.models.BankHolidayEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BankHolidayRepository extends MongoRepository<BankHolidayEntity, String> {
    List<BankHolidayEntity> getAllByYear(Integer year);
}
