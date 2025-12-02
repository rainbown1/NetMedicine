package com.rainbown.netmedicine.Dataa

import RecetEntity
import RecetRepository
import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.rainbown.netmedicine.domain.entity.UserEntity
import com.rainbown.netmedicine.domain.repository.AuthRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ReepositoryimplRecet(private val context: Context) : RecetRepository {

    override suspend fun obtenerRecetas(idPaciente: Int): List<RecetEntity> =
    suspendCancellableCoroutine { continuation ->

            val url = "http://172.16.100.122/Api_NetMedicine/Receta.php"
            val queue = Volley.newRequestQueue(context)

            val request = object : StringRequest(
                Method.POST, url,
                StringRequest@{ response ->
                    try {
                        println("RESPUESTA RECETAS ===> $response")

                        val json = JSONObject(response)

                        if (!json.optBoolean("success")) {
                            continuation.resume(emptyList())
                            return@StringRequest
                        }

                        val listaJson = json.getJSONArray("data")
                        val recetas = mutableListOf<RecetEntity>()

                        for (i in 0 until listaJson.length()) {
                            val obj = listaJson.getJSONObject(i)

                            recetas.add(
                                RecetEntity(
                                    idReceta = obj.getInt("id_receta"),

                                    fecha = obj.getString("fecha_prescripcion"),

                                    medicamento = obj.getString("medicamento"),
                                    cantidad = obj.getString("cantidad"),
                                    frecuencia = obj.getString("frecuencia"),
                                    duracion = obj.getString("duracion"),
                                    instruccion = obj.getString("instruccion")
                                )
                            )
                        }

                        continuation.resume(recetas)

                    } catch (e: Exception) {
                        continuation.resumeWithException(e)
                    }
                },
                { error ->
                    continuation.resumeWithException(error)
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["id_paciente"] = idPaciente.toString()
                    println("Enviando ID paciente: $idPaciente")
                    return params
                }


            }

            queue.add(request)
        }
}
