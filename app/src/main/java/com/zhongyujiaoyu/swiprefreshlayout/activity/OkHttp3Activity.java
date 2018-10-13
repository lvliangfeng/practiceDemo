package com.zhongyujiaoyu.swiprefreshlayout.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zhongyujiaoyu.swiprefreshlayout.R;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;

public class OkHttp3Activity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "OkHttp3Activity";
    MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http3);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_get).setOnClickListener(this);
        findViewById(R.id.btn_post_string).setOnClickListener(this);
        findViewById(R.id.btn_post_stream).setOnClickListener(this);
        findViewById(R.id.btn_post_file).setOnClickListener(this);
        findViewById(R.id.btn_post_form).setOnClickListener(this);
        findViewById(R.id.btn_interceptor).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                okhttpGet();
                break;
            case R.id.btn_post_string:
                okhttpPostString();
                break;
            case R.id.btn_post_stream:
                okhttpPostStream();
                break;
            case R.id.btn_post_file:
                okhttpPostFile();
                break;
            case R.id.btn_post_form:
                okhttpPostForm();
                break;
            case R.id.btn_interceptor:
                okhttpInterceptor();
                break;
        }
    }

    private void okhttpInterceptor() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingIntercepor())
                .build();
        Request request = new Request.Builder()
                .url("http://www.publicobject.com/helloworld.txt")
                .header("User-Agent","OkHttp Example")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + Log.getStackTraceString(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    Log.d(TAG, "onResponse: " + response.body().string());
                    body.close();
                }
            }
        });
    }

    private void okhttpPostForm() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("search", "Jurassic Park")
                .build();
        Request request = new Request.Builder()
                .url("https://en.wikipedia.org/w/index.php")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + Log.getStackTraceString(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }

    private void okhttpPostFile() {
        OkHttpClient okHttpClient = new OkHttpClient();
        File file = new File("test.md");
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mediaType, file))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + Log.getStackTraceString(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });

    }

    private void okhttpPostStream() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MediaType.parse("text/x-markdown; charset=utf-8");
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("I am llf");
            }
        };
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + Log.getStackTraceString(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }

    private void okhttpPostString() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mediaType, "I am Jdqm."))
//                .post(RequestBody.create(mediaType,new File("")))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + Log.getStackTraceString(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }

    private void okhttpGet() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
//        call.execute(); 为同步 需放在子线程
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + Log.getStackTraceString(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: response.body()" + response.body().string()
                        + "\n response.message()" + response.message());
            }
        });

    }

    private class LoggingIntercepor implements Interceptor {

        private static final String TAG = "LoggingIntercepor";

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            long startTime = System.nanoTime();
            Log.d(TAG, String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);
            long endTime = System.nanoTime();
            Log.d(TAG, String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (endTime - startTime) / 1e6d, response.headers()));
            return response;
        }
    }
}
