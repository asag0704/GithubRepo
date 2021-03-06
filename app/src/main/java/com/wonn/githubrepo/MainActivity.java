package com.wonn.githubrepo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wonn.githubrepo.adapter.ReposAdapter;
import com.wonn.githubrepo.model.UserInfo;
import com.wonn.githubrepo.network.RetrofitClient;
import com.wonn.githubrepo.network.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv_main_repos;
    ReposAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        getUserInfo("asag074");
    }

    private void init() {
        rv_main_repos = findViewById(R.id.rv_main_repos);
        adapter = new ReposAdapter();

        rv_main_repos.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rv_main_repos.setHasFixedSize(false);
        rv_main_repos.setAdapter(adapter);
    }

    private void getUserInfo(String username) {
        RetrofitService service = RetrofitClient.createService(RetrofitService.class);
        Call<JsonObject> userInfo = service.userInfo(username);
        userInfo.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonElement element = new JsonParser().parse(response.body().toString())
                            .getAsJsonObject();
                    UserInfo user = new Gson().fromJson(element, UserInfo.class);

                    adapter.clear();

                    adapter.addItem(user);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void getRepos(String username) {
        RetrofitService service = RetrofitClient.createService(RetrofitService.class);
        Call<JsonObject> repos = service.repos(username);
        repos.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}
