package com.example.yolov5training.support.exception

class YOLOv5ModelException(val error: YOLOv5ModelError = YOLOv5ModelError.UNKNOWN) : RuntimeException(error.messageKey)

enum class YOLOv5ModelError(
    val messageKey: String ?= null
) {
    UNKNOWN("error.unknown"),

}
