package com.zone.controller;


import com.zone.dto.PageBean;
import com.zone.result.PageResult;
import com.zone.result.Result;
import com.zone.service.ArticleService;
import com.zone.service.CategoryService;
import com.zone.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "管理员管理")
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
    @Transactional
    public Result<Object> addCategory(@RequestParam("categoryName") String categoryName){
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
    @ApiOperation("删除分类")
    @Transactional
    public Result<Object> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategoryById(id);

        return Result.success("删除成功");
    }


    /**
     * 添加标签
     */
    @PostMapping("/addTag")
    @ApiOperation("添加标签")
    @Transactional
    public Result<Object> addTag(@RequestBody String name){
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
    @ApiOperation("删除标签")
    @Transactional
    public Result<Object> deleteTag(@PathVariable Integer id) {
        tagService.deleteTag(id);

        return Result.success("删除成功");
    }
}
