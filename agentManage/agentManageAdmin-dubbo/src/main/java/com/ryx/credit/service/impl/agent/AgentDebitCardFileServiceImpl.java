package com.ryx.credit.service.impl.agent;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.ryx.credit.common.enumc.InternetRenewStatus;
import com.ryx.credit.common.enumc.InternetRenewWay;
import com.ryx.credit.common.util.*;
import com.ryx.credit.dao.agent.AgentDebitCardMapper;
import com.ryx.credit.service.agent.AgentDebitCardFileService;
import com.ryx.internet.pojo.OInternetRenewDetail;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service("agentDebitCardFileService")
public class AgentDebitCardFileServiceImpl implements AgentDebitCardFileService {
    private static Logger logger = LoggerFactory.getLogger(AgentDebitCardFileServiceImpl.class);

    private final String LOCAL_PATH = "debitcard.export.localpath";
    private final String REMOTE_PATH = "debitcard.export.remotepath";
    private final String REMOTE_IP = "debitcard.export.remoteIp";
    private final String REMOTE_PORT = "debitcard.export.remotePort";
    private final String REMOTE_USER = "debitcard.export.username";
    private final String REMOTE_PASS = "debitcard.export.passwd";
    private final String FILE_TYPE = ".xlsx";

    @Autowired
    private AgentDebitCardMapper agentDebitCardMapper;

    /**
     * 智慧POS/PLUS的一级代理商的结算卡信息，放置对账文件的FTP
     * @return
     */
    @Override
    public FastMap exportsForZHposOrPlus() throws Exception {
        logger.info("智慧POS结算卡信息导出开始");
        List<Map<String,String>> exportListMap = agentDebitCardMapper.exportsForZHposOrPlus();

        // 标题
        String[] titles = {"品牌名", "入网日期", "AG编码", "业务编号(O码)", "代理商名称", "S/P码", "级别",
                "归属省区", "负责人", "结算账户性质", "结算税点", "结算户名", "结算账号",
                "结算卡联行号", "结算支行名", "结算人身份证号", "日期"};
        List<List<String>> exportList = new ArrayList<List<String>>();
        exportListMap.stream().forEach( ite -> {
            List<String> ind = new ArrayList<String>();
            ind.add(ite.get("品牌名"));
            ind.add(ite.get("入网日期"));
            ind.add(ite.get("AG编码"));
            ind.add(ite.get("业务编号(O码)"));
            ind.add(ite.get("代理商名称"));
            ind.add(ite.get("SP码"));
            ind.add(ite.get("级别"));
            ind.add(ite.get("归属省区"));
            ind.add(ite.get("负责人"));
            ind.add(ite.get("结算账户性质"));
            ind.add(ite.get("结算税点"));
            ind.add(ite.get("结算户名"));
            ind.add(ite.get("结算账号"));
            ind.add(ite.get("结算卡联行号"));
            ind.add(ite.get("结算支行名"));
            ind.add(ite.get("结算人身份证号"));
            ind.add(ite.get("日期"));
            exportList.add(ind);
        });
        String filepath = AppConfig.getProperty(LOCAL_PATH);
        // 导出EXCEL文件名称
        String filaName = "DebitCard" + DateUtil.getFormatDate(new Date())+FILE_TYPE;
        String fileName = filepath + filaName;
        File file= new File(fileName);
        if(!file.exists()){
            file.createNewFile();
        }else{
            file.delete();
            file.createNewFile();
        }
        ExcelUtil.exportExcel(file.getAbsolutePath(), exportList, Arrays.asList(titles));
        logger.info("智慧POS结算卡信息文件生成" + file.getAbsolutePath());
        String ip = AppConfig.getProperty(REMOTE_IP);
        int port = Integer.parseInt(AppConfig.getProperty(REMOTE_PORT));
        String username = AppConfig.getProperty(REMOTE_USER);
        String passwd = AppConfig.getProperty(REMOTE_PASS);
        String remote = AppConfig.getProperty(REMOTE_PATH);
        SFTPUtil sftp = new SFTPUtil(ip, port, username , passwd);
        sftp.uploadFileName(remote, filaName, filepath, filaName);
        sftp.disconnect();
        logger.info("智慧POS结算卡信息导出结束");
        return null;
    }

    /**
     * 导出Excel到本地指定路径
     *
     * @param totalRowCount           总记录数
     * @param titles                  标题
     * @param exportPath              导出路径
     * @throws Exception
     */
    public static final void exportExcelToLocalPath(Integer totalRowCount, String[] titles, String exportPath, Integer perSheetRowCount,Integer perWriteRowCount) throws Exception {

//        Integer perSheetWriteCount = perSheetRowCount / perWriteRowCount;
//        // 初始化EXCEL
//        SXSSFWorkbook wb = PoiUtil2.initExcel(totalRowCount, titles, perSheetRowCount);
//        // 调用委托类分批写数据
//        int sheetCount = wb.getNumberOfSheets();
//        for (int i = 0; i < sheetCount; i++) {
//            SXSSFSheet eachSheet = wb.getSheetAt(i);
//            for (int j = 1; j <= perSheetWriteCount; j++) {
//                int currentPage = i * perSheetWriteCount + j;
//                int pageSize = perWriteRowCount;
//                int startRowCount = (j - 1) * perWriteRowCount + 1;
//                int endRowCount = startRowCount + pageSize - 1;
//                eachSheet
//            }
//        }
    }
}
