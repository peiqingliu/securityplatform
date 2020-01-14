package com.security.platform.modules.devicesdk.module;

import com.security.platform.netsdk.module.LoginModule;
import com.security.platform.netsdk.lib.NetSDKLib;
import com.security.platform.netsdk.lib.ToolKits;

import java.awt.*;

/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/7 13:48
 * @File RealPlayModule
 * @Software IntelliJ IDEA
 * @description 实时预览接口实现,主要有 ：开始拉流、停止拉流功能
 */
public class RealPlayModule {
    /**
     * \if ENGLISH_LANG
     * Start RealPlay
     * \else
     * 开始预览
     * \endif
     */
    public static NetSDKLib.LLong startRealPlay(int channel, int stream, Panel realPlayWindow) {
        NetSDKLib.LLong m_hPlayHandle = LoginModule.netsdk.CLIENT_RealPlayEx(LoginModule.m_hLoginHandle, channel, null, stream);

        if(m_hPlayHandle.longValue() == 0) {
            System.err.println("开始实时监视失败，错误码" + ToolKits.getErrorCodePrint());
        } else {
            System.out.println("Success to start realplay");
        }

        return m_hPlayHandle;
    }

    /**
     * \if ENGLISH_LANG
     * Start RealPlay
     * \else
     * 停止预览
     * \endif
     */
    public static void stopRealPlay(NetSDKLib.LLong m_hPlayHandle) {
        if(m_hPlayHandle.longValue() == 0) {
            return;
        }

        boolean bRet = LoginModule.netsdk.CLIENT_StopRealPlayEx(m_hPlayHandle);
        if(bRet) {
            m_hPlayHandle.setValue(0);
        }
    }



}
