package com.softsync.zerock.DTO;
 
import org.springframework.web.multipart.MultipartFile;

public class ItemDto {
    private Long id;
    private String itemCode;
    private Long childcategoryId; // 추가된 필드
    private String itemName;
    private String dimensions;
    private String material;
    private MultipartFile blueprintPath;

    // 생성자
    public ItemDto() {
    }

    public ItemDto(Long id, String itemCode, Long childcategoryId, String itemName, String dimensions, String material, MultipartFile blueprintPath) {
        this.id = id;
        this.itemCode = itemCode;
        this.childcategoryId = childcategoryId;
        this.itemName = itemName;
        this.dimensions = dimensions;
        this.material = material;
        this.blueprintPath = blueprintPath;
    }

    // Getter 및 Setter
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


    public Long getChildcategoryId() {
		return childcategoryId;
	}

	public void setChildcategoryId(Long subcategoryId) {
		this.childcategoryId = subcategoryId;
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

    public MultipartFile getBlueprintPath() {
        return blueprintPath;
    }

    public void setBlueprintPath(MultipartFile blueprintPath) {
        this.blueprintPath = blueprintPath;
    }
}