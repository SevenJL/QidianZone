package com.zone.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zone.entity.Tag;

/**
 * 用户服务
 */

public interface TagService extends IService<Tag> {


    /**
     * 添加标签
     */
    Integer addTag(String name);

    /**
     * 删除标签
     */
    void deleteTag(Integer id);
}
