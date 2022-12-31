package com.nene.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName PageVo
 * @Description 分页数据封装类
 * @Author Protip
 * @Date 2022/12/31 18:06
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo {

    private List rows;

    private Long total;
}
