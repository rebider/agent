package com.ryx.credit.service.dict;

import com.ryx.credit.common.enumc.TabId;

/**
 * Created by cx on 2018/5/22.
 */
public interface IdService {



    public String genId(TabId tablename);

    public String genIdInTran(TabId tablename);

    public String genOrderId(TabId tablename,Integer userid);


    public String genAgId(TabId tablename);


    public String genOrganizationId(TabId tablename,Integer userid);


}
