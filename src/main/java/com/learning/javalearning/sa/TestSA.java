//package com.learning.javalearning.sa;
//
//import sun.jvm.hotspot.oops.HeapVisitor;
//import sun.jvm.hotspot.oops.Klass;
//import sun.jvm.hotspot.oops.ObjectHeap;
//import sun.jvm.hotspot.oops.Oop;
//import sun.jvm.hotspot.runtime.VM;
//import sun.jvm.hotspot.tools.Tool;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class TestSA extends Tool {
//
//    @Override
//    public void run() {
//
//        Map<Klass, Value> map = new HashMap<>();
//        ObjectHeap objectHeap = VM.getVM().getObjectHeap();
//        objectHeap.iterate(new HeapVisitor() {//遍历堆里的对象
//            @Override
//            public void prologue(long l) {
//            }
//
//            @Override
//            public boolean doObj(Oop oop) {
//                Klass klass = oop.getKlass();//获取对象的class
//                Value value = map.get(klass);
//                if (value == null) {
//                    value = new Value();
//                    map.put(klass, value);
//                }
//                value.increase();//对象数目加一
//                value.add(oop.getObjectSize());//对象大小
//
//                return false;
//            }
//
//            @Override
//            public void epilogue() {
//            }
//        });
//        for (Map.Entry<Klass, Value> entry : map.entrySet()) {
//            StringBuilder sb = new StringBuilder("name:");
//            sb.append(entry.getKey().getName().asString())
//                    .append("---count:").append(entry.getValue().getCount())
//                    .append("---size:").append(entry.getValue().getSize());
//            System.out.println(sb.toString());
//        }
//
//    }
//
//    private class Value {
//        private int count = 0;
//        private long size = 0;
//
//        public void increase() {
//            count++;
//        }
//
//        public void add(long size) {
//            this.size += size;
//        }
//
//        public int getCount() {
//            return count;
//        }
//
//        public long getSize() {
//            return size;
//        }
//
//    }
//
//    public static void main(String[] args) {
//        TestSA testSA = new TestSA();
//        testSA.execute(args);
//    }
//}
