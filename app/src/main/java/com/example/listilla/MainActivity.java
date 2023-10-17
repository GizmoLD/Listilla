package com.example.listilla;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    // Model: Record (intents=puntuació, nom)
    class Record {
        public int intents;
        public String nom;

        public Record(int _intents, String _nom ) {
            intents = _intents;
            nom = _nom;
        }
    }
    // Model = Taula de records: utilitzem ArrayList
    ArrayList<Record> records;

    // ArrayAdapter serà l'intermediari amb la ListView
    ArrayAdapter<Record> adapter;

    //ArrayList<String> nombres = generarNombresAleatorios(15);
    //ArrayList<String> apellidos = generarApellidosAleatorios(15);
    //ArrayList<Integer> numeros = generarNumerosAleatorios(15, 1, 10);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialitzem model
        records = new ArrayList<Record>();
        // Afegim alguns exemples
        records.add( new Record(33,"Manolo") );
        records.add( new Record(12,"Pepe") );
        records.add( new Record(42,"Laura") );



        // Inicialitzem l'ArrayAdapter amb el layout pertinent
        adapter = new ArrayAdapter<Record>( this, R.layout.list_item, records )
        {
            @Override
            public View getView(int pos, View convertView, ViewGroup container)
            {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if( convertView==null ) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }
                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.nom)).setText(getItem(pos).nom);
                ((TextView) convertView.findViewById(R.id.intents)).setText(Integer.toString(getItem(pos).intents));
                ((ImageView) convertView.findViewById(R.id.imageView0)).setImageResource(R.drawable.elden_ring);
                return convertView;
            }

        };

        // busquem la ListView i li endollem el ArrayAdapter
        //ListView lv = (ListView) findViewById(R.id.recordsView);
        ListView lv = (ListView) findViewById(R.id.recordViews);
        lv.setAdapter(adapter);

        // botó per afegir entrades a la ListView
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i< randomNumber(15,20) ;i++) {
                    records.add(new Record(generarNumeroAleatorio(1,10), generarNombreAleatorio()+" "+generarApellidoAleatorio()));
                }
                // notificar l'adapter dels canvis al model
                adapter.notifyDataSetChanged();
            }
        });
    }

    public int randomNumber(int min, int max){
        return ThreadLocalRandom.current().nextInt(min,max);
    }

    public String generarNombreAleatorio() {
        String[] nombres = {"Juan", "María", "Carlos", "Laura", "Pedro", "Ana", "Luis", "Sara", "Diego", "Elena", "Miguel", "Carmen", "Javier", "Isabel", "Pablo"};
        Random rand = new Random();
        return nombres[rand.nextInt(nombres.length)];
    }

    public String generarApellidoAleatorio() {
        String[] apellidos = {"González", "Rodríguez", "López", "Martínez", "Pérez", "Fernández", "Gómez", "Díaz", "Torres", "Ruiz", "Sánchez", "Romero", "Hernández", "Jiménez", "Moreno"};
        Random rand = new Random();
        return apellidos[rand.nextInt(apellidos.length)];
    }

    public int generarNumeroAleatorio(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("El valor mínimo debe ser menor o igual al valor máximo.");
        }
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}