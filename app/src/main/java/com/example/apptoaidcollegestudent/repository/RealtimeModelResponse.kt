package com.example.apptoaidcollegestudent.repository

data class RealtimeModelResponse(
    val item : RealtimeItems?,
    val key : String? = ""
){
    data class RealtimeItems(
        val title : String? = "",
        val description : String? = "",
        val skills : String? = "",
        val whocanapply : String? = "",
        val noofopening : String? ="",
        val nameofteacher : String = ""
    )

}

data class RealtimeModelResponse2(
    val item : MedicalData?,
    val key : String? = ""
){
    data class MedicalData(
        val name : String = "",
        val hostel : String = "",
        val roomNo : String = "",
        val contact : String = "",
        val problem : String = "",
        val need : String = ""
    )

}
