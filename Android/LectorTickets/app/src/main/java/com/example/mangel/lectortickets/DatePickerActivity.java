package com.example.mangel.lectortickets;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Project LectorTickets
 * Created by M.Angel.
 * Activity para el selector de fecha del ticket.
 */
public class DatePickerActivity extends AppCompatActivity {

    // fecha resultado
    private String resultDate;
    // selector de fecha
    private DatePicker datePicker;
    // formatos de fechas
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
    private SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
    private SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        // se usa en caso de error en algun parametro pasado de la fecha
        Date date= new Date();
        this.resultDate="no date";
        this.datePicker= (DatePicker) findViewById(R.id.datePicker);
        Intent intent = getIntent();
        // dateYearDefault viene de TicketRecognitionActivity que es la del ticket
        // en caso de problemas cogeria Date date= new Date(); que es el segundo parametro
        int dateYear = intent.getIntExtra("dateYearDefault",Integer.parseInt(this.yearFormatter.format(date)));
        // dateMonthDefault viene de TicketRecognitionActivity que es la del ticket
        // en caso de problemas cogeria Date date= new Date(); que es el segundo parametro
        int dateMonth = intent.getIntExtra("dateMonthDefault",Integer.parseInt(this.monthFormatter.format(date)));
        // dateDayDefault viene de TicketRecognitionActivity que es la del ticket
        // en caso de problemas cogeria Date date= new Date(); que es el segundo parametro
        int dateDay = intent.getIntExtra("dateDayDefault",Integer.parseInt(this.dayFormatter.format(date)));
        // el ultimo parametro es null por que no hay escuchador. Tener en
        // cuenta 0 enero, 1 febrero...
        // Se inicializa el picker a la fecha que venga de la activity llamadora
        this.datePicker.init(dateYear, dateMonth, dateDay, null);
    }

    /**
     * metodo para la confirmacion de la seleccion de la fecha.
     * @param v
     */
    public void okDateOnclick (View v){
        Intent returnIntent = new Intent();
        Calendar calendar = Calendar.getInstance();
        calendar.set(this.datePicker.getYear(),this.datePicker.getMonth(),this.datePicker.getDayOfMonth());
        // fecha del ticket en String
        String auxResultDate=dateFormatter.format(calendar.getTime());

        // fecha actual en Date y String
        String nowDate=this.dateFormatter.format(new Date());
        Date dateNow=null;
        try {
            dateNow=this.dateFormatter.parse(nowDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // fecha del ticket
        Date fechaDelTicket=null;
        try {
            fechaDelTicket=this.dateFormatter.parse(auxResultDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // se comprueba que la fecha elegida no es superior a la fecha actual
        // ya que no se puede enviar un ticket con una fecha suerior a la actual
        // en caso de que la fecha actual sea superior a la actual se devuelve la actual.
        if(dateNow != null && fechaDelTicket!=null){
            if(fechaDelTicket.getTime() <= dateNow.getTime()){
                resultDate = dateFormatter.format(calendar.getTime());
            }else{
                resultDate = dateFormatter.format(new Date());
            }
        }else{
            resultDate = dateFormatter.format(new Date());
        }
        // se devuelve fecha del ticket
        returnIntent.putExtra("resultDate",resultDate);

        setResult(Activity.RESULT_OK,returnIntent);
        this.finish();
    }

    /**
     * metodo para cuando se cancela la seleccion de la fecha del ticket.
     * @param v
     */
    public void cancelDateOnclick (View v){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);
        this.finish();
    }
}
