package com.cos.blog.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
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

//htmlFileではなく、DataをReturnする Controller = RestController
@RestController
public class DummyControllerTest {

	@Autowired //DI 依存性注入
	private UserRepository userRepository;
	
	
	/*
	 * saveでやる時に
	 */
    /*
	//save Methodは idを渡さないとinsertしてくれる
	//save Methodは idを渡すと該当idに対するDataがある場合、updateをしてくれる
	//save Methodは idを渡すと該当idに対するDataがない場合、insertをする
	//email, password
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id,  @RequestBody User requestUser) { //jsonデータをRequest ＝＞ Java Object(MessageConvertのJackson Libraryが変換して取ってくれる)
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		//requestUser.setId(id);

		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("修正に失敗しました。");
		});
		
		//saveでupdateするなら、一度取得して更新する値を別途上書きをする必要がある
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//insertの時に使うが、すでに存在するDataならUpdateする
		userRepository.save(user); 
		//ただ、すべてのColumnに上書きをするのでparamに渡すものにnullなどがあればnullで更新されるのであまりよろしくない。
		
		return null;
		
	}
	*/
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		
		try { 
			
			userRepository.deleteById(id);
			
		} catch(EmptyResultDataAccessException e){
			
			return "削除に失敗しました。該当IDはDBに存在しません。";
		}
		
		return "削除されました。 id : " + id;
		
	}
	
	//dirtyChecking -> saveを使わずにUpdate
	@Transactional // Method処理の始まりでTransactionも開始、処理が終了されると一緒に終了されて自動Commitをする
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id,  @RequestBody User requestUser) { //jsonデータをRequest ＝＞ Java Object(MessageConvertのJackson Libraryが変換して取ってくれる)
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());

		//ここで永続コンテキストに対象のデータが永続化される
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("修正に失敗しました。");
		});
		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		return user;
	}
	//methodが終了されてから永続コンテキストにある値と比較し異なる点をDBにFlushする
	
	
	//すべて検索
	//http:localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	
	//pageに2件データを照会してReturnする
	@GetMapping("/dummy/user")
	//public Page<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {

		//ページを返すとpage情報も一緒に返す
		//Page<User> users =userRepository.findAll(pageable);
		//コンテンツのみに返す時に
		//List<User> users =userRepository.findAll(pageable).getContent();

		Page<User> pagingUser  = userRepository.findAll(pageable);
		
		//何か分岐処理をする時には下記のような物を使用することもあり
		//if(pagingUser.isFirst()) {
		//if(pagingUser.isLast()) {
			
		//}
		
		//最終的にListで返す
		List<User> users = pagingUser.getContent();
		
		return users;
		
	}
	
	
	//http:localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id ) {
		
		// user/4を探した場合、DBから見つからないとuserがnullになる
		//nullになっているまま次の処理に進むと問題が起きる可能性があるので、findByIdの戻り値はOptionalである。
		//OptionalでUserObjectを包んでreturnされるとnullであるかどうか判断してから返すことができる
		
		//User user = userRepository.findById(id).get(); //必ずある時には.get()を使用
		
		 //orElseGet => nullの時にobjectを一個生成して取得、paramにはSupplier Typeが入る
		//Supplierはinterfaceだから匿名関数でオブジェクトを生成する
		//nullである場合、 User Objectを新しく生成して返す
		/*
		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
			@Override
			public User get() {
				// TODO Auto-generated method stub
				return new User();
			}
		});*/
		
		//上記よりは下記の方が良い。
		/*
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("検索条件に一致するUserは存在しません。　id : " + id);
			}	
		});*/
		
		
		//Lamda式に変更↓
		User user = userRepository.findById(id).orElseThrow(() -> {
			
			return new IllegalArgumentException("検索条件に一致するUserは存在しません。　id : " + id);
			
		});
		
		// user Object = java Object
		// WebBrowser => request 
		// WebBrowserが分かるData Formatで送る必要がある　⇒　json (Gson LibraryをJavaでは使用。)
		// Spring Bootでは　MessageConvertが　responseの時に自動で動作する
		// もし、Java Objectをreturnする時にはMessageConvertがJackson Libraryを呼び出し
		// User Objectをjsonで変換してWeb BrowserにResponseをする
		return user;
	
	}
	
	
	
	// localhost:8000/blog/dummy/join
	// httpのbodyにusername,password,emailデータでリクエスト
	@PostMapping("/dummy/join")
	//public String join(String username, String password, String email) {//key =value 約束である規則 param名 = key
	public String join(User user) {//objectでもできる！
		System.out.println("id : " + user.getId());//DBでAutoincrement 
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());//@ColumnDefault => null値は入らないように@DynamicInsertを付けないといけない　(Userクラス)
		System.out.println("createDate : " + user.getCreateDate()); //@CreationTimestampで自動でシステム日付で登録
		
		user.setRole(RoleType.USER); // @DynamicInsertを使用せず、Enumで設定しよう。
		userRepository.save(user);
		
		return "登録完了";

	}

}
