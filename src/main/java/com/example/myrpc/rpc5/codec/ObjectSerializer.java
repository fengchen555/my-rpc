package com.example.myrpc.rpc5.codec;

import java.io.*;

public class ObjectSerializer implements Serializer{
    /**
     * @param obj
     * @return
     */
    // 利用java IO 对象 -> 字节数组
    @Override
    public byte[] serialize(Object obj) {
        byte[] bytes = null;
        // 1. 创建一个字节数组输出流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            // 2. 创建一个对象输出流
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            // 3. 写入对象
            oos.writeObject(obj);
            // 4. 刷新
            oos.flush();
            // 5. 获取字节数组
            bytes = bos.toByteArray();
            // 6. 关闭流
            oos.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * @param bytes
     * @param messageType
     * @return
     */
    // 字节数组 -> 对象
    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj = null;
        ByteArrayOutputStream bis = new ByteArrayOutputStream();
        try {
            // 1. 创建一个对象输入流
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            // 2. 读取对象
            obj = ois.readObject();
            // 3. 关闭流
            ois.close();
            bis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * @return
     */
    //0代表java自带序列化方式
    @Override
    public int getType() {
        return 0;
    }
}
