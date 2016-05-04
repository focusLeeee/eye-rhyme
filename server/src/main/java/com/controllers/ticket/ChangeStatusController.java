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
import com.utils.StringMerger;
import com.utils.Values;

import com.models.MovieProduct;
import com.models.MovieTicket;
import net.sf.json.JSONObject;

@RestController
public class ChangeStatusController {
	
	@Autowired
	MovieProductRepository movieProductRepository;
	
	@Autowired
	MovieTicketRepository movieTicketRepository;
	
	@Autowired
	UserRepository userRepository;
	
    @RequestMapping(value="movie_ticket/change_status", method=RequestMethod.GET)
    public String changeStatus() {
        return "this controller is for changing an order's status..";
    }
    
    @RequestMapping(value="movie_ticket/change_status", method=RequestMethod.POST)
    public String changeStatus(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, String> map = new HashMap<String, String>();
    	try {
    		if (jsonObj.containsKey(Keyword.TICKET_ID) && jsonObj.containsKey(Keyword.STATUS)) {
    			Integer status = jsonObj.getInt(Keyword.STATUS);
    			MovieTicket movieTicket = movieTicketRepository.findOne(jsonObj.getInt(Keyword.TICKET_ID));
    			movieTicket.setStatus(status);
    			if (status == Values.CANCEL) {
    				MovieProduct movieProduct = movieTicket.getMovieProduct();
        			movieProduct.getSeats().remove(movieTicket.getPositionNum());
        			movieProductRepository.save(movieProduct);
    			}
    			
    			if (status == Values.ALPAID) {
    				String receiptString = StringMerger.getRandomString(Values.RECEIPT_SIZE);
    				movieTicket.setReceiptNum(receiptString);
    				map.put(Keyword.RECEIPT_NUM, receiptString);
    			}
    			
    			movieTicketRepository.save(movieTicket);
    			
    			map.put(Keyword.STATUS, Values.OK);
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
