package com.ryx.credit.cms.controller.agent;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.enumc.TransFlag;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.AColinfoPayment;
import com.ryx.credit.service.agent.AColinfoPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by RYX on 2018/9/17.
 */
@RequestMapping("colinfoPayment")
@Controller
public class AColinfoPaymentController extends BaseController{

    @Autowired
    private AColinfoPaymentService colinfoPaymentService;

    @RequestMapping(value = "toPaymentList")
    public String toPaymentList(HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("flagList", TransFlag.getItemMap());
        return "agent/colinfoPaymentList";
    }

    @RequestMapping(value = "paymentList")
    @ResponseBody
    public PageInfo paymentList(HttpServletRequest request, HttpServletResponse response, AColinfoPayment colinfoPayment){
        Page page = pageProcess(request);
        PageInfo pageInfo = colinfoPaymentService.olinfoPaymentList(colinfoPayment, page);
        List<AColinfoPayment> colinfoPaymentList = pageInfo.getRows();
        colinfoPaymentList.forEach(row->{
            row.setFlag(TransFlag.getContentByValue(row.getFlag()));
        });
        return pageInfo;
    }

}
