package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            MealRepository mealRepository = appCtx.getBean(MealRepository.class);
            mealRepository.getAll(1).forEach(System.out::println);
            mealRepository.getAll(2).forEach(System.out::println);

            MealRestController controller = appCtx.getBean(MealRestController.class);
            controller.getFilteredAll(
                    LocalDate.of(2020, 1, 31),
                    LocalDate.of(2020, 1, 31),
                    LocalTime.of(10, 0),
                    LocalTime.of(21, 0)).
                    forEach(System.out::println);
            controller.get(5);
            controller.getAll().forEach(System.out::println);
        }
    }
}
