package com.example.yeqinglu.volley_http;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public Context context;

    public RequestQueue mQueue;
    public URL url;

    private Button get_http;
    private Button json_http;
    private Button image_http;
    private ImageView imageView;
    private Button imageLoader_http;
    private ImageView imageLoaderView;
    private Button netwrokImage_http;
    private NetworkImageView networkImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();

        //获取到一个RequestQueue对象
        /*RequestQueue是一个请求队列对象，它可以缓存所有的HTTP请求，
        然后按照一定的算法并发地发出这些请求。RequestQueue内部的设
        计就是非常合适高并发的，因此我们不必为每一次HTTP请求都创建
        一个RequestQueue对象，这是非常浪费资源的，基本上在每一个需
        要和网络交互的Activity中创建一个RequestQueue对象就足够了。*/
        mQueue = Volley.newRequestQueue(context);

        get_http = (Button)findViewById(R.id.get_http);
        json_http = (Button)findViewById(R.id.json_http);
        image_http = (Button)findViewById(R.id.image_http);
        imageView = (ImageView)findViewById(R.id.imageView);
        imageLoader_http = (Button)findViewById(R.id.imageLoader_http);
        imageLoaderView = (ImageView)findViewById(R.id.imageLoaderView);
        netwrokImage_http = (Button)findViewById(R.id.newtworkImage_http);
        networkImageView = (NetworkImageView)findViewById(R.id.networkImageView);
        networkImageView.setErrorImageResId(R.drawable.fail);


        get_http.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHttp();
            }
        });

        json_http.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonHttp();;
            }
        });

        image_http.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageHttp();
            }
        });

        imageLoader_http.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLoader_http();
            }
        });

        netwrokImage_http.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkImage_http();
            }
        });

    }

    public void getHttp() {
        //创建一个StringRequest对象
       /* 这里new出了一个StringRequest对象，StringRequest的构造函数需要
         传入三个参数，第一个参数就是目标服务器的URL地址，第二个参数是
         服务器响应成功的回调，第三个参数是服务器响应失败的回调。其中，
         目标服务器地址我们填写的是百度的首页，然后在响应成功的回调里打
         印出服务器返回的内容，在响应失败的回调里打印出失败的详细信息。*/
        StringRequest stringRequest = new StringRequest("http://www.baidu.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });

        //将这个StringRequest对象添加到RequestQueue里
        mQueue.add(stringRequest);
    }

    public void postHttp() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.baidu.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("params1", "value1");
                map.put("params2", "value2");
                return map;
            }
        };

        //将这个StringRequest对象添加到RequestQueue里
        mQueue.add(stringRequest);
    }


    //JsonRequest
    public void jsonHttp()
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://api.qljiang.com/student/1", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });

        mQueue.add(jsonObjectRequest);

    }

    public void imageHttp()
    {
        ImageRequest imageRequest = new ImageRequest(
                "http://img.18183.duoku.com/uploads/allimg/160907/102-160ZG50J4.jpg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imageView.setImageResource(R.drawable.pic);
            }
        });

        mQueue.add(imageRequest);

    }

    public void imageLoader_http()
    {
        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return null;
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {

            }
        });

        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageLoaderView,
                R.drawable.pic,R.drawable.fail);
        imageLoader.get("http://img0.imgtn.bdimg.com/it/u=2699385220,1016420654&fm=21&gp=0.jpg",listener);

    }


    public void networkImage_http()
    {
        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return null;
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {

            }
        });

        networkImageView.setImageUrl("http://i0.hdslb.com/video/5b/5b164d0ed7c10f4b9df2a2266fa3a252.jpg",imageLoader);

    }
}

