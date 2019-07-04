package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AttachmentMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.order.OrganizationMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.pojo.admin.order.OrganizationExample;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OorganizationVo;
import com.ryx.credit.pojo.admin.vo.OrganizationVo;
import com.ryx.credit.service.agent.AgentQueryService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.management.resources.agent;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Auther: lrr
 * @Date: 2019/6/12 17:56
 * @Description:
 */
@Service("oorganizationService")
public class OrganizationServiceImpl implements OrganizationService {
    private static Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Autowired
    private AgentQueryService agentQueryService;

    @Override
    public PageInfo organizationList(Page page, Organization organization) {
        HashMap<String, Object> map = new HashMap<>();
        if (null != organization) {
            if (StringUtils.isNotBlank(organization.getOrgId())) {
                map.put("orgId", organization.getOrgId());
            }
            if (StringUtils.isNotBlank(organization.getOrgName())) {
                map.put("orgName", organization.getOrgName());
            }
            if (StringUtils.isNotBlank(organization.getPlatId())) {
                map.put("platId", organization.getPlatId());
            }
        }
        List<Map<String, Object>> supplementList = organizationMapper.organizationList(map, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(supplementList);
        pageInfo.setTotal(organizationMapper.organizationCount(map));
        return pageInfo;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO organizationAdd(AgentVo agentVo) throws Exception {
        if (null != agentVo && null != agentVo.getOorganizationVoList()) {
            for (OorganizationVo ac : agentVo.getOorganizationVoList()) {
                try {
                    if (StringUtils.isBlank(ac.getPlatId())) {
                        logger.info("请选择业务平台");
                        throw new MessageException("请选择业务平台");
                    }
                    ac.setOrgNick(ac.getOrgName());
                    ac.setcUser(agentVo.getSid());
                    ac.setPlatId(ac.getPlatId().substring(0, ac.getPlatId().length() - 1));
                    if (StringUtils.isEmpty(ac.getcUser())) {
                        logger.info("操作人不能为空");
                        throw new MessageException("操作人不能为空");
                    }
                    if (StringUtils.isEmpty(ac.getAgentId())) {
                        logger.info("代理商ID不能为空");
                        throw new MessageException("代理商ID不能为空");
                    }
                    Date d = Calendar.getInstance().getTime();
                    ac.setcTime(d);
                    ac.setStatus(Status.STATUS_1.status);
                    ac.setVersion(Status.STATUS_1.status);
                    ac.setOrgId(idService.genId(TabId.O_ORGANIZATION));
                    List<String> att = ac.getOrganizationbleFile();
                    if (att != null) {
                        for (String s : att) {
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
                    if (1 != organizationMapper.insertSelective(ac)) {
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
        return ResultVO.fail("添加机构失败");
    }

    @Override
    public AgentResult organizationDelete(String id, String user) {
        if (null == user) return AgentResult.fail("操作用户不能为空");
        if (StringUtils.isBlank(id)) return AgentResult.fail("ID不能为空");
        Organization organization = new Organization();
        organization.setOrgId(id);
        organization.setuUser(user);
        organization.setStatus(Status.STATUS_0.status);
        if (1 == organizationMapper.updateByPrimaryKeySelective(organization)) {
            return AgentResult.ok("成功");
        }
        return AgentResult.fail();
    }

    @Override
    public List<Organization> selectTop() {
        OrganizationExample organizationExample = new OrganizationExample();
        OrganizationExample.Criteria criteria = organizationExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andOrgParentIsNull();
        List<Organization> organizations = organizationMapper.selectByExample(organizationExample);
        if (null == organizations || organizations.size() == 0) {
            return new ArrayList<>();
        }
        return organizations;
    }

    @Override
    public List<Organization> selectOrganization(String orgId) {
        List<Organization> organizationList = organizationMapper.selectOrganization(orgId);
        if (null != organizationList && organizationList.size() > 0) {
            for (Organization organization : organizationList) {
                organization.setAttachmentList(agentQueryService.accessoryQuery(organization.getOrgId(), AttachmentRelType.Organization.name()));

            }
        }
        return organizationList;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO organizationEdit(OrganizationVo organizationVo) throws Exception {
        if (null != organizationVo && null != organizationVo.getOrganization()) {
            try {
                Organization ac = organizationVo.getOrganization();
                Organization organization = organizationMapper.selectByPrimaryKey(ac.getOrgId());
                if (StringUtils.isNotBlank(ac.getBusinessNum())) {
                    organization.setBusinessNum(ac.getBusinessNum());
                }
                organization.setAccountName(ac.getAccountName());
                organization.setAccountBank(ac.getAccountBank());
                organization.setAccountNum(ac.getAccountNum());
                organization.setOrgNick(ac.getOrgName());
                organization.setOrgName(ac.getOrgName());
                organization.setBankCard(ac.getBankCard());
//                    organization.setOrgType(ac.getOrgType());
//                    organization.setOrgParent(ac.getOrgParent());
                organization.setAgentId(ac.getAgentId());
                organization.setCloType(ac.getCloType());
                organization.setCloRealname(ac.getCloRealname());
                organization.setCloBank(ac.getCloBank());
                organization.setCloBankBranch(ac.getCloBankBranch());
                organization.setRemark(ac.getRemark());
                organization.setuTime(Calendar.getInstance().getTime());
                organization.setBranchLineNum(ac.getBranchLineNum());
                organization.setAllLineNum(ac.getAllLineNum());
                organization.setBankRegion(ac.getBankRegion());
                organization.setPlatId(ac.getPlatId());
                if (1 != organizationMapper.updateByPrimaryKeySelective(organization)) {
                    throw new MessageException("更新机构信息失败");
                }

                //添加新的附件
                List<String> fileIdList = organizationVo.getOrganizatioTableFile();
                if (fileIdList != null) {
                    for (String fileId : fileIdList) {
                        Attachment attachment = attachmentMapper.selectByPrimaryKey(fileId);
                        if (attachment != null) {
                            if (AttDataTypeStatic.YHKSMJ.code.equals(attachment.getAttDataType() + "")) {
                                String attId = queryFile(organization, AttDataTypeStatic.YHKSMJ.code);
                                if (StringUtils.isNotBlank(attId)) {
                                    deleteFile(organization, attId);
                                }
                            }
                            if (AttDataTypeStatic.KHXUZ.code.equals(attachment.getAttDataType() + "")) {
                                String attId = queryFile(organization, AttDataTypeStatic.KHXUZ.code);
                                if (StringUtils.isNotBlank(attId)) {
                                    deleteFile(organization, attId);
                                }
                            }
                            if (AttDataTypeStatic.YBNSRZM.code.equals(attachment.getAttDataType() + "")) {
                                String attId = queryFile(organization, AttDataTypeStatic.YBNSRZM.code);
                                if (StringUtils.isNotBlank(attId)) {
                                    deleteFile(organization, attId);
                                }
                            }
                        }
                        addFile(organization, fileId);
                    }
                }
            } catch (ProcessException e) {
                e.printStackTrace();
                throw e;
            } catch (MessageException e) {
                e.printStackTrace();
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e.getMessage());
            }
        }
        return ResultVO.success(null);
    }

    @Override
    public List<Organization> queryAllOrgan() {
        logger.info("获取有效状态的所有机构数据~");
        OrganizationExample organizationExample = new OrganizationExample();
        organizationExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status);
        List<Organization> organizationList = organizationMapper.selectByExample(organizationExample);
        logger.info("queryAllOrgan().organizationList：", organizationList);
        return organizationList;
    }

    public String queryFile(Organization organization, String attDataType) {
        String fileId = "";
        //查询出附件id来进行删除
        AttachmentRelExample example = new AttachmentRelExample();
        AttachmentRelExample.Criteria criteria = example.createCriteria().andSrcIdEqualTo(organization.getOrgId()).andBusTypeEqualTo(AttachmentRelType.Organization.name()).andStatusEqualTo(Status.STATUS_1.status);
        List<AttachmentRel> attachmentRels = attachmentRelMapper.selectByExample(example);
        if (null == attachmentRels || attachmentRels.size() == 0) {
            return fileId;
        }
        for (AttachmentRel attachmentRel : attachmentRels) {
            AttachmentExample attachmentExample = new AttachmentExample();
            AttachmentExample.Criteria criteria1 = attachmentExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andIdEqualTo(attachmentRel.getAttId()).andAttDataTypeEqualTo(attDataType);
            List<Attachment> attachments = attachmentMapper.selectByExample(attachmentExample);
            if (null == attachments || attachments.size() == 0) {
                return fileId;
            }
            Attachment attachment = attachments.get(0);
            return attachment.getId();
        }
        return fileId;
    }

    private void deleteFile(Organization organization, String fileId) throws Exception {
        //删除老的附件
        AttachmentRelExample example = new AttachmentRelExample();
        example.or().andBusTypeEqualTo(AttachmentRelType.Organization.name()).andSrcIdEqualTo(organization.getOrgId()).andStatusEqualTo(Status.STATUS_1.status).andAttIdEqualTo(fileId);
        List<AttachmentRel> list = attachmentRelMapper.selectByExample(example);
        for (AttachmentRel attachmentRel : list) {
            attachmentRel.setStatus(Status.STATUS_0.status);
            int i = attachmentRelMapper.updateByPrimaryKeySelective(attachmentRel);
            if (1 != i) {
                logger.info("修改机构附件关系失败{}", attachmentRel.getId());
                throw new MessageException("更新机构附件失败");
            }
        }
    }


    private void addFile(Organization organization, String fileId) {
        AttachmentRel record = new AttachmentRel();
        record.setAttId(fileId);
        record.setSrcId(organization.getOrgId());
        record.setcUser(organization.getcUser());
        record.setcTime(Calendar.getInstance().getTime());
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(AttachmentRelType.Organization.name());
        record.setId(idService.genId(TabId.a_attachment_rel));
        int i = attachmentRelMapper.insertSelective(record);
        if (1 != i) {
            logger.info("机构信息附件关系失败");
            throw new ProcessException("更新机构信息失败");
        }
    }

    @Override
    public Organization selectById(String id) {
        if(StringUtils.isNotBlank(id))
        return organizationMapper.selectByPrimaryKey(id);
        return null;
    }
}
