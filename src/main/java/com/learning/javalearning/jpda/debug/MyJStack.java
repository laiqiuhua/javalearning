package com.learning.javalearning.jpda.debug;

import com.sun.tools.attach.VirtualMachine;
import sun.tools.attach.HotSpotVirtualMachine;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MyJStack {

    public static void main(String[] args)throws Exception {
        VirtualMachine virtualMachine = VirtualMachine.attach("96707");
        HotSpotVirtualMachine hotSpotVirtualMachine = (HotSpotVirtualMachine)virtualMachine;
        InputStream inputStream = hotSpotVirtualMachine.remoteDataDump();

        byte[] buff = new byte[256];
        int len;
        do {
            len = inputStream.read(buff);
            if (len > 0) {
                String response = new String(buff, 0, len, StandardCharsets.UTF_8);
                System.out.print(response);
            }
        } while(len > 0);

        inputStream.close();
        virtualMachine.detach();
    }
}