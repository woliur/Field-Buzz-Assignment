package com.example.fieldbuzz.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.fieldbuzz.R
import com.example.fieldbuzz.apiUtil.Resource
import com.example.fieldbuzz.model.CvFile
import com.example.fieldbuzz.model.UserInfoPayload
import com.example.fieldbuzz.util.UtilityMethods
import com.example.fieldbuzz.util.UtilityMethods.extractDouble
import com.example.fieldbuzz.util.UtilityMethods.extractInt
import com.example.fieldbuzz.util.UtilityMethods.extractText
import com.example.fieldbuzz.util.UtilityMethods.hideKeyboard
import com.example.fieldbuzz.util.UtilityMethods.validateMinMax
import com.example.fieldbuzz.util.UtilityMethods.validateTextField
import com.example.fieldbuzz.viewmodel.LogInViewModel
import com.example.fieldbuzz.viewmodel.UserInfoViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_log_in.*
import kotlinx.android.synthetic.main.fragment_user_info.*

class UserInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var navController: NavController? = null
    private var viewModel: UserInfoViewModel? = null
    private var userInfoPayload = UserInfoPayload()
    private var cvFile = CvFile()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(this).get(UserInfoViewModel::class.java)

        clickEvent()
        setObserver()
    }


    private fun clickEvent() {
        userInfoButton.setOnClickListener(listener)
    }

    private fun setObserver() {
        viewModel?.userInfo?.observe(viewLifecycleOwner, Observer {
            if (it.status == Resource.Status.SUCCESS) {
                FileUploadFragment.file_id = it.data?.cvFile?.id!!
                FileUploadFragment.file_tsyncId = it.data?.cvFile?.tsyncId.toString()
                navController?.navigate(R.id.action_userInfoFragment_to_fileUploadFragment)

            } else if (it.status == Resource.Status.ERROR) {
                progressbar.visibility = View.GONE
                userInfoGroup.visibility = View.VISIBLE
                Snackbar.make(userInfoParent,it.data?.message.toString(), Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private val listener = View.OnClickListener {
        when (it.id) {
            R.id.userInfoButton -> {
                if (validateTextField(name) && validateTextField(email) && validateTextField(phone)
                        && validateTextField(name_of_university) && validateTextField(graduation_year)
                        && validateTextField(applying_in) && validateTextField(expected_salary) && validateTextField(github_project_url)
                        && validateTextField(cgpa) && validateTextField(field_buzz_reference) && validateTextField(current_work_place_name)) {
                    if (validateMinMax(graduation_year, 2015, 2020) && validateMinMax(expected_salary, 15000, 60000)) {
                        hideKeyboard(requireActivity())
                        uploadInfo()
                    }
                }
            }
        }
    }

    private fun uploadInfo() {
        Toast.makeText(context, "Tada!", Toast.LENGTH_SHORT).show()
        userInfoPayload.tsyncId = "t-i-d-${token}"
        userInfoPayload.name = extractText(name)
        userInfoPayload.email = extractText(email)
        userInfoPayload.phone = extractText(phone)
        userInfoPayload.fullAddress = extractText(full_address)
        userInfoPayload.nameOfUniversity = extractText(name_of_university)
        userInfoPayload.graduationYear = extractInt(graduation_year)
        userInfoPayload.cgpa = extractDouble(cgpa)
        userInfoPayload.experienceInMonths = extractInt(experience_in_months)
        userInfoPayload.currentWorkPlaceName = extractText(current_work_place_name)
        userInfoPayload.applyingIn = extractText(applying_in)
        userInfoPayload.expectedSalary = extractInt(expected_salary)
        userInfoPayload.fieldBuzzReference = extractText(field_buzz_reference)
        userInfoPayload.githubProjectUrl = extractText(github_project_url)

        cvFile.tsyncId = "c-v-${token}"
        userInfoPayload.cvFile = cvFile

        viewModel?.postInfo(token, userInfoPayload)

        progressbar.visibility = View.VISIBLE
        userInfoGroup.visibility = View.GONE
    }

    companion object {

        var token: String = String()
    }
}