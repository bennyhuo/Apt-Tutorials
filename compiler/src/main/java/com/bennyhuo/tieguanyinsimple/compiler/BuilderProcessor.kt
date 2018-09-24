package com.bennyhuo.tieguanyinsimple.compiler

import com.bennyhuo.aptutils.AptContext
import com.bennyhuo.aptutils.logger.Logger
import com.bennyhuo.tieguanyinsimple.annotations.Builder
import com.bennyhuo.tieguanyinsimple.annotations.Optional
import com.bennyhuo.tieguanyinsimple.annotations.Required
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion.RELEASE_7
import javax.lang.model.element.TypeElement

class BuilderProcessor: AbstractProcessor() {

    private val supportedAnnotations = setOf(Builder::class.java, Required::class.java, Optional::class.java)

    override fun getSupportedSourceVersion() = RELEASE_7

    override fun getSupportedAnnotationTypes() = supportedAnnotations.mapTo(HashSet<String>(), Class<*>::getCanonicalName)

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        AptContext.init(processingEnv)
    }

    override fun process(annotations: MutableSet<out TypeElement>, env: RoundEnvironment): Boolean {
        env.getElementsAnnotatedWith(Builder::class.java).forEach {
            Logger.warn(it, "小爷到此一游 ${it.simpleName.toString()}")
        }
        return true
    }
}