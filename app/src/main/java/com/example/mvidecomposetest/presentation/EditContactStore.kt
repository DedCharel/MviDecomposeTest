package com.example.mvidecomposetest.presentation

import com.arkivanov.mvikotlin.core.store.Store

interface EditContactStore:
    Store<EditContactStore.Intent ,EditContactStore.State, EditContactStore.Label>{

    data class State(
        val id: Int,
        val username: String,
        val phone: String
    )

    sealed class Label{

        object ContactSaved: Label()
    }

    sealed class Intent {

        data class ChangeUsername(val username: String): Intent()

        data class ChangePhone(val phone: String): Intent()

        object SaveContact: Intent()
    }
}