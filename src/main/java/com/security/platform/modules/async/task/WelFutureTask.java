package com.security.platform.modules.async.task;

import com.security.platform.common.constant.CommonConstant;
import com.security.platform.common.enums.LogType;
import com.security.platform.common.utils.ThreadPoolUtil;
import com.security.platform.common.vo.SearchVo;
import com.security.platform.modules.async.dto.WelDataDto;
import com.security.platform.modules.log.service.LogService;
import com.security.platform.modules.monitor.service.CameraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/14 10:40
 * @File WelFutureTask
 * @Software IntelliJ IDEA
 * @description todo
 */
@Slf4j
@Component
public class WelFutureTask {

    @Autowired
    private LogService logService;

    @Autowired
    private CameraService cameraService;

    private static ExecutorService executorService = ThreadPoolUtil.getPool();

    /**
     * 传统方式 取结果
     * @return
     */
    public WelDataDto findWelDataStatistics(){
        WelDataDto welDataDto = new WelDataDto();
        Integer  loginTotal = logService.countDistinctByLogType(0);
        Integer  interfaceUseTotal = logService.countDistinctByLogType(1);
        Long deviceTotal = cameraService.count();
        Long deviceOnlineTotal = cameraService.countByLoginHandleNot(0);
        welDataDto.setLoginTotal(loginTotal);
        welDataDto.setInterfaceUseTotal(interfaceUseTotal);
        welDataDto.setDeviceTotal(deviceTotal);
        welDataDto.setDeviceOnlineTotal(deviceOnlineTotal);
        return welDataDto;
    }

    /**
     * 多线程并发执行任务，取结果归集
     * @return
     */
    @SuppressWarnings("all")
    public WelDataDto getWelDataStatistics(){
        log.info("WelFutureTask" + Thread.currentThread());
        //登录总数 和 接口调用次数
        Integer loginTotal = 0,interfaceUseTotal =0;
        //设备总数 和 设备在线数
        long deviceTotal = 0,deviceOnlineTotal=0;

        try {

            Future<Integer> loginTotalF = executorService.submit(() -> logService.countDistinctByLogType(CommonConstant.LOG_TYPE_OPERAT));
            Future<Integer> interfaceUseTotalF = executorService.submit(() ->  logService.countDistinctByLogType(CommonConstant.LOG_TYPE_LOGIN));
            Future<Long>  deviceTotalF = executorService.submit(() -> cameraService.count());
            Future<Long> deviceOnlineTotalF = executorService.submit(() -> cameraService.countByLoginHandleNot(0L));

            //使用get阻塞
            loginTotal = loginTotalF.get();
            interfaceUseTotal = interfaceUseTotalF.get();
            deviceTotal = deviceTotalF.get();
            deviceOnlineTotal = deviceOnlineTotalF.get();

        }catch (Exception e){
            log.error(">>>>>>聚合查询用户聚合信息异常:" + e + "<<<<<<<<<");
        }

        WelDataDto welDataDto = WelDataDto.builder().loginTotal(loginTotal).interfaceUseTotal(interfaceUseTotal)
                .deviceTotal(deviceTotal).deviceOnlineTotal(deviceOnlineTotal).build();
        return welDataDto;

    }
}
