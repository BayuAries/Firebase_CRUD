package com.example.firebase_crud.mahasiswa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebase_crud.R;
import com.example.firebase_crud.model.Mahasiswa;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNama,edtMail;
    private Button btnUpdate;

    public static final String EXTRA_MAHASISWA = "extra_mahasiswa";
    public final int ALERT_DIALOG_CLOSE = 10;
    public final int ALERT_DIALOG_DELET = 20;

    private Mahasiswa mahasiswa;
    private String mahasiswaId;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtNama = findViewById(R.id.edtEditNama);
        edtMail = findViewById(R.id.edtEditMail);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);

        mahasiswa = getIntent().getParcelableExtra(EXTRA_MAHASISWA);

        if (mahasiswa != null){
            mahasiswaId = mahasiswa.getId();
        }else {
            mahasiswa = new Mahasiswa();
        }

        if (mahasiswa != null){
            edtNama.setText(mahasiswa.getName());
            edtMail.setText(mahasiswa.getMail());
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Edit Data");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnUpdate){
            updateMahasiswa();
        }
    }

    private void updateMahasiswa() {
        String name = edtNama.getText().toString().trim();
        String mail = edtMail.getText().toString().trim();

        boolean isEmptyFields = false;

        if(TextUtils.isEmpty(name)){
            isEmptyFields = true;
            edtNama.setError("Field ini tidak boleh kosong");
        }

        if(TextUtils.isEmpty(mail)){
            isEmptyFields = true;
            edtMail.setError("Field ini tidak boleh kosong");
        }

        if(! isEmptyFields){
            Toast.makeText(UpdateActivity.this, "Updating data...", Toast.LENGTH_SHORT).show();

            mahasiswa.setName(name);
            mahasiswa.setMail(mail);

            DatabaseReference dbMahasiswa = mDatabase.child("mahasiswa");

            //update data
            dbMahasiswa.child(mahasiswaId).setValue(mahasiswa);

            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //pilih menu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delet:
                showAlertDialog(ALERT_DIALOG_DELET);
                break;
            case  android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(int type) {
        final  boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMassage;

        if (isDialogClose){
            dialogTitle = "Batal";
            dialogMassage = "Apakah anda yakin ingin membatalkan perubahan pada form ?";
        }else {
            dialogTitle = "Hapus Data";
            dialogMassage = "Apakah anda yakin ingin menghapus item ini?";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMassage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(isDialogClose){
                            finish();
                        }else {
                            //kode hapus data masuk sini
                            DatabaseReference dbMahasiswa = mDatabase.child("mahasiswa").child(mahasiswaId);

                            dbMahasiswa.removeValue();

                            Toast.makeText(UpdateActivity.this, "Deleting data....", Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.cancel();
                //
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}