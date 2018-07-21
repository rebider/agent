package com.ryx.credit.service.dict;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.PayComp;
import com.ryx.credit.pojo.admin.agent.TemplateAgreement;

import java.util.List;

/**
 * @author yangmx
 * @desc 模板协议维护服务
 */
public interface TemplateAgreementService {

    /**
     * 获取全部协议列表
     * @param page
     * @param templateAgreement
     * @return
     */
    PageInfo getTempAgreeList(Page page, TemplateAgreement templateAgreement);

    /**
     * 根据主键修改模板协议
     * @param id
     * @return
     */
    TemplateAgreement getTempAgreeById(String id);

    /**
     * 获取有效成功状态的协议列表
     * @return
     */
    List<TemplateAgreement> getTempAgreeSuccessList();

    /**
     * 新增协议模板
     *
     * @param templateAgreement
     */
    void insertTempAgreement(TemplateAgreement templateAgreement);

    /**
     * 修改协议模板
     * @param templateAgreement
     */
    void updateTempAgreement(TemplateAgreement templateAgreement);
}