package com.abdosharaf.sleeptrackerrecycler.listScreen

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.abdosharaf.sleeptrackerrecycler.R
import com.abdosharaf.sleeptrackerrecycler.database.SleepNightsDatabase.Companion.getDatabase
import com.abdosharaf.sleeptrackerrecycler.databinding.FragmentListBinding
import com.abdosharaf.sleeptrackerrecycler.dialogs.DeleteDialog
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: ListViewModel
    private lateinit var dialog: DeleteDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentListBinding.inflate(inflater, container, false)

        val database = getDatabase(requireContext())
        val viewModelFactory = ListViewModelFactory(database)
        viewModel = ViewModelProvider(this, viewModelFactory)[ListViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // TODO: Animation does the problem of inflation
//        binding.rvNights.itemAnimator = null

        val adapter = SleepNightsAdapter { night ->
            findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailsFragment(night))
        }
        binding.rvNights.adapter = adapter


        binding.btnClear.setOnClickListener {
            dialog = DeleteDialog(requireContext())
            dialog.showDialog()
            dialog.binding.tvDialogText.setText(R.string.want_clear_all)
            dialog.deleteCall = {
                viewModel.clearAll()
            }
        }


        viewModel.navigateToQualityScreen.observe(viewLifecycleOwner) { id ->
            id?.let {
                findNavController().navigate(ListFragmentDirections.actionListFragmentToQualityFragment(id))
                viewModel.doneNavigating()
            }
        }


        viewModel.sleepNights.observe(viewLifecycleOwner) { nights ->
            binding.loader.isVisible = false

            // TODO: Check why the list isn't updated in Logs immediately
            adapter.formatListAndSubmit(nights.toMutableList())

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


        setHasOptionsMenu(true)

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        viewModel.initializeTonight()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.app_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_exit -> requireActivity().finishAffinity()
            // TODO: Return the language & layout icon
//            R.id.mi_layout -> changeLayout()
//            R.id.mi_language -> changeLang()
        }
        return super.onOptionsItemSelected(item)
    }
}