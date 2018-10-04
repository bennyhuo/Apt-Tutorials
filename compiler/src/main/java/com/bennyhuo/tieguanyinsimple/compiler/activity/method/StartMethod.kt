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
import com.bennyhuo.tieguanyinsimple.compiler.activity.entity.Field
import com.bennyhuo.tieguanyinsimple.compiler.prebuilt.ACTIVITY_BUILDER
import com.bennyhuo.tieguanyinsimple.compiler.prebuilt.CONTEXT
import com.bennyhuo.tieguanyinsimple.compiler.prebuilt.INTENT
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier.PUBLIC
import javax.lang.model.element.Modifier.STATIC

class StartMethod(private val activityClass: ActivityClass, private val name: String) {
    private val fields = ArrayList<Field>()

    private var isStaticMethod = true

    fun staticMethod(staticMethod: Boolean): StartMethod{
        this.isStaticMethod = staticMethod
        return this
    }

    fun addAllField(fields: List<Field>){
        this.fields += fields
    }

    fun addField(field: Field){
        this.fields += field
    }

    fun copy(name: String) = StartMethod(activityClass, name).also {
        it.fields.addAll(fields)
    }

    fun build(typeBuilder: TypeSpec.Builder){
        val methodBuilder = MethodSpec.methodBuilder(name)
                .addModifiers(PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(CONTEXT.java, "context")

        methodBuilder.addStatement("\$T intent = new \$T(context, \$T.class)", INTENT.java, INTENT.java, activityClass.typeElement)

        fields.forEach {field ->
            val name = field.name
            methodBuilder.addParameter(field.asJavaTypeName(), name)
                    // intent.putExtra("age", age)
                    .addStatement("intent.putExtra(\$S, \$L)", name, name)
        }

        if(isStaticMethod){
            methodBuilder.addModifiers(STATIC)
        } else {
            methodBuilder.addStatement("fillIntent(intent)")
        }

        methodBuilder.addStatement("\$T.INSTANCE.startActivity(context, intent)", ACTIVITY_BUILDER.java)
        typeBuilder.addMethod(methodBuilder.build())
    }
}