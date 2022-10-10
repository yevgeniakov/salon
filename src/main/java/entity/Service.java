package entity;

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


}

	

