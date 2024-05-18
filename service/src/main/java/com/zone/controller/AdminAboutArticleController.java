package com.zone.controller;


import com.zone.dto.PageBean;
import com.zone.result.PageResult;
import com.zone.result.Result;
import com.zone.service.ArticleService;
import com.zone.service.CategoryService;
import com.zone.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员关于文章的操作
 */


@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor // 动态注入Bean注解
public class AdminAboutArticleController {

    private final ArticleService articleService;

    private final CategoryService categoryService;

    private final TagService tagService;


    /**
     * 文章列表
     */
    @GetMapping("/listArticle")
    public Result<PageResult> listArticle(@RequestBody PageBean pageBean){
        log.info("文章列表");
        PageResult allArticles = articleService.listArticle(pageBean);

        return Result.success(allArticles);
    }


    /**
     * 添加分类
     */
    @PostMapping("/addCategory")
    @Transactional
    public Result<Object> addCategory(@RequestParam("categoryName") String categoryName){
        log.info("添加分类");
        Integer categoryId = categoryService.addCategory(categoryName);

        if (categoryId == -1) {
            return Result.error("分类已存在");
        }

        return Result.success("分类添加成功");
    }


    /**
     * 删除分类
     */
    @DeleteMapping("/deleteCategory/{id}")
    @Transactional
    public Result<Object> deleteCategory(@PathVariable Integer id) {
        log.info("删除分类");
        categoryService.deleteCategoryById(id);

        return Result.success("删除成功");
    }


    /**
     * 添加标签
     */
    @PostMapping("/addTag")
    @Transactional
    public Result<Object> addTag(@RequestBody String name){
        log.info("添加标签");
        Integer tagId = tagService.addTag(name);

        if (tagId == -1) {
            return Result.error("标签已存在");
        }

        return Result.success("标签添加成功");
    }


    /**
     * 删除标签
     */
    @DeleteMapping("/deleteTag/{id}")
    @Transactional
    public Result<Object> deleteTag(@PathVariable Integer id) {
        log.info("删除标签");
        tagService.deleteTag(id);

        return Result.success("删除成功");
    }
}
