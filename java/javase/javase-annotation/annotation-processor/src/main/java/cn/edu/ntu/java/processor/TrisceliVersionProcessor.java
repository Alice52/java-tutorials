package cn.edu.ntu.java.processor;

import cn.edu.ntu.java.annotations.TrisceliVersion;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.Set;

/** {@link AbstractProcessor} 就属于 Pluggable Annotation Processing API */
public class TrisceliVersionProcessor extends AbstractProcessor {

    private JavacTrees javacTrees;
    private TreeMaker treeMaker;

    private ProcessingEnvironment processingEnv;

    /**
     * 初始化处理器
     *
     * @param processingEnv 提供了一系列的实用工具
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.processingEnv = processingEnv;
        this.javacTrees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {

        return SourceVersion.latest();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> set = new HashSet<>();
        // 支持解析的注解
        set.add(TrisceliVersion.class.getName());
        return set;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        roundEnv.getElementsAnnotatedWith(TrisceliVersion.class).stream()
                .map(element -> javacTrees.getTree(element))
                .map(x -> (JCTree.JCVariableDecl) x)
                .forEach(jcv -> jcv.init = treeMaker.Literal(getVersion()));

        return true;
    }

    //    @Override
    //    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    // {
    //        for (TypeElement t : annotations) {
    //            // 获取到给定注解的element（element可以是一个类、方法、包等）
    //            for (Element e : roundEnv.getElementsAnnotatedWith(t)) {
    //                // JCVariableDecl 为字段/变量定义语法树节点
    //                JCTree.JCVariableDecl jcv = (JCTree.JCVariableDecl) javacTrees.getTree(e);
    //                String varType = jcv.vartype.type.toString();
    //                if (!"java.lang.String".equals(varType)) { // 限定变量类型必须是String类型，否则抛异常
    //                    printErrorMessage(e, "Type '" + varType + "'" + " is not support.");
    //                }
    //                jcv.init = treeMaker.Literal(getVersion()); // 给这个字段赋值，也就是getVersion的返回值
    //            }
    //        }
    //        return true;
    //    }

    /**
     * 利用 processingEnv 内的 Messager 对象输出一些日志
     *
     * @param e element
     * @param m error message
     */
    private void printErrorMessage(Element e, String m) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, m, e);
    }

    private String getVersion() {

        // 获取 version: 这里省略掉复杂的代码, 直接返回固定值
        return "v1.0.1";
    }
}
