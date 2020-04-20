package com.ryx.credit.common.util;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

public class FutureTaskUtils {

    public static Map<String, Object> getTaskResult(List<FutureTask<Map<String, Object>>> taskList, List<String> taskNameList, Logger logger) {
        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < taskList.size(); i++) {
            logger.info(taskNameList.get(i) + "开始执行");
            FutureTask<Map<String, Object>> thread = taskList.get(i);
            try {
                Map<String, Object> sheetMap = thread.get();
                if (sheetMap.get("result").equals("success")) {
                    continue;
                } else if (sheetMap.get("result").equals("fail")) {
                    result.put("result", "fail");
                    result.put("Err", sheetMap.get("Err"));
                    return result;
                } else if (sheetMap.get("result").equals("dispose")) {
                    result.put("result", "dispose");
                    result.put(taskNameList.get(i), sheetMap.get("disposeResult"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
