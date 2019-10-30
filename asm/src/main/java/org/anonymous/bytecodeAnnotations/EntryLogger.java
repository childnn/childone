package org.anonymous.bytecodeAnnotations;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author child
 * 2019/6/18 23:14
 * <p>
 * adds "entering" logs to all methods of a class that have the LogFactory annotation
 */
public class EntryLogger extends ClassVisitor {

    private String className;

    /**
     * Constructs an EntryLogger that inserts logging into annotated methods of a given class.
     *
     * @param writer
     * @param className
     */
    public EntryLogger(ClassWriter writer, String className) {
        super(Opcodes.ASM5, writer);
        this.className = className;
    }

    /**
     * adds entry logging code to the given class.
     *
     * @param args the name of the class file to patch
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("USAGE: java bytecodeAnnotations.EntryLogger classfile");
            System.exit(1);
        }

        Path path = Paths.get(args[0]);

        ClassReader reader = new ClassReader(Files.newInputStream(path));
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

        EntryLogger entryLogger = new EntryLogger(writer,
                path.toString().replace(".class", "").replaceAll("[/\\\\]", "."));
        reader.accept(entryLogger, ClassReader.EXPAND_FRAMES);

        Path write = Files.write(Paths.get(args[0]), writer.toByteArray());
    }

    @Override
    public MethodVisitor visitMethod(int access, String methodName, String descriptor,
                                     String signature, String[] exceptions) {

        MethodVisitor mv = cv.visitMethod(access, methodName, descriptor, signature, exceptions);

        return new AdviceAdapter(Opcodes.ASM5, mv, access, methodName, descriptor) {

            private String loggerName;

            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                return new AnnotationVisitor(Opcodes.ASM5) {
                    @Override
                    public void visit(String name, Object value) {
                        if ("LbytecodeAnnotations/LogEntry;".equals(desc) && "logger".equals(name)) {
                            loggerName = value.toString();
                        }
                    }
                };
            }

            @Override
            protected void onMethodEnter() {
                if (loggerName != null) {
                    visitLdcInsn(loggerName);
                    visitMethodInsn(INVOKESTATIC, "java/util/logging/Logger", "getLogger",
                            "(Ljava/lang/String;)Ljava/util/logging/Logger;", false);
                    visitLdcInsn(className);
                    visitLdcInsn(methodName);

                    visitMethodInsn(INVOKEVIRTUAL, "java/util/logging/Logger", "entering",
                            "(Ljava/lang/String;Ljava/lang/String;)V", false);

                    loggerName = null;
                }
            }
        };
    }
}
