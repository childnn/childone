package test.swt.jface;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/7 23:03
 */
public class Msg {
    public static void main(String[] args) {
        MessageDialog.openWarning(new Shell(), "Warning", "Warning message");
    }
}
