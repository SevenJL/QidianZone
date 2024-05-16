package com.zone.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zone.dto.LoginDTO;
import com.zone.entity.Article;
import com.zone.entity.ArticleCategory;
import com.zone.mapper.AdminMapper;
import com.zone.mapper.ArticleCategoryMapper;
import com.zone.mapper.ArticleMapper;
import com.zone.service.AdminService;
import com.zone.service.ArticleCategoryService;
import com.zone.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements ArticleCategoryService {

}
