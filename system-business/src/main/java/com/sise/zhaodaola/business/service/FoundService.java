package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Found;
import com.sise.zhaodaola.business.service.dto.FoundQueryDto;
import com.sise.zhaodaola.business.service.dto.FoundSingleDto;
import com.sise.zhaodaola.business.service.dto.LostFoundBasicDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.vo.FoundQueryVo;
import com.sise.zhaodaola.tool.utils.PageHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface FoundService extends IService<Found> {

    /**
     * 认领登记发布
     *
     * @param lostFoundBasicDto /
     */
    void publishLost(LostFoundBasicDto lostFoundBasicDto);

    /**
     * 分页获取认领启事列表
     *
     * @param foundQueryDto     /
     * @param pageQueryCriteria /
     * @return /
     */
    PageHelper getListToPage(FoundQueryDto foundQueryDto, PageQueryCriteria pageQueryCriteria);

    /**
     * 认领启事数据导出
     *
     * @param foundQueryVoList /
     * @param response         /
     */
    void download(List<FoundQueryVo> foundQueryVoList, HttpServletResponse response) throws IOException;

    /**
     * 不分页条件查询认领启事数据
     *
     * @param foundQueryDto /
     * @return /
     */
    List<FoundQueryVo> getAll(FoundQueryDto foundQueryDto);

    /**
     * 删除认领启事数据
     *
     * @param foundIds /
     */
    void deleteFound(List<Integer> foundIds);

    /**
     * 单个认领启事查询
     *
     * @param foundId /
     * @return /
     */
    FoundSingleDto getOne(Integer foundId);


    /**
     * 修改认领启事信息
     *
     * @param foundSingleDto /
     */
    void updateFound(FoundSingleDto foundSingleDto);

    /**
     * 查询
     *
     * @param uuid /
     * @return /
     */
    Found getByUuid(String uuid);

    /**
     * 获取首页招领启事
     *
     * @return /
     */
    List<FoundQueryVo> getFoundIndex();

    /**
     * 查看招领启事信息
     *
     * @param id /
     * @return /
     */
    FoundQueryVo showFoundOne(Integer id);

    /**
     * 相关推荐
     *
     * @param category /
     * @return /
     */
    List<FoundQueryVo> pushFound(String category, Integer slfe);

    /**
     * 删除
     *
     * @param foundId /
     */
    void deleteOne(Integer foundId);
}
