package com.learning.javalearning;

import java.util.Arrays;

public class IPFilter {

    /**
     * @param network 黑名单网段
     * @param maskIp 扫描ip
     * @return
     **/
    public static boolean filt(String network, String maskIp){
        //首先将网段转换为10进制数
        String[] networks = network.split("\\.");
        long networkIp = Long.parseLong(networks[0])  << 24 |
                Long.parseLong(networks[1])  << 16|
                Long.parseLong(networks[2])  << 8|
                Long.parseLong(networks[3]);

        //取出网络位数
        int netCount = Integer.parseInt(maskIp.replaceAll(".*/", ""));
        //这里实际上通过CIDR的网络号转换为子网掩码
        int mask = 0xFFFFFFFF << (32 - netCount);

        //再将验证的ip转换为10进制数
        String testIp = maskIp.replaceAll("/.*", "");
        String[] ips = testIp.split("\\.");
        long ip = Long.parseLong(ips[0]) << 24|
                Long.parseLong(ips[1]) << 16|
                Long.parseLong(ips[2]) << 8|
                Long.parseLong(ips[3]);

        //将网段ip和验证ip分别和子网号进行&;运算之后,得到的是网络号,如果相同,说明是同一个网段的
        return (networkIp & mask) == (ip & mask);
    }

    public static void main(String[] args){
        boolean isBlack = filt("10.168.0.224", "10.168.0.224/23");
        if(isBlack){
            System.out.println("是黑名单");
        }else{
            System.out.println("不是黑名单");
        }

        System.out.println(Arrays.toString("a\nb\n".split("\n")));
    }
}