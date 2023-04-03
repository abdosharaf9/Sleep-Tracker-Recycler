package com.abdosharaf.sleeptrackerrecycler.listScreen

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdosharaf.sleeptrackerrecycler.R
import com.abdosharaf.sleeptrackerrecycler.database.SleepNightsDatabase.Companion.getDatabase
import com.abdosharaf.sleeptrackerrecycler.databinding.FragmentListBinding
import com.abdosharaf.sleeptrackerrecycler.utils.Constants.TAG
import com.abdosharaf.sleeptrackerrecycler.utils.LangChanger.changeLang
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentListBinding.inflate(inflater, container, false)

        val database = getDatabase(requireContext())
        val viewModelFactory = ListViewModelFactory(database)
        viewModel = ViewModelProvider(this, viewModelFactory)[ListViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // TODO: Animation does the problem of inflation
//        binding.rvNights.itemAnimator = null
        binding.rvNights.layoutManager = LinearLayoutManager(requireContext())

        val adapter = SleepNightsAdapter { night ->
            findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailsFragment(night))
        }
        binding.rvNights.adapter = adapter

        // Go to quality screen when the user click stop tracking
        viewModel.navigateToQualityScreen.observe(viewLifecycleOwner) { id ->
            id?.let {
                findNavController().navigate(ListFragmentDirections.actionListFragmentToQualityFragment(id))
                viewModel.doneNavigating()
            }
        }

        // Observe the nights list to inflate items or empty state
        viewModel.sleepNights.observe(viewLifecycleOwner) { nights ->
            binding.loader.isVisible = false

            // TODO: Check why the list isn't updated in Logs immediately
            adapter.submitList(nights)

            if(nights.isNullOrEmpty()){
                binding.emptyState.isVisible = true
                binding.rvNights.isVisible = false
            } else {
                binding.emptyState.isVisible = false
                binding.rvNights.isVisible = true
            }
        }

        viewModel.showSnackBar.observe(viewLifecycleOwner) { show ->
            if(show){
                Snackbar.make(requireContext(), requireView(), getString(R.string.cleared), Snackbar.LENGTH_SHORT).show()
                viewModel.doneSnackBar()
            }
        }

        // Set up the top options menu
        setHasOptionsMenu(true)

        return binding.root
    }

    // Get tonight when the user get back from the quality screen
    override fun onResume() {
        super.onResume()
        viewModel.initializeTonight()
    }

    // Inflate the options menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.app_menu, menu)
    }

    // What to do if the options menu item is selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_exit -> requireActivity().finishAffinity()
            // TODO: Return the language icon
//            R.id.mi_language -> changeLang()
        }
        return super.onOptionsItemSelected(item)
    }
}