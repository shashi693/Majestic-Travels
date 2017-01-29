package com.sukenkar.majestictravels;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Context context;

    private EditText mOrigin;
    private EditText mDestination;
    private EditText mPhone;
    private DatabaseReference mDatabase;
    private FirebaseDatabase myRef;
    private Enquiry enquiry;
    private ImageView mButton;
//    private ImageView rButton;
    private TextView rTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOrigin = (EditText) findViewById(R.id.m_origin);
        mDestination = (EditText) findViewById(R.id.m_destination);
        mPhone = (EditText) findViewById(R.id.m_phone);

        mDatabase = FirebaseDatabase.getInstance().getReference("Notification");

        mButton = (ImageView)findViewById(R.id.m_button);

//        rButton = (ImageView)findViewById(R.id.r_button);

        rTextView = (TextView) findViewById(R.id.rtextView);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enquiry = new Enquiry(mOrigin.getText().toString(),mDestination.getText().toString(),mPhone.getText().toString());

                mDatabase.push().setValue(enquiry);

                mOrigin.getText().clear();
                mDestination.getText().clear();
                mPhone.getText().clear();
            }
        });

//        rButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDatabase.addValueEventListener(new ValueEventListener(){
//
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        String value = dataSnapshot.getValue(String.class);
//                        rTextView.setText(value);
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//
//            }
//        });


        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]



        ImageView logTokenButton = (ImageView) findViewById(R.id.logTokenButton);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get token
                String token = FirebaseInstanceId.getInstance().getToken();

                // Log and toast
                String msg = getString(R.string.msg_token_fmt, token);
                Log.d(TAG, msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });


//        ConnectivityManager cm = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        if (null != activeNetwork) {
//            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
//                // here the network changed to Wifi so I can send a heartbeat to GCM to keep connection
//
//                if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
//            //here the network changed to mobileData so I can send a heartbeat to GCM to keep connection
//        }
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();


    }
}
