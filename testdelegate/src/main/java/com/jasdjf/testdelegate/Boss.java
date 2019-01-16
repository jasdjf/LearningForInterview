package com.jasdjf.testdelegate;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Boss implements Subject {
    //List<Method> methods = new ArrayList<>();
    HashMap<Method,Object> methods = new HashMap<>();
    //Method method;

    public void registerHandler(Object obj,String methodName,Class... paratypes){
        try {
            Method method = obj.getClass().getMethod(methodName,paratypes);
            if(!methods.containsKey(method)){
                methods.put(method,obj);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void unregisterHandler(){
        //method = null;
        /*if(methods.contains(method)){
            methods.add(method);
        }*/
    }

    @Override
    //public void Notify(Object subject,Object... objs) {
    public void Notify() {
        Iterator<Map.Entry<Method,Object>> iterator = methods.entrySet().iterator();
        Method method;
        Object obj;
        while(iterator.hasNext()){
            Map.Entry<Method,Object> entry = iterator.next();
            method = entry.getKey();
            obj = entry.getValue();
            if (method!=null) {
                try {
                    method.invoke(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("jasdjf","method==null");
            }
        }
    }
}
