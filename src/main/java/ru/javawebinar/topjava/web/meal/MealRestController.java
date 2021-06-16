package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getFilteredAll(LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        fromDate = DateTimeUtil.replacingEmptyDate(fromDate, true);
        toDate = DateTimeUtil.replacingEmptyDate(toDate, false);
        fromTime = DateTimeUtil.replacingEmptyTime(fromTime, true);
        toTime = DateTimeUtil.replacingEmptyTime(toTime, false);
        log.info("get filtered meals from date {} to {}, from time {} to {}", fromDate, toDate, fromTime, toTime);
        List<Meal> meals = service.getFilteredByDate(fromDate, toDate, SecurityUtil.authUserId());
        return MealsUtil.getFilteredTos(meals, SecurityUtil.authUserCaloriesPerDay(), fromTime, toTime);
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("create {}, userId = {}", meal, userId);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete mealId = {}, userId = {}", id, userId);
        service.delete(id, userId);
    }

    public Meal update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        log.info("update {}, userId = {}", meal, userId);
        assureIdConsistent(meal, id);
        return service.update(meal, userId);
    }

}