package com.shf.ggkt.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelListener extends AnalysisEventListener<User> {
    //    创建list集合封装最终的数据
    List<User> list = new ArrayList<User>();

    /**
     * 一行一行读取excel内容，把每行内容封装到user对象
     * 从excel第二行开始读取
     * @param user
     * @param analysisContext
     */
    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        System.out.println("***"+user);
        list.add(user);
    }

    /**
     * 读取表头内容
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头："+headMap);
    }

    /**
     *  读取完成后执行
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
