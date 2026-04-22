package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Caculadora extends AppCompatActivity {

    private TextView pantalla;
    private double primerNumero = 0;
    private String operacioPendent = "";
    private boolean novaOperacio = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caculadora);

        pantalla = findViewById(R.id.textViewDisplay);
        Switch switchUnits = findViewById(R.id.switchUnits);

        // --- NÚMEROS ---
        // Programem tots els botons del 0 al 9 d'un sol cop
        int[] idsBotonsNumeros = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9
        };

        View.OnClickListener oclNumeros = v -> {
            Button b = (Button) v;
            afegirNumero(b.getText().toString());
        };

        for (int id : idsBotonsNumeros) {
            findViewById(id).setOnClickListener(oclNumeros);
        }

        // --- OPERACIONS BÀSIQUES ---
        findViewById(R.id.buttonAdd).setOnClickListener(v -> prepararOperacio("+"));
        findViewById(R.id.buttonSub).setOnClickListener(v -> prepararOperacio("-"));
        findViewById(R.id.buttonMult).setOnClickListener(v -> prepararOperacio("*"));
        findViewById(R.id.buttonDiv).setOnClickListener(v -> prepararOperacio("/"));

        // --- IGUAL ---
        findViewById(R.id.buttonEqual).setOnClickListener(v -> calcularResultat());

        // --- NETEJAR (C) ---
        findViewById(R.id.buttonClear).setOnClickListener(v -> {
            pantalla.setText("0");
            primerNumero = 0;
            operacioPendent = "";
            novaOperacio = true;
        });

        // --- TRIGONOMETRIA ---
        findViewById(R.id.buttonSin).setOnClickListener(v -> calcularTrigo("SIN", switchUnits.isChecked()));
        findViewById(R.id.buttonCos).setOnClickListener(v -> calcularTrigo("COS", switchUnits.isChecked()));
        findViewById(R.id.buttonTan).setOnClickListener(v -> calcularTrigo("TAN", switchUnits.isChecked()));
    }

    private void afegirNumero(String numero) {
        if (novaOperacio) {
            pantalla.setText(numero);
            novaOperacio = false;
        } else {
            if (pantalla.getText().toString().equals("0")) {
                pantalla.setText(numero);
            } else {
                pantalla.append(numero);
            }
        }
    }

    private void prepararOperacio(String op) {
        // L'enunciat diu que podem executar d'esquerra a dreta (sense prioritats)
        if (!operacioPendent.isEmpty()) {
            calcularResultat();
        }
        primerNumero = Double.parseDouble(pantalla.getText().toString());
        operacioPendent = op;
        novaOperacio = true;
    }

    private void calcularResultat() {
        if (operacioPendent.isEmpty()) return;

        double segonNumero = Double.parseDouble(pantalla.getText().toString());
        double resultat = 0;

        switch (operacioPendent) {
            case "+": resultat = primerNumero + segonNumero; break;
            case "-": resultat = primerNumero - segonNumero; break;
            case "*": resultat = primerNumero * segonNumero; break;
            case "/": 
                if (segonNumero != 0) resultat = primerNumero / segonNumero;
                else {
                    pantalla.setText("Error");
                    return;
                }
                break;
        }

        pantalla.setText(String.valueOf(resultat));
        operacioPendent = "";
        novaOperacio = true;
    }

    private void calcularTrigo(String funcio, boolean isDeg) {
        double valor = Double.parseDouble(pantalla.getText().toString());
        
        // Si el switch està marcat, convertim graus a radians per a la funció Math
        double valorARadiants = isDeg ? Math.toRadians(valor) : valor;

        double resultat = 0;
        switch (funcio) {
            case "SIN": resultat = Math.sin(valorARadiants); break;
            case "COS": resultat = Math.cos(valorARadiants); break;
            case "TAN": resultat = Math.tan(valorARadiants); break;
        }

        pantalla.setText(String.valueOf(resultat));
        novaOperacio = true;
    }
}