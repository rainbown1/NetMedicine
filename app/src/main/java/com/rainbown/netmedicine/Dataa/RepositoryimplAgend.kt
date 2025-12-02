package com.rainbown.netmedicine.Dataa

import android.content.Context
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.rainbown.netmedicine.Domainn.repository.AgendRepository
import com.rainbown.netmedicine.Domainn.entity.Tarea
import com.rainbown.netmedicine.Domainn.entity.TipoTarea

import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.Locale

class TareaRepositoryImpl(private val context: Context) : AgendRepository {

    override fun obtenerTareas(
        idUsuario: Int,
        onSuccess: (List<Tarea>) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "http://192.168.1.11/Api_NetMedicine/agenda.php"
        val queue = Volley.newRequestQueue(context)

        val request = object : StringRequest(
            Method.POST, url,
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    val tareas = mutableListOf<Tarea>()

                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)

                        val tipoEnum = when (obj.getString("tipo").uppercase()) {
                            "EXAMEN" -> TipoTarea.EXAMEN
                            "CONSULTA" -> TipoTarea.CONSULTA
                            "MEDICAMENTO" -> TipoTarea.MEDICAMENTO
                            else -> TipoTarea.RECORDATORIO
                        }

                        val fechaOriginal = obj.getString("fecha")
                        val fechaFormateada = try {
                            val parser = SimpleDateFormat("yyyy-MM-dd", Locale("es"))
                            val date = parser.parse(fechaOriginal)
                            SimpleDateFormat("dd/MM/yyyy", Locale("es")).format(date!!)
                        } catch (e: Exception) {
                            fechaOriginal
                        }

                        tareas.add(
                            Tarea(
                                idAgenda = obj.getInt("id_agenda"),
                                Correo = "", // ← tu API no lo devuelve
                                Titulo = obj.getString("titulo"),
                                Descripcion = obj.getString("descripcion"),
                                Fecha = fechaFormateada,
                                tipo = tipoEnum,
                                completada = true
                            )
                        )
                    }

                    onSuccess(tareas)

                } catch (e: Exception) {
                    onError("Error al procesar datos: ${e.message}")
                }
            },
            { error ->
                onError(error.message ?: "Error en la solicitud")
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return hashMapOf("id_usuario" to idUsuario.toString())
            }
        }

        queue.add(request)
    }
    }
