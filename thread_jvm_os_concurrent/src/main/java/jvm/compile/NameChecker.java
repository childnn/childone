package jvm.compile;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic;
import java.util.EnumSet;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/15 14:32
 */
public class NameChecker {

    private final Messager messager;

    NameCheckScanner nameCheckScanner = new NameCheckScanner();

    public NameChecker(ProcessingEnvironment processingEnv) {
        this.messager = processingEnv.getMessager();
    }

    /**
     * Java 命名规范:
     * 类或接口: 符合驼峰命名法, 首字母大写
     * 方法: 符合驼峰命名, 首字母小写
     * 字段: 类,实例变量 符合驼峰命名法, 首字母小写; 常量 全部大写, 下划线分割, 不以下划线开头.
     *
     * @param e
     */
    public void checkNames(Element e) {
        nameCheckScanner.scan(e);
    }

    /**
     * 名称检查器实现类, 继承了 JDK 1.8 中提供的 ElementScanner6
     * 将会以 Visitor 模式访问抽象语法树种的元素
     */
    private class NameCheckScanner extends ElementScanner8<Void, Void> {
        /**
         * 检查 Java 类
         *
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitType(TypeElement e, Void aVoid) {
            scan(e.getTypeParameters(), aVoid);
            checkCamelCase(e, true);
            super.visitType(e, aVoid);
            return null;
        }

        /**
         * 检查传入的 Element 是否符合驼峰命名法, 如果不符合, 则输出警告信息
         *
         * @param e
         * @param initialCaps
         */
        private void checkCamelCase(Element e, boolean initialCaps) {
            String name = e.getSimpleName().toString();
            boolean previousUpper = false;
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);
            if (Character.isUpperCase(firstCodePoint)) {
                previousUpper = true;
                if (!initialCaps) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称 \"" + name + "\" 应当以小写字母开头", e);
                    return;
                }
            } else if (Character.isLowerCase(firstCodePoint)) {
                if (initialCaps) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称\" " + name + "\" 应当以大写字母开头", e);
                    return;
                }
            } else {
                conventional = false;
            }
            if (conventional) {
                int cp = firstCodePoint;
                for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (Character.isUpperCase(cp)) {
                        if (previousUpper) {
                            conventional = false;
                            break;
                        }
                        previousUpper = true;
                    } else {
                        previousUpper = false;
                    }
                }
                if (!conventional) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称 \"" + name + "\" 应当符合驼峰命名法(Camel Case Names)", e);
                }
            }
        }

        /**
         * 检查方法命名是否合法
         *
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitExecutable(ExecutableElement e, Void aVoid) {
            if (e.getKind() == ElementKind.METHOD) {
                Name simpleName = e.getSimpleName();
                if (simpleName.contentEquals(e.getEnclosingElement().getSimpleName())) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "一个普通方法 \"" + simpleName + "\" 不应当与类名重复, 避免与构造函数产生混淆", e);
                }
                checkCamelCase(e, false);
            }
            super.visitExecutable(e, aVoid);
            return null;
        }

        /**
         * 检查变量命名是否合法
         *
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitVariable(VariableElement e, Void aVoid) {
            // 如果这个 Variable 是枚举或常量, 则按大写命名检查,否则按照驼峰命名规则检查
            if (e.getKind() == ElementKind.ENUM_CONSTANT || e.getConstantValue() != null ||
                    heuristicallyConstant(e)) {
                checkAllCaps(e);
            } else {
                checkCamelCase(e, false);
            }
            return null;
        }

        /**
         * 大写命名检查, 要求第一个字母必须是大写的英文字母,其余部分可以实下划线或大写字母
         *
         * @param e
         */
        private void checkAllCaps(VariableElement e) {
            String name = e.getSimpleName().toString();
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);

            if (!Character.isUpperCase(firstCodePoint)) {
                conventional = false;
            } else {
                boolean previousUnderscore = false;
                int cp = firstCodePoint;
                for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (cp == (int) '_') {
                        if (previousUnderscore) {
                            conventional = false;
                            break;
                        }
                        previousUnderscore = true;
                    } else {
                        previousUnderscore = false;
                        if (!Character.isUpperCase(cp) && !Character.isDigit(cp)) {
                            conventional = false;
                            break;
                        }
                    }
                }
            }
            if (!conventional) {
                messager.printMessage(Diagnostic.Kind.WARNING,
                        "常量 \" " + name + "\" 应当全部以大写字母或下划线命名, 并且以字母开头 ", e);
            }
        }

        /**
         * 判断一个变量是否是常量
         *
         * @param e
         * @return
         */
        private boolean heuristicallyConstant(VariableElement e) {
            if (e.getEnclosingElement().getKind() == ElementKind.INTERFACE) {
                return true;
            } else {
                return e.getKind() == ElementKind.FIELD && e.getModifiers().containsAll(
                        EnumSet.of(Modifier.PUBLIC,
                                Modifier.STATIC, Modifier.FINAL));
            }
        }
    }
}
