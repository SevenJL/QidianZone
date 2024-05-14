package com.zone.service.Impl;

import com.zone.mapper.RecycleBinMapper;
import com.zone.service.RecycleBinService;
import com.zone.vo.ArticleRecycleBinVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 回收站业务层实现
 */


@Service
@AllArgsConstructor
@Slf4j
public class RecycleBinServiceImpl implements RecycleBinService {

    @Autowired
    private RecycleBinMapper recycleBinMapper;

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
    public List<ArticleRecycleBinVO> show() {

        return recycleBinMapper.show();
    }
}
