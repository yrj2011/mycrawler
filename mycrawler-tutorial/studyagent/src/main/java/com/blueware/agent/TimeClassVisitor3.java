package com.blueware.agent;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

//定义扫描待修改class的visitor，visitor就是访问者模式
public class TimeClassVisitor3 extends ClassVisitor {
	private String className;
	private String annotationDesc;

	public TimeClassVisitor3(ClassVisitor cv, String className) {
		super(Opcodes.ASM5, cv);
		this.className = className;
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		this.annotationDesc = desc;
		return super.visitAnnotation(desc, visible);
	}

	// 扫描到每个方法都会进入，参数详情下一篇博文详细分析
	@Override
	public MethodVisitor visitMethod(int access, final String name, final String desc, String signature,
			String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
		if (annotationDesc != null && annotationDesc.equals(Type.getDescriptor(ExclusiveTime.class))) {
			final String key = className + name + desc;
			// 过来待修改类的构造函数 
			if (!name.equals("<init>") && mv != null) {
				final Type returnType = Type.getReturnType(desc);
				 final Type OBJECT_TYPE = Type.getObjectType("java/lang/Object");
				mv = new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
					// 方法进入时获取开始时间
					@Override
					public void onMethodEnter() {
						// 相当于com.blueware.agent.TimeUtil.setStartTime("key");
						this.visitLdcInsn(key);
						this.visitMethodInsn(Opcodes.INVOKESTATIC, "com/blueware/agent/TimeUtil", "setStartTime",
								"(Ljava/lang/String;)V", false);
					}

					// 方法退出时获取结束时间并计算执行时间
					@Override
					public void onMethodExit(int opcode) {
						this.visitMethodInsn(Opcodes.INVOKESTATIC, "com/blueware/agent/TimeUtil", "getInvokeStack",
								"()V", false);
						/*// 相当于com.blueware.agent.TimeUtil.setEndTime("key");
						this.visitLdcInsn(key);
						this.visitMethodInsn(Opcodes.INVOKESTATIC, "com/blueware/agent/TimeUtil", "setEndTime",
								"(Ljava/lang/String;)V", false);
						// 向栈中压入类名称
						this.visitLdcInsn(className);
						// 向栈中压入方法名
						this.visitLdcInsn(name);
						// 向栈中压入方法描述
						this.visitLdcInsn(desc);
						// 相当于com.blueware.agent.TimeUtil.getExclusiveTime("com/blueware/agent/TestTime","testTime");
						this.visitMethodInsn(Opcodes.INVOKESTATIC, "com/blueware/agent/TimeUtil", "getExclusiveTime",
								"(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", false);
						*/
						
						visitLdcInsn(className);  
						 visitLdcInsn(name); //前一篇文章我们只用了方法的sortName,真正实现时应该用FullName,因为  
						 //方法有重载，只凭sortName不能限定到某一个方法。  
						 visitLdcInsn(String.valueOf(opcode));  
						 int localVarCnt = 0;  
						 if(nextLocal > firstLocal) {  
						       localVarCnt = nextLocal - firstLocal;
						      loadVarArray(localVarCnt);  
						 }else{  
						       visitInsn(ACONST_NULL); //为了占用一个栈位置。  
						 }  
						 int argsCnt = 1;  
						 //从方法签名可以分析出参数个数。  
						 if(argsCnt > 0){  
						      loadArgArray();  
						     //  loadVarArray(localVarCnt);  
						      //loadArgArray();  
						 } else {  
						      visitInsn(ACONST_NULL); //为了占用一个栈位置。  
						 }  
						    visitInsn(ACONST_NULL); //为了占用一个栈位置。
						//loadRetruns();
					    	 visitMethodInsn(Opcodes.INVOKESTATIC, "com/blueware/agent/TimeUtil","test",   
									 "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;[Ljava/lang/Object;[Ljava/lang/Object;)V");  
					}
					
					private void loadVarArray(int varCnt) {
						 Type OBJECT_TYPE = Type.getObjectType("java/lang/Object");  
						 push(varCnt);  
						 newArray(OBJECT_TYPE);  
						 for (int i = 0; i < varCnt; i++)  
						 {  
							  int index = super.firstLocal + i;  
							  dup();  
							  push(i);  
							  loadLocal(index);  
							  box(getLocalType(index));  
							  arrayStore(OBJECT_TYPE);
							 
						 }  
						
					}  
					
					
					 public void loadRetruns() {
					        push(1);
					        newArray(OBJECT_TYPE);
					        for (int i = 0; i < 1; i++) {
					            dup();
					            push(i);
					          /*  loadArg(i);
					            mv.visitVarInsn(type.getOpcode(Opcodes.ILOAD), index);*/
					           // mv.visitInsn(Opcodes.ARETURN);
					            mv.visitInsn(returnType.getOpcode(Opcodes.IRETURN));
					            box(returnType);
					            arrayStore(OBJECT_TYPE);
					        }
					    }
				};
			}
		}
		return mv;
	}
}
