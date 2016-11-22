package com.wifiesta.restaurant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wifiesta.restaurant.model.Menu;
import com.wifiesta.restaurant.service.MenuFilter;
import com.wifiesta.restaurant.service.MenuService;

@Controller
@ControllerAdvice
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException e) {
        e.printStackTrace();
    }

}
