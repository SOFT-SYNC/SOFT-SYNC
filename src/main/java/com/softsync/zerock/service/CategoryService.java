package com.softsync.zerock.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.Category;
import com.softsync.zerock.repository.CategoryRepository;

@Service
public class CategoryService {
	private Map<String, String> categoryCodeMap = new HashMap<>();

	@Autowired
	private CategoryRepository categoryRepository;

	public CategoryService() {
		// 카테고리 코드 매핑 초기화
	      // 건축자재
	      categoryCodeMap.put("건축자재", "BU");
	      categoryCodeMap.put("기초재료", "BM");
	      categoryCodeMap.put("콘크리트", "CN");
	      categoryCodeMap.put("철근", "RB");
	      categoryCodeMap.put("시멘트", "CM");
	      categoryCodeMap.put("마감재료", "FS");
	      categoryCodeMap.put("타일", "TL");
	      categoryCodeMap.put("페인트", "PT");
	      categoryCodeMap.put("몰딩", "ML");

	      // 전기자재
	      categoryCodeMap.put("전기자재", "EL");
	      categoryCodeMap.put("케이블", "CB");
	      categoryCodeMap.put("전력케이블", "PC");
	      categoryCodeMap.put("데이터케이블", "DC");
	      categoryCodeMap.put("통신케이블", "TC");
	      categoryCodeMap.put("배전기기", "DS");
	      categoryCodeMap.put("스위치", "SW");
	      categoryCodeMap.put("차단기", "CB");
	      categoryCodeMap.put("배전반", "DP");

	      // 기계자재
	      categoryCodeMap.put("기계자재", "MC");
	      categoryCodeMap.put("펌프", "PM");
	      categoryCodeMap.put("수중펌프", "SP");
	      categoryCodeMap.put("가정용펌프", "HP");
	      categoryCodeMap.put("산업용펌프", "IP");
	      categoryCodeMap.put("모터", "MT");
	      categoryCodeMap.put("AC모터", "AC");
	      categoryCodeMap.put("DC모터", "DC");
	      categoryCodeMap.put("서보모터", "SV");

	      // 공구
	      categoryCodeMap.put("공구", "TL");
	      categoryCodeMap.put("수공구", "HT");
	      categoryCodeMap.put("스패너", "SP");
	      categoryCodeMap.put("드라이버", "DV");
	      categoryCodeMap.put("플라이어", "PL");
	      categoryCodeMap.put("전동공구", "PT");
	      categoryCodeMap.put("드릴", "DR");
	      categoryCodeMap.put("그라인더", "GR");
	      categoryCodeMap.put("전기톱", "SA");

	      // 배관자재
	      categoryCodeMap.put("배관자재", "PP");
	      categoryCodeMap.put("파이프", "PP");
	      categoryCodeMap.put("PVC파이프", "PV");
	      categoryCodeMap.put("스테인리스파이프", "SS");
	      categoryCodeMap.put("구리파이프", "CU");
	      categoryCodeMap.put("밸브", "VL");
	      categoryCodeMap.put("게이트밸브", "GV");
	      categoryCodeMap.put("볼밸브", "BV");
	      categoryCodeMap.put("체크밸브", "CV");

	      // 안전용품
	      categoryCodeMap.put("안전용품", "SF");
	      categoryCodeMap.put("보호구", "PR");
	      categoryCodeMap.put("안전모", "HM");
	      categoryCodeMap.put("안전화", "FS");
	      categoryCodeMap.put("보호안경", "GG");
	      categoryCodeMap.put("안전표지", "SG");
	      categoryCodeMap.put("경고표지", "WP");
	      categoryCodeMap.put("안내표지", "GP");
	      categoryCodeMap.put("피난표지", "EP");

	      // 페인트 및 코팅재
	      categoryCodeMap.put("페인트 및 코팅재", "PC");
	      categoryCodeMap.put("내부용 페인트", "IP");
	      categoryCodeMap.put("수성페인트", "WP");
	      categoryCodeMap.put("유성페인트", "OP");
	      categoryCodeMap.put("에폭시페인트", "EP");
	      categoryCodeMap.put("외부용 코팅재", "OC");
	      categoryCodeMap.put("방수코팅", "WC");
	      categoryCodeMap.put("방청코팅", "RC");
	      categoryCodeMap.put("내열코팅", "HC");

	      // 조명기기
	      categoryCodeMap.put("조명기기", "LG");
	      categoryCodeMap.put("실내조명", "IL");
	      categoryCodeMap.put("LED등", "LE");
	      categoryCodeMap.put("형광등", "FL");
	      categoryCodeMap.put("천장등", "CL");
	      categoryCodeMap.put("실외조명", "OL");
	      categoryCodeMap.put("가로등", "SL");
	      categoryCodeMap.put("투광등", "FL");
	      categoryCodeMap.put("태양광등", "SL");

	      // 철물
	      categoryCodeMap.put("철물", "HW");
	      categoryCodeMap.put("나사", "SC");
	      categoryCodeMap.put("육각볼트", "HB");
	      categoryCodeMap.put("나사못", "SS");
	      categoryCodeMap.put("너트", "NT");
	      categoryCodeMap.put("경첩", "HN");
	      categoryCodeMap.put("도어경첩", "DH");
	      categoryCodeMap.put("창문경첩", "WH");
	      categoryCodeMap.put("가구경첩", "FH");

	      // 접착제 및 실링제
	      categoryCodeMap.put("접착제 및 실링제", "AS");
	      categoryCodeMap.put("접착제", "AD");
	      categoryCodeMap.put("에폭시접착제", "EA");
	      categoryCodeMap.put("순간접착제", "SA");
	      categoryCodeMap.put("실리콘접착제", "CA");
	      categoryCodeMap.put("실링제", "SG");
	      categoryCodeMap.put("우레탄실링제", "US");
	      categoryCodeMap.put("아크릴실링제", "AS");
	      categoryCodeMap.put("실리콘실링제", "SS");
		
	}

	String getCategoryCode(Category category) {
		// 카테고리 이름에 매핑된 코드를 반환
		String code = categoryCodeMap.get(category.getName());
		if (code == null) {
			throw new IllegalArgumentException("No code defined for category: " + category.getName());
		}
		return code;
	}

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<Category> findAllCategories() {
		return categoryRepository.findAll();
	}
}