package com.xuankai.wang.example;

import java.util.*;

/**
 * 负载均衡实现方法
 */
public class LoadBalancing {

    private static Integer pos = 0;

    private static Map<String,Integer> serverMap = new HashMap<String,Integer>(){{
        put("192.168.1.100",1);
        put("192.168.1.101",1);
        put("192.168.1.102",4);
        put("192.168.1.103",1);
        put("192.168.1.104",1);
        put("192.168.1.105",3);
        put("192.168.1.106",1);
        put("192.168.1.107",2);
        put("192.168.1.108",1);
        put("192.168.1.109",1);
        put("192.168.1.110",1);
    }};

    public static void main(String args[]){
        hash();
    }

    /**
     * 一般随机
     */
    public static void random(){
        List<String> keyList = new ArrayList<String>(serverMap.keySet());
        Random random = new Random();
        for(int i=0;i<100;i++){
            int idx = random.nextInt(keyList.size());
            System.out.print(idx+"**************");
            String server = keyList.get(idx);
            System.out.println(server);
        }
    }

    /**
     * 权重随机
     */
    public static void weightRandom(){
        Set<String> keySet = serverMap.keySet();
        List<String> servers = new ArrayList<String>();
        for(Iterator<String> it = keySet.iterator();it.hasNext();){
            String server = it.next();
            int weithgt = serverMap.get(server);
            for(int i=0;i<weithgt;i++){
                servers.add(server);
            }
        }
        String server = null;
        Random random = new Random();
        int idx = random.nextInt(servers.size());
        server = servers.get(idx);
        System.out.println(server);
    }

    /**
     * 轮询
     */
    public static void roundRobin(){
        List<String> keyList = new ArrayList<String>(serverMap.keySet());
        String server = null;
        synchronized (pos){
            if(pos > keyList.size()){
                pos = 0;
            }
            server = keyList.get(pos);
            pos++;
        }
        System.out.println(server);
    }

    /**
     * 权重轮询
     */
    public static void weightRoundRobin(){
        Set<String> keySet = serverMap.keySet();
        List<String> servers = new ArrayList<String>();
        for(Iterator<String> it = keySet.iterator();it.hasNext();){
            String server = it.next();
            int weithgt = serverMap.get(server);
            for(int i=0;i<weithgt;i++){
                servers.add(server);
            }
        }
        String server = null;
        synchronized (pos){
            if(pos > keySet.size()){
                pos = 0;
            }
            server = servers.get(pos);
            pos++;
        }
        System.out.println(server);
    }

    /**
     * 哈希
     */
    public static void hash(){
        List<String> keyList = new ArrayList<String>(serverMap.keySet());
        String remoteIp = "192.168.2.221";
        int hashCode = remoteIp.hashCode();
        int idx = hashCode % keyList.size();
        String server = keyList.get(Math.abs(idx));
        System.out.println(server);
    }
}
