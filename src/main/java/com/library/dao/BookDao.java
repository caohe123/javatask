package com.library.dao;

import com.library.common.DBUtil; // A写的数据库连接工具
import com.library.model.Book;   // 图书实体类
import java.sql.Connection;       // JDBC数据库连接
import java.sql.PreparedStatement;// JDBC预处理SQL
import java.sql.ResultSet;        // JDBC查询结果集
import java.util.ArrayList;        // 集合，存储多本图书
import java.util.List;

public class BookDao {
    // 作用：向数据库book表插入一本新图书
    // 参数：Book对象（前端传来的图书数据）
   // 返回值：受影响的行数（>0代表新增成功）
   // throws Exception：抛出异常，交给Service统一处理
    public int addBook(Book book) throws Exception {
        // 1. 通过DBUtil获取数据库连接
        Connection conn = DBUtil.getConnection();
        // 2. 编写新增SQL语句，? 是占位符（防止SQL注入）
        String sql = "insert into book(name,author,publisher,price,stock,category) values(?,?,?,?,?,?)";
        // 3. 预处理SQL语句
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 4. 给SQL的?赋值（对应数据库字段）
        pstmt.setString(1, book.getName());    // 第1个?：书名
        pstmt.setString(2, book.getAuthor());  // 第2个?：作者
        pstmt.setString(3, book.getPublisher());//第3个?：出版社
        pstmt.setDouble(4, book.getPrice());   // 第4个?：价格
        pstmt.setInt(5, book.getStock());       // 第5个?：库存
        pstmt.setString(6, book.getCategory()); // 第6个?：分类

        // 5. 执行增删改SQL，返回受影响行数
        int rows = pstmt.executeUpdate();
        // 6. 关闭数据库资源（复用DBUtil的close方法）
        DBUtil.close(conn,pstmt,null);
        // 7. 返回结果给Service
        return rows;
    }
    // 作用：根据图书id删除数据库中的数据
    // 参数：图书编号id
    public int deleteBook(Integer id) throws Exception {
        Connection conn = DBUtil.getConnection();
        // 删除SQL：根据id删除
        String sql = "delete from book where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 给占位符赋值：要删除的图书id
        pstmt.setInt(1, id);

        int rows = pstmt.executeUpdate();
        DBUtil.close(conn,pstmt,null);
        return rows;
    }
    // 作用：修改图书所有信息（包括库存，C的借阅模块用）
    // 参数：修改后的Book对象
    public int updateBook(Book book) throws Exception {
        Connection conn = DBUtil.getConnection();
        // 修改SQL：根据id更新所有字段
        String sql = "update book set name=?,author=?,publisher=?,price=?,stock=?,category=? where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 按顺序给?赋值
        pstmt.setString(1, book.getName());
        pstmt.setString(2, book.getAuthor());
        pstmt.setString(3, book.getPublisher());
        pstmt.setDouble(4, book.getPrice());
        pstmt.setInt(5, book.getStock());
        pstmt.setString(6, book.getCategory());
        pstmt.setInt(7, book.getId()); // 最后一个?：图书id（定位要修改的书）

        int rows = pstmt.executeUpdate();
        DBUtil.close(conn,pstmt,null);
        return rows;
    }
    // 作用：根据id查一本图书（编辑页面回显、借阅模块查询用）
    public Book getBookById(Integer id) throws Exception {
        Connection conn = DBUtil.getConnection();
        // 查询SQL：根据id查单条数据
        String sql = "select * from book where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);

        // 执行查询SQL，返回结果集
        ResultSet rs = pstmt.executeQuery();
        Book book = null;

        // 如果查询到数据，把结果封装成Book对象
        if(rs.next()){
            book = new Book();
            // 从结果集取值，赋值给Book对象
            book.setId(rs.getInt("id"));
            book.setName(rs.getString("name"));
            book.setAuthor(rs.getString("author"));
            book.setPublisher(rs.getString("publisher"));
            book.setPrice(rs.getDouble("price"));
            book.setStock(rs.getInt("stock"));
            book.setCategory(rs.getString("category"));
        }

        DBUtil.close(conn,pstmt,rs);
        // 返回图书对象给Service
        return book;
    }
    // 作用：分页查询图书列表，支持按书名搜索
    // pageNum：当前页码  pageSize：每页条数  keyword：搜索关键词
    public List<Book> listBooks(int pageNum, int pageSize, String keyword) throws Exception {
        Connection conn = DBUtil.getConnection();
        // 查询SQL：like模糊搜索书名，limit分页
        String sql = "select * from book where name like ? limit ?,?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 赋值：%关键词% 代表模糊匹配
        pstmt.setString(1, "%"+keyword+"%");
        // 分页起始位置
        pstmt.setInt(2, (pageNum-1)*pageSize);
        // 每页显示数量
        pstmt.setInt(3, pageSize);

        ResultSet rs = pstmt.executeQuery();
        // 创建集合，存储多本图书
        List<Book> list = new ArrayList<>();

        // 循环遍历结果集，封装成Book对象存入集合
        while(rs.next()){
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setName(rs.getString("name"));
            book.setAuthor(rs.getString("author"));
            book.setPublisher(rs.getString("publisher"));
            book.setPrice(rs.getDouble("price"));
            book.setStock(rs.getInt("stock"));
            book.setCategory(rs.getString("category"));
            list.add(book);
        }

        DBUtil.close(conn,pstmt,rs);
        // 返回图书列表集合
        return list;
    }
    // 作用：统计符合搜索条件的图书总数（用来计算总页数）
    public int countBooks(String keyword) throws Exception {
        Connection conn = DBUtil.getConnection();
        // 统计SQL：count(*)计算总条数
        String sql = "select count(*) from book where name like ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "%"+keyword+"%");

        ResultSet rs = pstmt.executeQuery();
        int count = 0;
        if(rs.next()){
            // 获取统计的总数量
            count = rs.getInt(1);
        }

        DBUtil.close(conn,pstmt,rs);
        // 返回总数量给Service
        return count;
    }
}

