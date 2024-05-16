package com.zone.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zone.entity.ArticleCategory;
import com.zone.mapper.ArticleCategoryMapper;
import com.zone.service.ArticleCategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements ArticleCategoryService {

}
