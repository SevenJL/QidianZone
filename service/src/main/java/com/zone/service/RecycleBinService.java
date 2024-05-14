package com.zone.service;


import com.zone.vo.ArticleRecycleBinVO;

import java.util.List;

/**
 * 回收站服务
 */
public interface RecycleBinService {


    /**
     * 清理
     */
    void clear();


    /**
     * 显示
     * @return
     */
    List<ArticleRecycleBinVO> show();
}
