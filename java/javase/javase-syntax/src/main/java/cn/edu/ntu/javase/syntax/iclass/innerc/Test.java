package cn.edu.ntu.javase.syntax.iclass.innerc;

public class Test {

    public static void main(String[] args) {

        OuterClass.InnerClass inner = new OuterClass().new InnerClass();
        inner.accessOp();
    }
}
