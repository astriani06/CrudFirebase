package com.example.crudfirebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBCreateActivity extends AppCompatActivity {
    private static final String TAG = "DBCreateActivity";
    private DatabaseReference database;

    private Button btSubmit;
    private EditText etNik;
    private EditText etNama;
    private Spinner etJa;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbcreate);

        Log.d(TAG, "Activity Created");

        etNik = findViewById(R.id.nik);
        etNama = findViewById(R.id.nama_dosen);
        etJa = findViewById(R.id.spinnerJA);
        btSubmit = findViewById(R.id.bt_submit);

        // mengambil referensi ke Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        //Final Update
        final Dosen dosen=(Dosen)getIntent().getSerializableExtra("data");
        if(dosen!=null) {
            //ini untuk update
            etNik.setText(dosen.getNik());
            etNama.setText(dosen.getNama());
            etJa.getSelectedItem().toString();
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dosen.setNik(etNik.getText().toString());
                    dosen.setNama(etNama.getText().toString());
                    dosen.setJa(etJa.getSelectedItem().toString());
                    updateDosen(dosen);
                }
            });
        }
        else {
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isEmpty(etNik.getText().toString()) && !isEmpty(etNama.getText().toString())) {
                        submitDosen(new Dosen(etNik.getText().toString(), etNama.getText().toString(), etJa.getSelectedItem().toString()));
                    } else {
                        Snackbar.make(findViewById(R.id.bt_submit), "Data Dosen tidak boleh kosong", Snackbar.LENGTH_LONG).show();
                    }
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etNama.getWindowToken(), 0);
                }
            });
        }
    }

    private boolean isEmpty(String s) {

        return TextUtils.isEmpty(s);
    }

    private void updateDosen(Dosen dosen) {
        // Update Dosen
        database.child("dosen")
                .child(dosen.getKey())
                .setValue(dosen)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(findViewById(R.id.bt_submit), "Data Berhasil di Update", Snackbar.LENGTH_LONG).setAction("OKE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        }).show();
                    }
                });
    }


    private void submitDosen(Dosen dosen) {
        database.child("dosen").push().setValue(dosen)
                .addOnSuccessListener(this, aVoid -> {
                    etNik.setText("");
                    etNama.setText("");
                    etJa.setSelection(0);
                    Toast.makeText(DBCreateActivity.this, "Data Dosen berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to add data", e);
                    Snackbar.make(findViewById(R.id.bt_submit), "Gagal menambahkan data", Snackbar.LENGTH_LONG).show();
                });
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, DBCreateActivity.class);
}
}