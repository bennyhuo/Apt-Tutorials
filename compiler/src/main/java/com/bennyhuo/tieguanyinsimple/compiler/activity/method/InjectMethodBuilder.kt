package com.bennyhuo.tieguanyinsimple.compiler.activity.method

import com.bennyhuo.tieguanyinsimple.compiler.activity.ActivityClass
import com.bennyhuo.tieguanyinsimple.compiler.activity.entity.OptionalField
import com.bennyhuo.tieguanyinsimple.compiler.prebuilt.ACTIVITY
import com.bennyhuo.tieguanyinsimple.compiler.prebuilt.BUNDLE
import com.bennyhuo.tieguanyinsimple.compiler.prebuilt.BUNDLE_UTILS
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier.PUBLIC
import javax.lang.model.element.Modifier.STATIC

class InjectMethodBuilder(private val activityClass: ActivityClass) {
    fun build(typeBuilder: TypeSpec.Builder) {
        val injectMethodBuilder = MethodSpec.methodBuilder("inject")
                .addParameter(ACTIVITY.java, "instance")
                .addParameter(BUNDLE.java, "savedInstanceState")
                .addModifiers(PUBLIC, STATIC)
                .returns(TypeName.VOID)
                .beginControlFlow("if(instance instanceof \$T)", activityClass.typeElement)
                .addStatement("\$T typedInstance = (\$T) instance", activityClass.typeElement, activityClass.typeElement)
                .addStatement("\$T extras = savedInstanceState == null ? typedInstance.getIntent().getExtras() : savedInstanceState", BUNDLE.java)
                .beginControlFlow("if(extras != null)")

        activityClass.fields.forEach { field ->
            val name = field.name
            val typeName = field.asJavaTypeName().box()

            if(field is OptionalField){
                injectMethodBuilder.addStatement("\$T \$LValue = \$T.<\$T>get(extras, \$S, \$L)", typeName, name, BUNDLE_UTILS.java, typeName, name, field.defaultValue)
            } else {
                injectMethodBuilder.addStatement("\$T \$LValue = \$T.<\$T>get(extras, \$S)", typeName, name, BUNDLE_UTILS.java, typeName, name)
            }

            if(field.isPrivate){
                injectMethodBuilder.addStatement("typedInstance.set\$L(\$LValue)", name.capitalize(), name)
            } else {
                injectMethodBuilder.addStatement("typedInstance.\$L = \$LValue", name, name)
            }
        }

        injectMethodBuilder.endControlFlow().endControlFlow()
        typeBuilder.addMethod(injectMethodBuilder.build())
    }
}