package com.sise.zhaodaola.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Announce;
import com.sise.zhaodaola.business.entity.Found;
import com.sise.zhaodaola.business.mapper.AnnounceMapper;
import com.sise.zhaodaola.business.mapper.FoundMapper;
import com.sise.zhaodaola.business.service.AnnounceSerivce;
import com.sise.zhaodaola.business.service.FoundSerivce;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FoundServiceImpl extends ServiceImpl<FoundMapper, Found> implements FoundSerivce {
}
