package com.example.mvidecomposetest.presentation

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.mvidecomposetest.domain.Contact
import kotlinx.parcelize.Parcelize

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext{

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.ContactList,
        handleBackButton = true,
        childFactory = ::child,
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when (config){
            Config.AddContact -> {
                val component = DefaultAddContactComponent(
                    componentContext = componentContext,
                    onContactSaved = {
                        navigation.pop()
                    }
                )
                RootComponent.Child.AddContact(component)
            }
            Config.ContactList -> {
                val component = DefaultContactListComponent(
                    componentContext = componentContext,
                    onEditingContactRequest = {
                        navigation.push(Config.EditContact(it))
                    },
                    onAddContactRequest = {
                        navigation.push(Config.AddContact)
                    })
                RootComponent.Child.ContactList(component)
            }
            is Config.EditContact -> {
                val component = DefaultEditContactComponent(
                    componentContext = componentContext,
                    onContactSaved = {navigation.pop()},
                    contact = config.contact
                )
                RootComponent.Child.EditContact(component)
            }
        }
    }



   private sealed interface Config: Parcelable{

        @Parcelize
        object ContactList: Config

        @Parcelize
        object AddContact: Config

        @Parcelize
        data class EditContact(val contact: Contact): Config
    }
}