package com.ryx.credit.service.order;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.OInternetCard;

import java.util.List;

/**
 * Created by RYX on 2018/12/4.
 */
public interface InternetCardService {

    PageInfo internetCardList(OInternetCard internetCard, Page page);

    List<OInternetCard> exportInternetCard(OInternetCard internetCard);

    void importInternetCard(List<List<Object>> excelList, String importType)throws Exception;
}
