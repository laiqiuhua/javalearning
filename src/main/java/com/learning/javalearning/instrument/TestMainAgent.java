package com.learning.javalearning.instrument;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.util.List;


public class TestMainAgent {
    public static void main(String[]args) throws Exception {

        // 被监控jvm的pid
        String targetVmPid = "22387";
        // Attach到被监控的JVM进程上
        VirtualMachine vm = VirtualMachine.attach(targetVmPid);
        // 让JVM加载jmx Agent
        String javaHome = vm.getSystemProperties().getProperty("java.home");
        String jmxAgent = javaHome + File.separator + "lib" + File.separator + "management-agent.jar";
        vm.loadAgent(jmxAgent, "com.sun.management.jmxremote");

        String jmxAddress = vm.startLocalManagementAgent();

        System.out.println("Agent started");

        // try to parse the return value as a JMXServiceURL
        new JMXServiceURL(jmxAddress);

        if (jmxAddress == null) {
            throw new Exception("Local management agent could not be started");
        }

        List<VirtualMachineDescriptor> descriptors =  VirtualMachine.list();
        for (VirtualMachineDescriptor claz: descriptors) {
            System.out.println("id:"+claz.id()+" display name:"+claz.displayName()+" attach name:"+claz.provider().type());
        }
    }
}
