package com.baby.babycareproductsshop.security;


import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
public class MyUserDetails implements UserDetails {

    private MyPrincipal myPrincipal;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {    //권한
        return null;
    }

    @Override
    public String getPassword() {   //시큐리티 루틴 이용하면 구현 필요
        return null;
    }

    @Override
    public String getUsername() {   //시큐리티 루틴 이용하면 구현 필요
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
