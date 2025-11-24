package com.rainbown.netmedicine.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.rainbown.netmedicine.View.Components.MyNavigationBar
import com.rainbown.netmedicine.View.Components.barra
import com.rainbown.netmedicine.ui.theme.onPrimaryContainerLight

@Composable
fun pantallaServiciosInfo(navController: NavController, servicioId: String?){
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
       ServiciosInfo(
           navController = navController,
           modifier = Modifier.padding(innerPadding),
           servicioId = servicioId
       )
    }
}

@Composable
fun ServiciosInfo(modifier: Modifier, navController: NavController, servicioId: String?){
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val(barraTop, contenido, menu) = createRefs()

        val serviciosFiltrados = remember (servicioId){
            if (servicioId != null){
                filtroServicios(servicioId)
            }else{
                emptyList()
            }
        }

        val nombreServicio = remember(servicioId) {
            infoservicios.find { it.servicioId == servicioId }?.nombre ?: "Servicio"
        }

        Box(
            modifier = Modifier
                .constrainAs(barraTop) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            barra("Servicios Medicos -> $nombreServicio")
        }

        if (serviciosFiltrados.isEmpty()){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No hay servicios disponibles",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Box(modifier = Modifier.constrainAs(contenido){
                start.linkTo(parent.start)
                top.linkTo(barraTop.bottom)
                end.linkTo(parent.end)
            }){
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)) {
                    items(serviciosFiltrados) { service ->
                        ServicesInfoCard(servicioinfo = service)
                    }
                }
            }
        }

        Box(modifier = Modifier.constrainAs(menu){
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }){
            MyNavigationBar(navController)
        }

    }
}

val infoservicios = listOf<ServicioInfo>(
    ServicioInfo("S1","Otorrinolaringologia","Horario\n" +
            "martes a jueves de 8:00 a 14:00\n" +
            "lunes y viernes 9:00 a 14:00\n" +
            "urgencias: 24 horas\n","Descripcion \n" +
            "El servicio es especializado en la atencion oportuna,honesta y responsable, de los pacientes con padecimientos de oidos, nariz y garganta y/o que requieran cirugía de cabeza y cuello; estamos comprometidos con la atención medica de calidad las 24 horas del día, los 365 días del año, para lo que contamos con la tecnología de vanguardia y el personal certificado para tal fin, quienes además poseen integridad y gran calidad humana.\n","Requisitos de consulta de primera vez:\n" +
            "Carnet de la institución\n" +
            "Interconsulta\n" +
            "Recibo de pago y/o línea de captura\n" +
            "\n" +
            "Requisitos de consultas subsecuentes:\n" +
            "Carnet\n" +
            "Recibo de pago y/o línea de captura\n","Costo '$'500"),
    ServicioInfo("S2","Urología","Horario\n" +
            "Lunes a Viernes 9:00 a 14:00 horas\n","Jefe del Servicio\n" +
            "Dr. Jorge Jaspersen Gastelum\n" +
            "\n","Requisitos de consulta de primera vez:\n" +
            "Carnet de la institución\n" +
            "Interconsulta\n" +
            "Recibo de pago y/o línea de captura\n" +
            "Requisitos de consultas subsecuentes:\n" +
            "Carnet\n" +
            "Recibo de pago y/o línea de captura\n" +
            "Horarios de visita para familiares de pacientes:\n" +
            "Lunes a Domingo Matutino: 12:00 a 13:00 horas Vespertino: 15:00 a 18:00 horas De acuerdo con la edad y estado de salud del paciente se otorga pase de 24 horas\n","Costo '$'700"),
    ServicioInfo("S3","CIRUGÍA PLÁSTICA Y RECONSTRUCTIVA","Horario\n" +
            "Matutino\n" +
            "L/M/M/V 08:00 a 11:00\n" +
            "Vespertino\n" +
            "Lunes a Viernes 15.00 a 18:00\n" +
            "Urgencias\n" +
            "las 24.00 hrs","Descripción del servicio\n" +
            "El Servicio de Medicina Interna cuenta con tres unidades de hospitalización, las Unidades 103-B, 108 y 110; con un total de 84 camas censables;Además, se da consulta en el turno matutino y vespertino, en el servicio de Consulta externa, en los consultorios 22 y 23.\n" +
            "\n","","Costo '\$'750"),
    ServicioInfo("S4","ONCOLOGÍA","Horario\n" +
            "L-V\n" +
            "8:00 a 13:00 hrs\n" +
            "15:00 a 19:00 hrs\n" +
            "Curaciones\n" +
            "10:30 a 13:00 hrs\n" +
            "Con cita agendada\n","Descripción del servicio\n" +
            "Atención medica-quirúrgica, radioterapia y quimioterapia a pacientes con tumores.\n" +
            "Desde 1920 se comienza a dar atención a pacientes con padecimientos malignos dentro del Hospital General de México, dando lugar así al servicio de oncología.\n" +
            "\n" +
            "Nuestro servicio se ha caracterizado por integrar un grupo de especialistas dedicados al diagnóstico oportuno, así como el tratamiento adecuado de las enfermedades malignas que afectan a cualquier órgano de nuestro organismo.\n" +
            "\n" +
            "Descripción de especialidad y subespecialidades en el servicio:\n" +
            "Unidad de Tumores Ginecológicos\n" +
            "Clinica de Colposcopia\n" +
            "Unidad de Tumores Mamarios\n" +
            "Unidad de Tumores Cabeza y Cuello\n" +
            "Unidad de Tumores Mixtos\n" +
            "Unidad de Cancer de Piel\n" +
            "Melanoma, Sarcoma de Partes Blandas y Tumores Oseos\n" +
            "Unidad de Quimioterapia\n" +
            "Unidad de Radioterapia\n" +
            "Unidad de Prótesis Maxilofacial\n" +
            "Unidad de Terapia Medica Intensiva\n" +
            "Psico-Oncología\n","Requisitos de consulta de primera vez:\n" +
            "Carnet con cita agendada\n" +
            "Hoja de interconsulta y/o referencia\n" +
            "Resumen clínico\n" +
            "Estudios de laboratorio y gabinete (con los que cuente en ese momento)\n" +
            "Línea de captura para consulta\n" +
            "Requisitos de consultas subsecuentes:\n" +
            "Carnet con cita agendada\n" +
            "Resultado de estudios de laboratorio y gabinete (en el caso de que hayan sido solicitados)\n","Horarios de visita para familiares de pacientes:\n" +
            "Lunes a Domingo 12:00 a 18:00\n"),
    ServicioInfo("S5","Laboratorio Central","Horario\n" +
            "Toma de muestras sanguíneas a pacientes externos de:\n" +
            "Lunes a viernes:\n" +
            "Pacientes adultos: de 06:30 am a 08:30 am.\n" +
            "Pacientes pediátricos: de 07:00 am a 08:30 am.\n" +
            "Sábados y Domingos\n" +
            "Pacientes adultos: de 07:30 am a 08:30 am.\n" +
            "Toma de muestras bacteriológicas\n" +
            "Pacientes adultos: lunes a viernes de 07:00 am a 08:30 am.\n" +
            "Recepción de muestras pacientes internos:\n" +
            "Pacientes adultos: lunes a domingo las 24 horas del día.\n" +
            "Pacientes pediátricos: lunes a domingo las 24 horas del día.\n","Descripción del servicio\n" +
            "El Laboratorio Central y Periféricos ofrece un servicio integrado por un grupo multidisciplinario de profesionales de la salud en apoyo al diagnóstico clínico, cuyo objetivo es brindar las herramientas que le permitan al médico integrar la información para estudiar, prevenir, diagnosticar y otorgar el mejor tratamiento a los pacientes que acuden al Hospital General de México. El Laboratorio cuenta con instalaciones modernas y equipos de vanguardia para la realización de pruebas. Está certificado por ISO 9001:2015 e inscrito en programas de Control de Calidad Externo como PACAL, QUALITAT, RIQAS y EQAS; además de procedimientos de control de calidad interno que el servicio lleva acabo. Lo que avala cada uno de nuestros procesos en temas de calidad y seguridad, así como la confiabilidad en los resultados emitidos.\n","Requisitos de consulta de primera vez:\n" +
            "Contar con carnet\n" +
            "Solicitudes de estudios\n" +
            "Orden de atención\n" +
            "Línea de captura con comprobante de pago\n" +
            "Requisitos de consultas subsecuentes:\n" +
            "Contar con carnet\n" +
            "Solicitudes de estudios\n" +
            "Orden de atención\n" +
            "Línea de captura con comprobante de pago\n",""),
    ServicioInfo("S6","Manual de Organización","","","Requisitos de consulta de primera vez:\n" +
            "Referencia de Consulta Externa del Hospital General de México\n" +
            "Interconsulta de los diferentes Servicios del Hospital\n" +
            "Cita programada\n" +
            "Línea de captura\n" +
            "Estudios previos (si cuenta con ellos)\n" +
            "Requisitos de consultas subsecuentes:\n" +
            "Cita programada\n" +
            "Línea de captura\n",""),
    ServicioInfo("S7","Consulta Externa","Horario\n" +
            "Lunes a viernes\n" +
            "Matutino: 08:00 a 13:30\n" +
            "Vespertino: 15:00 a 19:30\n","Descripción del servicio\n" +
            "Atención médica a los pacientes con padecimientos en general para su evaluación y en caso necesario derivación posterior a los servicios de especialidad cuando así se requiera. En este servicio se atienden aproximadamente el 70% de las enfermedades de los pacientes que acuden solicitando el servicio. Se cuenta con médicos certificados y capacitados para la atención de los pacientes.","Requisitos de consulta de primera vez:\n" +
            "Acudir directamente a Consulta Externa, al módulo de Relaciones Públicas/Informes con la sig. Documentación:\n" +
            "Hojas de Referencia del Hospital de Segundo nivel, centro de salud, Urgencias o Interconsulta del hospital\n" +
            "Identificación oficial Vigente (INE, PASAPORTE, CEDULA PROFESIONAL Y/O LICENCIA DE CONDUCIR)\n" +
            "CURP\n" +
            "Se otorgará ficha para asistir con medicina familiar y/o general y consultorio asignado.\n" +
            "Requisitos de consultas subsecuentes:\n" +
            "Se otorga cita de acuerdo a la programación del médico tratante.\n" +
            "En caso de que el paciente tenga más de un año sin acudir a cita al hospital, tendrá que realizar el trámite como si fuera de primera vez.\n",""),
    ServicioInfo("S8","Infectología","","Descripción del servicio\n" +
            "Cuenta con Hospitalización de pacientes con Diagnostico de Enfermedades Infecto-contagiosas.\n" +
            "\n" +
            "Servicio de hospitalización en Unidad de Cuidados Intensivos , para pacientes con Enfermedades Infecto-contagiosas criticas.\n" +
            "\n" +
            "Servicio de Consulta Externa con atención en turnos: matutino y vespertino.\n" +
            "\n" +
            "Quirófano: Se realizan procedimientos de diferentes Servicios del Hospital General de México.\n" +
            "\n","Requisitos de consulta de primera vez:\n" +
            "Referencia de Consulta Externa del Hospital General de México\n" +
            "Interconsulta de los diferentes Servicios del Hospital\n" +
            "Cita programada\n" +
            "Línea de captura\n" +
            "Estudios previos (si cuenta con ellos)\n" +
            "Requisitos de consultas subsecuentes:\n" +
            "Cita programada\n" +
            "Línea de captura\n",""),
    ServicioInfo("S9","OFTALMOLOGIA","","Descripción del servicio\n" +
            "Atención médica y quirúrgica a pacientes con padecimientos Oftalmológicos. El Servicio de Oftalmología inicia sus labores en el año de 1905, fecha desde la cual se ha caracterizado por otorgar una atención oportuna y eficiente a los usuarios que la solicitan.\n" +
            "\n" +
            "Descripción de especialidad y subespecialidades en el servicio:\n" +
            "Estrabismo y Oftalmología Pediátrica\n" +
            "Glaucoma\n" +
            "Oculoplástica\n" +
            "Retina: Segmento Anterior\n" +
            "Córnea: Uveítis\n" +
            "Visión Baja: Neuro-Oftalmología\n" +
            "Estudio de Refracción\n" +
            "\n","Requisitos de consulta de primera vez:\n" +
            "Previa Cita Matutina\n" +
            "Se reciben Carnets con Recibo de Pago o Línea de Captura de 07:30 a 09:00 horas.\n" +
            "Primera Cita Vespertino\n" +
            "Se reciben carnets con Recibo de Pago o Línea de Captura de 14:00 a 16:00 horas\n" +
            "Solicitud de estudio (referencia de especialidades del Hospital General de México o externos)\n" +
            "Requisitos de consultas subsecuentes:\n" +
            "Previa Cita Matutino\n" +
            "Se reciben Carnets con Recibo de Pago o Línea de Captura.\n" +
            "Lunes, Miércoles y Viernes de 07:30 a 10:00 horas.\n" +
            "Previa cita Vespertino\n" +
            "Se reciben Carnets con Recibo de Pago o Línea de Captura Lunes a Viernes de 14:00 a 16:00 horas.",""),
    ServicioInfo("S10","","","","","")
)

data class ServicioInfo(
    val servicioId: String,
    val nombre: String,
    val horario: String,
    val descripcion: String,
    val requisitos: String,
    val costo: String
)

@Composable
fun ServicesInfoCard(servicioinfo : ServicioInfo){
        Text(
            text = servicioinfo.nombre,
            fontFamily = FontFamily.Serif,
            fontSize = 20.sp,
        )
    Spacer(modifier = Modifier.height(15.dp))
    HorizontalDivider(thickness = 2.dp, color = onPrimaryContainerLight)
    Spacer(modifier = Modifier.height(15.dp))

    Text(
            text = servicioinfo.horario,
            fontFamily = FontFamily.Serif,
            fontSize = 12.sp
        )
    Spacer(modifier = Modifier.height(15.dp))


        Text(
            text = servicioinfo.descripcion,
            fontFamily = FontFamily.Serif,
            fontSize = 12.sp
        )
    Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = servicioinfo.requisitos,
            fontFamily = FontFamily.Serif,
            fontSize = 12.sp
        )

    if (servicioinfo.costo.isEmpty()){
        OutlinedButton(
            onClick = { }
        ) {
            Text("Solicitar")
        }
    } else {
        Row {
            Text(
                text = servicioinfo.costo,
                fontFamily = FontFamily.Serif,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedButton(
                onClick = { }
            ) {
                Text("Solicitar")
            }
        }
    }

        Spacer(modifier = Modifier.height(100.dp))

    OutlinedButton(
        onClick = {}
    ) {
        Text("Fin de la pagina")
    }
}

