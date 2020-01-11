package com.security.platform.modules.deviceSDK.service;

import com.security.platform.common.utils.AliyunOSSUtil;
import com.security.platform.common.utils.ResultUtil;
import com.security.platform.common.vo.CameraVo;
import com.security.platform.modules.deviceSDK.module.CapturePictureModule;
import com.security.platform.modules.deviceSDK.module.RealPlayModule;
import com.security.platform.netsdk.common.Res;
import com.security.platform.netsdk.common.SavePath;
import com.security.platform.netsdk.demo.LoginModule;
import com.security.platform.netsdk.lib.NetSDKLib;
import com.security.platform.netsdk.lib.ToolKits;
import com.sun.jna.CallbackThreadInitializer;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import static com.security.platform.netsdk.demo.LoginModule.m_hLoginHandle;
/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/7 10:46
 * @File CapturePictureService
 * @Software IntelliJ IDEA
 * @description todo
 */
@Slf4j
@Service
public class CapturePictureService {

    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;

    /**
     * 线程安全
     */
    private static final ThreadLocal<String> IpThreadLocal = new NamedThreadLocal<>("ip");

    private boolean bTimerCapture = false;

    // This field indicates whether the device is playing
    private boolean bRealPlay = false;

    private Panel realPlayWindow = new Panel();

    // realplay handle
    public static NetSDKLib.LLong m_hPlayHandle = new NetSDKLib.LLong(0);

    // device disconnect callback instance
    private static DisConnect disConnect       = new DisConnect();


    // device reconnect callback instance
    private static HaveReConnect haveReConnect = new HaveReConnect();


    /**
     * 判断设备是否在线
     */
    public boolean isOnline(CameraVo cameraVo){

        boolean isOnline = m_hLoginHandle.longValue() == 0? false:true;
        if (isOnline){
            return true;
        }else {
            boolean init = init();
            if (!init){
                log.error("Initialize SDK failed");
                return false;
            }
            boolean login = login(cameraVo.getIp(),cameraVo.getPort(),cameraVo.getLoginName(),cameraVo.getPassword());
            if (!login){
                log.error("LOGIN_FAILED" + ", " + LoginModule.netsdk.CLIENT_GetLastError() + ",ERROR_MESSAGE" + 0);
                return false;
            }
            return true;
        }
    }

    /**
     * 截图的方法
     */
    public void handleCapturePicture(){
        //初始化
        //LoginModule.init(disConnect, haveReConnect);   // init sdk
        boolean isOnline = m_hLoginHandle.longValue() == 0? false:true;
        init();
        //登录
        login("124.226.139.136",37777,"admin","admin888");
    }
    /**
     * 初始化
     * @return
     */
    public boolean init(){
        //初始化
        return  LoginModule.init(disConnect, haveReConnect);   // init sdk
    }

    private static class DisConnect implements NetSDKLib.fDisConnect {
        public void invoke(NetSDKLib.LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
            System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);
            log.info("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);
        }
    }

    // device reconnect(success) callback class
    // set it's instance by call CLIENT_SetAutoReconnect, when device reconnect success sdk will call it.
    private static class HaveReConnect implements NetSDKLib.fHaveReConnect {
        public void invoke(NetSDKLib.LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
            System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);
            log.info("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);
        }
    }

    public boolean login(String ip,int port,String loginName,String password) {
        Native.setCallbackThreadInitializer(m_CaptureReceiveCB,
                new CallbackThreadInitializer(false, false, "snapPicture callback thread"));
        boolean loginResult = LoginModule.login(ip,port,loginName,password);
        if(loginResult) {
            CapturePictureModule.setSnapRevCallBack(m_CaptureReceiveCB);
            IpThreadLocal.set(ip);
            String result = IpThreadLocal.get();
            log.info("result" + result);
            log.info("登录成功="+ip+port+loginName+password);
            //开始预览
            //realplay();
            //本地截图
            //localCapture();
            //远程截图
            //remoteCapture();

            return true;
        } else {
            log.error("LOGIN_FAILED" + ", " + LoginModule.netsdk.CLIENT_GetLastError() + ",ERROR_MESSAGE" + 0);
            return false;
        }
    }

    public fCaptureReceiveCB  m_CaptureReceiveCB = new fCaptureReceiveCB();

    public class fCaptureReceiveCB implements NetSDKLib.fSnapRev{
        BufferedImage bufferedImage = null;
        public void invoke(NetSDKLib.LLong lLoginID, Pointer pBuf, int RevLen, int EncodeType, int CmdSerial, Pointer dwUser) {
            if(pBuf != null && RevLen > 0) {
                String strFileName = SavePath.getSavePath().getSaveCapturePath();
                log.info("strFileName = " + strFileName);
                System.out.println("strFileName = " + strFileName);

                byte[] buf = pBuf.getByteArray(0, RevLen);
                ByteArrayInputStream byteArrInput = new ByteArrayInputStream(buf);
                try {
                    bufferedImage = ImageIO.read(byteArrInput);
                    if(bufferedImage == null) {
                        return;
                    }
                    ImageIO.write(bufferedImage, "jpg", new File(strFileName));
                    //上传到阿里云
                    String ip = IpThreadLocal.get();
                    aliyunOSSUtil.putObject("124.226.139.136",new File(strFileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void logout() {
        if (bTimerCapture) {
          //  CapturePictureModule.stopCapturePicture(chnComboBox.getSelectedIndex());
        }
        RealPlayModule.stopRealPlay(m_hPlayHandle);
        LoginModule.logout();
        bRealPlay = false;
        bTimerCapture = false;
    }

    public void realplay() {
        if(!bRealPlay) {
            m_hPlayHandle = RealPlayModule.startRealPlay(0,0,realPlayWindow);
            if(m_hPlayHandle.longValue() != 0) {
                bRealPlay = true;
            }
        } else {
            RealPlayModule.stopRealPlay(m_hPlayHandle);
            bRealPlay = false;
        }
    }

    /**
     * 本地截图
     */
    private void localCapture(){
        if (!bRealPlay){
            log.error(null, Res.string().getNeedStartRealPlay(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
            log.error("PLEASE_START_REALPLAY" + "," + "ERROR_MESSAGE" + 0);
            return;
        }
        String strFileName = SavePath.getSavePath().getSaveCapturePath();
        log.info("strFileName = " + strFileName);
        if(!CapturePictureModule.localCapturePicture(m_hPlayHandle, strFileName)) {
            log.error(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
            return;
        }
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File(strFileName));
            if(bufferedImage == null) {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void remoteCapture(){
        CapturePictureModule.remoteCapturePicture(0);
      //  CapturePictureModule.setSnapRevCallBack(m_CaptureReceiveCB);
    }
}
