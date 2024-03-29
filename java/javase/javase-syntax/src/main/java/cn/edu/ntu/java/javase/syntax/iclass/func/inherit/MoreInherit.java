package cn.edu.ntu.java.javase.syntax.iclass.func.inherit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Deprecated
public class MoreInherit {
    public static void main(String args[]) {
        MoreInherit mi = new MoreInherit();
        log.info("姓名:" + mi.name());
        log.info("地址:" + mi.age());
    }

    public String name() {
        return new test1().name();
    }

    public String age() {
        return new test2().address();
    }

    private class test1 extends Example1 {
        public String name() {
            return super.name();
        }
    }

    private class test2 extends Example2 {
        public String address() {
            return super.address();
        }
    }
}
