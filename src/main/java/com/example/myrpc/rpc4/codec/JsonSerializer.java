package com.example.myrpc.rpc4.codec;

import com.alibaba.fastjson.JSONObject;
import com.example.myrpc.rpc4.commom.RPCRequest;
import com.example.myrpc.rpc4.commom.RPCResponse;

/**
 * 由于json序列化的方式是通过把对象转化成字符串，丢失了Data对象的类信息，所以deserialize需要
 * 了解对象对象的类信息，根据类信息把JsonObject -> 对应的对象
 */
public class JsonSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) {
        byte[] bytes = JSONObject.toJSONBytes(obj);
        return bytes;
    }

    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj = null;
        //传输的信息分为request与response
        switch (messageType){
            case 0:
                RPCRequest request = JSONObject.parseObject(bytes, RPCRequest.class);
                // 通过反射得到参数类型
                Object[] objects = new Object[request.getParams().length];
                // 把json字串转化成对应的对象， fastjson可以读出基本数据类型，不用转化
                for (int i = 0; i < objects.length; i++) {
                    // 判断参数类型是否是基本数据类型
                    Class<?> paramsType = request.getParamsTypes()[i];
                    // 如果是基本数据类型，直接赋值
                    if(paramsType.isAssignableFrom(request.getParams()[i].getClass())){
                        objects[i] = request.getParams()[i];
                    } else {// 如果不是基本数据类型，转化成对应的对象
                        objects[i] = JSONObject.parseObject(JSONObject.toJSONString(request.getParams()[i]), paramsType);
                    }
                }
                // 重新设置参数
                request.setParams(objects);
                // 返回request对象
                obj = request;
                break;
            case 1:
                RPCResponse response = JSONObject.parseObject(bytes, RPCResponse.class);
                // 判断返回值是否是基本数据类型
                Class<?> dataType = response.getDataType();
                // 如果不是基本数据类型，转化成对应的对象
                if(!dataType.isAssignableFrom(response.getData().getClass())) {
                    // 把json字串转化成对应的对象， fastjson可以读出基本数据类型，不用转化
                    response.setData(JSONObject.parseObject(JSONObject.toJSONString(response.getData()), dataType));
                }
                // 返回response对象
                obj = response;
                break;
            default:
                System.out.println("暂不支持此种数据");
                throw new RuntimeException("暂不支持此种数据");
        }
        return obj;
    }

    //1 代表json序列化方式
    @Override
    public int getType() {
        return 1;
    }
}
