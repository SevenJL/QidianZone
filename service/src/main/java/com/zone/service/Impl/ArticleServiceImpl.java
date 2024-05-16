package com.zone.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zone.constant.ArticleAboutConstant;
import com.zone.constant.DeleteConstant;
import com.zone.dto.ArticleEditDTO;
import com.zone.dto.ArticlePublishDTO;
import com.zone.dto.PageSearchDTO;
import com.zone.entity.Article;
import com.zone.entity.ArticleCategory;
import com.zone.entity.ArticleTag;
import com.zone.mapper.ArticleCategoryMapper;
import com.zone.mapper.ArticleMapper;
import com.zone.mapper.ArticleTagMapper;
import com.zone.result.PageResult;
import com.zone.service.ArticleService;
import com.zone.vo.ArticleVO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service // 注解
@Slf4j // 日志
@RequiredArgsConstructor // 构造方法注解 注入bean对象
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    // 注入Mapper
    private final ArticleMapper articleMapper;

    private final ArticleCategoryMapper articleCategoryMapper;

    private final ArticleTagMapper articleTagMapper;

    /**
     * 发布文章
     */
    @Override
    public void publish(ArticlePublishDTO articlePublishDTO) {

        // 1.拷贝/添加 文章数据
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
                .deleteStatus(ArticleAboutConstant.DEFAULT_DELETE_STATUS) // 删除状态
                .build();

        // 插入文章数据
        articleMapper.insert(article);



        // 2.添加文章类型article_category到数据库中
        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setArticleId(article.getId());

        articlePublishDTO.getCategoryId().forEach(id -> {
            // 遍历添加
            articleCategory.setCategoryId(id);
            articleCategoryMapper.insert(articleCategory);
        });



        // 3.添加文章标签article_tag到数据库中
        ArticleTag articleTag = new ArticleTag();
        articleTag.setArticleId(article.getId());

        articlePublishDTO.getTagId().forEach(id -> {
            // 遍历添加
            articleTag.setTagId(id);
            articleTagMapper.insertArticleTag(articleTag);
        });

        log.info("发布文章成功");
    }


    /**
     * 编辑文章
     */

    @Override
    public void edit(ArticleEditDTO articleEditDTO) {
        // 1.拷贝数据
        Article article = Article.builder()
                .id(articleEditDTO.getId()) // id
                .title(articleEditDTO.getTitle()) // 标题
                .content(articleEditDTO.getContent()) // 内容
                .articleViewPower(articleEditDTO.getArticleViewPower()) // 访问权限
                .updateTime(LocalDateTime.now()) // 更新时间
                .build();
        articleMapper.update(article);

        // 2.根据文章ID删除文章分类
        articleCategoryMapper.deleteByArticleId(articleEditDTO.getId());

        // 3.添加文章分类
        articleEditDTO.getCategoryId().forEach(id -> {
            // 创建对象
            ArticleCategory articleCategory = new ArticleCategory();
            // 设置文章ID
            articleCategory.setArticleId(articleEditDTO.getId());
            // 设置分类ID
            articleCategory.setCategoryId(id);
            // 插入数据
            articleCategoryMapper.insert(articleCategory);
        });

        // 4.添加标签分类
        articleEditDTO.getTagId().forEach(id -> {
            // 创建对象
            ArticleTag articleTag = new ArticleTag();
            // 设置文章ID
            articleTag.setArticleId(articleEditDTO.getId());
            // 设置标签ID
            articleTag.setTagId(id);
            // 插入数据
            articleTagMapper.insert(articleTag);
        });

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
    public PageResult search(PageSearchDTO pageSearchDTO) {
        log.info("根据文章题目进行模糊搜索:{}", pageSearchDTO);

        // 1.使用PageHelper分页查询
        PageHelper.startPage(pageSearchDTO.getPageNum(), pageSearchDTO.getPageSize());
        Page<Article> articlePage = articleMapper.search(pageSearchDTO);
        long total = articlePage.getTotal();

        // 2.获取每一个文章
        List<ArticleVO> articleVOS = new ArrayList<>();
        articlePage.forEach(article -> {
            // 2.1创建对象
            ArticleVO articleVO = new ArticleVO();
            BeanUtils.copyProperties(article, articleVO);

            // 2.2创建集合
            List<String> articleCategoryNameList = new ArrayList<>();

            // 2.3根据查询文章ID 获取文章分类名称
            List<String> ac = articleCategoryMapper.findByArticleId(article.getId());

            // 2.4添加到文章分类名称集合中
            articleCategoryNameList.addAll(ac);
            articleVO.setCategoryName(articleCategoryNameList);
            articleVOS.add(articleVO);
        });

        log.info("根据文章题目进行模糊搜索成功");

        // 3.返回
        return new PageResult(total, articleVOS);
    }


    /**
     * 批量删除文章
     */
    @Override
    public void deleteArticleByIds(List<Integer> ids) {
        // 1.删除文章


        ids.forEach(id->{
            // 2.删除文章分类
            articleCategoryMapper.deleteByArticleId(id);
            // 3.删除文章标签
            articleTagMapper.deleteByArticleId(id);
        });

        log.info("批量删除文章成功");
    }
}
