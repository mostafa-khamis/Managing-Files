package com.example.stc.task3.STC.controller;

import com.example.stc.task3.STC.entity.Files;
import com.example.stc.task3.STC.entity.Item;
import com.example.stc.task3.STC.service.FilesService;
import com.example.stc.task3.STC.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class FilesController {
    public static final String SEPARATOR = "/";

    @Autowired
    private FilesService filesService;
    @Autowired
    private ItemService itemService;

    @PostMapping("/{space}/{folder}/upload")
    public String uploadFileIfNotExistInsideFolder(@RequestParam("file") MultipartFile file,
                                                   @PathVariable String space, @PathVariable String folder) throws Exception {
        if (itemService.spaceExist(space)) {
            if (itemService.folderExist(folder)) {
                if (filesService.fileExist(file.getOriginalFilename(), space + SEPARATOR + folder)) {
                    return "File already Exist";
                } else {
                    Item item = itemService.createFile(file.getOriginalFilename(), space + SEPARATOR + folder);
                    Files files = filesService.uploadFile(file, item.getId());

                    String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/download/")
                            .path(String.valueOf(files.getItem().getId()))
                            .toUriString();
                    return "File Created download URL:  " + downloadUrl;
                }

            }
            return "Folder doesn't exist";
        }
        return "Space doesn't exist";
    }

    @PostMapping("/{space}/upload")
    public String uploadFileIfNotExistInsideSpace(@RequestParam("file") MultipartFile file, @PathVariable String space) throws Exception {
        if (itemService.spaceExist(space)) {
            if (filesService.fileExist(file.getOriginalFilename(), space)) {
                return "File already Exist";
            } else {
                Item item = itemService.createFile(file.getOriginalFilename(), space);
                Files files = filesService.uploadFile(file, item.getId());

                String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/download/")
                        .path(String.valueOf(files.getId()))
                        .toUriString();
                return "File Created download URL:  " + downloadUrl;
            }
        }
        return "Space doesn't exist";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable int fileId) throws Exception {

        Files file = filesService.getFile(fileId);
        //Add some header info so browser can know what to do wth this request
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "file; filename=\"" + file.getItem().getName() + "\"")
                .body(new ByteArrayResource(file.getBinaryData()));

    }
}
