package com.example.listilla;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> imageNames;  // Lista de nombres de imágenes en res

    class Record {
        public int intents;
        public String nom;
        // Nueva propiedad para almacenar el recurso de imagen
        private int imageResource;

        public Record(int _intents, String _nom) {
            intents = _intents;
            nom = _nom;
        }

        public int getIntents() {
            return intents;
        }

        public void setIntents(int intents) {
            this.intents = intents;
        }

        public int getImageResource() {
            return imageResource;
        }

        public void setImageResource(int imageResource) {
            this.imageResource = imageResource;
        }
    }

    ArrayList<Record> records;
    ArrayAdapter<Record> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageNames = new ArrayList<>();
        imageNames.add("elden_ring");
        imageNames.add("fresa");
        imageNames.add("mango");
        imageNames.add("monster_hunter");
        imageNames.add("image1");

        // Inicialitzem model
        records = new ArrayList<>();
        // Afegim alguns exemples
        records.add(new Record(randomNumber(1,20), generarNombreAleatorio()+" "+generarApellidoAleatorio()));
        records.add(new Record(randomNumber(1,20), generarNombreAleatorio()+" "+generarApellidoAleatorio()));
        records.add(new Record(randomNumber(1,20), generarNombreAleatorio()+" "+generarApellidoAleatorio()));

        // Inicialitzem l'ArrayAdapter amb el layout pertinent
        adapter = new ArrayAdapter<Record>(this, R.layout.list_item, records) {
            @Override
            public View getView(int pos, View convertView, ViewGroup container) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }

                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.nom)).setText(getItem(pos).nom);
                ((TextView) convertView.findViewById(R.id.intents)).setText(Integer.toString(getItem(pos).intents));

                // Verifica si ya se ha asignado una imagen a este elemento
                if (getItem(pos).getImageResource() == 0) {
                    // Si no se ha asignado, elige una imagen aleatoria
                    int imageResourceId = getRandomImageResource();
                    getItem(pos).setImageResource(imageResourceId);
                }

                // Establece la imagen correspondiente
                ((ImageView) convertView.findViewById(R.id.imageView0)).setImageResource(getItem(pos).getImageResource());

                return convertView;
            }
        };

        ListView lv = findViewById(R.id.recordViews);
        lv.setAdapter(adapter);

        // botó per afegir entrades a la ListView
        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < randomNumber(15, 20); i++) {
                    records.add(new Record(randomNumber(1, 20), generarNombreAleatorio() + " " + generarApellidoAleatorio()));
                }
                // notificar l'adapter dels canvis al model
                adapter.notifyDataSetChanged();
            }
        });

        Button b1 = findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordenarRecords();
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void ordenarRecords() {
        Collections.sort(records, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return Integer.compare(o1.getIntents(), o2.getIntents());
            }
        });
    }

    public int randomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
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

    private int getRandomImageResource() {
        Random rand = new Random();
        String randomImageName = imageNames.get(rand.nextInt(imageNames.size()));
        return getResources().getIdentifier(randomImageName, "drawable", getPackageName());
    }
}
