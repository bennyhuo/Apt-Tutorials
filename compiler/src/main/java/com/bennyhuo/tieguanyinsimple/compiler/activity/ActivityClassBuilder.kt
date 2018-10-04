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

package com.bennyhuo.tieguanyinsimple.compiler.activity

import com.bennyhuo.tieguanyinsimple.compiler.activity.method.*
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import com.squareup.kotlinpoet.FileSpec
import javax.annotation.processing.Filer
import javax.lang.model.element.Modifier.FINAL
import javax.lang.model.element.Modifier.PUBLIC
import javax.tools.StandardLocation

class ActivityClassBuilder(private val activityClass: ActivityClass) {

    companion object {
        const val POSIX = "Builder"
        const val METHOD_NAME = "start"
        const val METHOD_NAME_NO_OPTIONAL = METHOD_NAME + "WithoutOptional"
        const val METHOD_NAME_FOR_OPTIONAL = METHOD_NAME + "WithOptional"
        const val METHOD_NAME_FOR_OPTIONALS = METHOD_NAME + "WithOptionals"
    }

    fun build(filer: Filer){
        if(activityClass.isAbstract) return
        val typeBuilder = TypeSpec.classBuilder(activityClass.simpleName + POSIX)
                .addModifiers(PUBLIC, FINAL)

        ConstantBuilder(activityClass).build(typeBuilder)
        StartMethodBuilder(activityClass).build(typeBuilder)
        SaveStateMethodBuilder(activityClass).build(typeBuilder)
        InjectMethodBuilder(activityClass).build(typeBuilder)

        if(activityClass.isKotlin){
            val fileBuilder = FileSpec.builder(activityClass.packageName, activityClass.simpleName + POSIX)
            StartKotlinFunctionBuilder(activityClass).build(fileBuilder)
            writeKotlinToFile(filer, fileBuilder.build())
        }

        writeJavaToFile(filer, typeBuilder.build())
    }

    private fun writeKotlinToFile(filer: Filer, fileSpec: FileSpec) {
        try {
            val fileObject = filer.createResource(StandardLocation.SOURCE_OUTPUT, activityClass.packageName, fileSpec.name + ".kt")
            fileObject.openWriter()
                    .also(fileSpec::writeTo)
                    .close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun writeJavaToFile(filer: Filer, typeSpec: TypeSpec){
        try {
            JavaFile.builder(activityClass.packageName, typeSpec).build().writeTo(filer)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}