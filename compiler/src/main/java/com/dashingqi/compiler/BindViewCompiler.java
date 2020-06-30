package com.dashingqi.compiler;

import com.dashingqi.annotation.BindView;
import com.dashingqi.compiler.utils.ElementUtils;
import com.dashingqi.compiler.utils.StringUtils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * @ProjectName: DQMaster
 * @Package: com.dashingqi.compiler
 * @ClassName: BindViewCompiler
 * @Author: DashingQI
 * @CreateDate: 2020/6/30 10:31 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/30 10:31 PM
 * @UpdateRemark:
 * @Version: 1.0
 * <p>
 * <p>
 * TypeElement ----> Class
 * VariableElement ----> 成员变量
 */
@SupportedAnnotationTypes({"com.dashingqi.annotation.BindView"})
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class BindViewCompiler extends AbstractProcessor {

    private Messager mMessager;
    private Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        //日志信息
        mMessager = processingEnvironment.getMessager();
        mElementUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        // 1.获取到所有包含BindView注解的元素
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        //用于存储 存储包含BindView的元素和对应的作用元素
        Map<TypeElement, Map<Integer, VariableElement>> typeElementMap = new HashMap<>();
        //2. 循环包含BindView注解的元素列表 拿到注解作用的成员的元素信息和成员对应类的元素信息

        for (Element element : elementsAnnotatedWith) {
            // 因为BindView的作用对象是FIELD，可以直接转化成VariableElement
            VariableElement variableElement = (VariableElement) element;
            // 拿到Element最里层的元素 此处对应的就是Activity
            TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();

            Map<Integer, VariableElement> variableElementMap = typeElementMap.get(typeElement);
            if (variableElementMap == null) {
                variableElementMap = new HashMap<>();
                typeElementMap.put(typeElement, variableElementMap);
            }

            //获取到注解
            BindView annotation = variableElement.getAnnotation(BindView.class);
            //获取到注解中的值
            int value = annotation.value();
            variableElementMap.put(value, variableElement);

        }


        for (TypeElement keyElement : typeElementMap.keySet()) {

            Map<Integer, VariableElement> variableElementMap = typeElementMap.get(keyElement);
            String packageName = ElementUtils.getPackageName(mElementUtils, keyElement);
            JavaFile.Builder builder = JavaFile.builder(packageName, generateCodeByPoet(keyElement, variableElementMap));
            JavaFile build = builder.build();
            try {
                build.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * 生成类
     *
     * @param typeElement
     * @param maps
     * @return
     */
    private TypeSpec generateCodeByPoet(TypeElement typeElement, Map<Integer, VariableElement> maps) {
        return TypeSpec.classBuilder(ElementUtils.getEnclosingClassName(typeElement) + "ViewBinding")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(generateMethodByPoet(typeElement, maps))
                .build();
    }


    /**
     * 生成方法
     *
     * @param typeElement
     * @param variableElementMap
     * @return
     */
    private MethodSpec generateMethodByPoet(TypeElement typeElement, Map<Integer, VariableElement> variableElementMap) {

        ClassName className = ClassName.bestGuess(typeElement.getQualifiedName().toString());
       // mMessager.printMessage(Diagnostic.Kind.WARNING, className.simpleName());
        String parameter = "_" + StringUtils.toLowerCaseFirstChar(className.simpleName());
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("bind")
                //public static
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(className, parameter);

        for (int viewId : variableElementMap.keySet()) {

            VariableElement variableElement = variableElementMap.get(viewId);
            String name = variableElement.getSimpleName().toString();
            String type = variableElement.asType().toString();
            String text = "{0}.{1}=({2})({3}.findViewById({4}));";
            String codeFormat = MessageFormat.format(text, parameter, name, type, parameter, String.valueOf(viewId));
            methodBuilder.addCode(codeFormat);
        }
        return methodBuilder.build();

    }
}
