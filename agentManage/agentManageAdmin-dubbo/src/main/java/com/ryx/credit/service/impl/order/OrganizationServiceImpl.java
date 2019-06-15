package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AttachmentMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.order.OrganizationMapper;
import com.ryx.credit.pojo.admin.agent.Attachment;
import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.pojo.admin.order.OOrder;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.pojo.admin.order.OrganizationExample;
import com.ryx.credit.pojo.admin.vo.AgentColinfoVo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OorganizationVo;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.impl.agent.AgentColinfoServiceImpl;
import com.ryx.credit.service.order.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.management.resources.agent;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Auther: lrr
 * @Date: 2019/6/12 17:56
 * @Description:
 */
@Service("oorganizationService")
public class OrganizationServiceImpl implements OrganizationService{
    private static Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;

    @Override
    public PageInfo organizationList(Page page, Organization organization) {
        HashMap<String, Object> map = new HashMap<>();
        if (null!=organization){
            if (StringUtils.isNotBlank(organization.getOrgId())){
                map.put("orgId",organization.getOrgId());
            }
            if (StringUtils.isNotBlank(organization.getOrgName())){
                map.put("orgName",organization.getOrgName());
            }
            if (StringUtils.isNotBlank(organization.getPlatId())){
                map.put("platId",organization.getPlatId());
            }
        }
        List<Map<String, Object>> supplementList = organizationMapper.organizationList(map, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(supplementList);
        pageInfo.setTotal(organizationMapper.organizationCount(map));
        return pageInfo;
    }

    @Override
    public ResultVO organizationAdd(AgentVo agentVo) {

        for (OorganizationVo ac : agentVo.getOorganizationVoList()) {
            try {
                ac.setcUser(agentVo.getSid());
                ac.setPlatId(ac.getPlatId().substring(0,ac.getPlatId().length() - 1));
                if(StringUtils.isEmpty(ac.getcUser())){
                    throw new ProcessException("操作人不能为空");
                }
                if(StringUtils.isEmpty(ac.getAgentId())){
                    throw new ProcessException("代理商ID不能为空");
                }
                Date d = Calendar.getInstance().getTime();
                ac.setcTime(d);
                ac.setStatus(Status.STATUS_1.status);
                ac.setVersion(Status.STATUS_1.status);
                ac.setOrgId(idService.genId(TabId.O_ORGANIZATION));
                //银行卡扫描件
                boolean isHaveYHKSMJ = false;
                //开户许可证
                boolean isHaveKHXUZ = false;
                //一般纳税人证明
                boolean isHaveYBNSRZM = false;
                List<String> att = ac.getOrganizationbleFile();
                if(att!=null) {
                    for (String s : att) {
                        if (org.apache.commons.lang.StringUtils.isEmpty(s)) continue;

                        Attachment attachment = attachmentMapper.selectByPrimaryKey(s);
                        if(attachment!=null){
                            if(AttDataTypeStatic.YHKSMJ.code.equals(attachment.getAttDataType()+"")){
                                isHaveYHKSMJ = true;
                            }
                            if(AttDataTypeStatic.KHXUZ.code.equals(attachment.getAttDataType()+"")){
                                isHaveKHXUZ = true;
                            }
                            if(AttDataTypeStatic.YBNSRZM.code.equals(attachment.getAttDataType()+"")){
                                isHaveYBNSRZM = true;
                            }
                        }

                        AttachmentRel record = new AttachmentRel();
                        record.setAttId(s);
                        record.setSrcId(ac.getOrgId());
                        record.setcUser(ac.getcUser());
                        record.setcTime(ac.getcTime());
                        record.setStatus(Status.STATUS_1.status);
                        record.setBusType(AttachmentRelType.Organization.name());
                        record.setId(idService.genId(TabId.a_attachment_rel));
                        if (1 != attachmentRelMapper.insertSelective(record)) {
                            logger.info("添加机构:{}", "机构添加附件关系失败");
                            throw new ProcessException("机构添加附件关系失败");
                        }
                    }
                }
                if(1!=organizationMapper.insertSelective(ac)){
                    logger.info("添加机构失败");
                    throw new ProcessException("添加机构失败");
                }
            } catch (ProcessException e) {
                e.printStackTrace();
                throw new ProcessException(e.getMessage());
            }

        }
        return ResultVO.success(agentVo.getOorganizationVoList());
    }

    @Override
    public AgentResult organizationDelete(String id, String user) {
        if (null == user) return AgentResult.fail("操作用户不能为空");
        if (StringUtils.isBlank(id)) return AgentResult.fail("ID不能为空");
        Organization organization = new Organization();
        organization.setOrgId(id);
        organization.setuUser(user);
        organization.setStatus(Status.STATUS_0.status);
        if (1==organizationMapper.updateByPrimaryKeySelective(organization)){
            return AgentResult.ok("成功");
        }
        return AgentResult.fail();
    }

    @Override
    public List<Organization> selectTop() {
        OrganizationExample organizationExample = new OrganizationExample();
        OrganizationExample.Criteria criteria = organizationExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andOrgParentIsNull();
        List<Organization> organizations = organizationMapper.selectByExample(organizationExample);
        if (null==organizations || organizations.size()==0){
            return null;
        }
        return organizations;
    }
}