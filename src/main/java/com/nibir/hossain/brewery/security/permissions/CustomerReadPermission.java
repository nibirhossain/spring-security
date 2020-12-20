package com.nibir.hossain.brewery.security.permissions;

/*
 * Created by Nibir Hossain on 20.12.20
 */

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('customer.read')")
public @interface CustomerReadPermission {
}
