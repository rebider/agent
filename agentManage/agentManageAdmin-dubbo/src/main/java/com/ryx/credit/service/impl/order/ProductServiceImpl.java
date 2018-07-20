package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.order.OProductMapper;
import com.ryx.credit.pojo.admin.order.OProduct;
import com.ryx.credit.pojo.admin.order.OProductExample;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by RYX on 2018/7/13.
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private OProductMapper productMapper;
    @Autowired
    private IdService idService;


    @Override
    public PageInfo productList(OProduct product, Page page){

        OProductExample example = new OProductExample();
        OProductExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(product.getProCode())){
            criteria.andProCodeEqualTo(product.getProCode());
        }
        if(StringUtils.isNotBlank(product.getProName())){
            criteria.andProNameEqualTo(product.getProName());
        }
        if(StringUtils.isNotBlank(product.getProType())){
            String proType = product.getProType();
            if(proType.contains(",")){
                List<String> proCodeList = new ArrayList<>();
                String[] split = proType.split(",");
                for(int i = 0 ; i < split.length ; i++){
                    proCodeList.add(split[i]);
                }
                criteria.andProTypeIn(proCodeList);
            }else{
                criteria.andProTypeEqualTo(proType);
            }
        }
        if(null!=product.getProStatus()){
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
    public AgentResult saveProduct(OProduct product){
        AgentResult result = new AgentResult(500, "系统异常", "");
        product.setId(idService.genId(TabId.o_product));
        Date nowDate = new Date();
        product.setcTime(nowDate);
        product.setuTime(nowDate);
        product.setStatus(Status.STATUS_1.status);
        product.setVersion(Status.STATUS_1.status);
        int insert = productMapper.insert(product);
        if(insert==1){
            return AgentResult.ok();
        }
        return result;
    }


    @Override
    public AgentResult updateProduct(OProduct product){
        AgentResult result = new AgentResult(500, "参数错误", "");
        if(product==null){
            return result;
        }
        if(StringUtils.isBlank(product.getId())){
            return result;
        }
        product.setuTime(new Date());
        int update = productMapper.updateByPrimaryKeySelective(product);
        if(update==1){
            return AgentResult.ok();
        }
        return result;
    }

    @Override
    public OProduct findById(String id){
        if(StringUtils.isBlank(id)){
            return null;
        }
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public String findNameById(String id){
        if(StringUtils.isBlank(id)){
            return "";
        }
        OProduct oProduct = productMapper.selectByPrimaryKey(id);
        if(oProduct==null){
            return "";
        }
        return oProduct.getProName();
    }

    @Override
    public AgentResult deleteById(String id){
        AgentResult result = new AgentResult(500, "参数错误", "");
        if(StringUtils.isBlank(id)){
            return result;
        }
        OProduct product = new OProduct();
        product.setId(id);
        product.setuTime(new Date());
        product.setStatus(Status.STATUS_0.status);
        int update = productMapper.updateByPrimaryKeySelective(product);
        if(update==1){
            return AgentResult.ok();
        }
        return result;
    }

    @Override
    public List<OProduct> allProductList(OProduct product){
        OProductExample example = new OProductExample();
        OProductExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<OProduct> oProducts = productMapper.selectByExample(example);
        return oProducts;
    }

}
