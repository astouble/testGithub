package com.youyi.shukan.task.mq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.youyi.shukan.base.MQSender;
import com.youyi.shukan.base.constant.GoodsItemType;
import com.youyi.shukan.base.constant.PtState;
import com.youyi.shukan.base.constant.TradeLogState;
import com.youyi.shukan.base.constant.TradeLogType;
import com.youyi.shukan.base.mq.MsgBody;
import com.youyi.shukan.pub.comp.dao.OrderDao;
import com.youyi.shukan.pub.comp.dao.PintuanActSettleDao;
import com.youyi.shukan.pub.comp.dao.PtTraceDao;
import com.youyi.shukan.pub.comp.dao.TradeLogDao;
import com.youyi.shukan.pub.comp.dao.UserDao;
import com.youyi.shukan.pub.comp.om.Order;
import com.youyi.shukan.pub.comp.om.PintuanActSettle;
import com.youyi.shukan.pub.comp.om.PtTrace;
import com.youyi.shukan.pub.comp.om.TradeLog;
import com.youyi.shukan.pub.comp.om.User;
import com.youyi.shukan.task.DbHelper;
import com.youyi.shukan.task.WxService;
import com.youyi.tools.utils.BaseConfig;
import com.youyi.tools.utils.DateUtil;
import com.youyi.tools.utils.LogUtils;

public class PtCreateExecutor extends BaseExecutor {

	@Resource
    WxService wxService;
	
	public PtCreateExecutor(MsgBody msgBody, String receiptHandle) {
		super(msgBody, receiptHandle);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void run() {
		// 首先根据data获取ptAct
		int sktId;
		int orderId;
		OrderDao orderDao;
		PtTraceDao traceDao;
		UserDao userDao;
		TradeLogDao tradeLogDao;
		PintuanActSettleDao pintuanActSettleDao;
		try {
			sktId = msgBody.getSktId();
			orderId = Integer.parseInt(msgBody.getData());
			orderDao = (OrderDao) DbHelper.getDao(DbHelper.DAO_NAME_ORDER);
			traceDao = (PtTraceDao) DbHelper.getDao(DbHelper.DAO_NAME_PT_TRACE);
			userDao = (UserDao) DbHelper.getDao(DbHelper.DAO_NAME_USER);
			tradeLogDao = (TradeLogDao) DbHelper.getDao(DbHelper.DAO_NAME_TRADE_LOG);
			pintuanActSettleDao = (PintuanActSettleDao) DbHelper.getDao(DbHelper.DAO_NAME_PINTUAN_ACT_SETTLE);
			Order order = orderDao.getOrder(orderId, sktId);
			PtTrace trace = traceDao.getPtTrace(order.getTraceId(), sktId);
			if(trace.getPtState() == PtState.SUCCESS.getCode() || trace.getPtState() == PtState.FAIL.getCode() || trace.getPtState() == PtState.REFUND.getCode()) {//成功、失败、或者已退款，不需要再执行延迟任务
				deleteMsg();
			}else if(trace.getPtState() == PtState.EXECUTE.getCode()) {//
				//检查时间
				long interval = order.getEndTime().getTime() - System.currentTimeMillis();
				if(interval > 5000) {//剩余时间超过5秒
					MQSender.sendPtCreate(sktId, orderId, order.getEndTime().getTime());
				}else {//最后5秒停止检票,设置过期
					Date now = new Date();
					trace.setPtState(PtState.FAIL.getCode());
					trace.setLastUptime(now);
					//先更新拼团跟踪表
					traceDao.updatePtTrace(trace);
					//将这里面的拼团订单全查出来
					List<Order> orderList=orderDao.getListOrder(" where skt_id=? and trace_id=? and act_id=?",
							null, Arrays.asList(sktId,trace.getId(),trace.getActId()), Arrays.asList(sktId));
					for(Order orderPt:orderList) {
						try {
							String outRefundNo = "ptRefund" + orderPt.getId();
				            WxPayRefundRequest refundRequest = new WxPayRefundRequest();
				            refundRequest.setOutTradeNo(orderPt.getOutTradeNo());
				            refundRequest.setOutRefundNo(outRefundNo);
				            refundRequest.setTotalFee(orderPt.getDiscountPrice());
				            refundRequest.setRefundFee(refundRequest.getTotalFee());
				            refundRequest.setRefundDesc("您参与的拼团活动“" + trace.getActTitle() + "”因人数不足，拼团失败！");
                            //微信发起退款
				            WxPayService payService = wxService.getWxPayService(sktId);
				            WxPayRefundResult refundResult = payService.refund(refundRequest);
				            if (refundResult != null && "SUCCESS".equals(refundResult.getReturnCode()) && "SUCCESS".equals(refundResult.getResultCode())) {
				            	//退款成功更新order表
				            	orderPt.setPtState(PtState.FAIL.getCode());
				            	orderPt.setLastUptime(now);
				                orderDao.updateOrder(orderPt);

				                //插入一条退款记录
				                TradeLog tradeLog = new TradeLog();
				                tradeLog.setSktId(sktId);
				                tradeLog.setUid(orderPt.getUid());
				                tradeLog.setOrderItemType(GoodsItemType.PINTUAN.getCode());
				                tradeLog.setGoodsName(orderPt.getGoodsName());
				                tradeLog.setListPic(orderPt.getGoodsPic());
				                tradeLog.setAmount(orderPt.getDiscountPrice());
				                tradeLog.setOutTradeNo(outRefundNo);
				                tradeLog.setTransferState(TradeLogState.SUCC.getCode());
				                tradeLog.setTradeType(TradeLogType.REFUND.getCode());
				                tradeLog.setCreateTime(new Date());
				                tradeLog.setLastUptime(tradeLog.getCreateTime());
				                tradeLog.setWxPayNo(refundResult.getTransactionId());
				                tradeLogDao.createTradeLog(tradeLog);
				                //更新活动统计
				                PintuanActSettle settle = pintuanActSettleDao.getPintuanActSettle(trace.getActId(),sktId);
				                if(settle==null) {
				                	settle=new PintuanActSettle();
				                	settle.setActId(trace.getActId());
				                	settle.setCardDownNum(0);
				                	settle.setCompleteGroupNum(0);
				                	settle.setCreateTime(now);
				                	settle.setFirstCommAmount(0);
				                	settle.setGroupNum(0);
				                	settle.setLastUptime(now);
				                	settle.setPayedAmount(orderPt.getDiscountPrice());
				                	settle.setPayedNum(1);
				                	settle.setRefundAmount(orderPt.getDiscountPrice());
				                	settle.setRefundNum(1);
				                	settle.setScanNum(0);
				                	settle.setSecondCommAmount(0);
				                	settle.setServiceFee(0);
				                	settle.setShareNum(0);
				                	settle.setSktId(sktId);
				                	settle.setWxFee(0);
				                }else {
				                	String sql = " update pintuan_act_settle set refund_num=refund_num-1 ,refund_amount=refund_amount - ? where skt_id=? and act_id=?";
					                List params = new ArrayList();
					                params.add(orderPt.getDiscountPrice());
					                params.add(sktId);
					                params.add(trace.getActId());
					                pintuanActSettleDao.operatePintuanActSettle(sql,params,Arrays.asList(sktId));
				                }

				                User user = userDao.getUser(orderPt.getUid(), sktId);
				                if (user != null) {
				                    //给用户推送拼团未成功已退款的模板消息
				                    sendPintuanRefundMsg(user.getOpenId(), trace.getActTitle(), tradeLog);
				                }
				            } else {
				                LogUtils.warn("refund fail order_id %d, returnCode:%s, resultCode:%s, returnMsg:%s", orderPt.getId(), refundResult.getReturnCode(), refundResult.getResultCode(), refundResult
				                    .getReturnMsg());
				            }
				        } catch (Exception e) {
				            LogUtils.error("refund|ERROR|" + orderPt.getId(), e);
				        }
					}
				}
				deleteMsg();//删除本条消息
			}
		} catch (NumberFormatException e) {
			LOG.error("parse data error", e);
		}
	}
	
	public void sendPintuanRefundMsg(String toOpenId, String actTitle, TradeLog tradeLog) {

        String page = "trade/log/list.do";
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("keyword1", "拼团活动失败退款");
        dataMap.put("keyword2", DateUtil.getDateStr(new Date()));
        dataMap.put("keyword3", actTitle);
        dataMap.put("keyword4", StringUtils.isBlank(tradeLog.getWxPayNo()) ? "" : tradeLog.getWxPayNo());
        dataMap.put("keyword5", "微信钱包");
        dataMap.put("keyword6", (tradeLog.getTransferState() == TradeLogState.FAIL.getCode()) ? "退款失败" : "退款成功");
        dataMap.put("keyword7", BaseConfig.getValue("customer.service.telephone", ""));
        dataMap.put("keyword8", tradeLog.getAmount() + "元");
//        WxTool.sendMaTmplMsg(toOpenId, TEMPLATE_ID_PINTUAN_REFUND, page, dataMap);
    }

}
