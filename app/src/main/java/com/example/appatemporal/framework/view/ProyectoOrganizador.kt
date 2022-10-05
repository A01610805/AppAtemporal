package com.example.appatemporal.framework.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appatemporal.databinding.ProyectosOrganizadorBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.view.adapters.ProjectsAdapter
import com.example.appatemporal.framework.viewModel.ProyectoOrganizadorViewModel
import kotlinx.coroutines.launch

class ProyectoOrganizador : AppCompatActivity() {
    private val  viewModel : ProyectoOrganizadorViewModel by viewModels()
    private lateinit var binding: ProyectosOrganizadorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProyectosOrganizadorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = Repository(this)
        binding.tvCompletedProject.setOnClickListener{
            binding.tvCompletedProject.getBackground().setAlpha(70);
            binding.tvNoCompletedProject.getBackground().setAlpha(255);
            viewModel.getAllProjectsCompleted(true,repository)
            viewModel.projects.observe(this, Observer { projectList ->
                Log.d("Prueba", projectList.toString())
                binding.recyclerViewProjects.layoutManager = LinearLayoutManager(this)
                binding.recyclerViewProjects.adapter = ProjectsAdapter(projectList, viewModel)
            })
        }
        binding.tvNoCompletedProject.setOnClickListener{
            binding.tvNoCompletedProject.getBackground().setAlpha(70);
            binding.tvCompletedProject.getBackground().setAlpha(255);
            viewModel.getAllProjectsCompleted(false,repository)
            viewModel.projects.observe(this, Observer { projectList1 ->
                Log.d("Prueba", projectList1.toString())
                binding.recyclerViewProjects.layoutManager = LinearLayoutManager(this)
                binding.recyclerViewProjects.adapter = ProjectsAdapter(projectList1, viewModel)
            })
        }
        viewModel.getProjects(repository)
        viewModel.projects.observe(this, Observer { projectList ->
            if (projectList.isEmpty()) {
                Toast.makeText(this, "no se encontraron resultados", Toast.LENGTH_SHORT).show()
            }
            Log.d("Prueba", projectList.toString())
            binding.recyclerViewProjects.layoutManager = LinearLayoutManager(this)
            binding.recyclerViewProjects.adapter = ProjectsAdapter(projectList, viewModel)
        })

        binding.tvNewProject.setOnClickListener {
            val intent = Intent(this, AddNewProjectForm::class.java)
            startActivity(intent)
        }

        binding.navbar.homeIcon.setOnClickListener {
            finish()
        }

        binding.navbar.budgetIcon.setOnClickListener {
            val intent = Intent(this, ProyectoOrganizador::class.java)
            startActivity(intent)
        }

        binding.navbar.ticketsIcon.setOnClickListener {
            finish()
        }

        binding.navbar.metricsIcon.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }
    }
}