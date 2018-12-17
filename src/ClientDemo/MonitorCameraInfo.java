package ClientDemo;

/**
 * @author songxuefeng
 * @create 2018-11-14 14:00
 * @description: ${description}
 **/
public class MonitorCameraInfo {
    private String cameraIp;
    private String cameraPort;
    private String userName;
    private String userPwd;

    private String barrierName;
    private String barrierId;
    private String mqttTopic;
    private Integer barrierType;//0:入口,1:出口
    private String barrierIp;

    public MonitorCameraInfo(String cameraIp, String cameraPort, String userName, String userPwd, String barrierName) {
        this.cameraIp = cameraIp;
        this.cameraPort = cameraPort;
        this.userName = userName;
        this.userPwd = userPwd;
        this.barrierName = barrierName;
    }

    public MonitorCameraInfo() {
    }

    public String getBarrierIp() {
        return barrierIp;
    }

    public void setBarrierIp(String barrierIp) {
        this.barrierIp = barrierIp;
    }

    public String getBarrierId() {
        return barrierId;
    }

    public void setBarrierId(String barrierId) {
        this.barrierId = barrierId;
    }

    public String getMqttTopic() {
        return mqttTopic;
    }

    public void setMqttTopic(String mqttTopic) {
        this.mqttTopic = mqttTopic;
    }

    public Integer getBarrierType() {
        return barrierType;
    }

    public void setBarrierType(Integer barrierType) {
        this.barrierType = barrierType;
    }

    public String getCameraIp() {
        return cameraIp;
    }

    public void setCameraIp(String cameraIp) {
        this.cameraIp = cameraIp;
    }

    public String getCameraPort() {
        return cameraPort;
    }

    public void setCameraPort(String cameraPort) {
        this.cameraPort = cameraPort;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getBarrierName() {
        return barrierName;
    }

    public void setBarrierName(String barrierName) {
        this.barrierName = barrierName;
    }
}
