package ClientDemo;

import java.io.*;
import java.util.Properties;


/**
 * @author songxuefeng
 * @create 2018-11-19 16:05
 * @description: ${description}
 **/
public class Config {
    private static Properties properties;


    static {
        try {
            //或者
//            properties.load(this.getClass().getResourceAsStream("/init.properties"));
            //获取路径
//            path =  this.getClass().getResource("/init.properties").getPath();
//            File file = new File(path);
//            String abPath = file.getAbsolutePath() ;


            properties = new Properties();


            // 读取src下配置文件 在resource目录下--- 属于读取内部文件 注意："/" 必须有，是指根本下
//            properties.load(Config.class.getResourceAsStream("/init.properties"));


//            InputStream  in = new BufferedInputStream(Config.class.getResourceAsStream("/init.properties"));
//            properties.load(new InputStreamReader(in, "GBK"));
//            in.close();

            // 读取系统外配置文件 (即Jar包外文件) --- 外部工程引用该Jar包时需要在工程下创建config目录存放配置文件
            String filePath = System.getProperty("user.dir")
                    + "\\config\\init.properties";
            System.out.println(filePath);
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
            properties.load(new InputStreamReader(in, "GBK"));
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getObject(String prepKey) {
        return properties.getProperty(prepKey);
    }
    public static void main(String[] agrs) {
        System.out.println(Config.getObject("MonitorNum"));
        System.out.println(Config.getObject("barrierName_1"));
    }
}
