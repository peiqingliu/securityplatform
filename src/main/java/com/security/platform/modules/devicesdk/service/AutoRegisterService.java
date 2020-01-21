package com.security.platform.modules.devicesdk.service;

import com.security.platform.common.utils.AliyunOSSUtil;
import com.security.platform.common.utils.RedisUtil;
import com.security.platform.common.utils.ThreadPoolUtil;
import com.security.platform.modules.api.dto.ApiDataDto;
import com.security.platform.modules.monitor.entity.Camera;
import com.security.platform.modules.monitor.service.CameraService;
import com.security.platform.netsdk.common.SavePath;
import com.security.platform.netsdk.module.AutoRegisterModule;
import com.security.platform.netsdk.module.LoginModule;
import com.security.platform.netsdk.lib.NetSDKLib;
import com.security.platform.netsdk.lib.ToolKits;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.security.platform.common.constant.RedisKeyConstant.pictureUrlKey;

/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/13 9:06
 * @File AutoRegisterService
 * @Software IntelliJ IDEA
 * @description 自动注册
 */
@Slf4j
@Service
public class AutoRegisterService {

    @Value("${us.ip}")
    private String ip;

    @Value("${us.port}")
    private String port;

    @Autowired
    private CameraService cameraService;

    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private volatile static  String  deviceId = "";

    // 设备断线通知回调
    private DisConnect disConnectCallback = new DisConnect();

    // 主动注册监听回调
    private ServiceCB servicCallback = new ServiceCB();

    // 抓图回调
    public CaptureReceiveCB  captureCallback = new CaptureReceiveCB();

    // 预览句柄
    private NetSDKLib.LLong realplayHandle = new NetSDKLib.LLong(0);


    public void init(){
        // 打开工程，初始化，设置断线回调
        LoginModule.init(disConnectCallback, null);

        // 设置抓图回调
        AutoRegisterModule.setSnapRevCallBack(captureCallback);
    }

    //开启服务
    public boolean startServer(){
        String ip = getHostAddress();
        log.info("ip${ip}=" + ip + "port${port}=" + port);
        return AutoRegisterModule.startServer(ip,Integer.parseInt(port),servicCallback);
    }

    public boolean stopServer(){
        return AutoRegisterModule.stopServer();
    }


    /**
     * 根据组别截图
     * @param groupId 组别Id
     * @return
     */
    public List<ApiDataDto> captureByGroup(String groupId){
        List<Camera> cameras = cameraService.findByGroupId(groupId);
        List<ApiDataDto> apiDataDtos = new ArrayList<>(16);
        if (null != cameras){
            for (Camera camera : cameras){
                ApiDataDto apiDataDto = new ApiDataDto();
                long loginHandle = camera.getLoginHandle();
                if (loginHandle == 0){
                    apiDataDto.setDevcieId(camera.getDevcieId());
                    apiDataDto.setPictureUrl(null);
                    apiDataDto.setDescription("设备未登录。");
                    apiDataDtos.add(apiDataDto);
                    continue;
                }
                    //此处设计有缺陷，需要重新设计
                    String devcieId = camera.getDevcieId();
                    boolean result =   capture(camera,0);
                    if (result){
                        String pictureUrl;
                        while (true){
                            pictureUrl = redisUtil.getPictureUrl(devcieId + ":" + pictureUrlKey);
                            if (!redisTemplate.hasKey(devcieId + ":" + pictureUrlKey) || StringUtils.isNotBlank(pictureUrl)){
                                break;
                            }
                        }
                        log.info("pictureUrl" + devcieId +":" +  pictureUrl);
                        apiDataDto.setDevcieId(camera.getDevcieId());
                        apiDataDto.setPictureUrl(pictureUrl);
                        apiDataDto.setDescription("截图成功");
                    }else {
                        apiDataDto.setDevcieId(camera.getDevcieId());
                        apiDataDto.setPictureUrl(null);
                        apiDataDto.setDescription("截图失败");
                    }
                    apiDataDtos.add(apiDataDto);

            }


        }
        return apiDataDtos;
    }

    /**
     * 抓图
     * @param camera 设备信息
     * @param chn 通道 0
     */
    public boolean capture(Camera camera, int chn){
        deviceId = camera.getGroupId();
        log.info("此时存储的设备是=" +camera.toString());
        NetSDKLib.LLong loginHandle = new NetSDKLib.LLong(camera.getLoginHandle());
        return AutoRegisterModule.snapPicture(loginHandle, chn);
    }

    // 停止对讲
    public void stopTalk(){
        if(AutoRegisterModule.m_hTalkHandle.longValue() != 0) {
            AutoRegisterModule.stopTalk(AutoRegisterModule.m_hTalkHandle);
        }
    }
    //停止拉流
    public void stopRealPlay(){
        if(realplayHandle.longValue() != 0) {
            AutoRegisterModule.stopRealPlay(realplayHandle);
        }
    }
    // 登出所有设备
    public boolean  logout(Camera camera){
        long loginHandle = camera.getLoginHandle();
        NetSDKLib.LLong m_hLoginHandle = new NetSDKLib.LLong(loginHandle);
        return AutoRegisterModule.logout(m_hLoginHandle);
    }

    /**
     * 侦听服务器回调函数
     */
    public class ServiceCB implements NetSDKLib.fServiceCallBack {
        @Override
        public int invoke(NetSDKLib.LLong lHandle, final String pIp, final int wPort,
                          int lCommand, Pointer pParam, int dwParamLen,
                          Pointer dwUserData) {
            log.info("开始监听");
            // 将 pParam 转化为序列号
            byte[] buffer = new byte[dwParamLen];
            pParam.read(0, buffer, 0, dwParamLen);
            String deviceId = "";
            try {
                deviceId = new String(buffer, "GBK").trim();
                log.info("监听到的设备id:${deviceId}=" + deviceId);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            log.info("Register Device Info [Device address %s][port %s][DeviceID %s] \n"+pIp+ wPort+ deviceId);
            System.out.printf("Register Device Info [Device address %s][port %s][DeviceID %s] \n", pIp, wPort, deviceId);
            List<Camera> cameras = getAllCamera();
            switch(lCommand) {
                case NetSDKLib.EM_LISTEN_TYPE.NET_DVR_DISCONNECT: {  // 验证期间设备断线回调
                    for (Camera camera : cameras){
                        if (camera.getDevcieId().equals(deviceId)){
                            camera.setDeviceIp("");
                            camera.setDevicePort(0);
                            break;
                        }
                    }
                    break;
                }
                case NetSDKLib.EM_LISTEN_TYPE.NET_DVR_SERIAL_RETURN: { // 设备注册携带序列号
                    for (Camera camera : cameras){
                        if (camera.getDevcieId().equals(deviceId)){
                            log.info("设备注册id=" + deviceId);
                           // Camera c  = cameraService.findByDevcieId(deviceId);
                           // if (c == null){
                                log.info("数据库里面，没有才进行连接");
                                camera.setDeviceIp(pIp);
                                camera.setDevicePort(wPort);
                                    //开一个线程去登录
                                    ExecutorService executorService = ThreadPoolUtil.getPool();
                                    Future<NetSDKLib.LLong> loginResult = executorService.submit(new Callable<NetSDKLib.LLong>() {
                                        @Override
                                        public NetSDKLib.LLong call() throws Exception {
                                            DEVICE_INFO deviceInfo = new DEVICE_INFO();
                                            deviceInfo.setDevcieId(camera.getDevcieId());
                                            deviceInfo.setDeviceIp(camera.getDeviceIp());
                                            deviceInfo.setUsername(camera.getLoginName());
                                            deviceInfo.setPassword(camera.getPassword());
                                            deviceInfo.setDevicePort(camera.getDevicePort());
                                            NetSDKLib.LLong lLong = new NetSDKLib.LLong(camera.getLoginHandle());
                                            deviceInfo.setLoginHandle(lLong);
                                            log.info("匹配成功，开始连接=" + deviceInfo);
                                            return login(deviceInfo);
                                        }
                                    });
                                    try {
                                        log.info("登录结果loginResult=" + loginResult.get().longValue());
                                        if(loginResult.get().longValue() != 0){
                                            log.info("当前设备信息=" + camera.toString());
                                            camera.setLoginHandle(loginResult.get().longValue());
                                            log.info("更新设备登录句柄=" + loginResult.get().longValue());
                                            cameraService.update(camera);
                                            for(int i = 0; i < AutoRegisterModule.m_stDeviceInfo.byChanNum; i++) {
                                                //此处进行通道设置
                                            }
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }
                           // }
                            break;
                        }
                    }
                    break;
                }
                default:
                    break;

            }

            return 0;
        }
    }

    /**
     * 登录设备
     * @param deviceIp 登录设备IP
     * @param port 登录设备端口号
     * @param deviceTreeNode 登录设备设备节点
     */
    private NetSDKLib.LLong login(DEVICE_INFO deviceInfo) {
        // 判断设备是否登录
        if(deviceInfo.getLoginHandle().longValue() != 0) {
            return null;
        }

        NetSDKLib.LLong loginHandleLong = AutoRegisterModule.login(deviceInfo.getDeviceIp(),
                deviceInfo.getDevicePort(),
                deviceInfo.getUsername(),
                deviceInfo.getPassword(),
                deviceInfo.getDevcieId());
        if(loginHandleLong.longValue() != 0) {
            System.out.printf("Login Success [Device IP %s][port %d][DeviceID %s]\n",deviceInfo.getDeviceIp(),
                    deviceInfo.getDevicePort(), deviceInfo.getDevcieId());
        }  else {
            System.err.printf("Login Failed[Device IP %s] [Port %d][DeviceID %s] %s", deviceInfo.getDeviceIp(),
                    deviceInfo.getDevicePort(), deviceInfo.getDevcieId(), ToolKits.getErrorCodePrint());
        }

        return loginHandleLong;
    }

    // 设备断线回调: 通过 CLIENT_Init 设置该回调函数，当设备出现断线时，SDK会调用该函数
    private class DisConnect implements NetSDKLib.fDisConnect {
        public void invoke(NetSDKLib.LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
            System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);
            log.info("设备断线，进入回调函数");
            List<Camera> cameras = getAllCamera();
            for (Camera camera : cameras){
                // 根据设备IP判断断线设备
                if(pchDVRIP.equals(camera.getDeviceIp())
                        && nDVRPort == camera.getDevicePort()) {

                    synchronized (this) {
                        log.info("断线设备信息=" +pchDVRIP + "," + nDVRPort );
                        //删除缓存
                        //redisTemplate.delete(startService);
                        // 停止断线设备的对讲, 主动注册中，对讲要用同步，不能在另开的线程里停止对讲，否则会出现句柄无效的错误
                        AutoRegisterModule.stopTalk(AutoRegisterModule.m_hTalkHandle);
                        // 停止断线设备的拉流
                        AutoRegisterModule.stopRealPlay(realplayHandle);

                        // 登出
                        log.info("设备断线登出，设备登录句柄=" + camera.getLoginHandle());
                        if(camera.getLoginHandle() != 0) {
                            //设备设置
                            camera.setLoginHandle(0);
                            cameraService.update(camera);
                            // 登出句柄
                            NetSDKLib.LLong loginHandle = new NetSDKLib.LLong(camera.getLoginHandle());
                            AutoRegisterModule.logout(loginHandle);
                        }
                    }
                    break;
                }
            }

        }
    }

    /**
     * 抓图回调函数
     */
    public class CaptureReceiveCB implements NetSDKLib.fSnapRev {
        BufferedImage bufferedImage = null;

        public void invoke(NetSDKLib.LLong lLoginID, Pointer pBuf, int RevLen, int EncodeType, int CmdSerial, Pointer dwUser) {
            if (pBuf != null && RevLen > 0) {
                String strFileName = SavePath.getSavePath().getSaveCapturePath();
                log.info("回调截图lLoginID" + lLoginID);
                Camera camera = cameraService.findByLoginHandle(lLoginID.longValue());
                log.info("根据登录句柄获取到的设备" + camera.toString());
                System.out.println("strFileName = " + strFileName);
                byte[] buf = pBuf.getByteArray(0, RevLen);
                ByteArrayInputStream byteArrInput = new ByteArrayInputStream(buf);
                try {
                    bufferedImage = ImageIO.read(byteArrInput);
                    if (bufferedImage == null) {
                        return;
                    }
                    ImageIO.write(bufferedImage, "jpg", new File(strFileName));
                    //上传到阿里云
                    aliyunOSSUtil.putObject(camera,new File(strFileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }



    /**
     * 设备信息
     */
    private class DEVICE_INFO {
        private String devcieId = "";
        private String username = "";
        private String password = "";
        private String deviceIp = "";
        private int port = 0;
        private NetSDKLib.LLong loginHandle = new NetSDKLib.LLong(0);

        public String getDevcieId() {
            return devcieId;
        }

        public void setDevcieId(String devcieId) {
            this.devcieId = devcieId;
        }

        public String getDeviceIp() {
            return deviceIp;
        }

        public void setDeviceIp(String deviceIp) {
            this.deviceIp = deviceIp;
        }

        public int getDevicePort() {
            return port;
        }

        public void setDevicePort(int port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public NetSDKLib.LLong getLoginHandle() {
            return loginHandle;
        }

        public void setLoginHandle(NetSDKLib.LLong loginHandle) {
            this.loginHandle = loginHandle;
        }
    }

    /**
     * 获取所有的摄像头设备
     * @return
     */
    private List<Camera> getAllCamera(){
        return cameraService.getAll();
    }

    /**
     * 获取本地地址
     * @return
     */
    private String getHostAddress() {
        String address = "";
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            address = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return address;
    }
}
