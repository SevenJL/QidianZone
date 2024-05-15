package com.zone.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zone.dto.PageSearchDTO;
import com.zone.entity.Article;
import com.zone.mapper.ArticleMapper;
import com.zone.mapper.RecycleBinMapper;
import com.zone.mapper.UserMapper;
import com.zone.result.PageResult;
import com.zone.service.RecycleBinService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 回收站业务层实现
 */


@Service
@AllArgsConstructor
@Slf4j
public class RecycleBinServiceImpl implements RecycleBinService {

    @Autowired
    private RecycleBinMapper recycleBinMapper;

    @Autowired
    private ArticleMapper articleMapper;
    /**
     * 清理
     */
    @Override
    public void clear() {
        // 计算时间 从而去选择需要删除的数据
        recycleBinMapper.clear();
    }

    /**
     * 显示文章信息
     * @return
     */
    @Override
    public PageResult show(PageSearchDTO pageSearchDTO) {
        // 查询是回收站的文章 所以需要将删除状态设置为0
        pageSearchDTO.setDeleteStatus(0);
        log.info("pageSearchDTO:{}", pageSearchDTO);

        PageHelper.startPage(pageSearchDTO.getPageNum(), pageSearchDTO.getPageSize());
        Page<Article> page = articleMapper.search(pageSearchDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }
}
