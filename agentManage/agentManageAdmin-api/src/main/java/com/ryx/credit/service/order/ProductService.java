package com.ryx.credit.service.order;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.OProduct;

import java.util.List;

/**
 * Created by RYX on 2018/7/13.
 * 商品管理
 */
public interface ProductService {

    PageInfo productList(OProduct product, Page page);

    PageInfo productGroupByList(OProduct product, Page page);

    AgentResult saveProduct(OProduct product);

    AgentResult updateProduct(OProduct product);

    OProduct findById(String id);

    AgentResult deleteById(String id);

    List<OProduct> allProductList(OProduct product);

    String findNameById(String id);

    List<OProduct> findListByProCode(String proCode);
}
