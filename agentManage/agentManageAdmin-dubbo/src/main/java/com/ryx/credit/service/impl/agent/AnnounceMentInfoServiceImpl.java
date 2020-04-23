package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.dao.COrganizationMapper;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AnnounceMentInfoVo;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.IResourceService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.AnnoPlatformRelaService;
import com.ryx.credit.service.agent.AnnounceMentInfoService;
import com.ryx.credit.service.dict.IdService;
import net.sf.ehcache.hibernate.strategy.NonStrictReadWriteEhcacheCollectionRegionAccessStrategy;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * @program: agentManage
 * @description:
 * @author: ssx
 * @create: 2019-10-10 10:28
 **/
@Service("announceMentInfoService")
public class AnnounceMentInfoServiceImpl implements AnnounceMentInfoService {
    private static Logger logger = LoggerFactory.getLogger(AnnounceMentInfoServiceImpl.class);
    @Autowired
    private AnnounceMentInfoMapper announceMentInfoMapper;

    @Autowired
    private AnnoPlatformRelaMapper annoPlatformRelaMapper;

    @Autowired
    private AnnoPlatformRelaService annoPlatformRelaService;

    @Autowired
    private IdService idService;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private AttachmentRelMapper attachmentRelMapper;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private IResourceService iResourceService;

    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;

    @Autowired
    private COrganizationMapper organizationMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public ResultVO saveAnn(AnnounceMentInfoVo announceMentInfoVo) {
        ResultVO resultVO = new ResultVO();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = LocalDateTime.now().atZone(zoneId);//Combines this date-time with a time-zone to create a  ZonedDateTime.
        Date date = Date.from(zdt.toInstant());
        String annId= idService.genId(TabId.A_ANNOUNCEMENT_INFO);
        logger.info("添加公告id{}",annId);
        announceMentInfoVo.setAnnId(annId);
        announceMentInfoVo.setAnnoStat(AnnoStat.WAIT.code);
        announceMentInfoVo.setCreateTm(date);
       try {
           announceMentInfoMapper.insert(announceMentInfoVo);
       }catch (Exception e){
           logger.error("保存公告表异常");
           return ResultVO.fail("保存公告失败!");
       }

        String orgs = announceMentInfoVo.getOrgs();
        List<AnnoPlatformRela> ralas = new ArrayList<>();
        String relaId = idService.genIdInTran(TabId.A_ANNO_PLATFORM_RELA);
        AnnoPlatformRela organnoPlatformRela = new AnnoPlatformRela();
        organnoPlatformRela.setId(relaId);
        organnoPlatformRela.setAnnoId(announceMentInfoVo.getAnnId());
        organnoPlatformRela.setRangType(RangType.org.code);
        organnoPlatformRela.setRangValue(orgs.toString());
        ralas.add(organnoPlatformRela);
        List<String> plats = announceMentInfoVo.getPlats();

        plats.forEach((plat)->{
            logger.info("添加平台{}",plat);
            AnnoPlatformRela platRela = new AnnoPlatformRela();
            platRela.setId(idService.genIdInTran(TabId.A_ANNO_PLATFORM_RELA));
            platRela.setAnnoId(announceMentInfoVo.getAnnId());
            platRela.setRangType(RangType.plat.code);
            platRela.setRangValue(plat);
            ralas.add(platRela);
        });

        List<String> busTypes = announceMentInfoVo.getBusTypes();
        busTypes.forEach((busType)->{
            logger.info("添加业务{}",busType);
            AnnoPlatformRela platRela = new AnnoPlatformRela();
            platRela.setId(idService.genIdInTran(TabId.A_ANNO_PLATFORM_RELA));
            platRela.setAnnoId(announceMentInfoVo.getAnnId());
            platRela.setRangType(RangType.bustype.code);
            platRela.setRangValue(busType);
            ralas.add(platRela);
        });
        logger.info("添加关联表{}",ralas.toString());
        try {
            annoPlatformRelaService.batchSave(ralas);
        }catch (Exception e){
            logger.error("保存公告关联表异常");
            return ResultVO.fail("保存公告失败,关联表异常!");
        }

        List<String> attFiles = announceMentInfoVo.getAttFiles();
        if(attFiles!=null){
        attFiles.forEach(attfile->{

            if (StringUtils.isEmpty(attfile)) return;

            Attachment attachment = attachmentMapper.selectByPrimaryKey(attfile);
//            if(attachment!=null){
//                if(AttDataTypeStatic.GGFJ.code.equals(attachment.getAttDataType()+"")){
//                    isHaveYYZZ = true;
//                }
//            }
            AttachmentRel record = new AttachmentRel();
            record.setAttId(attfile);
            record.setSrcId(announceMentInfoVo.getAnnId());
            record.setcUser(announceMentInfoVo.getPublisher());
            record.setcTime(announceMentInfoVo.getCreateTm());
            record.setStatus(Status.STATUS_1.status);
            record.setBusType(AttachmentRelType.AnnounceMent.name());
            record.setId(idService.genId(TabId.a_attachment_rel));
            logger.info("添加公告附件关系,公告ID{},附件ID{}",announceMentInfoVo.getAnnId(),attfile);
            if (1 != attachmentRelMapper.insertSelective(record)) {
                logger.info("公告添加:{}", "添加公告附件关系失败");
                throw new ProcessException("添加公告附件关系失败");
            }
        });
        }
        return ResultVO.success("新增公告保存成功!");
    }

    @Override
    public ResultVO upStat(AnnounceMentInfo announceMentInfo,String orgStat) {
        logger.info("更新公告{},原状态{},更新为{}",announceMentInfo.getAnnId(),orgStat,announceMentInfo.getAnnoStat());
        AnnounceMentInfo orgAnno = announceMentInfoMapper.selectByPrimaryKey(announceMentInfo.getAnnId());
        if (orgAnno==null){
            logger.error("更新公告{},公告不存在",announceMentInfo.getAnnId());
            return ResultVO.fail("公告不存在请刷新后重试!");
        }
        if (!orgAnno.getAnnoStat().toString().equals(orgStat)){
            logger.error("公告{},状态不一致，不能更新",announceMentInfo.getAnnId());
            return ResultVO.fail("公告状态异常请刷新后重试!");
        }
        int update = announceMentInfoMapper.updateStatByAnno(announceMentInfo);
        if (update>0){
            return ResultVO.success("更新公告状态成功!");
        }
        logger.error("公告{},更新异常!",announceMentInfo.getAnnId());
        return ResultVO.fail("更新公告状态异常!");
    }

    @Override
    public AnnounceMentInfo queryById(String annId) {
        AnnounceMentInfo announceMentInfo = announceMentInfoMapper.selectByPrimaryKey(annId);
        return announceMentInfo;
    }
    //公告运维
    @Override
    public PageInfo selectAnnViewsMaintain(Page page, Map map) {
        PageInfo pageInfo = new PageInfo();
        List<String> orgs = new ArrayList<>();
        if (map.get("pubOrg")!=null && !"".equals(String.valueOf(map.get("pubOrg"))) ){
            orgs.add(String.valueOf(map.get("pubOrg")));
            List<String> pubOrg = organizationMapper.selectSubOrg(orgs);
            map.put("pubOrg",pubOrg);
            logger.info("公告运维可读机构{}",pubOrg);
        }else {
            map.put("pubOrg",orgs);
        }
//        List<Map<String,Object>> announceMentInfos = announceMentInfoMapper.selectAnnMaintain(map, page);
//        announceMentInfos.forEach(announceMentInfo->{
//        });
        pageInfo.setRows(announceMentInfoMapper.selectAnnMaintain(map,page));
        pageInfo.setTotal(announceMentInfoMapper.selectCountAnnMaintain(map));
        return pageInfo;
    }
    //普通查看公告页面
    @Override
    public PageInfo selectAnnViewsRead(Page page, Map reqMap) {
        PageInfo pageInfo = new PageInfo();
//        List<String> annoIds = annoPlatformRelaMapper.selectAnnoIds(reqMap);
//        logger.info("非代理商可读公告{}",annoIds);
//        reqMap.put("annoIds",annoIds);
        List<String> orgs = new ArrayList<>();
        if (reqMap.get("pubOrg")!=null && !"".equals(String.valueOf(reqMap.get("pubOrg")))){
            orgs.add(String.valueOf(reqMap.get("pubOrg")));
            List<String> pubOrg = organizationMapper.selectSubOrg(orgs);
            reqMap.put("pubOrg",pubOrg);
            logger.info("非代理商可读机构{}",pubOrg);
        }else {
            reqMap.put("pubOrg",orgs);
            logger.info("非代理商可读机构{}",orgs);
        }
        pageInfo.setRows(announceMentInfoMapper.selectAnnReader(reqMap,page));
        pageInfo.setTotal(announceMentInfoMapper.selectCountAnnReader(reqMap));
        return pageInfo;
    }


    //公告管理
    @Override
    public PageInfo selectAnnViewsManage(Page page, Map map) {
        logger.info("公告管理service");
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(announceMentInfoMapper.selectAnnManage(map,page));
        pageInfo.setTotal(announceMentInfoMapper.selectCountAnnManage(map));
        return pageInfo;
    }
    //代理商查看公告
    @Override
    public PageInfo selectAnnViewsAgent(Page page, Map par,Long userId) {
        PageInfo pageInfo = new PageInfo();
        par.put("userId",userId);
        par.put("toAgent","0");//是否发布至代理商标志
        Agent agent = agentService.queryAgentByUserId(String.valueOf(userId));
        if (agent==null){
            return  pageInfo;
        }
        String agUniqNum = agent.getId();
        Map<String,Object> map = new HashMap<>();
        map.put("agentId",agUniqNum);
        logger.info("代理商查看公告,唯一编码{}",agUniqNum);
        map.put("cloReviewStatus", Status.STATUS_3.status);
        List<String> plats = agentBusInfoMapper.queryBusPlatform(map);
        List<String> docPros = agentBusInfoMapper.queryAgDocPro(map);
        List<String> busTypes = agentBusInfoMapper.queryBusType(map);
        //查询信息判断
        if (!(plats.size() > 0 &&  docPros.size() > 0 && busTypes.size() > 0)) {
            return  new PageInfo();
        }
        logger.info("业务:{},机构:{},业务平台:{}",plats,docPros,busTypes);
        logger.info("查找代理商{}上级机构编号",docPros);
        Map<String,Object> orgmap = new HashMap<>();
        orgmap.put("agentId",agUniqNum);
        orgmap.put("cloReviewStatus", Status.STATUS_3.status);
        List<COrganization> cOrganizations = organizationMapper.selectPorgByorgs(docPros);
        List<String> allOrg=new ArrayList<>();
        cOrganizations.forEach(org->{
            allOrg.add(String.valueOf(org.getId()));
        });
        par.put("platfromPerm",plats);
        par.put("orgIds",allOrg);
        par.put("busTypes",busTypes);
        par.put("agent",Status.STATUS_0.status);
//        List<String> annoIds = annoPlatformRelaMapper.selectAnnoIds(par);
//        logger.info("代理商可读公告{}",annoIds);
//        par.put("annoIds",annoIds);
        List<String> orgs = new ArrayList<>();
        if (par.get("pubOrg")!=null && !"".equals(String.valueOf(par.get("pubOrg")))){
            List<String> orgsTmp = new ArrayList<>();
            orgsTmp.add(String.valueOf(par.get("pubOrg")));
            List<String> pubOrg = organizationMapper.selectSubOrg(orgsTmp);
            par.put("pubOrg",pubOrg);
            logger.info("代理商可读机构{}",pubOrg);
        }else{
            par.put("pubOrg",orgs);
        }
        pageInfo.setRows(announceMentInfoMapper.selectAnnReader(par,page));
        pageInfo.setTotal(announceMentInfoMapper.selectCountAnnReader(par));
        return  pageInfo;
    }


    @Override
    public List<Attachment> queryAttByAnnoid(String id,String busType) {
        return attachmentMapper.accessoryQuery(id, busType);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public ResultVO updateAnnInfo(AnnounceMentInfoVo announceMentInfoVo) throws MessageException {
        ResultVO resultVO = new ResultVO();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = LocalDateTime.now().atZone(zoneId);//Combines this date-time with a time-zone to create a  ZonedDateTime.
        Date date = Date.from(zdt.toInstant());
        AnnounceMentInfo announceMentInfo = announceMentInfoMapper.selectByPrimaryKey(announceMentInfoVo.getAnnId());
        if (null == announceMentInfo){
            return ResultVO.fail("公告不存在!");
        }
        announceMentInfo.setAnnoStat(AnnoStat.WAIT.code);
        announceMentInfo.setCreateTm(date);
        try {
            if (1 != announceMentInfoMapper.updateStatByAnno(announceMentInfo)){
                return ResultVO.fail("未更新公告!");
            }
        }catch (Exception e){
            logger.error("更新公告表异常");
            return ResultVO.fail("更新公告失败!");
        }

        AnnoPlatformRelaExample annoPlatformRelaExample = new AnnoPlatformRelaExample();
        annoPlatformRelaExample.or().andAnnoIdEqualTo(announceMentInfoVo.getAnnId());
        try {
            annoPlatformRelaMapper.deleteByExample(annoPlatformRelaExample);
        }catch (Exception e){
            throw new MessageException("删除关联平台信息失败");
        }


        String orgs = announceMentInfoVo.getOrgs();
        List<AnnoPlatformRela> ralas = new ArrayList<>();
        String relaId = idService.genIdInTran(TabId.A_ANNO_PLATFORM_RELA);
        AnnoPlatformRela organnoPlatformRela = new AnnoPlatformRela();
        organnoPlatformRela.setId(relaId);
        organnoPlatformRela.setAnnoId(announceMentInfoVo.getAnnId());
        organnoPlatformRela.setRangType(RangType.org.code);
        organnoPlatformRela.setRangValue(orgs.toString());
        ralas.add(organnoPlatformRela);
        List<String> plats = announceMentInfoVo.getPlats();

        plats.forEach((plat)->{
            logger.info("添加平台{}",plat);
            AnnoPlatformRela platRela = new AnnoPlatformRela();
            platRela.setId(idService.genIdInTran(TabId.A_ANNO_PLATFORM_RELA));
            platRela.setAnnoId(announceMentInfoVo.getAnnId());
            platRela.setRangType(RangType.plat.code);
            platRela.setRangValue(plat);
            ralas.add(platRela);
        });

        List<String> busTypes = announceMentInfoVo.getBusTypes();
        busTypes.forEach((busType)->{
            logger.info("添加业务{}",busType);
            AnnoPlatformRela platRela = new AnnoPlatformRela();
            platRela.setId(idService.genIdInTran(TabId.A_ANNO_PLATFORM_RELA));
            platRela.setAnnoId(announceMentInfoVo.getAnnId());
            platRela.setRangType(RangType.bustype.code);
            platRela.setRangValue(busType);
            ralas.add(platRela);
        });
        logger.info("添加关联表{}",ralas.toString());
        try {
            annoPlatformRelaService.batchSave(ralas);
        }catch (Exception e){
            logger.error("保存公告关联表异常[修改]");
            return ResultVO.fail("修改公告失败,关联表异常!");
        }

        AttachmentRelExample recorddel = new AttachmentRelExample();
        recorddel.or().andSrcIdEqualTo(announceMentInfoVo.getAnnId());
        attachmentRelMapper.deleteByExample(recorddel);

        List<String> attFiles = announceMentInfoVo.getAttFiles();
        if(attFiles!=null){
            attFiles.forEach(attfile->{

                if (StringUtils.isEmpty(attfile)) return;

                Attachment attachment = attachmentMapper.selectByPrimaryKey(attfile);
//            if(attachment!=null){
//                if(AttDataTypeStatic.GGFJ.code.equals(attachment.getAttDataType()+"")){
//                    isHaveYYZZ = true;
//                }
//            }
                AttachmentRel record = new AttachmentRel();
                record.setAttId(attfile);
                record.setSrcId(announceMentInfoVo.getAnnId());
                record.setcUser(announceMentInfoVo.getPublisher());
                record.setcTime(announceMentInfoVo.getCreateTm());
                record.setStatus(Status.STATUS_1.status);
                record.setBusType(AttachmentRelType.AnnounceMent.name());
                record.setId(idService.genId(TabId.a_attachment_rel));
                logger.info("添加公告附件关系,公告ID{},附件ID{}",announceMentInfoVo.getAnnId(),attfile);
                if (1 != attachmentRelMapper.insertSelective(record)) {
                    logger.info("公告添加:{}", "添加公告附件关系失败");
                    throw new ProcessException("添加公告附件关系失败");
                }
            });
        }
        return ResultVO.success("修改公告保存成功!");
    }

}
