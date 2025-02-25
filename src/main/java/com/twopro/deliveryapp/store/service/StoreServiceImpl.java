package com.twopro.deliveryapp.store.service;

import com.twopro.deliveryapp.common.dto.AddressDto;
import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.common.enumType.StoreType;
import com.twopro.deliveryapp.store.dto.StoreRequestDto;
import com.twopro.deliveryapp.store.dto.StoreResponseDto;
import com.twopro.deliveryapp.store.dto.StoreSearchRequestDto;
import com.twopro.deliveryapp.store.entity.Category;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.entity.StoreDeliveryArea;
import com.twopro.deliveryapp.store.exception.InvalidDeliveryTypeException;
import com.twopro.deliveryapp.store.exception.InvalidSortDirectionException;
import com.twopro.deliveryapp.store.exception.StoreNotFoundException;
import com.twopro.deliveryapp.store.exception.StoreValidationException;
import com.twopro.deliveryapp.store.repository.CategoryRepository;
import com.twopro.deliveryapp.store.repository.StoreDeliveryAreaRepository;
import com.twopro.deliveryapp.store.repository.StoreRepository;
import com.twopro.deliveryapp.user.entity.User;
import com.twopro.deliveryapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final StoreDeliveryAreaRepository storeDeliveryAreaRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<Store> findByID(UUID id) {
        return storeRepository.findById(id);
    }

    @Override
    @Transactional
    public Store createStore(StoreRequestDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new StoreValidationException("카테고리가 존재하지 않습니다.", "categoryId", dto.getCategoryId().toString()));

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new StoreValidationException("가게 이름은 필수 입력 필드입니다.", "name", "입력되지 않음");
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new StoreValidationException("존재하지 않는 유저입니다.", "email", email));

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
                .user(user)
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
                .orElseThrow(() -> new StoreNotFoundException("해당 ID의 가게를 찾을 수 없거나 삭제되었습니다.", id));
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
        dto.setAverageRating(store.getRating());
        dto.setReviewCount(store.getReviewCount());
        return dto;
    }

    @Override
    @Transactional
    public void updateStore(UUID id, StoreRequestDto dto) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new StoreNotFoundException("해당 ID의 가게를 찾을 수 없습니다.", id));

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

    private void validateDeliveryType(StoreType deliveryType) {
        if (deliveryType == null || !(deliveryType == StoreType.DELIVERY || deliveryType == StoreType.PICKUP || deliveryType == StoreType.DELIVERY_AND_PICKUP)) {
            throw new InvalidDeliveryTypeException("유효하지 않은 배달 타입입니다.", deliveryType.name());
        }
    }

    private Category getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new StoreValidationException("해당 카테고리를 찾을 수 없습니다.", "categoryId", categoryId.toString()));
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
        if ("desc".equalsIgnoreCase(directionStr)) {
            return Sort.Direction.DESC;
        } else if ("asc".equalsIgnoreCase(directionStr)) {
            return Sort.Direction.ASC;
        }
        throw new InvalidSortDirectionException(directionStr);
    }

    private Pageable createPageableFromSearchDto(StoreSearchRequestDto searchDto) {
        String sortField = searchDto.getSortBy() != null ? searchDto.getSortBy() : "created_at";
        Sort.Direction sortDirection = parseSortDirection(searchDto.getDirection());
        Sort sort = Sort.by(sortDirection, sortField);

        return PageRequest.of(searchDto.getPage(), searchDto.getSize(), sort);
    }

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getStoresByDeliveryAreaAndCategory(UUID deliveryAreaId, UUID categoryId) {
        List<Store> stores = storeDeliveryAreaRepository.findByDeliveryAreaId(deliveryAreaId).stream()
                .map(StoreDeliveryArea::getStore)
                .filter(store -> store.getCategory() != null && store.getCategory().getId().equals(categoryId) && store.getDeletedAt() == null)
                .collect(Collectors.toList());

        return stores.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}