import com.rainbown.netmedicine.Domainn.entity.RecetEntity

class ObtenerRecetasUseCase(private val repository: RecetRepository) {
    suspend operator fun invoke(correo: String): List<RecetEntity> {
        return repository.obtenerRecetas(correo)
    }
}
