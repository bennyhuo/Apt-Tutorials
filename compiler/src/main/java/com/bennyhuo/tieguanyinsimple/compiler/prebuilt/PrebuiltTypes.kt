package com.bennyhuo.tieguanyinsimple.compiler.prebuilt

import com.bennyhuo.aptutils.types.ClassType

val CONTEXT = ClassType("android.content.Context")
val INTENT = ClassType("android.content.Intent")
val ACTIVITY = ClassType("android.app.Activity")
val BUNDLE = ClassType("android.os.Bundle")
val BUNDLE_UTILS = ClassType("com.bennyhuo.tieguanyinsimple.runtime.utils.BundleUtils")

val ACTIVITY_BUILDER = ClassType("com.bennyhuo.tieguanyinsimple.runtime.ActivityBuilder")