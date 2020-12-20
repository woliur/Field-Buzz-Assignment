package com.example.fieldbuzz.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.fieldbuzz.R
import com.example.fieldbuzz.apiUtil.Resource
import com.example.fieldbuzz.model.LoginPayload
import com.example.fieldbuzz.util.UtilityMethods.hideKeyboard
import com.example.fieldbuzz.util.UtilityMethods.validateTextField
import com.example.fieldbuzz.viewmodel.LogInViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_log_in.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LogInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LogInFragment : Fragment() {

    private var navController: NavController? = null
    private var viewModel: LogInViewModel? = null
    private var loginPayload: LoginPayload = LoginPayload()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(this).get(LogInViewModel::class.java)

        clickEvent()
        setObserver()
    }

    private fun clickEvent(){
        loginButton.setOnClickListener(listener)
    }

    private val listener = View.OnClickListener {
        when(it.id){
            R.id.loginButton -> {
                if (validateTextField(userName) && validateTextField(passWord)) {
                    //Toast.makeText(context,"Tada!" ,Toast.LENGTH_SHORT).show()
                    hideKeyboard(requireActivity())
                    login()
                }
            }
        }
    }

    private fun login(){
        if (!userName.editText?.text.toString().isNullOrEmpty()){
            loginPayload.username = userName.editText?.text.toString()
        }
        if (!passWord.editText?.text.toString().isNullOrEmpty()){
            loginPayload.password = passWord.editText?.text.toString()
        }

        loginProgress.visibility = View.VISIBLE
        loginGroup.visibility = View.GONE

        viewModel?.postCredintial(loginPayload)
    }

    private fun setObserver(){
        viewModel?.credentials?.observe(viewLifecycleOwner, Observer {
            if (it.status == Resource.Status.SUCCESS) {
                UserInfoFragment.token = it.data?.token.toString()
                navController?.navigate(R.id.action_logInFragment_to_userInfoFragment)

            } else if (it.status == Resource.Status.ERROR) {
                loginProgress.visibility = View.GONE
                loginGroup.visibility = View.VISIBLE
                Snackbar.make(loginParent,it.data?.message.toString(), Snackbar.LENGTH_SHORT).show()
            }
        })
    }
}