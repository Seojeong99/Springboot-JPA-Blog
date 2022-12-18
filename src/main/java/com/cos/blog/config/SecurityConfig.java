package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.blog.config.auth.PrincipalDetailService;

//빈 등록: 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration //빈등록 (IoC관리)
@EnableWebSecurity //security 필터 등록
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소를 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig{

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean //IOC가 된다
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	//시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
	//해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	//같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
	private void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		  .csrf().disable() //csrf 토근 비활성화 (테스트시 걸어두는게 좋음)
		  .authorizeRequests()
		  	.antMatchers("/","/auth/**","/js/**","/css/**","/image/**")
		  	.permitAll()
		  	.anyRequest()
		  	.authenticated()
		  .and()
		  	.formLogin()
		  	.loginPage("/auth/loginForm")
		  	.loginProcessingUrl("/auth/loginProc")//스프링시큐리티가 주소 가로채서 대신 로그인해줌
        	.defaultSuccessUrl("/");//정상적으로 완료되면 여기로옴
		return http.build();
    }

}