package com.scushare.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.scushare.entity.UserSearchKeyword;

public interface SearchKeyWordsMapper {
	@Select("SELECT search_times FROM keywordSearch WHERE search_date=#{search_date} AND key_word=#{key_word}")
	public Integer getKeyWordByDate(@Param("key_word") String key_word, @Param("search_date") Date search_date);
	
	@Update("UPDATE keywordSearch SET search_time=#{search_time} WHERE search_date=#{search_date} AND key_word=#{key_word}")
	public int updateKeyWordSearch(@Param("keyword") String keyword, @Param("search_time") int search_time, @Param("search_date") Date search_date);
	
	@Insert("INSERT INTO keywordSearch VALUES(#{key_word},#{search_times},#{search_date}) "
			+ "ON DUPLICATE KEY UPDATE key_word=#{key_word},search_times=#{search_times},search_date=#{search_date}")
	public int insertKeyWordSearch(@Param("key_word") String keyword, @Param("search_times") int search_times, @Param("search_date") Date search_date);
	
	@Select("SELECT * FROM keywordsearch WHERE search_date=#{search_date}")
	public List<UserSearchKeyword> selectKeywordsByDate(@Param("search_date") Date search_date);
	
	@Select({"<script>",
			"SELECT result_table.file_id " + 
			"FROM" + 
			"	(SELECT CONCAT_WS(',',keyword_str,file_name,file_college,file_major) AS total_keywords, file.file_id " + 
			"	FROM " + 
			"		file " + 
			"		INNER JOIN " + 
			"		(SELECT GROUP_CONCAT(key_word) as keyword_str, file_id FROM keyword GROUP BY file_id) AS keywords " + 
			"		ON file.file_id = keywords.file_id) AS result_table " + 
			"WHERE " +
			"	<foreach collection=\"keywords\" index=\"index\" item=\"keyword\" open=\"\" separator=\"AND\" close=\"\"> " +
			"		result_table.total_keywords LIKE CONCAT('%', #{keyword}, '%') " +
			"	</foreach> " , "</script>"})
	public List<String> searchFileIdByKeywords(@Param("keywords") String[] keywords);
}
