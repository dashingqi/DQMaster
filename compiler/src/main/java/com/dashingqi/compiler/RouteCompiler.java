package com.dashingqi.compiler;

import com.dashingqi.annotation.Route;
import com.google.auto.service.AutoService;

import java.awt.DisplayMode;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * @author : zhangqi
 * @time : 2020/7/1
 * desc :
 *
 * Elements：操作元素的主要类
 *      TypeElement：表示类、接口、方法、构造方法的参数元素
 *      VariableElement：表示字段、enum、方法、构造方法参数、局部变量、异常参数
 *
 * Filer：（Filer接口支持通过注解处理器创建新文件）
 *      createSourceFile 创建源文件
 *      createClassFile 创建类文件
 *      createResource 创建辅助源文件
 *
 * Messager：（Messager接口提供注解处理器用来报告错误消息、警告和其他通知的方式）
 *      printMessage() 打印信息
 *
 *
 * Types:
 *
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.dashingqi.annotation.Route"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)

public class RouteCompiler extends AbstractProcessor {

    private Messager mMessager;
    private Elements mElementUtils;
    private Filer mFiler;
    private Types mTypeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        //返回实现Messager接口的对象，用于报告错误信息、警告提醒
        mMessager = processingEnvironment.getMessager();
        //返回实现Elements接口的对象，用于操作元素的工具类
        mElementUtils = processingEnvironment.getElementUtils();
        //返回实现Filer接口的对象，用于创建文件、类和辅助文件
        mFiler = processingEnvironment.getFiler();
        //返回实现Types接口的对象，用于操作类型的工具类
        mTypeUtils = processingEnvironment.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //拿到注解所在的元素
        Set<? extends Element> routeAnnotationSets = roundEnvironment.getElementsAnnotatedWith(Route.class);
        HashMap<String, String> map = new HashMap<>();
        String packageName = null;
        for (Element element : routeAnnotationSets) {
            if (element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) element;
                //获取到注解
                Route routeAnnotation = typeElement.getAnnotation(Route.class);
                //路由地址
                String routePath = routeAnnotation.value();

                mMessager.printMessage(Diagnostic.Kind.WARNING, routePath);

                String activityName = typeElement.getQualifiedName().toString();

                mMessager.printMessage(Diagnostic.Kind.WARNING, activityName);
                if (packageName == null) {
                    packageName = activityName.substring(0, activityName.lastIndexOf("."));
                    packageName = packageName.replace(".", "_");
                }

                map.put(routePath, activityName);
            }
        }
        if (map.size() > 0) {
            Writer writer = null;
            String utilName = "RouteCompilerImpl";
            try {
                JavaFileObject javaFileObject = mFiler.createSourceFile("com.dashingqi.master." + utilName);
                writer = javaFileObject.openWriter();
                Iterator<String> iterator = map.keySet().iterator();
                writer.write("package com.dashingqi.master;\n" +
                        "\n" +
                        "import com.dashingqi.master.IRoute;\n" +
                        "import com.dashingqi.master.RouteUtils;\n" +
                        "\n" +
                        "public class " + utilName + " implements IRoute {\n" +
                        "    @Override\n" +
                        "    public void putActivity() {\n");
                while (iterator.hasNext()) {
                    String path = iterator.next();
                    String value = map.get(path);
                    writer.write("RouteUtils.getInstance().putActivity(\"" + path + "\"," + value + ".class);\n");
                }

                writer.write("    }\n" +
                        "}\n");


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }
}
