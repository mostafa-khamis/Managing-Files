package com.example.stc.task3.STC.service;

import com.example.stc.task3.STC.Repo.ItemRepository;
import com.example.stc.task3.STC.Repo.PermissionGroupsRepository;
import com.example.stc.task3.STC.entity.Item;
import com.example.stc.task3.STC.entity.PermissionGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    private ItemRepository itemRepository;
    private PermissionGroupsRepository permissionGroupsRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, PermissionGroupsRepository permissionGroupsRepository) {
        this.itemRepository = itemRepository;
        this.permissionGroupsRepository = permissionGroupsRepository;
    }

    public Item createSpace(String spaceName, int permissionGroupsId) {
        PermissionGroups permissionGroup = permissionGroupsRepository.findById(permissionGroupsId)
                .orElseThrow(() -> new IllegalArgumentException("Permission group not found"));

        Item space = new Item("space", spaceName, "");
        space.setPermissionGroups(permissionGroup);


        return itemRepository.save(space);
    }

    public Item createFolder(String folderName, String path) {
        Item folder = new Item("folder", folderName, path);
        PermissionGroups permissionGroups = permissionGroupsRepository.findById(1).orElseThrow(
                () -> new IllegalArgumentException("Permission group not found")
        );
        folder.setPermissionGroups(permissionGroups);
        return itemRepository.save(folder);
    }

    public Item createFile(String fileName, String path) {
        PermissionGroups permissionGroups = permissionGroupsRepository.findById(1).orElseThrow(
                () -> new IllegalArgumentException("Permission group not found")
        );
        Item file = new Item("file", fileName, path);
        file.setPermissionGroups(permissionGroups);

        return itemRepository.save(file);
    }

    public boolean spaceExist(String spaceName) {
        List<Item> items = itemRepository.findAll();
        for (Item type : items) {
            if (spaceName.equals(type.getName()) && type.getType().equals("space")) {
                return true;
            }
        }
        return false;
    }

    public boolean folderExist(String folderName) {
        List<Item> items = itemRepository.findAll();
        for (Item name : items) {
            if (folderName.equals(name.getName()) && name.getType().equals("folder")) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getItemsByPath(String path) {
        List<Item> items = itemRepository.findByPath(path);
        System.out.println(path);
        ArrayList<String> nameArr = new ArrayList<>();
        for (Item item : items) {

            nameArr.add(item.getName());
        }

        return nameArr;
    }
}
