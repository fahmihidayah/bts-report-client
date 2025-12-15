package com.polytech.btsreport.presentation.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.polytech.btsreport.databinding.HomeActivityBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var visitationAdapter: VisitationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Home"

        setupRecyclerView()
        observeVisitationsState()

        binding.fab.setOnClickListener {
            startActivity(android.content.Intent(this, com.polytech.btsreport.presentation.report.ReportActivity::class.java))
        }

        homeViewModel.loadVisitations()
    }

    private fun setupRecyclerView() {
        visitationAdapter = VisitationAdapter { visitation ->
            val intent = android.content.Intent(this, com.polytech.btsreport.presentation.report.ReportActivity::class.java).apply {
                putExtra("VISITATION_ID", visitation.id)
                putExtra("VISITATION_STATUS", visitation.visitStatus)
                putExtra("SITE_NAME", visitation.siteName)
            }
            startActivity(intent)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = visitationAdapter
        }
    }

    private fun observeVisitationsState() {
        homeViewModel.visitations.observe(this) { visitations ->
            visitationAdapter.submitList(visitations)
        }

        homeViewModel.visitationsState.observe(this) { state ->
            when (state) {
                is VisitationsState.Idle -> {
                    // Do nothing
                }
                is VisitationsState.Loading -> {
                    Toast.makeText(this, "Loading visitations...", Toast.LENGTH_SHORT).show()
                }
                is VisitationsState.Success -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
                is VisitationsState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}