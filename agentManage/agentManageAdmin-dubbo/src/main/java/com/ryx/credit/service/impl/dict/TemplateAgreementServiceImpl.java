package com.ryx.credit.service.impl.dict;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.TemplateAgreementMapper;
import com.ryx.credit.pojo.admin.agent.TemplateAgreement;
import com.ryx.credit.pojo.admin.agent.TemplateAgreementExample;
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
    @Autowired
    private TemplateAgreementMapper templateAgreementMapper;

    @Override
    public PageInfo getTempAgreeList(Page page, TemplateAgreement templateAgreement) {
        TemplateAgreementExample.Criteria example = new TemplateAgreementExample().createCriteria();
        if(StringUtils.isNotBlank(templateAgreement.getAgreName())){
            example.andAgreNameLike(templateAgreement.getAgreName());
        }
        if(StringUtils.isNotBlank(templateAgreement.getAgreVersion())){
            example.andAgreVersionEqualTo(templateAgreement.getAgreVersion());
        }
        if(StringUtils.isNotBlank(templateAgreement.getStatus())){
            example.andStatusEqualTo(new BigDecimal(templateAgreement.getStatus()));
        }

//        int count = templateAgreementMapper.countByExample(example);
        PageInfo info = new PageInfo();
//        info.setRows();
//        info.setTotal(count);
        return null;
    }

    @Override
    public TemplateAgreement getTempAgreeById(String id) {
        return templateAgreementMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TemplateAgreement> getTempAgreeSuccessList() {

        return null;
    }

    @Override
    public void insertTempAgreement(TemplateAgreement templateAgreement) {
        if(templateAgreement != null){
            templateAgreement.setcUtime(new Date());
            templateAgreement.setcUtime(new Date());
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
