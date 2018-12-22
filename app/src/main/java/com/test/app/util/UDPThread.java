package com.test.app.util;

import android.text.TextUtils;

import com.test.app.helper.LogHelper;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPThread extends Thread {

    private static final String TAG = "UDPThread";

    private String mTargetIp = "";

    public static final byte[] NBREQ = { (byte) 0x82, (byte) 0x28, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x1, (byte) 0x0, (byte) 0x0, (byte) 0x0,
            (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x20, (byte) 0x43, (byte) 0x4B, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41,
            (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41,
            (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41,
            (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x0, (byte) 0x0, (byte) 0x21, (byte) 0x0, (byte) 0x1 };

    public static final short NBUDPP = 137;

    public UDPThread(String targetIp) {
        this.mTargetIp = targetIp;
    }

    @Override
    public synchronized void run() {
        if (TextUtils.isEmpty(mTargetIp)) {
            return;
        }
        DatagramSocket socket = null;
        InetAddress address = null;
        DatagramPacket packet = null;
        try {
            address = InetAddress.getByName(mTargetIp);
            packet = new DatagramPacket(NBREQ, NBREQ.length, address, NBUDPP);
            socket = new DatagramSocket();
            socket.setSoTimeout(200);
            socket.send(packet);
            socket.close();
        } catch (SocketException e) {
            LogHelper.errorLog(TAG, "UDPThread SocketException error:" + e.toString());
        } catch (UnknownHostException e) {
            LogHelper.errorLog(TAG, "UDPThread UnknownHostException error:" + e.toString());
        } catch (IOException e) {
            LogHelper.errorLog(TAG, "UDPThread IOException error:" + e.toString());
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
