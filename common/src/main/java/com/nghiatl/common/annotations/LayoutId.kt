package com.nghiatl.common.annotations

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class LayoutId(val value: Int)
