package com.ryx.credit.profit.service.impl;/**
 * @Auther: zhaodw
 * @Date: 2018/7/31 20:27
 * @Description:
 */

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ImportDeductionDetailMapper;
import com.ryx.credit.profit.pojo.ImportDeductionDetail;
import com.ryx.credit.profit.pojo.ImportDeductionDetailExample;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitDeductionExample;
import com.ryx.credit.profit.service.ImportDeductionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 扣款明细导入实现
 * @author zhaodw
 * @create 2018/7/31
 * @since 1.0.0
 */
@Service("importDeductionDetailServiceImpl")
public class ImportDeductionDetailServiceImpl implements ImportDeductionDetailService{

    @Autowired
    private ImportDeductionDetailMapper importDeductionDetailMapper;

    @Override
    public PageInfo getImportDeductionDetailList(ImportDeductionDetail importDeductionDetail, Page page) {
        ImportDeductionDetailExample example = new ImportDeductionDetailExample();
        example.setPage(page);
        ImportDeductionDetailExample.Criteria criteria = example.createCriteria();
        // 月份按开始到结束查询

        if (StringUtils.isNotBlank(importDeductionDetail.getSourceId())){
            criteria.andSourceIdEqualTo(importDeductionDetail.getSourceId());
        }
        List<ImportDeductionDetail> importDeductionDetails = importDeductionDetailMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(importDeductionDetails);
        pageInfo.setTotal(importDeductionDetailMapper.countByExample(example));
        return pageInfo;
    }

    @Override
    public void insetImportDeductionDetail(ImportDeductionDetail importDeductionDetail) {
        importDeductionDetailMapper.insert(importDeductionDetail);
    }
}
