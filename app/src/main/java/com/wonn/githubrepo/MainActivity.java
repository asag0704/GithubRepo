package com.wonn.githubrepo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wonn.githubrepo.adapter.RepositoryAdapter;
import com.wonn.githubrepo.model.Repository;
import com.wonn.githubrepo.model.UserInfo;
import com.wonn.githubrepo.network.RetrofitClient;
import com.wonn.githubrepo.network.RetrofitService;

import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DeepLink";
    private static final String DEFAULT_URL = "testapp://repos/";
    RecyclerView rv_main_repos;
    RepositoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction()) && intent.getDataString() != null) {
            // get parameter without default url
            String userName = intent.getDataString().replace(DEFAULT_URL, "");
            if (userName.length() == 0) {
                getUserInfo("jakewharton");
            } else {
                Log.d(TAG, "parameter: " + userName);
                getUserInfo(userName);
            }
        } else {
            getUserInfo("jakewharton");
        }

    }

    private void init() {
        rv_main_repos = findViewById(R.id.rv_main_repos);

        rv_main_repos.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rv_main_repos.setHasFixedSize(false);
    }

    private void getUserInfo(final String username) {
        RetrofitService service = RetrofitClient.createService(RetrofitService.class);
        Call<JsonObject> userInfo = service.userInfo(username);
        userInfo.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonElement element = new JsonParser().parse(response.body().toString())
                            .getAsJsonObject();
                    UserInfo user = new Gson().fromJson(element, UserInfo.class);

                    adapter = new RepositoryAdapter(user);
                    rv_main_repos.setAdapter(adapter);
                    adapter.notifyAdapter();
                    getRepos(username);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRepos(String username) {
        RetrofitService service = RetrofitClient.createService(RetrofitService.class);
        Call<JsonArray> repos = service.repos(username);
        repos.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        for (JsonElement element: response.body()) {
                            Repository repository = new Gson().fromJson(element.getAsJsonObject(), Repository.class);
                            adapter.addItem(repository);
                            Log.d("Recyclerview", "getRepo: " + repository.getName());
                        }
                        adapter.sort();
                        adapter.notifyAdapter();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

