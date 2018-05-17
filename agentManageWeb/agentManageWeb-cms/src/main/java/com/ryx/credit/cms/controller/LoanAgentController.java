package com.ryx.credit.cms.controller;/*package com.ryx.credit.cms.controller;
//@UPDATE

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.base.BaseController;
import com.ryx.credit.pojo.dictionary.PayTypeName;
import com.ryx.credit.pojo.entity.CTransFlow;
import com.ryx.credit.service.cms.IAgentService;

@Controller
@RequestMapping("/agent")
public class LoanAgentController extends BaseController{
	
	@Resource
	private IAgentService iAgentService;

	*//**
	 * 代收代付管理
	 * @return
	 *//*
	@GetMapping("/manager")
	public String manager() {
		return "admin/agent";
	}
	

	*//**
	 * 
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return 分页查询数据
	 *//*
	@PostMapping("/find")
	@ResponseBody
	public Object dataGrid(Integer page, Integer rows, String sort, String order, String transDate, String custName, String flowId, String contractId, String loanId, String payType) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		CTransFlow cTransflow = new CTransFlow(flowId, loanId, contractId, custName, PayTypeName.getEnum(StringUtils.isBlank(payType) ? null : payType.trim()));
		PageInfo pages = iAgentService.findTranFlow(pageInfo, cTransflow, transDate);
		return pages;
	}

	*//**
	 * 贷款重提
	 * @param flowId
	 * @return
	 *//*
	@PostMapping("/loanAgain")
	@ResponseBody
	public Object startJob(String flowId){
		iAgentService.loanAginFlow(flowId);
		return renderSuccess("贷款重提成功！");
	}
	
}
*/