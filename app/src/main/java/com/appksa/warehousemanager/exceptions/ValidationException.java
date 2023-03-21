package com.appksa.warehousemanager.exceptions;

public class ValidationException extends Exception{

    private final int errorCode;

    public ValidationException(int errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode()
    {
        return errorCode;
    }

}
// 0 - reserved
// 1 - incorrect title field (item)
// 2 - incorrect date field (item)
// 3 - incorrect startAmount field (item)
// 4 - incorrect comment field (item)
// 5 - incorrect contractor field (dispatch)
// 6 - incorrect date field (dispatch)
// 7 - incorrect amount field (dispatch)