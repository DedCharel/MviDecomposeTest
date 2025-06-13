package com.example.mvidecomposetest.presentation

import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface AddContactComponent {

    val model: StateFlow<AddContactStore.State>

    fun onUsernameChanged(userName: String)

    fun onPhoneChanged(phone: String)

    fun onSaveContactClicked()

}