package com.controllers.ticket;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.repositories.MovieProductRepository;
import com.repositories.MovieTicketRepository;
import com.repositories.UserRepository;
import com.utils.Keyword;
import com.utils.Values;

import com.models.MovieProduct;
import com.models.MovieTicket;
import com.models.User;
import net.sf.json.JSONObject;

@RestController
public class MakeOrderController {
	
	@Autowired
	MovieProductRepository movieProductRepository;
	
	@Autowired
	MovieTicketRepository movieTicketRepository;
	
	@Autowired
	UserRepository userRepository;
	
    @RequestMapping(value="movie_ticket/make_order", method=RequestMethod.GET)
    public String makeOrder() {
        return "this controller is for making an order..";
    }
    
    @RequestMapping(value="movie_ticket/make_order", method=RequestMethod.POST)
    public String makeOrder(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, String> map = new HashMap<String, String>();
    	try {
    		if (jsonObj.containsKey(Keyword.PRODUCT_ID) && jsonObj.containsKey(Keyword.POSITION_NUM)
    				&& jsonObj.containsKey(Keyword.USER_ID) && jsonObj.containsKey(Keyword.TYPE)) {
    			MovieProduct movieProduct = movieProductRepository.findMovieProductById(jsonObj.getInt(Keyword.PRODUCT_ID));
    			Integer position = jsonObj.getInt(Keyword.POSITION_NUM);
    			movieProduct.getSeats().add(position);
    			movieProductRepository.save(movieProduct);
    			User user = userRepository.findOne(jsonObj.getInt(Keyword.USER_ID));
    			Integer type = jsonObj.getInt(Keyword.TYPE);
    			Integer status = Values.UNPAID;
    			MovieTicket movieTicket = new MovieTicket(user, movieProduct, position, type, status, "");
    			movieTicketRepository.save(movieTicket);
    			
    			map.put(Keyword.STATUS, Values.OK);
    			map.put(Keyword.TICKET_ID, movieTicket.getId().toString());
	    		return JSONObject.fromObject(map).toString();
    		}
    	} catch(Exception exception) {
    		exception.printStackTrace();
    	}
    	
    	// otherwise, return wrong response..
    	map.put(Keyword.STATUS, Values.ERR);
    	return JSONObject.fromObject(map).toString();
    }
}
