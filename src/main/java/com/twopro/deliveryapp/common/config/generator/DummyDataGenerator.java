package com.twopro.deliveryapp.common.config.generator;

import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.common.enumType.OrderType;
import com.twopro.deliveryapp.common.enumType.StoreStatus;
import com.twopro.deliveryapp.menu.entity.Menu;
import com.twopro.deliveryapp.menu.entity.MenuStatus;
import com.twopro.deliveryapp.menu.repository.MenuRepository;
import com.twopro.deliveryapp.order.entity.Order;
import com.twopro.deliveryapp.order.repository.OrderRepository;
import com.twopro.deliveryapp.orderItem.Entity.OrderItem;
import com.twopro.deliveryapp.store.entity.Category;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.repository.CategoryRepository;
import com.twopro.deliveryapp.store.repository.StoreRepository;
import com.twopro.deliveryapp.user.entity.User;
import com.twopro.deliveryapp.user.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DummyDataGenerator {

    @Bean
    public ApplicationRunner dataInitializer(StoreRepository storeRepository,
                                             MenuRepository menuRepository,
                                             UserRepository userRepository,
                                             OrderRepository orderRepository, CategoryRepository categoryRepository) {
        return args -> {
            Random random = new Random();

            // 임시 Category 생성
            Category category = new Category();
            category.setName("Dummy Category");
            categoryRepository.save(category);

            // 100개의 가게 생성
            List<Store> stores = new ArrayList<>();
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
                        .deliveryType(OrderType.DELIVERY)
                        .minimumOrderPrice(10000 + random.nextInt(10000))  // 10000 ~ 19999
                        .deliveryTip(1000 + random.nextInt(4000))           // 1000 ~ 4999
                        .address(new Address("Seoul", "Gangnam-gu", "Yeoksam-dong", "123-45"))
                        .build();
                stores.add(store);
            }
            storeRepository.saveAll(stores);

            List<Menu> menus = new ArrayList<>();
            for (int i = 1; i <= 1000; i++) {
                Store randomStore = stores.get(random.nextInt(stores.size()));
                String menuName = String.format("Menu #%03d", i);
                Menu menu = Menu.builder()
                        .store(randomStore)
                        .name(menuName)
                        .status(MenuStatus.AVAILABLE)
                        .imageUrl("http://example.com/menu" + i + ".jpg")
                        .description("Description for " + menuName)
                        .price(5000 + random.nextInt(25000)) // 5000 ~ 29999
                        .build();
                menus.add(menu);
            }
            menus.forEach(menuRepository::addMenu);

            List<User> users = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                User user = new User();
                user.setNickname("User #" + i);
                users.add(user);
            }
            userRepository.saveAll(users);

            List<Order> orders = new ArrayList<>();
            for (int i = 1; i <= 500; i++) {
                User randomUser = users.get(random.nextInt(users.size()));
                Store randomStore = stores.get(random.nextInt(stores.size()));

                List<OrderItem> orderItems = new ArrayList<>();

                Address orderAddress = new Address("Seoul", "Gangnam-gu", "Yeoksam-dong", "123-45");
                Order order = Order.createOrder(randomUser, orderItems, OrderType.DELIVERY, orderAddress, "Dummy order " + i, null);

                order.setStore(randomStore);

                orders.add(order);
            }
            orderRepository.saveAll(orders);
        };
    }
}