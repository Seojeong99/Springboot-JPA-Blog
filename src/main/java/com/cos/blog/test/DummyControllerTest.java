package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired //의존성 주입 DI
	private UserRepository userRepository;
	
	//json데이터 요청하면 java Object(MessageConverter의 jackson라이브러리가 변환해서 받아줌)
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update, 없으면 insert
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "해당 id가 존재하지 않습니다";
			// TODO: handle exception
		}
		
		return "삭제되었습니다. id:"+id;
	}
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser){
		System.out.println(id);
		System.out.println(requestUser.getPassword());
		
		User user=userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user);
		//더티체킹
		return user;
	}
	
	//http://localhost:8000/blog/dummy/user/
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//한페이지당 2건에 데이터를 리턴받아 볼 예정
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);
		List<User> users=pagingUser.getContent();
		return users;
	}
	
	//{id}주소로 파라미터를 전달 받을 수 있음
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id){
		//return 값이 null이면 문제가 생길 수 있으니까
		User user=userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저가 없습니다");
			}
		});
		//요청 : 웹브라우저
		//user 객체 = 자바 오브젝트
		//변환(웹브라우저가 이해할 수 있는 데이터)->json
		//스프빙부트 = messageconverter라는 애가 응답시에 자동 작동
		//만약에 자바 오브젝트를 리턴하게 되면 messageconverter가 jackson라이브러리를 호출해서
		//user오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.
		
		return user;
		
	}
	//http://localhost:8000/blog/dummy/join
	//http의 body에 username,...데이터 가지고 요청
	@PostMapping("/dummy/join")
	public String join(User user) {
		//key=value(약속된 규칙)
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getEmail());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}

}
