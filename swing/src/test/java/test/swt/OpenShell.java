package test.swt;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/7 21:30
 */
public class OpenShell {

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.open();

        // 开始事件处理循环, 直到用户关闭窗口
        while (shell.isDisposed()) {
            if (display.readAndDispatch()) {
                display.sleep();
            }
            display.dispose();
        }
    }
}
