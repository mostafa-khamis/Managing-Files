package com.example.stc.task3.STC.service;

import com.example.stc.task3.STC.Repo.FilesRepository;
import com.example.stc.task3.STC.Repo.ItemRepository;
import com.example.stc.task3.STC.entity.Files;
import com.example.stc.task3.STC.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FilesService {
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private ItemRepository itemRepository;

    public Files uploadFile(MultipartFile file, int id) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Item item = itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Permission group not found"));

        try {
            if (fileName.contains("..")) {
                throw new Exception("File name invalid " + fileName);
            }
            Files files = new Files(file.getBytes(), file.getContentType());
            files.setItem(item);

            return filesRepository.save(files);

        } catch (Exception e) {
            throw new Exception("Can't save the file");
        }

    }

    public Files getFile(int fileId) throws Exception {

        return filesRepository.findById(fileId)
                .orElseThrow(() ->
                        new Exception("File with id: " + fileId + " not found"));
    }

    public Boolean fileExist(String filename, String thePath) {
        List<Item> files = itemRepository.findAll();
        for (Item file : files) {
            if (filename.equals(file.getName()) && file.getPath().equals(thePath)) {
                return true;
            }
        }
        return false;

    }
}
