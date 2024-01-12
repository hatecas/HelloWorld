package com.core.tjoeun.mnHome.diary.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.tjoeun.mnHome.diary.service.DiaryService;
import com.core.tjoeun.mnHome.main.dao.MainDao;
import com.core.tjoeun.mnHome.main.service.MainService;

@Controller
public class DiaryController {
	
	@Autowired
	MainService mainService;

	@Autowired
	DiaryService diaryService;
	
	@Autowired
	MainDao mainDao;
	
	@RequestMapping("/mnHome/diaryView/{userNickname}")
	public String diaryView(@PathVariable String userNickname, Model model) {
		Map userMap = mainService.getUserInfo(userNickname);
		model.addAttribute("userName", userMap.get("userName"));
		model.addAttribute("userNickname", userMap.get("userNickname"));
		model.addAttribute("title", userMap.get("title"));
		System.out.println("userMap : " + userMap);
		System.out.println("map 시작");
		List<Map> diaryList = diaryService.selectDiary(userMap);
		if (!diaryList.isEmpty()) {
	        model.addAttribute("diaryList", diaryList);
	    }
		
		
		
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
        
      //방문자 수 가져오기
        try {
			Map visitCntMap = new HashMap();
			visitCntMap = mainDao.selectVisitCnt(userNickname);
			model.addAttribute("todayCnt", (int) visitCntMap.get("todayCnt"));
			model.addAttribute("totalCnt", (int) visitCntMap.get("totalCnt"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "miniHome/diary";
	}
	
	@RequestMapping(value="/mnHome/diaryWriteView/{userNickname}")
	public String diaryWriteView(@PathVariable String userNickname, Model model, Map map) {
		
		Map userMap = mainService.getUserInfo(userNickname);
		model.addAttribute("userName", userMap.get("userName"));
		model.addAttribute("title", userMap.get("title"));
		
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
		
      //방문자 수 가져오기
        try {
			Map visitCntMap = new HashMap();
			visitCntMap = mainDao.selectVisitCnt(userNickname);
			model.addAttribute("todayCnt", (int) visitCntMap.get("todayCnt"));
			model.addAttribute("totalCnt", (int) visitCntMap.get("totalCnt"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return "miniHome/diaryWrite";
	}
	
	@RequestMapping(value="/mnHome/diaryAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map diaryAdd(@RequestBody  Map map) throws Exception {
			System.out.println("add 실행");
			System.out.println("map : " + map);
			map.put("openScope",1);
			Map resultMap = diaryService.insertDiary(map);
			if(resultMap != null) {
				resultMap.put("resultCode", "1");
				return resultMap;
			} else {
				resultMap.put("resultCode", "0");
				return resultMap;
			}
	}
	
	@RequestMapping(value="/mnHome/diaryAddCMT", method = RequestMethod.POST)
	@ResponseBody
	public Map diaryAddCMT(@RequestBody  Map map) throws Exception {
			
			map.put("openScope",1);
			
			Map resultMap = new HashMap();
			
			try {
				diaryService.insertDiaryCMT(map);
				resultMap.put("resultCode", "1");
			} catch (Exception e) {
				resultMap.put("resultCode", "0");
			}
			
			return resultMap;
	}

}
