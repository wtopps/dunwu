package io.github.dunwu.quickstart.controller;

import io.github.dunwu.core.Page;
import io.github.dunwu.core.Result;
import io.github.dunwu.quickstart.entity.User;
import io.github.dunwu.quickstart.service.UserService;
import io.github.dunwu.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zhang Peng
 * @since 2019-04-21
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User> {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping("count")
    public Result<Integer> count(User entity) {
        return super.count(entity);
    }

    @Override
    @GetMapping("list")
    public Result<User> list(User entity) {
        return super.list(entity);
    }

    @Override
    @GetMapping("listWithPage")
    public Result<User> listWithPage(User entity, Page page) {
        return super.listWithPage(entity, page);
    }

    @Override
    @PostMapping("save")
    public Result save(User entity) {
        return super.save(entity);
    }

    @Override
    @PostMapping("saveBatch")
    public Result saveBatch(List<User> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    @PostMapping("remove")
    public Result remove(User entity) {
        return super.remove(entity);
    }
}
