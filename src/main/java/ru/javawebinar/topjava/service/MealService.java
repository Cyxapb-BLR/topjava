package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.Collection;

public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void delete(int mealId, int userId) {
        ValidationUtil.checkNotFoundWithId(repository.get(mealId, userId), mealId);
    }

    public Meal get(int mealId, int userId) {
        return ValidationUtil.checkNotFoundWithId(repository.get(mealId, userId), mealId);
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public Meal update(Meal meal, int userId) {
        return ValidationUtil.checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }
}