/*
 * Copyright (C) 2018 Bennyhuo.
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */

package com.bennyhuo.tieguanyinsimple.compiler.activity.method

import com.bennyhuo.tieguanyinsimple.compiler.activity.ActivityClass
import com.bennyhuo.tieguanyinsimple.compiler.activity.ActivityClassBuilder
import com.bennyhuo.tieguanyinsimple.compiler.activity.entity.OptionalField
import com.bennyhuo.tieguanyinsimple.compiler.prebuilt.INTENT
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier.PRIVATE
import javax.lang.model.element.Modifier.PUBLIC

class StartMethodBuilder(private val activityClass: ActivityClass) {

    fun build(typeBuilder: TypeSpec.Builder) {
        val startMethod = StartMethod(activityClass, ActivityClassBuilder.METHOD_NAME)

        val groupedFields = activityClass.fields.groupBy { it is OptionalField }
        val requiredFields = groupedFields[false] ?: emptyList()
        val optionalFields = groupedFields[true] ?: emptyList()

        startMethod.addAllField(requiredFields)

        val startMethodNoOptional = startMethod.copy(ActivityClassBuilder.METHOD_NAME_NO_OPTIONAL)

        startMethod.addAllField(optionalFields)
        startMethod.build(typeBuilder)

        if(optionalFields.isNotEmpty()){
            startMethodNoOptional.build(typeBuilder)
        }

        if(optionalFields.size < 3){
            optionalFields.forEach{field ->
                startMethodNoOptional.copy(ActivityClassBuilder.METHOD_NAME_FOR_OPTIONAL + field.name.capitalize())
                        .also { it.addField(field) }
                        .build(typeBuilder)
            }
        } else {
            val builderName = activityClass.simpleName + ActivityClassBuilder.POSIX
            val fillIntentMethodBuilder = MethodSpec.methodBuilder("fillIntent")
                    .addModifiers(PRIVATE)
                    .addParameter(INTENT.java, "intent")
            val builderClassName = ClassName.get(activityClass.packageName, builderName)
            optionalFields.forEach { field ->
                typeBuilder.addField(FieldSpec.builder(field.asJavaTypeName(), field.name, PRIVATE).build())
                typeBuilder.addMethod(MethodSpec.methodBuilder(field.name)
                        .addModifiers(PUBLIC)
                        .addParameter(field.asJavaTypeName(), field.name)
                        .addStatement("this.${field.name} = ${field.name}")
                        .addStatement("return this")
                        .returns(builderClassName)
                        .build())

                if(field.isPrimitive){
                    fillIntentMethodBuilder.addStatement("intent.putExtra(\$S, \$L)", field.name, field.name)
                } else {
                    fillIntentMethodBuilder.beginControlFlow("if(\$L != null)", field.name)
                            .addStatement("intent.putExtra(\$S, \$L)", field.name, field.name)
                            .endControlFlow()
                }
            }
            typeBuilder.addMethod(fillIntentMethodBuilder.build())
            startMethodNoOptional.copy(ActivityClassBuilder.METHOD_NAME_FOR_OPTIONALS)
                    .staticMethod(false)
                    .build(typeBuilder)
        }
    }
}