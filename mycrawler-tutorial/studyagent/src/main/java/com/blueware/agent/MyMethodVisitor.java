package com.blueware.agent;

import org.objectweb.asm.*;

/**
 * Created by ive on 14/11/2016.
 */
public class MyMethodVisitor extends MethodVisitor implements Opcodes {

    public MyMethodVisitor (int api) {
        super(api);
    }

    public MyMethodVisitor (int api, MethodVisitor mv) {
        super(api, mv);
    }

    @Override
    public void visitCode () {
        System.out.println("visitCode");
        super.visitCode();
        this.visitVarInsn(ALOAD, 1);
        this.visitMethodInsn(INVOKESTATIC, "com/blueware/agent/TimeUtil", "before", "(Ljava/lang/String;)V", false);

    }

    @Override
    public void visitInsn (int opcode) {
        System.out.println("-->" + opcode);

        if (opcode >= IRETURN && opcode <= RETURN)// 在返回之前安插after 代码。
        {
            this.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 1);
            this.visitMethodInsn(INVOKESTATIC, "com/blueware/agent/TimeUtil", "after", "(Ljava/lang/String;Ljava/lang/String;)V", false);
        }
        super.visitInsn(opcode);
    }

    @Override
    public AnnotationVisitor visitLocalVariableAnnotation (int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {

        return super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, desc, visible);
    }

    @Override
    public void visitMaxs (int maxStack, int maxLocals) {
        System.out.println("visitMaxs : " + maxStack + " , " + maxLocals);
        super.visitMaxs(maxStack + 2, maxLocals);
    }

    @Override
    public void visitEnd () {
        System.out.println("visitEnd");
        super.visitEnd();
    }
}
