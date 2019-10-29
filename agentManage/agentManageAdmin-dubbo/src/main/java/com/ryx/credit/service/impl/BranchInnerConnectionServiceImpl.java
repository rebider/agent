package com.ryx.credit.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.agentUtil.AESUtil;
import com.ryx.credit.common.util.agentUtil.RSAUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.CBranchInnerMapper;
import com.ryx.credit.dao.COrganizationMapper;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.machine.service.LmsUserService;
import com.ryx.credit.pojo.admin.CBranchInner;
import com.ryx.credit.pojo.admin.CBranchInnerExample;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.IBranchInnerConnectionService;
import com.ryx.credit.util.Constants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private LmsUserService lmsUserService;
    @Autowired
    private COrganizationMapper organizationMapper;
    @Autowired
    private IBranchInnerConnectionService branchInnerConnectionService;

    /**
     * 查询省区账号list
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> queryBranchList() {
        return organizationMapper.selectBranchList();
    }

    /**
     * 解除关联关系
     *
     * @param id
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Map<String, Object> removeBranchInnerConnection(String id) {
        int t = 0;
        //先查询，查询账号是否存在状态为2的，
        if (branchInnerMapper.countByIdforUpdate(id) == 1) {
            //存在-删除(此数据)
            t = branchInnerMapper.deleteInnerByIds(id);
        } else {
            //不存在-将状态更新成2
            t = branchInnerMapper.updateByPrimaryId(id);
        }

        if (t == 1) {
            return FastMap.fastMap("code", "1").putKeyV("msg", "删除成功");
        } else {
            return FastMap.fastMap("code", "2").putKeyV("msg", "删除失败");
        }
    }

    /**
     * 关联账号操作
     *
     * @param str
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Map<String, Object> branchConnectionInner(String str, Long userId) throws Exception {
        List<Map<String, String>> list = (List<Map<String, String>>) JSONArray.parse(str);
        for (Map<String, String> map : list) {
            //循环插入
            CBranchInner cBranchInner = new CBranchInner();
            cBranchInner.setcTime(Calendar.getInstance().getTime());
            cBranchInner.setBranchLogin(map.get("branchId"));
            COrganization branch = organizationMapper.selectByPrimaryKey(Integer.valueOf(map.get("branchId")));
            cBranchInner.setBranchName(branch.getName());
            cBranchInner.setId(StringUtils.getUUId());
            cBranchInner.setStatus(Status.STATUS_1.status);
            cBranchInner.setcUserId(userId + "");
            cBranchInner.setcUserName(userMapper.selectUserVoById(userId).getName());
            cBranchInner.setInnerLogin(map.get("innerLogin"));
            cBranchInner.setInnerName(lmsUserService.queryByLogin(map.get("innerLogin")).getName());
            branchInnerMapper.insert(cBranchInner);
        }
        return FastMap.fastMap("code", "1").putKeyV("msg", "关联成功");
    }

    /**
     * 新建立内管账号
     *
     * @param userVo
     * @return
     */
    @Override
    public Map<String, Object> buildInnerAccout(UserVo userVo, Map<String, String> param) throws Exception {
        try {
            String cooperator = Constants.cooperator;
            String tranCode = "ORG019"; // 交易码
            String charset = "UTF-8"; // 字符集
            String reqMsgId = UUID.randomUUID().toString().replace("-", ""); // 请求流水
            String reqDate = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"); // 请求时间

            JSONObject jsonParams = new JSONObject();
            jsonParams.put("msgType", "01");
            jsonParams.put("reqDate", reqDate);
            jsonParams.put("version", "1.0.0");
            jsonParams.put("data", FastMap.fastMap("loginname", userVo.getLoginName()).
                    putKeyV("name", userVo.getName()).
                    putKeyV("smsAuthSwitch", 0).
                    putKeyV("phone", userVo.getPhone()).
                    //putKeyV("referLoginname","").//省总权限参考登录账号,先不传
                            putKeyV("password", null != param.get("innerPwd") ? param.get("innerPwd") : ""));
            String plainXML = jsonParams.toString();
            // 请求报文加密开始
            String keyStr = AESUtil.getAESKey();
            byte[] plainBytes = plainXML.getBytes(charset);
            byte[] keyBytes = keyStr.getBytes(charset);
            String encryptData = new String(Base64.encodeBase64((AESUtil.encrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
            String signData = new String(Base64.encodeBase64(RSAUtil.digitalSign(plainBytes, Constants.privateKey, "SHA1WithRSA")), charset);
            String encrtptKey = new String(org.apache.commons.codec.binary.Base64.encodeBase64(RSAUtil.encrypt(keyBytes, Constants.publicKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);

            // 请求报文加密结束
            Map<String, String> map = new HashMap<>();
            map.put("encryptData", encryptData);
            map.put("encryptKey", encrtptKey);
            map.put("cooperator", cooperator);
            map.put("signData", signData);
            map.put("tranCode", tranCode);
            map.put("reqMsgId", reqMsgId);
            logger.info("内管账号建立请求加密参数:{}", map);
            String httpResult = HttpClientUtil.doPost(AppConfig.getProperty("agent_pos_notify_url"), map);
            logger.info("内管账号建立返回加密参数:{}", map);
            JSONObject jsonObject = JSONObject.parseObject(httpResult);
            if (!jsonObject.containsKey("encryptData") || !jsonObject.containsKey("encryptKey")) {
                logger.info("内管账号建立校验失败:{}", httpResult);
                throw new Exception("内管账号建立校验失败");
            } else {
                String resEncryptData = jsonObject.getString("encryptData");
                String resEncryptKey = jsonObject.getString("encryptKey");
                byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
                byte[] merchantAESKeyBytes = RSAUtil.decrypt(decodeBase64KeyBytes, Constants.privateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
                byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
                byte[] merchantXmlDataBytes = AESUtil.decrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
                String respXML = new String(merchantXmlDataBytes, charset);
                logger.info("内管账号建立返回解密参数:{}", respXML);

                //报文验签
                String resSignData = jsonObject.getString("signData");
                byte[] signBytes = Base64.decodeBase64(resSignData);
                if (!RSAUtil.verifyDigitalSign(respXML.getBytes(charset), signBytes, Constants.publicKey, "SHA1WithRSA")) {
                    logger.info("签名验证失败");
                    throw new MessageException("签名验证失败");
                } else {
                    logger.info("签名验证成功");
                    JSONObject respXMLObj = JSONObject.parseObject(respXML);
                    JSONObject retObj = JSONObject.parseObject(respXMLObj.getString("data"));
                    if (null != retObj.get("result_code") && "0000".equals(retObj.get("result_code"))) {
                        return FastMap.fastMap("code", "1").putKeyV("msg", "添加成功");
                    } else if (null != retObj.get("result_msg")) {
                        return FastMap.fastMap("code", "2").putKeyV("msg", retObj.get("result_msg"));
                    }else {
                        return FastMap.fastMap("code", "2").putKeyV("msg", "内管平台建立返回值异常！");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 分页查询数据
     *
     * @param param
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo queruAbleBranchInner(Map<String, Object> param, PageInfo pageInfo) throws Exception {
        //查询所有有效的账号
        CBranchInnerExample example = new CBranchInnerExample();
        CBranchInnerExample.Criteria criteria = example.or();
        if (null != param.get("branchId") && !"".equals(param.get("branchId")))
            criteria.andBranchLoginEqualTo(((String) param.get("branchId")));
        if (null != param.get("innerLogin") && !"".equals(param.get("innerLogin")))
            criteria.andInnerLoginEqualTo((String) param.get("innerLogin"));

        //有开始时间，没有结束时间
        if (null != param.get("beginTime") && !"".equals(param.get("beginTime")) && (null == param.get("endTime") || "".equals(param.get("endTime"))))
            criteria.andCTimeGreaterThanOrEqualTo((Date) param.get("endTime"));
        //有结束时间，没有开始时间
        if (null != param.get("endTime") && !"".equals(param.get("endTime")) && (null == param.get("beginTime") || "".equals(param.get("beginTime"))))
            criteria.andCTimeLessThanOrEqualTo((Date) param.get("endTime"));
        //有结束时间和开始时间
        if (null != param.get("endTime") && !"".equals(param.get("endTime")) && null != param.get("beginTime") && !"".equals(param.get("beginTime"))) {
            try {
                if (new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(param.get("endTime"))).compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(param.get("beginTime")))) < 0)
                    throw new MessageException("开始时间不能小于结束时间");
            } catch (ParseException e) {
                e.printStackTrace();
                throw e;
            }
            Calendar c = Calendar.getInstance();
            c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(param.get("endTime"))));
            c.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andCTimeBetween(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(param.get("beginTime"))), new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(c.getTime())));
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

    /**
     * 查询内管账号
     *
     * @return
     */
    @Override
    public List<Map<String, String>> queryinnerList() {
        List<Map<String, String>> innerList = lmsUserService.queryAllLmsUser();
        logger.info("查询的内管账号:{}", innerList);
        return innerList;
    }


    @Override
    public List<String> addList(List<List<Object>> data, String userId) throws Exception {
        List<String> listRes = new ArrayList<>();
        for (List<Object> objectList : data) {
            try {
                AgentResult agentResult =  branchInnerConnectionService.addListItem(objectList,userId);
                if(agentResult.isOK()) {
                    logger.info("导入物流{}成功", objectList.toString());
                    listRes.add("物流[" + objectList.toString() + "]导入成功");
                }else{
                    listRes.add("物流[" + objectList.toString() + "]导入失败:"+agentResult.getData());
                }
            }catch (MessageException e) {
                e.printStackTrace();
                logger.info("导入物流{}抛出异常",objectList.toString());
                listRes.add("物流["+objectList.toString()+"]导入失败:"+e.getMsg());
            }catch (Exception e) {
                e.printStackTrace();
                logger.info("导入物流{}抛出异常",objectList.toString());
                listRes.add("物流["+objectList.toString()+"]导入失败:"+e.getMessage());
            }
        }
        logger.info("user{}导省区关联信息的数据有{}",userId,listRes.toString());
        return listRes;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult addListItem(List<Object> branchInnerData, String userId) throws Exception {

        String branchName = "";
        String innerLogin = "";

        List col = Arrays.asList(InnerExportColum.InnerExportColum.col);
        branchName = String.valueOf(branchInnerData.get(col.indexOf("BRANCH_NAME"))).trim();
        innerLogin = String.valueOf(branchInnerData.get(col.indexOf("INNER_LOGIN"))).trim();

        if ("".equals(innerLogin) || "".equals(branchName)) {
            int i = 0, j = 0;
            String branchLogin = null, innerName = null;
            //进行姓名和总管进行校验
            List<Map<String, Object>> listBranch = organizationMapper.selectBranchList();
            for (Map<String, Object> branch : listBranch) {
                if (branchName.equals(branch.get("NAME"))) {
                    i++;
                    branchLogin = branch.get("ID").toString();
                }
            }
            List<Map<String, String>> innerList = lmsUserService.queryAllLmsUser();
            for (Map<String, String> branch : innerList) {
                if (innerLogin.equals(branch.get("LOGINNAME"))) {
                    j++;
                    innerName = branch.get("NAME") + "";
                }
            }
            if (i != 1) throw new MessageException("[" + branchName + "]不是省区部门。");
            if (j != 1) throw new MessageException("[" + innerLogin + "]不是有效的内管账号。");

            //插入表中账号
            CBranchInner cBranchInner = new CBranchInner();
            cBranchInner.setcTime(Calendar.getInstance().getTime());
            cBranchInner.setBranchLogin(branchLogin);
            cBranchInner.setBranchName(branchName);
            cBranchInner.setId(StringUtils.getUUId());
            cBranchInner.setStatus(Status.STATUS_1.status);
            cBranchInner.setcUserId(userId + "");
            cBranchInner.setcUserName(userMapper.selectUserVoById(Long.parseLong(userId)).getName());
            cBranchInner.setInnerLogin(innerLogin);
            cBranchInner.setInnerName(innerName);
            branchInnerMapper.insert(cBranchInner);

            logger.info("新建用户:{}", cBranchInner);
            return AgentResult.ok();
        }
        return AgentResult.fail("表格中存在空值，请完善表格！");
    }
}
