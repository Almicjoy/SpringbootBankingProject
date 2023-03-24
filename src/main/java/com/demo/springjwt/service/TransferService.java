package com.demo.springjwt.service;

import com.demo.springjwt.payload.request.TransferRequest;

public interface TransferService {
	public boolean doTransfer(TransferRequest transferRequest);

}
