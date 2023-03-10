package ru.scid.supportchat.presentation.ui.createChat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.scid.supportchat.databinding.FragmentCreateChatBinding

@AndroidEntryPoint
class CreateChatFragment : Fragment() {

    private var _binding: FragmentCreateChatBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreateChatBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        setUpObservers()
    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.ticketCreated.collectLatest {
                val action =
                    CreateChatFragmentDirections.actionCreateChatFragmentToChatFragment(it.id)
                findNavController().navigate(action)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.onError.collectLatest {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun setUpListeners() {
        binding.editTextSubject.addTextChangedListener {
            viewModel.onSubjectTextChanged(it.toString())
        }
        binding.editTextDescription.addTextChangedListener {
            viewModel.onDescriptionTextChanged(it.toString())
        }
        binding.sendBtn.setOnClickListener {
            viewModel.onCreateClicked()
        }
    }

}