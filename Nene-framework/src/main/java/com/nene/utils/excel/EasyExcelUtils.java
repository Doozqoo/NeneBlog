package com.nene.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class EasyExcelUtils {

    public static void writeExcel(HttpServletResponse response, Class<?> excelEntity, String fileName) {

        try {
            fileName = URLEncoder.encode(fileName + ".xlsx", "UTF-8").replaceAll("\\+", "%20");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            // 设置响应头部信息，格式为附件，以及文件名
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
            WriteSheet writeSheet = EasyExcelUtils.writeSelectedSheet(excelEntity, 0, "导入表格");
            // 此处只导出实体类中的数据所以只new 一个空的list，如果想导出数据库数据需要从数据库中查询数据list
            excelWriter.write(ListUtils.newArrayList(), writeSheet);
            excelWriter.finish();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 创建即将导出的sheet页（sheet页中含有带下拉框的列）
     *
     * @param head      导出的表头信息和配置
     * @param sheetNo   sheet索引
     * @param sheetName sheet名称
     * @param <T>       泛型
     * @return sheet页
     */
    public static <T> WriteSheet writeSelectedSheet(Class<T> head, Integer sheetNo, String sheetName) {
        Map<Integer, ExcelSelectedAnalysis> selectedMap = resolveSelectedAnnotation(head);

        return EasyExcel.writerSheet(sheetNo, sheetName)
                .head(head)
                .registerWriteHandler(new SelectedSheetWriteHandler(selectedMap))
                .build();
    }

    /**
     * 解析表头类中的下拉注解
     *
     * @param head 表头类
     * @param <T>  泛型
     * @return Map<下拉框列索引, 下拉框内容> map
     */
    private static <T> Map<Integer, ExcelSelectedAnalysis> resolveSelectedAnnotation(Class<T> head) {
        Map<Integer, ExcelSelectedAnalysis> selectedMap = new HashMap<>();

        // getDeclaredFields(): 返回全部声明的属性；getFields(): 返回public类型的属性
        Field[] fields = head.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            // 解析注解信息
            ExcelSelected selected = field.getAnnotation(ExcelSelected.class);
            ExcelProperty property = field.getAnnotation(ExcelProperty.class);
            if (selected != null) {
                ExcelSelectedAnalysis excelSelectedAnalysis = new ExcelSelectedAnalysis();
                String[] source = excelSelectedAnalysis.resolveSelectedSource(selected);
                if (source != null && source.length > 0) {
                    excelSelectedAnalysis.setSource(source);
                    excelSelectedAnalysis.setFirstRow(selected.firstRow());
                    excelSelectedAnalysis.setLastRow(selected.lastRow());
                    if (property != null && property.index() >= 0) {
                        selectedMap.put(property.index(), excelSelectedAnalysis);
                    } else {
                        selectedMap.put(i, excelSelectedAnalysis);
                    }
                }
            }
        }

        return selectedMap;
    }
}
