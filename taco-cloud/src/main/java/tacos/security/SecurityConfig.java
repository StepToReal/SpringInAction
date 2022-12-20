package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/design", "/orders")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/", "/**")
                .access("permitAll")
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll()
                .and()
                .httpBasic();

        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userSearchBase("ou=people")
                .userSearchFilter("(uid={0})")
                .groupSearchBase("ou=groups")
                .groupSearchFilter("(member={0})")
                .contextSource()
                    .url("ldap://localhost:8389/dc=tacocloud,dc=com")
                .and()
                .passwordCompare()
                    .passwordEncoder(new BCryptPasswordEncoder())
                    .passwordAttribute("userPasscode");
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user1")
//                .password("{noop}password1") //spring5 부터 비번을 암호화 하지 않으면 접근이 안됨. 인메모리 사용자 스토어에서는 {noop} 지정하면 암호화 하지 않고 테스트 가능
//                .authorities("ROLE_USER")
//                .and()
//                .withUser("user2")
//                .password("{noop}password2")
//                .authorities("ROLE_USER");

        /*
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                //spring security 기본 사용자 인증 테이블 정보와 다른 테이블을 사용하려면 직접 작성하면 된다.
                //다만 테이블이 갖는 데이터 타입과 길이는 일치해야 한다.
                //또한 where 절은 username 하나만 사용 가능하며 사용자정보 인증 쿼리에서 username, password, enabled 열의 값을 반환해야 한다.
                //(이정도 제약이면 다른 테이블을 쓰는 이유가 거의 없지 않나...)(아주 다른 테이블도 가능한가?)
                .usersByUsernameQuery(
                        "select username, password, enabled from users where username=?"
                )
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities where username=?"
                )
                .passwordEncoder(new NoEncodingPasswordEncoder());
         */

        /* 이건 이전 방식 변경된 방식으로 진행..
        auth
                .ldapAuthentication()
                .userSearchBase("ou=people")
                .userSearchFilter("(uid={0})")
                .groupSearchBase("ou=groups")
                .groupSearchFilter("(member={0})")
                .contextSource()
                .root("dc=tacocloud,dc=com")
                .ldif("classpath:users.ldif")
                .and()
                .passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder())
                .passwordAttribute("userPasscode");
         */
//    }

}
