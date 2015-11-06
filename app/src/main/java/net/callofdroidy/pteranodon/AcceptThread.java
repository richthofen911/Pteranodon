package net.callofdroidy.pteranodon;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;

/**
 * Created by admin on 06/11/15.
 */
public class AcceptThread extends Thread{
    private final BluetoothServerSocket bluetoothServerSocket;

    public AcceptThread(BluetoothAdapter mBluetoothAdapter){
        BluetoothServerSocket tmp = null;
        try {
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
        }catch (IOException e){
            Log.e("AcceptThread", "Error: " + e.toString());
        }
        bluetoothServerSocket = tmp;
    }

    public void run(){
        BluetoothSocket bluetoothSocket = null;
        //keep listening until exception occurs or a socket is returned
        while (true){
            try {
                bluetoothSocket = bluetoothServerSocket.accept();
            }catch (IOException e){
                Log.e("accept thread", "error: " + e.toString());
                break;
            }
            // if a connection was accepted
            if(bluetoothSocket != null){
                //do work to manage the connection in a separate thread
                manageConnectedSocket(bluetoothSocket);
                bluetoothServerSocket.close();
                break;
            }
        }
    }
}
