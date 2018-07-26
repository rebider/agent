package com.ryx.credit.service.impl.dict;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AttachmentMapper;
import com.ryx.credit.dao.agent.TemplateAgreementMapper;
import com.ryx.credit.pojo.admin.agent.Attachment;
import com.ryx.credit.pojo.admin.agent.TemplateAgreement;
import com.ryx.credit.pojo.admin.agent.TemplateAgreementExample;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.dict.TemplateAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author yangmx
 * @desc
 */
@Service("templateAgreementService")
public class TemplateAgreementServiceImpl implements TemplateAgreementService {
    private static BigDecimal SUCCESS_STATUS = BigDecimal.ONE;
    @Autowired
    private TemplateAgreementMapper templateAgreementMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public PageInfo getTempAgreeList(Page page, TemplateAgreement templateAgreement) {
        TemplateAgreementExample example = new TemplateAgreementExample();
        TemplateAgreementExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(templateAgreement.getAgreName())){
            criteria.andAgreNameLike("%"+templateAgreement.getAgreName()+"%");
        }
        if(StringUtils.isNotBlank(templateAgreement.getAgreVersion())){
            criteria.andAgreVersionEqualTo(templateAgreement.getAgreVersion());
        }
        if(StringUtils.isNotBlank(templateAgreement.getStatus())){
            criteria.andStatusEqualTo(new BigDecimal(templateAgreement.getStatus()));
        }

        int count = templateAgreementMapper.countByExample(example);
        example.setPage(page);
        List<TemplateAgreement> list = templateAgreementMapper.selectByExample(example);
        for (TemplateAgreement templateAgreement1:list) {
            Attachment attachment = attachmentMapper.selectByPrimaryKey(templateAgreement1.getAttrid());
            templateAgreement1.setAttrid(attachment == null ? null :attachment.getAttDbpath());
        }
        PageInfo info = new PageInfo();
        info.setTotal(count);
        info.setRows(list);
        return info;
    }

    @Override
    public TemplateAgreement getTempAgreeById(String id) {
        return templateAgreementMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TemplateAgreement> getTempAgreeSuccessList() {
        TemplateAgreementExample example = new TemplateAgreementExample();
        TemplateAgreementExample.Criteria criteria = new TemplateAgreementExample().createCriteria();
        criteria.andStatusEqualTo(SUCCESS_STATUS);
        return templateAgreementMapper.selectByExample(example);
    }

    @Override
    public void insertTempAgreement(TemplateAgreement templateAgreement) {
        if(templateAgreement != null){
            templateAgreement.setcUtime(new Date());
            templateAgreement.setcTime(new Date());
            templateAgreement.setId(idService.genId(TabId.template_agreement));
            templateAgreementMapper.insertSelective(templateAgreement);
        }
    }

    @Override
    public void updateTempAgreement(TemplateAgreement templateAgreement) {
        if(templateAgreement != null){
            templateAgreement.setcUtime(new Date());
            templateAgreementMapper.updateByPrimaryKeySelective(templateAgreement);
        }
    }
}
