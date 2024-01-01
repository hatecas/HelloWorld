package com.core.tjoeun.mnHome.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.core.tjoeun.mnHome.main.service.MainService;


@Controller
public class MainController {
	
	@Autowired
	MainService mainService;
	
	@Value("${default.image.path}")
    private String defaultImagePath;
	
	@RequestMapping("/mnHome/mainView")
	public String minihome(Model model, @RequestParam Map map, HttpServletRequest req , HttpSession session) {
		if(session == null || session.getAttribute("userId") == null) {
			model.addAttribute("image",defaultImagePath);
            return "miniHome/main";
        }
		
		Map userMap = new HashMap();
		
		session = req.getSession();
		userMap = (Map) session.getAttribute("userId");
		String userNickname = (String) userMap.get("userNickname");
		
		mainService.getProfile(userNickname);
		String image = (String) mainService.getProfile(userNickname).get("image");
		String msg = (String) mainService.getProfile(userNickname).get("msg");
		msg = msg.replace("\n", "<br>");
		
		model.addAttribute("image", image);
		model.addAttribute("msg", msg);
		session.setAttribute("userId", userMap);
		return "miniHome/main";
	}
	
	@RequestMapping("/mnHome/mnhProfileEditView")
	public String mnhProfileEdit() {
		
		return "miniHome/mnhProfileEdit";
	}
	
	@RequestMapping("/mnHome/miniroomHistoryView")
	public String mnhProfileHistory(HttpSession session, HttpServletRequest req, Model model) {
		
		Map userMap = new HashMap();
		
		session = req.getSession();
		userMap = (Map) session.getAttribute("userId");
		if(userMap==null) {
			model.addAttribute("loginStatus",false);
			return "miniHome/mnhProfileHistory";
		} else {
			model.addAttribute("loginStatus",true);
		}
		
		String userNickname = (String) userMap.get("userNickname");
		
		List<Map> profileHistory = mainService.getProfileHistory(userNickname);
		for(Map history : profileHistory) {
		    String msg = (String) history.get("msg");
		    if(msg != null) {
		        msg = msg.replace("\n", "<br>");
		        history.put("msg", msg);
		    }
		}
		if(profileHistory==null||profileHistory.isEmpty()) {
			model.addAttribute("historyMessage", "히스토리가 존재하지 않습니다.");			
		}		

		model.addAttribute("profileHistory", profileHistory);
		session.setAttribute("userId", userMap);
		
		return "miniHome/mnhProfileHistory";
	}
	
	@RequestMapping("/mnHome/miniroomEditView")
	public String miniroomEdit() {
		
		return "miniHome/miniroomEdit";
	}
	
	@RequestMapping("/mnHome/diaryView")
	public String diary() {
		
		return "miniHome/diary";
	}
	
	@RequestMapping("/mnHome/albumView")
	public String album() {
		
		return "miniHome/album";
	}
	
	@RequestMapping("/mnHome/boardView")
	public String board() {
		
		return "miniHome/board";
	}
	
	@RequestMapping("/mnHome/visitView")
	public String visit() {
		
		return "miniHome/visit";
	}
	
	@RequestMapping("/mnHome/settingView")
	public String setting() {
		
		return "miniHome/setting";
	}
	
}
