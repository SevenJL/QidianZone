package com.zone.service.Impl.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zone.context.BaseContext;
import com.zone.dto.PageSearchDTO;
import com.zone.dto.PageSearchWithDeletePower;
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
        // 1.查看是否传入pageNum和pageSize
        if (pageSearchDTO.getPageNum() == null || pageSearchDTO.getPageSize() == null) {
            // 设置默认值
            pageSearchDTO.setPageNum(1);
            pageSearchDTO.setPageSize(10);
        }
        // 2.查询是回收站的文章 所以需要将删除状态设置为0
        PageSearchWithDeletePower search
                = new PageSearchWithDeletePower(); // 封装查询条件
        search.setDeleteStatus(0); // 删除状态
        log.info("pageSearchDTO:{}", pageSearchDTO);

        // 3.设置要查询的回收站的用户ID 是当前用户的
        search.setUserId(BaseContext.getCurrentId());

        // 4.拷贝数据
        BeanUtils.copyProperties(pageSearchDTO, search);

        // 5.分页查询
        PageHelper.startPage(search.getPageNum(), search.getPageSize());
        Page<Article> page = articleMapper.search(search);

        // 6.遍历 拷贝数据
        // 如果有分类 就查询分类并遍历 添加到集合中
        List<RecycleBinShowVO> recycleBinShowVOS = new ArrayList<>();
        page.forEach(pageArticle -> {
            List<String> articleCategoryMapperByArticleIdList
                    = articleCategoryMapper.findByArticleId(pageArticle.getId());
            List<String> articleTagMapperByArticleIdList
                    = articleTagMapper.findByArticleId(pageArticle.getId());
            RecycleBinShowVO recycleBinShowVO = new RecycleBinShowVO();
            // 6.1查询是否有分类
            if (!articleCategoryMapperByArticleIdList.isEmpty()) {
                // 如果有分类就拷贝数据
                recycleBinShowVO.setCategoryName(articleCategoryMapperByArticleIdList);
            }
            // 6.2查询是否有标签
            if (!articleTagMapperByArticleIdList.isEmpty()) {
                // 如果有分类就拷贝数据
                recycleBinShowVO.setTagName(articleTagMapperByArticleIdList);
            }
            // 6.3拷贝数据
            BeanUtils.copyProperties(pageArticle, recycleBinShowVO);
            // 添加到集合中
            recycleBinShowVOS.add(recycleBinShowVO);
        });

        int total = recycleBinShowVOS.size();
        // 7.返回查询的数据
        return new PageResult(total, recycleBinShowVOS);
    }
}
