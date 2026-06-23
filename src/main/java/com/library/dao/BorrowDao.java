package com.library.dao;

import com.library.common.DBUtil;
import com.library.model.Borrow;
import com.library.model.PageBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowDao {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    // 1. 新增借阅记录（借书）
    public int add(Borrow borrow) throws SQLException {
        int rows = 0;
        String sql = "INSERT INTO borrow(user_id,book_id,borrow_time,due_time,return_time,status) VALUES (?,?,?,?,?,?)";
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, borrow.getUserId());
            pstmt.setInt(2, borrow.getBookId());
            pstmt.setTimestamp(3, new java.sql.Timestamp(borrow.getBorrowTime().getTime()));
            pstmt.setTimestamp(4, new java.sql.Timestamp(borrow.getDueTime().getTime()));
            pstmt.setTimestamp(5, null);
            pstmt.setInt(6, 0);
            rows = pstmt.executeUpdate();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return rows;
    }

    // 2. 归还图书：更新return_time、status
    public int returnBook(Integer borrowId, Date now) throws SQLException {
        int rows = 0;
        String sql = "UPDATE borrow SET return_time=?,status=1 WHERE id=?";
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setTimestamp(1, new java.sql.Timestamp(now.getTime()));
            pstmt.setInt(2, borrowId);
            rows = pstmt.executeUpdate();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return rows;
    }

    // 3. 根据用户id查询个人借阅记录（分页）
    public PageBean<Borrow> listByUserId(Integer userId, int pageNum, int pageSize) throws SQLException {
        PageBean<Borrow> page = new PageBean<>();
        List<Borrow> list = new ArrayList<>();
        // 总数
        String countSql = "SELECT COUNT(*) FROM borrow WHERE user_id=?";
        conn = DBUtil.getConnection();
        pstmt = conn.prepareStatement(countSql);
        pstmt.setInt(1, userId);
        rs = pstmt.executeQuery();
        if(rs.next()) page.setTotalCount(rs.getInt(1));
        DBUtil.close(null,pstmt,rs);

        // 分页数据
        String dataSql = "SELECT * FROM borrow WHERE user_id=? LIMIT ?,?";
        conn = DBUtil.getConnection();
        pstmt = conn.prepareStatement(dataSql);
        pstmt.setInt(1, userId);
        pstmt.setInt(2, (pageNum-1)*pageSize);
        pstmt.setInt(3, pageSize);
        rs = pstmt.executeQuery();
        while(rs.next()){
            Borrow b = new Borrow();
            b.setId(rs.getInt("id"));
            b.setUserId(rs.getInt("user_id"));
            b.setBookId(rs.getInt("book_id"));
            b.setBorrowTime(rs.getTimestamp("borrow_time"));
            b.setDueTime(rs.getTimestamp("due_time"));
            b.setReturnTime(rs.getTimestamp("return_time"));
            b.setStatus(rs.getInt("status"));
            list.add(b);
        }
        page.setData(list);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.calcPageCount();
        DBUtil.close(conn,pstmt,rs);
        return page;
    }

    // 4. 查询全部逾期未还记录（status=0 且 当前时间>到期日）
    public List<Borrow> listOverdue() throws SQLException {
        List<Borrow> list = new ArrayList<>();
        String sql = "SELECT * FROM borrow WHERE status=0 AND due_time < NOW()";
        conn = DBUtil.getConnection();
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
        while(rs.next()){
            Borrow b = new Borrow();
            b.setId(rs.getInt("id"));
            b.setUserId(rs.getInt("user_id"));
            b.setBookId(rs.getInt("book_id"));
            b.setBorrowTime(rs.getTimestamp("borrow_time"));
            b.setDueTime(rs.getTimestamp("due_time"));
            b.setReturnTime(rs.getTimestamp("return_time"));
            b.setStatus(rs.getInt("status"));
            list.add(b);
        }
        DBUtil.close(conn,pstmt,rs);
        return list;
    }

    // 5. 根据借阅id单条查询
    public Borrow getById(Integer borrowId) throws SQLException {
        Borrow borrow = null;
        String sql = "SELECT * FROM borrow WHERE id=?";
        conn = DBUtil.getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, borrowId);
        rs = pstmt.executeQuery();
        if(rs.next()){
            borrow = new Borrow();
            borrow.setId(rs.getInt("id"));
            borrow.setUserId(rs.getInt("user_id"));
            borrow.setBookId(rs.getInt("book_id"));
            borrow.setBorrowTime(rs.getTimestamp("borrow_time"));
            borrow.setDueTime(rs.getTimestamp("due_time"));
            borrow.setReturnTime(rs.getTimestamp("return_time"));
            borrow.setStatus(rs.getInt("status"));
        }
        DBUtil.close(conn,pstmt,rs);
        return borrow;
    }
}