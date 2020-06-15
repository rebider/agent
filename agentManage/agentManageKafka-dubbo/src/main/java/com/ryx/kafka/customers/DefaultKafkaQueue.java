package com.ryx.kafka.customers;

import com.ryx.credit.common.enumc.KafkaMessageTopic;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.PropUtils;
import com.ryx.kafka.dao.KfkSendMessageMapper;
import com.ryx.kafka.pojo.KfkSendMessage;
import com.ryx.kafka.service.CardChangeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PostConstruct;

/**
 * @author chenxiao
 * @desc 分润入账申请队列
 */
@Service("defaultKafkaQueue")
public class DefaultKafkaQueue implements MessageListener<String,String> {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultKafkaQueue.class);

	@Autowired
	private KfkSendMessageMapper kfkSendMessageMapper;

	@Autowired
	private KafkaTemplate kafkaTemplateService;

	private static KafkaTemplate kafkaTemplate;
	private static String defaultTopic = "";

	@Autowired
	private CardChangeService cardChangeService;
	/**
	 * 通过take方法，移除并返回队列头部的元素，如果队列为空，则阻塞
	 */
	@PostConstruct
	public void init(){
		kafkaTemplate = kafkaTemplateService;
		defaultTopic = PropUtils.getProp("kafka.producer.defaultTopic");
		LOG.info("初始化主题:{}",defaultTopic);

	}

	@Override
	public void onMessage(ConsumerRecord<String, String> msg) {
		LOG.info("接收到kafka消息{} {} {}",msg.topic(),msg.key(),msg.value());
		try {
			/**
			 * 结算卡变更通知
			 */
			if(StringUtils.isNotBlank(msg.topic()) && KafkaMessageTopic.CardChange.code.equals(msg.topic())){
				//结算卡信息
				String key = msg.key();String value = msg.value();
				LOG.info("接收到结算卡变更通知:{} {}",key);
				//通知清结算系统
				FastMap res = FastMap.fastFailMap("未调用");
				try {
				  res =  cardChangeService.notifyCardChange(key,value);
				  KfkSendMessage kfkSendMessage = kfkSendMessageMapper.selectByPrimaryKey(key);
				  if("0000".equals(res.get("code"))){
					  kfkSendMessage.setStatus(Status.STATUS_3.status);
				  }else{
					  kfkSendMessage.setStatus(Status.STATUS_4.status);
				  }
				} catch (MessageException e) {
					e.printStackTrace();
				}finally {
					LOG.info("调用通知清结算接口:{} {}",key,res.get("msg"));
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			LOG.error("异步队列处理异常：{} {} {} {}",msg.topic(),msg.key(),msg.value(),e.getLocalizedMessage());
		}
	}

	/**
	 * 添加一笔记账
	 * @param data
	 */
	public static void queuePut(String data){
		try {
			if(StringUtils.isBlank(data)) {
				LOG.error("发送消息 {}到主题 {} 内容为空不进行发送",data,defaultTopic);
				return;
			}
			LOG.info("发送消息 {}到主题 {}",data,defaultTopic);
			ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(defaultTopic, data);
			listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onFailure(Throwable ex) {
					LOG.info("发送消息失败 {} 到主题 {} {}",data,defaultTopic,ex.getMessage());
                    if (ex instanceof KafkaProducerException) {
                        ProducerRecord<String, String> recode = (ProducerRecord<String, String>) ((KafkaProducerException) ex).getProducerRecord();
                        LOG.info("发送消息失败 {} 到主题 {} {}",recode.value(),recode.topic(),ex.getMessage());
                    }
                    LOG.info("发送消息失败 {} 到主题 {} {}",data,defaultTopic,ex.getMessage());
                    ex.printStackTrace();
                }

                @Override
                public void onSuccess(SendResult<String, String> result) {
                    try {
                        String kyes = result.getProducerRecord().key();
                        LOG.info("发送消息成功 {} 到主题 {}",result.getProducerRecord().value(),result.getProducerRecord().topic());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
			LOG.info("发送消息完成 {} 到主题 {}",data,defaultTopic);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("发送消息异常 {} 到主题 {}",data,defaultTopic,e.getMessage());

		}
	}


}