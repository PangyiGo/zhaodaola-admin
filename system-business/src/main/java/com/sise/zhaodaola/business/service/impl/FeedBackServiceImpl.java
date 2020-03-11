package com.sise.zhaodaola.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.FeedBack;
import com.sise.zhaodaola.business.mapper.FeedBackMapper;
import com.sise.zhaodaola.business.service.FeedBackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FeedBackServiceImpl extends ServiceImpl<FeedBackMapper, FeedBack> implements FeedBackService {
}
