package com.ich.reciptopia.common.util

object Constants {
    const val BASE_URL = "http://reciptopia.firstian.kr/api/alpha/"
    const val IMAGE_API_URL = "http://ubinetlab.asuscomm.com:50001/"

    const val HTTP_EXCEPTION_COMMENT = "알 수 없는 오류가 발생했습니다"
    const val CANNOT_CONNECT_SERVER_COMMENT = "서버와 연결할 수 없습니다"
    const val SQL_EXCEPTION_COMMENT = "기기에 데이터를 저장하지 못했습니다"
    
    const val MAX_IMAGE_CNT = 10
    const val PW_MIN_LENGTH = 8
    const val PW_MAX_LENGTH = 16

    const val PW_EXPRESSION = "^[\\x00-\\x7F]*$"
}