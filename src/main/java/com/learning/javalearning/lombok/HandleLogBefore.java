//package com.learnning.javalearnning.lombok;
//
//import com.sun.tools.javac.tree.JCTree;
//import com.sun.tools.javac.util.List;
//import com.sun.tools.javac.util.ListBuffer;
//import com.sun.tools.javac.util.Name;
//
//import org.mangosdk.spi.ProviderFor;
//
//import java.util.logging.Logger;
//
//import lombok.core.AST;
//import lombok.core.AnnotationValues;
//import lombok.core.HandlerPriority;
//import lombok.extern.youzan.LogBefore;
//import lombok.javac.JavacAnnotationHandler;
//import lombok.javac.JavacNode;
//import lombok.javac.JavacTreeMaker;
//
///**
// * Date: 2018/5/26
// *
// * @author xuzhiyi
// */
//@ProviderFor(JavacAnnotationHandler.class)
//@HandlerPriority(20)
//public class HandleLogBefore extends JavacAnnotationHandler<LogBefore> {
//
//    private static Logger logger = Logger.getLogger(HandleLogBefore.class.getName());
//
//    @Override
//    public void handle(AnnotationValues<LogBefore> annotation, JCTree.JCAnnotation ast, JavacNode annotationNode) {
//        JavacNode methodNode = annotationNode.up();
//        switch (methodNode.getKind()) {
//            case METHOD:
//                JCTree.JCMethodDecl methodDecl = (JCTree.JCMethodDecl) methodNode.get();
//                String methodName = methodDecl.getName().toString();
//                String logLevel = annotation.getInstance().level();
//
//                if (logLevel == null) {
//                    logLevel = "info";
//                }
//
//                String logFieldName = "log";
//                String logMethodName = logFieldName + "." + logLevel;
//
//                String className = null;
//                String logTypeName = null;
//                Name logVarName = null;
//
//                JavacNode typeNode = methodNode.up();
//                if (AST.Kind.TYPE == typeNode.getKind()) {
//                    JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) typeNode.get();
//                    className = classDecl.getSimpleName().toString();
//                    // 遍历类，寻找是否有log类变量
//                    for (JCTree def : classDecl.defs) {
//                        if (def instanceof JCTree.JCVariableDecl) {
//                            JCTree.JCVariableDecl variableDecl = (JCTree.JCVariableDecl) def;
//                            if (variableDecl.name.toString().equals(logFieldName)) {
//                                logVarName = variableDecl.name;
//                                logTypeName = variableDecl.getType().toString();
//                                break;
//                            }
//                        }
//                    }
//                    // 没有log类变量，则直接返回
//                    if (logVarName == null) {
//                        return;
//                    }
//                }
//
//                JCTree.JCBlock block = methodDecl.getBody();
//                List<JCTree.JCStatement> statements = block.stats;
//
//                JavacTreeMaker maker = annotationNode.getTreeMaker();
//
//                JCTree.JCExpression logMethod = JavacHandlerUtil.chainDotsString(typeNode, logMethodName);
//                JCTree.JCExpression logType = JavacHandlerUtil.chainDotsString(typeNode, logTypeName);
//
//                List<JCTree.JCVariableDecl> parameters = methodDecl.getParameters();
//
//                JCTree.JCExpression apply = maker.Apply(List.<JCTree.JCExpression>of(logType), logMethod,
//                                                        generateLogArgs(parameters, className, methodName, maker, typeNode));
//
//                ListBuffer<JCTree.JCStatement> listBuffer = new ListBuffer<JCTree.JCStatement>();
//                listBuffer.append(maker.Exec(apply));
//
//                for (JCTree.JCStatement stat : statements) {
//                    listBuffer.append(stat);
//                }
//                methodDecl.body.stats = listBuffer.toList();
//                annotationNode.getAst().setChanged();
//
//                break;
//            default:
//                annotationNode.addError("@LogBefore is legal only on types.");
//                break;
//        }
//    }
//
//    /**
//     * 生成log的参数表达式
//     */
//    public static List<JCTree.JCExpression> generateLogArgs(List<JCTree.JCVariableDecl> parameters, String className, String methodName, JavacTreeMaker maker, JavacNode typeNode) {
//        JCTree.JCExpression[] argsArray = new JCTree.JCExpression[parameters.size() + 1];
//
//        StringBuilder stringBuilder = new StringBuilder(className).append(".").append(methodName);
//        if (parameters.size() > 0) {
//            stringBuilder.append(" ");
//            for (JCTree.JCVariableDecl variableDecl : parameters) {
//                stringBuilder.append(variableDecl.getName()).append(":{},");
//            }
//            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
//        } else {
//            stringBuilder.append(" begin");
//        }
//
//        argsArray[0] = maker.Literal(stringBuilder.toString());
//
//        JCTree.JCExpression jsonStringMethod = JavacHandlerUtil.chainDotsString(typeNode, "com.alibaba.fastjson.JSON.toJSONString");
//
//        for (int i = 0; i < parameters.size(); i++) {
//            argsArray[i + 1] = maker.Apply(List.<JCTree.JCExpression>nil(), jsonStringMethod, List.<JCTree.JCExpression>of(maker.Ident(parameters.get(i))));
//        }
//
//        return List.<JCTree.JCExpression>from(argsArray);
//    }
//}
