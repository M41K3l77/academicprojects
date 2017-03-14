package com.example.mangel.lectortickets.utils;

import android.media.ExifInterface;

import com.example.mangel.lectortickets.MainActivity;

import java.io.File;
import java.io.IOException;

/**
 * Project LectorTickets
 * Created by M.Angel on 15/04/2016.
 * Clase para manejar la orientacion de la imagen cuando se obtiene la imagen,
 * es importante para el reconocedor de texto en la imagen.
 */
public class ExifUtils {
    // main activity
    MainActivity mainActivity;

    public ExifUtils(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     * Metodo para la reorientación de la imagen.
     * @param imagePath
     * @return rotate es el angulo a rotar la imagen
     */
    public static int getImageOrientation(String imagePath){
        int rotate = 0;
        try {

            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }
}
