package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.AttDataTypeStatic;
import com.ryx.credit.common.enumc.AttachmentRelType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.dao.agent.AnnoPlatformRelaMapper;
import com.ryx.credit.dao.agent.AnnounceMentInfoMapper;
import com.ryx.credit.dao.agent.AttachmentMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.pojo.admin.agent.AnnoPlatformRela;
import com.ryx.credit.pojo.admin.agent.AnnounceMentInfo;
import com.ryx.credit.pojo.admin.agent.Attachment;
import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.pojo.admin.vo.AnnounceMentInfoVo;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public ResultVO upStat(AnnounceMentInfo announceMentInfo) {
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

    @Override
    public PageInfo selectAnnViewsMaintain(Page page, Map map) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(announceMentInfoMapper.selectAnnMaintain(map,page));
        pageInfo.setTotal(announceMentInfoMapper.selectCountAnnMaintain(map));
        return pageInfo;
    }

}
