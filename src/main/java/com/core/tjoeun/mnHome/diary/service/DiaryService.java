package com.core.tjoeun.mnHome.diary.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DiaryService {
	
	public Map insertDiary(Map map) throws Exception;

	public Map selectDiary(Map map);
	
	public int deleteDiary(ArrayList<String> list);
	
	public int modifyDiary(Map map);
	
	public void insertDiaryCMT(Map map) throws Exception;
	
	public List<HashMap> selectDiaryCMT(String userNickname);
	
	public Map diaryTest (Map paramMap);
	
	public List<HashMap> diaryCmtTest(String seq);

}
