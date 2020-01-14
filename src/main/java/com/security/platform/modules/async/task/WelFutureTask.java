package com.security.platform.modules.async.task;

import com.security.platform.common.utils.ThreadPoolUtil;
import com.security.platform.common.vo.SearchVo;
import com.security.platform.modules.async.dto.WelDataDto;
import com.security.platform.modules.log.service.LogService;
import com.security.platform.modules.monitor.service.CameraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.Callable;
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

    @SuppressWarnings("all")
    public WelDataDto getWelDataStatistics(final String userId, String departmentId, SearchVo searchVo){
        log.info("WelFutureTask" + Thread.currentThread());
        WelDataDto welDataDto = new WelDataDto();
        try {

            Future<Integer> loginTotal = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Integer logType = 0;
                    return logService.countDistinctByLogType(logType);
                }
            });

            Future<Long>  deviceTotal = executorService.submit(new Callable<Long>() {
                @Override
                public Long call() throws Exception {
                    return cameraService.count();
                }
            });

            Future<Long> deviceOnlineTotal = executorService.submit(new Callable<Long>() {
                @Override
                public Long call() throws Exception {
                    Long loginHandle = 0L;
                    return cameraService.countByLoginHandleNot(loginHandle);
                }
            });

            if (loginTotal.isDone() && !loginTotal.isCancelled()){
                welDataDto.setLoginTotal(loginTotal.get());
            }
            if (deviceTotal.isDone() && !deviceTotal.isCancelled()){
                welDataDto.setDeviceTotal(deviceTotal.get());
            }
            if (deviceOnlineTotal.isDone() && !deviceOnlineTotal.isCancelled()){
                welDataDto.setDeviceOnlineTotal(deviceOnlineTotal.get());
            }

            return welDataDto;
        }catch (Exception e){
            log.error(">>>>>>聚合查询用户聚合信息异常:" + e + "<<<<<<<<<");
        }


        return null;

    }
}
