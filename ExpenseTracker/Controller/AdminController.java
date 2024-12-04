package com.example.ExpenseTracker.Controller;

import com.example.ExpenseTracker.Modal.Category;
import com.example.ExpenseTracker.Modal.Transaction;
import com.example.ExpenseTracker.Modal.User;
import com.example.ExpenseTracker.Service.CategoryService;
import com.example.ExpenseTracker.Service.TransactionService;
import com.example.ExpenseTracker.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CategoryService categoryService;

    // Admin Dashboard
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        List<User> users = userService.getAllUsers();
        List<Transaction> transactions = transactionService.getAllTransactions();
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("users", users);
        model.addAttribute("transactions", transactions);
        model.addAttribute("categories", categories);
        return "admin/dashboard";
    }

    // Delete User
    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/dashboard";
    }

    // Delete Category
    @PostMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return "redirect:/admin/dashboard";
    }
}


