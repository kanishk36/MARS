package com.mars.common.listeners

interface ProgressIndicator {

    fun onStartProgress()

    fun onUpdateProgress(percentage: Int)

    fun onEndProgress()
}