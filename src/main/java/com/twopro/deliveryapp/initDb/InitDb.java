//package com.twopro.deliveryapp.initDb;
//
//import com.twopro.deliveryapp.cart.entity.Cart;
//import com.twopro.deliveryapp.common.dto.AddressDto;
//import com.twopro.deliveryapp.common.entity.Address;
//import com.twopro.deliveryapp.common.enumType.OrderType;
//import com.twopro.deliveryapp.common.enumType.StoreStatus;
//import com.twopro.deliveryapp.menu.entity.Menu;
//import com.twopro.deliveryapp.menu.entity.MenuStatus;
//import com.twopro.deliveryapp.menu.repository.JpaMenuRepository;
//import com.twopro.deliveryapp.menu.repository.MenuRepository;
//import com.twopro.deliveryapp.order.repository.OrderRepository;
//import com.twopro.deliveryapp.store.entity.Category;
//import com.twopro.deliveryapp.store.entity.Store;
//import com.twopro.deliveryapp.store.repository.CategoryRepository;
//import com.twopro.deliveryapp.store.repository.StoreRepository;
//import com.twopro.deliveryapp.user.entity.Role;
//import com.twopro.deliveryapp.user.entity.User;
//import com.twopro.deliveryapp.user.repository.UserRepository;
//import jakarta.annotation.PostConstruct;
//import jakarta.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class InitDb {
//
//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//        initService.dbInit1();
//    }
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService{
//        private final OrderRepository orderRepository;
//        private final UserRepository userRepository;
//        private final StoreRepository storeRepository;
//        private final CategoryRepository categoryRepository;
//        private final PasswordEncoder passwordEncoder;
//        private final JpaMenuRepository menuRepository;
//        private final EntityManager em;
//
//        public void dbInit1() {
//
//            AddressDto addressDto = new AddressDto("서울특별시 강남구 테헤란로 427", "서울특별시 강남구 삼성동 123-45", "101동 1203호", "서울특별시", "강남구", "삼성동");
//
//            List<Category> categories = new ArrayList<>();
//
//            Category category = new Category();
//            category.setName("중식");
//            Category category2 = new Category();
//            category.setName("한식");
//            Category category3 = new Category();
//            category.setName("치킨");
//
//            categories.add(category);
//            categories.add(category2);
//            categories.add(category3);
//
//
//            categoryRepository.saveAll(categories);
//
//
//            User user = new User();
//            user.setAddress(Address.of(addressDto));
//            user.setEmail("test@test.com");
//            user.setPassword(passwordEncoder.encode("1234"));
//            user.setRole(Role.CUSTOMER);
//            user.setNickname("전인종");
//
//            userRepository.save(user);
//
//
//            Store store = Store.builder()
//                    .deliveryType(OrderType.DELIVERY)
//                    .name("행복한 반찬가게")
//                    .phone("01012345667")
//                    .address(Address.of(addressDto))
//                    .minimumOrderPrice(50000)
//                    .category(category)
//                    .reviewCount(0)
//                    .rating(0)
//                    .pictureUrl("www.aws.s3.com")
//                    .deliveryTip(2000)
//                    .closedDays("월요일")
//                    .status(StoreStatus.OPEN)
//                    .category(category)
//                    .operatingHours("1~3")
//                    .build();
//
//            storeRepository.save(store);
//
//            Menu menu = Menu.builder()
//                    .price(10000)
//                    .description("떡볶이입니다")
//                    .imageUrl("www.s3.com")
//                    .store(store)
//                    .status(MenuStatus.AVAILABLE)
//                    .name("떡볶이")
//                    .build();
//
//            Menu menu2 = Menu.builder()
//                    .price(20000)
//                    .description("치킨입니다")
//                    .imageUrl("www.s3.com")
//                    .store(store)
//                    .status(MenuStatus.AVAILABLE)
//                    .name("치킨")
//                    .build();
//
//            menuRepository.save(menu);
//
//        }
//    }
//}