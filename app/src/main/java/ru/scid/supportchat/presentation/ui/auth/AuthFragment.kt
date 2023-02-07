package ru.scid.supportchat.presentation.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.scid.supportchat.databinding.FragmentAuthBinding

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        setUpObservers()
    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.onAuthSuccess.collectLatest {
                navigateToChats()
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.onError.collectLatest {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.btnAuth.isVisible = !it
            binding.pbLoaderAuth.isVisible = it
        }
    }

    private fun navigateToChats() {
        val action =
            AuthFragmentDirections.actionAuthFragmentToChatsFragment()
        findNavController().navigate(action)
    }

    private fun setUpListeners() {
        binding.btnAuth.setOnClickListener {
            viewModel.auth()
        }
        binding.tieName.addTextChangedListener {
            viewModel.onNameChanged(it.toString())
        }
        binding.tieEmail.addTextChangedListener {
            viewModel.onEmailChanged(it.toString())
        }
        binding.tiePassword.addTextChangedListener {
            viewModel.onPasswordChanged(it.toString())
        }
    }

}