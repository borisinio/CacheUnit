package main.java.com.hit.dm;

import java.io.Serializable;

public class DataModel<T> implements Serializable {

    Long dataModelId;
    T content;

    public DataModel (Long dataModelId, T content) {
        this.dataModelId = dataModelId ;
        this.content = content;
    }

    public Long getId() { return dataModelId; }

    public T getContent() {	return content;	}

    public void setContent(T content) { this.content = content; }

    public void setId(Long id) { this.dataModelId = id; }

    @Override
    public String toString() {
        return "dataModelId:" + this.dataModelId + ",content:" + this.content;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}
