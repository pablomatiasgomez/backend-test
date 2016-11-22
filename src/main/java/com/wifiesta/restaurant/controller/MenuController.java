package com.wifiesta.restaurant.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wifiesta.restaurant.model.Menu;
import com.wifiesta.restaurant.service.MenuFilter;
import com.wifiesta.restaurant.service.MenuService;

@Controller
@RequestMapping("/menus")
public class MenuController
    implements InitializingBean {

    @Autowired
    private MenuService menuService;
    private Map<String, Supplier<Map<?, List<Menu>>>> groupsBy;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Menu> getById(@PathVariable String id) {
        Menu menu = this.menuService.getById(id);
        if (menu == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(menu, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Menu>> getMenusFiltered(MenuFilter menuFilter) {

        List<Menu> menus = this.menuService.getFiltered(menuFilter);

        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @RequestMapping(value = "/grouped", method = RequestMethod.GET)
    public ResponseEntity<Map<?, List<Menu>>> getMenusGrouped(@RequestParam String by) {

        if (this.groupsBy.containsKey(by)) {
            return new ResponseEntity<>(this.groupsBy.get(by).get(), HttpStatus.OK);
        } else {
            throw new RuntimeException("Cannot group by " + by);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.groupsBy = new HashMap<>();
        this.groupsBy.put("ranking", this.menuService::getGroupedByRanking);
        this.groupsBy.put("price", this.menuService::getGroupedByPrice);
    }

}
