package com.example.fieldbuzz.view.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.fieldbuzz.R
import com.example.fieldbuzz.apiUtil.Resource
import com.example.fieldbuzz.util.AppConstants
import com.example.fieldbuzz.viewmodel.UserInfoViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_file_upload.*
import kotlinx.android.synthetic.main.fragment_user_info.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FileUploadFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FileUploadFragment : Fragment() {

    private val PICK_FILE = 1
    private var viewModel: UserInfoViewModel? = null
    private var navController: NavController? = null
    //private lateinit var part: MultipartBody.Part
    private var part: MultipartBody.Part? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_file_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(this).get(UserInfoViewModel::class.java)
        clickEvent()
        setObserver()
    }
    private fun clickEvent() {
        btnSelectFile.setOnClickListener(listener)
        btnSubmit.setOnClickListener(listener)
    }

    private val listener = View.OnClickListener {
        when (it.id) {
            R.id.btnSelectFile -> {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    pickFileFromStorage()
                } else {
                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), AppConstants.STORAGE_PERMISSION_CODE)
                }
            }
            R.id.btnSubmit -> {
                fileProgress.visibility = View.VISIBLE
                fileGroup.visibility = View.GONE
                viewModel?.postFile(UserInfoFragment.token, file_id, part!!)
            }
        }
    }

    private fun setObserver() {

        viewModel?.fileupload?.observe(viewLifecycleOwner, Observer {
            if (it.status == Resource.Status.SUCCESS) {
                fileProgress.visibility = View.GONE
                fileGroup.visibility = View.VISIBLE
                Toast.makeText(context,"success", Toast.LENGTH_SHORT).show()
                //Snackbar.make(fileParent, it.data?.message.toString(), Snackbar.LENGTH_SHORT).show()
            } else if (it.status == Resource.Status.ERROR) {
                fileProgress.visibility = View.GONE
                fileGroup.visibility = View.VISIBLE
                Snackbar.make(fileParent, it.message.toString(), Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == AppConstants.STORAGE_PERMISSION_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickFileFromStorage()
            }
        }
    }

    companion object {
        var file_id: Int = 0
        var file_tsyncId: String = String()
    }

    private fun pickFileFromStorage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, PICK_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE && resultCode == Activity.RESULT_OK && data != null) {

            val filepath = data.dataString
            val file = File(filepath!!)
            val reqFile = RequestBody.create(MediaType.parse("*/*"), file)
            part = MultipartBody.Part.createFormData("file", file.name, reqFile)

        }
    }

}