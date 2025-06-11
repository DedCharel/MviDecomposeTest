package com.example.mvidecomposetest.presentation

import com.example.mvidecomposetest.data.RepositoryImpl
import com.example.mvidecomposetest.domain.AddContactUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultAddContactComponent : AddContactComponent {

    val repository = RepositoryImpl
    val addContactsUseCase = AddContactUseCase(repository)


    private val _model = MutableStateFlow(
        AddContactComponent.Model(username = "", phone = "")
    )

    override val model: StateFlow<AddContactComponent.Model>
        get() = _model.asStateFlow()

    override fun onUsernameChanged(userName: String) {
        _model.value = _model.value.copy(username = userName)
    }

    override fun onPhoneChanged(phone: String) {
        _model.value = _model.value.copy(phone = phone)
    }

    override fun onSaveContactClicked() {
        val (username, phone) = _model.value
        addContactsUseCase(username, phone)
    }
}