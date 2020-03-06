package com.sise.zhaodaola.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Announce;
import com.sise.zhaodaola.business.entity.Site;
import com.sise.zhaodaola.business.mapper.AnnounceMapper;
import com.sise.zhaodaola.business.mapper.SiteMapper;
import com.sise.zhaodaola.business.service.AnnounceSerivce;
import com.sise.zhaodaola.business.service.SiteSerivce;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SiteServiceImpl extends ServiceImpl<SiteMapper, Site> implements SiteSerivce {
}
