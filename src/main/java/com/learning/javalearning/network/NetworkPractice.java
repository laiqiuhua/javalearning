package com.learning.javalearning.network;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class NetworkPractice {

    public static void main(String[] args) throws IOException {
        NetworkInterface nif = NetworkInterface.getByName("en0");

        Enumeration<InetAddress> nifAddresses = nif.getInetAddresses();

        while (nifAddresses.hasMoreElements()) {
            System.out.println(nifAddresses.nextElement());
        }

        MulticastSocket socket = new MulticastSocket(678);
        InetAddress group = InetAddress.getByName(" 192.168.31.234");
        socket.joinGroup(group);

        DatagramPacket packet;
        for (int i = 0; i < 5; i++) {
            byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            String received = new String(packet.getData());
            System.out.println("Quote of the Moment: " + received);
        }

        socket.leaveGroup(group);
        socket.close();



    }
}
