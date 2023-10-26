package com.example.proyecto_nota1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Crud extends AppCompatActivity {
        EditText edtNom, edtCor, edtContra;
        ListView List;

        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_crud);

            edtNom = (EditText) findViewById(R.id.edtNom);
            edtCor = (EditText) findViewById(R.id.edtCor);
            edtContra = (EditText) findViewById(R.id.edtContra);
            List = (ListView) findViewById(R.id.lstList);
        }

        public void onClickAgregar(View view){
            DataHelper dh = new DataHelper(this,"usuarios.db",null,1);
            SQLiteDatabase bd = dh.getWritableDatabase();
            ContentValues reg = new ContentValues();
            reg.put("nom", edtNom.getText().toString());
            reg.put("contra", edtContra.getText().toString());
            reg.put("cor", edtCor.getText().toString());

            long resp = bd.insert("usuarios",null,reg);
            bd.close();
            if (resp==-1){
                Toast.makeText(this,"No se pudo Ingresar",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Dato Ingresado Correctamente",Toast.LENGTH_LONG).show();
            }
            Limpiar();
            CargarLista();
        }

        public void onClickModificar(View view){
            DataHelper dh = new DataHelper(this,"usuarios.db",null,1);
            SQLiteDatabase bd = dh.getWritableDatabase();
            ContentValues reg = new ContentValues();
            reg.put("nom", edtNom.getText().toString());
            reg.put("contra", edtContra.getText().toString());
            reg.put("cor", edtCor.getText().toString());

            long resp  = bd.update("usuarios", reg,"nom=?", new String[]
                    {edtNom.getText().toString()});
            bd.close();
            if(resp==-1){
                Toast.makeText(this,"No se pudo Modificar",
                        Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"Dato Modificado",
                        Toast.LENGTH_LONG).show();
            }
            Limpiar();
            CargarLista();
        }

        public void onClickEliminar(View view){
            DataHelper dh = new DataHelper(this, "usuarios.db",  null, 1);
            SQLiteDatabase bd = dh.getWritableDatabase();
            String bNom = edtNom.getText().toString();
            long resp = bd.delete("usuarios", "nom="+ bNom, null);
            bd.close();
            if(resp==-1){
                Toast.makeText(this, "No se pudo Eliminar",
                        Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Dato Eliminado",
                        Toast.LENGTH_LONG).show();
            }
            Limpiar();
            CargarLista();
        }



        public void Limpiar(){
            edtNom.setText("");
            edtCor.setText("");
            edtContra.setText("");
        }


    public void CargarLista(){
        DataHelper dh = new DataHelper(this, "usuarios.db", null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();
        Cursor c = bd.rawQuery("SELECT id, nom, contra, cor FROM usuarios", null);
        String[] arr = new String[c.getCount()];

        if(c.moveToFirst() == true){
            int i = 0;
            do{
                String linea = "||" + c.getString(0) + "||" + c.getString(1)+
                        "||" + c.getString(2) + "||";
                arr[i] = linea;
                i++;
            }while (c.moveToNext() == true);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_expandable_list_item_1, arr);
            List.setAdapter(adapter);
            bd.close();
        }
    }
}
