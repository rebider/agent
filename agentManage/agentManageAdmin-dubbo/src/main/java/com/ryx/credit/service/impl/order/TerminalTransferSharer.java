package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.TerminalPlatformType;
import com.ryx.credit.service.order.TerminalTransferFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @Author cl
 * @Date 2020/6/10 15:45
 * @Version 1.0
 * <p>
 * 分配实例
 */
@Component("terminalTransferSharer")
public class TerminalTransferSharer {
    @Resource(name="terminalTransferMPosService")
    TerminalTransferFactory terminalTransferMPosService;
    @Resource(name="terminalTransferPosService")
    TerminalTransferFactory terminalTransferPosService;
    @Resource(name="terminalTransferRJPosService")
    TerminalTransferFactory terminalTransferRJPosService;



   public TerminalTransferFactory sharer(String platformType) {
        if (platformType.equals(TerminalPlatformType.POS.getValue().toString()) || platformType.equals(TerminalPlatformType.ZHPOS.getValue().toString())) {
          return terminalTransferPosService;
        } else if (platformType.equals(TerminalPlatformType.MPOS.getValue().toString())) {
            return terminalTransferMPosService;
        } else if (platformType.equals(TerminalPlatformType.RJPOS.getValue().toString())) {
            return terminalTransferRJPosService;
        }
        return null;
    }


}
