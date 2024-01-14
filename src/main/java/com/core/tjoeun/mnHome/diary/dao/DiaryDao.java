package com.core.tjoeun.mnHome.diary.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DiaryDao {
	
	public int insertDiary(Map map);

	public Map selectDiary(Map map);
	
	public int deleteDiary(ArrayList<String> list);
	
	public int modifyDiary(Map map);
	
	public int insertDiaryCMT(Map map);
	
	public List<HashMap> selectDiaryCMT (String userNickname);
	
	public Map diaryTest (Map paramMap);

}
