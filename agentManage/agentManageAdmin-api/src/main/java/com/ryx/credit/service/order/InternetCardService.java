package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.OInternetCard;
import com.ryx.credit.pojo.admin.order.OInternetCardImport;

import java.util.List;

/**
 * Created by RYX on 2018/12/4.
 */
public interface InternetCardService {

    PageInfo internetCardList(OInternetCard internetCard, Page page);

    PageInfo internetCardImportList(OInternetCardImport internetCardImport, Page page);

    void importInternetCard(String fileUrl, String importType, String userId,String batchNo)throws Exception;

    void disposeSn(List<String> snList,OInternetCard internetCard,OInternetCardImport oInternetCardImport)throws MessageException;

    List<OInternetCardImport>  exportErrorExcel(OInternetCardImport internetCardImport);

    void taskDisposeInternetCard();

    void taskUpdateMech();
}
