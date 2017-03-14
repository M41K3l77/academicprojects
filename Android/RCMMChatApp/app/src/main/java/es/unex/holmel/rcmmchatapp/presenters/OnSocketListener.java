package es.unex.holmel.rcmmchatapp.presenters;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.regex.Pattern;

import es.unex.holmel.rcmmchatapp.model.Mensaje;
import es.unex.holmel.rcmmchatapp.ui.adapter.ListAdapter;

/**
 * Created by darkside on 3/1/16.
 */
public class OnSocketListener implements Runnable {
    private int port;
    public boolean paused;
    private boolean finish;
    private boolean isMulticast;
    //
    private DatagramSocket socketP2PReceive;
    private MulticastSocket socketMulticastReceive;
    //

    private InetAddress groupInetAddress;
    //
    private static final int longBuffer = 65507;
    //
    private DatagramPacket receivedDatagram;
    //
    private ListAdapter recyclerAdapter;
    //
    private boolean isImage;
    //
    private String username;
    //
    private Handler handler = new Handler();
    //
    private Object lock; //Lock para evitar perder los datos del chat al cambiar de app

    public OnSocketListener(int port, boolean isMulticast, InetAddress groupInetAddress, ListAdapter recyclerAdapter, String username){
        this.port = port;
        this.isMulticast = isMulticast;
        this.recyclerAdapter = recyclerAdapter;
        this.groupInetAddress = groupInetAddress;
        this.lock = new Object();
        paused = false;
        finish = false;
        isImage = false;
        this.username = username;
        /*try {
            socketP2PReceive = new DatagramSocket(0);

        } catch (SocketException e) {
            e.printStackTrace();
        }*/
    }
    @Override
    public void run() {

        //Creación del socket que estará escuchando en el puerto ya sea multicast o p2p
        if(isMulticast == false){
            try {
                socketP2PReceive = new DatagramSocket(null);
                socketP2PReceive.setReuseAddress(true);
                socketP2PReceive.bind(new InetSocketAddress(this.port));
                Log.i("RCMM CHAT", "SOCKET DE ESCUCHA EN CONEXION P2P CREADO CORRECTAMENTE "+this.port);
            } catch (SocketException e1) {
                e1.printStackTrace();
                Log.i("RCMM CHAT", "FALLO AL CREAR EL SOCKET DE ESCUCHA EN CONEXION P2P");
            }
        }else{
            try {
                socketMulticastReceive = new MulticastSocket(this.port);
                Log.i("RCMM CHAT", "SOCKET DE ESCUCHA EN CONEXION MULTICAST CREADO CORRECTAMENTE");
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("RCMM CHAT", "FALLO AL CREAR EL SOCKET DE ESCUCHA EN CONEXION MULTICAST");
            }
            try {
                
                socketMulticastReceive.joinGroup(groupInetAddress);
                Log.i("RCMM CHAT", "CONEXION AL GRUPO REALIZADA CORRECTAMENTE");
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("RCMM CHAT", "FALLO DE CONEXIÓN AL GRUPO");
            }
        }
        //Comienza el bucle de escucha en recepción
        while (finish == false){
            byte[] recvBuf = new byte[longBuffer];

            if(isMulticast == false){
                try {
                    receivedDatagram = new DatagramPacket(recvBuf,recvBuf.length);
                    socketP2PReceive.receive(receivedDatagram);
                    int byteCount = receivedDatagram.getLength();
                    ByteArrayInputStream byteStream = new ByteArrayInputStream(recvBuf);
                    ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream));
                    final Mensaje mensaje = (Mensaje)is.readObject();
                    is.close();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                    recyclerAdapter.add(mensaje);
                            }
                        });

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    receivedDatagram = new DatagramPacket(recvBuf,recvBuf.length);

                    socketMulticastReceive.receive(receivedDatagram);

                    int byteCount = receivedDatagram.getLength();
                    ByteArrayInputStream byteStream = new ByteArrayInputStream(recvBuf);
                    ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream));
                    final Mensaje mensaje = (Mensaje)is.readObject();
                    is.close();
                    if(!this.username.equals(mensaje.getUserName())) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                recyclerAdapter.add(mensaje);

                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            Log.i("RCMM CHAT Recivir p2p", "valor salida del bucle recibir "+isMulticast);
            synchronized (lock) {
                while (paused) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        }//Fin del bucle while
        Log.i("RCMM CHAT Recivir p2p", "valor salida fuera del bucle "+isMulticast);
    }


    public void pause() {
        synchronized (lock) {
            paused = true;
        }
    }

    public void resume() {
        synchronized (lock) {
            paused = false;
            lock.notifyAll();
        }
    }

    public void finish() {
        finish = true;
    }

}
