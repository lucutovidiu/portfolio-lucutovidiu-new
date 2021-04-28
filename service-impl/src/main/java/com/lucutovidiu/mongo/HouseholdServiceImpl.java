package com.lucutovidiu.mongo;

import com.lucutovidiu.household.dto.*;
import com.lucutovidiu.household.exceptions.HouseholdException;
import com.lucutovidiu.models.HouseholdGroupEntity;
import com.lucutovidiu.models.HouseholdItemEntity;
import com.lucutovidiu.repos.HouseholdGroupRepository;
import com.lucutovidiu.repos.HouseholdItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class HouseholdServiceImpl implements HouseholdService {
    private final HouseholdItemRepository householdItemRepository;
    private final HouseholdGroupRepository householdGroupRepository;

    @Override
    @Transactional
    public HouseholdGroupPostResponseDto addGroup(HouseholdGroupRequestDto householdGroupRequestDto, String loggerUser) {
        try {
            HouseholdGroupEntity saved = householdGroupRepository.save(HouseholdGroupEntity.convertToGroupOnly(householdGroupRequestDto, loggerUser));
            HouseholdGroupPostResponseDto dto = convert(saved);
            dto.setSucceeded(true);
            return dto;
        } catch (DuplicateKeyException exception) {
            log.error(exception.getMessage());
            HouseholdGroupPostResponseDto dto = new HouseholdGroupPostResponseDto();
            dto.setSucceeded(false);
            dto.setErrorMsg(exception.getMessage());
            return dto;
        }
    }

    public HouseholdGroupPostResponseDto convert(HouseholdGroupEntity entity) {
        HouseholdGroupPostResponseDto dto = new HouseholdGroupPostResponseDto();
        dto.setId(entity.getId());
        dto.setGroupName(entity.getGroupName());
        return dto;
    }

    @Override
    public List<HouseholdGroupOnlyDto> getGroupsOnly(String loggerUser) {
        return householdGroupRepository.findAllByCreatedBy(loggerUser).stream()
                .map(HouseholdGroupEntity::toHouseholdGroupOnlyDto)
                .collect(toList());
    }

    @Override
    public List<HouseholdGroupDto> getGroups(String loggerUser) {
        return householdGroupRepository.findAllByCreatedBy(loggerUser).stream()
                .map(HouseholdGroupEntity::toHouseholdGroupDto)
                .filter(householdGroupDto -> householdGroupDto.getHouseholdItems().size() > 0)
                .collect(toList());
    }

    @Override
    public Optional<HouseholdGroupDto> getGroup(String groupName, String loggerUser) {
        return householdGroupRepository.findByGroupNameAndCreatedBy(groupName, loggerUser)
                .map(HouseholdGroupEntity::toHouseholdGroupDto);
    }

    @Override
    public List<HouseholdGroupDto> getAllGroups() {
        return householdGroupRepository.findAll().stream()
                .map(HouseholdGroupEntity::toHouseholdGroupDto)
                .collect(toList());
    }

    @Override
    @Transactional
    public boolean addItem(HouseholdItemRequestDto householdItemRequestDto) {
        householdGroupRepository.findById(householdItemRequestDto.getHouseholdGroupId())
                .map(groupEntity -> {
                    HouseholdItemEntity itemEntity = householdItemRepository.save(HouseholdItemEntity.convert(householdItemRequestDto));
                    groupEntity.getHouseholdItems().add(itemEntity);
                    return groupEntity;
                })
                .map(householdGroupRepository::save)
                .orElseThrow(() -> new HouseholdException("Group not found"));
        return true;
    }

    @Override
    public boolean deleteGroup(String groupId, String loggerUser) {
        HouseholdGroupEntity groupEntity = householdGroupRepository
                .findByIdAndCreatedBy(groupId, loggerUser)
                .orElseThrow(() -> new HouseholdException("Group not found"));
        groupEntity.getHouseholdItems().forEach(this::deleteItems);
        householdGroupRepository.delete(groupEntity);
        return true;
    }

    @Override
    public boolean deleteItemFromGroup(String itemId, String groupId) {
        HouseholdGroupEntity groupEntity = householdGroupRepository.findById(groupId).orElseThrow(() -> new HouseholdException("Group not found"));
        groupEntity.getHouseholdItems().stream()
                .filter(householdItemEntity -> householdItemEntity.getId().equals(itemId))
                .findFirst()
                .ifPresent(this::deleteItems);
        return true;
    }

    @Override
    public void updateLastNotification(String groupId, String itemId) {
        householdGroupRepository.findById(groupId).flatMap(group -> group.getHouseholdItems().stream().filter(item -> item.getId().equals(itemId))
                .findFirst()
                .map(householdItemEntity -> {
                    householdItemEntity.setLastNotificationSent(LocalDate.now());
                    householdItemEntity.setNotificationCount(householdItemEntity.getNotificationCount() + 1);
                    return householdItemEntity;
                })).ifPresent(householdItemRepository::save);
    }

    private void deleteItems(HouseholdItemEntity itemEntity) {
        householdItemRepository.delete(itemEntity);
    }
}
