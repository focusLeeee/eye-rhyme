package com;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.jdbc.core.JdbcTemplate;

import com.models.Account;
import com.models.Comment;
import com.models.MovieActivity;
import com.models.MovieArticle;
import com.models.MovieDescription;
import com.models.MovieGrade;
import com.models.MovieProduct;
import com.models.MovieTicket;
import com.models.Theater;
import com.models.TheaterGrade;
import com.models.User;
import com.repositories.AccountRepository;
import com.repositories.MovieActivityRepository;
import com.repositories.MovieArticleRepository;
import com.repositories.MovieDescriptionRepository;
import com.repositories.MovieProductRepository;
import com.repositories.MovieTicketRepository;
import com.repositories.TheaterRepository;
import com.repositories.UserRepository;
import com.utils.Values;

@SpringBootApplication
public class Application extends SpringBootServletInitializer implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	@Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MovieDescriptionRepository movieDescriptionRepository;
    
    @Autowired
    private TheaterRepository theaterRepository;
    
    @Autowired
    private MovieProductRepository movieProductRepository;
    
    @Autowired
    private MovieTicketRepository movieTicketRepository;
    
    @Autowired
    private MovieActivityRepository movieActivityRepository;
    
    @Autowired
    private MovieArticleRepository movieArticleRepository;
	
    @Autowired
    JdbcTemplate jdbcTemplate;
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
	
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }
    
    public void run(String... strings) throws Exception {
    	try {
    		
    		/*createUser();
    		createMovieDescription();
    		createTheaters();
    		createMovieProducts();
    		createMovieTicket();
    		createMovieActivity();
    		joinActivity();
    		createMovieArticle();
    		createComment();
    		joinActivity();
    		createMovieGrade();
    		createTheaterGrade();*/
    		//learnJDBC();
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void learnJDBC() throws Exception {
/*    	jdbcTemplate.execute("DROP TABLE IF EXISTS customers");
        jdbcTemplate.execute("CREATE TABLE customers(" +
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");*/
    	jdbcTemplate.execute("delete from join_activity where user_id = 1 and activity_id = 1");
    }
    
    public void createComment() throws Exception {
    	MovieArticle movieArticle = movieArticleRepository.findMovieArticleById(1);
    	User user = userRepository.findOne(2);
    	movieArticle.getComments().add(new Comment(user, "Oh, my God.."));
    	movieArticle.getComments().add(new Comment(user, "Oh, Shit.."));
    	movieArticleRepository.save(movieArticle);
    }
    
    public void createMovieArticle() throws Exception {
    	User user = userRepository.findUserById(2);
		MovieDescription movieDescription = movieDescriptionRepository.findMovieDescriptionById(1);
		String title = "Life is tough";
		String content = "Life is half spent before we know what it is.";
		String contentPic = "a.png";
    	movieArticleRepository.save(new MovieArticle(user, movieDescription, title, content, contentPic));
    }
    
    public void createUser() throws Exception {
    	String name = "thomas young";
		Account account = accountRepository.save(new Account("13246817678", "1234"));
    	Integer id = account.getId();
    	userRepository.save(new User(id, name, account.getPhone()));
    	
    	String name1 = "young lee";
		Account account1 = accountRepository.save(new Account("13246817677", "1234"));
    	Integer id1 = account1.getId();
    	userRepository.save(new User(id1, name1, account1.getPhone()));
    }
    
    public void createMovieDescription() throws Exception {
    	for (int i = 1; i <= 25; i++) {
	    	String name = "Thomas Muller" + String.valueOf(i);
			Integer type = i%5 + 1;
			Integer duration = 120 + i;
			String directors = "涔旀仼路璐瑰剴";
			String actors = "灏煎皵路濉炶タ/姣斿皵路榛樼憺";
			String description = "姣涘厠鍒╋紙灏煎皵路濉炶タ Neel Sethi 楗帮級鏄竴涓敱鐙肩兢鍏诲ぇ鐨勪汉绫荤敺瀛╋紝褰辩墖鍥寸粫浠栫殑妫灄鍐掗櫓寰愬緪灞曞紑銆�"
					+ "韪忎笂浜嗕竴鍦虹簿褰╃悍鍛堢殑鑷垜鎺㈢储鏃呯▼銆�";
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date releaseDate = sdf.parse(String.format("2016-04-%d", i));
			
			movieDescriptionRepository.save(new MovieDescription(name, type, releaseDate, duration, 
					directors, actors, description));
    	}
    }
    
    public void createTheaters() throws Exception {
    	String [][]locations = {
	    	{"23.064107","113.453293"},
	    	{"23.053333","113.437914"},
	    	{"23.148006","113.364468"},
	    	{"23.169537","113.354120"},
	    	{"23.117564","113.471690"},
	    	{"23.165151","113.463785"},
	    	{"22.980954","113.317325"},
	    	{"23.006504","113.286567"},
	    	{"22.969109","113.212475"},
	    	{"22.668504","113.637481"},
	    	{"22.989072","113.340968"}
    	};
    	for (int i = 1; i <= 11; i++) {
	    	String name = "娴蜂笂鍥介檯褰卞煄" + String.valueOf(i);
			String location = "鏅檧鐜悆娓�"+ String.valueOf(i) + "妤�";
			String contact = "6067096" + String.valueOf(i);
			String description = "娴蜂笂鍥介檯褰卞煄鐜悆娓簵鍦板涓北鍖楄矾3300鍙凤紝鍧愯惤浜庢枼璧勭櫨浜裤�佸舰浼煎法鍨嬭埅姣嶇殑鏈堟槦鐜悆娓�4灞傦紝"
					+ "绱т緷缇庝附鐨勯暱椋庢捣娲嬪叕鍥拰钁楀悕鐨勯珮绛夊搴溾�斺�斾笂娴峰崕涓滃笀鑼冨ぇ瀛︺�傞櫎浜嗕紭瓒婄殑鑷劧鍜屼汉鏂囩幆澧冨锛�"
					+ "娴蜂笂鍥介檯褰卞煄鐜悆娓簵浜ら�氫篃鍗佸垎渚垮埄锛屽奖鍩庡湴澶勯潤瀹夈�侀暱瀹併�佹櫘闄�涓夊尯缁撳悎澶勶紝"
					+ "涓庡唴鐜珮鏋跺弻鍚戝対閬撹繎鍦ㄥ挮灏猴紝绱ч偦涓幆锛屽懆杈规湁鍗佸嚑鏉″叕浜ょ嚎璺彲鎶佃揪";
			BigDecimal longitude = new BigDecimal(locations[i - 1][0]);
			BigDecimal latitude = new BigDecimal(locations[i - 1][1]);
			theaterRepository.save(new Theater(name, location, contact, description, longitude, latitude));
    	}
    }
    
    public void createMovieProducts() throws Exception {
    	MovieDescription movieDescription = movieDescriptionRepository.findMovieDescriptionById(1);
		for (int j = 1; j <= 5; j++) {
	    	for (int i = 1; i <= 11; i++) {
		    	Theater theater = theaterRepository.findTheaterById(i);
				Integer type = j;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(String.format("2016-04-%d", i));
				Integer round = j;
				Integer hall = j;
				Integer price = (30 + i);
				Integer discount = i;
				MovieProduct movieProduct = new MovieProduct(movieDescription, theater, type, date, round, hall, price, discount);
				movieProductRepository.save(movieProduct);
			}
		}
    }
    
    public void createMovieGrade() throws Exception {
    	User []user = {userRepository.findUserById(1), userRepository.findUserById(2)};
    	Random random = new Random();
    	for (int j = 0; j < 2; j++) {
	    	for (int i = 1; i < 12; i++) {
				MovieDescription movieDescription = movieDescriptionRepository.findMovieDescriptionById(i);
				Integer value = random.nextInt(5);
				String content = "i love it.";
				MovieGrade movieGrade = new MovieGrade(user[j], value, content);
				
				movieDescription.getGrades().add(movieGrade);
				movieDescriptionRepository.save(movieDescription);
	    	}
    	}
    }
    
    public void createTheaterGrade() throws Exception {
    	User []user = {userRepository.findUserById(1), userRepository.findUserById(2)};
    	Random random = new Random();
    	for (int j = 0; j < 2; j++) {
	    	for (int i = 1; i < 12; i++) {
				Theater theater = theaterRepository.findTheaterById(i);
				Integer value = random.nextInt(5);
				String content = "i love it.";
				TheaterGrade theaterGrade = new TheaterGrade(user[j], value, content);
				theater.getGrades().add(theaterGrade);
				theaterRepository.save(theater);
	    	}
    	}
    }
    
    public void createMovieTicket() throws Exception {
    	MovieProduct movieProduct = movieProductRepository.findMovieProductById(1);
		for (int i=10; i <= 20; i++) {
			movieProduct.getSeats().add(i);
			User user = userRepository.findOne(i%2 + 1);
			Integer type = i%5 + 1;
			Integer status = Values.UNPAID;
			MovieTicket movieTicket = new MovieTicket(user, movieProduct, i, type, status, "");
			movieTicketRepository.save(movieTicket);
		}
		
		movieProductRepository.save(movieProduct);
    }
    
    public void createMovieActivity() throws Exception {
    	User user = userRepository.findOne(1);
		MovieDescription movieDescription = movieDescriptionRepository.findMovieDescriptionById(1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateTime = sdf.parse("2016-04-30");
		BigDecimal longitude = new BigDecimal("123.2343242");
		BigDecimal latitude = new BigDecimal("23.7777773");
		String place = "Broad way";
		String contact = "510006";
		MovieActivity movieActivity = new MovieActivity(dateTime, place, longitude, latitude, contact);
		movieActivity.setMovieDescription(movieDescription);
		movieActivity.setLauncher(user);
		movieActivityRepository.save(movieActivity);
    }
    
    public void joinActivity() throws Exception {
    	MovieActivity movieActivity = movieActivityRepository.findMovieActivityById(1);
		logger.info(String.valueOf(movieActivity.getParticipants().size()));
    	User user = userRepository.findUserById(1);
		User user1 = userRepository.findUserById(2);
		
    	logger.info(String.valueOf(user1.getJoins().size()));
    	logger.info(String.valueOf(user1.getJoins().contains(movieActivity)));
		
    	user.getJoins().add(movieActivity);
		user1.getJoins().add(movieActivity);
		
		/*Set<User> t = movieActivity.getParticipants();
		t.remove(user);
		movieActivity.setParticipants(t);*/
		
		//movieActivity.getParticipants().remove(user);
		//movieActivityRepository.save(movieActivity);
		userRepository.save(user);
		userRepository.save(user1);
    }
    
}
