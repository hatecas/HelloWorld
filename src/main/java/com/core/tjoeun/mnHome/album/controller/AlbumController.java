package com.core.tjoeun.mnHome.album.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.core.tjoeun.index.member.service.MemberService;
import com.core.tjoeun.mnHome.album.service.AlbumService;
import com.core.tjoeun.mnHome.main.service.MainService;

@Controller
public class AlbumController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	MainService mainService;
	
	@Autowired
	AlbumService albumService;
	
	@RequestMapping(value="/mnHome/albumView/{userNickname}")
	public String albumView(@PathVariable String userNickname, Model model) {
		
		Map profile = mainService.getProfile(userNickname);
		String image = (String) profile.get("image");
		String msg = (String) profile.get("msg");
		msg = msg.replace("\n", "<br>");
		model.addAttribute("image", image);
		model.addAttribute("msg", msg);
		
		Map map = mainService.getUserInfo(userNickname);
		model.addAttribute("userName", map.get("userName"));
		model.addAttribute("title", map.get("title"));
		
        //접속중인 유저의 친구 전부 가져오기
        List<Map> friendMap = mainService.getMyFriends(userNickname);
        model.addAttribute("friend", friendMap);
        
     // menu color 적용하기
        Map callMenu = new HashMap();
        callMenu.put("category", "menu");
        callMenu.put("userNickname", userNickname);
        System.out.println("### callMenu : " + callMenu);
        
        try {
        	Map mainMenu = mainService.mainMenu(callMenu);
        	System.out.println("### mainMenu : " + mainMenu);
        	
        	model.addAttribute("menuProductName", mainMenu.get("productName"));
	        model.addAttribute("menuCategory", mainMenu.get("category"));
	        model.addAttribute("menuUserNickname", mainMenu.get("userNickname"));
	        System.out.println("### menu model : " + model);
	        
        } catch (NullPointerException n) {
	        	model.addAttribute("menuProductName", "rgb(42, 140, 168)");
	        	model.addAttribute("menuCategory", "menu");
	        	n.printStackTrace();
        }
        
        Map userMap = new HashMap();
		userMap.put("userNickname", userNickname);
		
		List<HashMap> listResult = albumService.getAlbum(userMap);
		
		String[] images = new String[listResult.size()];
		String tempPath = "";
		
		for(int i=0; i<listResult.size(); i++){
			tempPath = listResult.get(i).get("imagePath").toString();
			
			if(tempPath.indexOf(',') > 0) {
				images[i] = tempPath.substring(0,tempPath.indexOf(','));
				
			}else {
				images[i] = tempPath;
			}
		}
		
		model.addAttribute("list", albumService.getAlbum(userMap));
		model.addAttribute("images", images);
        
		
		return "miniHome/album";
	}
	
	
	
	
	
	@RequestMapping(value="/mnHome/albumWriteView/{userNickname}")
	public String albumWriteView(@PathVariable String userNickname, Model model) {
		
		Map map = mainService.getUserInfo(userNickname);
		model.addAttribute("userName", map.get("userName"));
		model.addAttribute("title", map.get("title"));
		
        //접속중인 유저의 친구 전부 가져오기
        List<Map> friendMap = mainService.getMyFriends(userNickname);
        model.addAttribute("friend", friendMap);
        
     // menu color 적용하기
        Map callMenu = new HashMap();
        callMenu.put("category", "menu");
        callMenu.put("userNickname", userNickname);
        System.out.println("### callMenu : " + callMenu);
        
        try {
        	Map mainMenu = mainService.mainMenu(callMenu);
        	System.out.println("### mainMenu : " + mainMenu);
        	
        	model.addAttribute("menuProductName", mainMenu.get("productName"));
	        model.addAttribute("menuCategory", mainMenu.get("category"));
	        model.addAttribute("menuUserNickname", mainMenu.get("userNickname"));
	        System.out.println("### menu model : " + model);
	        
        } catch (NullPointerException n) {
	        	model.addAttribute("menuProductName", "rgb(42, 140, 168)");
	        	model.addAttribute("menuCategory", "menu");
	        	n.printStackTrace();
        }
        
		return "miniHome/albumWrite";
	}
	
	
	@RequestMapping(value="/mnHome/albumWrite/{userNickname}")
	@ResponseBody
	public Map albumWrite(@PathVariable String userNickname
							, @RequestPart(value = "contents")Map map
							, @RequestPart(value = "uploadFile") MultipartFile[] uploadFile) {
		Map result = new HashMap<String, String>();
		try {
			map.put("userNickname", userNickname);
			map.put("openScope", 1);
			
			albumService.insertAlbum(uploadFile, map);
			result.put("resultCode", "1");
		} catch (Exception e) {
			result.put("resultCode", "0");
			e.printStackTrace();
			return result;
		}
		
		return result ;
	}
	
	
	@RequestMapping(value="/mnHome/albumDetailView/{userNickname}/{seq}")
	public String albumDetailView(@PathVariable String userNickname, @PathVariable int seq, Model model) {
		
		Map profile = mainService.getProfile(userNickname);
		String image = (String) profile.get("image");
		String msg = (String) profile.get("msg");
		msg = msg.replace("\n", "<br>");
		model.addAttribute("image", image);
		model.addAttribute("msg", msg);
		
		Map map = mainService.getUserInfo(userNickname);
		model.addAttribute("userName", map.get("userName"));
		model.addAttribute("title", map.get("title"));
		
		//접속중인 유저의 친구 전부 가져오기
        List<Map> friendMap = mainService.getMyFriends(userNickname);
        model.addAttribute("friend", friendMap);
        
     // menu color 적용하기
        Map callMenu = new HashMap();
        callMenu.put("category", "menu");
        callMenu.put("userNickname", userNickname);
        System.out.println("### callMenu : " + callMenu);
        
        try {
        	Map mainMenu = mainService.mainMenu(callMenu);
        	System.out.println("### mainMenu : " + mainMenu);
        	
        	model.addAttribute("menuProductName", mainMenu.get("productName"));
	        model.addAttribute("menuCategory", mainMenu.get("category"));
	        model.addAttribute("menuUserNickname", mainMenu.get("userNickname"));
	        System.out.println("### menu model : " + model);
	        
        } catch (NullPointerException n) {
	        	model.addAttribute("menuProductName", "rgb(42, 140, 168)");
	        	model.addAttribute("menuCategory", "menu");
	        	n.printStackTrace();
        }
        
        Map userMap = new HashMap();
		userMap.put("userNickname", userNickname);
		userMap.put("seq", seq);
		
		List<HashMap> listResult = albumService.getAlbum(userMap);
		String tempImages = listResult.get(0).get("imagePath").toString();
		
		String[] imageParts = tempImages.split(",");
		model.addAttribute("list", listResult.get(0));
		model.addAttribute("imageParts", imageParts);
		
		return  "miniHome/albumDetail";
		
	}
	
	
}

