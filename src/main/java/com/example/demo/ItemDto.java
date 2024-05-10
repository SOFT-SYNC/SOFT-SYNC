package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class ItemDto {

	    @Id	
	    @Column(name = "item_code") 
	    private String item_code;
	    
	    @Column(name = "item_name")
	    private String item_name;
	    
	    @Column(name = "width")  //가로
	    private int width;
	    
	    @Column(name = "length") //세로
	    private int length;
	    
	    @Column(name = "height") //높이
	    private int height;
	    
	    @Column(name = "material") //재질
	    private String material;
	    
	    @Column(name = "blueprint_org_name")//원래이름
	    private String blueprint_org_name;
	    
	    @Column(name = "blueprint_save_name")//저장된 이름
	    private String blueprint_save_name;
	    
	    @Column(name = "unit")
	    private String unit;
	    
	    @Column(name = "unit_code") //대분류
	    private String unit_code;
	    
	    @Column(name = "part_code") //중분류
	    private String part_code;
	    
	    @Column(name = "sub_code")
	    private String sub_code;
	    
	    // getters and setters

}
