package entity;

import java.util.Objects;

public class Service implements Comparable<Service> {
	private int id;
	private String name;
	private String info;
	
	
	public Service(int id, String name, String info) {
		this.id = id;
		this.name = name;
		this.info = info;
	}


	public Service() {
		// TODO Auto-generated constructor stub
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getInfo() {
		return info;
	}


	public void setInfo(String info) {
		this.info = info;
	}


	@Override
	public String toString() {
		return "Service [name=" + name + "]";
	}



	@Override
	public int compareTo(Service o) {
		return this.getName().compareTo(o.getName());
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Service other = (Service) obj;
		return id == other.id;
	}


}

	

