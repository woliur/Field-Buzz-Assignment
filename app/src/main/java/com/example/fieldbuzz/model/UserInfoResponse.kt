package com.example.fieldbuzz.model


import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class UserInfoResponse(
        @SerializedName("tsync_id")
        val tsyncId: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("full_address")
        val fullAddress: String,
        @SerializedName("name_of_university")
        val nameOfUniversity: String,
        @SerializedName("graduation_year")
        val graduationYear: Int,
        @SerializedName("cgpa")
        val cgpa: Double,
        @SerializedName("experience_in_months")
        val experienceInMonths: Int,
        @SerializedName("current_work_place_name")
        val currentWorkPlaceName: String,
        @SerializedName("applying_in")
        val applyingIn: String,
        @SerializedName("expected_salary")
        val expectedSalary: Int,
        @SerializedName("field_buzz_reference")
        val fieldBuzzReference: String,
        @SerializedName("github_project_url")
        val githubProjectUrl: String,
        @SerializedName("cv_file")
        val cvFile: CvFile,
        @SerializedName("on_spot_update_time")
        val onSpotUpdateTime: Long,
        @SerializedName("on_spot_creation_time")
        val onSpotCreationTime: Long,
        @SerializedName("success")
        val success: Boolean,
        @SerializedName("message")
        val message: String
) {
    data class CvFile(
            @SerializedName("id")
            val id: Int,
            @SerializedName("tsync_id")
            val tsyncId: String,
            @SerializedName("code")
            val code: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("path")
            val path: String,
            @SerializedName("extension")
            val extension: Any?,
            @SerializedName("description")
            val description: Any?,
            @SerializedName("file")
            val `file`: MultipartBody.Part,
            @SerializedName("date_created")
            val dateCreated: Long,
            @SerializedName("last_updated")
            val lastUpdated: Long
    )
}