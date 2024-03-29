package cn.edu.ntu.java.javase.jvm;


import cn.edu.ntu.java.javase.common.model.Person;
import cn.edu.ntu.java.javase.jvm.model.PaddedAtomicLong;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

/**
 * @author zack <br>
 * @create 2021-05-02 14:01 <br>
 * @project javase <br>
 */
@Slf4j
public class MemoryStruct {

    @Test
    public void testListStruct() { // 24 bytes

        ArrayList<String> list = new ArrayList<>(1);
        list.add("2955733203817413420");

        log.info(ClassLayout.parseInstance("2955733203817413420").toPrintable());
    }

    @Test
    public void testMapStruct() {

        Map map = new HashMap<>(2);
        map.put("age", 18);
        map.put("name", "zack");

        IntStream.rangeClosed(0, 10).forEach(x -> map.put("name" + x, x));
        log.info(ClassLayout.parseInstance(map).toPrintable());
    }

    @Test
    public void testPersonStruct() {
        Person person = new Person("zack", 18);
        log.info(ClassLayout.parseInstance(person).toPrintable());
    }

    @Test
    public void testArrayStruct() {
        ArrayList<Integer> list = new ArrayList<>(10);
        log.info(ClassLayout.parseInstance(list).toPrintable());
    }

    @Test
    public void testObjectStruct() {
        T o = new T();
        log.info(ClassLayout.parseInstance(o).toPrintable());
        o.hashCode();
        log.info(ClassLayout.parseInstance(o).toPrintable());
    }

    @Test
    public void testObject() {
        Object o = new Object();
        log.info(ClassLayout.parseInstance(o).toPrintable());
        o.hashCode();
        log.info(ClassLayout.parseInstance(o).toPrintable());
    }

    @Test
    public void testAtomicLong() {
        AtomicLong o = new AtomicLong();
        log.info(ClassLayout.parseInstance(o).toPrintable());
    }

    @Test
    public void testPaddedAtomicLong() {
        PaddedAtomicLong o = new PaddedAtomicLong();
        log.info(ClassLayout.parseInstance(o).toPrintable());
    }

    private static class T {
        // same as int a;
        // Integer a = new Integer(1);
        boolean b;
        // 对象则只占4个字节, 是指向对的一个指针
        // String s = "hello46541";
    }
}
