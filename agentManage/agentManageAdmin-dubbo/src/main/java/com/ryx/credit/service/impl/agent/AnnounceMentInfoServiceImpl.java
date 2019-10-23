package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.dao.COrganizationMapper;
import com.ryx.credit.dao.agent.*;
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
        announceMentInfoVo.setAnnoStat(new BigDecimal(0));
        announceMentInfoVo.setCreateTm(date);
        announceMentInfoMapper.insert(announceMentInfoVo);
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
            logger.info("添家平台{}",plat);
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
        int i = annoPlatformRelaService.batchSave(ralas);

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
        return ResultVO.success("保存成功");
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

        pageInfo.setRows(announceMentInfoMapper.selectAnnMaintain(map,page));
        pageInfo.setTotal(announceMentInfoMapper.selectCountAnnMaintain(map));
        return pageInfo;
    }
    //普通查看公告页面
    @Override
    public PageInfo selectAnnViewsRead(Page page, Map reqMap) {
        PageInfo pageInfo = new PageInfo();
        List<String> annoIds = annoPlatformRelaMapper.selectAnnoIds(reqMap);
        logger.info("非代理商可读公告{}",annoIds);
        reqMap.put("annoIds",annoIds);
        List<String> orgs = new ArrayList<>();
        if (reqMap.get("pubOrg")!=null && !"".equals(String.valueOf(reqMap.get("pubOrg")))){
            orgs.add(String.valueOf(reqMap.get("pubOrg")));
            List<String> pubOrg = organizationMapper.selectSubOrg(orgs);
            reqMap.put("pubOrg",pubOrg);
            logger.info("非代理商可读机构{}",pubOrg);
        }else {
            reqMap.put("pubOrg",orgs);
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
        String agUniqNum = agent.getAgUniqNum();
        Map<String,Object> map = new HashMap<>();
        map.put("agentId",agUniqNum);
        logger.info("代理商查看公告,唯一编码{}",agUniqNum);
        map.put("cloReviewStatus", Status.STATUS_3.status);
        List<String> plats = agentBusInfoMapper.queryBusPlatform(map);
        List<String> docPros = agentBusInfoMapper.queryAgDocPro(map);
        List<String> busTypes = agentBusInfoMapper.queryBusType(map);
        logger.info("业务:{},机构:{},业务平台:{}",plats,docPros,busTypes);
        par.put("platfromPerm",plats);
        par.put("orgIds",docPros);
        par.put("busTypes",busTypes);
        List<String> annoIds = annoPlatformRelaMapper.selectAnnoIds(par);
        logger.info("代理商可读公告{}",annoIds);
        par.put("annoIds",annoIds);
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

}
