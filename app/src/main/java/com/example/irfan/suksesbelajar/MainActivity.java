package com.example.irfan.suksesbelajar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irfan.suksesbelajar.mDataObject.Owner;
import com.example.irfan.suksesbelajar.mMySQL.Api;
import com.example.irfan.suksesbelajar.utils.BaseApiService;
import com.example.irfan.suksesbelajar.utils.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity{

    RelativeLayout relLay_1,relLay_2;
    TextView tvSuksesBelajar;
    ImageView imgViewLogo;
    Spinner sp;
    Button btnLogin;
    Context mContext;
    EditText edtUsername, edtPassword;
    ProgressDialog pd;
    BaseApiService mApiService;


    Handler handler = new Handler();
    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            relLay_1.setVisibility(View.VISIBLE);
            relLay_2.setVisibility(View.VISIBLE);
            sp.setVisibility(View.VISIBLE);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relLay_1 = (RelativeLayout)findViewById(R.id.relLay1);
        relLay_2 = (RelativeLayout)findViewById(R.id.relLay2);
        imgViewLogo = (ImageView)findViewById(R.id.imgView_logo);
        sp = (Spinner)findViewById(R.id.sp);
        edtUsername = (EditText)findViewById(R.id.edt_username);
        edtPassword = (EditText)findViewById(R.id.edt_password);
        btnLogin = (Button)findViewById(R.id.btn_login);
        mContext = this;
        mApiService = UtilsApi.getAPIService();


        handler.postDelayed(runnable1, 2000);

        //Start of Retrofit Spinner
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Owner>> call = api.listOwner();

        call.enqueue(new Callback<List<Owner>>() {
            @Override
            public void onResponse(Call<List<Owner>> call, Response<List<Owner>> response) {

                List<Owner> owners = response.body();

//                for (Owner o: owners)
//                {
//                    Log.d("id_owner", String.valueOf(o.getIdOwner()));
//                    Log.d("kota", o.getKota());
//                }

                String[] namaKota = new String[owners.size()];
                for (int i = 0;i<owners.size();i++)
                {
                    namaKota[i] = owners.get(i).getKota();
                }

                sp.setAdapter(
                        new ArrayAdapter<String>(
                                getApplicationContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                namaKota
                        )
                );

            }

            @Override
            public void onFailure(Call<List<Owner>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        //END of retrofit spinner
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestLogin();
            }
        });
    }

    private void requestLogin()
    {
        mApiService.loginRequest(edtUsername.getText().toString(), edtPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful())
                        {
                            pd.dismiss();
                            try
                            {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("Response").equals("OK"))
                                {
                                    Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
//                                  String nama = jsonRESULTS.getJSONObject("user").getString("nama");
                                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    String err_message = "Username/Password anda salah";
                                    Toast.makeText(mContext, err_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            } catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            pd.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug","onFailure: ERROR >" + t.toString());
                        pd.dismiss();
                    }
                });
    }
}
