package com.wonn.githubrepo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.wonn.githubrepo.adapter.ReposAdapter;
import com.wonn.githubrepo.model.Repository;
import com.wonn.githubrepo.model.UserInfo;
import com.wonn.githubrepo.network.RetrofitClient;
import com.wonn.githubrepo.network.RetrofitService;

import java.lang.reflect.Type;
import java.util.List;

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

        getUserInfo("asag0704");
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

                    adapter = new ReposAdapter(user);
                    rv_main_repos.setAdapter(adapter);
                    adapter.notifyAdapter();
                    getRepos(username);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

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
                        adapter.notifyAdapter();
                    }
//                    for (int i = 0; i < response.body().size(); i++) {
//                        adapter.addItem(new Repository(
//                                response.body().get(i).getAsJsonObject().get("name").getAsString(),
//                                response.body().get(i).getAsJsonObject().get("description").getAsString(),
//                                12
//                        ));
//                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
    }
}

