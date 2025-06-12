package com.example.mvidecomposetest.presentation

import com.arkivanov.decompose.ComponentContext
import com.example.mvidecomposetest.data.RepositoryImpl
import com.example.mvidecomposetest.domain.AddContactUseCase
import com.example.mvidecomposetest.presentation.AddContactComponent.Model
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultAddContactComponent(
    componentContext: ComponentContext,
    val onContactSaved: () -> Unit
) : AddContactComponent, ComponentContext by componentContext {

    val repository = RepositoryImpl
    val addContactsUseCase = AddContactUseCase(repository)

    init {
        stateKeeper.register(KEY, Model.serializer()){
            model.value
        }
    }

    private val _model = MutableStateFlow(
       stateKeeper.consume(KEY, Model.serializer()) ?: Model(username = "", phone = "")
    )

    override val model: StateFlow<Model>
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
        onContactSaved()
    }

    companion object{
        const val KEY ="DefaultAddContactComponent"
    }
}