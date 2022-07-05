package com.aah.sftelehealthworker.utils

import android.os.Environment

//const val BASE_URL = "http://192.168.3.13:1337/api/v2/"
const val BASE_URL = "https://telehealthapi.appinionbd.com/api/v2/"
const val CLIENT_SECRET = "Bearer p2S80tdxdrVjGC9F6wbn"
const val GRANT_TYPE = "password"
const val CLIENT_ID = "1"
const val TOKEN_PREFIX = "Bearer "


const val DATE_FORMAT_1 = "dd MMMM yyyy"
const val DATE_FORMAT_2 = "dd MMM yy"
const val APPLICATION_NAME = "APPINION_FIELD_FORCE"
const val DATABASE_NAME = "appinionfieldforce-database"

const val IMAGE_CAPTURE = 0
const val SHARED_PREFERENCE_NAME = "APPINION_FIELD_FORCE"
const val SHARED_PREFERENCE_LANGUAGE_KEY = "LANGUAGE"
const val GOOGLE_PDF_VIEWER_LINK_PREFIX =
    "https://docs.google.com/gview?embedded=true&url="
const val SHARED_PREFERENCE_LOGIN_KEY = "Login"
const val SHARED_PREFERENCE_TOKEN_KEY = "Token"
const val SHARED_PREFERENCE_OFFICE_ID = "OfficeId"
const val SHARED_PREFERENCE_USER_INFO_KEY = "UserInfo"
const val SHARED_PREFERENCE_USER_TYPE_KEY = "UserType"

const val BANGLA = "bn"
const val ENGLISH = "en"

const val PAGE_SIZE = 200
const val DISTANCE = 50
const val MAX_TEST_GIVEN_TIME = 3
const val DAY_IN_MILLISECOND = 24 * 60 * 60 * 1000
const val PERMISSION_REQUEST_CODE_CALL = 100

const val SIZE = "10000"
const val PAGE_NO = "1"
const val IMAGE = "image"
const val PDF = "pdf"

val DIRECTORY = Environment.getExternalStorageDirectory().path + "/appinion_field_force/"
const val DATE_TIME_PATTERN_WORD = "dd MMM, yyyy hh:mm a"
const val DATE_PATTERN_WORD = "dd MMM, yyyy"
const val PHONE_NO = "phoneNo"
const val OTP = "otp"
const val PRESCRIPTION_KEY = "pres_detail"
const val PATIENT_ID_KEY = "patientId"
const val CASE_ID_KEY = "case_id_key"
const val DOCUMENT_KEY = "doc_key"
const val STATUS_KEY = "status"
