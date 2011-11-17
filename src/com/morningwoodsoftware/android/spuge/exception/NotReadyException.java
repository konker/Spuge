package com.morningwoodsoftware.android.spuge.exception;

public class NotReadyException extends RuntimeException
{
    private static final long serialVersionUID = 7617647365451284458L;

    public NotReadyException(String msg)
    {
        super(msg);
    }
}
