package com.morningwoodsoftware.android.spuge.exception;

public class ApplicationException extends RuntimeException 
{
	private static final long serialVersionUID = 2691314088769261210L;
	
	public ApplicationException(String msg)
	{
		super(msg);
	}
	
	public ApplicationException(String msg, Exception e)
	{
		super(msg,e);
	}
}
