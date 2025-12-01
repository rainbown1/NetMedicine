package com.rainbown.netmedicine.Dataa

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.rainbown.netmedicine.Domainn.entity.MedicoEntity
import com.rainbown.netmedicine.Domainn.repository.MedicosRepository
import org.json.JSONObject

class RepositoryimplMedicos(private val context: Context) : MedicosRepository {

    private val url = "http://192.168.1.13/Api_NetMedicine/medicos.php"

    override fun obtenerMedicos(
        callback: (List<MedicoEntity>) -> Unit,
        error: (String) -> Unit
    ) {
        val queue = Volley.newRequestQueue(context)

        val request = object : StringRequest(
            Method.POST,
            url,
            { response ->
                try {
                    val json = JSONObject(response)
                    val lista = mutableListOf<MedicoEntity>()

                    if (json.getBoolean("success")) {
                        val medicosJson = json.getJSONArray("medicos")
                        for (i in 0 until medicosJson.length()) {
                            val o = medicosJson.getJSONObject(i)
                            lista.add(
                                MedicoEntity(
                                    idMedico = o.getInt("IdMedico"),
                                    nombre = o.getString("Nombre"),
                                    especialidad = o.getString("Especialidad"),
                                    idHospital = o.getString("idHospital"),
                                    contacto = o.getString("Contacto")
                                )
                            )
                        }
                        callback(lista)
                    } else {
                        error("Sin médicos")
                    }

                } catch (e: Exception) {
                    error(e.message ?: "Error desconocido")
                }
            },
            { volleyError ->
                error(volleyError.message ?: "Error en la petición")
            }
        ) {}

        queue.add(request)
    }
}
