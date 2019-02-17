package WebApp.controller;

import WebApp.entity.User;
import WebApp.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @Autowired
    private MainService mainService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<User> getAllUsers(){

        mainService.save(new User("max", "1234567", "max@mail.ru"));
        return mainService.getAll();
    }

    @RequestMapping(value = "/main/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@PathVariable("id") Integer userId) {
        return mainService.getById(userId);
    }

}
