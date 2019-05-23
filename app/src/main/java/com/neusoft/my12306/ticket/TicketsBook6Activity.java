package com.neusoft.my12306.ticket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.neusoft.my12306.R;
import com.neusoft.my12306.utils.QrCodeUtils;

public class TicketsBook6Activity extends AppCompatActivity {

    private Button btnCreateQr;
    private EditText etQrCodeInfo;
    ImageView im1 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_book6);


        im1 = findViewById(R.id.ivQrCode);

        btnCreateQr = findViewById(R.id.btnCreateQr);
        etQrCodeInfo = findViewById(R.id.etQrCodeInfo);//EditText

        btnCreateQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = etQrCodeInfo.getText().toString();
                QrCodeUtils.setImageView(im1, info, 300, 300);
            }
        });
    }
}


