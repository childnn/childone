package org.anonymous.bytecodeAnnotations;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.Instrumentation;

/**
 * @author child
 * 2019/6/19 8:50
 */
public class EntryLoggingAgent {
    public static void premain(final String arg, Instrumentation instr) {
        /*instr.addTransformer((loader, className, cl, pd, data) -> {

        });*/
        instr.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
            if (!className.equals(arg)) {
                return null;
            }
            ClassReader reader = new ClassReader(classfileBuffer);
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            EntryLogger el = new EntryLogger(writer, className);

            reader.accept(el, ClassReader.EXPAND_FRAMES);

            return writer.toByteArray();
        });
    }
}
