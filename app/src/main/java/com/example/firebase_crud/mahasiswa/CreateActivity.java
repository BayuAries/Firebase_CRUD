package com.example.firebase_crud.mahasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebase_crud.R;
import com.example.firebase_crud.model.Mahasiswa;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtName, edtMail;
    private Button btnSave;

    private Mahasiswa mahasiswa;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtName = findViewById(R.id.edtNama);
        edtMail = findViewById(R.id.edtMail);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(this);

        mahasiswa = new Mahasiswa();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnSave){
            saveMahasiswa();
        }
    }

    private void saveMahasiswa() {
        String name = edtName.getText().toString().trim();
        String mail = edtMail.getText().toString().trim();

        boolean isEmptyFields = false;

        if(TextUtils.isEmpty(name)){
            isEmptyFields = true;
            edtName.setError("Field ini tidak boleh kosong");
        }

        if(TextUtils.isEmpty(mail)){
            isEmptyFields = true;
            edtMail.setError("Field ini tidak boleh kosong");
        }

        if (!isEmptyFields){
            Toast.makeText(CreateActivity.this, "Saving Data...", Toast.LENGTH_SHORT).show();

            DatabaseReference dbMahasiswa = mDatabase.child("mahasiswa");

            String id = dbMahasiswa.push().getKey();
            mahasiswa.setId(id);
            mahasiswa.setName(name);
            mahasiswa.setMail(mail);

            //insert data
            dbMahasiswa.child(id).setValue(mahasiswa);

            finish();
        }
    }
}