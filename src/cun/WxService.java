package com.youyi.shukan.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.youyi.shukan.sys.comp.dao.SktConfigDao;
import com.youyi.shukan.sys.comp.om.SktConfig;
import com.youyi.tools.exception.BaseRuntimeException;
import com.youyi.tools.utils.ArrayUtils;

@Service
public class WxService {

    private static Logger LOG = LoggerFactory.getLogger(WxService.class);
    private static final ConcurrentHashMap<Integer, WxPayService> map = new ConcurrentHashMap<>();
    
    public WxPayService getWxPayService(int sktId) {

        if (!map.contains(sktId)) {
            createWxPayService(sktId);
        }
        return map.get(sktId);
    }

    @SuppressWarnings({ "unchecked","rawtypes" })
	public WxPayService createWxPayService(int sktId) {
    	SktConfigDao sktConfigDao=(SktConfigDao) DbHelper.getDao(DbHelper.DAO_NAME_SKT_CONFIG);
    	String whereSql = " where skt_id=? ";
        List params = new ArrayList<>();
        params.add(sktId);
        String orderSql = " order by id desc limit 1";
        List<SktConfig> list = sktConfigDao.getListSktConfig(whereSql, orderSql, params);
        if (!ArrayUtils.isEmpty(list)) {
        	SktConfig sktConfig = list.get(0);
        	WxPayConfig wxPayConfig = new WxPayConfig();
            wxPayConfig.setAppId(sktConfig.getAppId());
            wxPayConfig.setMchId(sktConfig.getMchId());
            wxPayConfig.setMchKey(sktConfig.getMchKey());
            wxPayConfig.setKeyPath(sktConfig.getKeyPath());
            WxPayService wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(wxPayConfig);
            map.put(sktId, wxPayService);
            return wxPayService;
        }else {
        	LOG.error("WX_PAY_CONFIG_NULL|" + sktId);
            throw new BaseRuntimeException("WX_PAY_CONFIG_NULL", "没有找到相关微信支付配置信息!");
        }
    }
}
