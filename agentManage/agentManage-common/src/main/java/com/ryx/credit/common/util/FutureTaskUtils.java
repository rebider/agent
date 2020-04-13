package com.ryx.credit.common.util;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

public class FutureTaskUtils {

    public static Map<String, Object> getTaskResult(List<FutureTask<Map<String, Object>>> taskList, List<String> taskNameList, Logger logger) {
        logger.info(taskNameList + "开始执行");
        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < taskList.size(); i++) {
            FutureTask<Map<String, Object>> thread = taskList.get(i);
            try {
                Map<String, Object> sheetMap = thread.get();
                if (sheetMap.get("result").equals("success")) {
                    result.put("result", "success");
                    continue;
                } else if (sheetMap.get("result").equals("fail")) {
                    result.put("result", "fail");
                    result.put("Err", sheetMap.get("Err"));
                    return result;
                } else if (sheetMap.get("result").equals("dispose")) {
                    for (int j = 0; j < taskList.size(); j++) {
                        FutureTask<Map<String, Object>> FutureTaskthread = taskList.get(j);
                        Map<String, Object> FutureTaskSheetMap = FutureTaskthread.get();
                        if (FutureTaskSheetMap.get("result").equals("dispose")) {
                            List<Map<String, Object>> resultList = (List<Map<String, Object>>) FutureTaskSheetMap.get("disposeResult");
                            if (resultList != null && resultList.size() != 0) {
                                if (result != null && result.size() != 0) {
                                    resultList.addAll((List<Map<String, Object>>) result.get("disposeResult"));
                                }
                            }
                            result.put("result", "dispose");
                            result.put("disposeResult"+taskNameList.get(0), resultList);
                        } else if (FutureTaskSheetMap.get("result").equals("fail")) {
                            result.put("result", "fail");
                            result.put("Err", sheetMap.get("Err"));
                            return result;
                        }
                    }
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
