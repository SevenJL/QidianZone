package com.zone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zone.entity.Category;

/**
 * 用户服务
 */
public interface CategoryService extends IService<Category> {

    /**
     * 添加分类
     */
    Integer addCategory(String categoryName);

    /**
     * 删除分类
     */
    void deleteCategoryById(Integer id);

}
