package bean;

public class Acknowledgement {
private String matchedCount,modifiedCount,upsertedId,message;

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

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}

@Override
public String toString() {
	return "Acknowledgement [matchedCount=" + matchedCount + ", modifiedCount=" + modifiedCount + ", upsertedId="
			+ upsertedId + ", message=" + message + "]";
}

public Acknowledgement() {
	super();
	// TODO Auto-generated constructor stub
}

public Acknowledgement(String matchedCount, String modifiedCount, String upsertedId, String message) {
	super();
	this.matchedCount = matchedCount;
	this.modifiedCount = modifiedCount;
	this.upsertedId = upsertedId;
	this.message = message;
}


}
