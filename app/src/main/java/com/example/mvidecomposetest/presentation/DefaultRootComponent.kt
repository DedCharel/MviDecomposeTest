package com.example.mvidecomposetest.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.example.mvidecomposetest.domain.Contact
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext{

    val navigate = StackNavigation<Config>()

    fun child(
        componentContext: ComponentContext,
        config: Config
    ): ComponentContext {
        return when (config){
            Config.AddContact -> {
                DefaultAddContactComponent(
                    componentContext = componentContext,
                    onContactSaved = {
                        navigate.pop()
                    }
                )
            }
            Config.ContactList -> {
                DefaultContactListComponent(
                    componentContext = componentContext,
                    onEditingContactRequest = {
                        navigate.push(Config.EditContact(it))
                    },
                    onAddContactRequest = {
                        navigate.push(Config.AddContact)
                    })
            }
            is Config.EditContact -> {
                DefaultEditContactComponent(
                    componentContext = componentContext,
                    onContactSaved = {navigate.pop()},
                    contact = config.contact
                )
            }
        }
    }

    @Serializable
    sealed interface Config{

        @Serializable
        object ContactList: Config

        @Serializable
        object AddContact: Config

        @Serializable
        data class EditContact(val contact: Contact): Config
    }
}