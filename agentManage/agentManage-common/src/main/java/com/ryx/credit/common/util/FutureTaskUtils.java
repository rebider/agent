package com.ryx.credit.common.util;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

public class FutureTaskUtils {

    public static Map<String, String> getTaskResult(List<FutureTask<Map<String, String>>> taskList, List<String> taskNameList, Logger logger) {
        Map<String, String> result = new HashMap<>();
        Boolean success = Boolean.TRUE;
        for (int i = 0; i < taskList.size(); i++) {
            FutureTask<Map<String, String>> thread = taskList.get(i);

            try {
                Map<String, String> sheetMap = thread.get();
                if (sheetMap.get("result").equals("success")) {
                    continue;
                } else if(sheetMap.get("result").equals("fail")){
                    result.put("result","fail");
                    result.put("Err",sheetMap.get("Err"));
                    return sheetMap;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            result.put("result","success");
		}
		return result;
	}

}
