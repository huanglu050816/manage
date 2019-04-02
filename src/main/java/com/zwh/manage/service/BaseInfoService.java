package com.zwh.manage.service;

import com.zwh.manage.exception.InnerException;
import com.zwh.manage.form.BaseInfoForm;
import com.zwh.manage.mapper.BaseInfoMapper;
import com.zwh.manage.model.BaseInfo;
import com.zwh.manage.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class BaseInfoService {

    @Autowired
    private BaseInfoMapper baseInfoMapper;

    public void submit(BaseInfoForm form) {
        // 检查手机号是否重复
        if (baseInfoMapper.checkMobile(form.getMobile()) > 0) {
            throw new InnerException(400, "手机号码已存在");
        }
        BaseInfo info = new BaseInfo();
        BeanUtils.copyProperties(form, info);
        baseInfoMapper.insert(info);
    }

    public void export(HttpServletResponse response) throws IOException {
        List<BaseInfo> baseInfoList = baseInfoMapper.getList();
        List<List<String>> dataList = revertBaseInfoList(baseInfoList);
        FileUtil.exportExcel(dataList, "数据.xls", response);
    }

    /**
     * 按照地区、手机号、线上投资金额，线下投资金额，日期，留言格式整理
     * @param baseInfoList
     * @return
     */
    private List<List<String>> revertBaseInfoList(List<BaseInfo> baseInfoList) {
        List<List<String>> result = new ArrayList<>();
        List<String> firstLine = new ArrayList<>();
        firstLine.add("地区");
        firstLine.add("手机号");
        firstLine.add("线上金额");
        firstLine.add("线下金额");
        firstLine.add("日期");
        firstLine.add("留言");
        result.add(firstLine);
        baseInfoList.forEach(baseInfo -> {
            List<String> list = new ArrayList<>();
            list.add(baseInfo.getLocation());
            list.add(baseInfo.getMobile());
            list.add(String.valueOf(baseInfo.getOnlineAmount()));
            list.add(String.valueOf(baseInfo.getOfflineAmount()));
            list.add(String.valueOf(baseInfo.getCreateTime()));
            list.add(baseInfo.getMsg());
            result.add(list);
        });
        return result;
    }

}
