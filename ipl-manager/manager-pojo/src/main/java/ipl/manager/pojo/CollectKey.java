package ipl.manager.pojo;

public class CollectKey {
    private Long userId;

    private Long docId;

    private Long mid;

    public Long getMid(){return mid;}

    public void setMid(Long mid){this.mid = mid; }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }
}
