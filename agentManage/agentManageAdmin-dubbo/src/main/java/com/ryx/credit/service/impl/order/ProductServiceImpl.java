package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.order.OProductMapper;
import com.ryx.credit.pojo.admin.order.OProduct;
import com.ryx.credit.pojo.admin.order.OProductExample;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.ProductService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2018/7/13.
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private OProductMapper productMapper;
    @Autowired
    private IdService idService;


    @Override
    public PageInfo productList(OProduct product, Page page) {

        OProductExample example = new OProductExample();
        OProductExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(product.getProCode())) {
            criteria.andProCodeEqualTo(product.getProCode());
        }
        if (StringUtils.isNotBlank(product.getProName())) {
            criteria.andProNameEqualTo(product.getProName());
        }
        if (StringUtils.isNotBlank(product.getProType())) {
            String proType = product.getProType();
            if (proType.contains(",")) {
                List<String> proCodeList = new ArrayList<>();
                String[] split = proType.split(",");
                for (int i = 0; i < split.length; i++) {
                    proCodeList.add(split[i]);
                }
                criteria.andProTypeIn(proCodeList);
            } else {
                criteria.andProTypeEqualTo(proType);
            }
        }
        if (null != product.getProStatus()) {
            criteria.andProStatusEqualTo(product.getProStatus());
        }
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        example.setPage(page);
        List<OProduct> oProducts = productMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(oProducts);
        pageInfo.setTotal(productMapper.countByExample(example));
        return pageInfo;
    }

    @Override
    public PageInfo productGroupByList(OProduct product, Page page) {
        FastMap example = FastMap.fastFailMap();
        if (StringUtils.isNotBlank(product.getProCode())) {
            example.putKeyV("proCode",product.getProCode());
        }
        if (StringUtils.isNotBlank(product.getProName())) {
            example.putKeyV("proName","%"+product.getProName()+"%");
        }
        if (StringUtils.isNotBlank(product.getProType())) {
            String proTypeString = product.getProType();
            if(proTypeString.contains(",")) {
                String[] split = proTypeString.split(",");
                List<String> proCodeList = new ArrayList<>();
                for (int i = 0; i < split.length; i++) {
                    proCodeList.add(split[i]);
                }
                example.putKeyV("proTypes", proCodeList);
            }else {
                example.putKeyV("proType", product.getProType());
            }
        }

        example.putKeyV("page",page);
        List<Map> oProducts = productMapper.queryGroupByProCodeList(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(oProducts);
        pageInfo.setTotal(productMapper.queryGroupByProCodeListCount(example));
        return pageInfo;
    }

    @Override
    public AgentResult saveProduct(OProduct product) {
        AgentResult result = new AgentResult(500, "系统异常", "");
        if (StringUtils.isBlank(product.getProCode())) {
            logger.info("商品添加:{}", "商品添加信息编号为空");
            throw new ProcessException("商品编号为空");
        }
        //去查找商品编号是否已经存在
        OProductExample oProductExam = new OProductExample();
        OProductExample.Criteria criter = oProductExam.createCriteria();
        criter.andStatusEqualTo(Status.STATUS_1.status);
        criter.andProCodeEqualTo(product.getProCode());
        List<OProduct> products = productMapper.selectByExample(oProductExam);
        if (null != products && products.size() > 0) {
            logger.info("商品添加:{}", "商品添加信息编号已存在");
            return AgentResult.fail("商品编号已存在");
        }

        if (StringUtils.isBlank(product.getProName())) {
            logger.info("商品添加:{}", "商品添加信息名称为空");
            throw new ProcessException("商品名称为空");
        }
        if (StringUtils.isBlank(product.getProType())) {
            logger.info("商品添加:{}", "商品添加信息机具类型为空");
            throw new ProcessException("机具类型为空");
        }
        if (null == product.getProPrice()) {
            logger.info("商品添加:{}", "商品添加信息单价为空");
            throw new ProcessException("商品单价为空");
        }
        if (StringUtils.isNotBlank(product.getProName()) && StringUtils.isNotBlank(product.getProType()) && StringUtils.isNotBlank(product.getProCom()) && StringUtils.isNotBlank(product.getProModel())) {
            //进行判断商品名称,机具类型,厂商，型号是否一致
            OProductExample oProductExample = new OProductExample();
            OProductExample.Criteria criteria = oProductExample.createCriteria();
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            List<OProduct> oProducts = productMapper.selectByExample(oProductExample);
            if (null == oProducts && oProducts.size() < 0) {
                return null;
            }
            for (OProduct oProduct : oProducts) {
                if (product.getProName().equals(oProduct.getProName()) && product.getProType().equals(oProduct.getProType()) && product.getProCom().equals(oProduct.getProCom()) && product.getProModel().equals(oProduct.getProModel())) {
                    logger.info("商品添加:{}", "商品不可重复添加");
                    return AgentResult.fail("商品不可重复添加");
                }
            }
        }
        product.setId(idService.genId(TabId.o_product));
        Date nowDate = new Date();
        product.setcTime(nowDate);
        product.setuTime(nowDate);
        product.setStatus(Status.STATUS_1.status);
        product.setVersion(Status.STATUS_1.status);
        int insert = productMapper.insert(product);
        if (insert == 1) {
            return AgentResult.ok();
        }
        return result;
    }


    @Override
    public AgentResult updateProduct(OProduct product) {
        AgentResult result = new AgentResult(500, "参数错误", "");
        if (product == null) {
            return result;
        }
        if (StringUtils.isBlank(product.getId())) {
            return result;
        }
        if (StringUtils.isNotBlank(product.getProCode())) {
            List<OProduct> oProducts = selectById(product);
            for (OProduct oProduct : oProducts) {
                String proCode = oProduct.getProCode();
                if (product.getProCode().equals(proCode)) {
                    logger.info("商品修改:{}", "商品编号不可重复");
                    return AgentResult.fail("商品编号不可重复");
                }
            }
        }
        if (StringUtils.isNotBlank(product.getProName()) && StringUtils.isNotBlank(product.getProType()) && StringUtils.isNotBlank(product.getProCom()) && StringUtils.isNotBlank(product.getProModel())) {
            //进行判断商品名称,机具类型,厂商，型号是否一致
            List<OProduct> oProducts = selectById(product);
            for (OProduct oProduct : oProducts) {
                if (product.getProName().equals(oProduct.getProName()) && product.getProType().equals(oProduct.getProType()) && product.getProCom().equals(oProduct.getProCom()) && product.getProModel().equals(oProduct.getProModel())) {
                    logger.info("商品修改:{}", "商品不可修改重复的选项");
                    return AgentResult.fail("商品不可修改重复的选项");
                }
            }
        }
        product.setuTime(new Date());
        int update = productMapper.updateByPrimaryKeySelective(product);
        if (update == 1) {
            return AgentResult.ok();
        }
        return result;
    }

    @Override
    public OProduct findById(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public String findNameById(String id) {
        if (StringUtils.isBlank(id)) {
            return "";
        }
        OProduct oProduct = productMapper.selectByPrimaryKey(id);
        if (oProduct == null) {
            return "";
        }
        return oProduct.getProName();
    }

    @Override
    public AgentResult deleteById(String id) {
        AgentResult result = new AgentResult(500, "参数错误", "");
        if (StringUtils.isBlank(id)) {
            return result;
        }
        OProduct product = new OProduct();
        product.setId(id);
        product.setuTime(new Date());
        product.setStatus(Status.STATUS_0.status);
        int update = productMapper.updateByPrimaryKeySelective(product);
        if (update == 1) {
            return AgentResult.ok();
        }
        return result;
    }

    @Override
    public List<OProduct> allProductList(OProduct product) {
        OProductExample example = new OProductExample();
        OProductExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andProStatusEqualTo(Status.STATUS_1.status);
        List<OProduct> oProducts = productMapper.selectByExample(example);
        return oProducts;
    }

    private List<OProduct> selectById(OProduct product) {
        OProductExample oProductExample = new OProductExample();
        OProductExample.Criteria criteria = oProductExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andIdNotEqualTo(product.getId());
        List<OProduct> oProducts = productMapper.selectByExample(oProductExample);
        if (null == oProducts && oProducts.size() < 0) {
            return null;
        }
        return oProducts;
    }
}
