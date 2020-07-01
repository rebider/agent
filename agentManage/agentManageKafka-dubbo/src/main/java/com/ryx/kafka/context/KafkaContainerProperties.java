package com.ryx.kafka.context;

import com.ryx.credit.common.util.PropUtils;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.BatchErrorHandler;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.GenericErrorHandler;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.support.LogIfLevelEnabled;
import org.springframework.kafka.support.TopicPartitionInitialOffset;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 作者：cx
 * 时间：2019/10/18
 * 描述：
 */
public class KafkaContainerProperties extends ContainerProperties {

    private Logger logger = LoggerFactory.getLogger(KafkaContainerProperties.class);


    public static final long DEFAULT_POLL_TIMEOUT = 1000L;
    public static final int DEFAULT_SHUTDOWN_TIMEOUT = 10000;
    public static final int DEFAULT_MONITOR_INTERVAL = 30;
    public static final float DEFAULT_NO_POLL_THRESHOLD = 3.0F;
    private final String[] topics;
    private final Pattern topicPattern;
    private final TopicPartitionInitialOffset[] topicPartitions;
    private AbstractMessageListenerContainer.AckMode ackMode;
    private int ackCount;
    private long ackTime;
    private Object messageListener;
    private volatile long pollTimeout;
    private AsyncListenableTaskExecutor consumerTaskExecutor;
    private GenericErrorHandler<?> errorHandler;
    private long shutdownTimeout;
    private ConsumerRebalanceListener consumerRebalanceListener;
    private OffsetCommitCallback commitCallback;
    private boolean syncCommits;
    private boolean ackOnError;
    private Long idleEventInterval;
    private String groupId;
    private PlatformTransactionManager transactionManager;
    private int monitorInterval;
    private TaskScheduler scheduler;
    private float noPollThreshold;
    private String clientId;
    private boolean logContainerConfig;
    private LogIfLevelEnabled.Level commitLogLevel;


    public KafkaContainerProperties(String... topics) {
        super(topics);
        this.ackMode = AbstractMessageListenerContainer.AckMode.BATCH;
        this.pollTimeout = 1000L;
        this.shutdownTimeout = 10000L;
        this.syncCommits = true;
        this.ackOnError = true;
        this.monitorInterval = 30;
        this.noPollThreshold = 3.0F;
        this.clientId = "";
        this.commitLogLevel = LogIfLevelEnabled.Level.DEBUG;
        Assert.notEmpty(topics, "An array of topicPartitions must be provided");
        String WalletQ_Topic = PropUtils.getProp("AgentQ_Topic");
        String WalletQ_client = PropUtils.getProp("AgentQ_client");
        List topicslist_cons = new ArrayList();
        if (!StringUtils.isEmpty(WalletQ_Topic)) {
            List topicslist = Arrays.asList(topics);
            if(!topicslist.contains(WalletQ_Topic)){
                topicslist_cons.add(WalletQ_Topic);
                topicslist_cons.addAll(topicslist);
                logger.info("添加动态主题到主体列表:{},{}",topicslist_cons,WalletQ_Topic);
            }
        }else{
            topicslist_cons.addAll(Arrays.asList(topics));
            logger.info("添加动态主题到主体列表:{} 没有动态主题",topicslist_cons);
        }
        if (!StringUtils.isEmpty(WalletQ_client)) {
            this.clientId=WalletQ_client;
            logger.info("动态clientId:{}",WalletQ_client);
        }
        this.topics = (String[])topicslist_cons.toArray(new String[topicslist_cons.size()]);
        for (String topic : this.topics) {
            logger.info("监听主题topics:{}",topic);
        }

        this.topicPattern = null;
        this.topicPartitions = null;
    }

    public KafkaContainerProperties(Pattern topicPattern) {
        super(topicPattern);
        this.ackMode = AbstractMessageListenerContainer.AckMode.BATCH;
        this.pollTimeout = 1000L;
        this.shutdownTimeout = 10000L;
        this.syncCommits = true;
        this.ackOnError = true;
        this.monitorInterval = 30;
        this.noPollThreshold = 3.0F;
        this.clientId = "";
        this.commitLogLevel = LogIfLevelEnabled.Level.DEBUG;
        this.topics = null;
        this.topicPattern = topicPattern;
        this.topicPartitions = null;
    }

    public KafkaContainerProperties(TopicPartitionInitialOffset... topicPartitions) {
        super(topicPartitions);
        this.ackMode = AbstractMessageListenerContainer.AckMode.BATCH;
        this.pollTimeout = 1000L;
        this.shutdownTimeout = 10000L;
        this.syncCommits = true;
        this.ackOnError = true;
        this.monitorInterval = 30;
        this.noPollThreshold = 3.0F;
        this.clientId = "";
        this.commitLogLevel = LogIfLevelEnabled.Level.DEBUG;
        this.topics = null;
        this.topicPattern = null;
        Assert.notEmpty(topicPartitions, "An array of topicPartitions must be provided");
        this.topicPartitions = (TopicPartitionInitialOffset[])(new LinkedHashSet(Arrays.asList(topicPartitions))).toArray(new TopicPartitionInitialOffset[topicPartitions.length]);
    }

    public void setMessageListener(Object messageListener) {
        this.messageListener = messageListener;
    }

    public void setAckMode(AbstractMessageListenerContainer.AckMode ackMode) {
        Assert.notNull(ackMode, "'ackMode' cannot be null");
        this.ackMode = ackMode;
    }

    public void setPollTimeout(long pollTimeout) {
        this.pollTimeout = pollTimeout;
    }

    public void setAckCount(int count) {
        Assert.state(count > 0, "'ackCount' must be > 0");
        this.ackCount = count;
    }

    public void setAckTime(long ackTime) {
        Assert.state(ackTime > 0L, "'ackTime' must be > 0");
        this.ackTime = ackTime;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void setBatchErrorHandler(BatchErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void setConsumerTaskExecutor(AsyncListenableTaskExecutor consumerTaskExecutor) {
        this.consumerTaskExecutor = consumerTaskExecutor;
    }

    public void setShutdownTimeout(long shutdownTimeout) {
        this.shutdownTimeout = shutdownTimeout;
    }

    public void setConsumerRebalanceListener(ConsumerRebalanceListener consumerRebalanceListener) {
        this.consumerRebalanceListener = consumerRebalanceListener;
    }

    public void setCommitCallback(OffsetCommitCallback commitCallback) {
        this.commitCallback = commitCallback;
    }

    public void setSyncCommits(boolean syncCommits) {
        this.syncCommits = syncCommits;
    }

    public void setIdleEventInterval(Long idleEventInterval) {
        this.idleEventInterval = idleEventInterval;
    }

    public void setAckOnError(boolean ackOnError) {
        this.ackOnError = ackOnError;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String[] getTopics() {
        return this.topics;
    }

    public Pattern getTopicPattern() {
        return this.topicPattern;
    }

    public TopicPartitionInitialOffset[] getTopicPartitions() {
        return this.topicPartitions;
    }

    public AbstractMessageListenerContainer.AckMode getAckMode() {
        return this.ackMode;
    }

    public int getAckCount() {
        return this.ackCount;
    }

    public long getAckTime() {
        return this.ackTime;
    }

    public Object getMessageListener() {
        return this.messageListener;
    }

    public long getPollTimeout() {
        return this.pollTimeout;
    }

    public AsyncListenableTaskExecutor getConsumerTaskExecutor() {
        return this.consumerTaskExecutor;
    }

    public GenericErrorHandler<?> getGenericErrorHandler() {
        return this.errorHandler;
    }

    public long getShutdownTimeout() {
        return this.shutdownTimeout;
    }

    public ConsumerRebalanceListener getConsumerRebalanceListener() {
        return this.consumerRebalanceListener;
    }

    public OffsetCommitCallback getCommitCallback() {
        return this.commitCallback;
    }

    public boolean isSyncCommits() {
        return this.syncCommits;
    }

    public Long getIdleEventInterval() {
        return this.idleEventInterval;
    }

    public boolean isAckOnError() {
        return this.ackOnError && !AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE.equals(this.ackMode) && !AbstractMessageListenerContainer.AckMode.MANUAL.equals(this.ackMode);
    }

    public String getGroupId() {
        return this.groupId;
    }

    public PlatformTransactionManager getTransactionManager() {
        return this.transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public int getMonitorInterval() {
        return this.monitorInterval;
    }

    public void setMonitorInterval(int monitorInterval) {
        this.monitorInterval = monitorInterval;
    }

    public TaskScheduler getScheduler() {
        return this.scheduler;
    }

    public void setScheduler(TaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public float getNoPollThreshold() {
        return this.noPollThreshold;
    }

    public void setNoPollThreshold(float noPollThreshold) {
        this.noPollThreshold = noPollThreshold;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean isLogContainerConfig() {
        return this.logContainerConfig;
    }

    public void setLogContainerConfig(boolean logContainerConfig) {
        this.logContainerConfig = logContainerConfig;
    }

    public LogIfLevelEnabled.Level getCommitLogLevel() {
        return this.commitLogLevel;
    }

    public void setCommitLogLevel(LogIfLevelEnabled.Level commitLogLevel) {
        Assert.notNull(commitLogLevel, "'commitLogLevel' cannot be nul");
        this.commitLogLevel = commitLogLevel;
    }

    public String toString() {
        return "ContainerProperties [" + (this.topics != null?"topics=" + Arrays.toString(this.topics):"") + (this.topicPattern != null?", topicPattern=" + this.topicPattern:"") + (this.topicPartitions != null?", topicPartitions=" + Arrays.toString(this.topicPartitions):"") + ", ackMode=" + this.ackMode + ", ackCount=" + this.ackCount + ", ackTime=" + this.ackTime + ", messageListener=" + this.messageListener + ", pollTimeout=" + this.pollTimeout + (this.consumerTaskExecutor != null?", consumerTaskExecutor=" + this.consumerTaskExecutor:"") + (this.errorHandler != null?", errorHandler=" + this.errorHandler:"") + ", shutdownTimeout=" + this.shutdownTimeout + (this.consumerRebalanceListener != null?", consumerRebalanceListener=" + this.consumerRebalanceListener:"") + (this.commitCallback != null?", commitCallback=" + this.commitCallback:"") + ", syncCommits=" + this.syncCommits + ", ackOnError=" + this.ackOnError + ", idleEventInterval=" + (this.idleEventInterval == null?"not enabled":this.idleEventInterval) + (this.groupId != null?", groupId=" + this.groupId:"") + (this.transactionManager != null?", transactionManager=" + this.transactionManager:"") + ", monitorInterval=" + this.monitorInterval + (this.scheduler != null?", scheduler=" + this.scheduler:"") + ", noPollThreshold=" + this.noPollThreshold + (StringUtils.hasText(this.clientId)?", clientId=" + this.clientId:"") + "]";
    }
}
