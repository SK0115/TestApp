package com.test.app.util;

import android.content.Context;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;

import com.test.app.helper.LogHelper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Locale;

public class CommonUtil {

    private static final String TAG = "CommonUtil";

    /**
     * 检测对象是否为空
     * 
     * @param reference
     * @param <T>
     * @return
     */
    public static <T> boolean checkIsNull(T reference) {
        if (null == reference) {
            return true;
        }
        return false;
    }

    /**
     * 获取设备支持的cpu架构
     * 
     * @return
     */
    public static String getDeviceSupportAbis() {
        String[] abis = new String[] {};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            abis = Build.SUPPORTED_ABIS;
        } else {
            abis = new String[] { Build.CPU_ABI, Build.CPU_ABI2 };
        }
        StringBuilder abiStr = new StringBuilder();
        for (String abi : abis) {
            abiStr.append(abi);
            abiStr.append(',');
        }
        LogHelper.releaseLog(TAG, "getDeviceSupportAbis:" + abiStr.toString());
        return abiStr.toString();
    }

    /**
     *
     * 获取cpu类型和架构
     *
     * @return 三个参数类型的数组，第一个参数标识是不是ARM架构，第二个参数标识是V6还是V7架构，第三个参数标识是不是neon指令集
     */
    public static Object[] getCpuArchitecture() {
        Object[] armArchitecture = new Object[3];
        try {
            InputStream is = new FileInputStream("/proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(ir);
            try {
                String nameProcessor = "Processor";
                String nameFeatures = "Features";
                String nameModel = "model name";
                String nameCpuFamily = "cpu family";
                while (true) {
                    String line = br.readLine();
                    String[] pair = null;
                    if (line == null) {
                        break;
                    }
                    pair = line.split(":");
                    if (pair.length != 2) {
                        continue;
                    }
                    String key = pair[0].trim();
                    String val = pair[1].trim();
                    if (key.compareTo(nameProcessor) == 0) {
                        String n = "";
                        for (int i = val.indexOf("ARMv") + 4; i < val.length(); i++) {
                            String temp = val.charAt(i) + "";
                            if (temp.matches("\\d")) {
                                n += temp;
                            } else {
                                break;
                            }
                        }
                        armArchitecture[0] = "ARM";
                        armArchitecture[1] = Integer.parseInt(n);
                        continue;
                    }

                    if (key.compareToIgnoreCase(nameFeatures) == 0) {
                        if (val.contains("neon")) {
                            armArchitecture[2] = "neon";
                        }
                        continue;
                    }

                    if (key.compareToIgnoreCase(nameModel) == 0) {
                        if (val.contains("Intel")) {
                            armArchitecture[0] = "INTEL";
                            armArchitecture[2] = "atom";
                        }
                        continue;
                    }

                    if (key.compareToIgnoreCase(nameCpuFamily) == 0) {
                        armArchitecture[1] = Integer.parseInt(val);
                        continue;
                    }
                }
            } finally {
                br.close();
                ir.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogHelper.errorLog(TAG, "getCpuArchitecture msg:" + e.getMessage());
        }
        for (int i = 0; i < armArchitecture.length; i++) {
            LogHelper.releaseLog(TAG, "getCpuArchitecture:" + armArchitecture[i]);
        }
        return armArchitecture;
    }

    /**
     * 获取本地IP
     * 
     * @return
     */
    public static String getLocalIPAddress() {
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        // Inetaddressutils.isIPv4Address(inetAddress.getHostAddress()) //API19以前可用
                        ip = inetAddress.getHostAddress();
                        break;
                    }
                }
                if (!TextUtils.isEmpty(ip)) {
                    break;
                }
            }
        } catch (Exception e) {
            LogHelper.errorLog(TAG, "getLocalIPAddress error:" + e.toString());
            ip = "";
        }
        LogHelper.releaseLog(TAG, "getLocalIPAddress ip:" + ip);
        return ip;
    }

    /**
     * 获取当前正在连接的wifi的信息
     *
     * @param context
     * @return
     */
    public static WifiInfo getCurrentConnectingWIFI(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            // 得到当前连接的wifi热点的信息
            if (!checkIsNull(wifiManager)) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String netName = wifiInfo.getSSID(); // 获取被连接网络的名称
                String netMac = wifiInfo.getBSSID(); // 获取被连接网络的mac地址
                String localMac = wifiInfo.getMacAddress();// 获得本机的MAC地址
                int localIP = wifiInfo.getIpAddress();
                int level = wifiInfo.getRssi();
                int linkSpeed = wifiInfo.getLinkSpeed();
                LogHelper.releaseLog(TAG, "getCurrentConnectingWIFI wifiInfo:" + wifiInfo.toString());
                return wifiInfo;
            }
            return null;
        } catch (Exception e) {
            LogHelper.errorLog(TAG, "getCurrentConnectingWIFI Exception! message:" + e.getMessage());
        }
        return null;
    }

    /**
     * 获取当前正在连接的wifi的SSID
     *
     * @param context
     * @return
     */
    public static String getCurrentConnectWIFISSID(Context context) {
        try {
            WifiInfo wifiInfo = getCurrentConnectingWIFI(context);
            if (null != wifiInfo) {
                String currentSSID = wifiInfo.getSSID();
                SupplicantState supplicantState = wifiInfo.getSupplicantState();
                if (!SupplicantState.COMPLETED.equals(supplicantState)) {
                    return "";
                }
                if (!TextUtils.isEmpty(currentSSID) && currentSSID.length() > 2) {
                    if (currentSSID.startsWith("\"") && currentSSID.endsWith("\"")) {
                        currentSSID = currentSSID.substring(1, currentSSID.length() - 1);
                    }
                    LogHelper.releaseLog(TAG + "getCurrentConnectWIFISSID wifiName:" + currentSSID);
                    return currentSSID;
                }
            }
        } catch (Exception e) {
            LogHelper.errorLog(TAG + "getCurrentConnectWIFISSID Exception! message:" + e.getMessage());
        }
        return "";
    }

    /**
     * 获取网络信息
     * 
     * @param context
     */
    public static void getNetworkInfo(Context context) {
        try {
            WifiManager wm = null;
            String ip = "";
            String wifiName = "";
            String mac = "";
            try {
                wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            } catch (Exception e) {
                wm = null;
                LogHelper.errorLog(TAG, "getNetworkInfo WifiManager error:" + e.toString());
            }
            if (null != wm && wm.isWifiEnabled()) {
                WifiInfo wifi = wm.getConnectionInfo();
                if (wifi.getRssi() != -200) {
                    ip = getWifiIPAddress(wifi.getIpAddress());
                }
                wifiName = wifi.getSSID(); // 获取被连接网络的名称
                mac = wifi.getBSSID(); // 获取被连接网络的mac地址
                String str = "WIFI:" + wifiName + " WiFiIP:" + ip + " MAC:" + mac;
                LogHelper.releaseLog(TAG, "getNetworkInfo str:" + str);
                discover(ip);// 发送arp请求
            }
        } catch (Exception e) {
            LogHelper.errorLog(TAG, "getNetworkInfo error:" + e.toString());
        }
    }

    /**
     * 获取wifi的ip地址
     * 
     * @param ipaddr
     * @return
     */
    public static String getWifiIPAddress(int ipaddr) {
        LogHelper.releaseLog(TAG, "getWifiIPAddress ipaddr:" + ipaddr);
        String ip = "";
        if (0 == ipaddr) {
            return ip;
        }
        byte[] addressBytes = { (byte) (0xff & ipaddr), (byte) (0xff & (ipaddr >> 8)), (byte) (0xff & (ipaddr >> 16)), (byte) (0xff & (ipaddr >> 24)) };
        try {
            ip = InetAddress.getByAddress(addressBytes).toString();
            if (ip.length() > 1) {
                ip = ip.substring(1, ip.length());
            } else {
                ip = "";
            }
        } catch (Exception e) {
            ip = "";
            LogHelper.errorLog(TAG, "getWifiIPAddress error:" + e.toString());
        }
        LogHelper.releaseLog(TAG, "getWifiIPAddress ip:" + ip);
        return ip;
    }

    /**
     * 根据ip网段去发送arp请求
     *
     * @param ip
     */
    public static void discover(String ip) {
        String newip = "";
        if (!ip.equals("")) {
            String ipseg = ip.substring(0, ip.lastIndexOf(".") + 1);
            for (int i = 2; i < 255; i++) {
                newip = ipseg + String.valueOf(i);
                if (newip.equals(ip))
                    continue;
                Thread ut = new UDPThread(newip);
                ut.start();
            }
        }
    }

    /**
     * 读取arp表
     */
    public static void readArp() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line = "";
            String ip = "";
            String flag = "";
            String mac = "";
            while ((line = br.readLine()) != null) {
                try {
                    line = line.trim();
                    if (line.length() < 63) {
                        continue;
                    }
                    if (line.toUpperCase(Locale.US).contains("IP")) {
                        continue;
                    }
                    ip = line.substring(0, 17).trim();
                    flag = line.substring(29, 32).trim();
                    mac = line.substring(41, 63).trim();
                    if (mac.contains("00:00:00:00:00:00")) {
                        continue;
                    }
                    LogHelper.releaseLog(TAG, "readArp: mac= " + mac + " ip= " + ip + " flag= " + flag);

                } catch (Exception e) {
                    LogHelper.errorLog(TAG, "readArp readLine error:" + e.toString());
                }
            }
            br.close();
        } catch (Exception e) {
            LogHelper.errorLog(TAG, "readArp error:" + e.toString());
        }
    }
}
