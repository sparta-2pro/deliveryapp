package com.twopro.deliveryapp.common.config.generator;

import com.twopro.deliveryapp.cart.entity.Cart;
import com.twopro.deliveryapp.cart.entity.CartMenu;
import com.twopro.deliveryapp.cart.repository.CartMenuRepository;
import com.twopro.deliveryapp.cart.repository.CartRepository;
import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.common.enumType.*;
import com.twopro.deliveryapp.menu.entity.Menu;
import com.twopro.deliveryapp.menu.entity.MenuStatus;
import com.twopro.deliveryapp.menu.repository.MenuRepository;
import com.twopro.deliveryapp.order.entity.Order;
import com.twopro.deliveryapp.order.repository.OrderRepository;
import com.twopro.deliveryapp.orderItem.Entity.OrderItem;
import com.twopro.deliveryapp.payment.entity.Payment;
import com.twopro.deliveryapp.payment.repository.PaymentRepository;
import com.twopro.deliveryapp.store.entity.Category;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.repository.CategoryRepository;
import com.twopro.deliveryapp.store.repository.StoreRepository;
import com.twopro.deliveryapp.user.entity.Role;
import com.twopro.deliveryapp.user.entity.User;
import com.twopro.deliveryapp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// @Component
public class DummyDataGenerator {
    // @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner dataInitializer(StoreRepository storeRepository,
                                             MenuRepository menuRepository,
                                             UserRepository userRepository,
                                             OrderRepository orderRepository,
                                             CategoryRepository categoryRepository,
                                             PaymentRepository paymentRepository,
                                             CartRepository cartRepository,
                                             CartMenuRepository cartMenuRepository) {
        return args -> {
            Random random = new Random();

            // 임시 Category 생성
            Category category = new Category();
            category.setName("Dummy Category");
            categoryRepository.save(category);

            // 100개의 가게 생성
            List<Store> stores = new ArrayList<>();

            // 고정 가게 데이터 저장
            stores.add(Store.builder()
                    .category(category)
                    .name("bbq")
                    .pictureUrl("http://bbq.test")
                    .phone("010-0000-0000")
                    .operatingHours("11:00 - 02:00")
                    .closedDays("None")
                    .rating(5)          // 1 ~ 5
                    .reviewCount(100)         // 0 ~ 99
                    .status(StoreStatus.OPEN)
                    .deliveryType(StoreType.DELIVERY)
                    .minimumOrderPrice(20000)  // 10000 ~ 19999
                    .deliveryTip(3000)           // 1000 ~ 4999
                    .address(new Address("Seoul", "Yongsan-gu", "hankangrodong", "hankangro", "서울특별시 용산구 한강로 2가", "102동 102호"))
                    .build()
            );

            for (int i = 1; i <= 100; i++) {
                String storeName = String.format("Store #%02d", i);
                Store store = Store.builder()
                        .category(category)
                        .name(storeName)
                        .pictureUrl("http://example.com/store" + i + ".jpg")
                        .phone(String.format("010-1234-%04d", i))
                        .operatingHours("09:00 - 22:00")
                        .closedDays("None")
                        .rating(random.nextInt(5) + 1)          // 1 ~ 5
                        .reviewCount(random.nextInt(100))         // 0 ~ 99
                        .status(StoreStatus.OPEN)
                        .deliveryType(StoreType.DELIVERY)
                        .minimumOrderPrice(10000 + random.nextInt(10000))  // 10000 ~ 19999
                        .deliveryTip(1000 + random.nextInt(4000))           // 1000 ~ 4999
                        .address(new Address("Seoul", "Gangnam-gu", "Yeoksam-dong", "123-45", "서울특별시 강남구 삼성동 123-45", "101동 1203호"))
                        .build();

                stores.add(store);
            }
            storeRepository.saveAll(stores);

            List<Menu> menus = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                Store randomStore = stores.get(random.nextInt(stores.size()));
                String menuName = String.format("Menu #%03d", i);
                Menu menu = Menu.builder()
                        .store(randomStore)
                        .name(menuName)
                        .status(MenuStatus.AVAILABLE)
                        .imageUrl("http://example.com/menu" + i + ".jpg")
                        .description("Description for " + menuName)
                        .price(5000 + random.nextInt(9999)) // 5000 ~ 29999
                        .build();
                menus.add(menu);
            }
            menus.forEach(menuRepository::addMenu);

            List<User> users = new ArrayList<>();
            for (int i = 1; i <= 6; i++) {
                User user = new User();
                user.setNickname("User #" + i);
                user.setEmail("user" + i + "@example.com");
                user.setPassword(passwordEncoder.encode("1234"));
                user.setRole(i % 2 == 0 ? Role.CUSTOMER : Role.OWNER);
                user.setAddress( new Address("Seoul", "Gangnam-gu", "Yeoksam-dong", "123-45", "서울특별시 강남구 삼성동 123-45", "101동 1203호"));
                users.add(user);
            }
            userRepository.saveAll(users);

            List<Order> orders = new ArrayList<>();
            for (int i = 1; i <= 500; i++) {
                User randomUser = users.get(random.nextInt(users.size()));
                Store randomStore = stores.get(random.nextInt(stores.size()));

                List<OrderItem> orderItems = new ArrayList<>();
                OrderItem orderItem = OrderItem.createOrderItem(menus.get(random.nextInt(menus.size())), 10000, 3);
                orderItems.add(orderItem);

                Address orderAddress = new Address("Seoul", "Gangnam-gu", "Yeoksam-dong", "123-45", "서울특별시 강남구 삼성동 123-45", "101동 1203호");
                Payment payment = Payment.createPayment(30000, PaymentStatus.SUCCESS, PaymentProvider.NH);
                Order order = Order.createOrder(randomUser, orderItems,
                        OrderType.DELIVERY, orderAddress,
                        "Dummy order " + i, paymentRepository.save(payment), randomStore);

                orders.add(order);
            }
            orderRepository.saveAll(orders);

            List<Cart> carts = new ArrayList<>();
            for (User user : users) {
                Cart cart = new Cart();
                cart.setUser(user);
                carts.add(cartRepository.save(cart));
            }

            List<CartMenu> cartMenus = new ArrayList<>();
            for (Cart cart : carts) {
                for (int i = 0; i < 2; i++) { // 각 장바구니에 2개의 메뉴 추가 (임의로 설정)
                    Menu randomMenu = menus.get(random.nextInt(menus.size()));
                    CartMenu cartMenu = new CartMenu();
                    cartMenu.setCart(cart);
                    cartMenu.setMenu(randomMenu);
                    cartMenu.setQuantity(random.nextInt(2) + 1);  // 1 ~ 2개씩 추가
                    // totalPrice 계산 (메뉴 가격 * 수량)
                    int totalPrice = cartMenu.getMenu().getPrice() * cartMenu.getQuantity();
                    cartMenu.setTotalPrice(totalPrice);
                    cartMenus.add(cartMenuRepository.save(cartMenu));
                }
            }
        };
    }
}