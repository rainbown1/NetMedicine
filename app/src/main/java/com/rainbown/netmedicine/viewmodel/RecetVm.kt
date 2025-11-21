import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rainbown.netmedicine.Domainn.entity.RecetEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RecetaVM(private val obtenerRecetasUseCase: ObtenerRecetasUseCase) : ViewModel() {

    private val _recetas = MutableStateFlow<List<RecetEntity>>(emptyList())
    val recetas = _recetas

    private val _loading = MutableStateFlow(false)
    val loading = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error = _error

    fun cargarRecetas(correo: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val lista = obtenerRecetasUseCase(correo)
                _recetas.value = lista
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            } finally {
                _loading.value = false
            }
        }
    }
}
