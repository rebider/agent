package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.TemplateAgreement;
import com.ryx.credit.pojo.admin.agent.TemplateAgreementExample;

import java.util.List;

public interface TemplateAgreementMapper {
    int countByExample(TemplateAgreementExample example);

    int deleteByExample(TemplateAgreementExample example);

    int insert(TemplateAgreement record);

    int insertSelective(TemplateAgreement record);

    List<TemplateAgreement> selectByExampleWithBLOBs(TemplateAgreementExample example);

    List<TemplateAgreement> selectByExample(TemplateAgreementExample example);

    TemplateAgreement selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TemplateAgreement record);

    int updateByPrimaryKeyWithBLOBs(TemplateAgreement record);

    int updateByPrimaryKey(TemplateAgreement record);
}