/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: SlideShow
 *  File Name: SlideShow.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.4
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.slideshow.app

import com.sigmay.slideshow.common.ApplicationException
import com.sigmay.slideshow.common.ImageReadingFailureException
import com.sigmay.slideshow.frame.ImageFrame
import java.io.File
import java.lang.System.err
import javax.swing.JFileChooser
import kotlin.system.exitProcess

/**
 * スライドショーの実行アプリ。
 *
 */
class SlideShowApp {

    companion object {
        private var rootDir: File = File("")

        /**
         * 初期設定を行う。
         *
         * @param rootDirPath 実行ディレクトリのパス（存在しない場合GUIで入力）
         */
        private fun setRootDir(rootDirPath: String) {
            if (File(rootDirPath).exists())
                rootDir = File(rootDirPath)
            else {
                val fc = JFileChooser()
                fc.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                val selected = fc.showOpenDialog(null)
                if (selected != JFileChooser.APPROVE_OPTION)
                    exitProcess(0)
                rootDir = fc.selectedFile
            }
            println("[Root Dir]: ${rootDir.canonicalPath}")
            println()
        }

        /**
         * スライドショーを開始する。
         */
        private fun start() {
            val fileArray = rootDir.listFiles { f -> f.isFile } ?: throw ApplicationException("ファイルが存在しません。")
            val fileList = fileArray.toMutableList()

            val frame = ImageFrame("SlideShow")
            var loopCount = 1

            println("==== Loop $loopCount ====")
            fileList.shuffle()
            val nonImageList = mutableListOf<File>()
            for (file in fileList)
                try {
                    frame.changeImage(file.absolutePath)
                    println("[Image File]: ${file.canonicalPath}")
                    Thread.sleep(3000)
                } catch (e: ImageReadingFailureException) {
                    err.println("[Warning]: ${file.canonicalPath} is not image")
                    nonImageList.add(file)
                }

            fileList.removeAll(nonImageList)
            if (fileList.isEmpty())
                throw ApplicationException("ファイルが存在しません。")

            loopCount++
            println()

            while (true) {
                println("==== Loop $loopCount ====")
                fileList.shuffle()
                for (file in fileList) {
                    frame.changeImage(file.absolutePath)
                    println("[Image File]: ${file.canonicalPath}")
                    Thread.sleep(3000)
                }
                loopCount++
                println()
            }
        }

        /**
         * スライドショーを実行する。
         *
         * @param rootDirPath 実行ディレクトリのパス（存在しない場合GUIで入力）
         */
        public fun execute(rootDirPath: String = "") {
            setRootDir(rootDirPath)
            start()
        }
    }

}
