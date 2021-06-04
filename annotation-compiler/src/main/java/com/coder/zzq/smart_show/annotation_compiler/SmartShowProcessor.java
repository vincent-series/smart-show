package com.coder.zzq.smart_show.annotation_compiler;

import androidx.annotation.StringRes;

import com.coder.zzq.smart_show.annotations.CustomToast;
import com.coder.zzq.smart_show.annotations.ToastConfig;
import com.coder.zzq.smart_show.annotations.ToastView;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class SmartShowProcessor extends AbstractProcessor {

    private final String[] supportAnnotations = {
            CustomToast.class.getCanonicalName(),
            ToastConfig.class.getCanonicalName(),
            ToastView.class.getCanonicalName(),
    };

    private ProcessingEnvironment mProcessingEnvironment;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mProcessingEnvironment = processingEnvironment;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Arrays.asList(supportAnnotations));
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        TypeSpec.Builder retrieverBuilder = TypeSpec.classBuilder(ClassNames.TOAST_RETRIEVER)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        FieldSpec retrieverField = FieldSpec.builder(ClassNames.TOAST_RETRIEVER, "sRetriever")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer("new $T()", ClassNames.TOAST_RETRIEVER)
                .build();

        retrieverBuilder.addField(retrieverField);

        MethodSpec privateConstructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .build();

        retrieverBuilder.addMethod(privateConstructor);

        MethodSpec createInstance = MethodSpec.methodBuilder("get")
                .returns(ClassNames.TOAST_RETRIEVER)
                .addStatement("return sRetriever")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .build();

        retrieverBuilder.addMethod(createInstance);

        Set<? extends Element> elementsAnnotatedByCustomToast = roundEnvironment.getElementsAnnotatedWith(CustomToast.class);

        for (Element element : elementsAnnotatedByCustomToast) {
            if (!(element instanceof TypeElement)) {
                mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, "Only classes can be annotated by @CustomToast!");
                return true;
            }

            TypeElement typeElementAnnotatedByCustomToast = (TypeElement) element;

            String targetPackage = mProcessingEnvironment.getElementUtils().getPackageOf(typeElementAnnotatedByCustomToast).toString();

            List<Element> toastConfigClass = new ArrayList<>();
            List<Element> toastViewMethod = new ArrayList<>();

            String alias = typeElementAnnotatedByCustomToast.getAnnotation(CustomToast.class).alias();

            if (alias.equals("emotion") || alias.equals("classic")) {
                continue;
            }

            List<? extends Element> innerElements = typeElementAnnotatedByCustomToast.getEnclosedElements();

            for (Element e : innerElements) {
                if (e instanceof TypeElement && e.getAnnotation(ToastConfig.class) != null) {
                    toastConfigClass.add(e);
                    TypeElement typeElement = (TypeElement) e;

                    if (!ClassName.get(typeElement.getSuperclass()).equals(ClassNames.BASE_TOAST_CONFIG)) {
                        mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                "the class (" + typeElement.getQualifiedName() + ") annotated by @ToastConfig must extend:" + ClassNames.BASE_TOAST_CONFIG.toString());
                        return true;
                    }

                    if (!typeElement.getModifiers().contains(Modifier.STATIC)
                            || !typeElement.getModifiers().contains(Modifier.PUBLIC)) {
                        mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                "the class (" + typeElement.getQualifiedName() + ") annotated by @ToastConfig must be public and static!");
                        return true;
                    }

                    List<? extends Element> innerElementsForConfig = typeElement.getEnclosedElements();

                    for (Element innerE : innerElementsForConfig) {
                        if (innerE instanceof VariableElement
                                && !innerE.getModifiers().contains(Modifier.FINAL)
                                && !innerE.getModifiers().contains(Modifier.STATIC)
                                && !innerE.getModifiers().contains(Modifier.PUBLIC)) {
                            mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                    "the field (" + innerE.getSimpleName() + ") of the config class (" + typeElement.getQualifiedName() + ") must be public!");
                            return true;
                        }
                    }
                }

                if (e instanceof ExecutableElement && e.getAnnotation(ToastView.class) != null) {
                    toastViewMethod.add(e);
                    ExecutableElement methodElement = (ExecutableElement) e;

                    if (!methodElement.getModifiers().contains(Modifier.PUBLIC)
                            || !methodElement.getModifiers().contains(Modifier.STATIC)) {
                        mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                "the method (" + methodElement.getSimpleName() + ") annotated by @ToastView must be public and static!");
                        return true;
                    }

                    String configParam = toastConfigClass.isEmpty() ? "BaseToastConfig" : toastConfigClass.get(0).getSimpleName().toString();
                    if (methodElement.getParameters().size() != 3) {
                        mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                String.format("the parameters of method (%s) annotated by @ToastView must be (View,LayoutInflater,%s).", methodElement.getSimpleName().toString(), configParam));
                        return true;
                    }

                    if (!ClassName.get(methodElement.getParameters().get(0).asType()).equals(ClassNames.VIEW_CLASS)) {
                        mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                String.format("the first parameters of method (%s)  must be %s.", methodElement.getSimpleName().toString(), ClassNames.VIEW_CLASS.toString()));
                        return true;
                    }

                    if (!ClassName.get(methodElement.getParameters().get(1).asType()).equals(ClassNames.LAYOUT_INFLATER)) {
                        mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                String.format("the second parameters of method (%s)  must be %s.", methodElement.getSimpleName().toString(), ClassNames.LAYOUT_INFLATER.toString()));
                        return true;
                    }

                    if (!ClassName.get(methodElement.getParameters().get(2).asType()).equals(toastConfigClass.isEmpty() ? ClassNames.BASE_TOAST_CONFIG : ClassName.get(((TypeElement) toastConfigClass.get(0))))) {
                        mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                String.format("the third parameters of method (%s)  must be %s.", methodElement.getSimpleName().toString(), (toastConfigClass.isEmpty() ? ClassNames.BASE_TOAST_CONFIG : ClassName.get(((TypeElement) toastConfigClass.get(0)))).toString()));
                        return true;
                    }

                    Element parent = methodElement.getEnclosingElement();

                    if (parent == null || !(parent instanceof TypeElement) || ((TypeElement) parent).getAnnotation(CustomToast.class) == null) {
                        mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                "the method (" + methodElement.getSimpleName() + ") annotated by @ToastView must define in a class annotated by @CustomToast");
                        return true;
                    }
                }
            }

            if (toastConfigClass.size() > 1) {
                mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, "@ToastConfig can only appear once in a class annotated by @CustomToast!");
                return true;
            }

            if (toastViewMethod.size() > 1) {
                mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, "@ToastView can only  appear once in a class annotated by @CustomToast!");
                return true;
            }

            if (toastViewMethod.isEmpty()) {
                mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, "@ToastView must exist one in a class annotated by @CustomToast!");
                return true;
            }


            ClassName toastConfigClassName = null;
            if (toastConfigClass.isEmpty()) {
                toastConfigClassName = ClassNames.BASE_TOAST_CONFIG;
            } else {
                toastConfigClassName = ClassName.get(targetPackage, typeElementAnnotatedByCustomToast.getSimpleName().toString(), toastConfigClass.get(0).getSimpleName().toString());
            }

            String toastFactoryName = !alias.endsWith("Toast") ? (alias + "ToastFactory") : (alias + "Factory");
            toastFactoryName = toastFactoryName.substring(0, 1).toUpperCase() + toastFactoryName.substring(1);

            ClassName toastFactoryClassName = ClassName.get(targetPackage, toastFactoryName);

            FieldSpec factorySingleton = FieldSpec.builder(toastFactoryClassName, "s" + toastFactoryName, Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                    .initializer("new $T()", toastFactoryClassName)
                    .build();

            MethodSpec getMethod = MethodSpec.methodBuilder("get")
                    .returns(toastFactoryClassName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .addStatement("return s$L", toastFactoryName)
                    .build();

            MethodSpec provideAliasMethod = MethodSpec.methodBuilder("toastAlias")
                    .returns(String.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement("return $S", alias)
                    .addAnnotation(Override.class)
                    .build();

            MethodSpec setupConfigMethod = MethodSpec.methodBuilder("setupConfig")
                    .returns(ClassNames.VIEW_CLASS)
                    .addModifiers(Modifier.PROTECTED)
                    .addAnnotation(Override.class)
                    .addParameter(ClassNames.VIEW_CLASS, "rootView")
                    .addParameter(toastConfigClassName, "toastConfig")
                    .addStatement("super.setupConfig(rootView,toastConfig)")
                    .addStatement("return $L.$L(rootView,$T.getInflater(),toastConfig)", typeElementAnnotatedByCustomToast.getSimpleName(),
                            toastViewMethod.get(0).getSimpleName().toString(),
                            ClassNames.UTILS)
                    .build();

            TypeSpec toastFactoryClass = TypeSpec.classBuilder(toastFactoryClassName)
                    .addField(factorySingleton)
                    .addMethod(getMethod)
                    .addMethod(provideAliasMethod)
                    .addMethod(setupConfigMethod)
                    .superclass(ClassNames.getGenericToastFactoryClassName(toastConfigClassName))
                    .build();

            try {
                JavaFile.builder(targetPackage, toastFactoryClass)
                        .build()
                        .writeTo(mProcessingEnvironment.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }


            String toastViewName = !alias.endsWith("Toast") ? (alias + "ToastView") : (alias + "View");
            toastViewName = toastViewName.substring(0, 1).toUpperCase() + toastViewName.substring(1);

            MethodSpec transition = MethodSpec.methodBuilder("transition")
                    .returns(ClassNames.CONFIG_SETTER)
                    .addParameter(ClassName.BOOLEAN, "b")
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .build();

            MethodSpec cancelOnExit = MethodSpec.methodBuilder("cancelOnActivityExit")
                    .returns(ClassNames.CONFIG_SETTER)
                    .addParameter(TypeName.BOOLEAN, "b")
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .build();

            List<MethodSpec> options = new ArrayList<>();
            if (!toastConfigClass.isEmpty()) {
                ToastConfig toastConfig = toastConfigClass.get(0).getAnnotation(ToastConfig.class);
                boolean withPrefix = toastConfig.withPrefix_m();

                List<? extends Element> optionItems = toastConfigClass.get(0).getEnclosedElements();

                for (Element e : optionItems) {
                    if (e instanceof VariableElement && !e.getModifiers().contains(Modifier.STATIC)
                            && !e.getModifiers().contains(Modifier.FINAL)) {
                        VariableElement variableElement = (VariableElement) e;
                        String fieldName = variableElement.getSimpleName().toString();

                        boolean needRemovePrefix = withPrefix
                                && fieldName.startsWith("m")
                                && fieldName.length() > 1
                                && Character.isUpperCase(fieldName.charAt(1));

                        MethodSpec method = MethodSpec.methodBuilder(
                                needRemovePrefix ?
                                        (fieldName.substring(1, 2).toLowerCase() + (fieldName.length() > 2 ? fieldName.substring(2) : ""))
                                        : fieldName
                        )
                                .returns(ClassNames.CONFIG_SETTER)
                                .addParameter(ClassName.get(variableElement.asType()), variableElement.getSimpleName().toString())
                                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                                .build();
                        options.add(method);
                    }
                }
            }


            MethodSpec applyMethod = MethodSpec.methodBuilder("apply")
                    .returns(ClassNames.PLAIN_TOAST_API)
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .build();

            TypeSpec configSetterInterface = TypeSpec.interfaceBuilder(ClassNames.CONFIG_SETTER)
                    .addMethod(transition)
                    .addMethod(cancelOnExit)
                    .addMethods(options)
                    .addMethod(applyMethod)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .build();


            MethodSpec configMethod = MethodSpec.methodBuilder("config")
                    .returns(ClassNames.CONFIG_SETTER)
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .build();
            TypeSpec overallInterface = TypeSpec.interfaceBuilder("Overall")
                    .addMethod(configMethod)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addSuperinterface(ClassNames.PLAIN_TOAST_API)
                    .build();


            TypeSpec toastViewInterface = TypeSpec.interfaceBuilder(toastViewName)
                    .addModifiers(Modifier.PUBLIC)
                    .addType(overallInterface)
                    .addType(configSetterInterface)
                    .build();

            try {
                JavaFile.builder(targetPackage, toastViewInterface)
                        .build()
                        .writeTo(mProcessingEnvironment.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }


            String invokerName = !alias.endsWith("Toast") ? (alias + "ToastInvoker") : (alias + "Invoker");

            invokerName = invokerName.substring(0, 1).toUpperCase() + invokerName.substring(1);


            FieldSpec configField = FieldSpec.builder(toastConfigClassName,
                    "mConfig",
                    Modifier.PRIVATE, Modifier.FINAL)
                    .initializer("new $T()", toastConfigClassName)
                    .build();

            MethodSpec configMethodImpl = MethodSpec.methodBuilder("config")
                    .returns(ClassName.get(targetPackage, toastViewName, "ConfigSetter"))
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement("return this")
                    .build();


            List<MethodSpec> optionsImpl = new ArrayList<>();

            if (!toastConfigClass.isEmpty()) {
                ToastConfig toastConfig = toastConfigClass.get(0).getAnnotation(ToastConfig.class);
                boolean withPrefix = toastConfig.withPrefix_m();
                List<? extends Element> optionItemsImpl = toastConfigClass.get(0).getEnclosedElements();

                for (Element e : optionItemsImpl) {
                    if (e instanceof VariableElement && !e.getModifiers().contains(Modifier.STATIC)
                            && !e.getModifiers().contains(Modifier.FINAL)) {
                        VariableElement variableElement = (VariableElement) e;
                        String fieldName = variableElement.getSimpleName().toString();

                        boolean needRemovePrefix = withPrefix
                                && fieldName.startsWith("m")
                                && fieldName.length() > 1
                                && Character.isUpperCase(fieldName.charAt(1));

                        String methodName = needRemovePrefix ?
                                (fieldName.substring(1, 2).toLowerCase() + (fieldName.length() > 2 ? fieldName.substring(2) : ""))
                                : fieldName;
                        MethodSpec method = MethodSpec.methodBuilder(methodName)
                                .returns(ClassName.get(targetPackage, toastViewName, "ConfigSetter"))
                                .addParameter(ClassName.get(variableElement.asType()), methodName)
                                .addModifiers(Modifier.PUBLIC)
                                .addStatement("mConfig.$L = $L", fieldName, methodName)
                                .addStatement("return this")
                                .build();
                        optionsImpl.add(method);
                    }
                }
            }

            MethodSpec transitionImpl = MethodSpec.methodBuilder("transition")
                    .returns(ClassName.get(targetPackage, toastViewName, "ConfigSetter"))
                    .addParameter(ClassName.BOOLEAN, "b")
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement("mConfig.mTransition = b")
                    .addStatement("return this")
                    .build();

            MethodSpec cancelOnExitImpl = MethodSpec.methodBuilder("cancelOnActivityExit")
                    .returns(ClassName.get(targetPackage, toastViewName, "ConfigSetter"))
                    .addParameter(TypeName.BOOLEAN, "b")
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement("mConfig.mCancelOnActivityExit = b")
                    .addStatement("return this")
                    .build();

            MethodSpec applyMethodImpl = MethodSpec.methodBuilder("apply")
                    .returns(ClassNames.PLAIN_TOAST_API)
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement("return this")
                    .build();


            MethodSpec show = MethodSpec.methodBuilder("show")
                    .returns(TypeName.VOID)
                    .addParameter(CharSequence.class, "msg")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addStatement("showHelper(msg, $T.BOTTOM | $T.CENTER_HORIZONTAL, 0, $T.DEFAULT_VALUE, $T.LENGTH_SHORT)", ClassNames.GRAVITY, ClassNames.GRAVITY, ClassNames.CONSTANTS, ClassNames.TOAST)
                    .build();

            MethodSpec showRes = MethodSpec.methodBuilder("show")
                    .returns(TypeName.VOID)
                    .addParameter(ParameterSpec.builder(TypeName.INT, "msg").addAnnotation(StringRes.class).build())
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addStatement("show($T.getStringFromRes(msg))", ClassNames.UTILS)
                    .build();

            MethodSpec showAtTop = MethodSpec.methodBuilder("showAtTop")
                    .returns(TypeName.VOID)
                    .addParameter(CharSequence.class, "msg")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addStatement("showHelper(msg, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, Utils.getToolbarHeight() + Utils.dpToPx(40), Toast.LENGTH_SHORT);")
                    .build();

            MethodSpec showResAtTop = MethodSpec.methodBuilder("showAtTop")
                    .returns(TypeName.VOID)
                    .addParameter(ParameterSpec.builder(TypeName.INT, "msg").addAnnotation(StringRes.class).build())
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addStatement("showAtTop(Utils.getStringFromRes(msg))")
                    .build();

            MethodSpec showInCenter = MethodSpec.methodBuilder("showInCenter")
                    .returns(TypeName.VOID)
                    .addParameter(CharSequence.class, "msg")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addStatement("showHelper(msg, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT)")
                    .build();

            MethodSpec showResInCenter = MethodSpec.methodBuilder("showInCenter")
                    .returns(TypeName.VOID)
                    .addParameter(ParameterSpec.builder(TypeName.INT, "msg").addAnnotation(StringRes.class).build())
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addStatement("showInCenter(Utils.getStringFromRes(msg))")
                    .build();

            MethodSpec showAtLocation = MethodSpec.methodBuilder("showAtLocation")
                    .returns(TypeName.VOID)
                    .addParameter(CharSequence.class, "msg")
                    .addParameter(TypeName.INT, "gravity")
                    .addParameter(TypeName.FLOAT, "xOffsetDp")
                    .addParameter(TypeName.FLOAT, "yOffsetDp")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addStatement("showHelper(msg, gravity, Utils.dpToPx(xOffsetDp), Utils.dpToPx(yOffsetDp), Toast.LENGTH_SHORT)")
                    .build();

            MethodSpec showResAtLocation = MethodSpec.methodBuilder("showAtLocation")
                    .returns(TypeName.VOID)
                    .addParameter(ParameterSpec.builder(TypeName.INT, "msg").addAnnotation(StringRes.class).build())
                    .addParameter(TypeName.INT, "gravity")
                    .addParameter(TypeName.FLOAT, "xOffsetDp")
                    .addParameter(TypeName.FLOAT, "yOffsetDp")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addStatement("showAtLocation(Utils.getStringFromRes(msg), gravity, xOffsetDp, yOffsetDp)")
                    .build();

            MethodSpec showLong = MethodSpec.methodBuilder("showLong")
                    .returns(TypeName.VOID)
                    .addParameter(CharSequence.class, "msg")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addStatement("showHelper(msg, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, Constants.DEFAULT_VALUE, Toast.LENGTH_LONG)")
                    .build();

            MethodSpec showLongRes = MethodSpec.methodBuilder("showLong")
                    .returns(TypeName.VOID)
                    .addParameter(ParameterSpec.builder(TypeName.INT, "msg").addAnnotation(StringRes.class).build())
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addStatement("showLong(Utils.getStringFromRes(msg))")
                    .build();

            MethodSpec showLongAtTop = MethodSpec.methodBuilder("showLongAtTop")
                    .returns(TypeName.VOID)
                    .addParameter(CharSequence.class, "msg")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addStatement("showHelper(msg, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, Utils.getToolbarHeight() + Utils.dpToPx(40), Toast.LENGTH_LONG)")
                    .build();

            MethodSpec showLongResAtTop = MethodSpec.methodBuilder("showLongAtTop")
                    .returns(TypeName.VOID)
                    .addParameter(ParameterSpec.builder(TypeName.INT, "msg").addAnnotation(StringRes.class).build())
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addStatement("showLongAtTop(Utils.getStringFromRes(msg))")
                    .build();

            MethodSpec showLongInCenter = MethodSpec.methodBuilder("showLongInCenter")
                    .returns(TypeName.VOID)
                    .addParameter(CharSequence.class, "msg")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addStatement("showHelper(msg, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG)")
                    .build();

            MethodSpec showLongResInCenter = MethodSpec.methodBuilder("showLongInCenter")
                    .returns(TypeName.VOID)
                    .addParameter(ParameterSpec.builder(TypeName.INT, "msg").addAnnotation(StringRes.class).build())
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addStatement("showLongInCenter(Utils.getStringFromRes(msg))")
                    .build();

            MethodSpec showLongAtLocation = MethodSpec.methodBuilder("showLongAtLocation")
                    .returns(TypeName.VOID)
                    .addParameter(CharSequence.class, "msg")
                    .addParameter(TypeName.INT, "gravity")
                    .addParameter(TypeName.FLOAT, "xOffsetDp")
                    .addParameter(TypeName.FLOAT, "yOffsetDp")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addStatement("showHelper(msg, gravity, Utils.dpToPx(xOffsetDp), Utils.dpToPx(yOffsetDp), Toast.LENGTH_LONG)")
                    .build();

            MethodSpec showLongResAtLocation = MethodSpec.methodBuilder("showLongAtLocation")
                    .returns(TypeName.VOID)
                    .addParameter(ParameterSpec.builder(TypeName.INT, "msg").addAnnotation(StringRes.class).build())
                    .addParameter(TypeName.INT, "gravity")
                    .addParameter(TypeName.FLOAT, "xOffsetDp")
                    .addParameter(TypeName.FLOAT, "yOffsetDp")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addStatement("showLongAtLocation(Utils.getStringFromRes(msg), gravity, xOffsetDp, yOffsetDp)")
                    .build();

            MethodSpec showHelper = MethodSpec.methodBuilder("showHelper")
                    .returns(TypeName.VOID)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(CharSequence.class, "msg")
                    .addParameter(TypeName.INT, "gravity")
                    .addParameter(TypeName.INT, "xOffsetDp")
                    .addParameter(TypeName.INT, "yOffsetDp")
                    .addParameter(TypeName.INT, "duration")
                    .addStatement("mConfig.mMsg = msg")
                    .addStatement("mConfig.mGravity = gravity")
                    .addStatement("mConfig.mXOffset = xOffsetDp", ClassNames.UTILS)
                    .addStatement("mConfig.mYOffset = yOffsetDp", ClassNames.UTILS)
                    .addStatement("mConfig.mDuration = duration")
                    .addStatement("$T.get().schedule(new $T($T.get(),mConfig))", ClassNames.TOAST_SCHEDULER, ClassNames.COMPACT_TOAST, toastFactoryClassName)
                    .build();

            TypeSpec invokerClass = TypeSpec.classBuilder(invokerName)
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(ClassName.get(targetPackage, toastViewName, "Overall"))
                    .addSuperinterface(ClassName.get(targetPackage, toastViewName, "ConfigSetter"))
                    .addField(configField)
                    .addMethod(configMethodImpl)
                    .addMethod(transitionImpl)
                    .addMethod(cancelOnExitImpl)
                    .addMethods(optionsImpl)
                    .addMethod(applyMethodImpl)
                    .addMethod(show)
                    .addMethod(showRes)
                    .addMethod(showAtTop)
                    .addMethod(showResAtTop)
                    .addMethod(showInCenter)
                    .addMethod(showResInCenter)
                    .addMethod(showAtLocation)
                    .addMethod(showResAtLocation)
                    .addMethod(showLong)
                    .addMethod(showLongRes)
                    .addMethod(showLongAtTop)
                    .addMethod(showLongResAtTop)
                    .addMethod(showLongInCenter)
                    .addMethod(showLongResInCenter)
                    .addMethod(showLongAtLocation)
                    .addMethod(showLongResAtLocation)
                    .addMethod(showHelper)
                    .build();

            try {
                JavaFile.builder(targetPackage, invokerClass)
                        .build()
                        .writeTo(mProcessingEnvironment.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }


            MethodSpec methodInRetriever = MethodSpec.methodBuilder(alias)
                    .addModifiers(Modifier.PUBLIC)
                    .addModifiers(Modifier.FINAL)
                    .returns(ClassName.get(targetPackage, toastViewName, "Overall"))
                    .addStatement("return new $T()", ClassName.get(targetPackage, invokerName))
                    .build();

            retrieverBuilder.addMethod(methodInRetriever);

        }

        try {
            JavaFile.builder("com.coder.zzq.smartshow.toast", retrieverBuilder.build())
                    .build()
                    .writeTo(mProcessingEnvironment.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

}