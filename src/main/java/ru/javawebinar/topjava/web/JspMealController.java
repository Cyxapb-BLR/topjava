package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.meal.AbstractMealController;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("meals")
public class JspMealController extends AbstractMealController {

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping
    public String getMeals(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping("create")
    public String create(Model model) {
        Meal meal = new Meal(LocalDateTime.now(), "", 500);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("update")
    public String update(@RequestParam Integer id, Model model) {
        model.addAttribute("meal", get(id));
        return "mealForm";
    }

    @GetMapping("delete")
    public String delete(@RequestParam Integer id) {
        super.delete(id);
        return "redirect:/meals";
    }

    @GetMapping("filter")
    public String filter(Model model,
                         @RequestParam String startDate, @RequestParam String endDate,
                         @RequestParam String startTime, @RequestParam String endTime) {
        model.addAttribute("meals", getBetween(
                parseLocalDate(startDate), parseLocalTime(startTime),
                parseLocalDate(endDate), parseLocalTime(endTime)
        ));
        return "meals";
    }

    @PostMapping
    public String save(@RequestParam(required = false) Integer id, @RequestParam String dateTime,
                       @RequestParam String description, @RequestParam Integer calories) {
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, calories);
        if (id == null) {
            create(meal);
        } else {
            update(meal, id);
        }

        return "redirect:/meals";
    }
}
