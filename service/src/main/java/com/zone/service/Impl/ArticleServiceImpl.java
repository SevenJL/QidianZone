package com.zone.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zone.constant.ArticleAboutConstant;
import com.zone.constant.DeleteConstant;
import com.zone.dto.ArticleEditDTO;
import com.zone.dto.ArticlePublishDTO;
import com.zone.dto.PageSearchDTO;
import com.zone.entity.Article;
import com.zone.mapper.ArticleMapper;
import com.zone.result.PageResult;
import com.zone.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

        // 拷贝 添加 文章数据
        Article article = Article.builder()
                .title(articlePublishDTO.getTitle()) // 标题
                .content(articlePublishDTO.getContent()) // 内容
                .articleViewPower(articlePublishDTO.getArticleViewPower()) // 访问权限
                .articleLike(ArticleAboutConstant.DEFAULT_ARTICLE_LIKE_VALUE) // 点赞数
                .articleView(ArticleAboutConstant.DEFAULT_ARTICLE_VIEW) // 访问数
                .articleCommentCount(ArticleAboutConstant.DEFAULT_ARTICLE_COMMENT_COUNT) // 评论数
                .createTime(LocalDateTime.now()) // 创建时间
                .updateTime(LocalDateTime.now()) // 更新时间
                .deleteTime(ArticleAboutConstant.DEFAULT_ARTICLE_DELETE_TIME) // 删除时间
                //TODO 根据JWT令牌解析Token 去获取当前创建人的ID 从而获取name 暂时写死
                .creator(ArticleAboutConstant.DEFAULT_CREATOR) // 创建者
                .status(ArticleAboutConstant.DEFAULT_STATUS) // 状态
                .deleteStatus(ArticleAboutConstant.DEFAULT_DELETE_STATUS)
                .build();

        //TODO 添加文章标签到article_category数据库中
        //TODO 添加文章类型article_tag到数据库中
        articleMapper.insert(article); // 插入文章数据
        log.info("发布文章成功");
    }


    /**
     * 编辑文章
     */
    @Override
    public void edit(ArticleEditDTO articleEditDTO) {
        // 拷贝数据
        Article article = Article.builder()
                .id(articleEditDTO.getId()) // id
                .title(articleEditDTO.getTitle()) // 标题
                .content(articleEditDTO.getContent()) // 内容
                .articleViewPower(articleEditDTO.getArticleViewPower()) // 访问权限
                .updateTime(LocalDateTime.now()) // 更新时间
                .build();
        articleMapper.update(article);
        log.info("编辑文章成功");
    }

    /**
     * 删除文章
     * 逻辑删除
     */
    @Override
    public void delete(Integer id) {
        Article article = Article.builder()
                .deleteStatus(DeleteConstant.DISABLE)
                .id(id)
                .deleteTime(LocalDateTime.now())
                .build();
        articleMapper.update(article);
        log.info("删除文章成功");
    }

    /**
     * 根据文章题目进行模糊搜索
     * @param pageSearchDTO 文章题目
     */
    @Override
    public PageInfo<Article> search(PageSearchDTO pageSearchDTO) {
        log.info("根据文章题目进行模糊搜索:{}",pageSearchDTO);

        // 使用PageHelper分页查询
        PageHelper.startPage(pageSearchDTO.getPageNum(), pageSearchDTO.getPageSize());
        List<Article> articlePage = articleMapper.search(pageSearchDTO);
        log.info("根据文章题目进行模糊搜索成功");
        return new PageInfo<>(articlePage);
    }
}
