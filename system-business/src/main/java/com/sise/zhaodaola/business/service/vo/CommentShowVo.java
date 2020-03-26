package com.sise.zhaodaola.business.service.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/2611:27 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentShowVo extends CommentQueryVo {

    private List<CommentQueryVo> children = new ArrayList<>(0);

    private long count;
}
