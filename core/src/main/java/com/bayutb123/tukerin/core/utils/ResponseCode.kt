package com.bayutb123.tukerin.core.utils

data object ResponseCode {
    const val OK = 200;
    const val CREATED = 201;
    const val NO_CONTENT = 204;
    const val BAD_REQUEST = 400;
    const val UNAUTHORIZED = 401;
    const val FORBIDDEN = 403;
    const val NOT_FOUND = 404;
    const val METHOD_NOT_ALLOWED = 405;
    const val CONFLICT = 409;
    const val INTERNAL_SERVER_ERROR = 500;
}