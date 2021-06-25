package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Util;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.web.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.web.UserTestData.USER_ID;
import static ru.javawebinar.topjava.util.DateTimeUtil.atStartOfDayOrMin;
import static ru.javawebinar.topjava.util.DateTimeUtil.atStartOfNextDayOrMax;
import static ru.javawebinar.topjava.web.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(TESTED_MEAL_ID, USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void delete() {
        service.delete(TESTED_MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(TESTED_MEAL_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> mealList = userMeals.stream()
                .filter(meal -> Util.isBetweenHalfOpen(meal.getDateTime(), atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate)))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        System.out.println(mealList);
        assertMatch(service.getBetweenInclusive(startDate, endDate, USER_ID), mealList);
    }

    @Test
    public void getAll() {
        List<Meal> allForUser = service.getAll(USER_ID);
        assertMatch(allForUser, userMeals);
        List<Meal> allForAdmin = service.getAll(ADMIN_ID);
        assertMatch(allForAdmin, adminMeals);
    }

    @Test
    public void update() {
        Meal updated = getUpdateForUser();
        service.update(updated, USER_ID);
        assertMatch(service.get(TESTED_MEAL_ID, USER_ID), updated);
    }

    @Test
    public void create() {
        Meal createdMeal = service.create(getNew(), USER_ID);
        Integer id = createdMeal.getId();
        Meal newMeal = getNew();
        newMeal.setId(id);
        assertMatch(createdMeal, newMeal);
        assertMatch(service.get(id, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () -> service.create(duplicateDateTimeMeal, USER_ID));
    }

    @Test
    public void getStrangerMeal() {
        assertThrows(NotFoundException.class, () -> service.get(TESTED_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void deleteStrangerMeal() {
        assertThrows(NotFoundException.class, () -> service.get(TESTED_MEAL_ID + 7, USER_ID));
    }

    @Test
    public void updateStrangerMeal() {
        assertThrows(NotFoundException.class, () -> service.update(userMeal1, ADMIN_ID));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));

    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void updateNotFound() {
        assertThrows(NotFoundException.class, () -> service.update(userMeal1, NOT_FOUND));
    }
}