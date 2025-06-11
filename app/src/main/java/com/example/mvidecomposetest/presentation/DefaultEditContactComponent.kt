package com.example.mvidecomposetest.presentation

import com.example.mvidecomposetest.data.RepositoryImpl
import com.example.mvidecomposetest.domain.Contact
import com.example.mvidecomposetest.domain.EditContactUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultEditContactComponent(
    private val contact: Contact
) : EditContactComponent {

    private val repository = RepositoryImpl
    private val editContactsUseCase = EditContactUseCase(repository)


    private val _model = MutableStateFlow(
        EditContactComponent.Model(username = contact.username, phone = contact.phone)
    )

    override val model: StateFlow<EditContactComponent.Model>
        get() = _model.asStateFlow()

    override fun onUsernameChanged(username: String) {
        _model.value = _model.value.copy(username = username)
    }

    override fun onPhoneChanged(phone: String) {
        _model.value = _model.value.copy(phone = phone)
    }

    override fun onSaveContactClick() {
        val (username, phone) = _model.value
        editContactsUseCase(contact.copy(username = username, phone = phone))
    }
}