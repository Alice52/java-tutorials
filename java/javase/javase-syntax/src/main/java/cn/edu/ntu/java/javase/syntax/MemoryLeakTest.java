package cn.edu.ntu.java.javase.syntax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * function: this class is created for test memory leak.<br>
 * 1. MyComponent, OnClickListener, MyWindow will be not recycled <br>
 * 2. due to OnClickListener is static, so it will not be recycled; <br>
 * and OnClickListener has a final member to point to MyComponent outer class, <br>
 * so MyComponent will not also be recycled;<br>
 * and MyComponent has MyWindow member, so MyWindow will also not recycled <br>
 * <br>
 * // TODO: why not recycle memory? for static or lambda<br>
 *
 * @author zack
 * @create 2020-01-30 17:18
 */
public class MemoryLeakTest {
    private static final Logger LOG = LoggerFactory.getLogger(MemoryLeakTest.class);

    public static void main(String[] args) throws InterruptedException {
        MyComponent myComponent = new MyComponent();
        myComponent.create();
        myComponent.myWindow.clickListener.onClick(new Object());
        myComponent.destroy();
        // this operation will do not recycled memory
        myComponent = null;
        System.gc();

        TimeUnit.HOURS.sleep(5);
    }

    public interface OnClickListener {
        void onClick(Object obj);
    }

    abstract static class Component {

        final void create() {
            onCreate();
        }

        final void destroy() {
            onDestroy();
        }

        /** This is for subClass overwrite. */
        abstract void onCreate();

        /** This is for subClass overwrite. */
        abstract void onDestroy();
    }

    static class MyComponent extends Component {
        OnClickListener clickListener;
        MyWindow myWindow;

        @Override
        void onCreate() {
            clickListener = obj -> LOG.info("Object " + obj + " is clicked.");
            myWindow = new MyWindow();
            myWindow.setClickListener(clickListener);
        }

        @Override
        void onDestroy() {
            myWindow.removeClickListener();
        }
    }

    static class MyWindow {
        OnClickListener clickListener;

        void setClickListener(OnClickListener clickListener) {
            this.clickListener = clickListener;
        }

        void removeClickListener() {
            this.clickListener = null;
        }
    }
}
