package com.ryx.credit.activity.service.impl;

import com.ryx.credit.activity.dao.KafkaSendMessageMapper;
import com.ryx.credit.activity.entity.KafkaSendMessage;
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

    /**
     * 发送一条String消息到主体
     * @param topic
     * @param message
     * @return
     */
    @Override
    public AgentResult sendPayMentMessage(String agentId,String agentName,String busId,String busnum,String ktype,String topic, String message) {
        LocalDateTime ldt = LocalDateTime.now();
        KafkaSendMessage record = new KafkaSendMessage();
        record.setId(UUID.randomUUID().toString().replace("-",""));
        record.setAgentId(agentId);
        record.setAgentName(agentName);
        record.setBusid(busId);
        record.setBusnum(busnum);
        record.setKtype(ktype);
        record.setKtopic(topic);
        record.setKmessage(message);
        record.setcDateStr(ldt.format(date));
        record.setcDateStr(ldt.format(time));
        record.setStatus(Status.STATUS_0.status);
        if(1==kafkaSendMessageMapper.insertSelective(record)){
            if(StringUtils.isNotBlank(record.getBusnum())) {
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
            }
        }
        return AgentResult.ok();
    }


    @Override
    public AgentResult sendPayMentMessage(String id) {
        KafkaSendMessage record =  kafkaSendMessageMapper.selectByPrimaryKey(id);
        if(StringUtils.isNotBlank(record.getBusnum()) && Status.STATUS_0.status.compareTo(record.getStatus())==0) {
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
        }
        return AgentResult.ok();
    }
}
