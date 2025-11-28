package com.rainbown.netmedicine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rainbown.netmedicine.Domainn.entity.MedicoEntity
import com.rainbown.netmedicine.Domainn.usecase.ObtenerMedicosUseCase
import kotlinx.coroutines.launch

class MedicosVM(
    private val useCase: ObtenerMedicosUseCase
) : ViewModel() {

    val medicosLiveData = MutableLiveData<List<MedicoEntity>>()
    val errorLiveData = MutableLiveData<String>()

    fun cargarMedicos() {
        viewModelScope.launch {
            useCase.invoke(
                callback = { lista -> medicosLiveData.postValue(lista) },
                error = { mensaje -> errorLiveData.postValue(mensaje) }
            )
        }
    }
}
