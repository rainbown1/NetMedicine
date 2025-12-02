

class ObtenerRecetasUseCase(
    private val repository: RecetRepository
) {
    suspend operator fun invoke(idPaciente: Int): List<RecetEntity> {
        return repository.obtenerRecetas(idPaciente)
    }
}


