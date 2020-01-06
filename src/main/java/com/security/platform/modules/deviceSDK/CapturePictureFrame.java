///*
//package com.security.platform.modules.deviceSDK;
//
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.GridLayout;
//import java.awt.Panel;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.IOException;
//import java.util.Vector;
//
//import com.security.platform.netsdk.common.BorderEx;
//import com.security.platform.netsdk.common.LoginPanel;
//import com.security.platform.netsdk.common.PaintPanel;
//import com.security.platform.netsdk.common.Res;
//import com.security.platform.netsdk.demo.LoginModule;
//import com.security.platform.netsdk.lib.NetSDKLib;
//import com.security.platform.netsdk.lib.ToolKits;
//import com.sun.jna.Pointer;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//
//public class CapturePictureFrame extends JFrame {
//
//    private static final long serialVersionUID = 1L;
//
//    // device channel list
//    private Vector<String> chnlist = new Vector<String>();
//
//    // This field indicates whether the device is playing
//    private boolean bRealPlay = false;
//
//    // This field indicates whether the device is timing capture
//    private boolean bTimerCapture = false;
//
//    // device disconnect callback instance
//    private static DisConnect disConnect       = new DisConnect();
//
//    // device reconnect callback instance
//    private static HaveReConnect haveReConnect = new HaveReConnect();
//
//    // realplay handle
//    public static NetSDKLib.LLong m_hPlayHandle = new NetSDKLib.LLong(0);
//
//    // capture picture frame (this)
//    private static JFrame frame = new JFrame();
//
//    private LoginPanel loginPanel;
//
//    private RealPanel realPanel;
//    private JPanel realplayPanel;
//    private Panel realPlayWindow;
//    private Panel channelPanel;
//    private JLabel chnlabel;
//    private JComboBox chnComboBox;
//    private JLabel streamLabel;
//    private JComboBox streamComboBox;
//    private JButton realplayBtn;
//
//    private PICPanel picPanel;
//    private JPanel pictureShowPanel;
//    private JPanel capturePanel;
//    private PaintPanel pictureShowWindow;
//    private JButton localCaptureBtn;
//    private JButton remoteCaptureBtn;
//    private JButton timerCaptureBtn;
//
//    public CapturePictureFrame() {
//        setTitle(Res.string().getCapturePicture());
//        setLayout(new BorderLayout());
//        pack();
//        setSize(800, 560);
//        setResizable(false);
//        setLocationRelativeTo(null);
//        LoginModule.init(disConnect, haveReConnect);   // init sdk
//
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        loginPanel = new LoginPanel();
//        realPanel = new RealPanel();
//        picPanel = new PICPanel();
//
//        add(loginPanel, BorderLayout.NORTH);
//        add(realPanel, BorderLayout.CENTER);
//        add(picPanel, BorderLayout.EAST);
//
//        loginPanel.addLoginBtnActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(loginPanel.checkLoginText()) {
//                    if(login()) {
//                        frame = ToolKits.getFrame(e);
//                        frame.setTitle(Res.string().getCapturePicture() + " : " + Res.string().getOnline());
//                    }
//                }
//            }
//        });
//
//        loginPanel.addLogoutBtnActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                frame.setTitle(Res.string().getCapturePicture());
//                logout();
//            }
//        });
//
//        addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                RealPlayModule.stopRealPlay(m_hPlayHandle);
//                LoginModule.logout();
//                LoginModule.cleanup();
//                dispose();
//
//                SwingUtilities.invokeLater(new Runnable() {
//                    public void run() {
//                        FunctionList demo = new FunctionList();
//                        demo.setVisible(true);
//                    }
//                });
//            }
//        });
//    }
//
//    /////////////////function///////////////////
//    // device disconnect callback class
//    // set it's instance by call CLIENT_Init, when device disconnect sdk will call it.
//    private static class DisConnect implements NetSDKLib.fDisConnect {
//        public void invoke(NetSDKLib.LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
//            System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);
//
//            SwingUtilities.invokeLater(new Runnable() {
//                public void run() {
//                    frame.setTitle(Res.string().getCapturePicture() + " : " + Res.string().getDisConnectReconnecting());
//                }
//            });
//        }
//    }
//
//    private static class HaveReConnect implements NetSDKLib.fHaveReConnect {
//        public void invoke(NetSDKLib.LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
//            System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);
//
//            SwingUtilities.invokeLater(new Runnable() {
//                public void run() {
//                    frame.setTitle(Res.string().getCapturePicture() + " : " + Res.string().getOnline());
//                }
//            });
//        }
//    }
//
//    private class RealPanel extends JPanel {
//        private static final long serialVersionUID = 1L;
//
//        public RealPanel() {
//            BorderEx.set(this, Res.string().getRealplay(), 2);
//            setLayout(new BorderLayout());
//
//            channelPanel = new Panel();
//            realplayPanel = new JPanel();
//
//            add(channelPanel, BorderLayout.SOUTH);
//            add(realplayPanel, BorderLayout.CENTER);
//
//            */
///************ realplay panel **************//*
//
//            realplayPanel.setLayout(new BorderLayout());
//            realplayPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
//            realPlayWindow = new Panel();
//            realPlayWindow.setBackground(Color.GRAY);
//            realplayPanel.add(realPlayWindow, BorderLayout.CENTER);
//
//            */
///************ channel and stream panel **************//*
//
//            chnlabel = new JLabel(Res.string().getChannel());
//            chnComboBox = new JComboBox();
//
//            streamLabel = new JLabel(Res.string().getStreamType());
//            String[] stream = {Res.string().getMasterStream(), Res.string().getSubStream()};
//            streamComboBox = new JComboBox(stream);
//
//            realplayBtn = new JButton(Res.string().getStartRealPlay());
//
//            channelPanel.setLayout(new FlowLayout());
//            channelPanel.add(chnlabel);
//            channelPanel.add(chnComboBox);
//            channelPanel.add(streamLabel);
//            channelPanel.add(streamComboBox);
//            channelPanel.add(realplayBtn);
//
//            chnComboBox.setPreferredSize(new Dimension(90, 20));
//            streamComboBox.setPreferredSize(new Dimension(90, 20));
//            realplayBtn.setPreferredSize(new Dimension(120, 20));
//
//            realPlayWindow.setEnabled(false);
//            chnComboBox.setEnabled(false);
//            streamComboBox.setEnabled(false);
//            realplayBtn.setEnabled(false);
//
//            realplayBtn.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    realplay();
//                }
//            });
//        }
//    }
//
//    */
///*
//     * capture picture panel
//     *//*
//
//    private class PICPanel extends JPanel {
//        private static final long serialVersionUID = 1L;
//
//        public PICPanel() {
//
//            setPreferredSize(new Dimension(350, 600));
//            BorderEx.set(this, Res.string().getCapturePicture(), 2);
//            setLayout(new BorderLayout());
//
//            pictureShowPanel = new JPanel();
//            capturePanel = new JPanel();
//
//            add(pictureShowPanel, BorderLayout.CENTER);
//            add(capturePanel, BorderLayout.SOUTH);
//
//            */
///************** capture picture button ************//*
//
//            capturePanel.setLayout(new GridLayout(3, 1));
//
//            localCaptureBtn = new JButton(Res.string().getLocalCapture());
//            remoteCaptureBtn = new JButton(Res.string().getRemoteCapture());
//            timerCaptureBtn = new JButton(Res.string().getTimerCapture());
//
//            localCaptureBtn.setPreferredSize(new Dimension(150, 20));
//            remoteCaptureBtn.setPreferredSize(new Dimension(150, 20));
//            timerCaptureBtn.setPreferredSize(new Dimension(150, 20));
//
//            capturePanel.add(localCaptureBtn);
//            capturePanel.add(remoteCaptureBtn);
//            capturePanel.add(timerCaptureBtn);
//
//            localCaptureBtn.setEnabled(false);
//            remoteCaptureBtn.setEnabled(false);
//            timerCaptureBtn.setEnabled(false);
//
//            */
///************** picture show panel ************//*
//
//            pictureShowPanel.setLayout(new BorderLayout());
//            pictureShowPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
//            pictureShowWindow = new PaintPanel();
//            pictureShowPanel.add(pictureShowWindow, BorderLayout.CENTER);
//
//            localCaptureBtn.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent arg0) {
//                    if (!bRealPlay) {
//                        JOptionPane.showMessageDialog(null, Res.string().getNeedStartRealPlay(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
//                        return;
//                    }
//                    String strFileName = SavePath.getSavePath().getSaveCapturePath();
//                    System.out.println("strFileName = " + strFileName);
//
//                    if(!CapturePictureModule.localCapturePicture(m_hPlayHandle, strFileName)) {
//                        JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
//                        return;
//                    }
//
//                    BufferedImage bufferedImage = null;
//                    try {
//                        bufferedImage = ImageIO.read(new File(strFileName));
//                        if(bufferedImage == null) {
//                            return;
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    pictureShowWindow.setOpaque(false);
//                    pictureShowWindow.setImage(bufferedImage);
//                    pictureShowWindow.repaint();
//
//                }
//            });
//
//            remoteCaptureBtn.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent arg0) {
//                    if(!CapturePictureModule.remoteCapturePicture(chnComboBox.getSelectedIndex())) {
//                        JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            });
//
//            timerCaptureBtn.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent arg0) {
//                    if (!bTimerCapture) {
//
//                        if(!CapturePictureModule.timerCapturePicture(chnComboBox.getSelectedIndex())) {
//                            JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
//                        }else{
//                            bTimerCapture = true;
//                            timerCaptureBtn.setText(Res.string().getStopCapture());
//                            chnComboBox.setEnabled(false);
//                            remoteCaptureBtn.setEnabled(false);
//                        }
//                    }else {
//                        if(!CapturePictureModule.stopCapturePicture(chnComboBox.getSelectedIndex())) {
//                            JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
//                        }else{
//                            bTimerCapture = false;
//                            timerCaptureBtn.setText(Res.string().getTimerCapture());
//                            chnComboBox.setEnabled(true && !bRealPlay);
//                            remoteCaptureBtn.setEnabled(true);
//                        }
//                    }
//                }
//            });
//        }
//    }
//}
//*/
