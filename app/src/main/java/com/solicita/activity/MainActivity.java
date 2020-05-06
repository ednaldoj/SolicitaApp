package com.solicita.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.solicita.R;
import com.solicita.helper.SharedPrefManager;
import com.solicita.network.ApiClient;
import com.solicita.network.ApiInterface;

public class MainActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;
    TextView tvNome;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefManager = new SharedPrefManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        tvNome = findViewById(R.id.tvNome);

        tvNome.setText(sharedPrefManager.getSPNome());
        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(view -> logoutApp());

    }
    public void logoutApp() {
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_STATUS_LOGIN, false);
        startActivity(new Intent(MainActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
}
