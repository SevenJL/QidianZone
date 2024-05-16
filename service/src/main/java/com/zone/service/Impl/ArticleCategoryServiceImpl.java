package com.zone.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zone.entity.ArticleCategory;
import com.zone.mapper.ArticleCategoryMapper;
import com.zone.service.ArticleCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements ArticleCategoryService {

    private final ArticleCategoryMapper articleCategoryMapper;

    @Override
    public Integer addCategory(String categoryName) {
        // 1.先判断分类是否存在
        Integer categoryId = articleCategoryMapper.findByCategoryName(categoryName);
        log.info("id:{}",categoryId);

        // 2.如果存在，则不添加 返回-1 表示存在该名称
        if (categoryId != null ) {
            return -1;
        }

        // 3.添加数据 并返回ID值
        return articleCategoryMapper.addCategory(categoryName);
    }
}
