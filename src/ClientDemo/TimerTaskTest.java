package ClientDemo;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Properties;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TimerTaskTest extends TimerTask{
    private String ipAddress;
    private ClientDemo clientDemo;

    public TimerTaskTest(String ipAddress, ClientDemo clientDemo) {
        this.ipAddress = ipAddress;
        this.clientDemo = clientDemo;
    }

    @Override
    public void run() {
        try {
            boolean b = ping(ipAddress,1,3000);
            System.out.println(b);
            if (!b){
//                JOptionPane.showMessageDialog(clientDemo, ipAddress+":监控断开连接,请检查网络及设备","提示",JOptionPane.INFORMATION_MESSAGE);

//                window.showModelessDialog()
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean ping(String ipAddress) throws Exception {
        int  timeOut =  3000 ;  //超时应该在3钞以上
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);     // 当返回值是true时，说明host是可用的，false则不可。
        return status;
    }

    //第二种方法:使用java调用cmd命令,这种方式最简单，可以把ping的过程显示在本地。
    public static void ping02(String ipAddress) throws Exception {
        String line = null;
        try {
            Process pro = Runtime.getRuntime().exec("ping " + ipAddress);
            BufferedReader buf = new BufferedReader(new InputStreamReader(
                    pro.getInputStream(),"GBK"));
            while ((line = buf.readLine()) != null){
                //line = new String(line.getBytes("ISO-8859-1"),"UTF-8");
                //line = new String(line.getBytes("UTF-8"),"GBK");
                //line = new String(line.getBytes("ISO-8859-1"),"utf-8");
                //line = new String(line.getBytes("ISO-8859-1"),"GBK");
                //line = new String(line.getBytes("gb2312"),"GBK");
                //line = new String(line.getBytes("gb2312"),"UTF-8");
                //System.out.println(transcoding(line, "gbk") );
                System.out.println(line);

            }


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static boolean ping(String ipAddress, int pingTimes, int timeOut) {
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();  // 将要执行的ping命令,此命令是windows格式的命令
        String pingCommand = "ping " + ipAddress + " -n " + pingTimes    + " -w " + timeOut;
        try {   // 执行命令并获取输出
            System.out.println(pingCommand);
            Process p = r.exec(pingCommand);
            if (p == null) {
                return false;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));   // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            int connectedCount = 0;
            String line = null;
            while ((line = in.readLine()) != null) {
                connectedCount += getCheckResult(line);
            }   // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            return connectedCount == pingTimes;
        } catch (Exception ex) {
            ex.printStackTrace();   // 出现异常则返回假
            return false;
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static int getCheckResult(String line) {  // System.out.println("控制台输出的结果为:"+line);
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",    Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) throws Exception{
//        ping02("192.168.3.64");
        System.out.println(ping("192.168.3.64",1,3000));
//        System.out.println(ping("192.168.3.64"));
    }

}

