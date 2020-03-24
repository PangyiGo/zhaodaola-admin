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
import com.sise.zhaodaola.business.entity.Found;
import com.sise.zhaodaola.business.entity.User;
import com.sise.zhaodaola.business.mapper.FoundMapper;
import com.sise.zhaodaola.business.service.*;
import com.sise.zhaodaola.business.service.dto.*;
import com.sise.zhaodaola.business.service.vo.FoundQueryVo;
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
public class FoundServiceImpl extends ServiceImpl<FoundMapper, Found> implements FoundService {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SiteService siteService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publishLost(LostFoundBasicDto lostFoundBasicDto) {
        Found found = new Found();
        // copy attribute
        BeanUtil.copyProperties(lostFoundBasicDto, found);
        if (StringUtils.isNotBlank(lostFoundBasicDto.getLostTime()))
            try {
                found.setLostTime(DateUtil.toLocalDateTime(DateUtil.parse(lostFoundBasicDto.getLostTime())));
            } catch (Exception e) {
                throw new BadRequestException("时间格式化异常");
            }
        if (ObjectUtil.isNotNull(lostFoundBasicDto.getImages())) {
            List<String> images = lostFoundBasicDto.getImages();
            String imagesUrl = StringUtils.join(images, ",");
            found.setImages(imagesUrl);
        }
        // init
        UserDto userDto = userService.findByUsername(SecurityUtils.getUsername());
        found.setUserId(userDto.getId());
        found.setUuid(IdUtil.simpleUUID());
        found.setStatus(1);
        found.setCreateTime(LocalDateTime.now());
        found.setUpdateTime(LocalDateTime.now());
        super.save(found);
    }

    @Override
    public PageHelper getListToPage(FoundQueryDto foundQueryDto, PageQueryCriteria pageQueryCriteria) {
        Page<Found> foundPage = new Page<>(pageQueryCriteria.getPage(), pageQueryCriteria.getSize());
        Page<Found> resultPage = super.page(foundPage, wrapper(foundQueryDto));
        List<FoundQueryVo> foundQueryVoList = recode(resultPage.getRecords());
        return PageUtils.toPage(foundQueryVoList, resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal());
    }

    @Override
    public void download(List<FoundQueryVo> foundQueryVoList, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> download = new ArrayList<>(0);
        foundQueryVoList.forEach(foundQueryVo -> {
            Map<String, Object> map = new HashMap<>(0);
            map.put("编号", foundQueryVo.getUuid());
            map.put("标题", foundQueryVo.getTitle());
            map.put("发布者", foundQueryVo.getUsername());
            map.put("物品分类", foundQueryVo.getType());
            map.put("说明", foundQueryVo.getRemark());
            map.put("拾到地址", foundQueryVo.getPlace());
            map.put("拾到时间", DateUtil.formatLocalDateTime(foundQueryVo.getLostTime()));
            map.put("浏览次数", foundQueryVo.getBrowse());
            map.put("认领方式", DictManager.foundType(foundQueryVo.getContact()));
            map.put("认领站点", foundQueryVo.getContactType());
            map.put("姓名", foundQueryVo.getName());
            map.put("联系电话", foundQueryVo.getTelephone());
            map.put("宿舍号", foundQueryVo.getDorm());
            map.put("微信", foundQueryVo.getWechat());
            map.put("认领者", foundQueryVo.getFoundUser());
            map.put("创建时间", DateUtil.formatLocalDateTime(foundQueryVo.getCreateTime()));
            map.put("更新时间", DateUtil.formatLocalDateTime(foundQueryVo.getUpdateTime()));
            map.put("物品状态", foundQueryVo.getStatus());
            download.add(map);
        });
        FileUtils.downloadExcel(download, response);
    }

    @Override
    public List<FoundQueryVo> getAll(FoundQueryDto foundQueryDto) {
        List<Found> foundList = super.list(wrapper(foundQueryDto));
        return recode(foundList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteFound(List<Integer> foundIds) {
        super.removeByIds(foundIds);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateFound(FoundSingleDto foundSingleDto) {
        Found found = new Found();
        BeanUtil.copyProperties(foundSingleDto, found);
        if (StringUtils.isNotBlank(foundSingleDto.getLostTime())) {
            found.setLostTime(DateUtil.toLocalDateTime(DateUtil.parse(foundSingleDto.getLostTime())));
        }
        if (ObjectUtil.isNotNull(foundSingleDto.getImages())) {
            String imagesUrl = StringUtils.join(foundSingleDto.getImages(), ",");
            found.setImages(imagesUrl);
        }
        found.setUpdateTime(LocalDateTime.now());
        super.updateById(found);
    }

    @Override
    public Found getByUuid(String uuid) {
        LambdaQueryWrapper<Found> wrapper = Wrappers.<Found>lambdaQuery().eq(Found::getUuid, uuid);
        return super.getOne(wrapper);
    }

    @Override
    public FoundSingleDto getOne(Integer foundId) {
        Found found = super.getById(foundId);
        // copy
        FoundSingleDto foundSingleDto = new FoundSingleDto();
        BeanUtil.copyProperties(found, foundSingleDto);
        if (found.getLostTime() != null)
            foundSingleDto.setLostTime(DateUtil.format(found.getLostTime(), DatePattern.NORM_DATE_PATTERN));
        if (StringUtils.isNotBlank(found.getImages())) {
            List<String> imagesUrl = Arrays.asList(StringUtils.split(found.getImages(), ","));
            foundSingleDto.setImages(imagesUrl);
        }
        return foundSingleDto;
    }

    private List<FoundQueryVo> recode(List<Found> foundList) {
        List<FoundQueryVo> foundQueryVoList = new ArrayList<>(0);
        foundList.forEach(found -> {
            FoundQueryVo foundQueryVo = new FoundQueryVo();
            BeanUtil.copyProperties(found, foundQueryVo);
            // select username and count of comment
            String username = userService.getById(found.getUserId()).getUsername();
            int count = commentService.count(Wrappers.<Comment>lambdaQuery().eq(Comment::getPostCode, found.getUuid()));
            String typeName = categoryService.getById(found.getType()).getName();
            if (found.getOwner() != null) {
                String owner = userService.getById(found.getOwner()).getUsername();
                foundQueryVo.setFoundUser(owner);
            }
            // found of type to site
            if (found.getContact() == 2) {
                String address = siteService.getById(found.getClaim()).getAddress();
                foundQueryVo.setContactType(address);
            }
            foundQueryVo.setUsername(username);
            foundQueryVo.setComment(count);
            foundQueryVo.setType(typeName);
            foundQueryVo.setStatus(DictManager.foundStatus(found.getStatus()));
            if (StringUtils.isNotBlank(found.getImages())) {
                List<String> imagesUrl = Arrays.asList(StringUtils.split(found.getImages(), ","));
                foundQueryVo.setImagesName(imagesUrl);
            }
            foundQueryVoList.add(foundQueryVo);
        });
        return foundQueryVoList;
    }

    private LambdaQueryWrapper<Found> wrapper(FoundQueryDto foundQueryDto) {
        LambdaQueryWrapper<Found> lambdaQuery = Wrappers.<Found>lambdaQuery();
        if (ObjectUtil.isNotNull(foundQueryDto)) {
            lambdaQuery.and(StringUtils.isNotBlank(foundQueryDto.getWord()), query -> {
                query.or().like(Found::getTitle, foundQueryDto.getWord());
                query.or().like(Found::getRemark, foundQueryDto.getWord());
            });
            lambdaQuery.eq(foundQueryDto.getType() > 0, Found::getType, foundQueryDto.getType());
            lambdaQuery.eq(foundQueryDto.getStatus() > 0, Found::getStatus, foundQueryDto.getStatus());
            lambdaQuery.eq(foundQueryDto.getContact() > 0, Found::getContact, foundQueryDto.getContact());
            if (StringUtils.isNotBlank(foundQueryDto.getStart()) && StringUtils.isNotBlank(foundQueryDto.getEnd()))
                lambdaQuery.between(Found::getCreateTime, DateTimeUtils.beginOfDay(foundQueryDto.getStart()), DateTimeUtils.endOfDay(foundQueryDto.getEnd()));
            if (StringUtils.isNotBlank(foundQueryDto.getUsername())) {
                LambdaQueryWrapper<User> userWrapper = Wrappers.<User>lambdaQuery().like(User::getUsername, foundQueryDto.getUsername()).select(User::getId);
                List<User> userList = userService.list(userWrapper);
                if (userList != null && userList.size() > 0) {
                    List<Integer> integers = userList.stream().map(User::getId).collect(Collectors.toList());
                    lambdaQuery.in(Found::getUserId, integers);
                } else {
                    lambdaQuery.in(Found::getUserId, -1);
                }
            }
        }
        return lambdaQuery;
    }
}
