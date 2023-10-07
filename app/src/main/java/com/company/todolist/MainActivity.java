package com.company.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText tfAdd;
    ListView ltAdd;
    ArrayList<String> itemAdd = new ArrayList(); // lista que contém os itens
    ArrayAdapter<String> arrayAdapter; //adaptador para conectar o listview ao Arraylist


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tfAdd = findViewById(R.id.tfAdicionar);
        ltAdd = findViewById(R.id.ltAdicionar);

        //ler os dados e envia para a tela se houver dados
        itemAdd = FileHelper.listar(this);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemAdd);
        ltAdd.setAdapter(arrayAdapter);

        // adicionar dados ao listview ao clicar o botão
        findViewById(R.id.btAdicionar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = tfAdd.getText().toString(); // atribuindo o item ao container
                itemAdd.add(itemName); // adiciona o item do container a lista
                tfAdd.setText(""); //limpar campo de texto
                FileHelper.cadastrar(itemAdd, getApplicationContext()); // salva o novo item no arquivo
                arrayAdapter.notifyDataSetChanged();
            }
        });
        ltAdd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Excluir"); // titulo da dialog
                alert.setMessage("Você deseja excluir esse item da lista"); // mensagem da dialog
                alert.setCancelable(false); //se clicar em algum lugar da tela, não fecha a dialog
                alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel(); // fecha a dialog
                    }
                });
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     itemAdd.remove(position); // remove o item da lista na posição de onde foi clicado
                     arrayAdapter.notifyDataSetChanged(); // notifica o adaptador
                     FileHelper.cadastrar(itemAdd, getApplicationContext()); // salva a lista atualizada
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });
    }
}