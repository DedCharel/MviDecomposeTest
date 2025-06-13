package com.example.mvidecomposetest.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.mvidecomposetest.core.componentScope
import com.example.mvidecomposetest.data.RepositoryImpl
import com.example.mvidecomposetest.domain.Contact
import com.example.mvidecomposetest.domain.GetContactsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DefaultContactListComponent(
    componentContext: ComponentContext,
    val onEditingContactRequest: (Contact) -> Unit,
    val onAddContactRequest: () -> Unit
) : ContactListComponent, ComponentContext by componentContext {

   private lateinit var store: ContactListStore

   init {
       componentScope().launch {
           store.labels.collect{
               when(it){
                   ContactListStore.Label.AddContact -> {
                       onAddContactRequest()
                   }
                   is ContactListStore.Label.EditContact -> {
                       onEditingContactRequest(it.contact)
                   }
               }
           }
       }
   }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<ContactListStore.State>
        get() = store.stateFlow


    override fun onAddContactClicked() {
       store.accept(ContactListStore.Intent.AddContact)
    }

    override fun onContactClicked(contact: Contact) {
        store.accept(ContactListStore.Intent.SelectContact(contact))
    }
}