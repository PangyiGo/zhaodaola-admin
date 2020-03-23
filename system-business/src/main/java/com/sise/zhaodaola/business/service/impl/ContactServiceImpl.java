package com.sise.zhaodaola.business.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Contact;
import com.sise.zhaodaola.business.mapper.ContactMapper;
import com.sise.zhaodaola.business.service.ContactService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {

    @Override
    public Contact getContact() {
        return getOne(Wrappers.<Contact>lambdaQuery().eq(Contact::getType, 1));
    }

    @Override
    public Contact getAbout() {
        return getOne(Wrappers.<Contact>lambdaQuery().eq(Contact::getType, 2));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateContact(Contact contact) {
        contact.setCreateTime(LocalDateTime.now());
        contact.setUpdateTime(LocalDateTime.now());
        super.saveOrUpdate(contact);
    }
}
