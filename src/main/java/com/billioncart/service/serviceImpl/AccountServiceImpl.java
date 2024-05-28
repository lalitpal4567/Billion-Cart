package com.billioncart.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.billioncart.repository.AccountRepository;
import com.billioncart.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	private AccountRepository accountRepository;

	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
}
