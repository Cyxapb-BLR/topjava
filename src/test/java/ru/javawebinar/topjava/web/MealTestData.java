package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int TESTED_MEAL_ID = START_SEQ + 2;

    public static final int NOT_FOUND = 10;

    public static final LocalDate startDate = LocalDate.of(2020, 1, 30);
    public static final LocalDate endDate = LocalDate.of(2020, 1, 31);

    public static final Meal userMeal1 = new Meal(TESTED_MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(TESTED_MEAL_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal userMeal3 = new Meal(TESTED_MEAL_ID + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal userMeal4 = new Meal(TESTED_MEAL_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal userMeal5 = new Meal(TESTED_MEAL_ID + 4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal userMeal6 = new Meal(TESTED_MEAL_ID + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal userMeal7 = new Meal(TESTED_MEAL_ID + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static final Meal adminMeal1 = new Meal(TESTED_MEAL_ID + 7, LocalDateTime.of(2021, Month.JUNE, 21, 10, 0), "Завтрак админа", 500);
    public static final Meal adminMeal2 = new Meal(TESTED_MEAL_ID + 8, LocalDateTime.of(2021, Month.JUNE, 21, 20, 0), "Ужин админа", 410);


    public static final List<Meal> userMeals = Stream.of(userMeal1, userMeal2, userMeal3, userMeal4, userMeal5, userMeal6, userMeal7)
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());

    public static final List<Meal> adminMeals = Stream.of(adminMeal1, adminMeal2)
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2021, 6, 22, 12, 0), "New Test Meal", 600);
    }

    public static Meal getUpdateForUser() {
        Meal meal = new Meal(userMeal1);
        meal.setDateTime(LocalDateTime.now());
        meal.setDescription("Updated meal");
        meal.setCalories(300);
        return meal;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }
}
