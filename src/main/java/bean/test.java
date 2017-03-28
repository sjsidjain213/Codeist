package bean;

public class test {
private String img;

public test(String img) {
	super();
	this.img = img;
}

public test() {
	super();
	// TODO Auto-generated constructor stub
}

@Override
public String toString() {
	return "test [img=" + img + "]";
}

public String getImg() {
	return img;
}

public void setImg(String img) {
	this.img = img;
}
}
