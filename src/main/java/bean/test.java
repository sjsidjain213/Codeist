package bean;

import java.util.ArrayList;

public class test {
private String img;
private Boolean loggedin;
private ArrayList<MultiUse> data;

public Boolean getLoggedin() {
	return loggedin;
}

public void setLoggedin(Boolean loggedin) {
	this.loggedin = loggedin;
}

public ArrayList<MultiUse> getData() {
	return data;
}

public void setData(ArrayList<MultiUse> data) {
	this.data = data;
}

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
