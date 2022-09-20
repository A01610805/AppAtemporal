package com.example.appatemporal.framework.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appatemporal.R
import com.example.appatemporal.data.network.dataclasses.DashPieModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.dashboard.*

class Dashboard : AppCompatActivity(){
    private lateinit var ourPieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        // En el onCreate se deben poblar las graficas
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

        // Declaraciom de datos dinamicos
        val eventCount = findViewById<TextView>(R.id.eventCount)
        ourPieChart = findViewById(R.id.dashPieChart)


        populateEventCount()
        populatePieChart()

        activityTest()
    }

    // Ejemplo de poblar ingresos de evento

    private fun populateEventCount() {

        // Aqui debe de recuperar los datos de Firebase y asignarlos a la variable eventCountEntry

        val eventCountEntry = "PRUEBA"

        eventCount.text = eventCountEntry

    }

    // Ejemplo de poblar la grafica de pastel

    private fun populatePieChart() {
        // Aqui se reciben los datos en teoria
        val ourPieEntry = ArrayList<PieEntry>()

        ourPieEntry.add(PieEntry(100f, "Asistencias Totales"))
        ourPieEntry.add(PieEntry(250f, "Asistencias Esperadas"))

        val ourSet = PieDataSet(ourPieEntry, "")
        val data = PieData(ourSet)

        // De aqui para abajo es formato

        val pieShades: ArrayList<Int> = ArrayList()
        pieShades.add(Color.parseColor("#FFBB86FC"))
        pieShades.add(Color.parseColor("#FE810E"))

        ourSet.colors = pieShades
        ourPieChart.data = data

        data.setValueTextColor(Color.DKGRAY)
        data.setValueTextSize(20f)

        ourPieChart.getLegend().setTextColor(Color.DKGRAY)
        ourPieChart.getDescription().setTextColor(Color.DKGRAY)
        ourPieChart.setEntryLabelColor(Color.DKGRAY)

        ourPieChart.description.isEnabled = false
        ourPieChart.setDrawEntryLabels(false)
    }

    fun activityTest(){
        eventCount.setOnClickListener{
            val intent = Intent(this, ListEventsProfits::class.java)
            startActivity(intent)
        }
    }
}