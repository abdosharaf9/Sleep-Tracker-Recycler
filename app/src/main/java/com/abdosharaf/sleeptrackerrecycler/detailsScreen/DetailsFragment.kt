package com.abdosharaf.sleeptrackerrecycler.detailsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.abdosharaf.sleeptrackerrecycler.database.SleepNightsDatabase.Companion.getDatabase
import com.abdosharaf.sleeptrackerrecycler.databinding.FragmentDetailsBinding
import com.abdosharaf.sleeptrackerrecycler.dialogs.DeleteDialog

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var dialog: DeleteDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)

        val database = getDatabase(requireContext())
        val factory = DetailsViewModelFactory(database)
        val viewModel = ViewModelProvider(this, factory)[DetailsViewModel::class.java]

        binding.night = args.night
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateBack.observe(viewLifecycleOwner) { navigate ->
            if(navigate) {
                findNavController().navigateUp()
                viewModel.doneNavigation()
            }
        }

        binding.btnDelete.setOnClickListener {
            dialog = DeleteDialog(requireContext())
            dialog.showDialog()
            dialog.deleteCall = {
                viewModel.delete(args.night)
            }
        }

        return binding.root
    }
}