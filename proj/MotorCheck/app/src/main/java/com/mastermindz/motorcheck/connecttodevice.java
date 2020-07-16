package com.mastermindz.motorcheck;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.engine.RPMCommand;
import com.github.pires.obd.commands.protocol.EchoOffCommand;
import com.github.pires.obd.commands.protocol.HeadersOffCommand;
import com.github.pires.obd.commands.protocol.LineFeedOffCommand;
import com.github.pires.obd.commands.protocol.ObdResetCommand;
import com.github.pires.obd.commands.protocol.SelectProtocolCommand;
import com.github.pires.obd.commands.protocol.TimeoutCommand;
import com.github.pires.obd.enums.ObdProtocols;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


public class connecttodevice extends AppCompatActivity {
    private static final String TAG = "help";
    public static BluetoothAdapter bladp;
    public static BluetoothSocket mmSocket = null;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    ArrayList list = new ArrayList();
    ArrayList hwrlst = new ArrayList();
    ArrayList err = new ArrayList();
    private BluetoothDevice dev;
    private static BluetoothSocket tmp = null;
    private InputStream rx = null;
    private OutputStream tx = null;
    int ix, att = 0;
    private ProgressBar pb;
    TextView t1, t2, t3, t4;
    private boolean fault_flag = false;
    private boolean at_flag = false;
    private boolean not_conn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecttodevice);
        ix = getIntent().getIntExtra("ListIndex", 0);
        t1 = (TextView) findViewById(R.id.attid);
        t2 = (TextView) findViewById(R.id.statusid);
        t3 = (TextView) findViewById(R.id.atid);
        t4 = (TextView) findViewById(R.id.tid);
        pb = (ProgressBar) findViewById(R.id.progressBar);


        //get bluetooth adapter
        final BluetoothAdapter bladp = BluetoothAdapter.getDefaultAdapter();
        //get paired devices
        final Set<BluetoothDevice> pairedDevices = bladp.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();
                // MAC address
                list.add(deviceName + "\n" + deviceHardwareAddress);
                hwrlst.add(device);

            }
            //Initiating background thread for bluetooth comms attempt
            dev = bladp.getRemoteDevice((hwrlst.get(ix)).toString());
            try {
                tmp = dev.createRfcommSocketToServiceRecord(MY_UUID);
                Method m = dev.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                tmp = (BluetoothSocket) m.invoke(dev, 1);
            } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                Log.e(TAG, "create() failed", e);
                Log.d(TAG, "failed");
            }
            mmSocket = tmp;
        }

        //send request to bt
        try {
            mmSocket.connect();
            Log.d(TAG, "connected!");
            t3.setText("Waiting for AT Commands");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "conn failed");
            Toast.makeText(getApplicationContext(), "Connection failed...Please try to check bluetooth connection", Toast.LENGTH_LONG).show();
            t2.setText("Status: Connection Failed");
            fault_flag = true;
        }
        if (not_conn)
            pb.setVisibility(View.GONE);
        if (!fault_flag) {
            not_conn = true;
            Thread bt_thread = new Thread() {
                @Override
                public void run() {
                    try {
                        while (rx == null && tx == null) {

                            rx = tmp.getInputStream();
                            tx = tmp.getOutputStream();
                            sleep(100);
                            att = att + 1;
                            Log.d(TAG, "Attempt value:" + att);
                            Log.d(TAG, "InputStream=" + rx);
                            Log.d(TAG, "OutputStream=" + tx);
                            t1.setText("Attempt: " + Integer.toString(att));
                            t2.setText("Status: Trying to connect...");
                            if (rx != null && tx != null) {
                                pb.setVisibility(View.GONE);
                                t2.setText("Status: Connected");
                                break;

                            }
                            if (att == 5) {
                                break;
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            bt_thread.start();
            //Initializing AT Commands
            try {
                new ObdResetCommand().run(mmSocket.getInputStream(), mmSocket.getOutputStream());
                new EchoOffCommand().run(mmSocket.getInputStream(), mmSocket.getOutputStream());
                new HeadersOffCommand().run(mmSocket.getInputStream(), mmSocket.getOutputStream());
                new LineFeedOffCommand().run(mmSocket.getInputStream(), mmSocket.getOutputStream());
                new TimeoutCommand(10).run(mmSocket.getInputStream(), mmSocket.getOutputStream());
                new SelectProtocolCommand(ObdProtocols.ISO_9141_2).run(mmSocket.getInputStream(),
                        mmSocket.getOutputStream());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
                //Attempting to get RPM Values
            /*
                RPM_Recalc rc = new RPM_Recalc();
                try {
                    rc.run(mmSocket.getInputStream(),mmSocket.getOutputStream());
                    t4.setText(rc.getCalculatedResult());
                    Log.d(TAG,"reached in custom method");
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

             */
                RPMCommand rx = new RPMCommand();
            try {
                rx.run(mmSocket.getInputStream(),mmSocket.getOutputStream());
                t3.setText(rx.getCalculatedResult());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                mmSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        }
    }
class RPM_Recalc extends RPMCommand {
    private int rpm,rpm_x;

    @Override
    protected void performCalculations(){
        rpm_x = (buffer.get(3)*256+buffer.get(4))/4;

    }
    @Override
    public String getCalculatedResult(){
        return String.valueOf(rpm_x);
    }
}
















