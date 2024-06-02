package com.fekent.medimate.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fekent.medimate.data.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


data class UiState(val userName: String)


class AppViewModel(private val userRepository: UserRepository): ViewModel(){
    val uiState: StateFlow<UiState> =
        userRepository.currentUserName.map { userName ->
            UiState(userName)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState("Unknown")
        )
}