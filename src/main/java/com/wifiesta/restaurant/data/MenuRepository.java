package com.wifiesta.restaurant.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wifiesta.restaurant.model.Menu;

@Service
public class MenuRepository {

    @Value(value = "${data.menu.sourcePath}")
    private String filePath;

    private Object fileLock = new Object(); // Lock to ensure that we access the file only once at a time
    private ObjectMapper objectMapper;

    public MenuRepository() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public List<Menu> getAll() {
        synchronized (this.fileLock) {
            return this.readAllFromFile();
        }
    }

    public void deleteAll() {
        synchronized (this.fileLock) {
            this.saveToFile(new ArrayList<>());
        }
    }

    public void saveMenu(Menu menu) {
        synchronized (this.fileLock) {
            List<Menu> menus = this.readAllFromFile();
            menus.add(menu);
            this.saveToFile(menus);
        }
    }

    // Not syncronized, callers should do that.
    private List<Menu> readAllFromFile() {
        try {
            InputStream stream = new FileInputStream(this.filePath);
            return this.objectMapper.readValue(stream, new TypeReference<List<Menu>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Not syncronized, callers should do that.
    private void saveToFile(List<Menu> menus) {
        try {
            OutputStream stream = new FileOutputStream(this.filePath);
            this.objectMapper.writeValue(stream, menus);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
