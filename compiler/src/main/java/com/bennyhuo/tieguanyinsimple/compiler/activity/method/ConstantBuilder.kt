package com.bennyhuo.tieguanyinsimple.compiler.activity.method

import com.bennyhuo.tieguanyinsimple.compiler.activity.ActivityClass
import com.bennyhuo.tieguanyinsimple.compiler.utils.camelToUnderline
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier.*

class ConstantBuilder(private val activityClass: ActivityClass) {

    //public static final String REQUIRED_WORK_PLACE = "workPlace"
    fun build(typeBuilder: TypeSpec.Builder) {
        activityClass.fields.forEach { field ->
            typeBuilder.addField(FieldSpec.builder(String::class.java, field.prefix + field.name.camelToUnderline().toUpperCase(),
                    PUBLIC, STATIC, FINAL).initializer("\$S", field.name)
                    .build())
        }
    }

}