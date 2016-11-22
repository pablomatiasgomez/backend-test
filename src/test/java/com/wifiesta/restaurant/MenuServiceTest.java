package com.wifiesta.restaurant;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wifiesta.restaurant.model.Menu;
import com.wifiesta.restaurant.model.Price;
import com.wifiesta.restaurant.model.WeekTimeInterval;
import com.wifiesta.restaurant.service.MenuFilter;
import com.wifiesta.restaurant.service.MenuService;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Before
    public void addData() {

        Menu menu;

        menu = new Menu();
        menu.setId("1");
        menu.setName("Cena 1");
        menu.setDescription("cena numero 1");
        menu.addValidWeekTime(new WeekTimeInterval(DayOfWeek.SUNDAY, LocalTime.of(20, 0, 0), LocalTime.of(23, 0, 0)));
        menu.setPrice(new Price("ARS", new BigDecimal(14)));
        menu.setRanking(5);
        menu.setValidFrom(LocalDateTime.of(2016, 3, 1, 0, 0));
        menu.setValidTo(LocalDateTime.of(2016, 4, 1, 0, 0));
        this.menuService.saveMenu(menu);

        menu = new Menu();
        menu.setId("2");
        menu.setName("Cena 2");
        menu.setDescription("cena numero 2");
        menu.addValidWeekTime(new WeekTimeInterval(DayOfWeek.SUNDAY, LocalTime.of(20, 0, 0), LocalTime.of(23, 0, 0)));
        menu.setPrice(new Price("USD", new BigDecimal(100)));
        menu.setRanking(2);
        menu.setValidFrom(LocalDateTime.of(2016, 3, 1, 0, 0));
        menu.setValidTo(LocalDateTime.of(2016, 4, 1, 0, 0));
        this.menuService.saveMenu(menu);

        menu = new Menu();
        menu.setId("3");
        menu.setName("Cena 3");
        menu.setDescription("cena numero 3");
        menu.addValidWeekTime(new WeekTimeInterval(DayOfWeek.SUNDAY, LocalTime.of(20, 0, 0), LocalTime.of(23, 0, 0)));
        menu.setPrice(new Price("USD", new BigDecimal(100)));
        menu.setRanking(1);
        menu.setValidFrom(LocalDateTime.of(2016, 3, 1, 0, 0));
        menu.setValidTo(LocalDateTime.of(2016, 4, 1, 0, 0));
        this.menuService.saveMenu(menu);

        menu = new Menu();
        menu.setId("4");
        menu.setName("Cena 4");
        menu.setDescription("cena numero 4");
        menu.addValidWeekTime(new WeekTimeInterval(DayOfWeek.SATURDAY, LocalTime.of(20, 0, 0), LocalTime.of(23, 0, 0)));
        menu.setPrice(new Price("ARS", new BigDecimal(50)));
        menu.setRanking(1);
        menu.setValidFrom(LocalDateTime.of(2017, 5, 1, 0, 0));
        menu.setValidTo(LocalDateTime.of(2017, 5, 1, 0, 0));
        this.menuService.saveMenu(menu);

        menu = new Menu();
        menu.setId("5");
        menu.setName("Cena 5");
        menu.setDescription("cena numero 5");
        menu.addValidWeekTime(new WeekTimeInterval(DayOfWeek.MONDAY, LocalTime.of(17, 0, 0), LocalTime.of(23, 0, 0)));
        menu.setPrice(new Price("ARS", new BigDecimal(506)));
        menu.setRanking(1);
        menu.setValidFrom(LocalDateTime.of(2017, 5, 1, 0, 0));
        menu.setValidTo(LocalDateTime.of(2017, 5, 1, 0, 0));
        this.menuService.saveMenu(menu);

        menu = new Menu();
        menu.setId("6");
        menu.setName("Cena 6");
        menu.setDescription("cena numero 6");
        menu.addValidWeekTime(new WeekTimeInterval(DayOfWeek.MONDAY, LocalTime.of(17, 0, 0), LocalTime.of(23, 0, 0)));
        menu.setPrice(new Price("ARS", new BigDecimal(506)));
        menu.setRanking(5);
        menu.setValidFrom(LocalDateTime.of(2017, 5, 1, 0, 0));
        menu.setValidTo(LocalDateTime.of(2017, 5, 1, 0, 0));
        this.menuService.saveMenu(menu);
    }

    @Test
    public void testSavingAndRetrievingMenu() {
        String id = "222";

        Menu menu = new Menu();
        menu = new Menu();
        menu.setId(id);
        menu.setName("Cena 2");
        menu.setDescription("cena numero 2");
        menu.addValidWeekTime(new WeekTimeInterval(DayOfWeek.SUNDAY, LocalTime.of(20, 0, 0), LocalTime.of(23, 0, 0)));
        menu.setPrice(new Price("USD", new BigDecimal(100)));
        menu.setRanking(2);
        menu.setValidFrom(LocalDateTime.of(2016, 3, 1, 0, 0));
        menu.setValidTo(LocalDateTime.of(2016, 4, 1, 0, 0));
        this.menuService.saveMenu(menu);

        Assert.assertEquals(menu, this.menuService.getById(id));
    }

    @Test
    public void testDeletingAll() {
        this.menuService.deleteAll();
        Assert.assertTrue(this.menuService.getAll().isEmpty());
    }

    @Test
    public void testGroupingByRanking() {
        this.addData();
        this.menuService.getGroupedByRanking().entrySet().forEach(entry -> {
            entry.getValue().forEach(menu -> {
                Assert.assertEquals(entry.getKey(), menu.getRanking());
            });
        });
    }

    @Test
    public void testGroupingByPrice() {
        this.addData();
        this.menuService.getGroupedByPrice().entrySet().forEach(entry -> {
            entry.getValue().forEach(menu -> {
                Assert.assertEquals(entry.getKey(), menu.getPrice());
            });
        });
    }

    @Test
    public void testFilteredWith2DaysOfValidity() {
        this.addData();
        LocalDateTime validFrom = LocalDateTime.of(2016, 5, 1, 0, 0);
        LocalDateTime validTo = LocalDateTime.of(2016, 5, 2, 0, 0);

        MenuFilter menuFilter = new MenuFilter(validFrom, validTo);
        this.menuService.getFiltered(menuFilter).forEach(menu -> {
            Assert.assertTrue(this.dateTimeIsBeforeOrEquals(menu.getValidFrom(), validFrom));
            Assert.assertTrue(this.dateTimeIsAfterOrEquals(menu.getValidTo(), validTo));
        });
    }

    @Test
    public void testFilteredWith1YearOfValidity() {
        this.addData();
        LocalDateTime validFrom = LocalDateTime.of(2016, 5, 1, 0, 0);
        LocalDateTime validTo = LocalDateTime.of(2017, 5, 1, 0, 0);

        MenuFilter menuFilter = new MenuFilter(validFrom, validTo);
        this.menuService.getFiltered(menuFilter).forEach(menu -> {
            Assert.assertTrue(this.dateTimeIsBeforeOrEquals(menu.getValidFrom(), validFrom));
            Assert.assertTrue(this.dateTimeIsAfterOrEquals(menu.getValidTo(), validTo));
        });
    }

    @Test
    public void testFilteredWith2YearsOfValidity() {
        this.addData();
        LocalDateTime validFrom = LocalDateTime.of(2015, 9, 1, 0, 0);
        LocalDateTime validTo = LocalDateTime.of(2017, 9, 1, 0, 0);

        MenuFilter menuFilter = new MenuFilter(validFrom, validTo);
        this.menuService.getFiltered(menuFilter).forEach(menu -> {
            Assert.assertTrue(this.dateTimeIsBeforeOrEquals(menu.getValidFrom(), validFrom));
            Assert.assertTrue(this.dateTimeIsAfterOrEquals(menu.getValidTo(), validTo));
        });
    }

    @Test
    public void testFilteredWithValidDatesIsContained() {
        Menu menu;
        menu = new Menu();
        menu.setId("contained");
        menu.setName("Cena 1");
        menu.setDescription("cena numero 1");
        menu.addValidWeekTime(new WeekTimeInterval(DayOfWeek.SUNDAY, LocalTime.of(20, 0, 0), LocalTime.of(23, 0, 0)));
        menu.setPrice(new Price("ARS", new BigDecimal(14)));
        menu.setRanking(5);
        menu.setValidFrom(LocalDateTime.of(2016, 3, 1, 0, 0));
        menu.setValidTo(LocalDateTime.of(2016, 4, 1, 0, 0));
        this.menuService.saveMenu(menu);

        LocalDateTime validFrom = LocalDateTime.of(2016, 3, 10, 0, 0);
        LocalDateTime validTo = LocalDateTime.of(2016, 3, 15, 0, 0);

        MenuFilter menuFilter = new MenuFilter(validFrom, validTo);
        Assert.assertTrue(this.menuService.getFiltered(menuFilter).contains(menu));
    }

    @Test
    public void testFilteredWithValidDatesIsNotContained() {
        Menu menu;
        menu = new Menu();
        menu.setId("not-contained");
        menu.setName("Cena 1");
        menu.setDescription("cena numero 1");
        menu.addValidWeekTime(new WeekTimeInterval(DayOfWeek.SUNDAY, LocalTime.of(20, 0, 0), LocalTime.of(23, 0, 0)));
        menu.setPrice(new Price("ARS", new BigDecimal(14)));
        menu.setRanking(5);
        menu.setValidFrom(LocalDateTime.of(2016, 2, 1, 0, 0));
        menu.setValidTo(LocalDateTime.of(2016, 3, 1, 0, 0));
        this.menuService.saveMenu(menu);

        LocalDateTime validFrom = LocalDateTime.of(2016, 3, 10, 0, 0);
        LocalDateTime validTo = LocalDateTime.of(2016, 3, 15, 0, 0);

        MenuFilter menuFilter = new MenuFilter(validFrom, validTo);
        Assert.assertFalse(this.menuService.getFiltered(menuFilter).contains(menu));
    }

    @Test
    public void testFilteredOnForSunday() {
        this.addData();
        DayOfWeek dayOfWeek = DayOfWeek.SUNDAY;

        MenuFilter menuFilter = new MenuFilter(dayOfWeek);
        this.menuService.getFiltered(menuFilter).forEach(menu -> {
            menu.getValidWeekTimes().stream().anyMatch(validWeekTime -> {
                return dayOfWeek.equals(validWeekTime.getDayOfWeek());
            });
        });
    }

    private boolean dateTimeIsAfterOrEquals(LocalDateTime localDateTime, LocalDateTime other) {
        return localDateTime.compareTo(other) >= 0;
    }

    private boolean dateTimeIsBeforeOrEquals(LocalDateTime localDateTime, LocalDateTime other) {
        return localDateTime.compareTo(other) <= 0;
    }

}
