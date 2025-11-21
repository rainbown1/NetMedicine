import com.rainbown.netmedicine.Domainn.entity.RecetEntity

interface RecetRepository {
    suspend fun obtenerRecetas(correo: String): List<RecetEntity>
}
