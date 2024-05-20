package com.zone.service.Impl.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zone.constant.ArticleAboutConstant;
import com.zone.constant.DeleteConstant;
import com.zone.context.BaseContext;
import com.zone.dto.*;
import com.zone.entity.*;
import com.zone.mapper.ArticleCategoryMapper;
import com.zone.mapper.ArticleMapper;
import com.zone.mapper.ArticleTagMapper;
import com.zone.mapper.CommentMapper;
import com.zone.result.PageResult;
import com.zone.service.ArticleService;
import com.zone.vo.ArticleInfoVO;
import com.zone.vo.ArticleManageVO;
import com.zone.vo.ArticleVO;
import com.zone.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章服务实现
 */

@Service // 注解
@Slf4j // 日志
@RequiredArgsConstructor // 构造方法注解
@SuppressWarnings("ALL") // 忽略警告
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private final ArticleMapper articleMapper;

    private final CommentMapper commentMapper;

    private final ArticleTagMapper articleTagMapper;

    private final ArticleCategoryMapper articleCategoryMapper;


    /**
     * 发布文章
     */
    @Override
    public void publish(ArticlePublishDTO articlePublishDTO) {
        // 判断 标题和内容 是否都存在
        if (articlePublishDTO.getTitle() == null || articlePublishDTO.getContent() == null) {
            throw new RuntimeException("标题和内容不能为空");
        }

        // 1.判断是否有访问权限 这个数据是前端传入的
        if(articlePublishDTO.getArticleViewPower() == null ){
            // 默认为公开
            articlePublishDTO.setArticleViewPower(ArticleAboutConstant.DEFAULT_ARTICLE_VIEW_POWER);
        }

        // 2.拷贝/添加 文章数据
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
                .creatorId(BaseContext.getCurrentId()) // 创建者ID
                .status(ArticleAboutConstant.DEFAULT_STATUS) // 状态
                .deleteStatus(DeleteConstant.ENABLE) // 删除状态
                .build();

        // 3.插入文章数据
        articleMapper.insert(article);


        // 4.判断是否有分类数据 和标签数据
        if (articlePublishDTO.getCategoryId() != null) {
            // 4.1添加文章类型article_category到数据库中
            ArticleCategory articleCategory = new ArticleCategory();
            articleCategory.setArticleId(article.getId());
            articlePublishDTO.getCategoryId().forEach(id -> {
                // 遍历添加
                articleCategory.setCategoryId(id);
                articleCategoryMapper.insertArticleCategory(articleCategory);
            });
        }
        if (articlePublishDTO.getTagId() != null){
            // 4.2添加文章标签article_tag到数据库中
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(article.getId());
            articlePublishDTO.getTagId().forEach(id -> {
                // 遍历添加
                articleTag.setTagId(id);
                articleTagMapper.insertArticleTag(articleTag);
                // XXX:使用Mp自带的insert方法会出现无法插入的问题
            });
        }

        log.info("发布文章成功");
    }


    /**
     * 编辑文章
     */

    @Override
    public void edit(ArticleEditDTO articleEditDTO) {

        // 1.判断前端数据 是否传入访问权限 如果有就不查询了 如果没有就查询
        if(articleEditDTO.getArticleViewPower() == null ){
            // 查询数据库中的文章的访问权限
            Article article = articleMapper.selectById(articleEditDTO.getId());
            // 设置文章的访问权限
            articleEditDTO.setArticleViewPower(article.getArticleViewPower());
        }

        // 2.拷贝传入的数据 添加文章数据
        Article article = Article.builder()
                .id(articleEditDTO.getId()) // id
                .title(articleEditDTO.getTitle()) // 标题
                .updateTime(LocalDateTime.now()) // 更新时间
                .content(articleEditDTO.getContent()) // 内容
                .creatorId(BaseContext.getCurrentId()) // 创建者ID
                .articleViewPower(articleEditDTO.getArticleViewPower()) // 访问权限
                .build();
        // 3.更新文章数据
        articleMapper.updateArticle(article);

        log.info("articleEditDTO:{}", articleEditDTO);
        // 4.根据文章ID删除/并添加新的 文章分类
        if (articleEditDTO.getCategoryId() != null) {
            articleCategoryMapper.deleteByArticleId(articleEditDTO.getId());
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
        }
        // 5.根据文章ID删除/并添加新的 标签分类
        if (articleEditDTO.getTagId() != null) {
            articleTagMapper.deleteByArticleId(articleEditDTO.getId());
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
        }

        log.info("编辑文章成功");
    }


    /**
     * 删除文章
     * 逻辑删除
     */
    @Override
    public void delete(Integer id) {
        // 1.创建对象
        Article article = Article.builder()
                .deleteStatus(DeleteConstant.DISABLE) // 删除状态
                .id(id) // id
                .creatorId(BaseContext.getCurrentId()) // 创建者ID
                .deleteTime(LocalDateTime.now()) // 删除时间
                .build();

        // 2.更新
        articleMapper.updateArticle(article);
        log.info("删除文章成功");
    }


        /**
     * 根据文章题目进行模糊搜索
     */
    @Override
    public PageResult search(PageSearchDTO pageSearchDTO) {
        // 1.判断是否传入pageNum和pageSize
        if (pageSearchDTO.getPageNum() == null || pageSearchDTO.getPageSize() == null
        ) {
            // 设置默认值
            pageSearchDTO.setPageNum(1);
            pageSearchDTO.setPageSize(10);
        }
        log.info("根据文章题目进行模糊搜索:{}", pageSearchDTO);

        // 2.创建 带有删除状态 的对象
        PageSearchWithDeletePower search = new PageSearchWithDeletePower();
        search.setDeleteStatus(DeleteConstant.ENABLE);

        // 3.拷贝数据
        BeanUtils.copyProperties(pageSearchDTO, search);
        log.info("根据文章题目进行模糊搜索:{}", search);

        // 4.使用PageHelper分页查询
        PageHelper.startPage(search.getPageNum(), search.getPageSize());
        //TODO 查询bug待修复
        Page<Article> articlePage = articleMapper.search(search);

        // 5.根据文章ID获取文章分类
        List<ArticleVO> articleVOS = gainCategoryByArticleId(articlePage);
        log.info("根据文章题目进行模糊搜索成功");

        // 6.返回
        long total = articleVOS.size();
        return new PageResult(total, articleVOS);
    }


    /**
     * 批量/单个 删除文章
     */
    @Override
    public void deleteArticleByIds(List<Integer> ids) {
        // 1.删除文章
        articleMapper.deleteBatchIds(ids);

        // 2.删除文章分类
        articleCategoryMapper.deleteByArticleIds(ids);

        // 3.删除文章标签
        articleTagMapper.deleteByArticleIds(ids);

        // TODO 4.删除文章评论

        log.info("批量删除文章成功");
    }


    /**
     * 获取最新文章详情
     */
    @Override
    public List<NewArticle> listNew() {
        return articleMapper.listNew();
    }


    /**
     * 文章列表
     */
    @Override
    public PageResult listAllArticle(PageBean pageBean) {
        // 1.进行分页查询
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());

        // 2.获取文章
        Page<Article> articlePage = articleMapper.listAllArticle();

        // 3.拷贝数据
        ArrayList<ArticleManageVO> articleManageVOS = new ArrayList<>();
        articlePage.getResult().forEach(article -> {
            ArticleManageVO articleManageVO = new ArticleManageVO();
            BeanUtils.copyProperties(article, articleManageVO);
            articleManageVOS.add(articleManageVO);
        });

        // 4.返回数据
        return new PageResult(articleManageVOS.size(), articleManageVOS);
    }


    /**
     * 根据文章ID获取文章分类
     */
    private List<ArticleVO> gainCategoryByArticleId(List<Article> articles) {
        List<ArticleVO> articleVOS = new ArrayList<>();
        articles.forEach(article -> {
            // 2.1创建对象
            ArticleVO articleVO = new ArticleVO();
            BeanUtils.copyProperties(article, articleVO);

            // 2.2创建集合
            List<String> articleCategoryNameList = new ArrayList<>();

            // 2.3根据文章ID 获取文章分类名称
            List<String> ac = articleCategoryMapper.findByArticleId(article.getId());

            // 2.4添加到文章分类名称集合中
            articleCategoryNameList.addAll(ac);
            articleVO.setCategoryName(articleCategoryNameList);
            articleVOS.add(articleVO);
        });
        return articleVOS;
    }


    /**
     * 查看文章详情
     */
    @Override
    public ArticleInfoVO checkOutArticle(Integer articleId) {
        // 1.获取文章详情
        Article article = articleMapper.checkOutArticle(articleId);
        // 拷贝数据
        ArticleInfoVO articleInfoVO = new ArticleInfoVO();
        BeanUtils.copyProperties(article, articleInfoVO);

        // 2.获取文章分类
        List<String> categoryNames = articleCategoryMapper.findByArticleId(articleId);
        articleInfoVO.setCategoryName(categoryNames); // 设置文章分类

        // 3.获取文章标签
        List<String> tagNames = articleTagMapper.findByArticleId(articleId);
        articleInfoVO.setTagName(tagNames); // 设置文章标签

        // 4.获取文章评论
        List<Comment> comments = commentMapper.selectByArticleId(articleId);
        // 拷贝数据
        ArrayList<CommentVO> commentVOS = new ArrayList<>();
        comments.forEach(comment -> {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);
            commentVOS.add(commentVO);
        });
        // 设置文章评论
        articleInfoVO.setCommentVOS(commentVOS);

        // 5.增加文章浏览量
        articleMapper.addArticleView(articleId);

        return articleInfoVO;
    }


    /**
     * 更新文章点赞数
     */
    @Override
    public void updateArticleLike(Integer articleId) {
        // 更新文章文章点赞量
        articleMapper.updateArticleLike(articleId);
    }


    /**
     * 查询个人所有文章
     */
    @Override
    public PageResult listPersonalArticle(PageBean pageBean) {

        // 1.进行分页查询
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());

        // 2.获取文章
        Page<Article> articlePage = articleMapper.listPersonalArticle(BaseContext.getCurrentId());

        // 3.拷贝数据
        List<ArticleManageVO> articleManageVOS = new ArrayList<>();
        articlePage.getResult().forEach(article -> {
            ArticleManageVO articleManageVO = new ArticleManageVO();
            BeanUtils.copyProperties(article, articleManageVO);
            articleManageVOS.add(articleManageVO);
        });

        // 4.返回数据
        return new PageResult(articleManageVOS.size(), articleManageVOS);
    }
}
