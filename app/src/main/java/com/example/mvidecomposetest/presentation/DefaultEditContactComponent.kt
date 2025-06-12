package com.example.mvidecomposetest.presentation

import com.arkivanov.decompose.ComponentContext
import com.example.mvidecomposetest.data.RepositoryImpl
import com.example.mvidecomposetest.domain.Contact
import com.example.mvidecomposetest.domain.EditContactUseCase
import com.example.mvidecomposetest.presentation.EditContactComponent.Model
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultEditContactComponent(
    componentContext: ComponentContext,
    val onContactSaved: () -> Unit,
    private val contact: Contact
) : EditContactComponent, ComponentContext by componentContext {

    private val repository = RepositoryImpl
    private val editContactsUseCase = EditContactUseCase(repository)

    init {
        stateKeeper.register(KEY, Model.serializer()){
            model.value
        }
    }

    private val _model = MutableStateFlow(
        stateKeeper.consume(KEY, Model.serializer()) ?:Model(
            username = contact.username,
            phone = contact.phone
        )
    )

    override val model: StateFlow<Model>
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
        onContactSaved()
    }

    companion object{
        private const val KEY =  "DefaultEditContactComponent"
    }
}