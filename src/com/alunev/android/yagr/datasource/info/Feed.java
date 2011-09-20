package com.alunev.android.yagr.datasource.info;


public class Feed {
    int id;
    String title;
    int unreadCount;

    public Feed() {
    }

    public Feed(int id, String title, int unreadCount) {
        this.id = id;
        this.title = title;
        this.unreadCount = unreadCount;
    }

    public Feed(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + id;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + unreadCount;

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        }

        Feed other = (Feed) obj;
        if (id != other.id) {
            return false;
        } else if (title == null && other.title != null) {
            return false;
        } else if (!title.equals(other.title)) {
            return false;
        } else if (unreadCount != other.unreadCount) {
            return false;
        }

        return true;
    }
}
