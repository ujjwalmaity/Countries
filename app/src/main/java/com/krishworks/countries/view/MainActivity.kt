package com.krishworks.countries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.krishworks.countries.R
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.krishworks.countries.model.Country
import com.krishworks.countries.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ListViewModel
    private lateinit var countries: MutableLiveData<List<Country>>
    private val countryListAdapter = CountryListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        countries = viewModel.getCountryList()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countryListAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
            swipeRefreshLayout.isRefreshing = false
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        countries.observe(this, Observer { countries ->
            countries?.let { countryListAdapter.updateCountries(it) }
        })

        viewModel.countryLoadError.observe(this, Observer { isError ->
            isError?.let { errorMsg.visibility = if (it) View.VISIBLE else View.GONE }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                if (it) {
                    recyclerView.visibility = View.GONE
                    errorMsg.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                } else {
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
        })
    }
}
