package org.example.coffeestore.service;

import lombok.RequiredArgsConstructor;
import org.example.coffeestore.dto.OrderItemInput;
import org.example.coffeestore.entity.*;
import org.example.coffeestore.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CoffeeShopService {

    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;
    private final InventoryRepository inventoryRepo;
    private final LoyaltyTransactionRepository loyaltyRepo;

    // --------------------- Queries ---------------------

    @Transactional(readOnly = true)
    public List<Customer> getCustomers() {
        return customerRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Order> getOrders() {
        return orderRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Inventory> getInventories() {
        return inventoryRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<LoyaltyTransaction> getLoyaltyTransactions() {
        return loyaltyRepo.findAll();
    }

    // --------------------- Business Logic ---------------------

    public Customer createCustomer(String name, String email, String phone) {
        Customer customer = Customer.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .loyaltyPoints(0)
                .build();

        return customerRepo.save(customer);
    }

    public Product createProduct(String name,
                                 String category,
                                 String size,
                                 BigDecimal price,
                                 BigDecimal cost,
                                 String description) {

        Product product = Product.builder()
                .name(name)
                .category(category)
                .size(size)
                .price(price)
                .cost(cost)
                .description(description)
                .build();

        return productRepo.save(product);
    }

    public Order createOrder(Long customerId, List<OrderItemInput> itemsInput) {

        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Order order = Order.builder()
                .customer(customer)
                .orderDate(LocalDateTime.now())
                .items(new ArrayList<>())
                .build();

        BigDecimal subtotal = BigDecimal.ZERO;

        for (OrderItemInput input : itemsInput) {

            Product product = productRepo.findById(input.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            BigDecimal quantity = BigDecimal.valueOf(input.getQuantity());
            BigDecimal price = product.getPrice();
            BigDecimal lineTotal = price.multiply(quantity);

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(input.getQuantity())
                    .priceAtOrder(price)
                    .build();

            order.getItems().add(item);
            subtotal = subtotal.add(lineTotal);
        }

        BigDecimal taxRate = new BigDecimal("0.09");
        BigDecimal tax = subtotal.multiply(taxRate);

        order.setSubtotal(subtotal);
        order.setTax(tax);
        order.setTotal(subtotal.add(tax));

        return orderRepo.save(order);
    }

    public Inventory createInventory(Long productId,
                                     Integer stockBefore,
                                     Integer sold,
                                     String notes) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Inventory inventory = Inventory.builder()
                .product(product)
                .date(LocalDateTime.now().toLocalDate())
                .stockBefore(stockBefore)
                .sold(sold)
                .stockAfter(stockBefore - sold)
                .notes(notes)
                .build();

        return inventoryRepo.save(inventory);
    }

    public LoyaltyTransaction addLoyalty(Long customerId,
                                         Integer changeAmount,
                                         String type) {

        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        LoyaltyTransaction trx = LoyaltyTransaction.builder()
                .customer(customer)
                .changeAmount(changeAmount)
                .type(type)
                .timestamp(LocalDateTime.now())
                .build();

        customer.setLoyaltyPoints(customer.getLoyaltyPoints() + changeAmount);

        return loyaltyRepo.save(trx);
    }
}
