package com.library.service;

import com.library.dao.BookDao;
import com.library.dao.BorrowDao;
import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.PageBean;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BorrowService {
    private final BorrowDao borrowDao = new BorrowDao();
    private final BookDao bookDao = new BookDao();

    /**
     * 借书业务
     * @param userId 借阅人id
     * @param bookId 图书id
     * @return true借书成功，false失败（库存不足）
     */
    // 改为 throws Exception，兼容BookDao抛出的Exception
    public boolean borrowBook(Integer userId, Integer bookId) throws Exception {
        Book book = bookDao.getBookById(bookId);
        if(book == null || book.getStock() <= 0){
            return false;
        }
        Borrow borrow = new Borrow();
        borrow.setUserId(userId);
        borrow.setBookId(bookId);
        Date now = new Date();
        borrow.setBorrowTime(now);
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_MONTH,30);
        borrow.setDueTime(cal.getTime());
        borrow.setReturnTime(null);
        borrow.setStatus(0);

        borrowDao.add(borrow);

        book.setStock(book.getStock()-1);
        bookDao.updateBook(book);
        return true;
    }

    /**
     * 还书业务
     * @param borrowId 借阅记录id
     */
    public void returnBook(Integer borrowId) throws Exception {
        Date now = new Date();
        Borrow borrow = borrowDao.getById(borrowId);
        if(borrow == null || borrow.getStatus() == 1){
            return;
        }
        borrowDao.returnBook(borrowId, now);
        Book book = bookDao.getBookById(borrow.getBookId());
        book.setStock(book.getStock()+1);
        bookDao.updateBook(book);
    }

    // 分页查询当前用户个人借阅列表
    public PageBean<Borrow> getUserBorrowList(Integer userId, int pageNum, int pageSize) throws SQLException {
        return borrowDao.listByUserId(userId, pageNum, pageSize);
    }

    // 查询全部逾期未还记录
    public List<Borrow> getOverdueList() throws SQLException {
        return borrowDao.listOverdue();
    }
}