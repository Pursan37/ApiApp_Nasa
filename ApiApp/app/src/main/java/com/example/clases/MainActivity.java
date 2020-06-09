package com.example.clases;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView mJsonTxtView;
    private ImageView image;
    private TextView title;
    private TextView explain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mJsonTxtView = findViewById(R.id.jsonText);
        title = findViewById(R.id.title);
        explain = findViewById(R.id.explanation);
        title.append("API NASA - Daily Photo");
         image = findViewById(R.id.imageView);
        getPosts();
    }

    private void getPosts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<AstronomyDailyPicture> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<AstronomyDailyPicture>() {
            @Override
            public void onResponse(Call<AstronomyDailyPicture> call, Response<AstronomyDailyPicture> response) {
                if (!response.isSuccessful()){
                    mJsonTxtView.setText("Codigo "+response.code());
                    return ;
                }

                AstronomyDailyPicture post = response.body();

                    String content = "";

                    content += "Copyright:"+ post.getCopyright() + "\n";
                    content += "date:"+ post.getDate() + "\n";
                    content += "Title:"+ post.getTitle() + "\n\n";
                    Picasso.get().load(post.getHdurl()).into(image);
                    mJsonTxtView.append(content);
                    explain.append(post.getExplanation() + "\n");

            }

            @Override
            public void onFailure(Call<AstronomyDailyPicture> call, Throwable t) {
                mJsonTxtView.setText("error "+t.getMessage());

            }
        });

    }
}
