import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rainbown.netmedicine.Dataa.UserRepository
import com.rainbown.netmedicine.domain.entity.UserEntity
import kotlinx.coroutines.launch

class PerfilViewModel(private val userRepository: UserRepository) : ViewModel() {

    val userData = MutableLiveData<UserEntity?>()
    val fotoPerfil = MutableLiveData<String?>()

    fun loadUserByEmail(correo: String) {
        viewModelScope.launch {
            try {
                val user = userRepository.fetchUserByEmail(correo)
                userData.value = user
            } catch (e: Exception) {
                userData.value = null
            }
        }
    }

    fun updateFotoPerfil(path: String) {
        fotoPerfil.value = path
    }
}
