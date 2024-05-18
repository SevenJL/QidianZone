package com.zone.service.Impl;

import com.zone.entity.Tag;
import com.zone.mapper.ArticleTagMapper;
import com.zone.mapper.TagMapper;
import com.zone.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
@Slf4j
@RequiredArgsConstructor
public class TagServiceImpl  extends ServiceImpl<TagMapper, Tag> implements TagService {

    private final TagMapper tagMapper;

    private final ArticleTagMapper articleTagMapper;

    /**
     * 添加标签
     */
    @Override
    public Integer addTag(String name) {
        // 1.先判断标签是否存在
        Integer tagId = tagMapper.findByTagName(name);
        log.info("id:{}",tagId);

        // 2.如果存在，则不添加 返回-1 表示存在该名称
        if (tagId != null ) {
            return -1;
        }

        // 3.添加数据 并返回ID值
        Tag tag = new Tag();
        tag.setName(name);
        return tagMapper.insert(tag);
    }

    /**
     * 删除标签
     */
    @Override
    public void deleteTag(Integer id) {
        // 1.删除分类 需要先删除关联表中的数据
        articleTagMapper.deleteByTagId(id);
        // 2.再删除分类
        tagMapper.deleteById(id);
    }


}
