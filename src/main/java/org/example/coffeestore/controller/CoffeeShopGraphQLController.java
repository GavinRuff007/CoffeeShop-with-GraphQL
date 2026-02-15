package org.example.coffeestore.controller;

import lombok.RequiredArgsConstructor;
import org.example.coffeestore.dto.OrderItemInput;
import org.example.coffeestore.entity.*;
import org.example.coffeestore.service.CoffeeShopService;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import java.math.BigDecimal;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class CoffeeShopGraphQLController {

    private final CoffeeShopService service;

    // --------------------- Queries ---------------------

    @QueryMapping
    public List<Customer> customers() {
        return service.getCustomers();
    }

    @QueryMapping
    public List<Product> products() {
        return service.getProducts();
    }

    @QueryMapping
    public List<Order> orders() {
        return service.getOrders();
    }

    @QueryMapping
    public List<Inventory> inventories() {
        return service.getInventories();
    }

    @QueryMapping
    public List<LoyaltyTransaction> loyaltyTransactions() {
        return service.getLoyaltyTransactions();
    }

    // --------------------- Mutations ---------------------

    @MutationMapping
    public Customer createCustomer(@Argument String name,
                                   @Argument String email,
                                   @Argument String phone) {
        return service.createCustomer(name, email, phone);
    }

    @MutationMapping
    public Product createProduct(@Argument String name,
                                 @Argument String category,
                                 @Argument String size,
                                 @Argument BigDecimal price,
                                 @Argument BigDecimal cost,
                                 @Argument String description) {

        return service.createProduct(name, category, size, price, cost, description);
    }

    @MutationMapping
    public Order createOrder(@Argument Long customerId,
                             @Argument List<OrderItemInput> items) {

        return service.createOrder(customerId, items);
    }

    @MutationMapping
    public Inventory createInventory(@Argument Long productId,
                                     @Argument Integer stockBefore,
                                     @Argument Integer sold,
                                     @Argument String notes) {

        return service.createInventory(productId, stockBefore, sold, notes);
    }

    @MutationMapping
    public LoyaltyTransaction addLoyalty(@Argument Long customerId,
                                         @Argument Integer changeAmount,
                                         @Argument String type) {

        return service.addLoyalty(customerId, changeAmount, type);
    }

}
