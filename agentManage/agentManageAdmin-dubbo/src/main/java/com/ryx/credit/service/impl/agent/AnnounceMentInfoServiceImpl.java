package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.AttDataTypeStatic;
import com.ryx.credit.common.enumc.AttachmentRelType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
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

    @Override
    public PageInfo selectAnnViews(Page page, Map map) {
        PageInfo pageInfo = new PageInfo();
        List<String> annoIds = annoPlatformRelaMapper.selectAnnoIds(map);
        map.put("annoIds",annoIds);
        pageInfo.setRows(announceMentInfoMapper.selectAnnRead(map,page));
        pageInfo.setTotal(announceMentInfoMapper.selectCountAnnRead(map));
        return pageInfo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public ResultVO saveAnn(AnnounceMentInfoVo announceMentInfoVo) {
        ResultVO resultVO = new ResultVO();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = LocalDateTime.now().atZone(zoneId);//Combines this date-time with a time-zone to create a  ZonedDateTime.
        Date date = Date.from(zdt.toInstant());
        String annId= idService.genId(TabId.A_ANNOUNCEMENT_INFO);
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
        organnoPlatformRela.setRangType("2");
        organnoPlatformRela.setRangValue(orgs.toString());
        ralas.add(organnoPlatformRela);
        List<String> plats = announceMentInfoVo.getPlats();

        plats.forEach((plat)->{
            AnnoPlatformRela platRela = new AnnoPlatformRela();
            platRela.setId(idService.genIdInTran(TabId.A_ANNO_PLATFORM_RELA));
            platRela.setAnnoId(announceMentInfoVo.getAnnId());
            platRela.setRangType("1");
            platRela.setRangValue(plat);
            ralas.add(platRela);
        });

        List<String> busTypes = announceMentInfoVo.getBusTypes();
        busTypes.forEach((busType)->{
            AnnoPlatformRela platRela = new AnnoPlatformRela();
            platRela.setId(idService.genIdInTran(TabId.A_ANNO_PLATFORM_RELA));
            platRela.setAnnoId(announceMentInfoVo.getAnnId());
            platRela.setRangType("0");
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

        AnnounceMentInfo orgAnno = announceMentInfoMapper.selectByPrimaryKey(announceMentInfo.getAnnId());
        if (orgAnno==null){
            return ResultVO.fail("公告不存在请刷新后重试!");
        }
        if (!orgAnno.getAnnoStat().toString().equals(orgStat))
        return ResultVO.fail("公告状态异常请刷新后重试!");
        int update = announceMentInfoMapper.updateStatByAnno(announceMentInfo);
        if (update>0){
            return ResultVO.success("保存成功");
        }
        return null;
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
        pageInfo.setRows(announceMentInfoMapper.selectAnnMaintain(map,page));
        pageInfo.setTotal(announceMentInfoMapper.selectCountAnnMaintain(map));
        return pageInfo;
    }
    //普通查看公告页面
    @Override
    public PageInfo selectAnnViewsRead(Page page, Map reqMap,Long userId) {
        PageInfo pageInfo = new PageInfo();
        logger.info("查找用户{}所属机构List",userId);
        List<String> annoIds = annoPlatformRelaMapper.selectAnnoIds(reqMap);
        reqMap.put("annoIds",annoIds);
        pageInfo.setRows(announceMentInfoMapper.selectAnnRead(reqMap,page));
        pageInfo.setTotal(announceMentInfoMapper.selectCountAnnRead(reqMap));
        return pageInfo;
    }


    //公告管理
    @Override
    public PageInfo selectAnnViewsManage(Page page, Map map) {
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
        map.put("cloReviewStatus", Status.STATUS_3.status);
        List<String> plats = agentBusInfoMapper.queryBusPlatform(map);
        List<String> docPros = agentBusInfoMapper.queryAgDocPro(map);
        List<String> busTypes = agentBusInfoMapper.queryBusType(map);
        par.put("platfromPerm",plats);
        par.put("orgIds",docPros);
        par.put("busTypes",busTypes);
        List<String> annoIds = annoPlatformRelaMapper.selectAnnoIds(par);
        par.put("annoIds",annoIds);
        if (annoIds.size()==0) return pageInfo;
        pageInfo.setRows(announceMentInfoMapper.selectAnnReader(par,page));
        pageInfo.setTotal(announceMentInfoMapper.selectCountAnnReader(par));
        return  pageInfo;
    }


    @Override
    public List<Attachment> queryAttByAnnoid(String id,String busType) {
        return attachmentMapper.accessoryQuery(id, busType);
    }

}
