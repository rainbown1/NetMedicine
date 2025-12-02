
interface RecetRepository {
    suspend fun obtenerRecetas(idPaciente: Int): List<RecetEntity>
}
