package ClientDemo;

import com.alibaba.fastjson.JSONObject;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.net.NoRouteToHostException;

import static ClientDemo.ClientDemo.hCNetSDK;

/**
 * @author songxuefeng
 * @create 2018-11-19 12:04
 * @description: ${description}
 **/
public class Main extends Application {
//    static String urlPref="http://localhost:8082";

    static String urlPref=Config.getObject("urlPref");

    public static void main(String[] args) {
        launch(args);
    }

    public static void centerWindow1(Container window) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(0, 0);
        window.setSize(dim.width/3,dim.height/2);
    }
    public static void centerWindow2(Container window) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/3, 0);
        window.setSize(dim.width/3,dim.height/2);
    }
    public static void centerWindow3(Container window) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(2*dim.width/3, 0);
        window.setSize(dim.width/3,dim.height/2);
    }
    public static void centerWindow4(Container window) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(0, dim.height/2);
        window.setSize(dim.width/3,dim.height/2);
    }
    public static void centerWindow5(Container window) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/3, dim.height/2);
        window.setSize(dim.width/3,dim.height/2);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        try
//        {
//            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }

        java.awt.EventQueue.invokeLater(new Runnable()
        {

            public void run()
            {
                boolean initSuc = hCNetSDK.NET_DVR_Init();
                if (initSuc != true)
                {
                    JOptionPane.showMessageDialog(null, "初始化失败");
                }
//                MonitorCameraInfo cameraInfo=new MonitorCameraInfo("192.168.3.64","8000","admin","maxcar2018","北一道闸");
//
//                ClientDemo Demo = new ClientDemo(cameraInfo);
//                ClientDemo Demo2 = new ClientDemo(cameraInfo);
//                ClientDemo Demo3 = new ClientDemo(cameraInfo);
//                ClientDemo Demo4 = new ClientDemo(cameraInfo);
//                ClientDemo Demo5 = new ClientDemo(cameraInfo);
//
//                centerWindow1(Demo);
//                centerWindow2(Demo2);
//                centerWindow3(Demo3);
//                centerWindow4(Demo4);
//                centerWindow5(Demo5);
//
//                Demo.setVisible(true);
//                Demo2.setVisible(true);
//                Demo3.setVisible(true);
//                Demo4.setVisible(true);
//                Demo5.setVisible(true);

                try {
                    MonitorCameraInfo cameraInfo;
                    ClientDemo Demo;
                    JSONObject camera;
                    JSONObject barrier;
                    int code;

                    //获取窗口数量,最大满足6个
                    int num=Integer.parseInt(Config.getObject("MonitorNum"));
                    if (num>6){
                        JOptionPane.showMessageDialog(new JFrame(), "配置监控数量不能超过6");
                        System.exit(0);
                        return;
                    }


                    for (int i=1;i<num+1;i++){
                        String s = HttpRequest.sendGet(urlPref+"/barrier/camera/get/"+Config.getObject("cameraIp_"+i), null);
                        if (s.equals("")){
                            JOptionPane.showMessageDialog(new JFrame(), "请检查连接网络");
                        }
                        code = JSONObject.parseObject(s).getIntValue("code");
                        if (code!=200){
                            JOptionPane.showMessageDialog(new JFrame(), Config.getObject("cameraIp_"+i)+":该设备未查到,请检查配置");
                            System.exit(0);
                        }
                        camera = JSONObject.parseObject(s).getJSONObject("data");
                        cameraInfo=new MonitorCameraInfo();
                        cameraInfo.setCameraIp(camera.getString("deviceIp"));
                        cameraInfo.setUserName(camera.getString("userName"));
                        cameraInfo.setUserPwd(camera.getString("password"));
                        cameraInfo.setBarrierId(camera.getString("barrierId"));
                        cameraInfo.setCameraPort(camera.getString("devicePort"));


                        String bar=HttpRequest.sendPost(urlPref+"/barrier/selectByBarrierId", "{\n" +
                                "\t\"barrierId\":\""+cameraInfo.getBarrierId()+"\"\n" +
                                "}");
                        code = JSONObject.parseObject(bar).getIntValue("code");
                        if (code!=200){
                            JOptionPane.showMessageDialog(new JFrame(), "道闸查询服务出现异常");
                            System.exit(0);
                        }
                        barrier=JSONObject.parseObject(bar).getJSONObject("data");
                        if (barrier==null){
                            JOptionPane.showMessageDialog(new JFrame(), Config.getObject("cameraIp_"+i)+":该设备绑定道闸信息未查到,请检查配置及设备");
                        }
                        cameraInfo.setBarrierName(barrier.getString("barrierPosition"));
                        cameraInfo.setMqttTopic(barrier.getString("mqttTopic"));
                        cameraInfo.setBarrierType(barrier.getIntValue("inOutType"));
                        cameraInfo.setBarrierIp(barrier.getString("clientIp"));
                        System.out.println("======="+cameraInfo.getBarrierName());
                        Demo = new ClientDemo(cameraInfo);
                        centerWindow(Demo,i);
                        Demo.setVisible(true);

//                        java.util.Timer timer = new java.util.Timer();
//                        // 6秒后启动任务,以后每隔4秒执行一次线程
//                        timer.schedule(new TimerTaskTest(cameraInfo.getCameraIp(),Demo),6000,5000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }

        });
    }

    public static void centerWindow(Container window,int i) {
        GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle dim=ge.getMaximumWindowBounds();
//        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(((i-1)%3)*dim.width/3, ((i-1)/3)*dim.height/2);
        window.setSize(dim.width/3,dim.height/2);
    }
}
