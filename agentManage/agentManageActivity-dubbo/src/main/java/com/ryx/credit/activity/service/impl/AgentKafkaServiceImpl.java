package com.ryx.credit.activity.service.impl;

import com.ryx.credit.activity.dao.KafkaSendMessageMapper;
import com.ryx.credit.activity.entity.KafkaSendMessage;
import com.ryx.credit.common.enumc.KafkaMessageTopic;
import com.ryx.credit.common.enumc.KafkaMessageType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.service.AgentKafkaService;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.quorum.auth.QuorumAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.UUID;

/**
 * 作者：cx
 * 时间：2019/9/18
 * 描述：kafka服务
 */
@Service("agentKafkaService")
public class AgentKafkaServiceImpl implements AgentKafkaService {


    private static DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static DateTimeFormatter time = DateTimeFormatter.ofPattern("HH24mmss");

    private Logger logger = LoggerFactory.getLogger(AgentKafkaServiceImpl.class);
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private KafkaSendMessageMapper kafkaSendMessageMapper;
    @Autowired
    private AgentKafkaService agentKafkaService;

    /**
     * 发送一条String消息到主体
     * @param topic
     * @param message
     * @return
     */
    @Override
    public AgentResult sendPayMentMessage(String agentId, String agentName, String busId, String busnum, KafkaMessageType ktype, String topic, String message) {
        logger.info("发送消息到kafka {}",message);
        AgentResult agentResult = agentKafkaService.sendPayMentMessageDb(agentId,agentName,busId,busnum,ktype,topic,message);
        logger.info("发送消息到kafka 数据库保存 {}",agentResult.getMsg());
        if(agentResult.isOK()){
            try {
                KafkaSendMessage record = (KafkaSendMessage)agentResult.getData();
                if(
                   StringUtils.isNotBlank(record.getBusnum()) && KafkaMessageType.PAYMENT.code.equals(record.getKtype())
                   ||
                   KafkaMessageType.CARD.code.equals(record.getKtype())
                   ) {
                    logger.info("发送消息到kafka 平台编号不为空发送kafka {}",message);
                    ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic, record.getId(), record.getKmessage());
                    listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                        @Override
                        public void onFailure(Throwable ex) {
                            if (ex instanceof KafkaProducerException) {
                                ProducerRecord<String, String> recode = (ProducerRecord<String, String>) ((KafkaProducerException) ex).getProducerRecord();
                                logger.info("kafka 发送消息失败 {} {} {}", recode.key(), recode.value(), ex.getMessage());
                                String kyes = recode.key();
                                KafkaSendMessage record = kafkaSendMessageMapper.selectByPrimaryKey(kyes);
                                record.setStatus(Status.STATUS_2.status);
                                kafkaSendMessageMapper.updateByPrimaryKeySelective(record);
                            }
                            logger.error("kafka 发送消息失败 {}", ex.getMessage());
                            ex.printStackTrace();
                        }

                        @Override
                        public void onSuccess(SendResult<String, String> result) {
                            try {
                                String kyes = result.getProducerRecord().key();
                                logger.info("kafka 发送消息成功 {} {}", result.getProducerRecord().key(), result.getProducerRecord().value());
                                KafkaSendMessage record = kafkaSendMessageMapper.selectByPrimaryKey(kyes);
                                record.setStatus(Status.STATUS_1.status);
                                kafkaSendMessageMapper.updateByPrimaryKeySelective(record);
                            } catch (Exception e) {
                                logger.error("kafka 发送消息失败 {} {} {}", result.getProducerRecord().key(), result.getProducerRecord().value(), e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    });
                    logger.info("发送消息到kafka 平台编号不为空发送kafka 完成 {}",message);
                }else{
                    logger.info("发送消息到kafka 平台编号为空发送kafka {}",message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            logger.info("发送消息到kafka 数据库保存失败 {}",message);
        }
        return AgentResult.ok();
    }


    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    @Override
    public AgentResult sendPayMentMessageDb(String agentId, String agentName, String busId, String busnum, KafkaMessageType ktype, String topic, String message) {
        LocalDateTime ldt = LocalDateTime.now();
        KafkaSendMessage record = new KafkaSendMessage();
        record.setId(UUID.randomUUID().toString().replace("-",""));
        record.setAgentId(agentId);
        record.setAgentName(agentName);
        record.setBusid(busId);
        record.setBusnum(busnum);
        record.setKtype(ktype.code);
        record.setKtopic(topic);
        record.setKmessage(message);
        record.setcDateStr(ldt.format(date));
        record.setcTimeStr(ldt.format(time));
        record.setStatus(Status.STATUS_0.status);
        if(1==kafkaSendMessageMapper.insertSelective(record)) {
            AgentResult result = AgentResult.ok();
            result.setData(record);
            return result;
        }
        return AgentResult.fail();
    }

    @Override
    public AgentResult sendPayMentMessageById(String id) {
        logger.info("发送消息到kafka {}",id);
        KafkaSendMessage record =  kafkaSendMessageMapper.selectByPrimaryKey(id);
        if(StringUtils.isNotBlank(record.getBusnum()) && Status.STATUS_0.status.compareTo(record.getStatus())==0) {
            logger.info("发送消息到kafka Busnum不为空 {}",id);
            ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(record.getKtopic(), record.getId(), record.getKmessage());
            listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onFailure(Throwable ex) {
                    if (ex instanceof KafkaProducerException) {
                        ProducerRecord<String, String> recode = (ProducerRecord<String, String>) ((KafkaProducerException) ex).getProducerRecord();
                        logger.info("kafka 发送消息失败 {} {} {}", recode.key(), recode.value(), ex.getMessage());
                        String kyes = recode.key();
                        KafkaSendMessage record = kafkaSendMessageMapper.selectByPrimaryKey(kyes);
                        record.setStatus(Status.STATUS_2.status);
                        kafkaSendMessageMapper.updateByPrimaryKeySelective(record);
                    }
                    logger.error("kafka 发送消息失败 {}", ex.getMessage());
                    ex.printStackTrace();
                }

                @Override
                public void onSuccess(SendResult<String, String> result) {
                    try {
                        String kyes = result.getProducerRecord().key();
                        logger.info("kafka 发送消息成功 {} {}", result.getProducerRecord().key(), result.getProducerRecord().value());
                        KafkaSendMessage record = kafkaSendMessageMapper.selectByPrimaryKey(kyes);
                        record.setStatus(Status.STATUS_1.status);
                        kafkaSendMessageMapper.updateByPrimaryKeySelective(record);
                    } catch (Exception e) {
                        logger.error("kafka 发送消息失败 {} {} {}", result.getProducerRecord().key(), result.getProducerRecord().value(), e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
        }else{
            logger.info("发送消息到kafka Busnum为空 {}",id);
        }
        logger.info("发送消息到kafka 完成 {}",id);
        return AgentResult.ok();
    }
}
