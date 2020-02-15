package com.test.newsapp

sealed class ApiResults<T : Any> {

    class Success<R : Any>(val data: R) : ApiResults<R>()

    class Error<E : Any>(val error: Throwable) : ApiResults<E>()

}