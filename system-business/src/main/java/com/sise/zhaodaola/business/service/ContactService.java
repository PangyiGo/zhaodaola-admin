package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Contact;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface ContactService extends IService<Contact> {

    /**
     * 获取联系我们
     *
     * @return /
     */
    Contact getContact();

    /**
     * 获取关于我们
     *
     * @return /
     */
    Contact getAbout();

    /**
     * 修改联系我们或关于我们数据
     *
     * @param contact /
     */
    void updateContact(Contact contact);
}
