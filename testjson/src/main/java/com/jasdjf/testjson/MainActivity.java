package com.jasdjf.testjson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //json数据格式
        //{
        //  "user":{
        //      "name":"alex",
        //      "age":"18",
        //      "isMan":true,
        //      "sex":男
        //}
        //User类中的SerializedName注解
        String strJson = "{\"user\":{\"name\":\"alex\",\"age\":\"18\",\"is_man\":true,\"sex\":男}}";
        try {
            String json = new JSONObject(strJson).getJSONObject("user").toString();
            Gson gson = new Gson();
            User user = gson.fromJson(json,User.class);
            Log.d("jasdjf", user.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //GsonBuilder ,通过该类初始化一些Gson的基本属性
        Gson tmpGson = new GsonBuilder()
                //序列化null
                .serializeNulls()
                // 设置日期时间格式，另有2个重载方法
                // 在序列化和反序化时均生效
                .setDateFormat("yyyy-MM-dd")
                // 禁此序列化内部类
                .disableInnerClassSerialization()
                //生成不可执行的Json（多了 )]}' 这4个字符）
                .generateNonExecutableJson()
                //禁止转义html标签
                .disableHtmlEscaping()
                //格式化输出
                .setPrettyPrinting()
                .create();
    }

    public void parserArray() throws JSONException {
        String json = "{\"user\":[{\"name\":\"alex\",\"age\":18,\"isMan\":true},{\"name\":\"mahao\",\"age\":16,\"isMan\":true}]}";
        //1， 获取对应实体类对象的字符串，当前为user的值。
        String userJson = new JSONObject(json).getJSONArray("user").toString();
        //2 ,创建Gson 对象
        Gson gson = new Gson();
        //3, 获取user 数组
        User[] users = gson.fromJson(userJson, User[].class);
        System.out.println(users[1]);
    }

    public void parserList() throws JSONException {
        String json = "{\"user\":[{\"name\":\"alex\",\"age\":18,\"isMan\":true},{\"name\":\"mahao\",\"age\":16,\"isMan\":true}]}";
        //1， 获取对应实体类对象的字符串，当前为user的值。
        String userJson = new JSONObject(json).getJSONArray("user").toString();
        //2 ,创建Gson 对象
        Gson gson = new Gson();
        //3, 获取user List
        List<User> users = gson.fromJson(userJson,new TypeToken<List<User>>(){}.getType());
        System.out.println(users.get(1));
    }

    public void parseBeanToJson(){
        //1 构造gson 对象
        Gson gson = new Gson();
        //2 构造对象
        User user = new User();
        user.setName("lala");
        user.setAge(20);
        // 3 生成json 数据
        String json = gson.toJson(user);
        System.out.println(json);
    }
}
