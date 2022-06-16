package com.connection.dropsocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;

@Controller
public class HomeController {
	private static final String ACCESS_TOKEN = "sl.BJm5tr36dPqSO-f7uMXeJROxvKoJmkSDa8bXuUo_FeJovAUSyuWScvLtqBYUWdSReL0ZNRR7QQiBUulDpJ67jtSJAX1QPMul0WPMhgSvCKqSTHO9Ed151Wgomf4xtstxNa7ITRI";
	@GetMapping(value = "/home")
	public String home() {
		DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        FullAccount account = null;
		try {
			account = client.users().getCurrentAccount();
		} catch (DbxApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(account.getName().getDisplayName());
        return account.getName().getDisplayName();
	}
}
