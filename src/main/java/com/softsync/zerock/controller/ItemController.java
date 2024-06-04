package com.softsync.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softsync.zerock.DTO.ItemDto;
import com.softsync.zerock.service.ItemService;

@RestController
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	 
	@PostMapping("/api/items")
	public ResponseEntity<?> uploadProdData(@ModelAttribute ItemDto itemDto, RedirectAttributes redirectAttributes) {
		try {
			itemService.addItem(itemDto);
			redirectAttributes.addFlashAttribute("message", "등록되었습니다.");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"message\":\"등록되었습니다.\"}");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "등록을 실패했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"등록을 실패했습니다\"}");
		}
	}


}