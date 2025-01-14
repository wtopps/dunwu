package io.github.dunwu.quickstart.global.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-11
 */
@RestController
@Api(tags = "global")
public class GlobalController {

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }

    @GetMapping("/404")
    public String error404() {
        return "error/404";
    }

    @GetMapping("/500")
    public String error500() {
        return "error/500";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

}
