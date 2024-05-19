package com.zone.service.Impl.admin;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zone.entity.Category;
import com.zone.mapper.ArticleCategoryMapper;
import com.zone.mapper.CategoryMapper;
import com.zone.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private final CategoryMapper categoryMapper;

    private final ArticleCategoryMapper articleCategoryMapper;


    /**
     * 添加分类
     */
    @Override
    public Integer addCategory(String categoryName) {
        // 1.先判断分类是否存在
        Integer categoryId = categoryMapper.findByCategoryName(categoryName);
        log.info("id:{}",categoryId);

        // 2.如果存在，则不添加 返回-1 表示存在该名称
        if (categoryId != null ) {
            return -1;
        }

        // 3.添加数据 并返回ID值
        return categoryMapper.addCategory(categoryName);
    }

    /**
     * 删除分类
     */
    @Override
    public void deleteCategoryById(Integer id) {
        // 1.先删除关联表中的数据
        articleCategoryMapper.deleteByCategoryId(id);
        // 2.再删除分类
        categoryMapper.deleteCategory(id);
    }

}
