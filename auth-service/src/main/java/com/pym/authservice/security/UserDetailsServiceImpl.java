package com.pym.authservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service   // It has to be annotated with @Service.
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // hard coding the users. All passwords must be encoded.
        final List<AppUser> users = Arrays.asList(new AppUser(1, "ariv", bCryptPasswordEncoder.encode("ariv"), "USER"),
                new AppUser(1, "admin", bCryptPasswordEncoder.encode("admin"), "ADMIN"));
        for (AppUser appUser: users) {
            if(appUser.getUsename().equals(username)) {
                // Remember that Spring needs roles to be in this format: "ROLE_" + userRole (i.e. "ROLE_ADMIN")
                // So, we need to set it to that format, so we can verify and compare roles (i.e. hasRole("ADMIN")).
                List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_"+ appUser.getRole());
                return  new User(appUser.getUsename(), appUser.getPassword(), grantedAuthorities);
            }
        }
        throw new UsernameNotFoundException("Username: " + username + " not found");
    }

    private static class AppUser {
        private Integer id;
        private String usename;
        private String password;
        private String role;

        public AppUser(Integer id, String usename, String password, String role) {
            this.id = id;
            this.usename = usename;
            this.password = password;
            this.role = role;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsename() {
            return usename;
        }

        public void setUsename(String usename) {
            this.usename = usename;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}


