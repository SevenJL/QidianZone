package com.zone.controller;


import com.zone.dto.PageBean;
import com.zone.result.PageResult;
import com.zone.result.Result;
import com.zone.service.AdminService;
import com.zone.service.ArticleCategoryService;
import com.zone.service.ArticleService;
import com.zone.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Api(tags = "管理员管理")
@Slf4j
@RequiredArgsConstructor // 动态注入Bean注解
public class AdminController {

    private final UserService userService;

    private final AdminService adminService;

    private final ArticleService articleService;

    private final ArticleCategoryService articleCategoryService;

    /**
     * 分页查询用户列表
     */
    @GetMapping("/list")
    @ApiOperation("获取用户列表")
    public Result<PageResult> listUser(@RequestBody PageBean pageBean){
        PageResult users = userService.listUser(pageBean);

        return Result.success(users);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除用户")
    @Transactional // 事务管理
    public Result<Object> deleteById(@PathVariable String id){
        adminService.deleteById(id);

        return Result.success("删除成功");
    }


    /**
     * 文章列表
     */
    @GetMapping("/listArticle")
    @ApiOperation("显示所有文章")
    public Result<PageResult> listArticle(@RequestBody PageBean pageBean){
        PageResult allArticles = articleService.listArticle(pageBean);

        return Result.success(allArticles);
    }

    /**
     * 添加分类
     */
    @PostMapping("/addCategory")
    @ApiOperation("添加分类")
    public Result<Object> addCategory(@RequestBody String categoryName){
        Integer categoryId = articleCategoryService.addCategory(categoryName);

        if (categoryId == -1) {
            return Result.error("分类已存在");
        }
        return Result.success("分类添加成功");
    }


}
