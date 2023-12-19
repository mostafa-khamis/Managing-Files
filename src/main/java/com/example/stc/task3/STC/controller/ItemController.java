package com.example.stc.task3.STC.controller;

import com.example.stc.task3.STC.entity.Item;
import com.example.stc.task3.STC.entity.PermissionGroups;
import com.example.stc.task3.STC.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;

    }

    @GetMapping("/createSpace/{spaceName}")
    String createSpaceIfNotExist(@PathVariable String spaceName) {
        if (itemService.spaceExist(spaceName)) {
            return "Space Already Exist";
        } else {
            Item space = itemService.createSpace(spaceName, 1);
            return "Space Created Successfully";
        }
    }

    @GetMapping("/{space}/createFolder/{folderName}")
    String createFolderIfNotExist(@PathVariable String folderName, @PathVariable String space) {
        if (itemService.spaceExist(space)) {
            if (itemService.folderExist(folderName)) {
                return "Folder Already Exist";
            } else {

                itemService.createFolder(folderName, space);
                return "Folder Created Successfully";
            }

        } else {
            return "Space Not Exit create one first";
        }
    }

    @GetMapping("/{space}")
    public ArrayList<String> getAllinSpace(@PathVariable String space) {
        if (itemService.spaceExist(space)) {

            return itemService.getItemsByPath(space);
        }

        return null;
    }

    @GetMapping("/{space}/{folder}")
    public ArrayList<String> getAllinFolder(@PathVariable String space, @PathVariable String folder) {

        if (itemService.folderExist(folder)) {

            return itemService.getItemsByPath(space + "/" + folder);
        }

        return null;
    }
}