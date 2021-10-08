package com.example.prueba;

import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Card {




    public String name;
    public int number;
    public String date;
    public boolean gold,platinum,signature = false;

    public boolean Error = false;
    public String MessageError;
    public int posError;







    //EJECUTAMOS Validate() PARA VALIDAR LOS DATOS Y GUARDAMOS EL OBJETO, RETORNAMOS LA RESPUESTA
    public String Save(String Name,int Number,String Date,boolean Gold,boolean Platinum,boolean Signature) {




        this.name = Name;
        this.number = Number;
        this.date = Date;
        this.gold = Gold;
        this.platinum = Platinum;
        this.signature = Signature;
        return "Tarjeta Guardada Correctamente";
    }




    // VALIDAMOS TODO LAS COSAS POSIBLES
    public boolean Validate(String Name, int number, String date, boolean gold, boolean platinum, boolean signature) {

        if (String.valueOf(number).length() == 0) {
            this.posError = 0;
            this.Error = true;
            this.MessageError = "Numero de Tarjeta no Ingresado";
            return true;
        }




        if (Name.length() == 0) {
            this.posError = 1;
            this.Error = true;
            this.MessageError = "Nombre no Ingresado";
            return true;
        }


        if (Name.length() == 0) {
            this.posError = 2;
            this.Error = true;
            this.MessageError = "Fecha no Ingresada";
            return true;
        }
        // VALIDACION DE FECHA

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {

            // FECHA DE TARJETA
            Date TarjetaDate = formato.parse(date);
            //FECHAS DE CALCULO
            Calendar Today = Calendar.getInstance(); //NO MENOR
            Date TodayFormat = Today.getTime();

            Calendar Future = Calendar.getInstance(); //NO MAYOR
            Future.add(Calendar.YEAR,6);
            Date FutureFormat = Future.getTime();

            if (TodayFormat.after(TarjetaDate)) {
                this.posError = 2;
                this.Error = true;
                this.MessageError = "Fecha menor a la actual";
                return true;
            }
            Log.d("INFO", String.valueOf(FutureFormat));
            Log.d("INFO", String.valueOf(TarjetaDate));
            if (TarjetaDate.after(FutureFormat)) {
                this.posError = 2;
                this.Error = true;
                this.MessageError = "Fecha mayor a 6 años desde la emision";
                return true;
            }

        } catch (ParseException e) {
            this.posError = 2;
            this.Error = true;
            this.MessageError = "Fecha no Valida";
            return true;
        }
        return false;

    };




}
