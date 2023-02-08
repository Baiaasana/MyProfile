package com.example.myprofile.presenter.ui.fragment.wallets

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myprofile.common.BaseFragment
import com.example.myprofile.databinding.FragmentWalletsBinding
import com.example.myprofile.presenter.adapter.WalletAdapter
import com.example.myprofile.presenter.model.WalletUI
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WalletsFragment : BaseFragment<FragmentWalletsBinding>(FragmentWalletsBinding::inflate) {

    private val viewModel: WalletsViewModel by viewModels()
    private val walletAdapter: WalletAdapter = WalletAdapter()

    private val args: WalletsFragmentArgs by navArgs()

    var id : Int? = 1

    @Inject lateinit var reference: FirebaseDatabase

    override fun listeners() {

        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigate(WalletsFragmentDirections.actionWalletsFragmentToConvertFragment())
            }
            binding.btnContinue.setOnClickListener {

                if(args.type == "from"){
                    findNavController().navigate(WalletsFragmentDirections.actionWalletsFragmentToConvertFragment(type = args.type, fromID = id!!, toID = args.toID))
                }
                if(args.type == "to"){
                    findNavController().navigate(WalletsFragmentDirections.actionWalletsFragmentToConvertFragment(type = args.type, toID = id!!, fromID = args.fromID))
                }
            }
        }

        walletAdapter.onWalletClickListener = { wallet ->
            id = wallet.id
        }
    }

    override fun init() {
        initRecycler()
    }

    private fun initRecycler() {
        binding.rvWallets.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = walletAdapter
        }

        val list = mutableListOf<WalletUI>()
        reference.getReference("wallets")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val item = it.getValue(WalletUI::class.java) ?: return
                        list.add(item)
                    }
                    walletAdapter.submitList(list)
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    override fun observers() {

    }
}