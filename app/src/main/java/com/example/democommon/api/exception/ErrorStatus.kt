package com.vn.onewayradio.api.exception

object ErrorStatus {

    object ErrorCode {

        val DEFAULT_ERROR = 1001

        val TIMEOUT_ERROR = 1002

        val NETWORK_ERROR = 1003

        val PARSE_ERROR = 1004

        val UNKNOWN_HOST_ERROR = 1005

        val API_ERROR = 1006

        val ILLEGAL_ARGUMENT_ERROR = 1007

        val UNKNOWN_ERROR = 1008

    }

    object ErrorMessage {

        val DEFAULT_ERROR = "Yêu cầu không thành công, vui lòng thử lại"

        val TIMEOUT_ERROR = "Kết nối không thành công (Time out)"

        val NETWORK_ERROR = "Không có kết nối mạng"

        val PARSE_ERROR = "ParseException"

        val UNKNOWN_HOST_ERROR = "Lỗi kết nối"

        val API_ERROR = 1005

        val ILLEGAL_ARGUMENT_ERROR = "Lỗi tham số"

        val UNKNOWN_ERROR = "Lỗi không xác định"

    }

}

