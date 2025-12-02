import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecetaVM(
    private val obtenerRecetasUseCase: ObtenerRecetasUseCase
) : ViewModel() {

    private val _recetas = MutableStateFlow<List<RecetEntity>>(emptyList())
    val recetas: StateFlow<List<RecetEntity>> = _recetas

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    fun cargarRecetas(idPaciente: Int) {
        viewModelScope.launch {

            _loading.value = true
            _error.value = null

            try {
                val recetasObtenidas = obtenerRecetasUseCase(idPaciente)

                _recetas.value = recetasObtenidas

            } catch (e: Exception) {

                _error.value = e.localizedMessage ?: "Error al cargar recetas"

            } finally {

                _loading.value = false
            }
        }
    }
}
