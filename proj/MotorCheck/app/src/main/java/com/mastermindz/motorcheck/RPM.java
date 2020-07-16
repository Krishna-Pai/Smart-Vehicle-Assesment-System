package com.mastermindz.motorcheck;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class RPM extends Thread{
    private final BluetoothSocket mmSocket;
    private final InputStream ix;
    private final OutputStream ox;
    private byte[] buffer = new byte[1024];
    int bytes;
    private String TAG="help";

    public RPM(BluetoothSocket mmSocket) throws IOException {

        this.mmSocket=mmSocket;
        InputStream inp = null;
        OutputStream out = null;
        inp = mmSocket.getInputStream();
        out = mmSocket.getOutputStream();
        ix=inp;
        ox=out;
    }
    public void run()
    {
        byte[] buffer = new byte[1024];
        int bytes;

    }


}
