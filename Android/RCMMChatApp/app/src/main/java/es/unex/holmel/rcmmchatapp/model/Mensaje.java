package es.unex.holmel.rcmmchatapp.model;

import java.io.Serializable;

/**
 * @class Mensaje
 * @description Clase encargada de mantener la información acerca de los mensajes que se van a
 *              intercambiar durante el proceso de envío y recepción
 */
public class Mensaje implements Serializable {
    //Enumerado que permite conocer si el mensaje es una imagen o texto.
    public enum Tipe {TEXT(0),IMAGE(1);
        private int value;

        Tipe(int value){
            this.value = value;
        }
        public int getValue(){
            return this.value;
        }
    };
    //Nombre del usuario que envía o del que se reciben mensajes
    private String userName;
    //En el caso de ser un mensaje de texto, esta variable almacenara su informacion
    private String text;
    //En el caso de ser un mensaje de imagen, esta variable almacenara su informacion
    /**
     * @annotation para permitir su correcto envío y recepción, así como su manejo
     *             los desarroladores de la aplicación decidieron utilizar un array de
     *             byte en lugar de un Bitmap.
     * */
    private byte[] image;
    //Tipo del mensaje, texto o imagen
    private int tipe;

    //Conjunto de constructores, getters y setters de la clase
    public Mensaje(String text, String userName){
        this.image=null;
        this.userName=userName;
        this.text = text;
        this.tipe = Tipe.TEXT.getValue();
    }
    public Mensaje(byte[] image, String userName){
        this.text=null;
        this.userName=userName;
        this.image = image;
        this.tipe = Tipe.IMAGE.getValue();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getTipe() {
        return tipe;
    }

    public void setTipe(int tipe) {
        this.tipe = tipe;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(boolean isUserMessage) {
        this.userName = userName;
    }
}
