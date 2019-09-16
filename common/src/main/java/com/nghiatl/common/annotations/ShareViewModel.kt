package com.nghiatl.common.annotations

import com.nghiatl.common.annotations.SharedOf


@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class ShareViewModel(
    val value: SharedOf = SharedOf.ACTIVITY
)