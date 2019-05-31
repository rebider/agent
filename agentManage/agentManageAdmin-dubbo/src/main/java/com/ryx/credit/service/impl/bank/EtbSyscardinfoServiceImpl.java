package com.ryx.credit.service.impl.bank;

import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.bank.EtbSysCardBinNoMapper;
import com.ryx.credit.pojo.admin.bank.EtbSysCardBinNo;
import com.ryx.credit.service.bank.EtbSyscardinfoService;
import com.ryx.credit.util.ProcessorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RYX on 2018/7/17.
 */
@Service("etbSyscardinfoService")
public class EtbSyscardinfoServiceImpl implements EtbSyscardinfoService {
    private static final Logger LOG = LoggerFactory.getLogger(EtbSyscardinfoServiceImpl.class);
    @Autowired
    private EtbSysCardBinNoMapper etbSysCardBinNoMapper;

    @Override
    public EtbSysCardBinNo findCardBinByCardNo(String cardNo) throws ProcessorException {
        LOG.debug("BaseCardServiceImpl findByCardNo cardNo: " + cardNo);
        EtbSysCardBinNo etbSyscardbinno = null;
        if(StringUtils.isBlank(cardNo)){
            throw new ProcessorException("卡号不能为空");
        }
//        String cardBinStr = cardNo.substring(0, 10);
        String cardBinStr = cardNo;
        List<EtbSysCardBinNo> result = new ArrayList<EtbSysCardBinNo>();
        for (int i = cardBinStr.length(); i > 1; i--) {
            String cardBinTmp = cardBinStr.substring(0, i);
            result = etbSysCardBinNoMapper.getCardBinByCardNo(cardBinTmp);
            if (result.size() != 0) {
                break;
            }
        }
        if (result.size() == 0) {
            throw new ProcessorException("验证账户状态找不到卡bin");
        } else {
            for (int j = 0; j < result.size(); j++) {
                if (String.valueOf(cardNo.length()).equals(result.get(j).getCardLen().toString())) {
                    etbSyscardbinno = result.get(j);
                    break;
                }
            }
            if (etbSyscardbinno == null) {
                throw new ProcessorException("验证账户状态找不到卡bin");
            }
        }
        return etbSyscardbinno;
    }

}
