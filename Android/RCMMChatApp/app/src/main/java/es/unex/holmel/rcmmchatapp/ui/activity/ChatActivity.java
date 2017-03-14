package es.unex.holmel.rcmmchatapp.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.unex.holmel.rcmmchatapp.R;
import es.unex.holmel.rcmmchatapp.model.Mensaje;
import es.unex.holmel.rcmmchatapp.presenters.OnSocketListener;
import es.unex.holmel.rcmmchatapp.ui.adapter.ListAdapter;

public class ChatActivity extends Activity {
    @Bind(R.id.chat_text_message)
    EditText textToSend;
    @Bind(R.id.chat_title_text)
    TextView chatTitleHeader;
    @Bind(R.id.recycler_view)
    ListView listView;


    //Valores que adjunta el intent
    private String username;
    private String destinationIP;
    private String sourceIP;
    private int listenPort;
    private boolean isMulticast;
    //Valores propios de la clase ChatActivity
    private InetAddress inetDestinationAddress, inetSourceAddress;
    private ListAdapter adapter;
    //
    private Thread listener;
    //
    private DatagramSocket socketP2PSend;
    private int portSocketP2PSend;
    private MulticastSocket socketMulticastSend;
    //
    private static int IMAGE_CAPTURE = 3;
    private static int SELECT_PICTURE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        //Evitamos problemas a la hora de las conexiones mediante uso de UDP
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Obteniendo los valores desde el intent que lanzo el formulario de ingreso a chat
        Intent i = getIntent();
        username = i.getStringExtra("username");
        Log.i("RCMM CHAT", "RECIBIMOS EN EL INTENT EL USUARIO [" + username + "]");
        portSocketP2PSend=9001;
        Log.i("RCMM CHAT", "ENVIAMOS POR EL PUERTO [" + portSocketP2PSend + "]");
        destinationIP = i.getStringExtra("destinationIP");
        Log.i("RCMM CHAT", "RECIBIMOS EN EL INTENT LA DIRECCION DESTINO [" + destinationIP + "]");
        sourceIP = i.getStringExtra("sourceIP");
        Log.i("RCMM CHAT", "RECIBIMOS EN EL INTENT LA DIRECCION ORIGEN [" + sourceIP + "]");
        listenPort = i.getIntExtra("port", 1111);
        Log.i("RCMM CHAT", "RECIBIMOS EN EL INTENT EL PUERTO [" + listenPort + "]");
        isMulticast = i.getBooleanExtra("isMulticast", false);
        Log.i("RCMM CHAT", "RECIBIMOS EN EL INTENT EL INDICADOR DE MULTICAST [" + isMulticast + "]");

        //Creando el adaptador para la lista que mostrará los mensajes
        adapter = new ListAdapter(getApplicationContext(), this.username);

        listView.setDivider(null);
        listView.setDividerHeight(0);

        if (isMulticast == true) {
            chatTitleHeader.setText("CONECTADO AL GRUPO >>>" + destinationIP);
        } else {
            chatTitleHeader.setText("CHAT ENTRE " + sourceIP + "  >>> " + destinationIP);
        }

        try {
            inetDestinationAddress = InetAddress.getByName(destinationIP);
            inetSourceAddress = InetAddress.getByName(sourceIP);
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }
        /*
        *
        *
        * INICIO DE CREACION DE LAS CONEXIONES
        *
        *
        * */
        if(isMulticast== false){
            try {
                socketP2PSend = new DatagramSocket(null);
                socketP2PSend.setReuseAddress(true);
                socketP2PSend.bind(new InetSocketAddress(this.portSocketP2PSend));
                Log.i("RCMM CHAT", "CONEXION P2P REALIZADA CORRECTAMENTE");
            } catch (SocketException e) {
                e.printStackTrace();
                Log.i("RCMM CHAT", "FALLO AL REALIZAR LA CONEXION P2P");
            }
        }else{
            try {
                socketMulticastSend=new MulticastSocket();
                Log.i("RCMM CHAT", "CONEXION MULTICAST REALIZADA CORRECTAMENTE");
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("RCMM CHAT", "FALLO AL REALIZAR LA CONEXION MULTICAST");
            }
            try {
                socketMulticastSend.joinGroup(inetDestinationAddress);
                Log.i("RCMM CHAT", "CONEXION A GRUPO MULTICAST REALIZADA CORRECTAMENTE");
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("RCMM CHAT", "FALLO AL REALIZAR LA CONEXION AL GRUPO MULTICAST");
            }
        }
        /*
        * FIN DE CREACION DE LAS CONEXIONES, ASOCIANDO EL ADAPTADOR A LA LISTA Y LANZANDO EL
        * ESCUCHADOR QUE PERMITE RECIBIR MENSAJES
        * */
        listView.setAdapter(adapter);
        listener = new Thread(new OnSocketListener(this.listenPort, this.isMulticast, inetDestinationAddress, adapter, username));
        listener.setDaemon(true);
        listener.start();
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        listener.getThreadGroup().interrupt();
        super.onDestroy();
    }


    public void sendTextMessage(View v) {
        String message = textToSend.getText().toString();
        if(message.equals("")){
            Toast.makeText(ChatActivity.this, "¡RELLENE EL TEXTO PARA ENVIAR UN MENSAJE!", Toast.LENGTH_SHORT).show();
        }else{
            Log.i("RCMM Chatactivity", "comprobacion username " +this.username);
            Mensaje mensaje=new Mensaje(message, this.username);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(5000);
            ObjectOutputStream os = null;
            try {
                os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
                os.flush();
                os.writeObject(mensaje);
                os.flush();

                byte[] sendBuf = byteStream.toByteArray();
                //
                DatagramPacket sendedPacket = new DatagramPacket(
                        sendBuf, sendBuf.length,this.inetDestinationAddress,this.listenPort);
                if(isMulticast==false){
                    try {
                        socketP2PSend.send(sendedPacket);
                        Log.i("RCMM CHAT","MENSAJE DE TEXTO ENVIADO CORRECTAMENTE al puerto de recepcion del otro "+this.listenPort);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i("RCMM CHAT", "FALLO AL ENVIAR EL MENSAJE DE TEXTO");
                    }
                    adapter.add(new Mensaje(message, this.username));
                    textToSend.setText("");
                    InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                else{
                    try {
                        socketMulticastSend.send(sendedPacket);
                        Log.i("RCMM CHAT","MENSAJE DE TEXTO ENVIADO CORRECTAMENTE al puerto de recepcion del otro "+this.listenPort);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i("RCMM CHAT", "FALLO AL ENVIAR EL MENSAJE DE TEXTO");
                    }
                    adapter.add(new Mensaje(message, this.username));
                    textToSend.setText("");
                    InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
    public void attachImage (View v){
        Log.i("RCMM CHAT", "COMIENZA LA SELECCION DE LA IMAGEN");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] options = {"GALERIA","CAMARA"};
        builder.setTitle("Subiendo Imagen...");
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.gallery);
        builder.setItems(options,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (options[which] == "GALERIA") {
                            Log.i("RCMM CHAT", "ESCOGEMOS UNA IMAGEN DE LA GALERIA");
                            Intent pictureActionIntent = null;

                            pictureActionIntent = new Intent(
                                    Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(
                                    pictureActionIntent,
                                    SELECT_PICTURE);

                        } else if (options[which] == "CAMARA") {
                            Log.i("RCMM CHAT", "ESCOGEMOS UNA IMAGEN DE LA CAMARA");
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment
                                    .getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(f));

                            startActivityForResult(intent,
                                    IMAGE_CAPTURE);
                        }

                    }
                });

        builder.create();
        builder.show();
        Log.i("RCMM CHAT", "MOSTRAMOS EL DIALOG EN PANTALLA PARA QUE EL USUARIO DECIDA");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("CHAT", "MANEJANDO EL ENVIO DE LAS IMAGENES");
        File gallery_image ;
        FileInputStream fileInputStream;
        final Bitmap bitMap;

        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE){//La imagen proviene de la galería
            //Obtenemos la imagen desde la galería
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            //Imagen obtenida correctamente, pasamos a reescalarla para enviarla sin necesidad de hacer fragmentación
            Bitmap auxImage=BitmapFactory.decodeFile(picturePath);
            //Bitmap resizedBitmap= Bitmap.createScaledBitmap(auxImage, 300, 300, false);
            Bitmap resizedBitmap=this.resizeImage(auxImage);

            ByteArrayOutputStream fOut= new ByteArrayOutputStream();
            //Pasamos la imagen a flujo para enviarlo como objeto
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            if (fOut != null) {
                try {
                    fOut.flush();
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            gallery_image = new File(picturePath);
            // Creación y envío del paquete que contiene la imagen
            byte[] bFile =fOut.toByteArray();
            Log.i("ChatActivity imagen", "PATH "+gallery_image.getPath());
            Log.i("ChatActivity imagen", "long imagen "+gallery_image.length());

            Mensaje mensaje=new Mensaje(bFile, this.username);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(5000);
            ObjectOutputStream os = null;
            try {
                os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
                os.flush();
                os.writeObject(mensaje);
                os.flush();
                byte[] sendBuf = byteStream.toByteArray();

                DatagramPacket sendedPacket = new DatagramPacket(sendBuf, sendBuf.length, this.inetDestinationAddress,
                        this.listenPort);
                //Mandando imagen
                if(isMulticast == false){

                    try {
                        socketP2PSend.send(sendedPacket);
                        Log.i("RCMM CHAT", "IMAGEN ENVIADA CORRECTAMENTE");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i("RCMM CHAT", "FALLO AL ENVIAR LA IMAGEN DESDE LA GALERIA");
                    }

                }else{
                    try {
                        socketMulticastSend.send(sendedPacket);
                        Log.i("RCMM CHAT", "IMAGEN ENVIADA CORRECTAMENTE");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i("RCMM CHAT", "FALLO AL ENVIAR LA IMAGEN DESDE LA GALERIA");
                    }
                }
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            adapter.add(mensaje);
            listView.smoothScrollToPosition(adapter.getCount() - 1);
        }//FIN DE LA OBTENCIÓN DE LA IMAGEN DESDE LA GALERÍA
        else if ( resultCode == RESULT_OK && requestCode == IMAGE_CAPTURE){//Imagen proviene de la camara
            //Obtenemos la imagen desde la camara
            File f = new File(Environment.getExternalStorageDirectory().toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    File photo = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                    break;
                }

            }//Obtenemos la foto de la cámara y la almacenamos en un fichero.
            try {
                Bitmap bitmap;
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                        bitmapOptions);
                //Escalamos la imagen para enviarla
                //bitmap = Bitmap.createScaledBitmap(bitmap,300,300,true);
                bitmap =this.resizeImage(bitmap);
                String path = android.os.Environment
                        .getExternalStorageDirectory()
                        + File.separator
                        + "Phoenix" + File.separator + "default";

                f.delete();

                ByteArrayOutputStream fOut= new ByteArrayOutputStream();
                File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");

                try {
                    fOut = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Componiendo la imagen para enviarla
                byte[] bFile = fOut.toByteArray();
                Log.i("ChatActivity imagen", "Imagen de la camara convertirda a byte[] ");

                Mensaje mensaje=new Mensaje(bFile, this.username);
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream(5000);
                ObjectOutputStream os = null;

                try {
                    os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
                    os.flush();
                    os.writeObject(mensaje);
                    os.flush();
                    byte[] sendBuf = byteStream.toByteArray();

                    DatagramPacket sendedPacket = new DatagramPacket(sendBuf, sendBuf.length, this.inetDestinationAddress,
                            this.listenPort);
                    //Mandando imagen
                    if(isMulticast == false){
                        try {
                            socketP2PSend.send(sendedPacket);
                            Log.i("RCMM CHAT", "IMAGEN ENVIADA CORRECTAMENTE");
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.i("RCMM CHAT", "FALLO AL ENVIAR LA IMAGEN DESDE LA CAMARA");
                        }
                    }else{
                        try {
                            socketMulticastSend.send(sendedPacket);
                            Log.i("RCMM CHAT", "IMAGEN ENVIADA CORRECTAMENTE");
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.i("RCMM CHAT", "FALLO AL ENVIAR LA IMAGEN DESDE LA CAMARA");
                        }
                    }
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                adapter.add(mensaje);
                listView.smoothScrollToPosition(adapter.getCount() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }//Fin del envio de imagenes desde la camara
    }

    public Bitmap resizeImage(Bitmap image){
        int y=image.getHeight();
        int x=image.getWidth();
        int ratio=1;
        if(x>300 || y>300){
            if(x>y){
                ratio=x/300;
                x=300;
                y=y/ratio;
            }else if (x<y){
                ratio=y/300;
                y=300;
                x=x/ratio;
            }

            image = Bitmap.createScaledBitmap(image,x,y,false);
        }
        return image;
    }
}
