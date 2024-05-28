package com.billioncart.configuration;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.billioncart.model.Account;

@Configuration
@EnableJpaAuditing
//@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {
//	@Bean
//	public AuditorAware<Long> auditorProvider() {
//		return new AuditorAwareImpl();
//	}
}

//class AuditorAwareImpl implements AuditorAware<Long>{
//	 @Override
//	    public Optional<Long> getCurrentAuditor() {
//	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//	        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
//	            return Optional.empty();
//	        }
//
//	        Account currentAccount = (Account) authentication.getPrincipal();
//	        System.out.println(currentAccount.getUsername());
//	        return Optional.ofNullable(currentAccount.getAccountId());
////	        return Optional.ofNullable(((UserDetails) authentication.getPrincipal()).getUsername());
//	    }
//}
