package com.cotech.controller;

import com.alibaba.fastjson.JSONObject;
import com.cotech.enums.Status;
import com.cotech.model.TopTenList;
import com.cotech.service.TResService;
import com.cotech.service.TUserService;
import com.cotech.util.JsonUtil;
import com.cotech.util.WrapJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Resource
    private TUserService TUserService;

    @Resource
    private TResService TResService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 首页接口
     * author:陈震威
     * bug联系方式：zhenweichen.ron@foxmail.com
     */
    @ResponseBody
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "index")
    public MappingJacksonValue indexController(HttpServletRequest request, @RequestParam(value="callback",required=false) String callback) {
        logger.debug("index接口收到来自"+request.getRemoteAddr()+"的请求！");
        //金币榜
        List<TopTenList> goldLst = null;
        //分数榜
        List<TopTenList> scoreLst = null;
        //参与人数
        Long resCount = 0l;
        JSONObject jsonObject = new JSONObject();
        try {
            goldLst = TUserService.getGoldTopTen();
            scoreLst = TUserService.getScoreTopTen();
            resCount = TResService.getResCount();
        }catch (Exception e){
            logger.error("index接口查询数据库出现问题:"+e.getMessage());
            WrapJson.getInstance().wrapJson(jsonObject,Status.Error.getMsg(),Status.Error.getCode(),null);
        }
        JSONObject paramMap = new JSONObject();
        if (goldLst.isEmpty()||scoreLst.isEmpty()){
            WrapJson.getInstance().wrapJson(jsonObject,Status.Fail.getMsg(),Status.Fail.getCode(),paramMap);
        }else {
            paramMap.put("gold",goldLst);
            paramMap.put("score",scoreLst);
            paramMap.put("resCount",resCount);
            WrapJson.getInstance().wrapJson(jsonObject,Status.SUCCESS.getMsg(),Status.SUCCESS.getCode(),paramMap);
        }
        return JsonUtil.getInstense().getJsonp(jsonObject, callback);
    }

}
