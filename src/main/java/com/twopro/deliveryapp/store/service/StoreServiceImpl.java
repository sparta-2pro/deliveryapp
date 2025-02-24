package com.twopro.deliveryapp.store.service;

import com.querydsl.core.types.OrderSpecifier;
import com.twopro.deliveryapp.common.dto.AddressDto;
import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.common.enumType.StoreStatus;
import com.twopro.deliveryapp.common.enumType.StoreType;
import com.twopro.deliveryapp.store.dto.StoreRequestDto;
import com.twopro.deliveryapp.store.dto.StoreResponseDto;
import com.twopro.deliveryapp.store.dto.StoreSearchRequestDto;
import com.twopro.deliveryapp.store.entity.Category;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.repository.CategoryRepository;
import com.twopro.deliveryapp.store.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.twopro.deliveryapp.store.entity.QStore.store;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Optional<Store> findByID(UUID id) {
        return storeRepository.findById(id);
    }

    @Override
    @Transactional
    public Store createStore(StoreRequestDto dto) {
        validateStoreStatus(dto.getStatus());
        validateDeliveryType(dto.getDeliveryType());
        Category category = getCategoryById(dto.getCategoryId());

        Store store = Store.builder()
                .category(category)
                .name(dto.getName())
                .address(Address.of(dto.getAddress()))
                .phone(dto.getPhone())
                .operatingHours(dto.getOperatingHours())
                .closedDays(dto.getClosedDays())
                .pictureUrl(dto.getPictureUrl())
                .status(dto.getStatus())
                .deliveryType(dto.getDeliveryType())
                .minimumOrderPrice(dto.getMinimumOrderPrice())
                .deliveryTip(dto.getDeliveryTip())
                .build();

        return storeRepository.save(store);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreResponseDto> getAllStores() {
        List<Store> stores = storeRepository.findAllNotDeleted();
        return stores.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StoreResponseDto getStoreById(UUID id) {
        Store store = storeRepository.findByStoreIdAndNotDeleted(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 가게를 찾을 수 없거나 삭제되었습니다."));
        return convertToDto(store);
    }

    private StoreResponseDto convertToDto(Store store) {
        StoreResponseDto dto = new StoreResponseDto();
        dto.setId(store.getStoreId());
        dto.setName(store.getName());
        dto.setPictureUrl(store.getPictureUrl());
        dto.setPhone(store.getPhone());
        dto.setAddress(AddressDto.of(store.getAddress()));
        dto.setOperatingHours(store.getOperatingHours());
        dto.setClosedDays(store.getClosedDays());
        dto.setStatus(store.getStatus().name());
        dto.setDeliveryType(store.getDeliveryType());
        dto.setMinimumOrderPrice(store.getMinimumOrderPrice());
        dto.setDeliveryTip(store.getDeliveryTip());
        return dto;
    }

    @Override
    @Transactional
    public void updateStore(UUID id, StoreRequestDto dto) {
        Store store = storeRepository.findById(id).orElseThrow();
        validateDeliveryType(dto.getDeliveryType());
        Category category = getCategoryById(dto.getCategoryId());

        store.updateStoreDetails(
                dto.getName(),
                dto.getPhone(),
                dto.getOperatingHours(),
                dto.getClosedDays(),
                dto.getPictureUrl(),
                category,
                dto.getStatus(),
                dto.getDeliveryType(),
                dto.getMinimumOrderPrice(),
                dto.getDeliveryTip(),
                Address.of(dto.getAddress())
        );

        storeRepository.save(store);
    }

    @Override
    @Transactional
    public void deleteStore(UUID id) {
        Store store = storeRepository.findById(id).orElseThrow();
        store.delete();
        storeRepository.save(store);
    }

    private void validateStoreStatus(StoreStatus status) {
        if (status == null || !(status == StoreStatus.OPEN || status == StoreStatus.CLOSED)) {
            throw new IllegalArgumentException("유효하지 않은 상태 값입니다.");
        }
    }

    private void validateDeliveryType(StoreType deliveryType) {
        if (deliveryType == null || !(deliveryType == StoreType.DELIVERY || deliveryType == StoreType.PICKUP || deliveryType == StoreType.DELIVERY_AND_PICKUP)) {
            throw new IllegalArgumentException("유효하지 않은 배달 타입입니다.");
        }
    }

    private Category getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 카테고리를 찾을 수 없습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StoreResponseDto> searchStores(StoreSearchRequestDto searchDto) {
        int size = searchDto.getSize();
        int page = searchDto.getPage();
        Pageable pageable = createPageableFromSearchDto(searchDto);

        Page<Store> storePage = storeRepository.searchStores(searchDto.getCategoryId(), searchDto.getStoreName(), pageable);
        return storePage.map(this::convertToDto);
    }

    private Sort.Direction parseSortDirection(String directionStr) {
        Sort.Direction direction = Sort.Direction.ASC; // 초기 방향은 ASC로 설정
        if ("desc".equalsIgnoreCase(directionStr)) {
            direction = Sort.Direction.DESC; // 명시적으로 DESC로 설정
        } else if ("asc".equalsIgnoreCase(directionStr)) {
            direction = Sort.Direction.ASC; // 명시적으로 ASC로 설정
        }
        System.out.println("Input direction string: " + directionStr + " parsed as: " + direction);
        return direction;
    }

    private Pageable createPageableFromSearchDto(StoreSearchRequestDto searchDto) {
        String sortField = searchDto.getSortBy() != null ? searchDto.getSortBy() : "created_at";
        Sort.Direction sortDirection = parseSortDirection(searchDto.getDirection());
        Sort sort = Sort.by(sortDirection, sortField);

        // 정렬 정보와 사용된 매개변수 출력
        System.out.println("Sort Field: " + sortField);
        System.out.println("Sort Direction in Sort Object: " + sortDirection);
        System.out.println("Sort Object Details: " + sort);

        return PageRequest.of(searchDto.getPage(), searchDto.getSize(), sort);
    }

}