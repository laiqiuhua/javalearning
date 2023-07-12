package com.learning.javalearning.jpda.debug;


public class JInfo {

    public static void main(String[] args) throws Exception {
//         String pid = "63787";
//         String agentName = "JInfoAgent";
//
//         System.out.printf("Attach to Pid %s, dynamic load agent %s \n", pid, agentName);
//         VirtualMachine virtualMachine = com.sun.tools.attach.VirtualMachine.attach(pid);
//         virtualMachine.loadAgentLibrary(agentName, null);
//         virtualMachine.detach();
        JInfo jInfo = new JInfo();
        System.out.println(jInfo.getClass().getEnclosingMethod());
        System.out.println(jInfo.getClass().getEnclosingClass());
        System.out.println(jInfo.getClass().getEnclosingConstructor());
    }
}