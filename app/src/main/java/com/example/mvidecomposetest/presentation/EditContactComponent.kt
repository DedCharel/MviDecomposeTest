package com.example.mvidecomposetest.presentation

import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface EditContactComponent {

    val model: StateFlow<EditContactStore.State>


    fun onUsernameChanged(username: String)

    fun onPhoneChanged(phone: String)

    fun onSaveContactClicked()


}