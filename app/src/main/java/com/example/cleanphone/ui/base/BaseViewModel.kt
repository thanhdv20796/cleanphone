package com.example.cleanphone.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.cleanphone.data.model.error.ErrorManager
import com.example.cleanphone.di.NetworkModule
import dagger.hilt.android.qualifiers.ApplicationContext

import javax.inject.Inject

abstract class BaseViewModel() : ViewModel() {
    /**Inject Singleton ErrorManager
     * Use this errorManager to get the Errors
     */
    @Inject
    lateinit var errorManager: ErrorManager

    @Inject
    lateinit var network: NetworkModule.Network
}
