package com.example.mangel.lectortickets;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project LectorTickets
 * Created by M.Angel.
 * Activity para el selector de la hora del ticket.
 */
public class TimePickerActivity extends AppCompatActivity {

    // hora resultado
    private String resultTime;
    // selector de hora
    private TimePicker timePicker;
    // formatos de hora y minutos
    private SimpleDateFormat hourFormatter;
    private SimpleDateFormat minuteFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        this.hourFormatter = new SimpleDateFormat("HH");
        this.minuteFormatter = new SimpleDateFormat("mm");
        this.resultTime="no date";
        this.timePicker= (TimePicker) findViewById(R.id.timePicker);
        // se usa forma 0-23 horas en la vista
        this.timePicker.setIs24HourView(true);
        // se usa en caso de error en algun parametro pasado de la fecha
        Date date=new Date();
        Intent intent = getIntent();
        // timeHourDefault hora que viene TicketRecognitionActivity, es la del ticket
        int timeHour = intent.getIntExtra("timeHourDefault",Integer.parseInt(this.hourFormatter.format(date)));
        // timeMinuteDefault hora que viene TicketRecognitionActivity, es la del ticket
        int timeMinute = intent.getIntExtra("timeMinuteDefault",Integer.parseInt(this.minuteFormatter.format(date)));
        // Se inicializa el picker a la hora y minuto pasado por la activity llamadora (TicketRecognitionActivity)
        this.timePicker.setCurrentHour(timeHour);
        this.timePicker.setCurrentMinute(timeMinute);
    }

    /**
     * metodo para la confirmacion de la hora (y minutos) seleccionado. Se controla que hora y/o
     * minutos solo tengan un digito y en tal caso se añade "0" delante.
     * @param v
     */
    public void okTimeOnclick (View v){
        Intent returnIntent = new Intent();
        // se añade hora
        int hora=this.timePicker.getCurrentHour();
        String horaString=String.valueOf(hora);
        if(hora<10){
            horaString="0"+horaString;
        }
        // se añaden minutos
        int minutos=this.timePicker.getCurrentMinute();
        String minutosString=String.valueOf(minutos);
        if(minutos<10){
            minutosString="0"+minutosString;
        }
        // el picker no tiene para segundos por lo que se añaden (:00 segs) por defecto
        this.resultTime=horaString+":"+minutosString+":00";
        returnIntent.putExtra("resultTime",resultTime);
        // se devuelve hora y segundos del ticket
        setResult(Activity.RESULT_OK,returnIntent);
        this.finish();
    }

    /**
     * metodo para cuando se cancela la seleccion de la hora (y minutos) del ticket.
     * @param v
     */
    public void cancelTimeOnclick (View v){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);
        this.finish();
    }
}
