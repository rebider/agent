package com.ryx.credit.service.order;

import com.ryx.credit.common.enumc.LogType;
import com.ryx.credit.common.enumc.LogisticsSendStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 作者：cx
 * 时间：2019/4/22
 * 描述：
 */
public interface OsnOperateService {
    /**
     * 根据物流联动状态查询物流id
     * 0：未联动 1：已联动 2：联动失败， 3：部分联动失败， 4：生成明细失败，5：生成明细中，6：生成明细成功7：联动业务系统处理中
     * @param sendStatus
     * @return
     */
    List<String> queryLogicInfoIdByStatus(LogType logType, LogisticsSendStatus sendStatus);

    void genLogicDetailTask();

    /**
     * 根据物流信息生成物流明细,物流明细生成后进行物流状态的更新，更新为 4：生成明细失败 5：生成明细中 6：生成明细成功 添加版本号控制
     * @param logcId
     * @return
     */
    boolean genOlogicDetailInfo(String logcId)throws Exception;

    /**
     * 将已生成物流明细成功的物流，待联动的物流明细，分批次送到业务系统，并检查是否发送完成，完成后更新物流信息未发送完毕
     * @param logcId
     * @param batch
     * @return
     */
    boolean sendInfoToBusinessSystem(String logcId,BigDecimal batch)throws Exception;
}
