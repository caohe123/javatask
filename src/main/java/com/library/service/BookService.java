package com.library.service;

// 导入需要用到的类：BookDao(数据库操作)、Book(图书实体)、PageBean(分页工具)、List(集合)
import com.library.dao.BookDao;
import com.library.model.Book;
import com.library.model.PageBean;
import java.util.List;

// 专门处理图书业务
public class BookService {

    // ===================== 核心变量 =====================
    // 创建BookDao对象：Service不直接操作数据库，所有数据库操作都调用Dao
    private BookDao bookDao = new BookDao();

    // ===================== 1. 新增图书方法 =====================
    // 参数：前端传来的图书对象  返回值：true=成功 false=失败
    public boolean addBook(Book book) {
        try {
            // 调用Dao的新增方法，返回受影响行数 >0 代表新增成功
            return bookDao.addBook(book) > 0;
        } catch (Exception e) {
            // 捕获数据库异常（连接失败、SQL错误），打印错误信息
            e.printStackTrace();
            // 异常则返回失败
            return false;
        }
    }

    // ===================== 2. 根据ID删除图书方法 =====================
    // 参数：要删除的图书ID
    public boolean deleteBook(Integer id) {
        try {
            // 调用Dao的删除方法
            return bookDao.deleteBook(id) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===================== 3. 修改图书方法 =====================
    // 给借阅模块用：修改库存/图书信息
    public boolean updateBook(Book book) {
        try {
            // 调用Dao的修改方法
            return bookDao.updateBook(book) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===================== 4. 根据ID查询单本图书方法 =====================
    // 编辑页面/借阅模块用：查询图书详情
    public Book getBookById(Integer id) {
        try {
            // 调用Dao，返回查询到的图书对象
            return bookDao.getBookById(id);
        } catch (Exception e) {
            e.printStackTrace();
            // 异常返回null
            return null;
        }
    }

    // ===================== 5. 分页+搜索核心方法 =====================
    // 参数：当前页码、搜索关键词  返回值：封装好的分页数据
    public PageBean<Book> findBookByPage(int currentPage, String keyword) {
        // 创建分页对象：用来装所有分页相关数据
        PageBean<Book> pb = new PageBean<>();
        // 固定每页显示5条数据（学生项目简化版）
        int pageSize = 5;

        try {
            // 1. 调用Dao方法6：统计符合条件的图书总数量（算页数用）
            int totalCount = bookDao.countBooks(keyword);
            // 2. 调用Dao方法5：查询当前页要展示的图书列表
            List<Book> bookList = bookDao.listBooks(currentPage, pageSize, keyword);

            // 3. 给分页对象赋值
            pb.setList(bookList);        // 当前页的图书数据
            pb.setTotalCount(totalCount); // 图书总数量
            pb.setCurrentPage(currentPage); // 当前页码
            pb.setPageSize(pageSize);    // 每页条数

            // 4. 计算总页数
            // 如果总数量能整除每页条数 → 直接除
            // 不能整除 → 商+1
            int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
            // 把总页数存入分页对象
            pb.setTotalPage(totalPage);

        } catch (Exception e) {
            // 捕获分页查询的所有异常
            e.printStackTrace();
        }

        // 最终返回完整的分页数据给Servlet
        return pb;
    }
}