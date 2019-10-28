package com.ryx.credit.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.Status;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.CBranchInnerMapper;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.pojo.admin.CBranchInnerExample;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.IBranchInnerConnectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author zhaoYd
 * @Date 2019/10/24 11:36
 * @Description 代理商账户和总管账户关联关系
 */
@Service("branchInnerConnectionService")
public class BranchInnerConnectionServiceImpl implements IBranchInnerConnectionService {

    private Logger logger = LoggerFactory.getLogger(BranchInnerConnectionServiceImpl.class);

    @Autowired
    private CBranchInnerMapper branchInnerMapper;
    @Autowired
    private CUserMapper userMapper;

    /**
     * 查询省区账号list
     * @return
     */
    @Override
    public List<Map<String, String>> queryBranchList() {
        List<UserVo> branchList = userMapper.selectListByName("省区");
        return null;
    }

    /**
     * 查询内管账号
     * @return
     */
    @Override
    public List<Map<String, String>> queryinnerList() {
        return null;
    }

    /**
     * 批量解除关联关系
     * @param ids
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Map<String, Object> removeBranchInnerConnectio(List<String> ids) {

        //查询关联关系表，查询内管账号
        List<String> inners = branchInnerMapper.selectInnerByIds(ids);
        //调用接口解除关联

        //将本地的表中数据删除

        return FastMap.fastMap("code", "").putKeyV("msg", "");
    }

    /**
     * 具体的关联操作
     * @param str
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Map<String, Object> branchConnectionInner(String str) throws Exception {

        JSONArray jsonArray = JSONArray.parseArray(str);

        //检验数据是否符合关联

        try {
            //调用内管账号进行关联

            //将关联成功的存储到数据库
            return FastMap.fastMap("code", "1").putKeyV("msg", "关联成功");
        }catch (Exception e){
            e.printStackTrace();
            return FastMap.fastMap("code", "2").putKeyV("msg", "关联失败，稍后再试");
        }
    }

    /**
     * 分页查询数据
     * @param param
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo queruAbleBranchInner(Map<String, Object> param, PageInfo pageInfo) throws Exception{
        //查询所有有效的账号
        CBranchInnerExample example = new CBranchInnerExample();
        CBranchInnerExample.Criteria criteria = example.or();
        if (null != param.get("branchName") && !"".equals(param.get("branchName")))
            criteria.andBranchNameLike((String) param.get("branchName"));
        if (null != param.get("inner") && !"".equals(param.get("inner")))
            criteria.andInnerEqualTo((String) param.get("inner"));

        //有开始时间，没有结束时间
        if (null != param.get("beginTime") && !"".equals(param.get("beginTime")) && (null == param.get("endTime") || "".equals(param.get("endTime"))))
            criteria.andCTimeGreaterThanOrEqualTo((Date) param.get("endTime"));
        //有结束时间，没有开始时间
        if (null != param.get("endTime") && !"".equals(param.get("endTime")) && (null == param.get("beginTime") || "".equals(param.get("beginTime"))))
            criteria.andCTimeLessThanOrEqualTo((Date) param.get("endTime"));
        //有结束时间和开始时间
        if (null != param.get("endTime") && !"".equals(param.get("endTime")) && null != param.get("beginTime") && !"".equals(param.get("beginTime"))){
            try {
                if (new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(param.get("endTime"))).compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(param.get("beginTime"))))<0)
                    throw new MessageException("开始时间不能小于结束时间");
            }catch (ParseException e){
                e.printStackTrace();
                throw e;
            }
            criteria.andCTimeBetween(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(param.get("beginTime"))), new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(param.get("endTime"))));
        }
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        example.setOrderByClause(" C_TIME desc,BRANCH_NAME asc");

        List<Map<String, Object>> listAll = branchInnerMapper.selectByExample(example);

        //返回分页
        logger.info("内管关联关系表:{}", JSONObject.toJSONString(listAll));
        pageInfo.setRows(listAll);
        pageInfo.setTotal((int) branchInnerMapper.countByExample(example));
        return pageInfo;
    }
}
