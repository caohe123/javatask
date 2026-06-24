package com.library.servlet;
import com.library.common.BaseServlet;
import com.library.model.Borrow;
import com.library.model.PageBean;
import com.library.model.User;
import com.library.service.BorrowService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/borrow")
public class BorrowServlet extends BaseServlet {
    private final BorrowService borrowService = new BorrowService();

    // GET请求统一交给doPost处理，走父类的action反射逻辑
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    // 借书：/borrow?action=borrow&bookId=xxx
    public String borrow(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        System.out.println("=====进入借书方法=====");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        HttpSession session = req.getSession();
        User loginUser = (User) session.getAttribute("user");
        if (loginUser == null) {
            return "redirect:" + req.getContextPath() + "/login.jsp";
        }

        String bookIdStr = req.getParameter("bookId");
        if (bookIdStr == null || bookIdStr.trim().length() == 0) {
            req.setAttribute("msg", "图书编号参数缺失！");
            return "/list.jsp";
        }

        Integer bookId;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            req.setAttribute("msg", "图书编号格式错误！");
            return "/list.jsp";
        }

        boolean flag = borrowService.borrowBook(loginUser.getId(), bookId);
        if (flag) {
            System.out.println("借书成功，跳转借阅列表");
            return "redirect:" + req.getContextPath() + "/borrow?action=list";
        } else {
            req.setAttribute("msg", "图书库存不足，暂时无法借阅！");
            return "/list.jsp";
        }
    }

    // 还书：/borrow?action=returnBook&borrowId=xxx
    public String returnBook(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        System.out.println("=====进入还书方法=====");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String borrowIdStr = req.getParameter("borrowId");
        if (borrowIdStr == null || borrowIdStr.trim().length() == 0) {
            req.setAttribute("msg", "借阅ID参数缺失！");
            return "/templates/borrow/myList.jsp";
        }

        Integer borrowId;
        try {
            borrowId = Integer.parseInt(borrowIdStr);
        } catch (NumberFormatException e) {
            req.setAttribute("msg", "借阅ID格式错误！");
            return "/templates/borrow/myList.jsp";
        }

        borrowService.returnBook(borrowId);
        // 修复：重定向补全项目上下文路径
        return "redirect:" + req.getContextPath() + "/borrow?action=list";
    }

    // 我的借阅记录分页列表 /borrow?action=list&pageNum=1
    public String list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        System.out.println("=====进入借阅列表方法=====");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        HttpSession session = req.getSession();
        User loginUser = (User) session.getAttribute("user");
        if (loginUser == null) {
            return "redirect:" + req.getContextPath() + "/login.jsp";
        }

        int pageNum = 1;
        String pageNumStr = req.getParameter("pageNum");
        if (pageNumStr != null && pageNumStr.trim().length() > 0) {
            try {
                pageNum = Integer.parseInt(pageNumStr);
                if (pageNum < 1) pageNum = 1;
            } catch (NumberFormatException e) {
                pageNum = 1;
            }
        }
        int pageSize = 6;

        PageBean<Borrow> page = borrowService.getUserBorrowList(loginUser.getId(), pageNum, pageSize);
        req.setAttribute("borrowPage", page);
        return "/templates/borrow/myList.jsp";
    }

    // 逾期图书统计页面 /borrow?action=overdue
    public String overdue(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        System.out.println("=====进入逾期图书方法=====");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        List<Borrow> overdueList = borrowService.getOverdueList();
        req.setAttribute("overdueList", overdueList);
        return "/templates/borrow/overdue.jsp";
    }

    // 管理员：查询所有用户的借阅记录 /borrow?action=allList&pageNum=1
    public String allList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        System.out.println("=====管理员查询全部借阅记录=====");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        HttpSession session = req.getSession();
        User loginUser = (User) session.getAttribute("user");

        // 正向判断管理员身份：用户不为空 且 角色等于admin，全程不使用!=
        boolean isAdmin = (loginUser != null) && ("admin".equals(loginUser.getRole()));
        if (!isAdmin) {
            return "redirect:" + req.getContextPath() + "/login.jsp";
        }

        // 分页参数处理
        int pageNum = 1;
        String pageNumStr = req.getParameter("pageNum");
        if (pageNumStr != null && pageNumStr.trim().length() > 0) {
            try {
                pageNum = Integer.parseInt(pageNumStr);
                if (pageNum < 1) pageNum = 1;
            } catch (NumberFormatException e) {
                pageNum = 1;
            }
        }
        int pageSize = 10;

        // 调用业务层查询全部借阅记录
        PageBean<Borrow> page = borrowService.getAllBorrowList(pageNum, pageSize);
        req.setAttribute("borrowPage", page);

        // 转发到管理员专属的全量借阅列表页面
        return "/templates/borrow/allBorrowList.jsp";
    }
}