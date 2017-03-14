package es.unex.holmel.rcmmchatapp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.unex.holmel.rcmmchatapp.R;

public class MainActivity extends Activity {
    //Elements from User Interface
    @Bind(R.id.dirIP)
    EditText destinationDirIP;
    @Bind(R.id.sourceIP)
    TextView sourceDirIP;
    @Bind(R.id.dirPort)
    EditText portNumber;
    @Bind(R.id.username)
    EditText userName;
    //Variable que comprobara si la dirección es o no una dirección multicast
    private InetAddress inetDestinationAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sourceDirIP.setText(getLocalIpAddress());
        Log.i("RCMM CHAT", "CREATING FORM USER INTERFACE");


    }

    public void login(View v){
        Log.i("RCMM CHAT", "INICIANDO EL PROCESO DE LOGIN");
         String destinationIPString = destinationDirIP.getText().toString();
         String portString = portNumber.getText().toString();
         String username = userName.getText().toString();
        if(destinationIPString.equals("")){
            Log.i("RCMM CHAT", "LA DIRECCIÓN IP DE DESTINO ESTÁ VACIA");
            Toast.makeText(MainActivity.this, "¡SIN DIRECCIÓN IP, NO PODRÁ CHATEAR, RELLENELA!", Toast.LENGTH_SHORT).show();
            destinationDirIP.setText("");
        }else if (portString.equalsIgnoreCase("")){
            Log.i("RCMM CHAT", "EL PUERTO ESTÁ VACÍO");
            Toast.makeText(MainActivity.this, "¡SIN PUERTO, NO PODRÁ CHATEAR, RELLENELA!", Toast.LENGTH_SHORT).show();
            portNumber.setText("");
        }else if(username.equals("")){
            Log.i("RCMM CHAT", "EL NOMBRE DE USUARIO ESTÁ VACÍO");
            Toast.makeText(MainActivity.this, "¡SIN NOMBRE DE USUARIO, NADIE PODRÁ SABER QUIÉN ES, RELLENELO!", Toast.LENGTH_SHORT).show();
            userName.setText("");
        }else{

            int portInt = Integer.valueOf(portString);
            //El puerto 9001 está reservado para el correcto funcionamiento de la aplicación
            if(portInt >= 1024 && portInt <= 49151 && portInt != 9001){//El puerto esta entre los valores aceptados
                Log.i("RCMM CHAT", "EL PUERTO ES CORRECTO, COMPROBACIÓN DE LA DIRECCIÓN IP");
                if(validIP(destinationIPString)){//La dirección IP es una dirección correcta
                    //Se ha comprobado que la dirección IP es correcta y que el puerto es un puerto aceptado
                    //Pasamos a comprobar si la dirección es multicast o no
                    try {
                        inetDestinationAddress = InetAddress.getByName(destinationIPString);
                        Log.i("RCMM CHAT", "LA DIRECCIÓN DE DESTINO ES CORRECTA, SE PROCEDE A COMPROBAR SI ES O NO MULTICAST");
                    }catch (UnknownHostException e){
                        e.printStackTrace();
                        Log.i("RCMM CHAT", "LA DIRECCIÓN IP DESTINO NO CORRESPONDE A NINGÚN HOST ASIGNABLE");
                    }

                    //Comprobamos si la dirección es una dirección multicast o por lo contrario es una conexión p2p
                    if(inetDestinationAddress.isMulticastAddress() == false){
                        Log.i("RCMM CHAT", "----------------------no es multicast");
                        Log.i("RCMM CHAT", "LA CONEXIÓN AL CHAT ES P2P, LANZANDO LA ACTIVIDAD CON LOS VALORES CORRESPONDIENTES");
                        /*
                        *
                        * LA DIRECCIÓN A LA QUE QUEREMOS CONECTARNOS ES UNICAST Y ADEMÁS DE ESO TODOS SUS VALORES SON CORRECTOS
                        * LANZANDO LA ACTIVIDAD DE CHAT PARA CONEXIÓN P2P
                        */
                        Intent i = new Intent(this,ChatActivity.class);
                        Log.i("RCMM CHAT", "ADJUNTANDO AL INTENT LA DIRECCIÓN DE CONEXIÓN DESTINO");
                        i.putExtra("destinationIP", destinationIPString);
                        Log.i("RCMM CHAT", "ADJUNTANDO AL INTENT LA DIRECCIÓN DEL DISPOSITIVO");
                        i.putExtra("sourceIP", sourceDirIP.getText().toString());
                        Log.i("RCMM CHAT", "ADJUNTANDO AL INTENT EL PUERTO DE ESCUCHA");
                        i.putExtra("port", portInt);
                        Log.i("RCMM CHAT", "ADJUNTANDO AL INTENT EL NOMBRE DE USUARIO");
                        i.putExtra("username", username);
                        startActivity(i);

                    }else{
                        Log.i("RCMM CHAT", "LA CONEXIÓN AL CHAT ES MULTICAST, COMPROBANDO QUE ES UNA DIRECCIÓN CORRECTA");
                        if(isValidMulticast(destinationIPString)){
                            Log.i("RCMM CHAT", "LA DIRECCIÓN MULTICAST ESCOGIDA ES CORRECTA, PROCEDIENDO A LA CONEXIÓN CON LOS VALORES DETERMINADOS");
                            /*
                            *
                            * LA DIRECCIÓN A LA QUE QUEREMOS CONECTARNOS ES MULTICAST Y ADEMÁS DE ESO TODOS SUS VALORES SON CORRECTOS
                            * LANZANDO LA ACTIVIDAD DE CHAT PARA CONEXIÓN A GRUPO
                            */
                            Intent i = new Intent(this,ChatActivity.class);
                            Log.i("RCMM CHAT", "ADJUNTANDO AL INTENT LA DIRECCIÓN DE CONEXIÓN DESTINO");
                            i.putExtra("destinationIP", destinationIPString);
                            Log.i("RCMM CHAT", "ADJUNTANDO AL INTENT LA DIRECCIÓN DEL DISPOSITIVO");
                            i.putExtra("sourceIP", sourceDirIP.getText().toString());
                            Log.i("RCMM CHAT", "ADJUNTANDO AL INTENT EL PUERTO DE ESCUCHA");
                            i.putExtra("port", portInt);
                            Log.i("RCMM CHAT", "ADJUNTANDO AL INTENT EL NOMBRE DE USUARIO");
                            i.putExtra("username", username);
                            Log.i("RCMM CHAT", "ADJUNTANDO AL INTENT LA INDICACION DE QUE ES UNA CONEXION MULTICAST");
                            i.putExtra("isMulticast", true);
                            startActivity(i);
                        }else{
                            Toast.makeText(MainActivity.this, "¡HA ESCOGIDO UNA DIRECCIÓN MULTICAST RESERVADA, INTENTE PROBAR CON OTRA!", Toast.LENGTH_SHORT).show();
                            destinationDirIP.setText("");
                        }
                    }


                }else{
                    Log.i("RCMM CHAT", "LA DIRECCIÓN IP DE DESTINO NO ES VÁLIDA");
                    Toast.makeText(MainActivity.this, "¡LA DIRECCION IP QUE HA INTRODUCIDO NO ES CORRECTA, VUELVA A PROBAR!", Toast.LENGTH_SHORT).show();
                    destinationDirIP.setText("");
                }



            }else{
                Log.i("RCMM CHAT", "EL PUERTO NO TIENE UN VALOR ACEPTADO");
                Toast.makeText(MainActivity.this, "DEBE INSERTAR UN VALOR ENTRE 1024 Y 49151 PARA PODER CHATEAR", Toast.LENGTH_SHORT).show();
                portNumber.setText("");
            }
        }
    }

    public void clean(View v){
        destinationDirIP.setText("");
        portNumber.setText("");
        userName.setText("");

    }


    /**
     *
     * @METHOD validIP
     * @DESCRIPTION determina si una dirección IPv4 es válida o no
     * @RETURN TRUE en el caso de que sea válida o FALSE en caso contrario
     *
     * */
    public static boolean validIP(String ip) {
        if (ip == null || ip.isEmpty()) return false;
        ip = ip.trim();
        if ((ip.length() < 6) & (ip.length() > 15)) return false;

        try {
            Pattern pattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
            Matcher matcher = pattern.matcher(ip);
            return matcher.matches();
        } catch (PatternSyntaxException ex) {
            return false;
        }
    }


    /**
     *
     * @METHOD getLocalIpAddress
     * @DESCRIPTION encargado de obtener la dirección IP del dispositivo que lanza la aplicación
     * @RETURN Cadena con la dirección IP del dispositivo desde el que se lanza la aplicación
     *
     * */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    /**
     *
     * @METHOD isValidMulticast
     * @DESCRIPTION determina si una dirección IPv4 multicast es válida o no
     * @RETURN TRUE en el caso de que sea válida o FALSE en caso contrario
     *
     * */
    public static boolean isValidMulticast (String multicastDir){
        //Las direcciones de grupo no validas son:
        //224.0.0.0 --> direccion de red
        //224.0.0.1 --> todos los ordenadores
        //224.0.0.2 --> todos los encaminadores
        //224.0.0.4 --> todos los encaminadores DVMRP
        //224.0.0.5 --> todos los encaminadores OSPF
        //224.0.0.13 --> todos los encaminadores PIM
        if(multicastDir.equals("224.0.0.0") ||
                multicastDir.equals("224.0.0.1") ||
                multicastDir.equals("224.0.0.2") ||
                multicastDir.equals("224.0.0.4") ||
                multicastDir.equals("224.0.0.5") ||
                multicastDir.equals("224.0.0.13")
                ){
            return false;
        }
        else{
            return true;
        }
    }

}
