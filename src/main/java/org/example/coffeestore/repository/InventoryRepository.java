package org.example.coffeestore.repository;

import org.example.coffeestore.domain.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {}
