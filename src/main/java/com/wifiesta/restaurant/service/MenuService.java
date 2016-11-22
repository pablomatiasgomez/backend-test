package com.wifiesta.restaurant.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.wifiesta.restaurant.data.MenuRepository;
import com.wifiesta.restaurant.model.Menu;
import com.wifiesta.restaurant.model.Price;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getAll() {
        return this.menuRepository.getAll();
    }

    public void deleteAll() {
        this.menuRepository.deleteAll();
    }

    public void saveMenu(Menu menu) {
        Assert.notNull(menu, "The menu can't be null!!");
        Assert.notNull(menu.getId(), "The menu id can't be null!!");

        this.menuRepository.saveMenu(menu);
    }

    // This kind of filters can be moved to the data layer if other storage engine is used (not a file..)
    public Menu getById(String id) {
        return this.getAll().stream().filter(menu -> id.equals(menu.getId())).findAny().orElse(null);
    }

    public List<Menu> getFiltered(MenuFilter menuFilter) {
        return this.getAll().stream().filter(menuFilter::menuApplies).collect(Collectors.toList());
    }

    public Map<Price, List<Menu>> getGroupedByPrice() {
        return this.getGroupedByProperty(Menu::getPrice);
    }

    public Map<Integer, List<Menu>> getGroupedByRanking() {
        return this.getGroupedByProperty(Menu::getRanking);
    }

    private <T> Map<T, List<Menu>> getGroupedByProperty(Function<Menu, T> grouper) {
        return this.getAll().stream().collect(Collectors.groupingBy(grouper));
    }

}
