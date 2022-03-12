package com.ymo.data.model.error

import com.example.cleanphone.data.model.error.Error
import retrofit2.HttpException

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
    fun getHttpError(httpException: HttpException): Error
}
