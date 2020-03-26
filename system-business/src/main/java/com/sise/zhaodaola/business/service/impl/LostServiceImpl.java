package com.sise.zhaodaola.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Comment;
import com.sise.zhaodaola.business.entity.Lost;
import com.sise.zhaodaola.business.entity.User;
import com.sise.zhaodaola.business.mapper.LostMapper;
import com.sise.zhaodaola.business.service.CategoryService;
import com.sise.zhaodaola.business.service.CommentService;
import com.sise.zhaodaola.business.service.LostService;
import com.sise.zhaodaola.business.service.UserService;
import com.sise.zhaodaola.business.service.dto.*;
import com.sise.zhaodaola.business.service.vo.LostFoundQueryVo;
import com.sise.zhaodaola.tool.dict.DictManager;
import com.sise.zhaodaola.tool.exception.BadRequestException;
import com.sise.zhaodaola.tool.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LostServiceImpl extends ServiceImpl<LostMapper, Lost> implements LostService {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publishLost(LostFoundBasicDto lostFoundBasicDto) {
        Lost lost = new Lost();
        // copy attribute
        BeanUtil.copyProperties(lostFoundBasicDto, lost);
        if (StringUtils.isNotBlank(lostFoundBasicDto.getLostTime()))
            try {
                lost.setLostTime(DateUtil.toLocalDateTime(DateUtil.parse(lostFoundBasicDto.getLostTime())));
            } catch (Exception e) {
                throw new BadRequestException("时间格式化异常");
            }
        if (ObjectUtil.isNotNull(lostFoundBasicDto.getImages())) {
            List<String> images = lostFoundBasicDto.getImages();
            String imagesUrl = StringUtils.join(images, ",");
            lost.setImages(imagesUrl);
        }
        // init
        UserDto userDto = userService.findByUsername(SecurityUtils.getUsername());
        lost.setUserId(userDto.getId());
        lost.setUuid(IdUtil.simpleUUID());
        lost.setStatus(1);
        lost.setCreateTime(LocalDateTime.now());
        lost.setUpdateTime(LocalDateTime.now());
        super.save(lost);
    }

    @Override
    public PageHelper getListToPage(LostFoundQueryDto lostFoundQueryDto, PageQueryCriteria queryCriteria) {
        Page<Lost> lostPage = new Page<>(queryCriteria.getPage(), queryCriteria.getSize());
        Page<Lost> resultPage = super.page(lostPage, wrapper(lostFoundQueryDto));
        // result
        List<LostFoundQueryVo> foundQueryVos = recode(resultPage.getRecords());
        return PageUtils.toPage(foundQueryVos, resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal());
    }

    @Override
    public void download(List<LostFoundQueryVo> lostFoundQueryVos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> download = new ArrayList<>(0);
        lostFoundQueryVos.forEach(lostFoundQueryVo -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("编号", lostFoundQueryVo.getUuid());
            map.put("标题", lostFoundQueryVo.getTitle());
            map.put("发布者", lostFoundQueryVo.getUsername());
            map.put("物品分类", lostFoundQueryVo.getType());
            map.put("说明", lostFoundQueryVo.getRemark());
            map.put("丢失地址", lostFoundQueryVo.getPlace());
            map.put("丢失时间", DateUtil.formatLocalDateTime(lostFoundQueryVo.getLostTime()));
            map.put("浏览次数", lostFoundQueryVo.getBrowse());
            map.put("姓名", lostFoundQueryVo.getName());
            map.put("联系电话", lostFoundQueryVo.getTelephone());
            map.put("宿舍号", lostFoundQueryVo.getDorm());
            map.put("微信", lostFoundQueryVo.getWechat());
            map.put("创建时间", DateUtil.formatLocalDateTime(lostFoundQueryVo.getCreateTime()));
            map.put("更新时间", DateUtil.formatLocalDateTime(lostFoundQueryVo.getUpdateTime()));
            map.put("物品状态", lostFoundQueryVo.getStatus());
            download.add(map);
        });
        FileUtils.downloadExcel(download, response);
    }

    @Override
    public List<LostFoundQueryVo> getAll(LostFoundQueryDto lostFoundQueryDto) {
        List<Lost> lostList = super.list(wrapper(lostFoundQueryDto));
        return recode(lostList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteLost(List<Integer> lostIds) {
        super.removeByIds(lostIds);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateLost(LostSingleUpdateDto lostSingleUpdateDto) {
        Lost lost = new Lost();
        BeanUtil.copyProperties(lostSingleUpdateDto, lost);
        if (StringUtils.isNotBlank(lostSingleUpdateDto.getLostTime())) {
            lost.setLostTime(DateUtil.toLocalDateTime(DateUtil.parse(lostSingleUpdateDto.getLostTime())));
        }
        if (ObjectUtil.isNotNull(lostSingleUpdateDto.getImages())) {
            String imagesUrl = StringUtils.join(lostSingleUpdateDto.getImages(), ",");
            lost.setImages(imagesUrl);
        }
        lost.setUpdateTime(LocalDateTime.now());
        super.updateById(lost);
    }

    @Override
    public LostSingleUpdateDto getOne(Integer lostId) {
        Lost lost = super.getById(lostId);
        // copy
        LostSingleUpdateDto singleUpdateDto = new LostSingleUpdateDto();
        BeanUtil.copyProperties(lost, singleUpdateDto);
        if (lost.getLostTime() != null)
            singleUpdateDto.setLostTime(DateUtil.format(lost.getLostTime(), DatePattern.NORM_DATE_PATTERN));
        if (StringUtils.isNotBlank(lost.getImages())) {
            List<String> imagesUrl = Arrays.asList(StringUtils.split(lost.getImages(), ","));
            singleUpdateDto.setImages(imagesUrl);
        }
        return singleUpdateDto;
    }

    @Override
    public Lost getByUuid(String uuid) {
        LambdaQueryWrapper<Lost> wrapper = Wrappers.<Lost>lambdaQuery().eq(Lost::getUuid, uuid);
        return super.getOne(wrapper);
    }

    @Override
    public List<LostFoundQueryVo> getLostIndex() {
        LostFoundQueryDto lostFoundQueryDto = new LostFoundQueryDto();
        lostFoundQueryDto.setStatus(1);
        Page<Lost> lostPage = new Page<>(1, 12);
        Page<Lost> page = super.page(lostPage, wrapper(lostFoundQueryDto));
        return recode(page.getRecords());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public LostFoundQueryVo showLostOne(Integer id) {
        Lost lost = super.getById(id);
        if (lost == null)
            return new LostFoundQueryVo();
        lost.setBrowse(lost.getBrowse() + 1);
        super.updateById(lost);
        return single(lost);
    }

    private List<LostFoundQueryVo> recode(List<Lost> lostList) {
        // result
        List<LostFoundQueryVo> lostFoundQueryVoList = new ArrayList<>(0);
        lostList.forEach(lost -> {
            // add
            lostFoundQueryVoList.add(single(lost));
        });
        return lostFoundQueryVoList;
    }

    private LostFoundQueryVo single(Lost lost) {
        LostFoundQueryVo queryVo = new LostFoundQueryVo();

        if (lost == null)
            return queryVo;

        // select username and count of comment
        User user = userService.getById(lost.getUserId());
        String username = user.getUsername();
        String nickname = user.getNickName();
        String avatar = user.getAvatar();

        queryVo.setUsername(username);
        queryVo.setNickName(nickname);
        queryVo.setAvatar(avatar);


        int count = commentService.count(Wrappers.<Comment>lambdaQuery().eq(Comment::getPostCode, lost.getUuid()));
        String typeName = categoryService.getById(lost.getType()).getName();
        // copy
        BeanUtil.copyProperties(lost, queryVo);
        queryVo.setType(typeName);
        if (StringUtils.isNotBlank(lost.getImages())) {
            List<String> imagesUrl = Arrays.asList(StringUtils.split(lost.getImages(), ","));
            queryVo.setImagesName(imagesUrl);
        }
        queryVo.setComment(count);
        queryVo.setStatus(DictManager.lostStatus(lost.getStatus()));

        return queryVo;
    }

    private LambdaQueryWrapper<Lost> wrapper(LostFoundQueryDto lostFoundQueryDto) {
        LambdaQueryWrapper<Lost> wrapper = Wrappers.<Lost>lambdaQuery();
        // conditional wrapper
        if (ObjectUtil.isNotNull(lostFoundQueryDto)) {
            wrapper.and(StringUtils.isNotBlank(lostFoundQueryDto.getWord()), query -> {
                query.or().like(Lost::getTitle, lostFoundQueryDto.getWord());
                query.or().like(Lost::getRemark, lostFoundQueryDto.getWord());
            });
            wrapper.eq(lostFoundQueryDto.getType() > 0, Lost::getType, lostFoundQueryDto.getType());
            wrapper.eq(lostFoundQueryDto.getStatus() > 0, Lost::getStatus, lostFoundQueryDto.getStatus());
            if (StringUtils.isNotBlank(lostFoundQueryDto.getStart()) && StringUtils.isNotBlank(lostFoundQueryDto.getEnd()))
                wrapper.between(Lost::getCreateTime, DateTimeUtils.beginOfDay(lostFoundQueryDto.getStart()), DateTimeUtils.endOfDay(lostFoundQueryDto.getEnd()));
            if (StringUtils.isNotBlank(lostFoundQueryDto.getUsername())) {
                LambdaQueryWrapper<User> userWrapper = Wrappers.<User>lambdaQuery().like(User::getUsername, lostFoundQueryDto.getUsername()).select(User::getId);
                List<User> userList = userService.list(userWrapper);
                if (userList != null && userList.size() > 0) {
                    List<Integer> integers = userList.stream().map(User::getId).collect(Collectors.toList());
                    wrapper.in(Lost::getUserId, integers);
                } else {
                    wrapper.in(Lost::getUserId, -1);
                }
            }
        }
        wrapper.orderByDesc(Lost::getCreateTime);
        return wrapper;
    }
}
