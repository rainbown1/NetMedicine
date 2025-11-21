import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RecetaVMFactory(
    private val obtenerRecetasUseCase: ObtenerRecetasUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecetaVM::class.java)) {
            return RecetaVM(obtenerRecetasUseCase) as T
        }
        throw IllegalArgumentException("ViewModel desconocido")
    }
}
