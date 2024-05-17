package com.softsync.zerock.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "items")
public class Item {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    

    @OneToMany( fetch = FetchType.LAZY)
    private List<Contract> contracts;
    
    @OneToMany (fetch = FetchType.LAZY)
    private List<Orders> orders;
    
    @Column(nullable = true, unique = true)
    private String itemCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    @Column(nullable = true)
    private String itemName;

    @Column(nullable = true)
    private String dimensions;

    @Column(nullable = true)
    private String material;

    @Column(nullable = true)
    private String blueprintPath;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getBlueprintPath() {
		return blueprintPath;
	}

	public void setBlueprintPath(String blueprintPath) {
		this.blueprintPath = blueprintPath;
	}
	
	
	 public Company getCompany() {
	        if (contracts != null && !contracts.isEmpty()) {
	            Contract firstContract = contracts.get(0);
	            return firstContract.getCompany();
	        }
	        return null;
	    }
    
    
    
}