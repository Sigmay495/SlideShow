/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: SlideShow
 *  File Name: SlideShowExe.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.4
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.slideshow

import com.sigmay.slideshow.app.SlideShowApp
import com.sigmay.slideshow.common.ApplicationException

fun main(args: Array<String>) {
    try {
        SlideShowApp.execute()
    } catch (e: ApplicationException) {
        e.printStackTrace()
    }
}
