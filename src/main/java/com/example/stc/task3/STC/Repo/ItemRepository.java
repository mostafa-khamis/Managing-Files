package com.example.stc.task3.STC.Repo;

import com.example.stc.task3.STC.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Integer> {
    List<Item> findByPath(String path);
}
