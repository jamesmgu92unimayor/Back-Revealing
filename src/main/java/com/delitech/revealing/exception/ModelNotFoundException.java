package com.delitech.revealing.exception;

public class ModelNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public ModelNotFoundException(String msj) {
		super(msj);
	}
}
