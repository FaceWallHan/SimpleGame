package com.example.simple.bean;

public class BeanComment {
    private String commit,commitTime,reviewer;

    public BeanComment() {
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public BeanComment(String commit, String commitTime, String reviewer) {
        this.commit = commit;
        this.commitTime = commitTime;
        this.reviewer = reviewer;
    }
}
