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

package com.bennyhuo.tieguanyinsimple.compiler

import com.bennyhuo.aptutils.AptContext
import com.bennyhuo.aptutils.logger.Logger
import com.bennyhuo.aptutils.types.isSubTypeOf
import com.bennyhuo.tieguanyinsimple.annotations.Builder
import com.bennyhuo.tieguanyinsimple.annotations.Optional
import com.bennyhuo.tieguanyinsimple.annotations.Required
import com.bennyhuo.tieguanyinsimple.compiler.activity.ActivityClass
import com.bennyhuo.tieguanyinsimple.compiler.activity.entity.Field
import com.bennyhuo.tieguanyinsimple.compiler.activity.entity.OptionalField
import com.sun.tools.javac.code.Symbol.VarSymbol
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion.RELEASE_7
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

class BuilderProcessor : AbstractProcessor() {

    private val supportedAnnotations = setOf(Builder::class.java, Required::class.java, Optional::class.java)

    override fun getSupportedSourceVersion() = RELEASE_7

    override fun getSupportedAnnotationTypes() = supportedAnnotations.mapTo(HashSet<String>(), Class<*>::getCanonicalName)

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        AptContext.init(processingEnv)
    }

    override fun process(annotations: MutableSet<out TypeElement>, env: RoundEnvironment): Boolean {
        val activityClasses = HashMap<Element, ActivityClass>()
        env.getElementsAnnotatedWith(Builder::class.java)
                .filter { it.kind.isClass }
                .forEach { element: Element ->
                    try {
                        if(element.asType().isSubTypeOf("android.app.Activity")){
                            activityClasses[element] = ActivityClass(element as TypeElement)
                        } else {
                            Logger.error(element, "Unsupported typeElement: ${element.simpleName}")
                        }
                    } catch (e: Exception){
                        Logger.logParsingError(element, Builder::class.java, e)
                    }
                }

        env.getElementsAnnotatedWith(Required::class.java)
                .filter { it.kind == ElementKind.FIELD }
                .forEach {element ->
                    activityClasses[element.enclosingElement]?.fields?.add(Field(element as VarSymbol))
                    ?: Logger.error(element, "Field $element annotated as Required while ${element.enclosingElement} not annotated.")
                }

        env.getElementsAnnotatedWith(Optional::class.java)
                .filter { it.kind == ElementKind.FIELD }
                .forEach {element ->
                    activityClasses[element.enclosingElement]?.fields?.add(OptionalField(element as VarSymbol))
                            ?: Logger.error(element, "Field $element annotated as Required while ${element.enclosingElement} not annotated.")
                }

        activityClasses.values.forEach {
            it.builder.build(AptContext.filer)
        }
        return true
    }
}