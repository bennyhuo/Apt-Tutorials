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
import com.bennyhuo.tieguanyinsimple.compiler.prebuilt.ACTIVITY_BUILDER
import com.bennyhuo.tieguanyinsimple.compiler.prebuilt.CONTEXT
import com.bennyhuo.tieguanyinsimple.compiler.prebuilt.INTENT
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier.PUBLIC
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.UNIT

class StartKotlinFunctionBuilder(private val activityClass: ActivityClass) {

    fun build(fileBuilder: FileSpec.Builder) {
        val name = ActivityClassBuilder.METHOD_NAME + activityClass.simpleName
        val functionBuilder = FunSpec.builder(name)
                .receiver(CONTEXT.kotlin)
                .addModifiers(PUBLIC)
                .returns(UNIT)
                .addStatement("val intent = %T(this, %T::class.java)", INTENT.kotlin, activityClass.typeElement)

        activityClass.fields.forEach { field ->
            val name = field.name
            val className = field.asKotlinTypeName()
            if(field is OptionalField){
                functionBuilder.addParameter(ParameterSpec.builder(name, className).defaultValue("null").build())
            } else {
                functionBuilder.addParameter(name, className)
            }

            functionBuilder.addStatement("intent.putExtra(%S, %L)", name, name)
        }

        functionBuilder.addStatement("%T.INSTANCE.startActivity(this, intent)", ACTIVITY_BUILDER.kotlin)
        fileBuilder.addFunction(functionBuilder.build())
    }

}