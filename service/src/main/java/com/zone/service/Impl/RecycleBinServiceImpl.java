package com.zone.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zone.context.BaseContext;
import com.zone.dto.PageSearchDTO;
import com.zone.dto.PageSearchWithDeleteStatusAndViewPowerStatus;
import com.zone.entity.Article;
import com.zone.mapper.ArticleCategoryMapper;
import com.zone.mapper.ArticleMapper;
import com.zone.mapper.ArticleTagMapper;
import com.zone.mapper.RecycleBinMapper;
import com.zone.result.PageResult;
import com.zone.service.RecycleBinService;
import com.zone.vo.RecycleBinShowVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 回收站业务层实现
 */


@Service
@Slf4j
@RequiredArgsConstructor
public class RecycleBinServiceImpl
        extends ServiceImpl<ArticleMapper, Article> implements RecycleBinService {

    private final RecycleBinMapper recycleBinMapper;

    private final ArticleMapper articleMapper;

    private final ArticleCategoryMapper articleCategoryMapper;

    private final ArticleTagMapper articleTagMapper;

    /**
     * 清理
     */
    @Override
    public void clear() {

        // 1.先查询回收站有哪些文章的删除状态为0
        List<Article> deleteStatusEqualZero = recycleBinMapper.findDeleteStatusEqualZero();
        if (deleteStatusEqualZero.isEmpty()) {
            log.info("没有需要清理的文章");
            return;
        }
        // 2.获取需要 删除的文章的ID的List集合
        List<Integer> ids = new ArrayList<>();
        deleteStatusEqualZero.forEach(article -> {
            Integer articleId = article.getId();
            ids.add(articleId);
        });

        // 3.先查询要删除的文章 是否 含有分类 和标签
        List<String> articleCategories = articleCategoryMapper.findByArticleIds(ids);
        List<String> articleTags = articleTagMapper.findByArticleIds(ids);

        // 4.如果有就删除 分类/标签
        if (!articleCategories.isEmpty()) {
            articleCategoryMapper.deleteByArticleIds(ids);
        }
        if (!articleTags.isEmpty()) {
            articleTagMapper.deleteByArticleIds(ids);
        }

        // 5.最后再对文章进行删除
        recycleBinMapper.clear();
        log.info("清理完成");
    }

    /**
     * 显示文章信息
     */
    @Override
    public PageResult show(PageSearchDTO pageSearchDTO) {
        // 1.查询是回收站的文章 所以需要将删除状态设置为0
        PageSearchWithDeleteStatusAndViewPowerStatus pageSearchWithDeleteStatusAndViewPowerStatus
                = new PageSearchWithDeleteStatusAndViewPowerStatus();
        pageSearchWithDeleteStatusAndViewPowerStatus.setDeleteStatus(0);
        log.info("pageSearchDTO:{}", pageSearchDTO);

        // 2.设置要查询的回收站 是当前用户的
        pageSearchWithDeleteStatusAndViewPowerStatus.setUserId(BaseContext.getCurrentId());

        // 3.拷贝数据
        BeanUtils.copyProperties(pageSearchDTO, pageSearchWithDeleteStatusAndViewPowerStatus);

        // 4.分页查询
        PageHelper.startPage(pageSearchDTO.getPageNum(), pageSearchDTO.getPageSize());
        Page<Article> page = articleMapper.search(pageSearchWithDeleteStatusAndViewPowerStatus);

        // 5.遍历 拷贝数据
        // 如果有分类 就查询分类并遍历 添加到集合中
        List<RecycleBinShowVO> recycleBinShowVOS = new ArrayList<>();
        page.forEach(pageArticle -> {
            List<String> articleCategoryMapperByArticleIdList
                    = articleCategoryMapper.findByArticleId(pageArticle.getId());
            List<String> articleTagMapperByArticleIdList
                    = articleTagMapper.findByArticleId(pageArticle.getId());
            RecycleBinShowVO recycleBinShowVO = new RecycleBinShowVO();
            // 5.1查询是否有分类
            if (!articleCategoryMapperByArticleIdList.isEmpty()) {
                // 如果有分类就拷贝数据
                recycleBinShowVO.setCategoryName(articleCategoryMapperByArticleIdList);
            }
            // 5.2查询是否有标签
            if (!articleTagMapperByArticleIdList.isEmpty()) {
                // 如果有分类就拷贝数据
                recycleBinShowVO.setTagName(articleTagMapperByArticleIdList);
            }
            // 5.3拷贝数据
            BeanUtils.copyProperties(pageArticle, recycleBinShowVO);
            // 添加到集合中
            recycleBinShowVOS.add(recycleBinShowVO);
        });

        // 6.返回查询的数据
        return new PageResult(page.getTotal(), recycleBinShowVOS);
    }
}
