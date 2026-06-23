package com.library.model;

import java.util.Date;

public class Borrow {
    private Integer id;          // 借阅记录主键
    private Integer userId;      // 借阅人id
    private Integer bookId;      // 图书id
    private Date borrowTime;     // 借出时间
    private Date dueTime;        // 应还时间
    private Date returnTime;     // 归还时间，null=未还
    private Integer status;      // 0未还，1已还，2逾期未还

    // 无参构造
    public Borrow() {}

    // 全参构造
    public Borrow(Integer id, Integer userId, Integer bookId, Date borrowTime, Date dueTime, Date returnTime, Integer status) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowTime = borrowTime;
        this.dueTime = dueTime;
        this.returnTime = returnTime;
        this.status = status;
    }

    // Get & Set
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getBookId() { return bookId; }
    public void setBookId(Integer bookId) { this.bookId = bookId; }

    public Date getBorrowTime() { return borrowTime; }
    public void setBorrowTime(Date borrowTime) { this.borrowTime = borrowTime; }

    public Date getDueTime() { return dueTime; }
    public void setDueTime(Date dueTime) { this.dueTime = dueTime; }

    public Date getReturnTime() { return returnTime; }
    public void setReturnTime(Date returnTime) { this.returnTime = returnTime; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    @Override
    public String toString() {
        return "Borrow{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", borrowTime=" + borrowTime +
                ", dueTime=" + dueTime +
                ", returnTime=" + returnTime +
                ", status=" + status +
                '}';
    }
}