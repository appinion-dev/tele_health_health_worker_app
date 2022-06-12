package com.aah.sftelehealthworker.network.retrofit;

import com.aah.sftelehealthworker.models.ResponseModel;
import com.aah.sftelehealthworker.models.appoinment.CategoriesModel;
import com.aah.sftelehealthworker.models.appoinment.CreateAppointmentModel;
import com.aah.sftelehealthworker.models.appoinment.DoctorsInfoModel;
import com.aah.sftelehealthworker.models.appoinment.DoctorsModel;
import com.aah.sftelehealthworker.models.appoinment.TimeSlotsModel;
import com.aah.sftelehealthworker.models.home.AppointmentModel;
import com.aah.sftelehealthworker.models.home.PatientProfileModel;
import com.aah.sftelehealthworker.models.login.HealthWorkerModel;
import com.aah.sftelehealthworker.models.login.OtpModel;
import com.aah.sftelehealthworker.models.login.PhoneNo;
import com.aah.sftelehealthworker.models.newPatient.DistrictUpazillasModel;
import com.aah.sftelehealthworker.models.newPatient.PatientProfile;
import com.aah.sftelehealthworker.models.newPatient.PrescriptionsModel;
import com.aah.sftelehealthworker.models.newPatient.Report;
import com.aah.sftelehealthworker.models.newPatient.ReportsModel;
import com.aah.sftelehealthworker.models.newPatient.Vital;
import com.aah.sftelehealthworker.models.newPatient.VitalsModel;
import com.aah.sftelehealthworker.models.newPatient.VitalsSubmitModel;
import com.aah.sftelehealthworker.models.patient.PatientAppointment;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "authorization: Bearer p2S80tdxdrVjGC9F6wbn"})
    @POST("healthworker/sendOtp")
    Call<OtpModel> requestWithLoginPhoneNo(@Body PhoneNo phoneNo);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "authorization: Bearer p2S80tdxdrVjGC9F6wbn"})
    @POST("healthworker/login")
    Call<HealthWorkerModel> requestHealthWorker(@Body PhoneNo phoneNo);

    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @POST("healthworker/branchwisepatients")
    Call<PatientProfileModel> requestBranchWisePatients(@Header("authorization") String token,
                                                        @Query("branch_id") String branchId,
                                                        @Query("page") String page, @Query("size") String size,
                                                        @Query("phone") String phone);

    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @POST("callback/getCallbacksForBranch")
    Call<AppointmentModel> requestAppointmentList(@Header("authorization") String token, @Query("branchId") String branchId,
                                                  @Query("page") String page, @Query("size") String size,
                                                  @Query("status") String status, @Query("search") String search, @Query("date") String date);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "authorization: Bearer p2S80tdxdrVjGC9F6wbn"})
    @POST("user/sendOtp")
    Call<OtpModel> requestPatientWithPhone(@Body PhoneNo phoneNo);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"})
    @POST("healthworker/verifyPatientOtp")
    Call<ResponseModel> requestPatientWithOtp(@Header("authorization") String token, @Body PhoneNo phoneNo);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"})
    @POST("misc/getDistrictUpazillas")
    Call<DistrictUpazillasModel> requestDistrictUpazillas();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"})
    @POST("healthworker/createPatient")
    Call<PatientProfileModel> requestCreatePatient(@Header("authorization") String token, @Body PatientProfile patientProfile);

    @Multipart
    @POST("healthworker/uploadPatientImage")
    Call<ResponseModel> requestCreatePatientImage(@Header("Authorization") String token,
                                                  @Part("id") RequestBody id,
                                                  @Part("modelProperty") RequestBody modelProperty,
                                                  @Part("path") RequestBody path,
                                                  @Part("model") RequestBody model,
                                                  @Part MultipartBody.Part image);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"})
    @POST("healthworker/getOnePatient/{patientId}")
    Call<PatientProfile> requestPatient(@Header("authorization") String token, @Path("patientId") String patientId);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"})
    @GET("doctor/getCategories")
    Call<CategoriesModel> requestCategories(@Header("authorization") String token);

    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @GET("doctor/getCategoryWiseDoctors/{categoryId}}")
    Call<DoctorsModel> requestDoctorList(@Header("authorization") String token, @Path("categoryId") String categoryId, @Query("page") String page, @Query("size") String size);

    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @GET("doctor/getAvailableTimeslots")
    Call<TimeSlotsModel> requestTimeSlots(@Header("authorization") String token, @Query("doctorId") String doctorId, @Query("categoryId") String categoryId, @Query("date") String date);

    //healthworker/getCallbackCost?doctorId=391&timeslotId=219
    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @GET("healthworker/getCallbackCost")
    Call<DoctorsInfoModel> requestDoctorPatientDetails(@Header("authorization") String token, @Query("doctorId") String doctorId, @Query("timeslotId") String timeslotId);
//    @GET("healthworker/getDoctorPatientDetails")
//    Call<DoctorsInfoModel> requestDoctorPatientDetails(@Header("authorization") String token, @Query("doctorId")String doctorId, @Query("patientId")String patientId);

    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @POST("callback")
    Call<ResponseModel> requestCreateAppointment(@Header("authorization") String token, @Body CreateAppointmentModel createAppointmentModel);

    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @GET("patient/getVitals/{patientId}")
    Call<VitalsModel> requestVitals(@Header("authorization") String token, @Path("patientId") String patientId);

    //@api: {get} /prescription/hwGetPatientPrescriptions?patientId=59302
    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @GET("prescription/hwGetPatientPrescriptions")
    Call<PrescriptionsModel> requestPrescription(@Header("authorization") String token, @Query("patientId") String patientId, @Query("caseId") String caseId);

    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @GET("patient/getReports/{patientId}")
    Call<ReportsModel> requestReports(@Header("authorization") String token, @Path("patientId") String patientId);


    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @GET("patient/hwGetAppointments")
    Call<PatientAppointment> requestPatientAppointment(@Header("authorization") String token,
                                                       @Query("patientId") String patientId,
                                                       @Query("page") String page,
                                                       @Query("size") String size,
                                                       @Query("status") String status,
                                                       @Query("date") String date);

    //    @Headers({"Accept: application/json",
//            "Content-Type: application/json"})
    @POST("patient/hwUploadVital")
    Call<ResponseModel> submitVitals(@Header("authorization") String token, @Body VitalsSubmitModel vitalsSubmitModel);


    @Multipart
    @POST("patientdocument/hwUploadDocument")
    Call<ResponseModel> submitDocument(
            @Header("Authorization") String authToken,
            //@Body UploadDocumentRequest uploadDocumentRequest
            @Part("model") RequestBody model,
            @Part("modelProperty") RequestBody modelProperty,
            @Part("path") RequestBody path,
            @Part("patientId") RequestBody patientId,
            @Part("documentCategoryId") RequestBody documentCategoryId,
            @Part("title") RequestBody title,
            @Part("note") RequestBody note,
            //@Part("image") RequestBody image
            @Part MultipartBody.Part image
    );
    //POST 201 Created https://telehealthapi.appinionbd.com/api/v2/patient/uploadVital (80ms)
    //{"patientId":59374,"bloodPressure":"90/110","pulseRate":"75","respirationRate":"13","bloodSuger":"9","bloodTemperature":"95","bodyWeight":"80"}
    //<-- GET 200 OK https://telehealthapi.appinionbd.com/api/v2/patient/patientWiseVitalList?patientId=59374 (873ms)
    //{"data":[{"patientId":59374,"bloodTemperature":"95","pulseRate":"75","respirationRate":"13","bloodPressure":"90/110","bodyWeight":"80","bloodSuger":"9","id":3,"createdAt":"2020-10-13T09:18:01.000Z","updatedAt":"2020-10-13T09:18:01.000Z"}]}
}
