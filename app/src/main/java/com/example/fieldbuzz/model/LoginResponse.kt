package com.example.fieldbuzz.model


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("organization_name")
    val organizationName: String,
    @SerializedName("app_top_module_assignment")
    val appTopModuleAssignment: List<Any>,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("auth_info")
    val authInfo: AuthInfo,
    @SerializedName("organization_logo")
    val organizationLogo: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("organization_info")
    val organizationInfo: OrganizationInfo
) {
    data class AuthInfo(
        @SerializedName("user_id")
        val userId: Int,
        @SerializedName("user_type")
        val userType: String,
        @SerializedName("is_first_login")
        val isFirstLogin: Boolean,
        @SerializedName("name")
        val name: String,
        @SerializedName("assigned_to")
        val assignedTo: Int,
        @SerializedName("user_photo")
        val userPhoto: Any?,
        @SerializedName("role_name")
        val roleName: String,
        @SerializedName("role_id")
        val roleId: Int,
        @SerializedName("designation")
        val designation: Any?,
        @SerializedName("username")
        val username: String
    )

    data class OrganizationInfo(
        @SerializedName("project_name")
        val projectName: String,
        @SerializedName("vat_registration_number")
        val vatRegistrationNumber: String,
        @SerializedName("translated_project_name")
        val translatedProjectName: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("organization_status")
        val organizationStatus: Int,
        @SerializedName("code")
        val code: String,
        @SerializedName("name")
        val name: String
    )
}