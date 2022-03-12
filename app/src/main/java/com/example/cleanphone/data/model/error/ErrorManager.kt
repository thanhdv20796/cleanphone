package com.example.cleanphone.data.model.error

import com.example.cleanphone.data.model.error.mapper.ErrorMapperImpl
import com.ymo.data.model.error.ErrorUseCase
import retrofit2.HttpException
import javax.inject.Inject

class ErrorManager @Inject constructor(private val errorMapperImpl: ErrorMapperImpl) :
    ErrorUseCase {
    override fun getError(errorCode: Int): Error {
        return Error(code = errorCode, description = errorMapperImpl.errorsMap.getValue(errorCode))
    }

    override fun getHttpError(httpException: HttpException): Error {
      return Error(description = errorMapperImpl.getHttpExceptionError(httpException))
    }

}
