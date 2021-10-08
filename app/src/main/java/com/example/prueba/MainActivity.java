package com.example.prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    ArrayList<Card> Cards = new ArrayList<Card>();

    int Indice = 0;
    TextView dtDate;
    EditText dtName;
    EditText dtNumber;

    RadioButton dtGold,dtPlatinum,dtSignature;

    DatePickerDialog.OnDateSetListener DateLister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //SETEAMOS LOS DATOS
        dtDate = findViewById(R.id.DATE);
        dtName = findViewById(R.id.NAME);
        dtNumber = findViewById(R.id.NUMBER);

        dtGold = findViewById(R.id.Gold);
        dtPlatinum = findViewById(R.id.Platinum);
        dtSignature = findViewById(R.id.Signature);


        //TODO LO DE LA FECHA
        Calendar calendar= Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //TODO LO DE LA FECHA
        dtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dateDialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        DateLister,
                        year,
                        month,
                        day);
                dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dateDialog.show();
            }
        });

        DateLister = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth+ "/" + month + "/" + year;
                dtDate.setText(date);
            }
        };


    }






    public void SaveCard(View view) {

        Card Tarjeta = new Card();
        Tarjeta.Validate(dtName.getText().toString(),Integer.parseInt(dtNumber.getText().toString()),dtDate.getText().toString(),dtGold.isChecked(),dtPlatinum.isChecked(),dtSignature.isChecked());

        if (Tarjeta.Error) {
            ShowError(Tarjeta.posError,Tarjeta.MessageError);
            return;
        }

        Tarjeta.Save(dtName.getText().toString(),Integer.parseInt(dtNumber.getText().toString()),dtDate.getText().toString(),dtGold.isChecked(),dtPlatinum.isChecked(),dtSignature.isChecked());
        for (Card card: Cards) {
            if (card.number == Integer.parseInt( dtNumber.getText().toString())) {
                dtDate.setText(card.date);
                dtName.setText(card.name);

                dtGold.setChecked(card.gold);
                dtPlatinum.setChecked(card.platinum);
                dtSignature.setChecked(card.signature);
                Toast toast1 = Toast.makeText(getApplicationContext(), "TARJETA MODIFICADA", Toast.LENGTH_SHORT);
                toast1.show();
            } else {
                Cards.add(Tarjeta);
                Toast toast1 = Toast.makeText(getApplicationContext(), "TARJETA AGREGADA", Toast.LENGTH_SHORT);
                toast1.show();
            }
        }
        ClearScreen();
    }

    private  void ShowError(int Posicion,String Error) {
        if (Posicion == 0) {
            dtNumber.setError(Error);
        }

        if (Posicion == 1) {
            dtName.setError(Error);
        }

        if (Posicion == 2) {
            dtDate.setError(Error);
        }
    }


    private void ClearScreen () {
        dtDate.setText("");
        dtNumber.setText("");
        dtName.setText("");
        dtGold.setChecked(true);
    }


    public void SearchCard(View view) {
        if (dtNumber.length() == 0) {
            dtNumber.setError("Ingrese Numero de tarjeta");
            return;
        }



        for (Card card: Cards) {
            if (card.number == Integer.parseInt( dtNumber.getText().toString())) {
                dtDate.setText(card.date);
                dtName.setText(card.name);

                dtGold.setChecked(card.gold);
                dtPlatinum.setChecked(card.platinum);
                dtSignature.setChecked(card.signature);
                Toast toast1 = Toast.makeText(getApplicationContext(), "TARJETA ENCONTRADA", Toast.LENGTH_SHORT);
                toast1.show();
            }
        }
    }

    public void DeleteCard(View view) {
        if (dtNumber.length() == 0) {
            dtNumber.setError("Ingrese Numero de tarjeta");
            return;
        }



        for (Card card: Cards) {
            if (card.number == Integer.parseInt( dtNumber.getText().toString())) {
                Cards.remove(card);
            }
        }
    }




    public void NextCard(View view) {
        if (String.valueOf(Cards.get(Indice).number).length() != 0) {
            dtDate.setText(Cards.get(Indice).date);
            dtName.setText(Cards.get(Indice).name);

            dtGold.setChecked(Cards.get(Indice).gold);
            dtPlatinum.setChecked(Cards.get(Indice).platinum);
            dtSignature.setChecked(Cards.get(Indice).signature);
            Indice++;
        }
    }


    public void PrevCard(View view) {
        if (Indice == 0) {
            Toast toast1 = Toast.makeText(getApplicationContext(), "NADA PREVIAMENTE", Toast.LENGTH_SHORT);
            toast1.show();
            return;
        }
        if (String.valueOf(Cards.get(Indice).number).length() != 0) {
            dtDate.setText(Cards.get(Indice).date);
            dtName.setText(Cards.get(Indice).name);

            dtGold.setChecked(Cards.get(Indice).gold);
            dtPlatinum.setChecked(Cards.get(Indice).platinum);
            dtSignature.setChecked(Cards.get(Indice).signature);
            Indice--;
        }

    }
}

