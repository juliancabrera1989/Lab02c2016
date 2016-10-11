package dam.isi.frsf.edu.ar.lab02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Switch;
import android.widget.ToggleButton;
import android.widget.*;


import java.lang.*;
import java.util.*;
import java.text.*;


public class MainActivity extends AppCompatActivity {

    private ToggleButton toggleBut;

    private Switch switchBut;

    private Spinner spinnerHorario;
    private ArrayAdapter<CharSequence> listAdapterSpinner;


    private TextView textPedido;
    private RadioGroup radioPlato;
    private Button buttonAgregar;
    private Button buttonConfirmar;
    private Button buttonReiniciar;

    private ListView listView1;
    private ArrayAdapter<ElementoMenu> listAdapterListView;

    private ElementoMenu[] listaBebidas;

    private ElementoMenu[] listaPlatos;
    private ElementoMenu[] listaPostre;

    protected ElementoMenu actual;
    protected Double sumaTotal = 0.0000;
    protected boolean pedidoConfirmado = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinnerHorario = (Spinner) findViewById(R.id.spinner);
        listAdapterSpinner = ArrayAdapter.createFromResource(this, R.array.valores_array, android.R.layout.simple_spinner_item);
        listAdapterSpinner.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerHorario.setAdapter(listAdapterSpinner);


        listView1 = (ListView) findViewById(R.id.listView);
        listView1.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        iniciarListas();
        final ArrayList<ElementoMenu> listaActual = new ArrayList<ElementoMenu>();

        radioPlato = (RadioGroup) findViewById(R.id.rBtnGrp);
        radioPlato.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                actual = null;
                listaActual.clear();
                switch (checkedId) {
                    case R.id.radioButton:
                        listaActual.addAll(Arrays.asList(listaPlatos));
                        break;
                    case R.id.radioButton2:
                        listaActual.addAll(Arrays.asList(listaPostre));
                        break;
                    case R.id.radioButton3:
                        listaActual.addAll(Arrays.asList(listaBebidas));
                        break;
                }
                listAdapterListView = new ArrayAdapter<ElementoMenu>(MainActivity.this, android.R.layout.simple_list_item_single_choice, android.R.id.text1, listaActual);
                listView1.setAdapter(listAdapterListView);
            }

        });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                actual = listAdapterListView.getItem(position);
            }

        });

        textPedido = (TextView) findViewById(R.id.textPedido);
        buttonAgregar = (Button) findViewById(R.id.button);
        buttonAgregar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               if (pedidoConfirmado)
                   Toast.makeText(getApplicationContext(), getString(R.string.toast2), Toast.LENGTH_LONG).show();
               else {
                   if (actual == null)
                       Toast.makeText(getApplicationContext(), getString(R.string.toast1), Toast.LENGTH_LONG).show();
                   else {
                       if (textPedido.getText().toString() == getString(R.string.pedido).toString())
                           textPedido.setText("");
                       textPedido.append(actual.toString());
                       sumaTotal += actual.getPrecio();
                       textPedido.append(" \n");
                   }
               }
            }
        });

        buttonConfirmar = (Button) findViewById(R.id.button2);
        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (pedidoConfirmado)
                    Toast.makeText(getApplicationContext(), getString(R.string.toast2), Toast.LENGTH_LONG).show();
               else {
                   if (textPedido.getText().toString() == getString(R.string.pedido).toString())
                       Toast.makeText(getApplicationContext(), getString(R.string.toast3), Toast.LENGTH_LONG).show();
                   else {
                       textPedido.append(sumaTotal.toString());
                       pedidoConfirmado = true;

                   }
               }
            }

        });

        buttonReiniciar = (Button) findViewById(R.id.button3);
        buttonReiniciar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                sumaTotal = 0.0000;
                textPedido.clearComposingText();
                textPedido.setText(getString(R.string.pedido));
                actual = null;
                pedidoConfirmado = false;
                listaActual.clear();
                radioPlato.clearCheck();
            }
        });

    }









    DecimalFormat f = new DecimalFormat("##.00");
    class ElementoMenu {
        private Integer id;
        private String nombre;
        private Double precio;
        public ElementoMenu() {
        }
        public ElementoMenu(Integer i, String n, Double p) {
            this.setId(i);
            this.setNombre(n);
            this.setPrecio(p);
        }
        public ElementoMenu(Integer i, String n) {
            this(i,n,0.0);
            Random r = new Random();
            this.precio= (r.nextInt(3)+1)*((r.nextDouble()*100));
        }
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public String getNombre() {
            return nombre;
        }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        public Double getPrecio() {
            return precio;
        }
        public void setPrecio(Double precio) {
            this.precio = precio;
        }
        @Override
        public String toString() {
            return this.nombre+ "( "+f.format(this.precio)+")";
        }
    }






    private void iniciarListas() {
// inicia lista de bebidas
        listaBebidas = new ElementoMenu[7];
        listaBebidas[0] = new ElementoMenu(1, "Coca");
        listaBebidas[1] = new ElementoMenu(4, "Jugo");
        listaBebidas[2] = new ElementoMenu(6, "Agua");
        listaBebidas[3] = new ElementoMenu(8, "Soda");
        listaBebidas[4] = new ElementoMenu(9, "Fernet");
        listaBebidas[5] = new ElementoMenu(10, "Vino");
        listaBebidas[6] = new ElementoMenu(11, "Cerveza");
// inicia lista de platos
        listaPlatos = new ElementoMenu[14];
        listaPlatos[0] = new ElementoMenu(1, "Ravioles");
        listaPlatos[1] = new ElementoMenu(2, "Gnocchi");
        listaPlatos[2] = new ElementoMenu(3, "Tallarines");
        listaPlatos[3] = new ElementoMenu(4, "Lomo");
        listaPlatos[4] = new ElementoMenu(5, "Entrecot");
        listaPlatos[5] = new ElementoMenu(6, "Pollo");
        listaPlatos[6] = new ElementoMenu(7, "Pechuga");
        listaPlatos[7] = new ElementoMenu(8, "Pizza");
        listaPlatos[8] = new ElementoMenu(9, "Empanadas");
        listaPlatos[9] = new ElementoMenu(10, "Milanesas");
        listaPlatos[10] = new ElementoMenu(11, "Picada 1");
        listaPlatos[11] = new ElementoMenu(12, "Picada 2");
        listaPlatos[12] = new ElementoMenu(13, "Hamburguesa");
        listaPlatos[13] = new ElementoMenu(14, "Calamares");
// inicia lista de postres
        listaPostre = new ElementoMenu[15];
        listaPostre[0] = new ElementoMenu(1, "Helado");
        listaPostre[1] = new ElementoMenu(2, "Ensalada de Frutas");
        listaPostre[2] = new ElementoMenu(3, "Macedonia");
        listaPostre[3] = new ElementoMenu(4, "Brownie");
        listaPostre[4] = new ElementoMenu(5, "Cheescake");
        listaPostre[5] = new ElementoMenu(6, "Tiramisu");
        listaPostre[6] = new ElementoMenu(7, "Mousse");
        listaPostre[7] = new ElementoMenu(8, "Fondue");
        listaPostre[8] = new ElementoMenu(9, "Profiterol");
        listaPostre[9] = new ElementoMenu(10, "Selva Negra");
        listaPostre[10] = new ElementoMenu(11, "Lemon Pie");
        listaPostre[11] = new ElementoMenu(12, "KitKat");
        listaPostre[12] = new ElementoMenu(13, "IceCreamSandwich");
        listaPostre[13] = new ElementoMenu(14, "Frozen Yougurth");
        listaPostre[14] = new ElementoMenu(15, "Queso y Batata");
    }
}
