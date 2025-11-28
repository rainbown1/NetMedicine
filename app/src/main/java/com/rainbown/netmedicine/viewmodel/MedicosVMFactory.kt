package com.rainbown.netmedicine.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rainbown.netmedicine.Dataa.RepositoryimplMedicos
import com.rainbown.netmedicine.Domainn.usecase.ObtenerMedicosUseCase

class MedicosVMFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repo = RepositoryimplMedicos(context)
        val useCase = ObtenerMedicosUseCase(repo)

        return MedicosVM(useCase) as T
    }
}
