package com.company.todolist;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class FileHelper {

    public static final String fileName = "listInfo.dat"; // arquivo que será salvo na memória

    //metodo para gravar dados no arquivo
    public static void cadastrar(ArrayList<String> item, Context context){
        try {
            // cria o arquivo na memória e abre, leva o nome do arquivo e o modo de exibição
            FileOutputStream fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            ObjectOutputStream oas = new ObjectOutputStream(fos);
            oas.writeObject(item); // grava o item no arquivo
            oas.close(); // fecha arquivo para não ocupar memória
        } catch (FileNotFoundException e){ // captura FileOutputStream
            e.printStackTrace();
        } catch (IOException ex){ // captura ObjectOutputStream
            ex.printStackTrace();
        }
    }
    // leitura do arquivo
    public static ArrayList<String> listar(Context contex){
        ArrayList<String> itemList = null;
        try {
            FileInputStream fis = contex.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            itemList = (ArrayList<String>)  ois.readObject(); // casting
        } catch (FileNotFoundException e){
            itemList = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        } catch (ClassNotFoundException fe){
            fe.printStackTrace();
        }
        return itemList;
    }
}
