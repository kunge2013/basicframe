package org.objectweb.test.asm;

import java.io.FileOutputStream;
import java.io.PrintStream;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

public class Helloworld extends ClassLoader implements Opcodes {
	 public static void main(final String args[]) throws Exception {
	        // Generates the bytecode corresponding to the following Java class:
	        //
	        // public class Example {
	        // public static void main (String[] args) {
	        // System.out.println("Hello world!");
	        // }
	        // }

	        // creates a ClassWriter for the Example public class,
	        // which inherits from Object
	        ClassWriter cw = new ClassWriter(0);
	        cw.visit(V1_1, ACC_PUBLIC, "Example", null, "java/lang/Object", null);

	        // creates a MethodWriter for the (implicit) constructor
	        MethodVisitor mw = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null,
	                null);
	        // pushes the 'this' variable
	        mw.visitVarInsn(ALOAD, 0);
	        // invokes the super class constructor
	        mw.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
	        mw.visitInsn(RETURN);
	        // this code uses a maximum of one stack element and one local variable
	        mw.visitMaxs(1, 1);
	        mw.visitEnd();

	        // creates a MethodWriter for the 'main' method
	        mw = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main",
	                "([Ljava/lang/String;)V", null, null);
	        // pushes the 'out' field (of type PrintStream) of the System class
	        mw.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
	                "Ljava/io/PrintStream;");
	        // pushes the "Hello World!" String constant
	        mw.visitLdcInsn("Hello world!");
	        // invokes the 'println' method (defined in the PrintStream class)
	        mw.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
	                "(Ljava/lang/String;)V");
	        mw.visitInsn(RETURN);
	        // this code uses a maximum of two stack elements and two local
	        // variables
	        mw.visitMaxs(2, 2);
	        mw.visitEnd();

	        // gets the bytecode of the Example class, and loads it dynamically
	        byte[] code = cw.toByteArray();

	        FileOutputStream fos = new FileOutputStream("Example.class");
	        fos.write(code);
	        fos.close();

	        Helloworld loader = new Helloworld();
	        Class<?> exampleClass = loader.defineClass("Example", code, 0,
	                code.length);

	        // uses the dynamically generated class to print 'Helloworld'
	        exampleClass.getMethods()[0].invoke(null, new Object[] { null });

	        // ------------------------------------------------------------------------
	        // Same example with a GeneratorAdapter (more convenient but slower)
	        // ------------------------------------------------------------------------

	        cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
	        cw.visit(V1_8, ACC_PUBLIC, "Example", null, "java/lang/Object", null);

	        // creates a GeneratorAdapter for the (implicit) constructor
	        Method m = Method.getMethod("void <init> ()");
	        GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC, m, null, null,
	                cw);
	        mg.loadThis();
	        mg.invokeConstructor(Type.getType(Object.class), m);
	        mg.returnValue();
	        mg.endMethod();

	        // creates a GeneratorAdapter for the 'main' method
	        m = Method.getMethod("void main (String[])");
	        mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);
	        mg.getStatic(Type.getType(System.class), "out",
	                Type.getType(PrintStream.class));
	        mg.push("Hello world!");
	        mg.invokeVirtual(Type.getType(PrintStream.class),
	                Method.getMethod("void println (String)"));
	        mg.returnValue();
	        mg.endMethod();

	        cw.visitEnd();

	        code = cw.toByteArray();
	        loader = new Helloworld();
	        exampleClass = loader.defineClass("Example", code, 0, code.length);
	        Object[] obj = exampleClass.getMethods();
	        // uses the dynamically generated class to print 'Helloworld'
	        exampleClass.getMethods()[0].invoke(null, new Object[] { null });
	    }
}
