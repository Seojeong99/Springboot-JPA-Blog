package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌.IOC를 해준다
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	

	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword=user.getPassword();
		String encPassword=encoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}

	@Transactional
	public void 회원수정(User user) {
		// TODO Auto-generated method stub
		//수정시에는 영속성 컨택스트 User 오브젝트를 영속화시키고, 영속화된 User오브젝트를 수정
		//select해서 User오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서!!
		//영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려주거든요.
		User persistance = userRepository.findById(user.getId())
				.orElseThrow(()->{
					return new IllegalArgumentException("회원 찾기 실패");
				}); //영속화 완료
		
		String rawPassword = user.getPassword();
		String encPassword= encoder.encode(rawPassword);
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());
		

	}

}
