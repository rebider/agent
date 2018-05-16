package com.ryx.credit.common.result;

import java.io.Serializable;

/**
 * 跑批量请求报文
 * @author ryx
 *
 */
public class RequestInfo implements Serializable{
	private String jobName;
	
	private String jobTime;
	
	private String jobId;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobTime() {
		return jobTime;
	}

	public void setJobTime(String jobTime) {
		this.jobTime = jobTime;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
}
