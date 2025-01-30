package com.plantnursery.bean;

import com.plantnursery.exception.IncorrectDataException;
import java.util.Arrays;
import java.util.List;

public class SetPlantBean {

	private Integer idSet;

	private String name;

	private String description;

	private Double price;

	private String temperature;

	private String plantMonth;

	private Integer availability;

	private String sellerUsername;

	private Integer quantity;


	public void setIdSet(Integer idSet) {
		this.idSet = idSet;
	}


	public void setAvailability(Integer closed) {
		this.availability = closed;
	}


	public void setName(String name) throws IncorrectDataException {
		if(name == null || name.isEmpty()) {
			throw new IncorrectDataException("Name cannot be empty");
		}else {
			this.name = name;
		}
	}

	public void setSellerUsername(String sellerName) throws IncorrectDataException {
		if(sellerName == null || sellerName.isEmpty()) {
			throw new IncorrectDataException("Seller Username cannot be empty");
		}else if(sellerName.length() > 45) {
			throw new IncorrectDataException("Seller Username is too long (max 45 characters)");
		}else {
			this.sellerUsername = sellerName;
		}
	}

	public void setDescription(String desc) throws IncorrectDataException {
		if(desc == null || desc.isEmpty()) {
			this.description = "No description available.";
		}else if(desc.length() > 1000) {
			throw new IncorrectDataException("Description is too long (max 1000 characters)");
		}else {
			this.description = desc;
		}
	}

	public void setPrice(Double price) throws IncorrectDataException {
		if(price == null || price < 0) {
			throw new IncorrectDataException("Price cannot be negative");
		}else {
			this.price = price;
		}
	}

	public void setTemperature(String temperature) {
			this.temperature = temperature;
	}

	public void setPlantMonth(String month) throws IncorrectDataException {
		List<String> validMonths = Arrays.asList(
				"January", "February", "March", "April", "May", "June",
				"July", "August", "September", "October", "November", "December"
		);

		if (month == null || !validMonths.contains(month)) {
			throw new IncorrectDataException("Invalid planting month. Must be one of the valid months (e.g., 'January').");
		} else {
			this.plantMonth = month;
		}
	}

	public void setQuantity(Integer quantity){
		this.quantity = quantity;
	}

	public Integer getIdSet() {
		return idSet;
	}

	public String getName() {
		return name;
	}

	public String getSellerUsername() {
		return sellerUsername;
	}

	public String getDescription() {
		return description;
	}

	public Integer getAvailability() {
		return availability;
	}

	public Double getPrice() {
		return price;
	}

	public String getTemperature() {
		return temperature;
	}

	public String getPlantMonth() {
		return plantMonth;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public String toString(List<PlantBean> plants) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < plants.size(); i++) {
			PlantBean plant = plants.get(i);
			sb.append((i + 1) + ". " + plant.getName() + " (" + plant.getScientificName() + ")");
			if (i < plants.size() - 1) {
				sb.append(",\n");
			}
		}
		return sb.toString();
	}
}