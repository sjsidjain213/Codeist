package dao;

public class Acknowledgement {
private String matchedCount,modifiedCount,upsertedId;

public String getMatchedCount() {
	return matchedCount;
}

public void setMatchedCount(String matchedCount) {
	this.matchedCount = matchedCount;
}

public String getModifiedCount() {
	return modifiedCount;
}

public void setModifiedCount(String modifiedCount) {
	this.modifiedCount = modifiedCount;
}

public String getUpsertedId() {
	return upsertedId;
}

public void setUpsertedId(String upsertedId) {
	this.upsertedId = upsertedId;
}

public Acknowledgement(String matchedCount, String modifiedCount, String upsertedId) {
	super();
	this.matchedCount = matchedCount;
	this.modifiedCount = modifiedCount;
	this.upsertedId = upsertedId;
}

public Acknowledgement() {
	super();
	// TODO Auto-generated constructor stub
}

@Override
public String toString() {
	return "Acknowledgement [matchedCount=" + matchedCount + ", modifiedCount=" + modifiedCount + ", upsertedId="
			+ upsertedId + "]";
}
}
