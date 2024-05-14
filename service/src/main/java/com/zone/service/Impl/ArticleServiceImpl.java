package com.zone.service.Impl;

import com.zone.constant.ArticleAboutConstant;
import com.zone.dto.ArticlePublishDTO;
import com.zone.entity.Article;
import com.zone.mapper.ArticleMapper;
import com.zone.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    // 注入Mapper
    @Autowired
    private  ArticleMapper articleMapper;

    /**
     * 发布文章
     */
    @Override
    public void publish(ArticlePublishDTO articlePublishDTO) {

        // 拷贝数据
        Article article = Article.builder()
                .title(articlePublishDTO.getTitle()) // 标题
                .content(articlePublishDTO.getContent()) // 内容
                .categoryId(articlePublishDTO.getCategoryId()) // 分类id
                .articleViewPower(articlePublishDTO.getArticleViewPower()) // 访问权限
                .articleLike(ArticleAboutConstant.DEFAULT_ARTICLE_LIKE_VALUE) // 点赞数
                .articleView(ArticleAboutConstant.DEFAULT_ARTICLE_VIEW) // 访问数
                .articleViewPower(ArticleAboutConstant.DEFAULT_ARTICLE_VIEW_POWER) // 访问权限
                .articleCommentCount(ArticleAboutConstant.DEFAULT_ARTICLE_COMMENT_COUNT) // 评论数
                .createTime(LocalDateTime.now()) // 创建时间
                .updateTime(LocalDateTime.now()) // 更新时间
                .deleteTime(ArticleAboutConstant.DEFAULT_ARTICLE_DELETE_TIME) // 删除时间
                .commentId(ArticleAboutConstant.DEFAULT_COMMENT_ID) // 评论
                //TODO 根据JWT令牌解析Token 去获取当前创建人的ID 从而获取name 暂时写死
                .creator(ArticleAboutConstant.DEFAULT_CREATOR) // 创建者
                .build();
        articleMapper.insert(article);
    }
}
